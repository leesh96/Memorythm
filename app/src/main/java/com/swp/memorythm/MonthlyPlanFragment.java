package com.swp.memorythm;

import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MonthlyPlanFragment extends Fragment {
    private TextView textViewDate;
    GridView gridView;
    MonthAdapter monthAdapter;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    public int memoid;
    private String userDate;
    private StringBuilder contentMonth;
    private EditText[] monthView;
    private String[] setContent;

    // 메인액티비티에서 고정메모 보는 프래그먼트로 만들어졌으면 수정 불가능하게 뷰 조정
    public boolean fromFixedFragment;

    public boolean isFromFixedFragment() {
        return fromFixedFragment;
    }

    public void setFromFixedFragment(boolean fromFixedFragment) {
        this.fromFixedFragment = fromFixedFragment;
    }

    public static MonthlyPlanFragment newInstance() {
        return new MonthlyPlanFragment();
    }

    // 캘린더 객체 생성
    Calendar myCalendar = Calendar.getInstance();

    //데이트픽커 다이얼로그
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            monthAdapter.cal = myCalendar;
            monthAdapter.calculate();
            monthAdapter.notifyDataSetChanged();
            updateLabel();
        }
    };

    // 텍스트뷰 날짜 업데이트
    private void updateLabel() {
        String DateFormat = "yyyy - MM";
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat, Locale.KOREA);

        textViewDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        if (getArguments() != null) {
            memoid = getArguments().getInt("memoid");
        }
        Cursor cursor = db.rawQuery("SELECT userdate, contentMonth, splitKey FROM monthlyplan WHERE id = "+memoid+"", null);
        while (cursor.moveToNext()) {
            userDate = cursor.getString(0);
            String splitKey = cursor.getString(2);
            setContent = cursor.getString(1).split(splitKey);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_monthlyplan, container, false);

        textViewDate = rootView.findViewById(R.id.write_date);
        gridView = rootView.findViewById(R.id.container_date);

        //키보드가 화면 못밀게 하기
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy - MM", Locale.KOREA);

        if(getArguments() != null) {

            textViewDate.setText(userDate);
            monthAdapter = new MonthAdapter(getContext(), myCalendar, setContent);

            // 수정 불가하게 만들기
            if (isFromFixedFragment()) {
                textViewDate.setEnabled(false);
                monthAdapter.setEnabled();
                monthAdapter.notifyDataSetChanged();
            }
        }
        else {

            textViewDate.setText(simpleDateFormat.format(currentTime));
            monthAdapter = new MonthAdapter(getContext(), myCalendar);
        }

        //달력 어댑터 설정
        gridView.setAdapter(monthAdapter);

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 데이트픽커 띄우기
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        return rootView;
    }

    // 메모아이디 가져오기
    public int getMemoid() {
        return memoid;
    }

    // 저장 및 수정
    public boolean saveData(String Mode, String Bgcolor, String title) {
        db = dbHelper.getReadableDatabase();

        userDate = textViewDate.getText().toString();
        contentMonth = new StringBuilder();
        monthView = monthAdapter.getEditTexts();
        String splitKey = makeKey();

        for (EditText editText : monthView) {

            if(editText.getText().toString().equals("")) {

                contentMonth.append(" ").append(splitKey);
            }
            else {

                contentMonth.append(editText.getText().toString().replaceAll("'", "''")).append(splitKey);
            }
        }

        //editdate 컬럼 업데이트 때문에
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        switch (Mode) {
            case "write":
                db.execSQL("INSERT INTO monthlyplan('userdate', 'contentMonth', 'splitKey', 'title', 'bgcolor') VALUES('" + userDate + "', '" + contentMonth + "', '" + splitKey + "', '" + title + "', '" + Bgcolor + "');");
                // 작성하면 view 모드로 바꾸기 위해 최근 삽입한 레코드 id로 바꿔줌
                final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                cursor.moveToFirst();
                memoid = cursor.getInt(0);
                db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '메모';");
                break;
            case "view":
                // 메모 수정
                if (getArguments() == null) {
                    db.execSQL("UPDATE monthlyplan SET userdate = '"+userDate+"', contentMonth = '"+contentMonth+"', splitKey = '"+splitKey+"', title = '"+title+"', editdate = '"+dateFormat.format(date.getTime()) + "' WHERE id = "+memoid+";");
                } else {
                    memoid = getArguments().getInt("memoid");
                    db.execSQL("UPDATE monthlyplan SET userdate = '"+userDate+"', contentMonth = '"+contentMonth+"', splitKey = '"+splitKey+"', title = '"+title+"', editdate = '"+dateFormat.format(date.getTime()) + "' WHERE id = "+memoid+";");
                }
                break;
        }
        return true;
    }

    public String makeKey(){
        StringBuilder key = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int rIndex = random.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    key.append((char) ((int) (random.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    key.append((char) ((int) (random.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    key.append(random.nextInt(10));
                    break;
            }
        }
        return key.toString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
    }
}
