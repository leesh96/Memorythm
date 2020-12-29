package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MonthTrackerFragment extends Fragment {
    private TextView textViewDate;
    private EditText et_goal, et_comment;
    private Button btn_day1, btn_day2, btn_day3, btn_day4, btn_day5, btn_day6, btn_day7, btn_day8, btn_day9, btn_day10,
            btn_day11, btn_day12, btn_day13, btn_day14, btn_day15, btn_day16, btn_day17, btn_day18, btn_day19, btn_day20,
            btn_day21, btn_day22, btn_day23, btn_day24, btn_day25, btn_day26, btn_day27, btn_day28, btn_day29, btn_day30, btn_day31;
    private int num_day[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    public static MonthTrackerFragment newInstance() {
        return new MonthTrackerFragment();
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.template_monthtracker, container, false);

        textViewDate = viewGroup.findViewById(R.id.write_date);
        btn_day1 = viewGroup.findViewById(R.id.btn_day1); btn_day2 = viewGroup.findViewById(R.id.btn_day2); btn_day3 = viewGroup.findViewById(R.id.btn_day3);
        btn_day4 = viewGroup.findViewById(R.id.btn_day4); btn_day5 = viewGroup.findViewById(R.id.btn_day5); btn_day6 = viewGroup.findViewById(R.id.btn_day6);
        btn_day7 = viewGroup.findViewById(R.id.btn_day7); btn_day8 = viewGroup.findViewById(R.id.btn_day8); btn_day9 = viewGroup.findViewById(R.id.btn_day9);
        btn_day10 = viewGroup.findViewById(R.id.btn_day10); btn_day11 = viewGroup.findViewById(R.id.btn_day11); btn_day12 = viewGroup.findViewById(R.id.btn_day12);
        btn_day13 = viewGroup.findViewById(R.id.btn_day13); btn_day14 = viewGroup.findViewById(R.id.btn_day14); btn_day15 = viewGroup.findViewById(R.id.btn_day15);
        btn_day16 = viewGroup.findViewById(R.id.btn_day16); btn_day17 = viewGroup.findViewById(R.id.btn_day17); btn_day18 = viewGroup.findViewById(R.id.btn_day18);
        btn_day19 = viewGroup.findViewById(R.id.btn_day19); btn_day20 = viewGroup.findViewById(R.id.btn_day20); btn_day21 = viewGroup.findViewById(R.id.btn_day21);
        btn_day22 = viewGroup.findViewById(R.id.btn_day22); btn_day23 = viewGroup.findViewById(R.id.btn_day23); btn_day24 = viewGroup.findViewById(R.id.btn_day24);
        btn_day25 = viewGroup.findViewById(R.id.btn_day25); btn_day26 = viewGroup.findViewById(R.id.btn_day26); btn_day27 = viewGroup.findViewById(R.id.btn_day27);
        btn_day28 = viewGroup.findViewById(R.id.btn_day28); btn_day29 = viewGroup.findViewById(R.id.btn_day29); btn_day30 = viewGroup.findViewById(R.id.btn_day30); btn_day31 = viewGroup.findViewById(R.id.btn_day31);
        et_goal = viewGroup.findViewById(R.id.et_goal); et_comment = viewGroup.findViewById(R.id.et_comment);

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy - MM", Locale.KOREA);
        textViewDate.setText(simpleDateFormat.format(currentTime));

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 데이트픽커 띄우기
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //날짜 체크
        btn_day1.setOnClickListener(v -> { num_day[0] = changeBgcolor(btn_day1, num_day[0]); }); btn_day2.setOnClickListener(v -> { num_day[1] = changeBgcolor(btn_day2, num_day[1]); });
        btn_day3.setOnClickListener(v -> { num_day[2] = changeBgcolor(btn_day3, num_day[2]); }); btn_day4.setOnClickListener(v -> { num_day[3] = changeBgcolor(btn_day4, num_day[3]); });
        btn_day5.setOnClickListener(v -> { num_day[4] = changeBgcolor(btn_day5, num_day[4]); }); btn_day6.setOnClickListener(v -> { num_day[5] = changeBgcolor(btn_day6, num_day[5]); });
        btn_day7.setOnClickListener(v -> { num_day[6] = changeBgcolor(btn_day7, num_day[6]); }); btn_day8.setOnClickListener(v -> { num_day[7] = changeBgcolor(btn_day8, num_day[7]); });
        btn_day9.setOnClickListener(v -> { num_day[8] = changeBgcolor(btn_day9, num_day[8]); }); btn_day10.setOnClickListener(v -> { num_day[9] = changeBgcolor(btn_day10, num_day[9]); });
        btn_day11.setOnClickListener(v -> { num_day[10] = changeBgcolor(btn_day11, num_day[10]); }); btn_day12.setOnClickListener(v -> { num_day[11] = changeBgcolor(btn_day12, num_day[11]); });
        btn_day13.setOnClickListener(v -> { num_day[12] = changeBgcolor(btn_day13, num_day[12]); }); btn_day14.setOnClickListener(v -> { num_day[13] = changeBgcolor(btn_day14, num_day[13]); });
        btn_day15.setOnClickListener(v -> { num_day[14] = changeBgcolor(btn_day15, num_day[14]); }); btn_day16.setOnClickListener(v -> { num_day[15] = changeBgcolor(btn_day16, num_day[15]); });
        btn_day17.setOnClickListener(v -> { num_day[16] = changeBgcolor(btn_day17, num_day[16]); }); btn_day18.setOnClickListener(v -> { num_day[17] = changeBgcolor(btn_day18, num_day[17]); });
        btn_day19.setOnClickListener(v -> { num_day[18] = changeBgcolor(btn_day19, num_day[18]); }); btn_day20.setOnClickListener(v -> { num_day[19] = changeBgcolor(btn_day20, num_day[19]); });
        btn_day21.setOnClickListener(v -> { num_day[20] = changeBgcolor(btn_day21, num_day[20]); }); btn_day22.setOnClickListener(v -> { num_day[21] = changeBgcolor(btn_day22, num_day[21]); });
        btn_day23.setOnClickListener(v -> { num_day[22] = changeBgcolor(btn_day23, num_day[22]); }); btn_day24.setOnClickListener(v -> { num_day[23] = changeBgcolor(btn_day24, num_day[23]); });
        btn_day25.setOnClickListener(v -> { num_day[24] = changeBgcolor(btn_day25, num_day[24]); }); btn_day26.setOnClickListener(v -> { num_day[25] = changeBgcolor(btn_day26, num_day[25]); });
        btn_day27.setOnClickListener(v -> { num_day[26] = changeBgcolor(btn_day27, num_day[26]); }); btn_day28.setOnClickListener(v -> { num_day[27] = changeBgcolor(btn_day28, num_day[27]); });
        btn_day29.setOnClickListener(v -> { num_day[28] = changeBgcolor(btn_day29, num_day[28]); }); btn_day30.setOnClickListener(v -> { num_day[29] = changeBgcolor(btn_day30, num_day[29]); });
        btn_day31.setOnClickListener(v -> { num_day[30] = changeBgcolor(btn_day31, num_day[30]); });

        return viewGroup;
    }
    //날짜 체크 함수
    public int changeBgcolor(Button button, int n){
        final GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.icon_circle);
        if(n==0){
            int sum = 0;
            for (int i = 0; i <num_day.length ; i++) sum += num_day[i];

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
    public void setBgcolor(){
        //if(num_day[0]==1) btn_day1.setBackground(Color.parseColor("#FAED7D"));
    }
    public void saveData(){
        //String : date, goal, dayCheck, comment
        String date = textViewDate.getText().toString();
        String goal = et_goal.getText().toString();
        String comment = et_comment.getText().toString();
        String dayCheck = "";
        for (int i = 0; i <num_day.length ; i++) dayCheck+=Integer.toString(num_day[i]);

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
