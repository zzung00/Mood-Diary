package com.example.mooddiary;

import android.provider.BaseColumns;

public class TodoContract {
    private TodoContract() {}

    public static class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DONE = "done";
        public static final String COLUMN_NAME_DATE = "date";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TodoEntry.TABLE_NAME + " (" +
             TodoEntry._ID + " INTEGER PRIMARY KEY," +
             TodoEntry.COLUMN_NAME_CONTENT + " TEXT," +
             TodoEntry.COLUMN_NAME_DONE + " INTEGER," +
             TodoEntry.COLUMN_NAME_DATE + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TodoEntry.TABLE_NAME;
}
