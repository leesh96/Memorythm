package com.swp.memorythm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TrashFragment extends Fragment implements View.OnClickListener {
    private RecyclerView trashRecyclerView;
    private TrashFragAdapter trashAdapter;
    private ImageButton btnRestore, btnEmpty;
    private TextView empty;
    private ArrayList<MemoData> trashList;
    private Boolean check = false;

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trash, container, false);
        // 빈 휴지통일때
        empty = (TextView) view.findViewById(R.id.emptyTrashText);

        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT id, title, editdate, template_case FROM nonlinememo WHERE deleted = 1 UNION ALL " +
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
                    "SELECT id, title, editdate, template_case FROM studytracker WHERE deleted = 1 ", null);

            trashList = new ArrayList<>();
            while (cursor.moveToNext()) {
                MemoData memoData = new MemoData();
                memoData.setMemoid(cursor.getInt(0));
                memoData.setMemoTitle(cursor.getString(1));
                memoData.setMemoDate(cursor.getString(2).substring(0, 10));
                memoData.setTemplate(cursor.getString(3));
                trashList.add(memoData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (trashList.size() == 0) {
            empty.setVisibility(View.VISIBLE);
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
                if (check) {
                    if (trashAdapter.removeItems()) {
                        List<MemoData> list = trashAdapter.setCheckBox();
                        for (MemoData memoData : list) {
                            String table = memoData.getTemplate();
                            int tableID = memoData.getMemoid();
                            try {
                                db.execSQL("DELETE FROM '" + table + "' WHERE id = '" + tableID + "';");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (trashAdapter.getItemCount() == 0) {
                                empty.setVisibility(View.VISIBLE);
                            }
                        }
                        trashAdapter.setVisible(false);
                        btnRestore.setVisibility(View.VISIBLE);
                        trashAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        trashAdapter.setVisible(true);
                        btnRestore.setVisibility(View.INVISIBLE);
                        trashAdapter.notifyDataSetChanged();
                        check = false;
                        Toast.makeText(getContext(), "삭제할 메모를 선택하세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    trashAdapter.setVisible(true);
                    btnRestore.setVisibility(View.INVISIBLE);
                    trashAdapter.notifyDataSetChanged();
                    check = true;
                }
                break;

            case R.id.restoreBtn:
                if (check) {
                    if (trashAdapter.removeItems()) {
                        List<MemoData> list = trashAdapter.setCheckBox();
                        for (MemoData memoData : list) {
                            String table = memoData.getTemplate();
                            int tableID = memoData.getMemoid();

                            try {
                                db.execSQL("UPDATE '" + table + "' SET deleted = 0 WHERE id = '" + tableID + "';");
                                Cursor cursor = db.rawQuery("SELECT folder_name FROM '" + table + "' WHERE id = '" + tableID + "'", null);
                                cursor.moveToFirst();
                                String folder = cursor.getString(0);
                                db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '" + folder + "';");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (trashAdapter.getItemCount() == 0) {
                                empty.setVisibility(View.VISIBLE);
                            }
                        }
                        trashAdapter.setVisible(false);
                        btnEmpty.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "복구되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        trashAdapter.setVisible(true);
                        btnEmpty.setVisibility(View.INVISIBLE);
                        trashAdapter.notifyDataSetChanged();
                        check = false;
                        Toast.makeText(getContext(), "복구할 메모를 선택하세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    trashAdapter.setVisible(true);
                    btnEmpty.setVisibility(View.INVISIBLE);
                    trashAdapter.notifyDataSetChanged();
                    check = true;
                }
                break;
        }
    }
}