package com.swp.memorythm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class TemplateFragment extends Fragment {
    private View view;
    private RecyclerView templateRecyclerView;
    private ArrayList<Template> listTemplate;
    private TemplateFragAdapter templateFragAdapter;

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_template, container, false);

        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(*) FROM nonlinememo WHERE deleted = 0 UNION ALL " +
                "SELECT count(*) FROM linememo WHERE deleted = 0 UNION ALL " +
                "SELECT count(*) FROM gridmemo WHERE deleted = 0 UNION ALL " +
                "SELECT count(*) FROM todolist WHERE deleted = 0 UNION ALL " +
                "SELECT count(*) FROM wishlist WHERE deleted = 0 UNION ALL " +
                "SELECT count(*) FROM shoppinglist WHERE deleted = 0 UNION ALL " +
                "SELECT count(*) FROM review WHERE deleted = 0 UNION ALL " +
                "SELECT count(*) FROM dailyplan WHERE deleted = 0 UNION ALL " +
                "SELECT count(*) FROM weeklyplan WHERE deleted = 0 UNION ALL " +
                "SELECT count(*) FROM monthlyplan WHERE deleted = 0 UNION ALL " +
                "SELECT count(*) FROM yearlyplan WHERE deleted = 0 UNION ALL " +
                "SELECT count(*) FROM healthtracker WHERE deleted = 0 UNION ALL " +
                "SELECT count(*) FROM monthtracker WHERE deleted = 0 UNION ALL " +
                "SELECT count(*) FROM studytracker WHERE deleted = 0 ", null);

        // 무지메모 줄메모 방안메모 투두리스트 위시리스트 쇼핑리스트 리뷰 데일리플랜 위클리플랜 먼슬리플랜 year플랜 헬스트래커 monthtracker 스터디트래커
        listTemplate = new ArrayList<>();
        cursor.moveToFirst();
        listTemplate.add(new Template("무지 메모", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("메모 (줄) ", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("메모 (방안) ", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("TODO LIST", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("WISH LIST", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("SHOPPING LIST", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Review", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Daily Plan", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Weekly Plan", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Monthly Plan", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Yearly Plan", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Health Tracker", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Month Tracker", cursor.getInt(0)));
        cursor.moveToLast();
        listTemplate.add(new Template("Study Tracker", cursor.getInt(0)));



        templateRecyclerView = (RecyclerView)view.findViewById(R.id.templateRV);
        templateRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        templateRecyclerView.setLayoutManager(manager);

        //어뎁터
        templateFragAdapter = new TemplateFragAdapter(getContext(),listTemplate);
        templateRecyclerView.setAdapter(templateFragAdapter);

        templateFragAdapter.setItems(listTemplate);
        return view;
    }
}