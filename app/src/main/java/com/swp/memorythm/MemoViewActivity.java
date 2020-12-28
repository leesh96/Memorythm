package com.swp.memorythm;

import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Switch;

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
    private String Mode;
    private boolean isMemoFixed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoview);

        btnBack = findViewById(R.id.btn_back);
        btnSelcolor = findViewById(R.id.btn_selColor);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
        checkBoxFixed = findViewById(R.id.checkbox_fixed);
        template_frame = findViewById(R.id.template_frame);

        // 뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        // 메모지 템플릿 종류 받아오기
        Intent intent = getIntent();
        TemplateCase = intent.getStringExtra("template");
        // 작성모드 or 보기모드
        Mode = intent.getStringExtra("mode");

        // 템플릿 별로 프래그먼트 다르게 띄우기
        switch (TemplateCase) {
            case "nonlinememo":
                ft.replace(R.id.template_frame, new NonlineMemoFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "linememo":
                ft.replace(R.id.template_frame, new LineMemoFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "todolist":
                ft.replace(R.id.template_frame, new TodoFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "gridmemo":
                ft.replace(R.id.template_frame, new GridMemoFragment());
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
            case "review":
                ft.replace(R.id.template_frame, new ReviewFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "healthtracker":
                ft.replace(R.id.template_frame, new HealthTrackerFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "studytracker":
                ft.replace(R.id.template_frame, new StudyTrackerFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "monthlytracker":
                ft.replace(R.id.template_frame, new MonthTrackerFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "dailyplan":
                ft.replace(R.id.template_frame, new DailyPlanFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "weeklyplan":
                ft.replace(R.id.template_frame, new WeeklyPlanFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "monthlyplan":
                ft.replace(R.id.template_frame, new MonthlyPlanFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "yearlyplan":
                ft.replace(R.id.template_frame, new YearlyPlanFragment());
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
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.template_frame);      // 현재 보여지는 프래그먼트 가져오기
                // 프래그먼트 구분
                // 각 프래그먼트마다 파이어베이스에 저장하는 함수 만들어놓고 아래 처럼 호출
                if (fragment instanceof NonlineMemoFragment) {
                    ((NonlineMemoFragment) fragment).save();
                }
                switch (Mode) {
                    case "writemode":
                        break;
                    case "viewmode":
                        break;
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.template_frame);      // 현재 보여지는 프래그먼트 가져오기

                AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                alert.setMessage("이 메모를 삭제하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        // 서버에 메모 삭제 여부 on으로 표시 (휴지통으로 옮겨짐) 프래그먼트에서 removeitem 다이얼로그 끄고 액티비티 종료
                        dialog.dismiss();
                        finish();
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
    }
}
