package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HealthTrackerFragment extends Fragment {
    private TextView textViewDate, tv_upperbody, tv_lowerbody, tv_chest, tv_aerobic;
    private EditText et_breakfast, et_lunch, et_snack, et_dinner, et_exercise, et_comment;
    private ImageView iv_health;
    private ImageButton ibtn_cup1,ibtn_cup2,ibtn_cup3,ibtn_cup4,ibtn_cup5,ibtn_cup6,ibtn_cup7,ibtn_cup8;
    private boolean upperbody, lowerbody, chest;
    private int aerobic=0;
    private int cup1, cup2, cup3, cup4, cup5, cup6, cup7, cup8;
    //시계
    private TextView tv_breakfast, tv_lunch, tv_dinner;
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
        tv_upperbody = viewGroup.findViewById(R.id.tv_upperbody);
        tv_lowerbody = viewGroup.findViewById(R.id.tv_lowerbody);
        tv_chest = viewGroup.findViewById(R.id.tv_chest);
        tv_aerobic = viewGroup.findViewById(R.id.tv_aerobic);
        iv_health = viewGroup.findViewById(R.id.iv_health);
        tv_breakfast = viewGroup.findViewById(R.id.tv_breakfast);
        tv_lunch = viewGroup.findViewById(R.id.tv_lunch);
        tv_dinner = viewGroup.findViewById(R.id.tv_dinner);
        et_breakfast = viewGroup.findViewById(R.id.et_breakfast);
        et_lunch = viewGroup.findViewById(R.id.et_lunch);
        et_snack = viewGroup.findViewById(R.id.et_snack);
        et_dinner = viewGroup.findViewById(R.id.et_dinner);
        et_comment = viewGroup.findViewById(R.id.et_comment);
        et_exercise = viewGroup.findViewById(R.id.et_exercise);

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy - MM - dd", Locale.KOREA);
        textViewDate.setText(simpleDateFormat.format(currentTime));

        textViewDate.setOnClickListener(v -> { // 데이트픽커 띄우기
            new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        // 시계
        tv_breakfast.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,
                    new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hour = changeTime(hourOfDay);
                    String min = changeTime(minute);
                    tv_breakfast.setText(hour+":"+min);
                }
            },8,0,true); timePickerDialog.show();
        });
        tv_lunch.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String hour = changeTime(hourOfDay);
                            String min = changeTime(minute);
                            tv_lunch.setText(hour+":"+min);
                        }
                    },12,0,true); timePickerDialog.show();
        });
        tv_dinner.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String hour = changeTime(hourOfDay);
                            String min = changeTime(minute);
                            tv_dinner.setText(hour+":"+min);
                        }
                    },18,0,true); timePickerDialog.show();
        });
        // 운동
        tv_upperbody.setOnClickListener(v -> {
            if(!upperbody) upperbody = true;
            else upperbody = false;
            setHealth(iv_health, getExercise(), aerobic);
        });
        tv_lowerbody.setOnClickListener(v -> {
            if(!lowerbody)lowerbody = true;
            else lowerbody = false;
            setHealth(iv_health, getExercise(), aerobic);
        });
        tv_chest.setOnClickListener(v -> {
            if(!chest) chest = true;
            else chest = false;
            setHealth(iv_health, getExercise(), aerobic);
        });
        tv_aerobic.setOnClickListener(v -> {
            if(aerobic==0) aerobic = 1;
            else aerobic = 0;
            setHealth(iv_health, getExercise(), aerobic);
        });

        // 물컵
        ibtn_cup1.setOnClickListener(v -> {cup1 = setCup(ibtn_cup1,cup1);});
        ibtn_cup2.setOnClickListener(v -> {cup2 = setCup(ibtn_cup2,cup2);});
        ibtn_cup3.setOnClickListener(v -> {cup3 = setCup(ibtn_cup3,cup3);});
        ibtn_cup4.setOnClickListener(v -> {cup4 = setCup(ibtn_cup4,cup4);});
        ibtn_cup5.setOnClickListener(v -> {cup5 = setCup(ibtn_cup5,cup5);});
        ibtn_cup6.setOnClickListener(v -> {cup6 = setCup(ibtn_cup6,cup6);});
        ibtn_cup7.setOnClickListener(v -> {cup7 = setCup(ibtn_cup7,cup7);});
        ibtn_cup8.setOnClickListener(v -> {cup8 = setCup(ibtn_cup8,cup8);});


        return viewGroup;
    }
    public int getExercise(){
        if(upperbody&&lowerbody&&chest) return 7;
        else if(upperbody&&lowerbody) return 6;
        else if(upperbody&&chest) return 5;
        else if(lowerbody&&chest) return 4;
        else if(upperbody) return 3;
        else if(lowerbody) return 2;
        else if(chest) return 1;
        else return 0;
    }
    public void setHealth(ImageView imageView, int doExercise, int doAerobic){
        switch (doExercise){
            case 0:
                upperbody=false; lowerbody=false; chest=false;
                imageView.setImageResource(R.drawable.icon_exerciseblank);
                break;
            case 1:
                upperbody=false; lowerbody=false; chest=true;
                imageView.setImageResource(R.drawable.icon_chest);
                break;
            case 2:
                upperbody=false; lowerbody=true; chest=false;
                imageView.setImageResource(R.drawable.icon_lower);
                break;
            case 3:
                upperbody=true; lowerbody=false; chest=false;
                imageView.setImageResource(R.drawable.icon_upper);
                break;
            case 4:
                upperbody=false; lowerbody=true; chest=true;
                imageView.setImageResource(R.drawable.icon_lowchest);
                break;
            case 5:
                upperbody=true; lowerbody=false; chest=true;
                imageView.setImageResource(R.drawable.icon_upchest);
                break;
            case 6:
                upperbody=true; lowerbody=true; chest=false;
                imageView.setImageResource(R.drawable.icon_uplower);
                break;
            case 7:
                upperbody=true; lowerbody=true; chest=true;
                imageView.setImageResource(R.drawable.icon_exerciseall);
                break;
        }
        if(upperbody) tv_upperbody.setBackgroundColor(Color.parseColor("#FAED7D"));
        else tv_upperbody.setBackgroundColor(Color.parseColor("#00FAED7D"));
        if(lowerbody) tv_lowerbody.setBackgroundColor(Color.parseColor("#FAED7D"));
        else tv_lowerbody.setBackgroundColor(Color.parseColor("#00FAED7D"));
        if(chest) tv_chest.setBackgroundColor(Color.parseColor("#FAED7D"));
        else tv_chest.setBackgroundColor(Color.parseColor("#00FAED7D"));
        if(doAerobic==0) tv_aerobic.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        else tv_aerobic.setBackgroundColor(Color.parseColor("#FAED7D"));
        aerobic=doAerobic;
    }
    public String changeTime(int n){
        if(n<10) return "0"+Integer.toString(n);
        else return Integer.toString(n);
    }
    public int setCup(ImageButton imageButton, int n){
        if(n==0){
            imageButton.setImageResource(R.drawable.icon_fillcup);
            return 1;
        }else {
            ibtn_cup8.setImageResource(R.drawable.icon_blankcup);
            return 0;
        }
    }
    public void saveData(){
        //string : date, breakfastTime, breakfastMenu, lunchTime, lunchMenu, snackMenu , dinnerTime, dinnerMenu, exerciseContent, comment
        //int : waterCnt, exercisedata, aerobicdata
        String date = textViewDate.getText().toString();
        String breakfastTime = tv_breakfast.getText().toString();
        String lunchTime = tv_lunch.getText().toString();
        String dinnerTime = tv_dinner.getText().toString();
        String breakfastMenu = et_breakfast.getText().toString();
        String lunchMenu = et_lunch.getText().toString();
        String snackMenu = et_snack.getText().toString();
        String dinnerMenu = et_dinner.getText().toString();
        String exerciseContent = et_exercise.getText().toString();
        String comment = et_comment.getText().toString();
        int waterCnt = cup1 + cup2 + cup3 + cup4 + cup5 + cup6 + cup7 + cup8;
        int exercisedata = getExercise();
        int aerobicdata = aerobic;
        // TODO: 2020-12-29 SQL에 저장
    }
    public void setData(){
        String date=null, breakfastTime=null, breakfastMenu=null, lunchTime=null, lunchMenu=null, snackMenu=null, dinnerTime=null, dinnerMenu=null, exerciseContent=null, comment=null;
        int waterCnt=0, exercisedata=0, aerobicdata=0;
        // TODO: 2020-12-29 SQL에서 불러오기
        textViewDate.setText(date);
        tv_breakfast.setText(breakfastTime); tv_lunch.setText(lunchTime); tv_dinner.setText(dinnerTime);
        et_breakfast.setText(breakfastMenu); et_lunch.setText(lunchMenu); et_snack.setText(snackMenu); et_dinner.setText(dinnerMenu);
        et_exercise.setText(exerciseContent); et_comment.setText(comment);
        setHealth(iv_health, exercisedata, aerobicdata);
    }
}
