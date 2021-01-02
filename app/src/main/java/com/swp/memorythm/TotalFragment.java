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

    private RecyclerView totalRecyclerView;
    private TotalMemoAdapter totalMemoAdapter;
    private ArrayList<TotalMemoData> listTotal;
    private ImageButton btnDelete;
    private Boolean check = false;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total, container, false);
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, title, editdate, template_case FROM nonlinememo WHERE deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM linememo WHERE deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM gridmemo WHERE deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM todolist WHERE deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM wishlist WHERE deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM shoppinglist WHERE deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM review WHERE deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM dailyplan WHERE deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM weeklyplan WHERE deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthlyplan WHERE deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM yearlyplan WHERE deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM healthtracker WHERE deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthtracker WHERE deleted = 0 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM studytracker WHERE deleted = 0 ",null);

        listTotal = new ArrayList<>();

        while (cursor.moveToNext()) {
            TotalMemoData totalMemoData = new TotalMemoData();
            totalMemoData.setTotalID(cursor.getInt(0));
            totalMemoData.setTotalTitle(cursor.getString(1));
            totalMemoData.setTotalDate(cursor.getString(2).substring(0, 10));
            totalMemoData.setTemplate(cursor.getString(3));

            listTotal.add(totalMemoData);
        }

//        db.execSQL("INSERT INTO ");

        // recyclerview
        totalRecyclerView = (RecyclerView)view.findViewById(R.id.totalRV);
        totalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        totalMemoAdapter = new TotalMemoAdapter(getContext(),listTotal);
        totalRecyclerView.setAdapter(totalMemoAdapter);
        //삭제버튼
        btnDelete = (ImageButton)view.findViewById(R.id.totalDelBtn);
        btnDelete.setOnClickListener(view1 -> {
            if(check){
                if((totalMemoAdapter.removeItems())){
                    List<TotalMemoData> list = totalMemoAdapter.setCheckBox();

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

                    Toast.makeText(getContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "삭제할 폴더를 선택하세요", Toast.LENGTH_SHORT).show();
                }
                totalMemoAdapter.setVisible(false);
                totalMemoAdapter.notifyDataSetChanged();
                check = false;
            }else {
                totalMemoAdapter.setVisible(true);
                totalMemoAdapter.notifyDataSetChanged();
                check = true;
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}