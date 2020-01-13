
package com.me.ui.library.download;

public interface DownloadTaskListener {

    void updateProgress(DownloadTask task);

    void onDownloadFinished(DownloadTask task);

    void onDownloadStart(DownloadTask task);

    void onDownloadError(DownloadTask task, int error);
}
