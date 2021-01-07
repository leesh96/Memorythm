package com.swp.memorythm;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ImageButton drawerBtn;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton homeBtn; // 홈으로 가는 버튼
    private ListView listview = null;
    private String uid, name, profile, email;
    private TextView tv_name, tv_email;
    private CircleImageView iv_profile;
    private int fixedmemocnt = 0;
    private Bitmap bitmap;

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private LocationManager locationManager;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // drawer 버튼
        drawerBtn = (ImageButton) findViewById(R.id.drawerButton);
        // drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //navigationview
        navigationView = (NavigationView) findViewById(R.id.navigation_layout);
        tv_name = navigationView.getHeaderView(0).findViewById(R.id.user_name);
        iv_profile = navigationView.getHeaderView(0).findViewById(R.id.user_pro);
        tv_email = navigationView.getHeaderView(0).findViewById(R.id.user_email);
        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavi);
        // 홈버튼
        homeBtn = (ImageButton) findViewById(R.id.homeButton);

        // 인텐트 넘어오는 값
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        name = intent.getStringExtra("name");
        profile = intent.getStringExtra("profile");
        email = intent.getStringExtra("email");

        // DB 오픈
        dbHelper = new DBHelper(MainActivity.this);
        db = dbHelper.getReadableDatabase();

        // 날씨
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (PreferenceManager.getInt(MainActivity.this, "fixedmemocnt") == -1) {
            try {
                Cursor cursor = db.rawQuery("SELECT count(*) FROM nonlinememo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                        "SELECT count(*) FROM linememo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                        "SELECT count(*) FROM gridmemo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                        "SELECT count(*) FROM todolist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                        "SELECT count(*) FROM wishlist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                        "SELECT count(*) FROM shoppinglist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                        "SELECT count(*) FROM review WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                        "SELECT count(*) FROM dailyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                        "SELECT count(*) FROM weeklyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                        "SELECT count(*) FROM monthlyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                        "SELECT count(*) FROM yearlyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                        "SELECT count(*) FROM healthtracker WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                        "SELECT count(*) FROM monthtracker WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                        "SELECT count(*) FROM studytracker WHERE fixed = 1 AND deleted = 0", null);
                while (cursor.moveToNext()) {
                    fixedmemocnt += cursor.getInt(0);
                }
                PreferenceManager.setInt(MainActivity.this, "fixedmemocnt", fixedmemocnt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            fixedmemocnt = PreferenceManager.getInt(MainActivity.this, "fixedmemocnt");
        }

        // 첫 프레그먼트 지정
        if (fixedmemocnt != 0) {
            setFrag(0);
        } else {
            setFrag(1);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);
        }

        //스레드로 사용자 프로필 사진 가져옴
        Thread mThread= new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(profile);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                } catch (MalformedURLException ee) {
                    ee.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();
        try{
            mThread.join();
            //변환한 bitmap적용
            iv_profile.setImageBitmap(bitmap);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // 홈버튼 누르면 고정 메모 프레그먼트 띄우기
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fixedmemocnt != 0) {
                    setFrag(0);
                } else {
                    setFrag(1);
                }
            }
        });

        tv_name.setText(name);
        tv_email.setText(email);

        //바텀 네비게이션 사용
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.totalItem:
                        setFrag(1);
                        break;
                    case R.id.folderItem:
                        setFrag(2);
                        break;
                    case R.id.templateItem:
                        setFrag(3);
                        break;
                    case R.id.writeItem:
                        setFrag(4);
                        break;
                    case R.id.trashItem:
                        setFrag(5);
                        break;
                }
                return true;
            }
        });

        // drawer 사용
        drawerBtn.setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                int id = item.getItemId();
                String title = item.getTitle().toString();

                switch (id) {
                    case R.id.dataBackUp:
                        backupData("snack");
                        break;
                    case R.id.dataRestore:
                        restoreData("snack");
                        break;
                    case R.id.logout:
                        backupData("logout");
                        break;
                }

                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                return;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                return;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(this);
        backupData("");
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fixedmemocnt = PreferenceManager.getInt(MainActivity.this, "fixedmemocnt");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);
    }

    //
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.removeKey(MainActivity.this, "fixedmemocnt");
    }

    //프레그먼트 교체
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.mainFrame, new FixedMemoFragment());
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.mainFrame, new TotalFragment());
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.mainFrame, new FolderFragment());
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.mainFrame, new TemplateFragment());
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.mainFrame, new WriteFragment());
                ft.commit();
                break;
            case 5:
                ft.replace(R.id.mainFrame, new TrashFragment());
                ft.commit();
                break;
        }
    }

    //db파일 백업
    public void backupData(String flag) {

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        try {
            File data = Environment.getDataDirectory(); // 경로(data/)

            File currentDB = new File(data, "/data/com.swp.memorythm/databases/memorythm.db");
            Uri uri = Uri.fromFile(currentDB); //파일로부터 uri 생성?
            StorageReference mRef = storageReference.child("backup/" + uid); //스토리지 참조
            UploadTask uploadTask = mRef.putFile(uri); //파일 업로드

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                    // 업로드 실패알림
                    Log.d("오류", exception.getLocalizedMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    switch(flag) {

                        // 백업 버튼 눌러서 백업 성공하면 스낵바 띄움
                        case "snack":
                            Snackbar snackbar = Snackbar.make(navigationView, "백업 성공", Snackbar.LENGTH_SHORT);

                            snackbar.setAction("확인", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    snackbar.dismiss();
                                }
                            }).show();
                            break;
                        case "logout":
                            dbHelper.onUpgrade(db, 1, 1); //로그아웃시 테이블 초기화
                            FirebaseAuth.getInstance().signOut(); //파이어베이스 인증해제
                            Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent1);
                            finish();
                            break;
                        default:
                            Log.d("reuslt", "백업 성공");
                            break;
                    }
                }
            });
        } catch (Exception e) {
            Log.d("reuslt", "백업 실패");
        }
    }

    //db파일 복원
    public void restoreData(String flag) {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();

        try {
            File data = Environment.getDataDirectory(); // 경로(data/)

            File currentDB = new File(data, "/data/com.swp.memorythm/databases/memorythm.db");
            Uri uri = Uri.fromFile(currentDB); //파일로부터 uri 생성?
            StorageReference mRef = storageReference.child("backup/" + uid); //스토리지 참조

            mRef.getFile(uri).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    // 업로드 실패알림
                    Log.d("오류", e.getLocalizedMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    if(flag.equals("snack")) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        intent.putExtra("uid", uid);
                        intent.putExtra("name", name);
                        intent.putExtra("profile", profile);
                        intent.putExtra("email", email);
                        finish();
                    }
                    else {

                        Log.d("reuslt", "복원 성공");
                    }
                }
            });
        } catch (Exception e) {
            Log.d("reuslt", "파일 다운로드 실패");
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        /*현재 위치에서 위도경도 값을 받아온뒤 우리는 지속해서 위도 경도를 읽어올것이 아니니
        날씨 api에 위도경도 값을 넘겨주고 위치 정보 모니터링을 제거한다.*/
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        //날씨 가져오기 통신
        getWeather(latitude, longitude);
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
                .baseUrl(MainActivity.ApiService.BASEURL)
                .build();
        MainActivity.ApiService apiService = retrofit.create(MainActivity.ApiService.class);
        Call<JsonObject> call = apiService.getHourly(latitude, longitude, MainActivity.ApiService.APPKEY);
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

                        PreferenceManager.setString(MainActivity.this, "currentWeather", currentWeather);
                        PreferenceManager.setInt(MainActivity.this, "currentWeatherId", currentWeatherId);
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

    // 권한 요청 결과
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);
            }
        }
    }
}