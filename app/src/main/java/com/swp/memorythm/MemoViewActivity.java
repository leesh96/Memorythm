package com.swp.memorythm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MemoViewActivity extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft;

    private ImageButton btnBack, btnSelcolor, btnSave, btnDelete;
    private CheckBox checkBoxFixed;
    private FrameLayout template_frame;

    private String TemplateCase;
    private boolean isMemoFixed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoview);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        btnBack = findViewById(R.id.btn_back);
        btnSelcolor = findViewById(R.id.btn_selColor);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
        checkBoxFixed = findViewById(R.id.checkbox_fixed);
        template_frame = findViewById(R.id.template_frame);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 메모지 템플릿 종류 intent로 넘겨서 받아오기
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

        btnSelcolor.setBackgroundResource(R.drawable.ic_bgyellow);
        template_frame.setBackgroundResource(R.drawable.template_style_bgyellow);

        // 색상변경 다이얼로그
        btnSelcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MemoViewActivity.this);

                View dialogView = LayoutInflater.from(MemoViewActivity.this).inflate(R.layout.dialog_colorselect, null, false);
                builder.setView(dialogView);

                ImageButton btnPink, btnYellow, btnMint, btnSky, btnGray;

                btnYellow = dialogView.findViewById(R.id.btn_bgyellow);
                btnPink = dialogView.findViewById(R.id.btn_bgpink);
                btnMint = dialogView.findViewById(R.id.btn_bgmint);
                btnSky = dialogView.findViewById(R.id.btn_bgsky);
                btnGray = dialogView.findViewById(R.id.btn_bggray);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnYellow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnSelcolor.setBackgroundResource(R.drawable.ic_bgyellow);
                        template_frame.setBackgroundResource(R.drawable.template_style_bgyellow);

                        alertDialog.dismiss();
                    }
                });

                btnPink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnSelcolor.setBackgroundResource(R.drawable.ic_bgpink);
                        template_frame.setBackgroundResource(R.drawable.template_style_bgpink);

                        alertDialog.dismiss();
                    }
                });

                btnMint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnSelcolor.setBackgroundResource(R.drawable.ic_bgmint);
                        template_frame.setBackgroundResource(R.drawable.template_style_bgmint);

                        alertDialog.dismiss();
                    }
                });

                btnSky.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnSelcolor.setBackgroundResource(R.drawable.ic_bgsky);
                        template_frame.setBackgroundResource(R.drawable.template_style_bgsky);

                        alertDialog.dismiss();
                    }
                });

                btnGray.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnSelcolor.setBackgroundResource(R.drawable.ic_bggray);
                        template_frame.setBackgroundResource(R.drawable.template_style_bggray);

                        alertDialog.dismiss();
                    }
                });
            }
        });

        // 메모지 고정 여부 DB에서 받아오기
        isMemoFixed = false;
        checkBoxFixed.setChecked(isMemoFixed);
        checkBoxFixed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isMemoFixed = b;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2020-11-23 메모지 저장 방법
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2020-12-27 메모지 삭제 방법
            }
        });
    }
}
