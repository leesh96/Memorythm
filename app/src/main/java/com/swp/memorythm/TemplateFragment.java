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

    private DBHelper dbHelper;
    private SQLiteDatabase db;

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

        listTemplate = new ArrayList<>();
        cursor.moveToFirst();
        listTemplate.add(new Template("무지 메모","nonlinememo" ,cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("메모 (줄) ","linememo", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("메모 (방안) ","gridmemo" ,cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("TODO LIST","todolist", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("WISH LIST","wishlist", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("SHOPPING LIST","shoppinglist", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Review","review", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Daily Plan","dailyplan", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Weekly Plan","weeklyplan", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Monthly Plan","monthlyplan", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Yearly Plan","yearlyplan", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Health Tracker","healthtracker", cursor.getInt(0)));
        cursor.moveToNext();
        listTemplate.add(new Template("Month Tracker","monthtracker", cursor.getInt(0)));
        cursor.moveToLast();
        listTemplate.add(new Template("Study Tracker","studytracker", cursor.getInt(0)));

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