package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LoadingActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // 앱 실행할때마다 날짜 가져오기
        // 캘린더 객체 생성
        Calendar myCalendar = Calendar.getInstance();

        String DateFormat = "yyyy - MM - dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat, Locale.KOREA);
        String CurrentDate = simpleDateFormat.format(myCalendar.getTime());

        if (!PreferenceManager.getString(LoadingActivity.this, "currentDate").equals(CurrentDate)) {
            PreferenceManager.setString(LoadingActivity.this, "currentDate", CurrentDate);
        }

        // 로딩화면 3초
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }
}
