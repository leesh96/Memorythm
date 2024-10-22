package com.swp.memorythm.template;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
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

public class MonthTrackerFragment extends Fragment {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private int memoID, sum;
    private TextView textViewDate;
    private EditText et_goal, et_comment;
    private final Button[] btn_day = new Button[31];
    private final int[] num_day = new int[31];
    private boolean fromFixedFragment;

    public boolean isFromFixedFragment() {
        return fromFixedFragment;
    }

    public void setFromFixedFragment(boolean fromFixedFragment) {
        this.fromFixedFragment = fromFixedFragment;
    }

    public static MonthTrackerFragment newInstance() {
        return new MonthTrackerFragment();
    }

    // 캘린더 객체 생성
    Calendar myCalendar = Calendar.getInstance();

    //데이트픽커 다이얼로그
    DatePickerDialog.OnDateSetListener myDatePicker = (datePicker, year, month, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        updateLabel();
    };

    // 텍스트뷰 날짜 업데이트
    private void updateLabel() {
        String DateFormat = "yyyy - MM";
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat, Locale.KOREA);

        textViewDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.template_monthtracker, container, false);
        dbHelper = new DBHelper(getContext());

        textViewDate = viewGroup.findViewById(R.id.write_date);
        et_goal = viewGroup.findViewById(R.id.et_goal);
        et_comment = viewGroup.findViewById(R.id.et_comment);

        String packName = Objects.requireNonNull(this.getActivity()).getPackageName();
        for (int i = 1; i < 32; i++) {
            String name = "btn_day" + i;
            int id = getResources().getIdentifier(name, "id", packName);
            btn_day[i - 1] = viewGroup.findViewById(id);
        }

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        String date = PreferenceManager.getString(getContext(), "currentDate");
        date = date.substring(0,9);
        textViewDate.setText(date);

        textViewDate.setOnClickListener(v -> { // 데이트픽커 띄우기
            new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        //날짜 체크
        for (int i = 0; i < btn_day.length; i++) {
            int finalI = i;
            btn_day[i].setOnClickListener(v -> num_day[finalI] = changeBgColor(btn_day[finalI], num_day[finalI]));
        }
        View activityRootView = viewGroup.findViewById(R.id.parentLayout);
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

    //날짜 체크 함수
    public int changeBgColor(Button button, int n) {
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.icon_circle);
        assert drawable != null;
        if (n == 0) {
            switch ((sum + 1) / 10) {
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
            sum++;
            return 1;
        } else {
            drawable.setColor(Color.TRANSPARENT);
            button.setBackground(drawable);
            return 0;
        }
    }

    public void setBgColor() {
        for (int i = 0; i < num_day.length; i++) {
            if (num_day[i] == 1) changeBgColor(btn_day[i], 0);
            else changeBgColor(btn_day[i], 1);
        }
    }

    public Boolean saveData(String Mode, String Bgcolor, String title) {
        //String : userdate, goal, dayCheck, comment
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String userdate = textViewDate.getText().toString();
        String goal = et_goal.getText().toString().replaceAll("'", "''");
        String comment = et_comment.getText().toString().replaceAll("'", "''");
        StringBuilder dayCheck = new StringBuilder();
        for (int value : num_day) dayCheck.append(value).append("!");
        Log.d("dayCheck",dayCheck.toString());

        db = dbHelper.getReadableDatabase();
        switch (Mode) {
            case "write":
                db.execSQL("INSERT INTO monthtracker('userdate', 'goal', 'dayCheck', 'comment', 'bgcolor', 'title') VALUES('" + userdate + "', '" + goal + "', '" + dayCheck + "', '" + comment + "', '" + Bgcolor + "', '" + title + "');");
                @SuppressLint("Recycle") final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                cursor.moveToFirst();
                memoID = cursor.getInt(0);
                db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '메모';");
                break;
            case "view":
                if (getArguments() != null) {
                    memoID = getArguments().getInt("memoid");
                }
                db.execSQL("UPDATE monthtracker SET userdate = '" + userdate + "', goal = '" + goal + "', dayCheck = '" + dayCheck + "', comment = '" + comment + "', editdate = '" + dateFormat.format(date.getTime()) + "' WHERE id = " + memoID + ";");
                break;
        }
        return true;
    }

    public void setData() {
        String userdate = null, goal = null, dayCheck = null, comment = null;
        String[] array;
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();
        if (getArguments() != null) {
            memoID = getArguments().getInt("memoid");
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT userdate, goal, dayCheck, comment  FROM monthtracker WHERE id = " + memoID + "", null);
            while (cursor.moveToNext()) {
                userdate = cursor.getString(0);
                goal = cursor.getString(1);
                dayCheck = cursor.getString(2);
                comment = cursor.getString(3);
            }
            textViewDate.setText(userdate);
            et_goal.setText(goal);
            et_comment.setText(comment);
            array = dayCheck.split("!");
            for (int i = 0; i < array.length; i++) num_day[i] = Integer.parseInt(array[i]);
            setBgColor();
            // 데이트픽커 다이얼로그에 userdate로 뜨게 하는 코드
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

    // 널 값 검증
    public boolean checkNull() {
        String goal = et_goal.getText().toString();
        return !(goal.equals("") | goal == null);
    }
}
