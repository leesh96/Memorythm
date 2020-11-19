package com.swp.memorythm;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class WriteActivity extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft;

    private ImageButton btn_back, btn_save, btn_selcolor;
    private FrameLayout template_frame;

    private int templateCase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        btn_selcolor = findViewById(R.id.btn_selColor);
        template_frame = findViewById(R.id.template_frame);

        final GradientDrawable gradientDrawable = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.template_border_style);
        gradientDrawable.setColor(ContextCompat.getColor(getBaseContext(), R.color.template_background));
        template_frame.setBackground(gradientDrawable);

        // TODO: 2020-11-20 어떤 템플릿 선택했는지 받아올 방법
        templateCase = 0;

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2020-11-20 템플릿 저장 방법
            }
        });

        btn_selcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2020-11-20 template_frame 색상 변경 다이얼로그
                gradientDrawable.setColor(ContextCompat.getColor(getBaseContext(), R.color.template_background));
                template_frame.setBackground(gradientDrawable);
            }
        });

        switch (templateCase) {
            // TODO: 2020-11-20 템플릿 케이스 별로 프래그먼트 다르게 띄우기
            case 0:
                ft.replace(R.id.template_frame, new NonlineMemoFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
        }
    }
}
