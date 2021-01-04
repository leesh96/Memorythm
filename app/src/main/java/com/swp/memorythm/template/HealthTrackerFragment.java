package com.swp.memorythm.template;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
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

import com.swp.memorythm.CommonUtils;
import com.swp.memorythm.DBHelper;
import com.swp.memorythm.PreferenceManager;
import com.swp.memorythm.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class HealthTrackerFragment extends Fragment {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private int memoID, aerobic;
    private TextView textViewDate, tv_upperBody, tv_lowerBody, tv_chest, tv_aerobic;
    private EditText et_breakfast, et_lunch, et_snack, et_dinner, et_exercise, et_comment;
    private ImageView iv_health;
    private final ImageButton[] iBtn_cup = new ImageButton[8];
    private boolean upperBody, lowerBody, chest, fromFixedFragment;
    private final int[] cups = new int[8];
    private TextView tv_breakfast, tv_lunch, tv_dinner;

    public static HealthTrackerFragment newInstance() {
        return new HealthTrackerFragment();
    }


    public boolean isFromFixedFragment() {
        return fromFixedFragment;
    }

    public void setFromFixedFragment(boolean fromFixedFragment) {
        this.fromFixedFragment = fromFixedFragment;
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
        tv_upperBody = viewGroup.findViewById(R.id.tv_upperbody);
        tv_lowerBody = viewGroup.findViewById(R.id.tv_lowerbody);
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
        String packName = Objects.requireNonNull(this.getActivity()).getPackageName();
        for (int i = 1; i < 9; i++) {
            String name = "ibtn_cup" + i;
            int id = getResources().getIdentifier(name, "id", packName);
            iBtn_cup[i - 1] = viewGroup.findViewById(id);
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
                        String string = hour + ":" + min;
                        tv_breakfast.setText(string);
                    }, 8, 0, true);
            timePickerDialog.show();
        });
        tv_lunch.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,
                    (view, hourOfDay, minute) -> {
                        String hour = changeTime(hourOfDay);
                        String min = changeTime(minute);
                        String string = hour + ":" + min;
                        tv_lunch.setText(string);
                    }, 12, 0, true);
            timePickerDialog.show();
        });
        tv_dinner.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog,
                    (view, hourOfDay, minute) -> {
                        String hour = changeTime(hourOfDay);
                        String min = changeTime(minute);
                        String string = hour + ":" + min;
                        tv_dinner.setText(string);
                    }, 18, 0, true);
            timePickerDialog.show();
        });
        // 운동
        tv_upperBody.setOnClickListener(v -> {
            upperBody = !upperBody;
            setHealth(iv_health, getExercise(), aerobic);
        });
        tv_lowerBody.setOnClickListener(v -> {
            lowerBody = !lowerBody;
            setHealth(iv_health, getExercise(), aerobic);
        });
        tv_chest.setOnClickListener(v -> {
            chest = !chest;
            setHealth(iv_health, getExercise(), aerobic);
        });
        tv_aerobic.setOnClickListener(v -> {
            if (aerobic == 0) aerobic = 1;
            else aerobic = 0;
            setHealth(iv_health, getExercise(), aerobic);
        });

        for (int i = 0; i < iBtn_cup.length; i++) {
            int finalI = i;
            iBtn_cup[i].setOnClickListener(v -> cups[finalI] = changeCups(iBtn_cup[finalI], cups[finalI]));
        }

        View activityRootView = viewGroup.findViewById(R.id.parentLayout);
        // 수정 불가하게 만들기
        if (isFromFixedFragment()) CommonUtils.setTouchable(activityRootView);
        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setData();
    }

    public int getMemoid() {
        return memoID;
    }

    public int getExercise() {
        if (upperBody && lowerBody && chest) return 7;
        else if (upperBody && lowerBody) return 6;
        else if (upperBody && chest) return 5;
        else if (lowerBody && chest) return 4;
        else if (upperBody) return 3;
        else if (lowerBody) return 2;
        else if (chest) return 1;
        else return 0;
    }

    public void setHealth(ImageView imageView, int doExercise, int doAerobic) {
        switch (doExercise) {
            case 0:
                upperBody = false;
                lowerBody = false;
                chest = false;
                imageView.setImageResource(R.drawable.icon_exerciseblank);
                break;
            case 1:
                upperBody = false;
                lowerBody = false;
                chest = true;
                imageView.setImageResource(R.drawable.icon_chest);
                break;
            case 2:
                upperBody = false;
                lowerBody = true;
                chest = false;
                imageView.setImageResource(R.drawable.icon_lower);
                break;
            case 3:
                upperBody = true;
                lowerBody = false;
                chest = false;
                imageView.setImageResource(R.drawable.icon_upper);
                break;
            case 4:
                upperBody = false;
                lowerBody = true;
                chest = true;
                imageView.setImageResource(R.drawable.icon_lowchest);
                break;
            case 5:
                upperBody = true;
                lowerBody = false;
                chest = true;
                imageView.setImageResource(R.drawable.icon_upchest);
                break;
            case 6:
                upperBody = true;
                lowerBody = true;
                chest = false;
                imageView.setImageResource(R.drawable.icon_uplower);
                break;
            case 7:
                upperBody = true;
                lowerBody = true;
                chest = true;
                imageView.setImageResource(R.drawable.icon_exerciseall);
                break;
        }
        if (upperBody) tv_upperBody.setBackgroundColor(Color.parseColor("#FAED7D"));
        else tv_upperBody.setBackgroundColor(Color.TRANSPARENT);
        if (lowerBody) tv_lowerBody.setBackgroundColor(Color.parseColor("#FAED7D"));
        else tv_lowerBody.setBackgroundColor(Color.TRANSPARENT);
        if (chest) tv_chest.setBackgroundColor(Color.parseColor("#FAED7D"));
        else tv_chest.setBackgroundColor(Color.TRANSPARENT);
        if (doAerobic == 0) tv_aerobic.setBackgroundColor(Color.TRANSPARENT);
        else tv_aerobic.setBackgroundColor(Color.parseColor("#FAED7D"));
        aerobic = doAerobic;
    }

    public String changeTime(int n) {
        if (n < 10) return "0" + n;
        else return Integer.toString(n);
    }

    public int changeCups(ImageButton imageButton, int n) {
        if (n == 0) {
            imageButton.setImageResource(R.drawable.icon_fillcup);
            return 1;
        } else {
            imageButton.setImageResource(R.drawable.icon_blankcup);
            return 0;
        }
    }

    public void setCups() {
        for (int i = 0; i < cups.length; i++) {
            if (cups[i] == 1) changeCups(iBtn_cup[i], 0);
            else changeCups(iBtn_cup[i], 1);
        }
    }

    public Boolean saveData(String Mode, String Bgcolor, String title) {
        //string : userdate, breakfastTime, breakfastMenu, lunchTime, lunchMenu, snackMenu , dinnerTime, dinnerMenu, exerciseContent, comment
        //int : exercisedata, aerobicdata
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        int exerciseData = getExercise();
        int aerobicData = aerobic;
        db = dbHelper.getReadableDatabase();
        switch (Mode) {
            case "write":
                db.execSQL("INSERT INTO healthtracker('userdate', 'waterCups', 'breakfastTime', 'breakfastMenu', 'lunchTime', 'lunchMenu', 'snackMenu', 'dinnerTime', 'dinnerMenu', 'exerciseContent', 'comment',  'exercise', 'aerobic', 'bgcolor', 'title') " +
                        "VALUES('" + userdate + "', '" + waterCups + "', '" + breakfastTime + "', '" + breakfastMenu + "', '" + lunchTime + "', '" + lunchMenu + "', '" + snackMenu + "', '" + dinnerTime + "', '" + dinnerMenu + "', '" + exerciseContent + "', '" + comment + "', '" + exerciseData + "', '" + aerobicData + "', '" + Bgcolor + "', '" + title + "');");
                @SuppressLint("Recycle") final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                cursor.moveToFirst();
                memoID = cursor.getInt(0);
                db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '메모';");
                break;
            case "view":
                if (getArguments() != null) {
                    memoID = getArguments().getInt("memoid");
                }
                db.execSQL("UPDATE healthtracker SET userdate = '" + userdate + "', waterCups = '" + waterCups + "', breakfastTime = '" + breakfastTime + "', breakfastMenu = '" + breakfastMenu + "', lunchTime = '" + lunchTime + "', " +
                        "lunchMenu = '" + lunchMenu + "', snackMenu = '" + snackMenu + "', dinnerTime = '" + dinnerTime + "', dinnerMenu = '" + dinnerMenu + "', exerciseContent = '" + exerciseContent + "', comment = '" + comment + "', exercise = '" + exerciseData + "', aerobic = '" + aerobicData + "'" +
                        "editdate = '" + dateFormat.format(date.getTime()) + "' WHERE id = " + memoID + ";");
                break;
        }
        return true;
    }

    public void setData() {
        String userdate = null, breakfastTime = null, breakfastMenu = null, lunchTime = null, lunchMenu = null, snackMenu = null, dinnerTime = null, dinnerMenu = null, exerciseContent = null, comment = null, waterCups = null;
        int exerciseData = 0, aerobicData = 0;
        String[] array;

        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();
        if (getArguments() != null) {
            memoID = getArguments().getInt("memoid");
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT userdate, breakfastTime, breakfastMenu, lunchTime, lunchMenu, snackMenu , dinnerTime, dinnerMenu, exerciseContent, comment, exercise, aerobic, waterCups  FROM healthtracker WHERE id = " + memoID + "", null);
            while (cursor.moveToNext()) {
                userdate = cursor.getString(0);
                breakfastTime = cursor.getString(1);
                breakfastMenu = cursor.getString(2);
                lunchTime = cursor.getString(3);
                lunchMenu = cursor.getString(4);
                snackMenu = cursor.getString(5);
                dinnerTime = cursor.getString(6);
                dinnerMenu = cursor.getString(7);
                exerciseContent = cursor.getString(8);
                comment = cursor.getString(9);
                exerciseData = cursor.getInt(10);
                aerobicData = cursor.getInt(11);
                waterCups = cursor.getString(12);
            }
            textViewDate.setText(userdate);
            tv_breakfast.setText(breakfastTime);
            tv_lunch.setText(lunchTime);
            tv_dinner.setText(dinnerTime);
            et_breakfast.setText(breakfastMenu);
            et_lunch.setText(lunchMenu);
            et_snack.setText(snackMenu);
            et_dinner.setText(dinnerMenu);
            et_exercise.setText(exerciseContent);
            et_comment.setText(comment);
            setHealth(iv_health, exerciseData, aerobicData);
            array = waterCups.split("");
            for (int i = 0; i < array.length; i++) cups[i] = Integer.parseInt(array[i]);
            setCups();
            String toDate = textViewDate.getText().toString();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat stringToDate = new SimpleDateFormat("yyyy - MM - dd");
            try {
                Date fromString = stringToDate.parse(toDate);
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
