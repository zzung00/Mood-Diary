package com.example.mooddiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;

public class CalActivity extends Activity {
    CalendarView calendar;
    Button btnDiary1, btnDiary2;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);

        calendar = (CalendarView) findViewById(R.id.calendar);
        btnDiary1 = (Button) findViewById(R.id.btnDiary1);
        btnDiary1.setEnabled(false);
        btnDiary2 = (Button) findViewById(R.id.btnDiary2);
        btnDiary2.setEnabled(false);

        btnDiary1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DiaryActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        btnDiary2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScheActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                btnDiary1.setEnabled(true);
                btnDiary2.setEnabled(true);
                date = year + "년 " + (month+1) + "월 " + dayOfMonth + "일";
            }
        });
    }
}