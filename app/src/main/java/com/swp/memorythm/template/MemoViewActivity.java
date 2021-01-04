package com.swp.memorythm.template;

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

import com.swp.memorythm.DBHelper;
import com.swp.memorythm.MainActivity;
import com.swp.memorythm.PreferenceManager;
import com.swp.memorythm.R;

public class MemoViewActivity extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft;

    private ImageButton btnBack, btnSelcolor, btnSave, btnDelete;
    private CheckBox checkBoxFixed;
    private FrameLayout template_frame;

    private String TemplateCase, Mode, MemoBackground, MemoTitle;
    private int isMemofixed, memoid, fixedmemocnt;

    private boolean isAfterWrite = false;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

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
                fixedmemocnt = PreferenceManager.getInt(MemoViewActivity.this, "fixedmemocnt");
                if (b) {
                    try {
                        if (fixedmemocnt >= 3) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                            alert.setMessage("고정 메모는 3개를 넘을 수 없습니다!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = alert.create();
                            alertDialog.show();
                            checkBoxFixed.setChecked(false);
                        } else {
                            db.execSQL("UPDATE "+TemplateCase+" SET fixed = 1 WHERE id = "+memoid+";");
                            fixedmemocnt = fixedmemocnt + 1;
                            PreferenceManager.setInt(MemoViewActivity.this, "fixedmemocnt", fixedmemocnt);
                            Toast.makeText(MemoViewActivity.this, "고정 변경 성공", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MemoViewActivity.this, "고정 변경 실패", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        db.execSQL("UPDATE "+TemplateCase+" SET fixed = 0 WHERE id = "+memoid+";");
                        fixedmemocnt = fixedmemocnt - 1;
                        PreferenceManager.setInt(MemoViewActivity.this, "fixedmemocnt", fixedmemocnt);
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

                // 저장 할건지 확인하는 다이얼로그 만들기만 여기서 이 다이얼로그 안에서 saveData 호출
                AlertDialog.Builder savealert = new AlertDialog.Builder(MemoViewActivity.this);
                savealert.setMessage("메모를 저장하시겠습니까?");
                savealert.setPositiveButton("확인", (dialog, i) -> {
                    // 프래그먼트 구분
                    if (fragment instanceof NonlineMemoFragment) {
                        if (((NonlineMemoFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) { // 저장 성공 시
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((NonlineMemoFragment) fragment).getMemoid();
                            }
                            dialog.dismiss();
                        } else { // 저장 실패 시
                            Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                    else if (fragment instanceof TodoFragment) {
                        if (((TodoFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) {  // 저장 성공 시
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((TodoFragment) fragment).getMemoid();
                            }
                            dialog.dismiss();
                        } else {    // 저장 실패 시
                            Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                    else if (fragment instanceof ShoppingFragment) {
                        if (((ShoppingFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) {
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((ShoppingFragment) fragment).getMemoid();
                            }
                            dialog.dismiss();
                        } else {    // 저장 실패 시
                            Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                    else if (fragment instanceof WishFragment) {
                        if (((WishFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) {
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((WishFragment) fragment).getMemoid();
                            }
                            dialog.dismiss();
                        } else {    // 저장 실패 시
                            Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                    else if (fragment instanceof GridMemoFragment) {
                        if (((GridMemoFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) { // 저장 성공 시
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((GridMemoFragment) fragment).getMemoid();
                            }
                        } else Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if (fragment instanceof ReviewFragment) {
                        if (((ReviewFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) { // 저장 성공 시
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((ReviewFragment) fragment).getMemoid();
                            }
                        } else Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if (fragment instanceof MonthTrackerFragment) {
                        if (((MonthTrackerFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) { // 저장 성공 시
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((MonthTrackerFragment) fragment).getMemoid();
                            }
                        } else Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if(fragment instanceof StudyTrackerFragment) {
                        if (((StudyTrackerFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) { // 저장 성공 시
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((StudyTrackerFragment) fragment).getMemoid();
                            }
                        } else Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if (fragment instanceof HealthTrackerFragment) {
                        if (((HealthTrackerFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) { // 저장 성공 시
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((HealthTrackerFragment) fragment).getMemoid();
                            }
                        } else Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if (fragment instanceof LineMemoFragment) {
                        if (((LineMemoFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) { // 저장 성공 시
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((LineMemoFragment) fragment).getMemoid();
                            }
                        } else Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if (fragment instanceof DailyPlanFragment) {
                        if (((DailyPlanFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) { // 저장 성공 시
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((DailyPlanFragment) fragment).getMemoid();
                            }
                        } else
                            Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if (fragment instanceof WeeklyPlanFragment) {
                        if (((WeeklyPlanFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) { // 저장 성공 시
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((WeeklyPlanFragment) fragment).getMemoid();
                            }
                        } else
                            Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if (fragment instanceof MonthlyPlanFragment) {
                        if (((MonthlyPlanFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) { // 저장 성공 시
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((MonthlyPlanFragment) fragment).getMemoid();
                            }
                        } else
                            Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                    else if (fragment instanceof YearlyPlanFragment) {
                        if (((YearlyPlanFragment) fragment).saveData(Mode, MemoBackground, MemoTitle)) { // 저장 성공 시
                            Toast.makeText(MemoViewActivity.this, "저장 성공", Toast.LENGTH_SHORT).show();
                            // write 모드이면, memo id 받고 view 모드로 설정
                            if (Mode.equals("write")) {
                                Mode = "view";
                                isAfterWrite = true;
                                setVisibility(Mode);
                                memoid = ((YearlyPlanFragment) fragment).getMemoid();
                            }
                        } else
                            Toast.makeText(MemoViewActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                savealert.setNegativeButton("취소", (dialog, i) -> dialog.dismiss());

                AlertDialog.Builder builder = new AlertDialog.Builder(MemoViewActivity.this);
                View dialogView = LayoutInflater.from(MemoViewActivity.this).inflate(R.layout.dialog_title, null, false);
                builder.setView(dialogView);

                EditText editTextMemoTitle;
                Button btnApply, btnCancel;

                editTextMemoTitle = dialogView.findViewById(R.id.et_memotitle);
                btnApply = dialogView.findViewById(R.id.btn_apply);
                btnCancel = dialogView.findViewById(R.id.btn_cancel);

                if (Mode.equals("view")) editTextMemoTitle.setText(MemoTitle);

                // 널 값 검증을 제일 먼저 진행, 널 값 검증 필요 없으면 else에 있는 제목 받는거 바로 진행 ㄱㄱ
                if (fragment instanceof NonlineMemoFragment) {
                    if (!((NonlineMemoFragment) fragment).checkNull()) {    // 널 값 검증 통과 못함 -> 프래그먼트 참조
                        AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                        alert.setMessage("내용을 입력하세요!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                    } else {    // 널 값 검증 통과하면 제목 받음
                        AlertDialog alertDialog = builder.create();

                        btnApply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MemoTitle = editTextMemoTitle.getText().toString();
                                if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                                    AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                                    alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                                } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                                    alertDialog.dismiss();
                                    savealert.show();
                                }
                            }
                        });
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();
                    }
                }
                else if (fragment instanceof TodoFragment) {
                    if (!((TodoFragment) fragment).checkNull()) {   // 널 값 검증
                        AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                        alert.setMessage("할 일을 추가하세요!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                    } else {    // 널 값 검증 통과하면 제목 받음
                        AlertDialog alertDialog = builder.create();

                        btnApply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MemoTitle = editTextMemoTitle.getText().toString();
                                if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                                    AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                                    alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                                } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                                    alertDialog.dismiss();
                                    savealert.show();
                                }
                            }
                        });
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();
                    }
                }
                else if (fragment instanceof ShoppingFragment) {
                    if (!((ShoppingFragment) fragment).checkNull()) {   // 널 값 검증
                        AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                        alert.setMessage("쇼핑 항목을 추가하세요!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = builder.create();

                        btnApply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MemoTitle = editTextMemoTitle.getText().toString();
                                if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                                    AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                                    alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                                } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                                    alertDialog.dismiss();
                                    savealert.show();
                                }
                            }
                        });
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();
                    }
                }
                else if (fragment instanceof WishFragment) {
                    if (!((WishFragment) fragment).checkNull()) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                        alert.setMessage("위시리스트를 추가하세요!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = builder.create();

                        btnApply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MemoTitle = editTextMemoTitle.getText().toString();
                                if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                                    AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                                    alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                                } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                                    alertDialog.dismiss();
                                    savealert.show();
                                }
                            }
                        });
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });

                        alertDialog.show();
                    }
                }
                else if (fragment instanceof GridMemoFragment) {
                    if (!((GridMemoFragment) fragment).checkNull()) { // 널 값 검증 통과 못함 -> 프래그먼트 참조
                        AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                        alert.setMessage("내용을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss());
                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                    } else { // 널 값 검증 통과하면 제목 받음
                        AlertDialog alertDialog = builder.create();
                        btnApply.setOnClickListener(view12 -> {
                            MemoTitle = editTextMemoTitle.getText().toString();
                            if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                                AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                                alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss()).show();
                            } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                                alertDialog.dismiss();
                                savealert.show();
                            }
                        });
                        btnCancel.setOnClickListener(view1 -> alertDialog.dismiss());
                        alertDialog.show();
                    }
                }
                else if (fragment instanceof MonthTrackerFragment) {
                    if (!((MonthTrackerFragment) fragment).checkNull()) { // 널 값 검증 통과 못함 -> 프래그먼트 참조
                        AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                        alert.setMessage("목표를 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss());
                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                    } else { // 널 값 검증 통과하면 제목 받음
                        AlertDialog alertDialog = builder.create();
                        btnApply.setOnClickListener(view12 -> {
                            MemoTitle = editTextMemoTitle.getText().toString();
                            if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                                AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                                alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss()).show();
                            } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                                alertDialog.dismiss();
                                savealert.show();
                            }
                        });
                        btnCancel.setOnClickListener(view1 -> alertDialog.dismiss());
                        alertDialog.show();
                    }
                }
                else if (fragment instanceof StudyTrackerFragment) { //널값 검증 안함
                    AlertDialog alertDialog = builder.create();
                    btnApply.setOnClickListener(view12 -> {
                        MemoTitle = editTextMemoTitle.getText().toString();
                        if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                            AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                            alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss()).show();
                        } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                            alertDialog.dismiss();
                            savealert.show();
                        }
                    });
                    btnCancel.setOnClickListener(view1 -> alertDialog.dismiss());
                    alertDialog.show();
                }
                else if (fragment instanceof HealthTrackerFragment) { //널값 검증 안함
                    AlertDialog alertDialog = builder.create();
                    btnApply.setOnClickListener(view12 -> {
                        MemoTitle = editTextMemoTitle.getText().toString();
                        if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                            AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                            alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss()).show();
                        } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                            alertDialog.dismiss();
                            savealert.show();
                        }
                    });
                    btnCancel.setOnClickListener(view1 -> alertDialog.dismiss());
                    alertDialog.show();
                }
                else if (fragment instanceof ReviewFragment) {
                    if (!((ReviewFragment) fragment).checkNull()) { // 널 값 검증 통과 못함 -> 프래그먼트 참조
                        AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                        alert.setMessage("내용을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss());
                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                    } else { // 널 값 검증 통과하면 제목 받음
                        AlertDialog alertDialog = builder.create();
                        btnApply.setOnClickListener(view12 -> {
                            MemoTitle = editTextMemoTitle.getText().toString();
                            if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                                AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                                alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss()).show();
                            } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                                alertDialog.dismiss();
                                savealert.show();
                            }
                        });
                        btnCancel.setOnClickListener(view1 -> alertDialog.dismiss());
                        alertDialog.show();
                    }
                }
                else if (fragment instanceof LineMemoFragment) {
                    if (!((LineMemoFragment) fragment).checkNull()) { // 널 값 검증 통과 못함 -> 프래그먼트 참조
                        AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                        alert.setMessage("내용을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss());
                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                    } else { // 널 값 검증 통과하면 제목 받음
                        AlertDialog alertDialog = builder.create();
                        btnApply.setOnClickListener(view12 -> {
                            MemoTitle = editTextMemoTitle.getText().toString();
                            if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                                AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                                alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss()).show();
                            } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                                alertDialog.dismiss();
                                savealert.show();
                            }
                        });
                        btnCancel.setOnClickListener(view1 -> alertDialog.dismiss());
                        alertDialog.show();
                    }
                }
                else if (fragment instanceof DailyPlanFragment) {
                    if (!((DailyPlanFragment) fragment).checkNull()) { // 널 값 검증 통과 못함 -> 프래그먼트 참조
                        AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                        alert.setMessage("내용을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss());
                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();
                    } else { // 널 값 검증 통과하면 제목 받음
                        AlertDialog alertDialog = builder.create();
                        btnApply.setOnClickListener(view12 -> {
                            MemoTitle = editTextMemoTitle.getText().toString();
                            if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                                AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                                alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss()).show();
                            } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                                alertDialog.dismiss();
                                savealert.show();
                            }
                        });
                        btnCancel.setOnClickListener(view1 -> alertDialog.dismiss());
                        alertDialog.show();
                    }
                }
                else if (fragment instanceof WeeklyPlanFragment) { //널값 검증 안함
                    AlertDialog alertDialog = builder.create();
                    btnApply.setOnClickListener(view12 -> {
                        MemoTitle = editTextMemoTitle.getText().toString();
                        if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                            AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                            alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss()).show();
                        } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                            alertDialog.dismiss();
                            savealert.show();
                        }
                    });
                    btnCancel.setOnClickListener(view1 -> alertDialog.dismiss());
                    alertDialog.show();
                }
                else if (fragment instanceof MonthlyPlanFragment) { //널값 검증 안함
                    AlertDialog alertDialog = builder.create();
                    btnApply.setOnClickListener(view12 -> {
                        MemoTitle = editTextMemoTitle.getText().toString();
                        if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                            AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                            alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss()).show();
                        } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                            alertDialog.dismiss();
                            savealert.show();
                        }
                    });
                    btnCancel.setOnClickListener(view1 -> alertDialog.dismiss());
                    alertDialog.show();
                }
                else if (fragment instanceof YearlyPlanFragment) { //널값 검증 안함
                    AlertDialog alertDialog = builder.create();
                    btnApply.setOnClickListener(view12 -> {
                        MemoTitle = editTextMemoTitle.getText().toString();
                        if (MemoTitle.equals("") | MemoTitle == null) { // 제목 입력 안했을 때
                            AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                            alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss()).show();
                        } else { // 제목까지 입력 받았으면 첨에 만든 저장 할건지 묻는 다이얼로그 출력하고 저장
                            alertDialog.dismiss();
                            savealert.show();
                        }
                    });
                    btnCancel.setOnClickListener(view1 -> alertDialog.dismiss());
                    alertDialog.show();
                }
            }
        });

        // 메모 휴지통으로 보내기
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fixedmemocnt = PreferenceManager.getInt(MemoViewActivity.this, "fixedmemocnt");
                AlertDialog.Builder alert = new AlertDialog.Builder(MemoViewActivity.this);
                alert.setMessage("메모를 삭제하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        try {
                            Cursor cursor = db.rawQuery("SELECT folder_name, fixed FROM "+TemplateCase+" WHERE id = "+memoid+";", null);
                            cursor.moveToFirst();
                            String whereMemoIn = cursor.getString(0);
                            int memofixed = cursor.getInt(1);
                            if (memofixed == 1) {
                                fixedmemocnt = fixedmemocnt - 1;
                                PreferenceManager.setInt(MemoViewActivity.this, "fixedmemocnt", fixedmemocnt);
                            }
                            db.execSQL("UPDATE "+TemplateCase+" SET deleted = 1, fixed = 0 WHERE id = "+memoid+";");
                            db.execSQL("UPDATE folder SET count = count - 1 WHERE name = '"+whereMemoIn+"';");
                            Toast.makeText(MemoViewActivity.this, "휴지통 이동 성공", Toast.LENGTH_SHORT).show();
                            if (isAfterWrite) {
                                Intent newMain = new Intent(MemoViewActivity.this, MainActivity.class);
                                startActivity(newMain);
                                finish();
                            } else finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MemoViewActivity.this, "휴지통 이동 실패", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
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

    // DB 닫기
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
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

        // 템플릿 받아오기
        TemplateCase = intent.getStringExtra("template");

        // 배경색, 고정여부 설정
        switch (Mode) {
            case "write":
                MemoBackground = "yellow";
                setMemoBackground(MemoBackground);
                initFragment(TemplateCase);
                break;
            case "view":
                memoid = intent.getIntExtra("memoid", 0);
                MemoTitle = intent.getStringExtra("memotitle");
                try {
                    Cursor cursor = db.rawQuery("SELECT bgcolor, fixed FROM "+TemplateCase+" WHERE id = "+memoid+"", null);
                    cursor.moveToFirst();
                    MemoBackground = cursor.getString(0);
                    isMemofixed = cursor.getInt(1);
                    setMemoBackground(MemoBackground);
                    if (isMemofixed == 1) checkBoxFixed.setChecked(true);
                    else checkBoxFixed.setChecked(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            case "gridmemo":
                ft.replace(R.id.template_frame, new GridMemoFragment());
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
            case "monthtracker":
                ft.replace(R.id.template_frame, new MonthTrackerFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
        }
    }

    // view 모드에서 initFragment
    private void viewMemo(String Template, int id) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putInt("memoid", id);

        switch (Template) {
            case "nonlinememo":
                NonlineMemoFragment nonlineMemoFragment = new NonlineMemoFragment();
                nonlineMemoFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, nonlineMemoFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "todolist":
                TodoFragment todoFragment = new TodoFragment();
                todoFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, todoFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "shoppinglist":
                ShoppingFragment shoppingFragment = new ShoppingFragment();
                shoppingFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, shoppingFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "wishlist":
                WishFragment wishFragment = new WishFragment();
                wishFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, wishFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "gridmemo":
                GridMemoFragment gridMemoFragment = new GridMemoFragment();
                gridMemoFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, gridMemoFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "review":
                ReviewFragment reviewFragment = new ReviewFragment();
                reviewFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, reviewFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "healthtracker":
                HealthTrackerFragment healthTrackerFragment = new HealthTrackerFragment();
                healthTrackerFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, healthTrackerFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "studytracker":
                StudyTrackerFragment studyTrackerFragment = new StudyTrackerFragment();
                studyTrackerFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, studyTrackerFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "monthtracker":
                MonthTrackerFragment monthTrackerFragment = new MonthTrackerFragment();
                monthTrackerFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, monthTrackerFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "linememo":
                LineMemoFragment lineMemoFragment = new LineMemoFragment();
                lineMemoFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, lineMemoFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "dailyplan":
                DailyPlanFragment dailyPlanFragment = new DailyPlanFragment();
                dailyPlanFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, dailyPlanFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "weeklyplan":
                WeeklyPlanFragment weeklyPlanFragment = new WeeklyPlanFragment();
                weeklyPlanFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, weeklyPlanFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "monthlyplan":
                MonthlyPlanFragment monthlyPlanFragment = new MonthlyPlanFragment();
                monthlyPlanFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, monthlyPlanFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "yearlyplan":
                YearlyPlanFragment yearlyPlanFragment = new YearlyPlanFragment();
                yearlyPlanFragment.setArguments(bundle);
                ft.replace(R.id.template_frame, yearlyPlanFragment);
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
