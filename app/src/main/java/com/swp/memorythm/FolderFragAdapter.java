package com.swp.memorythm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FolderFragAdapter extends RecyclerView.Adapter<FolderFragAdapter.ViewHolder> implements ItemTouchHelperListener{

    Context mContext;

    private ArrayList<Folder> dataFolder;
    private Map<Folder, Boolean> mCheckedMap = new HashMap<>();
    private List<Folder> mCheckedFolder = new ArrayList<>(); //체크한 항목 저장
    public boolean isTrash = false;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private boolean trigger = false;

    public FolderFragAdapter() {
    }

    public FolderFragAdapter(Context mContext, ArrayList<Folder> listFolder) {
        this.mContext = mContext;
        this.dataFolder = listFolder;
        dbHelper = new DBHelper(mContext);
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_folder_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (viewHolder.getAdapterPosition() == 0){
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setMessage("메모폴더는 삭제가 안됩니다.");
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }else {
                    Folder folder = dataFolder.get(viewHolder.getAdapterPosition());
                    mCheckedMap.put(folder,isChecked);
                    // 체크된거 mCheckedFolder 넣기
                    if(isChecked){
                        mCheckedFolder.add(folder);
                    }else {
                        mCheckedFolder.remove(folder);
                    }
                }

            }
        });

        return viewHolder;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Folder folder = dataFolder.get(position);

        holder.titleTV.setText(dataFolder.get(position).getTitle());
        holder.numTV.setText(String.valueOf(dataFolder.get(position).getCount()));
        //체크박스 체크 여부
        boolean isChecked = mCheckedMap.get(folder)==null?false:mCheckedMap.get(folder);
        holder.checkBox.setChecked(isChecked);

        //리사이클러뷰 아이템 클릭
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(view -> {
            // 프레그먼트 교체하는 부분
            FragmentActivity activity = (FragmentActivity)view.getContext();
            MemoListFragment memoListFragment = new MemoListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("folder", dataFolder.get(position).getTitle());
            bundle.putString("case", "folder");
            memoListFragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.folderID,memoListFragment).addToBackStack(null).commit();
            notifyDataSetChanged();
        });
        //삭제
        if(isTrash) {
            holder.numTV.setVisibility(View.GONE);
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
        }
        else {
            holder.numTV.setVisibility(View.VISIBLE);
            holder.checkBox.setVisibility(View.GONE);
            holder.itemView.setClickable(true);
        }
    }
    //getItemCount() : 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return (null != dataFolder ? dataFolder.size():0);
    }

    //FolderFragment 에서 객체를 생성한 후에 리스트를 입력하여 어댑터의 dataFolder 에 매치
    @Override
    public boolean onItemMove(int from_position, int to_position) {

        if(!trigger) {

            if(to_position == 0 || from_position == 0){
                trigger = true;
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setMessage("메모폴더는 고정입니다.");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                        trigger = false;
                    }
                });
                alert.show();

            }else {
                Folder folder = dataFolder.get(from_position);
                dataFolder.remove(from_position);
                dataFolder.add(to_position, folder);
                notifyItemMoved(from_position, to_position); // 데이터가 이동함을 알림

                db = dbHelper.getWritableDatabase();
                for (int i = 0; i < dataFolder.size(); i++) {
                    String folder_name = dataFolder.get(i).getTitle();
                    db.execSQL("UPDATE folder SET sequence = '"+i+"' WHERE name = '"+ folder_name +"';");
                }
                trigger = false;
            }
        } else {

            return true;
        }

        return true;
    }
    // 사용 안 함
    @Override
    public void onItemSwipe(int position) { }
    // 사용 안 함
    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) { }

    //ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTV, numTV;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView) itemView.findViewById(R.id.folderName);
            this.numTV = (TextView)itemView.findViewById(R.id.folderNum);
            this.checkBox = (CheckBox)itemView.findViewById(R.id.folderCheckBox);
        }

    }

    // 아이템 삭제
    public boolean removeItems(){
        boolean result = dataFolder.removeAll(mCheckedFolder);
        if(result){ notifyDataSetChanged(); }
        return  result;
    }
    // 아이템 갱신에 사용
    public void setItems(ArrayList<Folder> items){ dataFolder = items; }
    //체크박스 숨김에 사용
    public void setVisible(boolean trash){
        isTrash = trash;
    }
    //체크박스 체크항목 전달
    public List<Folder> setCheckBox(){
        return mCheckedFolder;
    }
}
