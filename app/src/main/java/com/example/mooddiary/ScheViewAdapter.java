package com.example.mooddiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScheViewAdapter extends BaseAdapter {
    private ArrayList<Todo> items;
    private TodoHelper todoHelper;

    public ScheViewAdapter(ArrayList<Todo> items, TodoHelper todoHelper) {
        super();
        this.items = items;
        this.todoHelper = todoHelper;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_list, parent, false);
        }

        TextView scheTextView = (TextView) convertView.findViewById(R.id.scheContent);
        Button btnDel = (Button) convertView.findViewById(R.id.btnDel);

        btnDel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = todoHelper.getWritableDatabase();
                String selection = TodoContract.TodoEntry._ID + " = ?";
                String[] selectionArgs = {String.valueOf(items.get(position).getId())};
                int deleteRows = db.delete(TodoContract.TodoEntry.TABLE_NAME, selection, selectionArgs);
                items.remove(position);
                notifyDataSetChanged();
            }
        });

        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
        checkBox.setChecked(items.get(position).getDone());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SQLiteDatabase db = todoHelper.getWritableDatabase();
                int done = isChecked ? 1:0;
                items.get(position).setDone(isChecked);
                ContentValues values = new ContentValues();
                values.put(TodoContract.TodoEntry.COLUMN_NAME_DONE, done);
                String selection = TodoContract.TodoEntry._ID + " = ?";
                String[] selectionArgs = {String.valueOf(items.get(position).getId())};

                int count = db.update(
                        TodoContract.TodoEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
            }
        });
        scheTextView.setText(items.get(position).getContent());
        return convertView;
    }
}
