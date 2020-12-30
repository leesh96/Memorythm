package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LineMemoFragment extends Fragment {
    private TextView textViewDate;
    private EditText editTextContent;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private int id;

    public static LineMemoFragment newInstance() {
        return new LineMemoFragment();
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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_linememo, container, false);

        textViewDate = rootView.findViewById(R.id.write_date);
        editTextContent = rootView.findViewById(R.id.memo_content);

        dbHelper = new DBHelper(getActivity());
        db = dbHelper.getWritableDatabase();

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy - MM - dd", Locale.KOREA);
        textViewDate.setText(simpleDateFormat.format(currentTime));

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 데이트픽커 띄우기
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // TODO: 2020-11-20 파이어베이스 연동

        return rootView;
    }

    public void saveData(String Mode) {

        String userDate = textViewDate.getText().toString();
        String content = editTextContent.getText().toString();

        switch (Mode) {
            case "write":
                db.execSQL("INSERT INTO linememo('userdate', 'content') VALUES('" + userDate + "', '" + content + "');");
                final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                cursor.moveToFirst();
                id = cursor.getInt(0); //가장 최근에 삽입된 레코드의 id
                break;
            case "view":
                db.execSQL("UPDATE linememo SET userdate = '" + userDate + "', content = '" + content + "' WHERE id = " + id + ";");
                break;
        }
    }

    public void delete() {

        db.execSQL("UPDATE linememo SET deleted = 1 WHERE id = " + id + ";");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
    }
}
