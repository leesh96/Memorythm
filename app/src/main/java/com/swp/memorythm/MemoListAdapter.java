package com.swp.memorythm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swp.memorythm.template.MemoViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoListAdapter extends RecyclerView.Adapter<MemoListAdapter.ViewHolder> implements ItemTouchHelperListener {
    Context memoContext;
    private ArrayList<MemoData> listMemo;
    private Map<MemoData, Boolean> memoCheckedMap = new HashMap<>();
    private ArrayList<MemoData> mCheckedMemo = new ArrayList<>(); //체크한 항목 저장
    public boolean isTrash = false;

    public MemoListAdapter(Context memoContext, ArrayList<MemoData> listMemo) {
        this.listMemo = listMemo;
        this.memoContext = memoContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(memoContext).inflate(R.layout.list_memo, parent, false);
        ViewHolder vHolder = new ViewHolder(view);
        vHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                MemoData memoData = listMemo.get(vHolder.getAdapterPosition());
                memoCheckedMap.put(memoData, isChecked);
                // 체크된거 mCheckedTrash 넣기
                if (isChecked) {
                    mCheckedMemo.add(memoData);
                } else {
                    mCheckedMemo.remove(memoData);
                }
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MemoData memoData = listMemo.get(position);
        holder.titleTV.setText(listMemo.get(position).getMemoTitle());
        holder.DateTV.setText(listMemo.get(position).getMemoDate());
        //체크박스 체크 여부
        boolean isChecked = memoCheckedMap.get(memoData) == null ? false : memoCheckedMap.get(memoData);
        holder.checkBox.setChecked(isChecked);
        //삭제
        if (isTrash) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
        } else {
            holder.checkBox.setVisibility(View.INVISIBLE);
            //holder.itemView.setClickable(true);
            //아이템 클릭
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(memoContext, MemoViewActivity.class);
                intent.putExtra("memoid", listMemo.get(position).getMemoid());
                intent.putExtra("memotitle", listMemo.get(position).getMemoTitle());
                intent.putExtra("template", listMemo.get(position).getTemplate());
                intent.putExtra("mode", "view");
                memoContext.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return (null != listMemo ? listMemo.size() : 0);
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        return false;
    }

    @Override
    public void onItemSwipe(int position) {
        listMemo.remove(position);
        notifyItemRemoved(position);
    }

    // 왼쪽으로 스와이프 시 생기는 오른쪽 버튼
    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {
        // 버튼 클릭시 다이얼로그 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(memoContext);
        View dialogView = LayoutInflater.from(memoContext).inflate(R.layout.dialog_memo_list, null, false);
        builder.setView(dialogView);

        DBHelper dbHelper = new DBHelper(memoContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<String> folderlist = new ArrayList<>();
        String currentFolder = null;

        try {
            Cursor cursor = db.rawQuery("SELECT name FROM folder ORDER BY sequence", null);
            while (cursor.moveToNext()) {
                folderlist.add(cursor.getString(0));
            }
            cursor = db.rawQuery("SELECT folder_name FROM " + listMemo.get(position).getTemplate() + " WHERE id = " + listMemo.get(position).getMemoid(), null);
            cursor.moveToFirst();
            currentFolder = cursor.getString(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Spinner spinner = dialogView.findViewById(R.id.folder_spinner);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(memoContext, R.layout.support_simple_spinner_dropdown_item, folderlist);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(getIndex(spinner, currentFolder));

        Button btnApply, btnCancel;
        btnApply = dialogView.findViewById(R.id.btn_apply);
        btnCancel = dialogView.findViewById(R.id.btn_cancel);

        AlertDialog folderChange = builder.create();
        folderChange.show();
        notifyItemChanged(position);

        String finalCurrentFolder = currentFolder;
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldFolder = finalCurrentFolder;
                String changeFolder = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                try {
                    db.execSQL("UPDATE " + listMemo.get(position).getTemplate() + " SET folder_name = '" + changeFolder + "' WHERE id = " + listMemo.get(position).getMemoid() + ";");
                    db.execSQL("UPDATE folder SET count = count - 1 WHERE name = '" + oldFolder + "';");
                    db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '" + changeFolder + "';");
                    listMemo.remove(position);
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                folderChange.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                folderChange.dismiss();
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV, DateTV;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView) itemView.findViewById(R.id.memoName);
            this.DateTV = (TextView) itemView.findViewById(R.id.memoDate);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.memoCheckBox);
        }
    }

    // 아이템 삭제
    public boolean removeItems() {
        boolean result = listMemo.removeAll(mCheckedMemo);
        if (result) {
            notifyDataSetChanged();
        }
        return result;
    }

    //체크박스 숨김에 사용
    public void setVisible(boolean trash) {
        isTrash = trash;
    }

    //체크박스 체크항목 전달
    public List<MemoData> setCheckBox() {
        return mCheckedMemo;
    }

    // 아이템 갱신에 사용
    public void setItems(ArrayList<MemoData> items) {
        listMemo = items;
    }

    private int getIndex(Spinner spinner, String folderName) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(folderName)) {
                return i;
            }
        }
        return 0;
    }
}

