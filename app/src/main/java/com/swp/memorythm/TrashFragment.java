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

public class TrashFragment extends Fragment implements View.OnClickListener {
    private RecyclerView trashRecyclerView;
    private TrashFragAdapter trashAdapter;
    private ImageButton btnRestore, btnEmpty;
    private ArrayList<MemoData> trashList;
    private Boolean check = false;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trash, container, false);
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, title, editdate, template_case FROM nonlinememo WHERE deleted = 1 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM linememo WHERE deleted = 1 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM gridmemo WHERE deleted = 1 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM todolist WHERE deleted = 1 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM wishlist WHERE deleted = 1 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM shoppinglist WHERE deleted = 1 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM review WHERE deleted = 1 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM dailyplan WHERE deleted = 1 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM weeklyplan WHERE deleted = 1 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthlyplan WHERE deleted = 1 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM yearlyplan WHERE deleted = 1 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM healthtracker WHERE deleted = 1 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM monthtracker WHERE deleted = 1 UNION ALL " +
                "SELECT id, title, editdate, template_case FROM studytracker WHERE deleted = 1 ",null);

        trashList = new ArrayList<>();

        while (cursor.moveToNext()) {
            MemoData memoData = new MemoData();

            memoData.setMemoid(cursor.getInt(0));
            memoData.setMemoTitle(cursor.getString(1));
            memoData.setMemoDate(cursor.getString(2).substring(0, 10));
            memoData.setTemplate(cursor.getString(3));

            trashList.add(memoData);
        }

        //복구 버튼
        btnRestore = (ImageButton) view.findViewById(R.id.restoreBtn);
        btnRestore.setOnClickListener(this);
        //비우기 버튼
        btnEmpty = (ImageButton) view.findViewById(R.id.emptyBtn);
        btnEmpty.setOnClickListener(this);
        //리사이클러뷰 연결
        trashRecyclerView = (RecyclerView) view.findViewById(R.id.trashRV);
        trashRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        trashAdapter = new TrashFragAdapter(getContext(), trashList);
        trashRecyclerView.setAdapter(trashAdapter);
        // 아이템 갱신
        trashAdapter.setItems(trashList);
        trashAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emptyBtn:
                if(check){
                    if(trashAdapter.removeItems()){
                        List<MemoData> list = trashAdapter.setCheckBox();

                        for (MemoData memoData : list) {
                            String table = memoData.getTemplate();
                            int tableID = memoData.getMemoid();
                            db.execSQL("DELETE FROM '"+table+"' WHERE id = '"+ tableID +"';");
                        }
                        Toast.makeText(getContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "삭제할 메모를 선택하세요", Toast.LENGTH_SHORT).show();
                    }
                    trashAdapter.setVisible(false);
                    btnRestore.setVisibility(View.VISIBLE);
                    trashAdapter.notifyDataSetChanged();
                    check = false;
                }else {
                    trashAdapter.setVisible(true);
                    btnRestore.setVisibility(View.INVISIBLE);
                    trashAdapter.notifyDataSetChanged();
                    check = true;
                }
                break;

            case R.id.restoreBtn:
                if(check){
                    if(trashAdapter.removeItems()){
                        List<MemoData> list = trashAdapter.setCheckBox();

                        for (MemoData memoData : list) {
                            String table = memoData.getTemplate();
                            int tableID = memoData.getMemoid();
                            db.execSQL("UPDATE '"+table+"' SET deleted = 0 WHERE id = '"+ tableID +"';");
                        }
                        Toast.makeText(getContext(), "복구버튼 눌렀음", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "복구할 메모를 선택하세요", Toast.LENGTH_SHORT).show();
                    }
                    trashAdapter.setVisible(false);
                    btnEmpty.setVisibility(View.VISIBLE);
                    trashAdapter.notifyDataSetChanged();
                    check = false;
                }else {
                    trashAdapter.setVisible(true);
                    btnEmpty.setVisibility(View.INVISIBLE);
                    trashAdapter.notifyDataSetChanged();
                    check = true;
                }
                break;

        }
    }
}