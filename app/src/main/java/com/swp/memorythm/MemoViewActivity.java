package com.swp.memorythm;

import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.concurrent.atomic.AtomicBoolean;

public class MemoViewActivity extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft;

    private ImageButton btnBack, btnSelcolor, btnSave, btnDelete;
    private CheckBox checkBoxFixed;
    private FrameLayout template_frame;

    private String TemplateCase, Mode, MemoBackground;
    private int isMemoFixed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoview);

        Intent intent = getIntent();

        btnBack = findViewById(R.id.btn_back);
        btnSelcolor = findViewById(R.id.btn_selColor);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
        checkBoxFixed = findViewById(R.id.checkbox_fixed);
        template_frame = findViewById(R.id.template_frame);

        initMemoView(intent);

        // 뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
                        MemoBackground = "yellow";
                        setMemoBackground(MemoBackground);
                        alertDialog.dismiss();
                    }
                });

                btnPink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MemoBackground = "pink";
                        setMemoBackground(MemoBackground);
                        alertDialog.dismiss();
                    }
                });

                btnMint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MemoBackground = "mint";
                        setMemoBackground(MemoBackground);
                        alertDialog.dismiss();
                    }
                });

                btnSky.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MemoBackground = "sky";
                        setMemoBackground(MemoBackground);
                        alertDialog.dismiss();
                    }
                });

                btnGray.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MemoBackground = "gray";
                        setMemoBackground(MemoBackground);
                        alertDialog.dismiss();
                    }
                });
            }
        });

        // 메모 고정여부 변경
        checkBoxFixed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isMemoFixed = 1;
                } else {
                    isMemoFixed = 0;
                }
            }
        });

        // 메모 저장
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.template_frame);      // 현재 보여지는 프래그먼트 가져오기

                // 포커스 삭제 및 키보드 내리기
                InputMethodManager manager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                if(getCurrentFocus()!=null){
                    manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    getCurrentFocus().clearFocus();
                }

                // 이게 뭔진 잘 모르겠는데 이걸 써야한대
                AtomicBoolean success = new AtomicBoolean(false);

                AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                alert.setMessage("메모를 저장하시겠습니까?");
                alert.setPositiveButton("확인", (dialog, i) -> {
                    try {
                        // 프래그먼트 구분
                        if(fragment instanceof NonlineMemoFragment) success.set(((NonlineMemoFragment) fragment).saveData(Mode, MemoBackground, isMemoFixed));
                        else if(fragment instanceof TodoFragment) success.set(((TodoFragment) fragment).saveData(Mode, MemoBackground));
                        else if(fragment instanceof ShoppingFragment) success.set(((ShoppingFragment) fragment).saveData(Mode, MemoBackground));
                        else if(fragment instanceof HealthTrackerFragment) ((GridMemoFragment) fragment).saveData(Mode);
                        else if(fragment instanceof MonthTrackerFragment) ((MonthTrackerFragment) fragment).saveData(Mode);
                        else if(fragment instanceof StudyTrackerFragment) ((StudyTrackerFragment) fragment).saveData(Mode);
                        else if(fragment instanceof ReviewFragment) ((ReviewFragment) fragment).saveData(Mode);
                        if (success.get()) {
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // 뷰 모드로 변경
                            Mode = "view";
                            setVisibility(Mode);
                        }
                    } catch (Exception e) {
                        Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                });
                alert.setNegativeButton("취소", (dialog, i) -> dialog.dismiss());
                alert.show();
            }
        });

        // 메모 휴지통으로 보내기
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.template_frame);      // 현재 보여지는 프래그먼트 가져오기

                AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                alert.setMessage("메모를 삭제하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
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

    //휴대폰 버튼으로 뒤로 가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    // initActivity
    private void initMemoView(Intent intent) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        // 모드 받아오기
        Mode = intent.getStringExtra("mode");
        setVisibility(Mode);

        // 배경색, 고정여부 설정
        switch (Mode) {
            case "write":
                TemplateCase = intent.getStringExtra("template");
                initFragment(TemplateCase);
                MemoBackground = "yellow";
                setMemoBackground(MemoBackground);
                isMemoFixed = 0;
                break;
            case "view":
                MemoBackground = intent.getStringExtra("bgcolor");
                isMemoFixed = intent.getIntExtra("memofixed", 0);
                setMemoBackground(MemoBackground);
                switch (isMemoFixed) {
                    case 0:
                        checkBoxFixed.setChecked(false);
                        break;
                    case 1:
                        checkBoxFixed.setChecked(true);
                        break;
                }
        }
    }

    // 모드에 따른 뷰 가시성 세팅
    private void setVisibility(String Mode) {
        switch (Mode) {
            case "write":
                btnDelete.setVisibility(View.GONE);
                checkBoxFixed.setVisibility(View.GONE);
                break;
            case "view":
                btnDelete.setVisibility(View.VISIBLE);
                checkBoxFixed.setVisibility(View.VISIBLE);
                break;
        }
    }

    // 탬플릿 별로 프래그먼트 생성
    private void initFragment(String Template) {
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
    }

    // 메모지 배경색 변경
    private void setMemoBackground(String BackgroundColor) {
        switch (BackgroundColor) {
            case "yellow":
                btnSelcolor.setBackgroundResource(R.drawable.ic_bgyellow);
                template_frame.setBackgroundResource(R.drawable.template_style_bgyellow);
                break;
            case "pink":
                btnSelcolor.setBackgroundResource(R.drawable.ic_bgpink);
                template_frame.setBackgroundResource(R.drawable.template_style_bgpink);
                break;
            case "mint":
                btnSelcolor.setBackgroundResource(R.drawable.ic_bgmint);
                template_frame.setBackgroundResource(R.drawable.template_style_bgmint);
                break;
            case "sky":
                btnSelcolor.setBackgroundResource(R.drawable.ic_bgsky);
                template_frame.setBackgroundResource(R.drawable.template_style_bgsky);
                break;
            case "gray":
                btnSelcolor.setBackgroundResource(R.drawable.ic_bggray);
                template_frame.setBackgroundResource(R.drawable.template_style_bggray);
                break;
        }
    }
}
