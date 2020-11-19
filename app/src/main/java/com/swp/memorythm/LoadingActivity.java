package com.swp.memorythm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) { // Handler 클래스
                super.handleMessage(msg);
                intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        mHandler.sendEmptyMessageDelayed(0, 3000); // 로딩화면 딜레이 3초
    }
}
