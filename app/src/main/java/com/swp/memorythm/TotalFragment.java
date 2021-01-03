package com.swp.memorythm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TotalFragment extends Fragment {

    private RecyclerView totalRecyclerView, totalTopRecyclerView;
    private TotalMemoAdapter totalMemoAdapter;
    private TotalFixedMemoAdapter totalFixedMemoAdapter;
    private ArrayList<TotalMemoData> listTotal, listFixed;
    private ImageButton btnDelete;
    private Boolean check = false;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public TotalFixedMemoAdapter getTotalFixedMemoAdapter() {
        return totalFixedMemoAdapter;
    }

    public void setTotalFixedMemoAdapter(TotalFixedMemoAdapter totalFixedMemoAdapter) {
        this.totalFixedMemoAdapter = totalFixedMemoAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total, container, false);
/*        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM (SELECT id, title, editdate, template_case FROM nonlinememo WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM linememo WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM gridmemo WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM todolist WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM wishlist WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM shoppinglist WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM review WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM dailyplan WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM weeklyplan WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthlyplan WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM yearlyplan WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM healthtracker WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthtracker WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM studytracker WHERE deleted = 0 AND fixed = 0) ORDER BY editdate DESC ",null);

        Cursor cursorFixed = db.rawQuery("SELECT * FROM (SELECT id, title, editdate, template_case FROM nonlinememo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM linememo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM gridmemo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM todolist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM wishlist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM shoppinglist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM review WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM dailyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM weeklyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthlyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM yearlyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM healthtracker WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthtracker WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM studytracker WHERE fixed = 1 AND deleted = 0) ORDER BY editdate DESC",null);

        listTotal = new ArrayList<>();
        listFixed = new ArrayList<>();

        while (cursor.moveToNext()) {
            //전체메모
            TotalMemoData totalMemoData = new TotalMemoData();
            totalMemoData.setTotalID(cursor.getInt(0));
            totalMemoData.setTotalTitle(cursor.getString(1));
            totalMemoData.setTotalDate(cursor.getString(2).substring(0, 10));
            totalMemoData.setTemplate(cursor.getString(3));

            listTotal.add(totalMemoData);

        }

        while (cursorFixed.moveToNext()) {
            //전체메모의 고정메모
            TotalMemoData fixedData = new TotalMemoData();
            fixedData.setTotalID(cursorFixed.getInt(0));
            fixedData.setTotalTitle(cursorFixed.getString(1));
            fixedData.setTotalDate(cursorFixed.getString(2).substring(0, 10));
            fixedData.setTemplate(cursorFixed.getString(3));
            listFixed.add(fixedData);
        }*/


        // recyclerview
        totalRecyclerView = (RecyclerView)view.findViewById(R.id.totalRV);
        totalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        totalMemoAdapter = new TotalMemoAdapter(getContext(),listTotal);
        totalRecyclerView.setAdapter(totalMemoAdapter);
        // fixed recyclerview
        totalTopRecyclerView = (RecyclerView)view.findViewById(R.id.totalTopRV);
        totalTopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        totalFixedMemoAdapter = new TotalFixedMemoAdapter(getContext(),listFixed);
        totalTopRecyclerView.setAdapter(totalFixedMemoAdapter);
        //삭제버튼
        btnDelete = (ImageButton)view.findViewById(R.id.totalDelBtn);
        btnDelete.setOnClickListener(view1 -> {
            if(check){
                if(totalMemoAdapter.removeItems() | totalFixedMemoAdapter.removeItems()){
                    List<TotalMemoData> list = totalMemoAdapter.setCheckBox();
                    List<TotalMemoData> fixedList = totalFixedMemoAdapter.setCheckBox();

                    for (TotalMemoData totalMemoData : list) {
                        String table = totalMemoData.getTemplate();
                        int tableID = totalMemoData.getTotalID();
                        db.execSQL("UPDATE '"+table+"' SET deleted = 1 WHERE id = '"+ tableID +"';");
                        Cursor cursor1 = db.rawQuery("SELECT folder_name FROM '"+table+"' WHERE id = '"+ tableID +"'",null);
                        cursor1.moveToFirst();
                        String folderN = cursor1.getString(0);
                        db.execSQL("UPDATE folder SET count = count - 1 WHERE name = '"+folderN+"';");
                        listTotal.remove(totalMemoData);
                    }
                    for (TotalMemoData totalMemoData : fixedList) {
                        String table = totalMemoData.getTemplate();
                        int tableID = totalMemoData.getTotalID();
                        db.execSQL("UPDATE '"+table+"' SET deleted = 1 WHERE id = '"+ tableID +"';");
                        Cursor cursor1 = db.rawQuery("SELECT folder_name FROM '"+table+"' WHERE id = '"+ tableID +"'",null);
                        cursor1.moveToFirst();
                        String folderN = cursor1.getString(0);
                        db.execSQL("UPDATE folder SET count = count - 1 WHERE name = '"+folderN+"';");
                        listFixed.remove(totalMemoData);
                    }

                    Toast.makeText(getContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "삭제할 폴더를 선택하세요", Toast.LENGTH_SHORT).show();
                }
                totalMemoAdapter.setVisible(false);
                totalFixedMemoAdapter.setVisible(false);
                totalMemoAdapter.notifyDataSetChanged();
                totalFixedMemoAdapter.notifyDataSetChanged();
                check = false;
            }else {
                totalMemoAdapter.setVisible(true);
                totalFixedMemoAdapter.setVisible(true);
                totalMemoAdapter.notifyDataSetChanged();
                totalFixedMemoAdapter.notifyDataSetChanged();
                check = true;
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Cursor cursor = db.rawQuery("SELECT * FROM (SELECT id, title, editdate, template_case FROM nonlinememo WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM linememo WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM gridmemo WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM todolist WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM wishlist WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM shoppinglist WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM review WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM dailyplan WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM weeklyplan WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthlyplan WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM yearlyplan WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM healthtracker WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthtracker WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM studytracker WHERE deleted = 0 AND fixed = 0) ORDER BY editdate DESC ",null);

        Cursor cursorFixed = db.rawQuery("SELECT * FROM (SELECT id, title, editdate, template_case FROM nonlinememo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM linememo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM gridmemo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM todolist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM wishlist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM shoppinglist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM review WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM dailyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM weeklyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthlyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM yearlyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM healthtracker WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthtracker WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM studytracker WHERE fixed = 1 AND deleted = 0) ORDER BY editdate DESC",null);

        listTotal.clear();
        listFixed.clear();

        while (cursor.moveToNext()) {
            //전체메모
            TotalMemoData totalMemoData = new TotalMemoData();
            totalMemoData.setTotalID(cursor.getInt(0));
            totalMemoData.setTotalTitle(cursor.getString(1));
            totalMemoData.setTotalDate(cursor.getString(2).substring(0, 10));
            totalMemoData.setTemplate(cursor.getString(3));

            listTotal.add(totalMemoData);
        }

        while (cursorFixed.moveToNext()) {
            //전체메모의 고정메모
            TotalMemoData fixedData = new TotalMemoData();
            fixedData.setTotalID(cursorFixed.getInt(0));
            fixedData.setTotalTitle(cursorFixed.getString(1));
            fixedData.setTotalDate(cursorFixed.getString(2).substring(0, 10));
            fixedData.setTemplate(cursorFixed.getString(3));
            listFixed.add(fixedData);
        }

        totalFixedMemoAdapter.notifyDataSetChanged();
        totalMemoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM (SELECT id, title, editdate, template_case FROM nonlinememo WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM linememo WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM gridmemo WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM todolist WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM wishlist WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM shoppinglist WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM review WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM dailyplan WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM weeklyplan WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthlyplan WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM yearlyplan WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM healthtracker WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthtracker WHERE deleted = 0 AND fixed = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM studytracker WHERE deleted = 0 AND fixed = 0) ORDER BY editdate DESC ",null);

        Cursor cursorFixed = db.rawQuery("SELECT * FROM (SELECT id, title, editdate, template_case FROM nonlinememo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM linememo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM gridmemo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM todolist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM wishlist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM shoppinglist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM review WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM dailyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM weeklyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthlyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM yearlyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM healthtracker WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthtracker WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM studytracker WHERE fixed = 1 AND deleted = 0) ORDER BY editdate DESC",null);

        listTotal = new ArrayList<>();
        listFixed = new ArrayList<>();

        while (cursor.moveToNext()) {
            //전체메모
            TotalMemoData totalMemoData = new TotalMemoData();
            totalMemoData.setTotalID(cursor.getInt(0));
            totalMemoData.setTotalTitle(cursor.getString(1));
            totalMemoData.setTotalDate(cursor.getString(2).substring(0, 10));
            totalMemoData.setTemplate(cursor.getString(3));

            listTotal.add(totalMemoData);

        }

        while (cursorFixed.moveToNext()) {
            //전체메모의 고정메모
            TotalMemoData fixedData = new TotalMemoData();
            fixedData.setTotalID(cursorFixed.getInt(0));
            fixedData.setTotalTitle(cursorFixed.getString(1));
            fixedData.setTotalDate(cursorFixed.getString(2).substring(0, 10));
            fixedData.setTemplate(cursorFixed.getString(3));
            listFixed.add(fixedData);
        }
    }
}