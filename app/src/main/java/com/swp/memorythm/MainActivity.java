package com.swp.memorythm;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ImageButton homeBtn, drawerBtn;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int fixedmemocnt = 0;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(MainActivity.this);
        db = dbHelper.getReadableDatabase();

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

        // 홈버튼 누르면 고정 메모 프레그먼트 띄우기
        homeBtn = (ImageButton) findViewById(R.id.homeButton);
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
        // drawer 버튼
        drawerBtn = (ImageButton) findViewById(R.id.drawerButton);
        // drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //navigationview
        navigationView = (NavigationView) findViewById(R.id.navigation_layout);

        //바텀 네비게이션 사용
        bottomNavigationView = findViewById(R.id.bottomNavi);
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

        // 첫 프레그먼트 지정
        if (fixedmemocnt != 0) {
            setFrag(0);
        } else {
            setFrag(1);
        }

        //drawer 사용
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                int id = item.getItemId();
                String title = item.getTitle().toString();

                switch (id) {
                    case R.id.dataBackUp:
                        break;
                    case R.id.dataRestore:
                        break;
                    case R.id.logout:
                        break;
                }

                return true;
            }
        });
        drawerBtn.setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
    }

    @Override
    protected void onStop() {
        db.close();
        super.onStop();
    }

    @Override
    protected void onResume() {
        fixedmemocnt = PreferenceManager.getInt(MainActivity.this, "fixedmemocnt");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        PreferenceManager.removeKey(MainActivity.this, "fixedmemocnt");
        super.onDestroy();
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
}