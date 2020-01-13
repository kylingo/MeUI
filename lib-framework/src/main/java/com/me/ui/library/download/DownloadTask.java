package com.me.ui.library.download;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class DownloadTask {

    private static final int TIME_OUT = 30000;
    private static final int BUFFER_SIZE = 1024 * 8;

    private HttpURLConnection mHttpURLConnection;
    private AsyncTask<Void, Integer, Long> mAsyncTask;
    private DownloadTaskListener mListener;
    private Context mContext;
    private boolean isOnLastWritingOut;
    private AtomicBoolean isFrozen = new AtomicBoolean(false);

    private int status = DownloadConstants.STATUS_WAITING;
    private int error = DownloadConstants.NO_ERROR;
    private File file;
    private File tempFile;
    private DownloadItem item;
    private String downloadUrl;
    private long downloadSize;
    private long totalSize;
    private int downloadPercent;
    private long lastReportPercent;
    private long previousDownloadSize;
    private long previousTotalSize;
    private long networkSpeed;
    private long startTime;
    private long totalTime;

    public DownloadTask(Context context, DownloadItem item, DownloadTaskListener listener) {
        super();
        this.item = item;
        this.mListener = listener;
        this.mContext = context;

        this.file = new File(item.getLocalUri());
        this.status = item.getStatus();
        this.previousTotalSize = item.getTotalBytes();
    }

    public DownloadTask(Context context, DownloadItem item, String path, String name, DownloadTaskListener listener) {
        super();
        this.item = item;
        this.mListener = listener;
        this.mContext = context;

        name = name.replaceAll("/", " ");
        this.file = new File(path, name);
    }

    public int getDownloadPercent() {
        return downloadPercent;
    }

    public long getDownloadSize() {
        return downloadSize + previousDownloadSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public long getDownloadSpeed() {
        return this.networkSpeed;
    }

    public long getTotalTime() {
        return this.totalTime;
    }

    public String getId() {
        return item.getDownloadId();
    }

    public boolean isEquals(String id) {
        return TextUtils.equals(id, getId());
    }

    public DownloadTaskListener getListener() {
        return this.mListener;
    }

    public File getDownloadFile() {
        return file;
    }

    public File getDownloadTempFile() {
        return tempFile;
    }

    public int getDownloadStatus() {
        return status;
    }

    public int getError() {
        return error;
    }

    public DownloadItem getDownloadItem() {
        return item;
    }

    public String getUrl() {
        return downloadUrl;
    }

    public boolean resume() {
        if (isPaused()) {
            status = DownloadConstants.STATUS_WAITING;

            return true;
        }

        return false;
    }

    public boolean isWaiting() {
        return status == DownloadConstants.STATUS_WAITING;
    }

    public boolean isDownloading() {
        return status == DownloadConstants.STATUS_DOWNLOADING;
    }

    public boolean isPaused() {
        return status == DownloadConstants.STATUS_PAUSED || status == DownloadConstants.STATUS_ERROR;
    }

    public boolean isRemoved() {
        return status == DownloadConstants.STATUS_CANCEL;
    }

    public boolean isComplete() {
        return status == DownloadConstants.STATUS_COMPLETE;
    }

    public boolean isError() {
        return status == DownloadConstants.STATUS_ERROR;
    }

    public void pause() {
        status = DownloadConstants.STATUS_PAUSED;
        if (mAsyncTask != null && !isOnLastWritingOut) {
            mAsyncTask.cancel(true);
        }
    }

    public void remove() {
        status = DownloadConstants.STATUS_CANCEL;
        if (mAsyncTask != null && !isOnLastWritingOut) {
            mAsyncTask.cancel(true);
        }
    }

    public void setFrozenState(boolean frozen) {
        isFrozen.set(frozen);
    }

    public void execute(Executor executor) {
        mAsyncTask = new InnerDownloadTask();
        mAsyncTask.executeOnExecutor(executor);
    }

    private class InnerDownloadTask extends AsyncTask<Void, Integer, Long> {
        private final class ProgressReportingRandomAccessFile extends RandomAccessFile {
            private int progress = 0;

            public ProgressReportingRandomAccessFile(File file, String mode) throws FileNotFoundException {
                super(file, mode);
            }

            @Override
            public void write(byte[] buffer, int offset, int count) throws IOException {
                super.write(buffer, offset, count);
                progress += count;
                publishProgress(progress);
            }
        }

        @Override
        protected void onPreExecute() {
            DownloadConstants.d("Start Task : " + file.getName());

            status = DownloadConstants.STATUS_DOWNLOADING;
            error = DownloadConstants.NO_ERROR;
            if (mListener != null)
                mListener.onDownloadStart(DownloadTask.this);
        }

        @Override
        protected Long doInBackground(Void... params) {
            long result = -1;
            try {
                result = download();
            } catch (IOException e) {
                e.printStackTrace();
                if (e instanceof SocketException) {
                    error = DownloadConstants.ERROR_NETWORK;
                } else {
                    error = DownloadConstants.ERROR_IO;
                }
            } catch (NetworkErrorException e) {
                e.printStackTrace();
                error = DownloadConstants.ERROR_NETWORK;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                error = DownloadConstants.ERROR_URL_INVALID;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mHttpURLConnection != null) {
                    mHttpURLConnection.disconnect();
                }
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {

            if (progress.length > 1) {
                totalSize = progress[1];
                if (totalSize == -1) {
                    if (mListener != null)
                        mListener.onDownloadError(DownloadTask.this, error);
                }
            } else {
                totalTime = System.currentTimeMillis() - startTime;
                downloadSize = progress[0];
                downloadPercent = (int) ((downloadSize + previousDownloadSize) * 100 / totalSize);
                networkSpeed = downloadSize / totalTime;
                if (mListener != null) {
                    if (downloadPercent - lastReportPercent > 1 || downloadPercent == 100) {
                        lastReportPercent = downloadPercent;
                        mListener.updateProgress(DownloadTask.this);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(Long result) {
            if (isCancelled()) {
                return;
            }

            if (result == -1 || error != DownloadConstants.NO_ERROR) {
                status = DownloadConstants.STATUS_ERROR;
                if (error != DownloadConstants.NO_ERROR) {
                    DownloadConstants.e("Download failed: " + error);
                }
                if (mListener != null) {
                    mListener.onDownloadError(DownloadTask.this, error);
                }
                return;
            }

            // finish download
            status = DownloadConstants.STATUS_COMPLETE;
            if (mListener != null)
                mListener.onDownloadFinished(DownloadTask.this);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            if (tempFile != null && status == DownloadConstants.STATUS_CANCEL && tempFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                tempFile.delete();
            }
        }

        private String getFileExtension(String url) {
            return MimeTypeMap.getFileExtensionFromUrl(url);
        }

        private String getCurrentFileExtension(File file) {
            String extension = "";
            String fileName = file.toString();

            int i = fileName.lastIndexOf('.');
            int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

            if (i > p) {
                extension = fileName.substring(i);
            }

            return extension;
        }

        private long download() throws NetworkErrorException, IOException {
            downloadUrl = item.getUri();

            if (TextUtils.isEmpty(downloadUrl)) {
                int maxCheck = 120;
                while (item.onTranslate() && maxCheck-- > 0) {
                    DownloadConstants.d("Waiting for url translate " + maxCheck + "...");
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                downloadUrl = item.getTranslateResult();
            }

            if (!isNetworkAvailable(mContext)) {
                throw new NetworkErrorException("Network blocked.");
            }

            if (TextUtils.isEmpty(downloadUrl)) {
                DownloadConstants.e("download url is NULL !");
                throw new IllegalArgumentException("download url is NULL !");
            }

            DownloadConstants.d("Start download : " + downloadUrl);
            startTime = System.currentTimeMillis();

            File parentFile = file.getParentFile();
            if (!parentFile.exists() && !parentFile.mkdirs()) {
                DownloadConstants.e("make download folder failed !");
                throw new IOException("make download folder failed !");
            }

            mHttpURLConnection = getClient(downloadUrl);
            if (mHttpURLConnection == null) {
                throw new IllegalArgumentException("download url is invalid !");
            }

            mHttpURLConnection.connect();
            totalSize = mHttpURLConnection.getContentLength();

            // Set extension
            String extension = "." + getFileExtension(downloadUrl);
            String currentExtension = getCurrentFileExtension(file);
            if (!TextUtils.equals(currentExtension, extension)) {
                file = new File(file.getPath() + extension);
            }

            DownloadConstants.d("totalSize: " + totalSize);
            if (previousTotalSize == 0 && totalSize > 0) {
                DownloadConstants.d("save totalSize");
                previousTotalSize = totalSize;
                DownloadStorageHelper.getInstance(mContext).updateDownloadRecord(DownloadTask.this);
            }
            tempFile = new File(file.toString() + DownloadConstants.TEMP_SUFFIX);
            if (tempFile.exists()) {
                if (previousTotalSize == totalSize) {
                    DownloadConstants.d("File is not complete, length=" + tempFile.length());
                    previousDownloadSize = tempFile.length();

                    mHttpURLConnection.disconnect();
                    mHttpURLConnection = getClient(downloadUrl);
                    mHttpURLConnection.addRequestProperty("Range", "bytes=" + tempFile.length() + "-" + totalSize);
                    mHttpURLConnection.setInstanceFollowRedirects(true);
                    mHttpURLConnection.connect();
                } else {
                    DownloadConstants.e("totalSize changed. discard temp file!");
                    //noinspection ResultOfMethodCallIgnored
                    tempFile.delete();
                }
            }

            if (file.exists() && totalSize <= file.length()) {
                DownloadConstants.d("Output file already exists. Skipping download.");
                publishProgress(100);
                return totalSize;
            }

            long storage = getAvailableStorage();
            DownloadConstants.d("storage:" + storage + " totalSize:" + totalSize);
            if (totalSize - tempFile.length() > storage) {
                DownloadConstants.e("SD card no memory.");
                throw new IOException("SD card no memory.");
            }

            ProgressReportingRandomAccessFile outputStream = new ProgressReportingRandomAccessFile(tempFile, "rw");
            publishProgress(0, (int) totalSize);
            InputStream input = mHttpURLConnection.getInputStream();

            int bytesCopied = copy(input, outputStream);
            if (isCancelled()) {
                return bytesCopied;
            }

            if ((previousDownloadSize + bytesCopied) != totalSize && totalSize != -1) {
                DownloadConstants.e("Download incomplete: " + bytesCopied + " != " + totalSize);
                throw new IOException("Download incomplete: " + bytesCopied + " != " + totalSize);
            }

            isOnLastWritingOut = true;
            if (!item.writeOutputFile(tempFile, file)) {
                isOnLastWritingOut = false;
                DownloadConstants.e("Write final file error.");
                throw new IOException("Write final file error.");
            }
            isOnLastWritingOut = false;

            DownloadConstants.d("Download completed successfully.");
            return bytesCopied;

        }

        public int copy(InputStream input, RandomAccessFile out) throws IOException, NetworkErrorException {
            if (input == null || out == null) {
                return -1;
            }

            byte[] buffer = new byte[BUFFER_SIZE];
            BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
            DownloadConstants.d("length : " + out.length());

            int count = 0;
            int n;
            long errorBlockTimePreviousTime = -1;
            long expireTime;
            try {
                out.seek(out.length());

                while (!isCancelled()) {
                    if (isFrozen.get()) {
                        // DownloadConstants.d("freeze !!!!");
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    n = in.read(buffer, 0, BUFFER_SIZE);
                    if (n == -1) {
                        break;
                    }
                    out.write(buffer, 0, n);
                    count += n;

                    if (DownloadConstants.SLOW_MODE) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (!isNetworkAvailable(mContext)) {
                        DownloadConstants.e("Network blocked.");
                        throw new NetworkErrorException("Network blocked.");
                    }

                    if (networkSpeed == 0) {
                        if (errorBlockTimePreviousTime > 0) {
                            expireTime = System.currentTimeMillis() - errorBlockTimePreviousTime;
                            if (expireTime > TIME_OUT) {
                                DownloadConstants.e("connection time out.");
                                throw new NetworkErrorException("connection time out.");
                            }
                        } else {
                            errorBlockTimePreviousTime = System.currentTimeMillis();
                        }
                    } else {
                        expireTime = 0;
                        errorBlockTimePreviousTime = -1;
                    }
                }
            } finally {
                mHttpURLConnection.disconnect();
                mHttpURLConnection = null;
                out.close();
                in.close();
                input.close();
            }
            return count;

        }

        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                return false;
            }  else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED
                                || info[i].getState() == NetworkInfo.State.CONNECTING) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        private HttpURLConnection getClient(String sUrl) {
            try {
                URL url = new URL(sUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn .setRequestProperty("Accept-Encoding", "identity");
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                return conn;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private static long getAvailableStorage() {

        String storageDirectory;
        storageDirectory = Environment.getExternalStorageDirectory().toString();

        try {
            StatFs stat = new StatFs(storageDirectory);
            return ((long) stat.getAvailableBlocks() * (long) stat.getBlockSize());
        } catch (RuntimeException ex) {
            return 0;
        }
    }
}
