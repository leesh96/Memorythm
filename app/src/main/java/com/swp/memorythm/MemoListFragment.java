package com.swp.memorythm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MemoListFragment extends Fragment {
    private RecyclerView memoRecyclerView;
    private ArrayList<MemoData> listMemo;
    private MemoListAdapter memoListAdapter;
    private ImageButton btnDelete;
    private TextView empty;
    private Boolean check = false;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private ItemTouchHelper helper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_list, container, false);
        //빈폴더일때
        empty = (TextView) view.findViewById(R.id.emptyFolderText);
        //recyclerview
        memoRecyclerView = (RecyclerView) view.findViewById(R.id.memoListBottomRV);
        memoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        memoListAdapter = new MemoListAdapter(getContext(), listMemo);
        memoRecyclerView.setAdapter(memoListAdapter);

        if (listMemo.size() == 0) {
            empty.setVisibility(View.VISIBLE);
        }
        //삭제버튼
        btnDelete = (ImageButton) view.findViewById(R.id.memoDelBtn);
        btnDelete.setOnClickListener(view1 -> {
            if (check) {
                if ((memoListAdapter.removeItems())) {
                    List<MemoData> list = memoListAdapter.setCheckBox();
                    for (MemoData memoData : list) {
                        String table = memoData.getTemplate();
                        int tableID = memoData.getMemoid();
                        db.execSQL("UPDATE '" + table + "' SET deleted = 1 WHERE id = '" + tableID + "';");
                        Cursor cursor1 = db.rawQuery("SELECT folder_name FROM '" + table + "' WHERE id = '" + tableID + "'", null);
                        cursor1.moveToFirst();
                        String folderN = cursor1.getString(0);
                        db.execSQL("UPDATE folder SET count = count - 1 WHERE name = '" + folderN + "';");
                        listMemo.remove(memoData);
                    }
                    // 아무것도 없을때 없음 표시
                    if (listMemo.size() == 0) {
                        empty.setVisibility(View.VISIBLE);
                    }
                    memoListAdapter.setVisible(false);
                    memoListAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    memoListAdapter.setVisible(true);
                    memoListAdapter.notifyDataSetChanged();
                    check = false;
                    Toast.makeText(getContext(), "삭제할 메모를 선택하세요", Toast.LENGTH_SHORT).show();
                }
            } else {
                memoListAdapter.setVisible(true);
                memoListAdapter.notifyDataSetChanged();
                check = true;
            }
        });
        //ItemTouchHelper 생성
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(memoListAdapter));
        //RecyclerView에 ItemTouchHelper 붙이기
        helper.attachToRecyclerView(memoRecyclerView);
        //아이템 갱신
        memoListAdapter.setItems(listMemo);
        memoListAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listMemo = new ArrayList<>();
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();
        if (getArguments() != null) {
            String Case = getArguments().getString("case");
            switch (Case) {
                case "folder":
                    String Folder = getArguments().getString("folder");
                    try {
                        Cursor cursor = db.rawQuery("SELECT id, title, editdate, template_case FROM nonlinememo WHERE deleted = 0 and folder_name = '" + Folder + "' UNION ALL " +
                                "SELECT id, title, editdate, template_case FROM linememo WHERE deleted = 0 and folder_name = '" + Folder + "' UNION ALL " +
                                "SELECT id, title, editdate, template_case FROM gridmemo WHERE deleted = 0 and folder_name = '" + Folder + "' UNION ALL " +
                                "SELECT id, title, editdate, template_case FROM todolist WHERE deleted = 0 and folder_name = '" + Folder + "' UNION ALL " +
                                "SELECT id, title, editdate, template_case FROM wishlist WHERE deleted = 0 and folder_name = '" + Folder + "' UNION ALL " +
                                "SELECT id, title, editdate, template_case FROM shoppinglist WHERE deleted = 0 and folder_name = '" + Folder + "' UNION ALL " +
                                "SELECT id, title, editdate, template_case FROM review WHERE deleted = 0 and folder_name = '" + Folder + "' UNION ALL " +
                                "SELECT id, title, editdate, template_case FROM dailyplan WHERE deleted = 0 and folder_name = '" + Folder + "' UNION ALL " +
                                "SELECT id, title, editdate, template_case FROM weeklyplan WHERE deleted = 0 and folder_name = '" + Folder + "' UNION ALL " +
                                "SELECT id, title, editdate, template_case FROM monthlyplan WHERE deleted = 0 and folder_name = '" + Folder + "' UNION ALL " +
                                "SELECT id, title, editdate, template_case FROM yearlyplan WHERE deleted = 0 and folder_name = '" + Folder + "' UNION ALL " +
                                "SELECT id, title, editdate, template_case FROM healthtracker WHERE deleted = 0 and folder_name = '" + Folder + "' UNION ALL " +
                                "SELECT id, title, editdate, template_case FROM monthtracker WHERE deleted = 0 and folder_name = '" + Folder + "' UNION ALL " +
                                "SELECT id, title, editdate, template_case FROM studytracker WHERE deleted = 0 and folder_name = '" + Folder + "' ", null);

                        while (cursor.moveToNext()) {
                            MemoData memoData = new MemoData();
                            memoData.setMemoid(cursor.getInt(0));
                            memoData.setMemoTitle(cursor.getString(1));
                            memoData.setMemoDate(cursor.getString(2).substring(0, 10));
                            memoData.setTemplate(cursor.getString(3));
                            listMemo.add(memoData);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "template":
                    String Template = getArguments().getString("template");
                    try {
                        Cursor cursor = db.rawQuery("SELECT id, title, editdate from " + Template + " WHERE deleted = 0 ORDER BY editdate DESC", null);
                        while (cursor.moveToNext()) {
                            MemoData memoData = new MemoData();
                            memoData.setMemoid(cursor.getInt(0));
                            memoData.setMemoTitle(cursor.getString(1));
                            memoData.setMemoDate(cursor.getString(2).substring(0, 10));
                            memoData.setTemplate(Template);
                            listMemo.add(memoData);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void setUpRecyclerView() {
        memoRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                helper.onDraw(c, parent, state);
            }
        });
    }

}

