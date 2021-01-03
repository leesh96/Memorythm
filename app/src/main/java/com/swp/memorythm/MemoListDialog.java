package com.swp.memorythm;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoListDialog extends Dialog {
    private OnDialogListener listener;
    private Context context;
    private RecyclerView fNameRecyclerView;
    private MemoListDialogAdapter memoListDialogAdapter;
    private ArrayList<Folder> folderList;
    private Button transfercancelBtn;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public MemoListDialog(Context context, final int position, MemoData memoData) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_memo_list);

        fNameRecyclerView = (RecyclerView)findViewById(R.id.dialogRV);
        fNameRecyclerView.setHasFixedSize(true);
        fNameRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT name FROM folder ORDER BY sequence ",null);
        folderList = new ArrayList<>();
        cursor.moveToFirst();
        String name = cursor.getString(0);
        folderList.add(new Folder(name));
        while(cursor.moveToNext()) {
            name = cursor.getString(0);
            folderList.add(new Folder(name));
        }
        memoListDialogAdapter = new MemoListDialogAdapter(getContext(),folderList);
        fNameRecyclerView.setAdapter(memoListDialogAdapter);

        //취소버튼
        transfercancelBtn = findViewById(R.id.btnTransferCancel);
        transfercancelBtn.setOnClickListener(view -> {
            dismiss();
        });


    }
    public void setDialogListener(OnDialogListener listener){ this.listener = listener; }

}
