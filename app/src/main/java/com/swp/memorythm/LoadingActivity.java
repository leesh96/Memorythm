package com.swp.memorythm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class LoadingActivity extends AppCompatActivity implements LocationListener {
    private Intent intent;
    LocationManager locationManager;
    double latitude;
    double longitude;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        dbHelper = new DBHelper(LoadingActivity.this);
        // 이거 써서 db 접근
        db = dbHelper.getReadableDatabase();
        // DB 읽기. DB가 없으면 onCreate가 호출, 버전이 바뀌었으면 onUpgrade 호출
        // dbHelper.getWritableDatabase() : 읽고 쓰기 위해 DB 연다. 권한이 없거나 디스크가 가득 차면 실패
        // db.close() : DB를 닫는다.

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //GPS 권한 요구, 확인
        requestLocation();

        // 날짜 받아오기
        setCurrentDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        db.close();
    }

    private void setCurrentDate() {
        // 앱 실행할때마다 날짜 가져오기
        // 캘린더 객체 생성
        Calendar myCalendar = Calendar.getInstance();

        String DateFormat = "yyyy - MM - dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat, Locale.KOREA);
        String CurrentDate = simpleDateFormat.format(myCalendar.getTime());

        if (!PreferenceManager.getString(LoadingActivity.this, "currentDate").equals(CurrentDate)) {
            PreferenceManager.setString(LoadingActivity.this, "currentDate", CurrentDate);
        }
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
        locationManager.removeUpdates(LoadingActivity.this);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) { //GPS 켜져있을 때

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) { //GPS 꺼져있을 때

        PreferenceManager.removeKey(LoadingActivity.this, "currentWeather");
        PreferenceManager.removeKey(LoadingActivity.this, "currentWeatherId");
    }

    private void requestLocation() {
        //사용자로 부터 위치정보 권한체크, 권한 없으면 권한 요구
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else { //권한 있으면 3초후 메인으로 이동
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);

            // 로딩화면 3초 전환
            Handler mHandler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    intent = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            mHandler.sendEmptyMessageDelayed(0, 3000);
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
                        int currentWeatherId = weatherObject.get("id").getAsInt(); //partly cloud랑 cloud 구분할 id

                        PreferenceManager.setString(LoadingActivity.this, "currentWeather", currentWeather);
                        PreferenceManager.setInt(LoadingActivity.this, "currentWeatherId", currentWeatherId);
                        Log.d("weather", currentWeather);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.d("weather", "fail");
            }
        });
    }

    //권한 없을 때 권한 허용, 거부 아무거나 선택하면 메인으로 이동
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 0) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);
            }

            intent = new Intent(LoadingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
