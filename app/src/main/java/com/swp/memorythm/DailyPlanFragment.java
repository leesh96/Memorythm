package com.swp.memorythm;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private int id;

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

        // TODO: 2020-11-20 파이어베이스 연동

        return rootView;
    }
}
