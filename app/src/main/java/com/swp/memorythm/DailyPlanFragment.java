package com.swp.memorythm;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public class DailyPlanFragment extends Fragment {
    private RadioGroup radioGroup;
    private TextView textViewDate;
    private EditText editTextContentAm, editTextContentPm;
    private String currentWeather;
    private int id, weather;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    public int memoid;
    private String userDate, contentAm, contentPm;

    // 메인액티비티에서 고정메모 보는 프래그먼트로 만들어졌으면 수정 불가능하게 뷰 조정
    public boolean fromFixedFragment;

    public boolean isFromFixedFragment() {
        return fromFixedFragment;
    }

    public void setFromFixedFragment(boolean fromFixedFragment) {
        this.fromFixedFragment = fromFixedFragment;
    }

    public static DailyPlanFragment newInstance() {
        return new DailyPlanFragment();
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        if (getArguments() != null) {
            memoid = getArguments().getInt("memoid");
        }
        Cursor cursor = db.rawQuery("SELECT userdate, contentAm, contentPm, weather FROM dailyplan WHERE id = "+memoid+"", null);
        while (cursor.moveToNext()) {
            userDate = cursor.getString(0);
            contentAm = cursor.getString(1);
            contentPm = cursor.getString(2);
            weather = cursor.getInt(3);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_dailyplan, container, false);

        textViewDate = rootView.findViewById(R.id.write_date);
        editTextContentAm = rootView.findViewById(R.id.memo_content_am);
        editTextContentPm = rootView.findViewById(R.id.memo_content_pm);
        radioGroup = rootView.findViewById(R.id.weather_group);

        if((PreferenceManager.getInt(getActivity(), "currentWeatherId") != -1) && !PreferenceManager.getString(getActivity(), "currentWeather").equals("")) {

            currentWeather = PreferenceManager.getString(getActivity(), "currentWeather");
            id = PreferenceManager.getInt(getActivity(), "currentWeatherId");

            //날씨값에 따른 아이콘 선택
            switch (currentWeather) {

                case "Clear":
                    radioGroup.check(R.id.sun);
                    break;
                case "Clouds":
                    if(id == 801 || id == 802) radioGroup.check(R.id.cloudy);
                    else radioGroup.check(R.id.cloud);
                    break;
                case "Thunderstorm":
                    radioGroup.check(R.id.storm);
                    break;
                case "Snow":
                    radioGroup.check(R.id.snow);
                    break;
                case "Drizzle":
                    radioGroup.check(R.id.rain);
                case "Rain":
                    radioGroup.check(R.id.rain);
                    break;
                default:
                    radioGroup.check(R.id.cloud);
                    break;
            }
        }

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy - MM - dd", Locale.KOREA);
        textViewDate.setText(simpleDateFormat.format(currentTime));

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
            editTextContentAm.setText(contentAm);
            editTextContentPm.setText(contentPm);
            radioGroup.check(weather);

            // 수정 불가하게 만들기
            if (isFromFixedFragment()) {
                textViewDate.setEnabled(false);
                editTextContentAm.setEnabled(false);
                editTextContentPm.setEnabled(false);
                radioGroup.setEnabled(false);
            }
        }
    }

    // 메모아이디 가져오기
    public int getMemoid() {
        return memoid;
    }

    // 널 값 검증
    public boolean checkNull() {
        contentAm = editTextContentAm.getText().toString();
        contentPm = editTextContentPm.getText().toString();

        if ((contentAm.equals("") | contentAm == null) && (contentPm.equals("") | contentPm == null)) {
            return false;
        } else {
            return true;
        }
    }

    // 저장 및 수정
    public boolean saveData(String Mode, String Bgcolor, String title) {
        db = dbHelper.getReadableDatabase();

        userDate = textViewDate.getText().toString();
        contentAm = editTextContentAm.getText().toString();
        contentPm = editTextContentPm.getText().toString();
        weather = radioGroup.getCheckedRadioButtonId();

        //작은 따옴표 이스케이프 시키기
        contentAm = contentAm.replaceAll("'", "''");
        contentPm = contentPm.replaceAll("'", "''");

        //editdate 컬럼 업데이트 때문에
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        switch (Mode) {
            case "write":
                db.execSQL("INSERT INTO dailyplan('userdate', 'contentAm', 'contentPm', 'weather', 'title', 'bgcolor') VALUES('" + userDate + "', '" + contentAm + "', '" + contentPm + "', '" + weather + "', '" + title + "', '" + Bgcolor + "');");
                // 작성하면 view 모드로 바꾸기 위해 최근 삽입한 레코드 id로 바꿔줌
                final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                cursor.moveToFirst();
                memoid = cursor.getInt(0);
                db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '메모';");
                break;
            case "view":
                // 메모 수정
                if (getArguments() == null) {
                    db.execSQL("UPDATE dailyplan SET userdate = '"+userDate+"', contentAm = '"+contentAm+"', contentPm = '"+contentPm+"', weather = '"+weather+"', title = '"+title+"', editdate = '"+dateFormat.format(date.getTime()) + "' WHERE id = "+memoid+";");
                } else {
                    memoid = getArguments().getInt("memoid");
                    db.execSQL("UPDATE dailyplan SET userdate = '"+userDate+"', contentAm = '"+contentAm+"', contentPm = '"+contentPm+"', weather = '"+weather+"', title = '"+title+"', editdate = '"+dateFormat.format(date.getTime()) + "' WHERE id = "+memoid+";");
                }
                break;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
    }
}
