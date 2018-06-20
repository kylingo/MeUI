package com.me.ui.library.download;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.me.ui.library.download.DownloadDatabase.DownloadTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadStorageHelper {

    private static final String TAG = "DownloadStorageHelper";

    private Context mContext;
    private static String mDownloadPath;
    private static boolean isInited;
    private static DownloadStorageHelper sInstance;
    private DownloadDatabase mDatabase;

    public static DownloadStorageHelper getInstance(Context context) {
        synchronized (DownloadStorageHelper.class) {
            if (sInstance == null) {
                synchronized (DownloadStorageHelper.class) {
                    sInstance = new DownloadStorageHelper(context);
                }
            }
        }

        return sInstance;
    }

    private DownloadStorageHelper(Context context) {
        mDatabase = new DownloadDatabase(context);
        mContext = context;
        if (mContext != null) {
            File rootFile = mContext.getExternalCacheDir();
            if (rootFile != null) {
                mDownloadPath = rootFile.getAbsolutePath().concat(File.separator).concat("Downloads");
            }
        }
    }

    public synchronized void init() {
        if (!isInited) {
            File dir = new File(getDownloadFileRoot());
            if (!dir.exists()) {
                isInited = dir.mkdirs();
            } else {
                isInited = true;
            }
        }
    }

    public static String getDownloadFileRoot() {
        return mDownloadPath;
    }

    List<DownloadItem> getQuickTable() {
        SQLiteDatabase helper = mDatabase.getReadableDatabase();

        List<DownloadItem> records = new ArrayList<DownloadItem>();

        Cursor cursor = helper.query(DownloadTable.TABLE_NAME,
                new String[]{
                        DownloadTable.COLUMN_DOWNLOAD_ID,
                        DownloadTable.COLUMN_LOCAL_URI,
                        DownloadTable.COLUMN_STATUS,
                        DownloadTable.COLUMN_EXTRA2},
                null, null, null, null, null);

        while (cursor != null && cursor.moveToNext()) {
            DownloadItem record = new DownloadItem();
            record.setLocalUri(cursor.getString(cursor.getColumnIndex(
                    DownloadTable.COLUMN_LOCAL_URI)));
            record.setDownloadId(cursor.getString(cursor.getColumnIndex(
                    DownloadTable.COLUMN_DOWNLOAD_ID)));
            record.setStatus(cursor.getInt(cursor.getColumnIndex(
                    DownloadTable.COLUMN_STATUS)));
            record.setExtra2(cursor.getString(cursor.getColumnIndex(
                    DownloadTable.COLUMN_EXTRA2)));

            records.add(record);
        }

        if (cursor != null) {
            cursor.close();
        }

        return records;
    }

    public List<DownloadItem> getDownloadRecords(int type, int status, String key) {
        SQLiteDatabase helper = mDatabase.getReadableDatabase();

        List<DownloadItem> records = new ArrayList<DownloadItem>();

        String where = type == -1 ? "" : DownloadTable.COLUMN_TYPE + "=" + type;
        if (status != -1) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += DownloadTable.COLUMN_STATUS + "=" + status;
        }
        if (!TextUtils.isEmpty(key)) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += DownloadTable.COLUMN_EXTRA1 + "='" + key.replace("'", "''") + "'";
        }

        Cursor cursor = helper.query(DownloadTable.TABLE_NAME,
                new String[]{
                        DownloadTable._ID,
                        DownloadTable.COLUMN_DOWNLOAD_ID,
                        DownloadTable.COLUMN_CREATE_TIMESTAMP,
                        DownloadTable.COLUMN_LAST_MODIFIED_TIMESTAMP,
                        DownloadTable.COLUMN_BYTES_TOTAL,
                        DownloadTable.COLUMN_LOCAL_FILENAME,
                        DownloadTable.COLUMN_LOCAL_URI,
                        DownloadTable.COLUMN_URI,
                        DownloadTable.COLUMN_STATUS,
                        DownloadTable.COLUMN_TYPE,
                        DownloadTable.COLUMN_EXTRA1,
                        DownloadTable.COLUMN_EXTRA2},
                where, null, null, null, DownloadTable.COLUMN_TYPE + " ASC, " +
                        "CASE " + DownloadTable.COLUMN_STATUS + " WHEN " +
                        DownloadConstants.STATUS_COMPLETE + " THEN 1 ELSE 0 END ASC, "
                        + DownloadTable.COLUMN_LAST_MODIFIED_TIMESTAMP + " DESC ");

        while (cursor != null && cursor.moveToNext()) {
            int recordType = cursor.getInt(cursor.getColumnIndex(
                    DownloadTable.COLUMN_TYPE));
            DownloadItem record = DownloadItem.DownloadItemFactory.getDownloadItem(recordType);

            record.setCreateTime(cursor.getLong(cursor.getColumnIndex(
                    DownloadTable.COLUMN_CREATE_TIMESTAMP)));
            record.setLastModifiedTime(cursor.getLong(cursor.getColumnIndex(
                    DownloadTable.COLUMN_LAST_MODIFIED_TIMESTAMP)));
            record.setTotalBytes(cursor.getLong(cursor.getColumnIndex(
                    DownloadTable.COLUMN_BYTES_TOTAL)));
            record.setFileName(cursor.getString(cursor.getColumnIndex(
                    DownloadTable.COLUMN_LOCAL_FILENAME)));
            record.setLocalUri(cursor.getString(cursor.getColumnIndex(
                    DownloadTable.COLUMN_LOCAL_URI)));
            record.setUri(cursor.getString(cursor.getColumnIndex(
                    DownloadTable.COLUMN_URI)));
            record.setStatus(cursor.getInt(cursor.getColumnIndex(
                    DownloadTable.COLUMN_STATUS)));
            record.setExtra1(cursor.getString(cursor.getColumnIndex(
                    DownloadTable.COLUMN_EXTRA1)));
            record.setExtra2(cursor.getString(cursor.getColumnIndex(
                    DownloadTable.COLUMN_EXTRA2)));
            record.setId(cursor.getLong(cursor.getColumnIndex(
                    DownloadTable._ID)));
            record.setDownloadId(cursor.getString(cursor.getColumnIndex(
                    DownloadTable.COLUMN_DOWNLOAD_ID)));

            records.add(record);
        }

        if (cursor != null) {
            cursor.close();
        }

        return records;
    }

    public int getDownloadRecordCount(int type, int status) {
        SQLiteDatabase helper = mDatabase.getReadableDatabase();
        String where = type == -1 ? (" WHERE " + DownloadTable.COLUMN_DOWNLOAD_ID + " NOT LIKE '%hungama%'") : " WHERE " + DownloadTable.COLUMN_TYPE + "=" + type;
        if (status != -1) {
            if (where.length() > 0) {
                where += " AND ";
            }
            where += DownloadTable.COLUMN_STATUS + "=" + status;
        }

        Cursor cursor = helper.rawQuery("select count(1) from " + DownloadTable.TABLE_NAME + where, null);

        try {
            if (cursor != null && cursor.moveToNext()) {
                return cursor.getInt(0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return 0;
    }

    public synchronized int deleteDownloadUris(List<String> localUris) {
        SQLiteDatabase helper = mDatabase.getWritableDatabase();
        int affectCount = 0;

        helper.beginTransaction();
        try {
            for (String localUri : localUris) {
                affectCount = helper.delete(DownloadTable.TABLE_NAME,
                        DownloadTable.COLUMN_LOCAL_URI + "=?",
                        new String[]{localUri});

                helper.yieldIfContendedSafely();
            }
            helper.setTransactionSuccessful();
        } finally {
            helper.endTransaction();
        }

        return affectCount;
    }

    public synchronized int deleteDownloadRecord(List<String> ids) {
        SQLiteDatabase helper = mDatabase.getWritableDatabase();
        int affectCount = 0;

        helper.beginTransaction();
        try {
            for (String id : ids) {
                affectCount = helper.delete(DownloadTable.TABLE_NAME,
                        DownloadTable.COLUMN_DOWNLOAD_ID + "='" + id + "'", null);

                helper.yieldIfContendedSafely();
            }
            helper.setTransactionSuccessful();
        } finally {
            helper.endTransaction();
        }

        return affectCount;
    }

    synchronized void updateDownloadRecord(final DownloadTask task) {
        List<DownloadTask> tasks = new ArrayList<DownloadTask>();
        tasks.add(task);
        updateDownloadRecord(tasks);
    }

    synchronized void updateDownloadRecord(final List<DownloadTask> tasks) {
        SQLiteDatabase helper = mDatabase.getWritableDatabase();
        long currentTime = System.currentTimeMillis();
        helper.beginTransaction();

        try {
            for (DownloadTask task : tasks) {
                ContentValues cv = new ContentValues();
                cv.put(DownloadTable.COLUMN_LAST_MODIFIED_TIMESTAMP, currentTime);
                cv.put(DownloadTable.COLUMN_BYTES_TOTAL, task.getTotalSize());
                cv.put(DownloadTable.COLUMN_LOCAL_FILENAME, task.getDownloadFile().getName());
                cv.put(DownloadTable.COLUMN_LOCAL_URI, task.getDownloadFile().toString());
                cv.put(DownloadTable.COLUMN_URI, task.getUrl());
                cv.put(DownloadTable.COLUMN_STATUS, task.getDownloadStatus());
                cv.put(DownloadTable.COLUMN_TYPE, task.getDownloadItem().getType());
                cv.put(DownloadTable.COLUMN_EXTRA1, task.getDownloadItem().getExtra1());
                cv.put(DownloadTable.COLUMN_EXTRA2, task.getDownloadItem().getExtra2());
                cv.put(DownloadTable.COLUMN_DOWNLOAD_ID, task.getId());

                long id = 0;
                if (isTaskExists(task.getId())) {
                    id = helper.update(DownloadTable.TABLE_NAME, cv,
                            DownloadTable.COLUMN_DOWNLOAD_ID + "='" + task.getId() + "'", null);
                } else {
                    cv.put(DownloadTable.COLUMN_CREATE_TIMESTAMP, currentTime);
                    id = helper.insert(DownloadTable.TABLE_NAME, null, cv);
                }
            }
            helper.setTransactionSuccessful();
        } finally {
            helper.endTransaction();
        }

    }

    private boolean isTaskExists(String id) {
        Cursor cursor = null;
        try {
            SQLiteDatabase helper = mDatabase.getReadableDatabase();
            cursor = helper.query(DownloadTable.TABLE_NAME, new String[]{DownloadTable._ID},
                    DownloadTable.COLUMN_DOWNLOAD_ID + "='" + id + "'", null, null, null, null);
            return cursor != null && cursor.moveToNext();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
