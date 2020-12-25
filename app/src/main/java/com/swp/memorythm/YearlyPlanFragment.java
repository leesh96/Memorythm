package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.os.Bundle;
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

public class YearlyPlanFragment extends Fragment {
    private TextView textViewDate;
    private EditText editTextJan, editTextFeb, editTextMar, editTextApr, editTextMay, editTextJun;
    private EditText editTextJul, editTextAug, editTextSep, editTextOct, editTextNov, editTextDec;

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

        // TODO: 2020-11-20 파이어베이스 연동

        return rootView;
    }
}
