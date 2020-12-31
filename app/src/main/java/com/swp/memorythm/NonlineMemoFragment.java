package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.gson.internal.$Gson$Preconditions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NonlineMemoFragment extends Fragment {
    private TextView textViewDate;
    private EditText editTextContent;
    DBHelper dbHelper;
    SQLiteDatabase db;
    public int memoid;
    private String Userdate, Content, MemoTitle;

    public static NonlineMemoFragment newInstance() {
        return new NonlineMemoFragment();
    }

    // 캘린더 객체 생성
    Calendar myCalendar = Calendar.getInstance();

    //데이트픽커 다이얼로그
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    // 텍스트뷰 날짜 업데이트
    private void updateLabel() {
        String DateFormat = "yyyy - MM - dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat, Locale.KOREA);

        textViewDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_nonlinememo, container, false);

        textViewDate = rootView.findViewById(R.id.write_date);
        editTextContent = rootView.findViewById(R.id.nonlinememo_content);

        if (getArguments() != null) {
            textViewDate.setText(Userdate);
            editTextContent.setText(Content);
        } else {
            // 텍스트뷰 초기 날짜 현재 날짜로 설정
            textViewDate.setText(PreferenceManager.getString(getContext(), "currentDate"));
        }

        textViewDate.setOnClickListener(new View.OnClickListener() { // 데이트픽커 띄우기
            @Override
            public void onClick(View v) {
                // 뷰 모드면 날짜 맞게 해줘야댐
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();
        if (getArguments() != null) {
            memoid = getArguments().getInt("memoid");
        }
        Cursor cursor = db.rawQuery("SELECT userdate, content FROM nonlinememo WHERE id = "+memoid+"", null);
        while (cursor.moveToNext()) {
            Userdate = cursor.getString(0);
            Content = cursor.getString(1);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // 메모아이디 가져오기
    public int getMemoid() {
        return memoid;
    }

    public boolean checkNull() {
        Content = editTextContent.getText().toString();

        if (Content.equals("") | Content == null) {
            return false;
        } else {
            return true;
        }
    }

    // 저장 및 수정
    public boolean saveData(String Mode, String Bgcolor, String Title) {
        db = dbHelper.getReadableDatabase();

        boolean success = false;

        Userdate = textViewDate.getText().toString();
        Content = editTextContent.getText().toString();

        //editdate 컬럼 업데이트 때문에
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        switch (Mode) {
            case "write":
                try {
                    db.execSQL("INSERT INTO nonline('userdate', 'content', 'bgcolor', 'title') VALUES('" + Userdate + "', '" + Content + "', '" + Bgcolor + "', '" + Title + "');");
                    // 작성하면 view 모드로 바꾸기 위해 최근 삽입한 레코드 id로 바꿔줌
                    final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                    cursor.moveToFirst();
                    memoid = cursor.getInt(0);
                    success = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "view":
                // 메모 수정
                if (getArguments() == null) {
                    try {
                        db.execSQL("UPDATE nonlinememo SET userdate = '" + Userdate + "', content = '" + Content + "', Title = '" + Title + "', editdate = '" + dateFormat.format(date.getTime()) + "' WHERE id = " + memoid + ";");
                        success = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    memoid = getArguments().getInt("memoid");
                    try {
                        db.execSQL("UPDATE nonlinememo SET userdate = '" + Userdate + "', content = '" + Content + "', Title = '" + Title + "', editdate = '" + dateFormat.format(date.getTime()) + "' WHERE id = " + memoid + ";");
                        success = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

        return success;

        /*if (Content.equals("") | Content == null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setMessage("내용을 입력하세요!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            while (alertDialog.isShowing()) {
                if (!alertDialog.isShowing()) return success;
            }
        } else {
            if (Mode.equals("view")) {
                MemoTitle = getArguments().getString("memotitle");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_title, null, false);
            builder.setView(dialogView);

            EditText editTextMemoTitle;
            *//*Button btnApply, btnCancel;*//*

            editTextMemoTitle = dialogView.findViewById(R.id.et_memotitle);
            *//*btnApply = dialogView.findViewById(R.id.btn_apply);
            btnCancel = dialogView.findViewById(R.id.btn_cancel);*//*

            editTextMemoTitle.setText(MemoTitle);

            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MemoTitle = editTextMemoTitle.getText().toString();
                    if (MemoTitle.equals("") | MemoTitle == null) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setMessage("제목을 입력하세요!").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    } else {
                        dialog.dismiss();
                    }
                }
            });
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            if (!alertDialog.isShowing()) {

            }
            return success;
        }*/
    }
}
