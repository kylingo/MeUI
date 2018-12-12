package com.me.ui.library.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

class DownloadDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "download.db";

    private static final int DB_VERSION = 2;

    public DownloadDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Record Sample
     */
    public static abstract class DownloadTable implements BaseColumns {

        public static final String TABLE_NAME = "download";

        public static final String COLUMN_DOWNLOAD_ID = "download_id";
        public static final String COLUMN_LAST_MODIFIED_TIMESTAMP = "last_modified_time";
        public static final String COLUMN_CREATE_TIMESTAMP = "create_time";
        public static final String COLUMN_BYTES_TOTAL = "total_byte";
        public static final String COLUMN_LOCAL_FILENAME = "local_filename";
        public static final String COLUMN_LOCAL_URI = "local_uri";
        public static final String COLUMN_URI = "uri";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_TYPE = "type";

        public static final String COLUMN_EXTRA1 = "extra1";
        public static final String COLUMN_EXTRA2 = "extra2";

        public static void onCreate(SQLiteDatabase sqLiteDatabase) {
            onUpgrade(sqLiteDatabase, 0, DB_VERSION);
        }

        public static void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_DOWNLOAD_ID + " TEXT, " +
                    COLUMN_LAST_MODIFIED_TIMESTAMP + " INTEGER, " +
                    COLUMN_CREATE_TIMESTAMP + " INTEGER, " +
                    COLUMN_BYTES_TOTAL + " INTEGER, " +
                    COLUMN_TYPE + " INTEGER, " +
                    COLUMN_LOCAL_FILENAME + " TEXT, " +
                    COLUMN_LOCAL_URI + " TEXT, " +
                    COLUMN_URI + " TEXT, " +
                    COLUMN_EXTRA1 + " TEXT, " +
                    COLUMN_EXTRA2 + " TEXT, " +
                    COLUMN_STATUS + " INTEGER); ");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        DownloadTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        DownloadTable.onUpgrade(sqLiteDatabase, i, i2);
    }
}
