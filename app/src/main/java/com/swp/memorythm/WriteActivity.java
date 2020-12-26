package com.swp.memorythm;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class WriteActivity extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft;

    private ImageButton btnBack, btnSave, btnSelcolor;
    private FrameLayout template_frame;

    private String TemplateCase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        btnBack = findViewById(R.id.btn_back);
        btnSave = findViewById(R.id.btn_save);
        btnSelcolor = findViewById(R.id.btn_selColor);
        template_frame = findViewById(R.id.template_frame);

        // 템플릿 종류 intent로 넘겨서 받아오기
        TemplateCase = "shoppinglist";

        switch (TemplateCase) {
            // TODO: 2020-11-20 템플릿 케이스 별로 프래그먼트 다르게 띄우기
            case "nonlinememo":
                ft.replace(R.id.template_frame, new NonlineMemoFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "todolist":
                ft.replace(R.id.template_frame, new TodoFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "wishlist":
                ft.replace(R.id.template_frame, new WishFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "shoppinglist":
                ft.replace(R.id.template_frame, new ShoppingFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
        }

        GradientDrawable gradientDrawable = (GradientDrawable) ContextCompat.getDrawable(getBaseContext(), R.drawable.template_border_style);
        gradientDrawable.setColor(ContextCompat.getColor(getBaseContext(), R.color.template_bgyellow));
        btnSelcolor.setBackgroundColor(this.getResources().getColor(R.color.template_bgyellow));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2020-11-23 템플릿 저장 방법
            }
        });

        // 색상변경 다이얼로그
        btnSelcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);

                View dialogView = LayoutInflater.from(WriteActivity.this).inflate(R.layout.dialog_colorselect, null, false);
                builder.setView(dialogView);

                ImageButton btnPink, btnYellow, btnMint, btnSky, btnGray;

                btnPink = dialogView.findViewById(R.id.btn_bgpink);
                btnYellow = dialogView.findViewById(R.id.btn_bgyellow);
                btnMint = dialogView.findViewById(R.id.btn_bgmint);
                btnSky = dialogView.findViewById(R.id.btn_bgsky);
                btnGray = dialogView.findViewById(R.id.btn_bggray);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnPink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gradientDrawable.setColor(ContextCompat.getColor(getBaseContext(), R.color.template_bgpink));
                        template_frame.setBackground(gradientDrawable);
                        btnSelcolor.setBackgroundColor(WriteActivity.this.getResources().getColor(R.color.template_bgpink));
                        alertDialog.dismiss();
                    }
                });
                btnYellow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gradientDrawable.setColor(ContextCompat.getColor(getBaseContext(), R.color.template_bgyellow));
                        template_frame.setBackground(gradientDrawable);
                        btnSelcolor.setBackgroundColor(WriteActivity.this.getResources().getColor(R.color.template_bgyellow));
                        alertDialog.dismiss();
                    }
                });
                btnMint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gradientDrawable.setColor(ContextCompat.getColor(getBaseContext(), R.color.template_bgmint));
                        template_frame.setBackground(gradientDrawable);
                        btnSelcolor.setBackgroundColor(WriteActivity.this.getResources().getColor(R.color.template_bgmint));
                        alertDialog.dismiss();
                    }
                });
                btnSky.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gradientDrawable.setColor(ContextCompat.getColor(getBaseContext(), R.color.template_bgsky));
                        template_frame.setBackground(gradientDrawable);
                        btnSelcolor.setBackgroundColor(WriteActivity.this.getResources().getColor(R.color.template_bgsky));
                        alertDialog.dismiss();
                    }
                });
                btnGray.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gradientDrawable.setColor(ContextCompat.getColor(getBaseContext(), R.color.template_bggray));
                        template_frame.setBackground(gradientDrawable);
                        btnSelcolor.setBackgroundColor(WriteActivity.this.getResources().getColor(R.color.template_bggray));
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }
}
