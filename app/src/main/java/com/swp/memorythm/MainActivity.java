package com.swp.memorythm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.Instant;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView; // 바텀네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FolderFragment folderFragment; // 폴더 프레그먼트
    private TemplateFragment templateFragment; // 템플릿 프레그먼트
    private WriteFragment writeFragment; // 작성 프레그먼트
    private TrashFragment trashFragment; // 휴지통 프레그먼트
    private SetFragment setFragment; // 설정 프레그먼트


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView  = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.folderItem:
                        setFrag(0);
                        break;
                    case R.id.templateItem:
                        setFrag(1);
                        break;
                    case R.id.writeItem:
                        setFrag(2);
                        break;
                    case R.id.trashItem:
                        setFrag(3);
                        break;
                    case R.id.setItem:
                        setFrag(4);
                        break;
                }
                return true;
            }
        });
        folderFragment = new FolderFragment();
        templateFragment = new TemplateFragment();
        writeFragment = new WriteFragment();
        trashFragment = new TrashFragment();
        setFragment = new SetFragment();
//        setFrag(1); // 첫 프레그먼트 지정



    }

    //프레그먼트 교체
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.mainFrame, folderFragment);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.mainFrame, templateFragment);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.mainFrame, writeFragment);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.mainFrame, trashFragment);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.mainFrame, setFragment);
                ft.commit();
                break;

        }
    }
}