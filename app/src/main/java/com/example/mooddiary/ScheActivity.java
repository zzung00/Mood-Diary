package com.example.mooddiary;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ScheActivity extends Activity {
    TextView textview;
    EditText editText;
    Button btnAdd;
    String date;
    ArrayList<Todo> items = new ArrayList<>();
    ScheViewAdapter adapter;
    TodoHelper todoHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sche);
        todoHelper = new TodoHelper(getApplicationContext());

        date = getIntent().getStringExtra("date");
        textview = (TextView) findViewById(R.id.textview);
        textview.setText(date);
        editText = (EditText) findViewById(R.id.editText);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        SQLiteDatabase db = todoHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                TodoContract.TodoEntry.COLUMN_NAME_CONTENT,
                TodoContract.TodoEntry.COLUMN_NAME_DONE,
                TodoContract.TodoEntry.COLUMN_NAME_DATE
        };
        String selection = TodoContract.TodoEntry.COLUMN_NAME_DATE + " = ?";
        String[] selectionArgs = {date};
        String sortOrder = TodoContract.TodoEntry._ID + " ASC";

        Cursor cursor = db.query(
                TodoContract.TodoEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(TodoContract.TodoEntry._ID));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_CONTENT));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_DATE));
            boolean done = cursor.getInt(cursor.getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_NAME_DONE))==1;
            Todo todo = new Todo(itemId, content, done, date);
            items.add(todo);
            System.out.println(itemId + ", " + done);
        }

        adapter = new ScheViewAdapter(items, todoHelper);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().isEmpty()) {
                    SQLiteDatabase db = todoHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(TodoContract.TodoEntry.COLUMN_NAME_CONTENT, editText.getText().toString());
                    values.put(TodoContract.TodoEntry.COLUMN_NAME_DONE, 0);
                    values.put(TodoContract.TodoEntry.COLUMN_NAME_DATE, date);
                    long newRowId = db.insert(TodoContract.TodoEntry.TABLE_NAME, null, values);
                    Todo todo = new Todo(newRowId, editText.getText().toString(), false, date);
                    items.add(todo);
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        todoHelper.close();
        super.onDestroy();
    }
}
