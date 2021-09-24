package com.example.mooddiary;

import android.provider.BaseColumns;

public class DiaryContract {
    private DiaryContract() {}

    public static class DiaryEntry implements BaseColumns {
        public static final String TABLE_NAME = "diary";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_MOOD = "mood";
        public static final String COLUMN_NAME_DATE = "date";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DiaryContract.DiaryEntry.TABLE_NAME + " (" +
                    DiaryContract.DiaryEntry._ID + " INTEGER PRIMARY KEY," +
                    DiaryContract.DiaryEntry.COLUMN_NAME_CONTENT + " TEXT," +
                    DiaryContract.DiaryEntry.COLUMN_NAME_MOOD + " INTEGER," +
                    DiaryContract.DiaryEntry.COLUMN_NAME_DATE + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DiaryContract.DiaryEntry.TABLE_NAME;
}
