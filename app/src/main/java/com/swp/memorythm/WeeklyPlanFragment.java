package com.swp.memorythm;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeeklyPlanFragment extends Fragment {
    private TextView textViewDate;
    private EditText editTextMon, editTextTue, editTextWed, editTextThu, editTextFri, editTextSat, editTextSun,
                     editTextDayMon, editTextDayTue, editTextDayWed, editTextDayThu, editTextDayFri, editTextDaySat, editTextDaySun;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private EditText[] weekView = new EditText[7], dayView = new EditText[7];
    public int memoid;
    private String userDate;
    private String[] setContent, setDay;
    private StringBuilder contentWeek, contentDay;

    public static WeeklyPlanFragment newInstance() {
        return new WeeklyPlanFragment();
    }

    // 캘린더 객체 생성
    Calendar myCalendar = Calendar.getInstance();

    //데이트픽커 다이얼로그
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            updateLabel();
        }
    };

    // 텍스트뷰 날짜 업데이트
    private void updateLabel() {
        String DateFormat = "yyyy - MM";
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
        Cursor cursor = db.rawQuery("SELECT userdate, contentWeek, contentDay, splitKey FROM weeklyplan WHERE id = "+memoid+"", null);
        while (cursor.moveToNext()) {
            userDate = cursor.getString(0);
            String splitKey = cursor.getString(3);
            setContent = cursor.getString(1).split(splitKey);
            setDay = cursor.getString(2).split(",");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_weeklyplan, container, false);

        textViewDate = rootView.findViewById(R.id.write_date);
        editTextMon = rootView.findViewById(R.id.memo_content_mon);
        editTextTue = rootView.findViewById(R.id.memo_content_tue);
        editTextWed = rootView.findViewById(R.id.memo_content_wed);
        editTextThu = rootView.findViewById(R.id.memo_content_thu);
        editTextFri = rootView.findViewById(R.id.memo_content_fri);
        editTextSat = rootView.findViewById(R.id.memo_content_sat);
        editTextSun = rootView.findViewById(R.id.memo_content_sun);
        editTextDayMon = rootView.findViewById(R.id.day_mon);
        editTextDayTue = rootView.findViewById(R.id.day_tue);
        editTextDayWed = rootView.findViewById(R.id.day_wed);
        editTextDayThu = rootView.findViewById(R.id.day_thu);
        editTextDayFri = rootView.findViewById(R.id.day_fri);
        editTextDaySat = rootView.findViewById(R.id.day_sat);
        editTextDaySun = rootView.findViewById(R.id.day_sun);

        weekView[0] = editTextMon;
        weekView[1] = editTextTue;
        weekView[2] = editTextWed;
        weekView[3] = editTextThu;
        weekView[4] = editTextFri;
        weekView[5] = editTextSat;
        weekView[6] = editTextSun;

        dayView[0] = editTextDayMon;
        dayView[1] = editTextDayTue;
        dayView[2] = editTextDayWed;
        dayView[3] = editTextDayThu;
        dayView[4] = editTextDayFri;
        dayView[5] = editTextDaySat;
        dayView[6] = editTextDaySun;

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy - MM", Locale.KOREA);
        textViewDate.setText(simpleDateFormat.format(currentTime));

        final View activityRootView = rootView.findViewById(R.id.frame);

        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - r.height();
                if ((heightDiff < 0.25 * activityRootView.getRootView().getHeight()) && WeeklyPlanFragment.this.getActivity() != null) {
                    Log.d("키보드 ", "내려감?");
                    if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[0]) {

                        calculateDay(0);
                        dayView[0].clearFocus();
                    } else if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[1]) {

                        calculateDay(1);
                        dayView[1].clearFocus();
                    } else if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[2]) {

                        calculateDay(2);
                        dayView[2].clearFocus();
                    }
                    else if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[3]) {

                        calculateDay(3);
                        dayView[3].clearFocus();
                    }
                    else if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[4]) {

                        calculateDay(4);
                        dayView[4].clearFocus();
                    }
                    else if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[5]) {

                        calculateDay(5);
                        dayView[5].clearFocus();
                    }
                    else if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[6]) {

                        calculateDay(6);
                        dayView[6].clearFocus();
                    }
                }
            }
        });

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

            for(int i = 0; i < weekView.length; i++) {

                weekView[i].setText(setContent[i]);

            }
            for(int i = 0; i < dayView.length; i++) {

                dayView[i].setText(setDay[i]);
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
        contentWeek = new StringBuilder();
        contentDay = new StringBuilder();
        String splitKey = makeKey();

        for (EditText editText : weekView) {

            if(editText.getText().toString().equals("")) {

                contentWeek.append(" ").append(splitKey);
            }
            else {

                contentWeek.append(editText.getText().toString().replaceAll("'", "''")).append(splitKey);
            }
        }

        for(EditText editText : dayView) {

            if(editText.getText().toString().equals("")) {

                contentDay.append(" ").append(",");
            }
            else {

                contentDay.append(editText.getText().toString()).append(",");
            }
        }

        //editdate 컬럼 업데이트 때문에
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        switch (Mode) {
            case "write":
                db.execSQL("INSERT INTO weeklyplan('userdate', 'contentWeek', 'contentDay', 'splitKey', 'title', 'bgcolor') VALUES('" + userDate + "', '" + contentWeek + "', '" + contentDay + "', '" + splitKey + "', '" + title + "', '" + Bgcolor + "');");
                // 작성하면 view 모드로 바꾸기 위해 최근 삽입한 레코드 id로 바꿔줌
                final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                cursor.moveToFirst();
                memoid = cursor.getInt(0);
                break;
            case "view":
                // 메모 수정
                if (getArguments() == null) {
                    db.execSQL("UPDATE weeklyplan SET userdate = '"+userDate+"', contentWeek = '"+contentWeek+"', contentDay = '"+contentDay+"', splitKey = '"+splitKey+"', title = '"+title+"', editdate = '"+dateFormat.format(date.getTime()) + "' WHERE id = "+memoid+";");
                } else {
                    memoid = getArguments().getInt("memoid");
                    db.execSQL("UPDATE weeklyplan SET userdate = '"+userDate+"', contentWeek = '"+contentWeek+"', contentDay = '"+contentDay+"', splitKey = '"+splitKey+"', title = '"+title+"', editdate = '"+dateFormat.format(date.getTime()) + "' WHERE id = "+memoid+";");
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

    public void calculateDay(int index) {

        int std, diff, day;
        String input;

        std = Integer.parseInt(dayView[index].getText().toString());

        for(int i = 0; i < dayView.length; i++) {

            diff = i - index;
            day = std + diff;
            input = Integer.toString(day);

            if(i == index) continue;
            if((day < 1) || (day > myCalendar.getActualMaximum(Calendar.DATE))) continue;
            dayView[i].setText(input);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
    }
}
