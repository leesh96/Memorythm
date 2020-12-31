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

public class TemplateFragment extends Fragment implements View.OnClickListener {
    private View view;
    private RecyclerView templateRecyclerView;
    private ArrayList<Template> listTemplate;
    private TemplateFragAdapter templateFragAdapter;
    private ItemTouchHelper helper;
    private ImageButton btnRange;
    private boolean click = false;

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_template, container, false);

        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(*) FROM nonlinememo/* UNION ALL " +
                "SELECT count(*) FROM linememo UNION ALL " +
                "SELECT count(*) FROM gridmemo*/", null);
        listTemplate = new ArrayList<>();
        cursor.moveToFirst();
        listTemplate.add(new Template("무지 메모", cursor.getInt(0)));

        /*while (cursor.moveToNext()) {
            listTemplate.add(new Template("메모 (줄)", cursor.getInt(0)));
            listTemplate.add(new Template("메모 (방안)",cursor.getInt(0)));
        }*/


        templateRecyclerView = (RecyclerView)view.findViewById(R.id.templateRV);
        templateRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        templateRecyclerView.setLayoutManager(manager);
        //어뎁터
        templateFragAdapter = new TemplateFragAdapter(getContext(),listTemplate);
        templateRecyclerView.setAdapter(templateFragAdapter);
        // 정렬 버튼
//        btnRange = (ImageButton)view.findViewById(R.id.rangeBtn);
//        btnRange.setOnClickListener(this);

        //ItemTouchHelper 생성
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(templateFragAdapter));
        //RecyclerView에 ItemTouchHelper 붙이기
        helper.attachToRecyclerView(templateRecyclerView);

        templateFragAdapter.setItems(listTemplate);
        return view;
    }

    @Override
    public void onClick(View view) {
        /*
        switch (view.getId()){
            case R.id.rangeBtn:
                if(click){
                    templateFragAdapter.setArray(false);
                    click = false;
                }else{
                    templateFragAdapter.setArray(true);
                    click = true;
                }

                break;
        }

         */

    }
}