package com.example.mooddiary;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class DiaryActivity extends Activity implements AdapterView.OnItemSelectedListener {
    TextView txtTitle;
    EditText txtWriting;
    String date;
    private Spinner moodSpinner;
    int mood[] = {R.drawable.emo1, R.drawable.emo2, R.drawable.emo3, R.drawable.emo4, R.drawable.emo5, R.drawable.emo6};
    DiaryHelper diaryHelper;
    boolean isNew = true;
    Diary diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        diaryHelper = new DiaryHelper(getApplicationContext());

        date = getIntent().getStringExtra("date");
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(date);
        txtWriting = (EditText) findViewById(R.id.txtWriting);
        moodSpinner = (Spinner) findViewById(R.id.spinner);
        moodSpinner.setOnItemSelectedListener(this);
        MoodAdapter moodAdapter = new MoodAdapter(getApplicationContext(), mood);
        moodSpinner.setAdapter(moodAdapter);

        SQLiteDatabase db = diaryHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                DiaryContract.DiaryEntry.COLUMN_NAME_CONTENT,
                DiaryContract.DiaryEntry.COLUMN_NAME_MOOD,
                DiaryContract.DiaryEntry.COLUMN_NAME_DATE
        };
        String selection = DiaryContract.DiaryEntry.COLUMN_NAME_DATE + " = ?";
        String[] selectionArgs = {date};
        String sortOrder = DiaryContract.DiaryEntry._ID + " ASC";

        Cursor cursor = db.query(
                DiaryContract.DiaryEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()) {
            isNew = false;
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(DiaryContract.DiaryEntry._ID));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(DiaryContract.DiaryEntry.COLUMN_NAME_CONTENT));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DiaryContract.DiaryEntry.COLUMN_NAME_DATE));
            int mood = cursor.getInt(cursor.getColumnIndexOrThrow(DiaryContract.DiaryEntry.COLUMN_NAME_MOOD));
            diary = new Diary(itemId, content, mood, date);
            txtWriting.setText(content);
            moodSpinner.setSelection(mood);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onDestroy() {
        if (isNew) {
            SQLiteDatabase db = diaryHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DiaryContract.DiaryEntry.COLUMN_NAME_CONTENT, txtWriting.getText().toString());
            values.put(DiaryContract.DiaryEntry.COLUMN_NAME_DATE, date);
            values.put(DiaryContract.DiaryEntry.COLUMN_NAME_MOOD, moodSpinner.getSelectedItemPosition());
            long newRowId = db.insert(DiaryContract.DiaryEntry.TABLE_NAME, null, values);
        }
        else {
            SQLiteDatabase db = diaryHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DiaryContract.DiaryEntry.COLUMN_NAME_CONTENT, txtWriting.getText().toString());
            values.put(DiaryContract.DiaryEntry.COLUMN_NAME_MOOD, moodSpinner.getSelectedItemPosition());
            String selection = DiaryContract.DiaryEntry._ID + " = ?";
            String[] selectionArgs = { String.valueOf(diary.getId())};

            int count = db.update(
                    DiaryContract.DiaryEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
        }
        diaryHelper.close();
        super.onDestroy();
    }
}
