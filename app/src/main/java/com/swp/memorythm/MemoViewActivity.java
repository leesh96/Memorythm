package com.swp.memorythm;

import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

    private String TemplateCase, Mode, MemoBackground, MemoTitle;
    private int isMemofixed, memoid;

    private boolean isAfterWrite = false;

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoview);

        dbHelper = new DBHelper(MemoViewActivity.this);
        db = dbHelper.getReadableDatabase();
        Intent intent = getIntent();

        btnBack = findViewById(R.id.btn_back);
        btnSelcolor = findViewById(R.id.btn_selColor);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
        checkBoxFixed = findViewById(R.id.checkbox_fixed);
        template_frame = findViewById(R.id.template_frame);

        initMemoActivity(intent);

        // 뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAfterWrite) {
                    Intent newMain = new Intent(MemoViewActivity.this, MainActivity.class);
                    startActivity(newMain);
                    finish();
                } else finish();
            }
        });

        // 메모지 색상 변경
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
                        switch (Mode) {
                            case "write":
                                MemoBackground = "yellow";
                                setMemoBackground(MemoBackground);
                                break;
                            case "view":
                                MemoBackground = "yellow";
                                setMemoBackground(MemoBackground);
                                try {
                                    db.execSQL("UPDATE "+TemplateCase+" SET bgcolor = '"+MemoBackground+"' WHERE id = "+memoid+";");
                                    Toast.makeText(MemoViewActivity.this, "배경색 변경 성공", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(MemoViewActivity.this, "배경색 변경 실패", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                        alertDialog.dismiss();
                    }
                });
                btnPink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (Mode) {
                            case "write":
                                MemoBackground = "pink";
                                setMemoBackground(MemoBackground);
                                break;
                            case "view":
                                MemoBackground = "pink";
                                setMemoBackground(MemoBackground);
                                try {
                                    db.execSQL("UPDATE "+TemplateCase+" SET bgcolor = '"+MemoBackground+"' WHERE id = "+memoid+";");
                                    Toast.makeText(MemoViewActivity.this, "배경색 변경 성공", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(MemoViewActivity.this, "배경색 변경 실패", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                        alertDialog.dismiss();
                    }
                });
                btnMint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (Mode) {
                            case "write":
                                MemoBackground = "mint";
                                setMemoBackground(MemoBackground);
                                break;
                            case "view":
                                MemoBackground = "mint";
                                setMemoBackground(MemoBackground);
                                try {
                                    db.execSQL("UPDATE "+TemplateCase+" SET bgcolor = '"+MemoBackground+"' WHERE id = "+memoid+";");
                                    Toast.makeText(MemoViewActivity.this, "배경색 변경 성공", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(MemoViewActivity.this, "배경색 변경 실패", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                        alertDialog.dismiss();
                    }
                });
                btnSky.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (Mode) {
                            case "write":
                                MemoBackground = "sky";
                                setMemoBackground(MemoBackground);
                                break;
                            case "view":
                                MemoBackground = "sky";
                                setMemoBackground(MemoBackground);
                                try {
                                    db.execSQL("UPDATE "+TemplateCase+" SET bgcolor = '"+MemoBackground+"' WHERE id = "+memoid+";");
                                    Toast.makeText(MemoViewActivity.this, "배경색 변경 성공", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(MemoViewActivity.this, "배경색 변경 실패", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                        alertDialog.dismiss();
                    }
                });
                btnGray.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (Mode) {
                            case "write":
                                MemoBackground = "gray";
                                setMemoBackground(MemoBackground);
                                break;
                            case "view":
                                MemoBackground = "gray";
                                setMemoBackground(MemoBackground);
                                try {
                                    db.execSQL("UPDATE "+TemplateCase+" SET bgcolor = '"+MemoBackground+"' WHERE id = "+memoid+";");
                                    Toast.makeText(MemoViewActivity.this, "배경색 변경 성공", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(MemoViewActivity.this, "배경색 변경 실패", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                        alertDialog.dismiss();
                    }
                });
            }
        });

        // 메모 고정상태 변경
        checkBoxFixed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    try {
                        db.execSQL("UPDATE "+TemplateCase+" SET fixed = 1 WHERE id = "+memoid+";");
                        Toast.makeText(MemoViewActivity.this, "고정 변경 성공", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MemoViewActivity.this, "고정 변경 실패", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        db.execSQL("UPDATE "+TemplateCase+" SET fixed = 0 WHERE id = "+memoid+";");
                        Toast.makeText(MemoViewActivity.this, "고정 변경 성공", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MemoViewActivity.this, "고정 변경 실패", Toast.LENGTH_SHORT).show();
                    }
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

                AlertDialog.Builder builder = new AlertDialog.Builder(MemoViewActivity.this);
                AlertDialog.Builder savealert = new AlertDialog.Builder(MemoViewActivity.this);

                View dialogView = LayoutInflater.from(MemoViewActivity.this).inflate(R.layout.dialog_title, null, false);
                builder.setView(dialogView);

                EditText editTextMemoTitle;
                Button btnApply, btnCancel;

                editTextMemoTitle = dialogView.findViewById(R.id.et_memotitle);
                btnApply = dialogView.findViewById(R.id.btn_apply);
                btnCancel = dialogView.findViewById(R.id.btn_cancel);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                if (Mode.equals("view")) editTextMemoTitle.setText(MemoTitle);

                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MemoTitle = editTextMemoTitle.getText().toString();
                        if (MemoTitle.equals("") | MemoTitle == null) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                            alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                        } else {
                            alertDialog.dismiss();
                            savealert.show();
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                // 이게 뭔진 잘 모르겠는데 이걸 써야한대
                AtomicBoolean success = new AtomicBoolean(false);

                savealert.setMessage("메모를 저장하시겠습니까?");
                savealert.setPositiveButton("확인", (dialog, i) -> {
                    try {
                        // 프래그먼트 구분
                        if(fragment instanceof NonlineMemoFragment) success.set(((NonlineMemoFragment) fragment).saveData(Mode, MemoBackground, MemoTitle));
                        else if(fragment instanceof TodoFragment) success.set(((TodoFragment) fragment).saveData(Mode, MemoBackground, MemoTitle));
                        else if(fragment instanceof ShoppingFragment) success.set(((ShoppingFragment) fragment).saveData(Mode, MemoBackground, MemoTitle));
                        else if(fragment instanceof GridMemoFragment) success.set(((GridMemoFragment) fragment).saveData(Mode, MemoBackground, MemoTitle));
                        else if(fragment instanceof HealthTrackerFragment) success.set(((HealthTrackerFragment) fragment).saveData(Mode, MemoBackground, MemoTitle));
                        else if(fragment instanceof MonthTrackerFragment) success.set(((MonthTrackerFragment) fragment).saveData(Mode, MemoBackground, MemoTitle));
                        else if(fragment instanceof StudyTrackerFragment) success.set(((StudyTrackerFragment) fragment).saveData(Mode, MemoBackground, MemoTitle));
                        else if(fragment instanceof ReviewFragment) success.set(((ReviewFragment) fragment).saveData(Mode, MemoBackground, MemoTitle));
                        if (success.get()) {
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                if(fragment instanceof NonlineMemoFragment) memoid = ((NonlineMemoFragment) fragment).getMemoid();
                                else if(fragment instanceof GridMemoFragment) memoid =  ((GridMemoFragment) fragment).getMemoid();
                                else if(fragment instanceof HealthTrackerFragment) memoid = ((HealthTrackerFragment) fragment).getMemoid();
                                else if(fragment instanceof MonthTrackerFragment) memoid = ((MonthTrackerFragment) fragment).getMemoid();
                                else if(fragment instanceof StudyTrackerFragment) memoid = ((StudyTrackerFragment) fragment).getMemoid();
                                else if(fragment instanceof ReviewFragment) memoid = ((ReviewFragment) fragment).getMemoid();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                });
                savealert.setNegativeButton("취소", (dialog, i) -> dialog.dismiss());
            }
        });

        // 메모 휴지통으로 보내기
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                alert.setMessage("메모를 삭제하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        db.execSQL("UPDATE "+TemplateCase+" SET deleted = 1 WHERE id = "+memoid+";");
                        dialog.dismiss();
                        if (isAfterWrite) {
                            Intent newMain = new Intent(MemoViewActivity.this, MainActivity.class);
                            startActivity(newMain);
                            finish();
                        } else finish();
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
    private void initMemoActivity(Intent intent) {
        // 모드 받아오기
        Mode = intent.getStringExtra("mode");
        setVisibility(Mode);

        // 배경색, 고정여부 설정
        switch (Mode) {
            case "write":
                MemoBackground = "yellow";
                setMemoBackground(MemoBackground);
                TemplateCase = intent.getStringExtra("template");
                initFragment(TemplateCase);
                break;
            case "view":
                TemplateCase = intent.getStringExtra("template");
                memoid = intent.getIntExtra("memoid", 0);
                MemoTitle = intent.getStringExtra("memotitle");
                Cursor cursor = db.rawQuery("SELECT bgcolor, fixed FROM "+TemplateCase+" WHERE id = "+memoid+"", null);
                cursor.moveToFirst();
                MemoBackground = cursor.getString(0);
                isMemofixed = cursor.getInt(1);
                setMemoBackground(MemoBackground);
                if (isMemofixed == 1) {
                    checkBoxFixed.setChecked(true);
                } else checkBoxFixed.setChecked(false);
                viewMemo(TemplateCase, memoid);
                break;
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
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (Template) {
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

    // 메모 보여주기
    private void viewMemo(String Template, int id) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        Bundle bundle;
        switch (Template) {
            case "nonlinememo":
                NonlineMemoFragment nonlineMemoFragment = new NonlineMemoFragment();
                bundle = new Bundle();
                bundle.putInt("memoid", id);
                nonlineMemoFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, nonlineMemoFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "gridmemo":
                GridMemoFragment gridMemoFragment = new GridMemoFragment();
                bundle = new Bundle();
                bundle.putInt("memoid", id);
                gridMemoFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, gridMemoFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "review":
                ReviewFragment reviewFragment = new ReviewFragment();
                bundle = new Bundle();
                bundle.putInt("memoid", id);
                reviewFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, reviewFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "healthtracker":
                HealthTrackerFragment healthTrackerFragment = new HealthTrackerFragment();
                bundle = new Bundle();
                bundle.putInt("memoid", id);
                healthTrackerFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, healthTrackerFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "studytracker":
                StudyTrackerFragment studyTrackerFragment = new StudyTrackerFragment();
                bundle = new Bundle();
                bundle.putInt("memoid", id);
                studyTrackerFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, studyTrackerFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "monthlytracker":
                MonthTrackerFragment monthTrackerFragment = new MonthTrackerFragment();
                bundle = new Bundle();
                bundle.putInt("memoid", id);
                monthTrackerFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, monthTrackerFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "linememo":
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
