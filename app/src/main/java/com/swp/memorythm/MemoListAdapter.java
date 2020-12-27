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

public class MemoListAdapter extends RecyclerView.Adapter<MemoListAdapter.ViewHolder> {

    Context memoContext;
    private ArrayList<MemoData> listMemo;
    private Map<MemoData, Boolean> memoCheckedMap = new HashMap<>();
    private List<MemoData> mCheckedMemo = new ArrayList<>(); //체크한 항목 저장

    public MemoListAdapter(Context memoContext,ArrayList<MemoData> listMemo) {
        this.listMemo = listMemo;
        this.memoContext = memoContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(memoContext).inflate(R.layout.list_memo,parent, false);
        ViewHolder vHolder = new ViewHolder(view);

        vHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                MemoData memoData = listMemo.get(vHolder.getAdapterPosition());
                memoCheckedMap.put(memoData, isChecked);
                // 체크된거 mCheckedTrash 넣기
                if(isChecked){
                    mCheckedMemo.add(memoData);
                }else {
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
        boolean isChecked = memoCheckedMap.get(memoData)==null?false:memoCheckedMap.get(memoData);
        holder.checkBox.setChecked(isChecked);
    }

    @Override
    public int getItemCount() {
        return (null != listMemo ? listMemo.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV, DateTV;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView)itemView.findViewById(R.id.memoName);
            this.DateTV = (TextView)itemView.findViewById(R.id.memoDate);
            this.checkBox = (CheckBox)itemView.findViewById(R.id.memoCheckBox);
        }
    }
    // 아이템 삭제
    public boolean removeItems(){
        boolean result = listMemo.removeAll(mCheckedMemo);
        if(result){ notifyDataSetChanged(); }
        return result;
    }
}
