package com.swp.memorythm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoListFragment extends Fragment {
    private RecyclerView memoRecyclerView;
    private ArrayList<MemoData> listMemo;
    private MemoListAdapter memoListAdapter;
    private ImageButton btnDelete;
    private Boolean check = false;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_list, container, false);
        //중간부분
        memoRecyclerView = (RecyclerView)view.findViewById(R.id.memoListBottomRV);
        memoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        memoListAdapter = new MemoListAdapter(getContext(),listMemo);
        memoRecyclerView.setAdapter(memoListAdapter);

        //삭제버튼
        btnDelete = (ImageButton)view.findViewById(R.id.memoDelBtn);
        btnDelete.setOnClickListener(view1 -> {
            if(check){
                if((memoListAdapter.removeItems())){
                    Toast.makeText(getContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "삭제할 폴더를 선택하세요", Toast.LENGTH_SHORT).show();
                }
                memoListAdapter.setVisible(false);
                memoListAdapter.notifyDataSetChanged();
                check = false;
            }else {
                memoListAdapter.setVisible(true);
                memoListAdapter.notifyDataSetChanged();
                check = true;
            }

        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //가운데 부분
        listMemo = new ArrayList<>();
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT id, title, editdate from nonlinememo WHERE deleted = 0 ORDER BY editdate DESC", null);
            while (cursor.moveToNext()) {
                MemoData memoData = new MemoData();

                memoData.setMemoid(cursor.getInt(0));
                memoData.setMemoTitle(cursor.getString(1));
                memoData.setMemoDate(cursor.getString(2).substring(0, 10));

                listMemo.add(memoData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
        }
    }
}

