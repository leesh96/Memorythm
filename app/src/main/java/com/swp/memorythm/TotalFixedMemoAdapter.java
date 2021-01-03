package com.swp.memorythm;

import android.content.Context;
import android.content.Intent;
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

public class TotalFixedMemoAdapter extends RecyclerView.Adapter<TotalFixedMemoAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<TotalMemoData> fixedMemo;
    private Map<TotalMemoData, Boolean> fixedCheckedMap = new HashMap<>();
    private List<TotalMemoData> mCheckedFixed = new ArrayList<>(); //체크한 항목 저장
    public boolean isTrash = false;

    public TotalFixedMemoAdapter(Context mContext, ArrayList<TotalMemoData> fixedMemo) {
        this.mContext = mContext;
        this.fixedMemo = fixedMemo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_total_memo,parent, false);
        ViewHolder vHolder = new TotalFixedMemoAdapter.ViewHolder(view);
        vHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                TotalMemoData fixedData = fixedMemo.get(vHolder.getAdapterPosition());
                fixedCheckedMap.put(fixedData, isChecked);
                // 체크된거 mCheckedTrash 넣기
                if(isChecked){
                    mCheckedFixed.add(fixedData);
                }else {
                    mCheckedFixed.remove(fixedData);
                }
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TotalMemoData fixedMemoData = fixedMemo.get(position); //위치 받아오기
        holder.titleTV.setText(fixedMemo.get(position).getTotalTitle());
        holder.dataTV.setText(fixedMemo.get(position).getTotalDate());
        //체크박스 체크 여부
        boolean isChecked = fixedCheckedMap.get(fixedMemoData) == null ? false : fixedCheckedMap.get(fixedMemoData);
        holder.checkBox.setChecked(isChecked);
        //삭제
        if(isTrash) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
        }
        else {
            holder.checkBox.setVisibility(View.INVISIBLE);
            holder.itemView.setClickable(true);
        }

        //아이템 클릭
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, MemoViewActivity.class);
            intent.putExtra("memoid", fixedMemo.get(position).getTotalID());
            intent.putExtra("memotitle", fixedMemo.get(position).getTotalTitle());
            intent.putExtra("template", fixedMemo.get(position).getTemplate());
            intent.putExtra("mode", "view");
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return (null != fixedMemo ? fixedMemo.size():0); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV, dataTV;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView)itemView.findViewById(R.id.memoTotalName);
            this.checkBox = (CheckBox)itemView.findViewById(R.id.memoTotalCheckBox);
            this.dataTV = (TextView)itemView.findViewById(R.id.totalDate);
        }
    }
    // 아이템 삭제
    public boolean removeItems(){
        boolean result = fixedMemo.removeAll(mCheckedFixed);
        if(result){ notifyDataSetChanged(); }
        return result;
    }
    //체크박스 숨김에 사용
    public void setVisible(boolean trash){
        isTrash = trash;
    }
    //체크박스 체크항목 전달
    public List<TotalMemoData> setCheckBox(){
        return mCheckedFixed;
    }
}
