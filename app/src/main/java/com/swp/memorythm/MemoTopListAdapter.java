package com.swp.memorythm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoTopListAdapter extends RecyclerView.Adapter<MemoTopListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<TopMemoData> listTopMemo;
    private Map<TopMemoData, Boolean> memoTopCheckedMap = new HashMap<>();
    private List<TopMemoData> mCheckedMemoTop = new ArrayList<>(); //체크한 항목 저장
    public boolean isTrash = false;

    public MemoTopListAdapter(Context mContext, ArrayList<TopMemoData> listTopMemo) {
        this.mContext = mContext;
        this.listTopMemo = listTopMemo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_memo_top,parent, false);
        ViewHolder vHolder = new ViewHolder(view);
        vHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                TopMemoData topMemoData = listTopMemo.get(vHolder.getAdapterPosition());
                memoTopCheckedMap.put(topMemoData, isChecked);
                // 체크된거 mCheckedTrash 넣기
                if(isChecked){
                    mCheckedMemoTop.add(topMemoData);
                }else {
                    mCheckedMemoTop.remove(topMemoData);
                }
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopMemoData topMemoData = listTopMemo.get(position); //위치 받아오기
        holder.titleTV.setText(listTopMemo.get(position).getTopMemoTitle());
        //체크박스 체크 여부
        boolean isChecked = memoTopCheckedMap.get(topMemoData)==null?false:memoTopCheckedMap.get(topMemoData);
        holder.checkBox.setChecked(isChecked);
        //삭제
        if(isTrash) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
        }
        else {
            holder.checkBox.setVisibility(View.GONE);
            holder.itemView.setClickable(true);
        }
    }

    @Override
    public int getItemCount() {
        return (null != listTopMemo ? listTopMemo.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView)itemView.findViewById(R.id.memoTopName);
            this.checkBox = (CheckBox)itemView.findViewById(R.id.memoTopCheckBox);
        }
    }
    // 아이템 삭제
    public boolean removeItems(){
        boolean result = listTopMemo.removeAll(mCheckedMemoTop);
        if(result){ notifyDataSetChanged(); }
        return result;
    }
    //체크박스 숨김에 사용
    public void setVisible(boolean trash){
        isTrash = trash;
    }
}
