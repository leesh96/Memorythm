package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StudyTrackerFragment extends Fragment {
    private TextView textViewDate;
    private ImageView am06_1, am06_2, am06_3, am06_4, am06_5, am06_6,
            am07_1, am07_2, am07_3, am07_4, am07_5, am07_6, am08_1, am08_2, am08_3, am08_4, am08_5, am08_6,
            am09_1, am09_2, am09_3, am09_4, am09_5, am09_6, am10_1, am10_2, am10_3, am10_4, am10_5, am10_6,
            am11_1, am11_2, am11_3, am11_4, am11_5, am11_6, pm12_1, pm12_2, pm12_3, pm12_4, pm12_5, pm12_6,
            pm01_1, pm01_2, pm01_3, pm01_4, pm01_5, pm01_6, pm02_1, pm02_2, pm02_3, pm02_4, pm02_5, pm02_6,
            pm03_1, pm03_2, pm03_3, pm03_4, pm03_5, pm03_6, pm04_1, pm04_2, pm04_3, pm04_4, pm04_5, pm04_6,
            pm05_1, pm05_2, pm05_3, pm05_4, pm05_5, pm05_6, pm06_1, pm06_2, pm06_3, pm06_4, pm06_5, pm06_6,
            pm07_1, pm07_2, pm07_3, pm07_4, pm07_5, pm07_6, pm08_1, pm08_2, pm08_3, pm08_4, pm08_5, pm08_6,
            pm09_1, pm09_2, pm09_3, pm09_4, pm09_5, pm09_6, pm10_1, pm10_2, pm10_3, pm10_4, pm10_5, pm10_6,
            pm11_1, pm11_2, pm11_3, pm11_4, pm11_5, pm11_6, am12_1, am12_2, am12_3, am12_4, am12_5, am12_6,
            am01_1, am01_2, am01_3, am01_4, am01_5, am01_6, am02_1, am02_2, am02_3, am02_4, am02_5, am02_6,
            am03_1, am03_2, am03_3, am03_4, am03_5, am03_6, am04_1, am04_2, am04_3, am04_4, am04_5, am04_6,
            am05_1, am05_2, am05_3, am05_4, am05_5, am05_6;
    private int num_am06_1=0, num_am06_2=0, num_am06_3=0, num_am06_4=0, num_am06_5=0, num_am06_6=0,num_am07_1=0, num_am07_2=0, num_am07_3=0, num_am07_4=0, num_am07_5=0, num_am07_6=0,
            num_am08_1=0, num_am08_2=0, num_am08_3=0, num_am08_4=0, num_am08_5=0, num_am08_6=0,num_am09_1=0, num_am09_2=0, num_am09_3=0, num_am09_4=0, num_am09_5=0, num_am09_6=0,
            num_am10_1=0, num_am10_2=0, num_am10_3=0, num_am10_4=0, num_am10_5=0, num_am10_6=0,num_am11_1=0, num_am11_2=0, num_am11_3=0, num_am11_4=0, num_am11_5=0, num_am11_6=0,
            num_pm12_1=0, num_pm12_2=0, num_pm12_3=0, num_pm12_4=0, num_pm12_5=0, num_pm12_6=0,num_pm01_1=0, num_pm01_2=0, num_pm01_3=0, num_pm01_4=0, num_pm01_5=0, num_pm01_6=0,
            num_pm02_1=0, num_pm02_2=0, num_pm02_3=0, num_pm02_4=0, num_pm02_5=0, num_pm02_6=0,num_pm03_1=0, num_pm03_2=0, num_pm03_3=0, num_pm03_4=0, num_pm03_5=0, num_pm03_6=0,
            num_pm04_1=0, num_pm04_2=0, num_pm04_3=0, num_pm04_4=0, num_pm04_5=0, num_pm04_6=0,num_pm05_1=0, num_pm05_2=0, num_pm05_3=0, num_pm05_4=0, num_pm05_5=0, num_pm05_6=0,
            num_pm06_1=0, num_pm06_2=0, num_pm06_3=0, num_pm06_4=0, num_pm06_5=0, num_pm06_6=0,num_pm07_1=0, num_pm07_2=0, num_pm07_3=0, num_pm07_4=0, num_pm07_5=0, num_pm07_6=0,
            num_pm08_1=0, num_pm08_2=0, num_pm08_3=0, num_pm08_4=0, num_pm08_5=0, num_pm08_6=0,num_pm09_1=0, num_pm09_2=0, num_pm09_3=0, num_pm09_4=0, num_pm09_5=0, num_pm09_6=0,
            num_pm10_1=0, num_pm10_2=0, num_pm10_3=0, num_pm10_4=0, num_pm10_5=0, num_pm10_6=0,num_pm11_1=0, num_pm11_2=0, num_pm11_3=0, num_pm11_4=0, num_pm11_5=0, num_pm11_6=0,
            num_am12_1=0, num_am12_2=0, num_am12_3=0, num_am12_4=0, num_am12_5=0, num_am12_6=0,num_am01_1=0, num_am01_2=0, num_am01_3=0, num_am01_4=0, num_am01_5=0, num_am01_6=0,
            num_am02_1=0, num_am02_2=0, num_am02_3=0, num_am02_4=0, num_am02_5=0, num_am02_6=0,num_am03_1=0, num_am03_2=0, num_am03_3=0, num_am03_4=0, num_am03_5=0, num_am03_6=0,
            num_am04_1=0, num_am04_2=0, num_am04_3=0, num_am04_4=0, num_am04_5=0, num_am04_6=0,num_am05_1=0, num_am05_2=0, num_am05_3=0, num_am05_4=0, num_am05_5=0, num_am05_6;
    public static StudyTrackerFragment newInstance() {
        return new StudyTrackerFragment();
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.template_studytracker, container, false);

        textViewDate = viewGroup.findViewById(R.id.write_date);


        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy - MM - dd", Locale.KOREA);
        textViewDate.setText(simpleDateFormat.format(currentTime));

        textViewDate.setOnClickListener(v -> { // 데이트픽커 띄우기
            new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        am01_1 = viewGroup.findViewById(R.id.am01_1); am01_2 = viewGroup.findViewById(R.id.am01_2); am01_3 = viewGroup.findViewById(R.id.am01_3);
        am01_4 = viewGroup.findViewById(R.id.am01_4); am01_5 = viewGroup.findViewById(R.id.am01_5); am01_6 = viewGroup.findViewById(R.id.am01_6);
        am02_1 = viewGroup.findViewById(R.id.am02_1); am02_2 = viewGroup.findViewById(R.id.am02_2); am02_3 = viewGroup.findViewById(R.id.am02_3);
        am02_4 = viewGroup.findViewById(R.id.am02_4); am02_5 = viewGroup.findViewById(R.id.am02_5); am02_6 = viewGroup.findViewById(R.id.am02_6);
        am03_1 = viewGroup.findViewById(R.id.am03_1); am03_2 = viewGroup.findViewById(R.id.am03_2); am03_3 = viewGroup.findViewById(R.id.am03_3);
        am03_4 = viewGroup.findViewById(R.id.am03_4); am03_5 = viewGroup.findViewById(R.id.am03_5); am03_6 = viewGroup.findViewById(R.id.am03_6);
        am04_1 = viewGroup.findViewById(R.id.am04_1); am04_2 = viewGroup.findViewById(R.id.am04_2); am04_3 = viewGroup.findViewById(R.id.am04_3);
        am04_4 = viewGroup.findViewById(R.id.am04_4); am04_5 = viewGroup.findViewById(R.id.am04_5); am04_6 = viewGroup.findViewById(R.id.am04_6);
        am05_1 = viewGroup.findViewById(R.id.am05_1); am05_2 = viewGroup.findViewById(R.id.am05_2); am05_3 = viewGroup.findViewById(R.id.am05_3);
        am05_4 = viewGroup.findViewById(R.id.am05_4); am05_5 = viewGroup.findViewById(R.id.am05_5); am05_6 = viewGroup.findViewById(R.id.am05_6);
        am06_1 = viewGroup.findViewById(R.id.am06_1); am06_2 = viewGroup.findViewById(R.id.am06_2); am06_3 = viewGroup.findViewById(R.id.am06_3);
        am06_4 = viewGroup.findViewById(R.id.am06_4); am06_5 = viewGroup.findViewById(R.id.am06_5); am06_6 = viewGroup.findViewById(R.id.am06_6);
        am07_1 = viewGroup.findViewById(R.id.am07_1); am07_2 = viewGroup.findViewById(R.id.am07_2); am07_3 = viewGroup.findViewById(R.id.am07_3);
        am07_4 = viewGroup.findViewById(R.id.am07_4); am07_5 = viewGroup.findViewById(R.id.am07_5); am07_6 = viewGroup.findViewById(R.id.am07_6);
        am08_1 = viewGroup.findViewById(R.id.am08_1); am08_2 = viewGroup.findViewById(R.id.am08_2); am08_3 = viewGroup.findViewById(R.id.am08_3);
        am08_4 = viewGroup.findViewById(R.id.am08_4); am08_5 = viewGroup.findViewById(R.id.am08_5); am08_6 = viewGroup.findViewById(R.id.am08_6);
        am09_1 = viewGroup.findViewById(R.id.am09_1); am09_2 = viewGroup.findViewById(R.id.am09_2); am09_3 = viewGroup.findViewById(R.id.am09_3);
        am09_4 = viewGroup.findViewById(R.id.am09_4); am09_5 = viewGroup.findViewById(R.id.am09_5); am09_6 = viewGroup.findViewById(R.id.am09_6);
        am10_1 = viewGroup.findViewById(R.id.am10_1); am10_2 = viewGroup.findViewById(R.id.am10_2); am10_3 = viewGroup.findViewById(R.id.am10_3);
        am10_4 = viewGroup.findViewById(R.id.am10_4); am10_5 = viewGroup.findViewById(R.id.am10_5); am10_6 = viewGroup.findViewById(R.id.am10_6);
        am11_1 = viewGroup.findViewById(R.id.am11_1); am11_2 = viewGroup.findViewById(R.id.am11_2); am11_3 = viewGroup.findViewById(R.id.am11_3);
        am11_4 = viewGroup.findViewById(R.id.am11_4); am11_5 = viewGroup.findViewById(R.id.am11_5); am11_6 = viewGroup.findViewById(R.id.am11_6);
        am12_1 = viewGroup.findViewById(R.id.am12_1); am12_2 = viewGroup.findViewById(R.id.am12_2); am12_3 = viewGroup.findViewById(R.id.am12_3);
        am12_4 = viewGroup.findViewById(R.id.am12_4); am12_5 = viewGroup.findViewById(R.id.am12_5); am12_6 = viewGroup.findViewById(R.id.am12_6);
        pm01_1 = viewGroup.findViewById(R.id.pm01_1); pm01_2 = viewGroup.findViewById(R.id.pm01_2); pm01_3 = viewGroup.findViewById(R.id.pm01_3);
        pm01_4 = viewGroup.findViewById(R.id.pm01_4); pm01_5 = viewGroup.findViewById(R.id.pm01_5); pm01_6 = viewGroup.findViewById(R.id.pm01_6);
        pm02_1 = viewGroup.findViewById(R.id.pm02_1); pm02_2 = viewGroup.findViewById(R.id.pm02_2); pm02_3 = viewGroup.findViewById(R.id.pm02_3);
        pm02_4 = viewGroup.findViewById(R.id.pm02_4); pm02_5 = viewGroup.findViewById(R.id.pm02_5); pm02_6 = viewGroup.findViewById(R.id.pm02_6);
        pm03_1 = viewGroup.findViewById(R.id.pm03_1); pm03_2 = viewGroup.findViewById(R.id.pm03_2); pm03_3 = viewGroup.findViewById(R.id.pm03_3);
        pm03_4 = viewGroup.findViewById(R.id.pm03_4); pm03_5 = viewGroup.findViewById(R.id.pm03_5); pm03_6 = viewGroup.findViewById(R.id.pm03_6);
        pm04_1 = viewGroup.findViewById(R.id.pm04_1); pm04_2 = viewGroup.findViewById(R.id.pm04_2); pm04_3 = viewGroup.findViewById(R.id.pm04_3);
        pm04_4 = viewGroup.findViewById(R.id.pm04_4); pm04_5 = viewGroup.findViewById(R.id.pm04_5); pm04_6 = viewGroup.findViewById(R.id.pm04_6);
        pm05_1 = viewGroup.findViewById(R.id.pm05_1); pm05_2 = viewGroup.findViewById(R.id.pm05_2); pm05_3 = viewGroup.findViewById(R.id.pm05_3);
        pm05_4 = viewGroup.findViewById(R.id.pm05_4); pm05_5 = viewGroup.findViewById(R.id.pm05_5); pm05_6 = viewGroup.findViewById(R.id.pm05_6);
        pm06_1 = viewGroup.findViewById(R.id.pm06_1); pm06_2 = viewGroup.findViewById(R.id.pm06_2); pm06_3 = viewGroup.findViewById(R.id.pm06_3);
        pm06_4 = viewGroup.findViewById(R.id.pm06_4); pm06_5 = viewGroup.findViewById(R.id.pm06_5); pm06_6 = viewGroup.findViewById(R.id.pm06_6);
        pm07_1 = viewGroup.findViewById(R.id.pm07_1); pm07_2 = viewGroup.findViewById(R.id.pm07_2); pm07_3 = viewGroup.findViewById(R.id.pm07_3);
        pm07_4 = viewGroup.findViewById(R.id.pm07_4); pm07_5 = viewGroup.findViewById(R.id.pm07_5); pm07_6 = viewGroup.findViewById(R.id.pm07_6);
        pm08_1 = viewGroup.findViewById(R.id.pm08_1); pm08_2 = viewGroup.findViewById(R.id.pm08_2); pm08_3 = viewGroup.findViewById(R.id.pm08_3);
        pm08_4 = viewGroup.findViewById(R.id.pm08_4); pm08_5 = viewGroup.findViewById(R.id.pm08_5); pm08_6 = viewGroup.findViewById(R.id.pm08_6);
        pm09_1 = viewGroup.findViewById(R.id.pm09_1); pm09_2 = viewGroup.findViewById(R.id.pm09_2); pm09_3 = viewGroup.findViewById(R.id.pm09_3);
        pm09_4 = viewGroup.findViewById(R.id.pm09_4); pm09_5 = viewGroup.findViewById(R.id.pm09_5); pm09_6 = viewGroup.findViewById(R.id.pm09_6);
        pm10_1 = viewGroup.findViewById(R.id.pm10_1); pm10_2 = viewGroup.findViewById(R.id.pm10_2); pm10_3 = viewGroup.findViewById(R.id.pm10_3);
        pm10_4 = viewGroup.findViewById(R.id.pm10_4); pm10_5 = viewGroup.findViewById(R.id.pm10_5); pm10_6 = viewGroup.findViewById(R.id.pm10_6);
        pm11_1 = viewGroup.findViewById(R.id.pm11_1); pm11_2 = viewGroup.findViewById(R.id.pm11_2); pm11_3 = viewGroup.findViewById(R.id.pm11_3);
        pm11_4 = viewGroup.findViewById(R.id.pm11_4); pm11_5 = viewGroup.findViewById(R.id.pm11_5); pm11_6 = viewGroup.findViewById(R.id.pm11_6);
        pm12_1 = viewGroup.findViewById(R.id.pm12_1); pm12_2 = viewGroup.findViewById(R.id.pm12_2); pm12_3 = viewGroup.findViewById(R.id.pm12_3);
        pm12_4 = viewGroup.findViewById(R.id.pm12_4); pm12_5 = viewGroup.findViewById(R.id.pm12_5); pm12_6 = viewGroup.findViewById(R.id.pm12_6);

        //onclicklistener
        am01_1.setOnClickListener(v -> num_am01_1 = changeBgcolor(am01_1,num_am01_1)); am01_2.setOnClickListener(v -> num_am01_2 = changeBgcolor(am01_2,num_am01_2));
        am01_3.setOnClickListener(v -> num_am01_3 = changeBgcolor(am01_3,num_am01_3)); am01_4.setOnClickListener(v -> num_am01_4 = changeBgcolor(am01_4,num_am01_4));
        am01_5.setOnClickListener(v -> num_am01_5 = changeBgcolor(am01_5,num_am01_5)); am01_6.setOnClickListener(v -> num_am01_6 = changeBgcolor(am01_6,num_am01_6));
        am02_1.setOnClickListener(v -> num_am02_1 = changeBgcolor(am02_1,num_am02_1)); am02_2.setOnClickListener(v -> num_am02_2 = changeBgcolor(am02_2,num_am02_2));
        am02_3.setOnClickListener(v -> num_am02_3 = changeBgcolor(am02_3,num_am02_3)); am02_4.setOnClickListener(v -> num_am02_4 = changeBgcolor(am02_4,num_am02_4));
        am02_5.setOnClickListener(v -> num_am02_5 = changeBgcolor(am02_5,num_am02_5)); am02_6.setOnClickListener(v -> num_am02_6 = changeBgcolor(am02_6,num_am02_6));
        am03_1.setOnClickListener(v -> num_am03_1 = changeBgcolor(am03_1,num_am03_1)); am03_2.setOnClickListener(v -> num_am03_2 = changeBgcolor(am03_2,num_am03_2));
        am03_3.setOnClickListener(v -> num_am03_3 = changeBgcolor(am03_3,num_am03_3)); am03_4.setOnClickListener(v -> num_am03_4 = changeBgcolor(am03_4,num_am03_4));
        am03_5.setOnClickListener(v -> num_am03_5 = changeBgcolor(am03_5,num_am03_5)); am03_6.setOnClickListener(v -> num_am03_6 = changeBgcolor(am03_6,num_am03_6));
        am04_1.setOnClickListener(v -> num_am04_1 = changeBgcolor(am04_1,num_am04_1)); am04_2.setOnClickListener(v -> num_am04_2 = changeBgcolor(am04_2,num_am04_2));
        am04_3.setOnClickListener(v -> num_am04_3 = changeBgcolor(am04_3,num_am04_3)); am04_4.setOnClickListener(v -> num_am04_4 = changeBgcolor(am04_4,num_am04_4));
        am04_5.setOnClickListener(v -> num_am04_5 = changeBgcolor(am04_5,num_am04_5)); am04_6.setOnClickListener(v -> num_am04_6 = changeBgcolor(am04_6,num_am04_6));
        am05_1.setOnClickListener(v -> num_am05_1 = changeBgcolor(am05_1,num_am05_1)); am05_2.setOnClickListener(v -> num_am05_2 = changeBgcolor(am05_2,num_am05_2));
        am05_3.setOnClickListener(v -> num_am05_3 = changeBgcolor(am05_3,num_am05_3)); am05_4.setOnClickListener(v -> num_am05_4 = changeBgcolor(am05_4,num_am05_4));
        am05_5.setOnClickListener(v -> num_am05_5 = changeBgcolor(am05_5,num_am05_5)); am05_6.setOnClickListener(v -> num_am05_6 = changeBgcolor(am05_6,num_am05_6));
        am06_1.setOnClickListener(v -> num_am06_1 = changeBgcolor(am06_1,num_am06_1)); am06_2.setOnClickListener(v -> num_am06_2 = changeBgcolor(am06_2,num_am06_2));
        am06_3.setOnClickListener(v -> num_am06_3 = changeBgcolor(am06_3,num_am06_3)); am06_4.setOnClickListener(v -> num_am06_4 = changeBgcolor(am06_4,num_am06_4));
        am06_5.setOnClickListener(v -> num_am06_5 = changeBgcolor(am06_5,num_am06_5)); am06_6.setOnClickListener(v -> num_am06_6 = changeBgcolor(am06_6,num_am06_6));
        am07_1.setOnClickListener(v -> num_am07_1 = changeBgcolor(am07_1,num_am07_1)); am07_2.setOnClickListener(v -> num_am07_2 = changeBgcolor(am07_2,num_am07_2));
        am07_3.setOnClickListener(v -> num_am07_3 = changeBgcolor(am07_3,num_am07_3)); am07_4.setOnClickListener(v -> num_am07_4 = changeBgcolor(am07_4,num_am07_4));
        am07_5.setOnClickListener(v -> num_am07_5 = changeBgcolor(am07_5,num_am07_5)); am07_6.setOnClickListener(v -> num_am07_6 = changeBgcolor(am07_6,num_am07_6));
        am08_1.setOnClickListener(v -> num_am08_1 = changeBgcolor(am08_1,num_am08_1)); am08_2.setOnClickListener(v -> num_am08_2 = changeBgcolor(am08_2,num_am08_2));
        am08_3.setOnClickListener(v -> num_am08_3 = changeBgcolor(am08_3,num_am08_3)); am08_4.setOnClickListener(v -> num_am08_4 = changeBgcolor(am08_4,num_am08_4));
        am08_5.setOnClickListener(v -> num_am08_5 = changeBgcolor(am08_5,num_am08_5)); am08_6.setOnClickListener(v -> num_am08_6 = changeBgcolor(am08_6,num_am08_6));
        am09_1.setOnClickListener(v -> num_am09_1 = changeBgcolor(am09_1,num_am09_1)); am09_2.setOnClickListener(v -> num_am09_2 = changeBgcolor(am09_2,num_am09_2));
        am09_3.setOnClickListener(v -> num_am09_3 = changeBgcolor(am09_3,num_am09_3)); am09_4.setOnClickListener(v -> num_am09_4 = changeBgcolor(am09_4,num_am09_4));
        am09_5.setOnClickListener(v -> num_am09_5 = changeBgcolor(am09_5,num_am09_5)); am09_6.setOnClickListener(v -> num_am09_6 = changeBgcolor(am09_6,num_am09_6));
        am10_1.setOnClickListener(v -> num_am10_1 = changeBgcolor(am10_1,num_am10_1)); am10_2.setOnClickListener(v -> num_am10_2 = changeBgcolor(am10_2,num_am10_2));
        am10_3.setOnClickListener(v -> num_am10_3 = changeBgcolor(am10_3,num_am10_3)); am10_4.setOnClickListener(v -> num_am10_4 = changeBgcolor(am10_4,num_am10_4));
        am10_5.setOnClickListener(v -> num_am10_5 = changeBgcolor(am10_5,num_am10_5)); am10_6.setOnClickListener(v -> num_am10_6 = changeBgcolor(am10_6,num_am10_6));
        am11_1.setOnClickListener(v -> num_am11_1 = changeBgcolor(am11_1,num_am11_1)); am11_2.setOnClickListener(v -> num_am11_2 = changeBgcolor(am11_2,num_am11_2));
        am11_3.setOnClickListener(v -> num_am11_3 = changeBgcolor(am11_3,num_am11_3)); am11_4.setOnClickListener(v -> num_am11_4 = changeBgcolor(am11_4,num_am11_4));
        am11_5.setOnClickListener(v -> num_am11_5 = changeBgcolor(am11_5,num_am11_5)); am11_6.setOnClickListener(v -> num_am11_6 = changeBgcolor(am11_6,num_am11_6));
        am12_1.setOnClickListener(v -> num_am12_1 = changeBgcolor(am12_1,num_am12_1)); am12_2.setOnClickListener(v -> num_am12_2 = changeBgcolor(am12_2,num_am12_2));
        am12_3.setOnClickListener(v -> num_am12_3 = changeBgcolor(am12_3,num_am12_3)); am12_4.setOnClickListener(v -> num_am12_4 = changeBgcolor(am12_4,num_am12_4));
        am12_5.setOnClickListener(v -> num_am12_5 = changeBgcolor(am12_5,num_am12_5)); am12_6.setOnClickListener(v -> num_am12_6 = changeBgcolor(am12_6,num_am12_6));
        pm01_1.setOnClickListener(v -> num_pm01_1 = changeBgcolor(pm01_1,num_pm01_1)); pm01_2.setOnClickListener(v -> num_pm01_2 = changeBgcolor(pm01_2,num_pm01_2));
        pm01_3.setOnClickListener(v -> num_pm01_3 = changeBgcolor(pm01_3,num_pm01_3)); pm01_4.setOnClickListener(v -> num_pm01_4 = changeBgcolor(pm01_4,num_pm01_4));
        pm01_5.setOnClickListener(v -> num_pm01_5 = changeBgcolor(pm01_5,num_pm01_5)); pm01_6.setOnClickListener(v -> num_pm01_6 = changeBgcolor(pm01_6,num_pm01_6));
        pm02_1.setOnClickListener(v -> num_pm02_1 = changeBgcolor(pm02_1,num_pm02_1)); pm02_2.setOnClickListener(v -> num_pm02_2 = changeBgcolor(pm02_2,num_pm02_2));
        pm02_3.setOnClickListener(v -> num_pm02_3 = changeBgcolor(pm02_3,num_pm02_3)); pm02_4.setOnClickListener(v -> num_pm02_4 = changeBgcolor(pm02_4,num_pm02_4));
        pm02_5.setOnClickListener(v -> num_pm02_5 = changeBgcolor(pm02_5,num_pm02_5)); pm02_6.setOnClickListener(v -> num_pm02_6 = changeBgcolor(pm02_6,num_pm02_6));
        pm03_1.setOnClickListener(v -> num_pm03_1 = changeBgcolor(pm03_1,num_pm03_1)); pm03_2.setOnClickListener(v -> num_pm03_2 = changeBgcolor(pm03_2,num_pm03_2));
        pm03_3.setOnClickListener(v -> num_pm03_3 = changeBgcolor(pm03_3,num_pm03_3)); pm03_4.setOnClickListener(v -> num_pm03_4 = changeBgcolor(pm03_4,num_pm03_4));
        pm03_5.setOnClickListener(v -> num_pm03_5 = changeBgcolor(pm03_5,num_pm03_5)); pm03_6.setOnClickListener(v -> num_pm03_6 = changeBgcolor(pm03_6,num_pm03_6));
        pm04_1.setOnClickListener(v -> num_pm04_1 = changeBgcolor(pm04_1,num_pm04_1)); pm04_2.setOnClickListener(v -> num_pm04_2 = changeBgcolor(pm04_2,num_pm04_2));
        pm04_3.setOnClickListener(v -> num_pm04_3 = changeBgcolor(pm04_3,num_pm04_3)); pm04_4.setOnClickListener(v -> num_pm04_4 = changeBgcolor(pm04_4,num_pm04_4));
        pm04_5.setOnClickListener(v -> num_pm04_5 = changeBgcolor(pm04_5,num_pm04_5)); pm04_6.setOnClickListener(v -> num_pm04_6 = changeBgcolor(pm04_6,num_pm04_6));
        pm05_1.setOnClickListener(v -> num_pm05_1 = changeBgcolor(pm05_1,num_pm05_1)); pm05_2.setOnClickListener(v -> num_pm05_2 = changeBgcolor(pm05_2,num_pm05_2));
        pm05_3.setOnClickListener(v -> num_pm05_3 = changeBgcolor(pm05_3,num_pm05_3)); pm05_4.setOnClickListener(v -> num_pm05_4 = changeBgcolor(pm05_4,num_pm05_4));
        pm05_5.setOnClickListener(v -> num_pm05_5 = changeBgcolor(pm05_5,num_pm05_5)); pm05_6.setOnClickListener(v -> num_pm05_6 = changeBgcolor(pm05_6,num_pm05_6));
        pm06_1.setOnClickListener(v -> num_pm06_1 = changeBgcolor(pm06_1,num_pm06_1)); pm06_2.setOnClickListener(v -> num_pm06_2 = changeBgcolor(pm06_2,num_pm06_2));
        pm06_3.setOnClickListener(v -> num_pm06_3 = changeBgcolor(pm06_3,num_pm06_3)); pm06_4.setOnClickListener(v -> num_pm06_4 = changeBgcolor(pm06_4,num_pm06_4));
        pm06_5.setOnClickListener(v -> num_pm06_5 = changeBgcolor(pm06_5,num_pm06_5)); pm06_6.setOnClickListener(v -> num_pm06_6 = changeBgcolor(pm06_6,num_pm06_6));
        pm07_1.setOnClickListener(v -> num_pm07_1 = changeBgcolor(pm07_1,num_pm07_1)); pm07_2.setOnClickListener(v -> num_pm07_2 = changeBgcolor(pm07_2,num_pm07_2));
        pm07_3.setOnClickListener(v -> num_pm07_3 = changeBgcolor(pm07_3,num_pm07_3)); pm07_4.setOnClickListener(v -> num_pm07_4 = changeBgcolor(pm07_4,num_pm07_4));
        pm07_5.setOnClickListener(v -> num_pm07_5 = changeBgcolor(pm07_5,num_pm07_5)); pm07_6.setOnClickListener(v -> num_pm07_6 = changeBgcolor(pm07_6,num_pm07_6));
        pm08_1.setOnClickListener(v -> num_pm08_1 = changeBgcolor(pm08_1,num_pm08_1)); pm08_2.setOnClickListener(v -> num_pm08_2 = changeBgcolor(pm08_2,num_pm08_2));
        pm08_3.setOnClickListener(v -> num_pm08_3 = changeBgcolor(pm08_3,num_pm08_3)); pm08_4.setOnClickListener(v -> num_pm08_4 = changeBgcolor(pm08_4,num_pm08_4));
        pm08_5.setOnClickListener(v -> num_pm08_5 = changeBgcolor(pm08_5,num_pm08_5)); pm08_6.setOnClickListener(v -> num_pm08_6 = changeBgcolor(pm08_6,num_pm08_6));
        pm09_1.setOnClickListener(v -> num_pm09_1 = changeBgcolor(pm09_1,num_pm09_1)); pm09_2.setOnClickListener(v -> num_pm09_2 = changeBgcolor(pm09_2,num_pm09_2));
        pm09_3.setOnClickListener(v -> num_pm09_3 = changeBgcolor(pm09_3,num_pm09_3)); pm09_4.setOnClickListener(v -> num_pm09_4 = changeBgcolor(pm09_4,num_pm09_4));
        pm09_5.setOnClickListener(v -> num_pm09_5 = changeBgcolor(pm09_5,num_pm09_5)); pm09_6.setOnClickListener(v -> num_pm09_6 = changeBgcolor(pm09_6,num_pm09_6));
        pm10_1.setOnClickListener(v -> num_pm10_1 = changeBgcolor(pm10_1,num_pm10_1)); pm10_2.setOnClickListener(v -> num_pm10_2 = changeBgcolor(pm10_2,num_pm10_2));
        pm10_3.setOnClickListener(v -> num_pm10_3 = changeBgcolor(pm10_3,num_pm10_3)); pm10_4.setOnClickListener(v -> num_pm10_4 = changeBgcolor(pm10_4,num_pm10_4));
        pm10_5.setOnClickListener(v -> num_pm10_5 = changeBgcolor(pm10_5,num_pm10_5)); pm10_6.setOnClickListener(v -> num_pm10_6 = changeBgcolor(pm10_6,num_pm10_6));
        pm11_1.setOnClickListener(v -> num_pm11_1 = changeBgcolor(pm11_1,num_pm11_1)); pm11_2.setOnClickListener(v -> num_pm11_2 = changeBgcolor(pm11_2,num_pm11_2));
        pm11_3.setOnClickListener(v -> num_pm11_3 = changeBgcolor(pm11_3,num_pm11_3)); pm11_4.setOnClickListener(v -> num_pm11_4 = changeBgcolor(pm11_4,num_pm11_4));
        pm11_5.setOnClickListener(v -> num_pm11_5 = changeBgcolor(pm11_5,num_pm11_5)); pm11_6.setOnClickListener(v -> num_pm11_6 = changeBgcolor(pm11_6,num_pm11_6));
        pm12_1.setOnClickListener(v -> num_pm12_1 = changeBgcolor(pm12_1,num_pm12_1)); pm12_2.setOnClickListener(v -> num_pm12_2 = changeBgcolor(pm12_2,num_pm12_2));
        pm12_3.setOnClickListener(v -> num_pm12_3 = changeBgcolor(pm12_3,num_pm12_3)); pm12_4.setOnClickListener(v -> num_pm12_4 = changeBgcolor(pm12_4,num_pm12_4));
        pm12_5.setOnClickListener(v -> num_pm12_5 = changeBgcolor(pm12_5,num_pm12_5)); pm12_6.setOnClickListener(v -> num_pm12_6 = changeBgcolor(pm12_6,num_pm12_6));

        // TODO: 2020-11-20 파이어베이스 연동

        return viewGroup;
    }
    //배경색 바꾸는 함수
    public int changeBgcolor(ImageView imageView, int n){
        if(n==0){
            imageView.setBackgroundColor(Color.parseColor("#BFFFFFFF")); //투명도 75%
            return 1;
        }else{
            imageView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
            return 0;
        }
    }

}
