package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class HealthTrackerFragment extends Fragment {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private int memoid;
    private TextView textViewDate, tv_upperbody, tv_lowerbody, tv_chest, tv_aerobic;
    private EditText et_breakfast, et_lunch, et_snack, et_dinner, et_exercise, et_comment;
    private ImageView iv_health;
    private final ImageButton[] ibtn_cup = new ImageButton[8];
    private boolean upperbody, lowerbody, chest;
    private int aerobic=0;
    private final int[] cups = new int[8];
    //시계
    private TextView tv_breakfast, tv_lunch, tv_dinner;
    public static HealthTrackerFragment newInstance() {
        return new HealthTrackerFragment();
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.template_healthtracker, container, false);
        dbHelper = new DBHelper(getContext());

        textViewDate = viewGroup.findViewById(R.id.write_date);
        tv_upperbody = viewGroup.findViewById(R.id.tv_upperbody); tv_lowerbody = viewGroup.findViewById(R.id.tv_lowerbody);
        tv_chest = viewGroup.findViewById(R.id.tv_chest); tv_aerobic = viewGroup.findViewById(R.id.tv_aerobic);
        iv_health = viewGroup.findViewById(R.id.iv_health);
        tv_breakfast = viewGroup.findViewById(R.id.tv_breakfast); tv_lunch = viewGroup.findViewById(R.id.tv_lunch); tv_dinner = viewGroup.findViewById(R.id.tv_dinner);
        et_breakfast = viewGroup.findViewById(R.id.et_breakfast); et_lunch = viewGroup.findViewById(R.id.et_lunch);
        et_snack = viewGroup.findViewById(R.id.et_snack); et_dinner = viewGroup.findViewById(R.id.et_dinner);
        et_comment = viewGroup.findViewById(R.id.et_comment); et_exercise = viewGroup.findViewById(R.id.et_exercise);
        String packName = Objects.requireNonNull(this.getActivity()).getPackageName();
        for (int i = 1; i < 9; i++) {
            String name = "ibtn_cup"+i;
            int id = getResources().getIdentifier(name,"id",packName);
            ibtn_cup[i-1]=viewGroup.findViewById(id);
        }
        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        textViewDate.setText(PreferenceManager.getString(getContext(), "currentDate"));

        textViewDate.setOnClickListener(v -> { // 데이트픽커 띄우기
            new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        // 시계
        tv_breakfast.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,
                    (view, hourOfDay, minute) -> {
                        String hour = changeTime(hourOfDay);
                        String min = changeTime(minute);
                        String string = hour+":"+min;
                        tv_breakfast.setText(string);
                    },8,0,true); timePickerDialog.show();
        });
        tv_lunch.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,
                    (view, hourOfDay, minute) -> {
                        String hour = changeTime(hourOfDay);
                        String min = changeTime(minute);
                        String string = hour+":"+min;
                        tv_lunch.setText(string);
                    },12,0,true); timePickerDialog.show();
        });
        tv_dinner.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,
                    (view, hourOfDay, minute) -> {
                        String hour = changeTime(hourOfDay);
                        String min = changeTime(minute);
                        String string = hour+":"+min;
                        tv_dinner.setText(string);
                    },18,0,true); timePickerDialog.show();
        });
        // 운동
        tv_upperbody.setOnClickListener(v -> {
            upperbody = !upperbody;
            setHealth(iv_health, getExercise(), aerobic);
        });
        tv_lowerbody.setOnClickListener(v -> {
            lowerbody = !lowerbody;
            setHealth(iv_health, getExercise(), aerobic);
        });
        tv_chest.setOnClickListener(v -> {
            chest = !chest;
            setHealth(iv_health, getExercise(), aerobic);
        });
        tv_aerobic.setOnClickListener(v -> {
            if(aerobic==0) aerobic = 1;
            else aerobic = 0;
            setHealth(iv_health, getExercise(), aerobic);
        });

        for (int i = 0; i <ibtn_cup.length ; i++) {
            int finalI = i;
            ibtn_cup[i].setOnClickListener(v -> cups[finalI]= changeCups(ibtn_cup[finalI], cups[finalI]));
        }
        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setData();
    }

    public int getMemoid() {
        return memoid;
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
        if(n<10) return "0"+ n;
        else return Integer.toString(n);
    }
    public int changeCups(ImageButton imageButton, int n){
        if(n==0){
            imageButton.setImageResource(R.drawable.icon_fillcup);
            return 1;
        }else {
            imageButton.setImageResource(R.drawable.icon_blankcup);
            return 0;
        }
    }
    public void setCups(){
        for (int i = 0; i <cups.length ; i++) {
            if(cups[i]==1) changeCups(ibtn_cup[i],0);
            else changeCups(ibtn_cup[i],1);
        }
    }
    public Boolean saveData(String Mode, String Bgcolor, String title){
        //string : userdate, breakfastTime, breakfastMenu, lunchTime, lunchMenu, snackMenu , dinnerTime, dinnerMenu, exerciseContent, comment
        //int : exercisedata, aerobicdata
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String userdate = textViewDate.getText().toString();
        String breakfastTime = tv_breakfast.getText().toString();
        String lunchTime = tv_lunch.getText().toString();
        String dinnerTime = tv_dinner.getText().toString();
        String breakfastMenu = et_breakfast.getText().toString().replaceAll("'", "''");
        String lunchMenu = et_lunch.getText().toString().replaceAll("'", "''");
        String snackMenu = et_snack.getText().toString().replaceAll("'", "''");
        String dinnerMenu = et_dinner.getText().toString().replaceAll("'", "''");
        String exerciseContent = et_exercise.getText().toString().replaceAll("'", "''");
        String comment = et_comment.getText().toString().replaceAll("'", "''");
        StringBuilder waterCups = new StringBuilder();
        for (int value : cups) waterCups.append(value);
        int exercisedata = getExercise();
        int aerobicdata = aerobic;
        db = dbHelper.getReadableDatabase();
        switch (Mode) {
            case "write":
                db.execSQL("INSERT INTO healthtracker('userdate', 'waterCups', 'breakfastTime', 'breakfastMenu', 'lunchTime', 'lunchMenu', 'snackMenu', 'dinnerTime', 'dinnerMenu', 'exerciseContent', 'comment',  'exercise', 'aerobic', 'bgcolor', 'title') " +
                        "VALUES('" + userdate + "', '"+ waterCups +"', '" + breakfastTime + "', '" + breakfastMenu + "', '" + lunchTime + "', '" + lunchMenu + "', '" + snackMenu + "', '" + dinnerTime + "', '" + dinnerMenu + "', '" + exerciseContent + "', '" + comment + "', '" + exercisedata + "', '" + aerobicdata + "', '"+Bgcolor+"', '"+title+"');");
                final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                cursor.moveToFirst();
                memoid = cursor.getInt(0);
                db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '메모';");
                break;
            case "view":
                if (getArguments() != null) {
                    memoid = getArguments().getInt("memoid");
                }
                db.execSQL("UPDATE healthtracker SET userdate = '"+userdate+"', waterCups = '"+waterCups+"', breakfastTime = '"+breakfastTime+"', breakfastMenu = '"+breakfastMenu+"', lunchTime = '"+lunchTime+"', " +
                        "lunchMenu = '"+lunchMenu+"', snackMenu = '"+snackMenu+"', dinnerTime = '"+dinnerTime+"', dinnerMenu = '"+dinnerMenu+"', exerciseContent = '"+exerciseContent+"', comment = '"+comment+"', exercise = '"+exercisedata+"', aerobic = '"+aerobicdata+"'" +
                        "editdate = '"+dateFormat.format(date.getTime()) + "' WHERE id = "+memoid+";");
                break;
        }
        return true;
    }
    public void setData(){
        String userdate=null, breakfastTime=null, breakfastMenu=null, lunchTime=null, lunchMenu=null, snackMenu=null, dinnerTime=null, dinnerMenu=null, exerciseContent=null, comment=null, waterCups=null;
        int exerciseData =0, aerobicData =0;
        String[] array;

        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();
        if (getArguments() != null) {
            memoid = getArguments().getInt("memoid");
            Cursor cursor = db.rawQuery("SELECT userdate, breakfastTime, breakfastMenu, lunchTime, lunchMenu, snackMenu , dinnerTime, dinnerMenu, exerciseContent, comment, exercise, aerobic, waterCups  FROM healthtracker WHERE id = "+memoid+"", null);
            while (cursor.moveToNext()) {
                userdate = cursor.getString(0); breakfastTime = cursor.getString(1); breakfastMenu = cursor.getString(2); lunchTime = cursor.getString(3); lunchMenu = cursor.getString(4);
                snackMenu = cursor.getString(5); dinnerTime = cursor.getString(6); dinnerMenu = cursor.getString(7); exerciseContent = cursor.getString(8); comment = cursor.getString(9);
                exerciseData = cursor.getInt(10); aerobicData = cursor.getInt(11); waterCups = cursor.getString(12);
            }
            textViewDate.setText(userdate);
            tv_breakfast.setText(breakfastTime); tv_lunch.setText(lunchTime); tv_dinner.setText(dinnerTime);
            et_breakfast.setText(breakfastMenu); et_lunch.setText(lunchMenu); et_snack.setText(snackMenu); et_dinner.setText(dinnerMenu);
            et_exercise.setText(exerciseContent); et_comment.setText(comment);
            setHealth(iv_health, exerciseData, aerobicData);
            array = waterCups.split("");
            for (int i = 0; i <array.length ; i++) cups[i] = Integer.parseInt(array[i]);
            setCups();
            // 데이트픽커 다이얼로그에 userdate로 뜨게 하는 코드
            String toDate = textViewDate.getText().toString();
            SimpleDateFormat stringtodate = new SimpleDateFormat("yyyy - MM - dd");
            try {
                Date fromString = stringtodate.parse(toDate);
                myCalendar.setTime(fromString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // 데이트픽커 띄우기
            textViewDate.setOnClickListener(v -> {
                // 뷰 모드면 날짜 맞게 해줘야댐
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            });
        }

    }
}
