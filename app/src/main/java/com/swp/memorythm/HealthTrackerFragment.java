package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HealthTrackerFragment extends Fragment {
    private TextView textViewDate;
    private ImageButton ibtn_cup1,ibtn_cup2,ibtn_cup3,ibtn_cup4,ibtn_cup5,ibtn_cup6,ibtn_cup7,ibtn_cup8;

    private int cup1, cup2, cup3, cup4, cup5, cup6, cup7, cup8;
    public static HealthTrackerFragment newInstance() {
        return new HealthTrackerFragment();
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.template_healthtracker, container, false);

        textViewDate = viewGroup.findViewById(R.id.write_date);
        ibtn_cup1 = viewGroup.findViewById(R.id.ibtn_cup1);
        ibtn_cup2 = viewGroup.findViewById(R.id.ibtn_cup2);
        ibtn_cup3 = viewGroup.findViewById(R.id.ibtn_cup3);
        ibtn_cup4 = viewGroup.findViewById(R.id.ibtn_cup4);
        ibtn_cup5 = viewGroup.findViewById(R.id.ibtn_cup5);
        ibtn_cup6 = viewGroup.findViewById(R.id.ibtn_cup6);
        ibtn_cup7 = viewGroup.findViewById(R.id.ibtn_cup7);
        ibtn_cup8 = viewGroup.findViewById(R.id.ibtn_cup8);

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy - MM - dd", Locale.KOREA);
        textViewDate.setText(simpleDateFormat.format(currentTime));

        textViewDate.setOnClickListener(v -> { // 데이트픽커 띄우기
            new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // 물컵
        int nweek = myCalendar.get(Calendar.DAY_OF_WEEK); //요일 구하기
        if(PreferenceManager.getInt(getContext(),"nweek")!=nweek) {
            PreferenceManager.setInt(getContext(), "cup1", 0);
            PreferenceManager.setInt(getContext(), "cup2", 0);
            PreferenceManager.setInt(getContext(), "cup3", 0);
            PreferenceManager.setInt(getContext(), "cup4", 0);
            PreferenceManager.setInt(getContext(), "cup5", 0);
            PreferenceManager.setInt(getContext(), "cup6", 0);
            PreferenceManager.setInt(getContext(), "cup7", 0);
            PreferenceManager.setInt(getContext(), "cup8", 0);
            PreferenceManager.setInt(getContext(),"nweek",nweek);
        }
        cup1 = PreferenceManager.getInt(getContext(),"cup1");
        cup2 = PreferenceManager.getInt(getContext(),"cup2");
        cup3 = PreferenceManager.getInt(getContext(),"cup3");
        cup4 = PreferenceManager.getInt(getContext(),"cup4");
        cup5 = PreferenceManager.getInt(getContext(),"cup5");
        cup6 = PreferenceManager.getInt(getContext(),"cup6");
        cup7 = PreferenceManager.getInt(getContext(),"cup7");
        cup8 = PreferenceManager.getInt(getContext(),"cup8");
        setCup(ibtn_cup1,cup1); setCup(ibtn_cup2,cup2); setCup(ibtn_cup3,cup3); setCup(ibtn_cup4,cup4);
        setCup(ibtn_cup5,cup5); setCup(ibtn_cup6,cup6); setCup(ibtn_cup7,cup7); setCup(ibtn_cup8,cup8);
        ibtn_cup1.setOnClickListener(v -> {
            if(cup1 == 0 ){
                PreferenceManager.setInt(getContext(), "cup1", 1);
                cup1 = 1;
                ibtn_cup1.setImageResource(R.drawable.icon_fillcup);
            }else{
                PreferenceManager.setInt(getContext(), "cup1", 0);
                cup1 = 0;
                ibtn_cup1.setImageResource(R.drawable.icon_blankcup);
            }
        });
        ibtn_cup2.setOnClickListener(v -> {
            if(cup2 == 0 ){
                PreferenceManager.setInt(getContext(), "cup2", 1);
                cup2 = 1;
                ibtn_cup2.setImageResource(R.drawable.icon_fillcup);
            }else{
                PreferenceManager.setInt(getContext(), "cup2", 0);
                cup2 = 0;
                ibtn_cup2.setImageResource(R.drawable.icon_blankcup);
            }
        });
        ibtn_cup3.setOnClickListener(v -> {
            if(cup3 == 0 ){
                PreferenceManager.setInt(getContext(), "cup3", 1);
                cup3 = 1;
                ibtn_cup3.setImageResource(R.drawable.icon_fillcup);
            }else{
                PreferenceManager.setInt(getContext(), "cup3", 0);
                cup3 = 0;
                ibtn_cup3.setImageResource(R.drawable.icon_blankcup);
            }
        });
        ibtn_cup4.setOnClickListener(v -> {
            if(cup4 == 0 ){
                PreferenceManager.setInt(getContext(), "cup4", 1);
                cup4 = 1;
                ibtn_cup4.setImageResource(R.drawable.icon_fillcup);
            }else{
                PreferenceManager.setInt(getContext(), "cup4", 0);
                cup4 = 0;
                ibtn_cup4.setImageResource(R.drawable.icon_blankcup);
            }
        });
        ibtn_cup5.setOnClickListener(v -> {
            if(cup5 == 0 ){
                PreferenceManager.setInt(getContext(), "cup5", 1);
                cup5 = 1;
                ibtn_cup5.setImageResource(R.drawable.icon_fillcup);
            }else{
                PreferenceManager.setInt(getContext(), "cup5", 0);
                cup5 = 0;
                ibtn_cup5.setImageResource(R.drawable.icon_blankcup);
            }
        });
        ibtn_cup6.setOnClickListener(v -> {
            if(cup6 == 0 ){
                PreferenceManager.setInt(getContext(), "cup6", 1);
                cup6 = 1;
                ibtn_cup6.setImageResource(R.drawable.icon_fillcup);
            }else{
                PreferenceManager.setInt(getContext(), "cup6", 0);
                cup6 = 0;
                ibtn_cup6.setImageResource(R.drawable.icon_blankcup);
            }
        });
        ibtn_cup7.setOnClickListener(v -> {
            if(cup7 == 0 ){
                PreferenceManager.setInt(getContext(), "cup7", 1);
                cup7 = 1;
                ibtn_cup7.setImageResource(R.drawable.icon_fillcup);
            }else{
                PreferenceManager.setInt(getContext(), "cup7", 0);
                cup7 = 0;
                ibtn_cup7.setImageResource(R.drawable.icon_blankcup);
            }
        });
        ibtn_cup8.setOnClickListener(v -> {
            if(cup8 == 0 ){
                PreferenceManager.setInt(getContext(), "cup8", 1);
                cup8 = 1;
                ibtn_cup8.setImageResource(R.drawable.icon_fillcup);
            }else{
                PreferenceManager.setInt(getContext(), "cup8", 0);
                cup8 = 0;
                ibtn_cup8.setImageResource(R.drawable.icon_blankcup);
            }
        });


        // TODO: 2020-11-20 파이어베이스 연동

        return viewGroup;
    }
    public void setCup(ImageButton imageButton, int n){
        if(n==1) imageButton.setImageResource(R.drawable.icon_fillcup);
        else imageButton.setImageResource(R.drawable.icon_blankcup);
    }
}
