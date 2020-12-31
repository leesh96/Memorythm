package com.swp.memorythm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; // 바텀네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ImageButton homeBtn; // 홈으로 가는 버튼
    private ListView listview = null;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 홈버튼 누르면 고정 메모 프레그먼트 띄우기
        homeBtn = (ImageButton)findViewById(R.id.homeButton);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFrag(0);
            }
        });

        //바텀 네비게이션 사용
        bottomNavigationView  = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
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

        setFrag(0); // 첫 프레그먼트 지정

        //Navigation Drawer(왼쪽에 있음, 드래그하면 나타남)
        final String[] items = {"Setting"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items) ;
        listview = (ListView) findViewById(R.id.drawer_menulist) ;
        listview.setAdapter(adapter) ;
        //클릭
        listview.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {

                //(나중에 아이템 추가할거 있으면 추가하기)

                //리스트뷰의 아이템을 선택하면 drawer 닫기
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout) ;
                drawer.closeDrawer(Gravity.LEFT) ;
            }
        });
    }

    //프레그먼트 교체
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
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