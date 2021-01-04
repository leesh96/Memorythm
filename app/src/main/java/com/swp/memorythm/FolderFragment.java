package com.swp.memorythm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//폴더 프레그먼트
public class FolderFragment extends Fragment implements View.OnClickListener {

    private RecyclerView folderRecyclerView;
    private ArrayList<Folder> listFolder;
    private FolderFragAdapter folderFragAdapter;
    private ImageButton addBtn, deleteBtn;
    private ItemTouchHelper helper;
    private Boolean check = false;
    Cursor cursor;

    private DBHelper dbHelper;
    private SQLiteDatabase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder, container, false);
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT name, count FROM folder ORDER BY sequence ", null);
        listFolder = new ArrayList<>();
        cursor.moveToFirst();
        String name = cursor.getString(0);
        int count = cursor.getInt(1);
        listFolder.add(new Folder(name, count));

        while (cursor.moveToNext()) {
            // 기본폴더 생성
            name = cursor.getString(0);
            count = cursor.getInt(1);
            listFolder.add(new Folder(name, count));
        }

        folderRecyclerView = (RecyclerView) view.findViewById(R.id.folderRV);
        folderRecyclerView.setHasFixedSize(true);
        //리니어레이아웃
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        folderRecyclerView.setLayoutManager(manager);

        //어뎁터 연결
        folderFragAdapter = new FolderFragAdapter(getContext(), listFolder);
        folderRecyclerView.setAdapter(folderFragAdapter);
        //추가 버튼 구현
        addBtn = (ImageButton) view.findViewById(R.id.addFolderBtn);
        addBtn.setOnClickListener(this);
        //삭제 버튼
        deleteBtn = (ImageButton) view.findViewById(R.id.deleteFolderBtn);
        deleteBtn.setOnClickListener(this);
        //ItemTouchHelper 생성
        helper = new ItemTouchHelper(new FolderItemTouchHelperCallback(folderFragAdapter));
        //RecyclerView에 ItemTouchHelper 붙이기
        helper.attachToRecyclerView(folderRecyclerView);
        //아이템 갱신
        folderFragAdapter.setItems(listFolder);
        folderFragAdapter.notifyDataSetChanged();

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //  addBtn 누르면 폴더 추가 다이얼로그 생성
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //폴더 추가
            case R.id.addFolderBtn:
                // 데이터를 다이얼로그로 보내는 코드
                Bundle args = new Bundle();
                args.putString("key", "value");

                FragmentDialog dialog = new FragmentDialog(listFolder);
                dialog.setArguments(args); // 데이터 전달
                dialog.show(getActivity().getSupportFragmentManager(), "tag");
                //다이얼로그에서 받아온 데이터
                dialog.setFolderDialogResult(new FragmentDialog.FolderDialogResult() {
                    @Override
                    public void finish(String foldername) {
                        listFolder.add(new Folder(foldername, 0));
                        folderFragAdapter.notifyDataSetChanged();
                    }
                });
                break;
            //삭제 기능 구현하기
            case R.id.deleteFolderBtn:
                if (check) {
                    if (folderFragAdapter.removeItems()) {
                        List<Folder> list = folderFragAdapter.setCheckBox();

                        for (Folder folder : list) {
                            String table = folder.getTitle();
                            int num = folder.getCount();
                            db.execSQL("UPDATE nonlinememo SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE linememo SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE gridmemo SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE todolist SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE wishlist SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE shoppinglist SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE review SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE dailyplan SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE weeklyplan SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE monthlyplan SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE yearlyplan SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE healthtracker SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE monthtracker SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE studytracker SET folder_name = '메모' WHERE deleted = 0 and folder_name = '" + table + "';");
                            db.execSQL("UPDATE folder SET count = count + '" + num + "' WHERE name = '메모';");
                            db.execSQL("DELETE FROM folder WHERE name = '" + table + "';");
                        }
                        folderFragAdapter.setVisible(false);
                        folderFragAdapter.notifyDataSetChanged();
                        check = false;
                        Toast.makeText(getContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                    } else {
                        folderFragAdapter.setVisible(true);
                        folderFragAdapter.notifyDataSetChanged();
                        check = false;
                        Toast.makeText(getContext(), "삭제할 폴더를 선택하세요", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    folderFragAdapter.setVisible(true);
                    folderFragAdapter.notifyDataSetChanged();
                    check = true;
                }
                break;
        }
    }
}