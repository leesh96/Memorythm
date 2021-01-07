package com.swp.memorythm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

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

        setCurrentDate();

        // 로딩화면 3초 전환
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                intent = new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setCurrentDate() {
        // 앱 실행할때마다 날짜 가져오기
        // 캘린더 객체 생성
        Calendar myCalendar = Calendar.getInstance();

        String DateFormat = "yyyy - MM - dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat, Locale.KOREA);
        String CurrentDate = simpleDateFormat.format(myCalendar.getTime());

        if (!PreferenceManager.getString(LoadingActivity.this, "currentDate").equals(CurrentDate)) {
            PreferenceManager.setString(LoadingActivity.this, "currentDate", CurrentDate);
        }
    }
}
