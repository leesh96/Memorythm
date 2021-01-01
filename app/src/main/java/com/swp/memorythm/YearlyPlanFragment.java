package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class YearlyPlanFragment extends Fragment {
    private TextView textViewDate;
    private EditText editTextJan, editTextFeb, editTextMar, editTextApr, editTextMay, editTextJun,
                     editTextJul, editTextAug, editTextSep, editTextOct, editTextNov, editTextDec;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    public int memoid;
    private EditText[] yearView = new EditText[12];
    private String userDate;
    private String[] setContent;
    private StringBuilder contentYear;

    public static YearlyPlanFragment newInstance() {
        return new YearlyPlanFragment();
    }

    // 캘린더 객체 생성
    Calendar myCalendar = Calendar.getInstance();

    //데이트픽커 다이얼로그
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            updateLabel();
        }
    };

    // 텍스트뷰 날짜 업데이트
    private void updateLabel() {
        String DateFormat = "yyyy";
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
        Cursor cursor = db.rawQuery("SELECT userdate, contentYear, splitKey FROM yearlyplan WHERE id = "+memoid+"", null);
        while (cursor.moveToNext()) {
            userDate = cursor.getString(0);
            String splitKey = cursor.getString(2);
            setContent = cursor.getString(1).split(splitKey);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_yearlyplan, container, false);

        textViewDate = rootView.findViewById(R.id.write_date);
        editTextJan = rootView.findViewById(R.id.memo_content_jan);
        editTextFeb = rootView.findViewById(R.id.memo_content_feb);
        editTextMar = rootView.findViewById(R.id.memo_content_mar);
        editTextApr = rootView.findViewById(R.id.memo_content_apr);
        editTextMay = rootView.findViewById(R.id.memo_content_may);
        editTextJun = rootView.findViewById(R.id.memo_content_jun);
        editTextJul = rootView.findViewById(R.id.memo_content_jul);
        editTextAug = rootView.findViewById(R.id.memo_content_aug);
        editTextSep = rootView.findViewById(R.id.memo_content_sep);
        editTextOct = rootView.findViewById(R.id.memo_content_oct);
        editTextNov = rootView.findViewById(R.id.memo_content_nov);
        editTextDec = rootView.findViewById(R.id.memo_content_dec);

        yearView[0] = editTextJan;
        yearView[1] = editTextFeb;
        yearView[2] = editTextMar;
        yearView[3] = editTextApr;
        yearView[4] = editTextMay;
        yearView[5] = editTextJun;
        yearView[6] = editTextJul;
        yearView[7] = editTextAug;
        yearView[8] = editTextSep;
        yearView[9] = editTextOct;
        yearView[10] = editTextNov;
        yearView[11] = editTextDec;

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        textViewDate.setText(simpleDateFormat.format(currentTime));

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 데이트픽커 띄우기
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getArguments() != null) {

            textViewDate.setText(userDate);

            for(int i = 0; i < yearView.length; i++) {

                yearView[i].setText(setContent[i]);
            }
        }
    }

    // 메모아이디 가져오기
    public int getMemoid() {
        return memoid;
    }

    // 저장 및 수정
    public boolean saveData(String Mode, String Bgcolor, String title) {
        db = dbHelper.getReadableDatabase();

        userDate = textViewDate.getText().toString();
        contentYear = new StringBuilder();
        String splitKey = makeKey();

        for (EditText editText : yearView) {

            if(editText.getText().toString().equals("")) {

                contentYear.append(" ").append(splitKey);
            }
            else {

                contentYear.append(editText.getText().toString().replaceAll("'", "''")).append(splitKey);
            }
        }

        //editdate 컬럼 업데이트 때문에
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        switch (Mode) {
            case "write":
                db.execSQL("INSERT INTO yearlyplan('userdate', 'contentYear', 'splitKey', 'title', 'bgcolor') VALUES('" + userDate + "', '" + contentYear + "', '" + splitKey + "', '" + title + "', '" + Bgcolor + "');");
                // 작성하면 view 모드로 바꾸기 위해 최근 삽입한 레코드 id로 바꿔줌
                final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                cursor.moveToFirst();
                memoid = cursor.getInt(0);
                break;
            case "view":
                // 메모 수정
                if (getArguments() == null) {
                    db.execSQL("UPDATE yearlyplan SET userdate = '"+userDate+"', contentYear = '"+contentYear+"', splitKey = '"+splitKey+"', title = '"+title+"', editdate = '"+dateFormat.format(date.getTime()) + "' WHERE id = "+memoid+";");
                } else {
                    memoid = getArguments().getInt("memoid");
                    db.execSQL("UPDATE yearlyplan SET userdate = '"+userDate+"', contentYear = '"+contentYear+"', splitKey = '"+splitKey+"', title = '"+title+"', editdate = '"+dateFormat.format(date.getTime()) + "' WHERE id = "+memoid+";");
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
