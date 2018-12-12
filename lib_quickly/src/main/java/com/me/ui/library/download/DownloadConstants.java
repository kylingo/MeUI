package com.me.ui.library.download;

import android.util.Log;

public class DownloadConstants {

    static final boolean DEBUG = true;
    static final boolean SLOW_MODE = false;
    static final String LOG_TAG = "DownloadTask";
    static final String TEMP_SUFFIX = ".download";
    static final boolean AUTO_START_ON_LOGIN = false;

    public static final String ACTION_DOWNLOAD_STATUS = "com.android.music.download.status";
    public static final String ACTION_SONG_DOWNLOAD_FINISH = "com.letv.music.song.download.notify";

    public static final String KEY_ACTION = "service_action";
    public static final String KEY_STATUS = "download_status";
    public static final String KEY_ERROR_CODE = "error_code";
    public static final String KEY_RESOURCE_ID = "resource_id";
    public static final String KEY_ID = "download_id";
    public static final String KEY_ID_LIST = "download_id_list";
    public static final String KEY_ITEM = "download_item";
    public static final String KEY_ITEM_LIST = "download_item_list";
    public static final String KEY_IS_CLEAR_DOWNLOAD = "is_clear_download";
    public static final String KEY_DOWNLOADED_SIZE = "downloaded_size";
    public static final String KEY_TOTAL_SIZE = "total_size";

    public static final int STATUS_MISSING = -100;
    public static final int STATUS_WAITING = 0;
    public static final int STATUS_DOWNLOADING = 1;
    public static final int STATUS_PAUSED = 2;
    public static final int STATUS_COMPLETE = 3;
    public static final int STATUS_ERROR = 4;
    public static final int STATUS_CANCEL = 5;

    public static final int NO_ERROR = 0;
    public static final int ERROR_ALREADY_DOWNLOADED = 1;
    public static final int ERROR_IO = 2;
    public static final int ERROR_NETWORK = 3;
    public static final int ERROR_URL_INVALID = 4;

    public static class Action {
        public static final int ADD = 3;
        public static final int PAUSE = 4;
        public static final int RESUME = 5;
        public static final int DELETE = 6;
        public static final int PAUSE_ALL = 7;
        public static final int RESUME_ALL = 8;
        public static final int FREEZE = 9;
        public static final int THAW = 10;
        public static final int TASK_END = 11;
        public static final int REMOVE_ALL = 12;
    }

    static final void d(String message) {
        if (DEBUG) {
            Log.d(LOG_TAG, message);
        }
    }

    static final void e(String message) {
        if (DEBUG) {
            Log.d(LOG_TAG, message);
        }
    }
}
