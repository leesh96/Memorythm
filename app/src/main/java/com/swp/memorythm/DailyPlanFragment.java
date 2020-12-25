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

public class DailyPlanFragment extends Fragment implements LocationListener {
    LocationManager locationManager;
    double latitude;
    double longitude;
    private RadioGroup radioGroup;
    private TextView textViewDate;
    private EditText editTextContentAm, editTextContentPm;

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
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        //GPS 권한 요구, 확인
        requestLocation();

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

    @Override
    public void onLocationChanged(@NonNull Location location) {
        /*현재 위치에서 위도경도 값을 받아온뒤 우리는 지속해서 위도 경도를 읽어올것이 아니니
        날씨 api에 위도경도 값을 넘겨주고 위치 정보 모니터링을 제거한다.*/
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        //날씨 가져오기 통신
        getWeather(latitude, longitude);
        //위치정보 모니터링 제거
        locationManager.removeUpdates(DailyPlanFragment.this);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) { //GPS 켜져있을 때

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) { //GPS 꺼져있을 때

    }

    private void requestLocation() {
        //사용자로 부터 위치정보 권한체크
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);

        }
    }

    //api 통신 인터페이스
    private interface ApiService {
        //베이스 Url
        String BASEURL = "https://api.openweathermap.org/data/2.5/";
        String APPKEY = "29e3890bbf63fa8c85d67f0a5c5f41d9";

        //get 메소드를 통한 http rest api 통신
        @GET("weather?")
        Call<JsonObject> getHourly(@Query("lat") double lat, @Query("lon") double lon, @Query("APPID") String APPID);

    }

    //날씨 받아오기
    private void getWeather(double latitude, double longitude) {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.BASEURL)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<JsonObject> call = apiService.getHourly(latitude, longitude, ApiService.APPKEY);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    //날씨데이터를 받아옴
                    JsonObject object = response.body();
                    if (object != null) {
                        //데이터가 null 이 아니라면 날씨 데이터를 파싱 currentWeather에 날씨 값
                        JsonArray weatherArray = object.getAsJsonArray("weather");
                        JsonObject weatherObject = (JsonObject) weatherArray.get(0);
                        String currentWeather = weatherObject.get("main").getAsString();
                        int id = weatherObject.get("id").getAsInt(); //partly cloud랑 cloud 구분할 id
                        radioGroup = getView().findViewById(R.id.weather_group);

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
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

            }
        });
    }

}
