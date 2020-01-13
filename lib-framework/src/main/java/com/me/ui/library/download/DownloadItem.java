package com.me.ui.library.download;

import java.io.File;
import java.io.Serializable;

public class DownloadItem implements Serializable, DownloadUrlTranslation {

    public static final int TYPE_UNKNOWN = 0;

    private long id;
    private String downloadId;
    private String fileName;
    private String localUri;
    private String uri;
    private int status;
    private long createTime;
    private long lastModifiedTime;
    private long totalBytes;
    private long downloadedBytes;

    protected int type = TYPE_UNKNOWN;
    private String name;
    private String extra1;
    private String extra2;

    public static class DownloadItemFactory {
        public static DownloadItem getDownloadItem(int type) {
            return new DownloadItem();
        }
    }

    boolean writeOutputFile(File tempFile, File finalFile) {
        return tempFile.renameTo(finalFile);
    }

    @Override
    public boolean onTranslate() {
        return false;
    }

    @Override
    public String getTranslateResult() {
        return null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLocalUri() {
        return localUri;
    }

    public void setLocalUri(String localUri) {
        this.localUri = localUri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public String getExtra1() {
        return extra1;
    }

    public void setExtra1(String extra1) {
        this.extra1 = extra1;
    }

    public String getExtra2() {
        return extra2;
    }

    public void setExtra2(String extra2) {
        this.extra2 = extra2;
    }

    public File getTempFile() {
        return new File(localUri + DownloadConstants.TEMP_SUFFIX);
    }

    public long getDownloadedBytes() {
        return downloadedBytes;
    }

    public void setDownloadedBytes(long downloadedBytes) {
        this.downloadedBytes = downloadedBytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDownloadId(String downloadId) {
        this.downloadId = downloadId;
    }

    public String getDownloadId() {
        return downloadId;
    }
}
