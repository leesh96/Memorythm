package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class MonthTrackerFragment extends Fragment {
    private TextView textViewDate;
    private EditText et_goal, et_comment;
    private final Button[] btn_day = new Button[31] ;
    private final int[] num_day = new int[31];

    public static MonthTrackerFragment newInstance() {
        return new MonthTrackerFragment();
    }

    // 캘린더 객체 생성
    Calendar myCalendar = Calendar.getInstance();

    //데이트픽커 다이얼로그
    DatePickerDialog.OnDateSetListener myDatePicker = (datePicker, year, month, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.template_monthtracker, container, false);

        textViewDate = viewGroup.findViewById(R.id.write_date);

        String packName = Objects.requireNonNull(this.getActivity()).getPackageName();
        for (int i = 1; i < 32; i++) {
            String name = "btn_day"+i;
            int id = getResources().getIdentifier(name,"id",packName);
            btn_day[i-1]=viewGroup.findViewById(id);
        }

        et_goal = viewGroup.findViewById(R.id.et_goal); et_comment = viewGroup.findViewById(R.id.et_comment);

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        textViewDate.setText(PreferenceManager.getString(getContext(), "currentDate"));

        textViewDate.setOnClickListener(v -> { // 데이트픽커 띄우기
            new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        setBgColor();

        //날짜 체크
        for (int i = 0; i < btn_day.length ; i++) {
            int finalI = i;
            btn_day[i].setOnClickListener(v-> num_day[finalI]=changeBgColor(btn_day[finalI],num_day[finalI]));
        }

        return viewGroup;
    }
    //날짜 체크 함수
    public int changeBgColor(Button button, int n){
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.icon_circle);
        assert drawable != null;
        if(n==0){
            int sum = 0;
            for (int value : num_day) sum += value;
            switch ((sum+1)/10){
                case 0:
                    drawable.setColor(Color.parseColor("#FAED7D"));
                    break;
                case 1:
                    drawable.setColor(Color.parseColor("#CEF279"));
                    break;
                case 2:
                    drawable.setColor(Color.parseColor("#B2CCFF"));
                    break;
                case 3:
                    drawable.setColor(Color.parseColor("#FFA7A7"));
                    break;
            }
            button.setBackground(drawable);
            return 1;
        }else{
            drawable.setColor(Color.parseColor("#00FFA7A7"));
            button.setBackground(drawable);
            return 0;
        }
    }
    public void setBgColor(){
        for (int i = 0; i < num_day.length; i++) {
            if(num_day[i]==1) changeBgColor(btn_day[i],0);
            else changeBgColor(btn_day[i],1);
        }
    }
    public void saveData(){
        //String : date, goal, dayCheck, comment
        String date = textViewDate.getText().toString();
        String goal = et_goal.getText().toString();
        String comment = et_comment.getText().toString();
        StringBuilder dayCheck = new StringBuilder();
        for (int value : num_day) dayCheck.append(value);

        // TODO: 2020-12-29 SQL에 저장
    }
    public void setData(){
        String date=null, goal=null, dayCheck=null, comment=null;
        String[] array;
        // TODO: 2020-12-29 SQL에서 불러오기
        textViewDate.setText(date);
        et_goal.setText(goal);
        et_comment.setText(comment);
        array = dayCheck.split("");
        for (int i = 0; i <array.length ; i++) num_day[i] = Integer.parseInt(array[i]);
    }
}
