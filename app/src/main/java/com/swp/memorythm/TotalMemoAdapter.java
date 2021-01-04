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

import com.swp.memorythm.template.MemoViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalMemoAdapter extends RecyclerView.Adapter<TotalMemoAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<TotalMemoData> totalMemo;
    private Map<TotalMemoData, Boolean> memoTotalCheckedMap = new HashMap<>();
    private List<TotalMemoData> mCheckedMemoTotal = new ArrayList<>(); //체크한 항목 저장
    public boolean isTrash = false;

    public TotalMemoAdapter(Context mContext, ArrayList<TotalMemoData> listTopMemo) {
        this.mContext = mContext;
        this.totalMemo = listTopMemo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_total_memo, parent, false);
        ViewHolder vHolder = new ViewHolder(view);
        vHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                TotalMemoData topMemoData = totalMemo.get(vHolder.getAdapterPosition());
                memoTotalCheckedMap.put(topMemoData, isChecked);
                // 체크된거 mCheckedTrash 넣기
                if (isChecked) {
                    mCheckedMemoTotal.add(topMemoData);
                } else {
                    mCheckedMemoTotal.remove(topMemoData);
                }
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TotalMemoData totalMemoData = totalMemo.get(position); //위치 받아오기
        holder.titleTV.setText(totalMemo.get(position).getTotalTitle());
        holder.dataTV.setText(totalMemo.get(position).getTotalDate());
        //체크박스 체크 여부
        boolean isChecked = memoTotalCheckedMap.get(totalMemoData) == null ? false : memoTotalCheckedMap.get(totalMemoData);
        holder.checkBox.setChecked(isChecked);
        //삭제
        if (isTrash) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
        } else {
            holder.checkBox.setVisibility(View.INVISIBLE);
            holder.itemView.setClickable(true);
        }
        //아이템 클릭
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, MemoViewActivity.class);
            intent.putExtra("memoid", totalMemo.get(position).getTotalID());
            intent.putExtra("memotitle", totalMemo.get(position).getTotalTitle());
            intent.putExtra("template", totalMemo.get(position).getTemplate());
            intent.putExtra("mode", "view");
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (null != totalMemo ? totalMemo.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV, dataTV;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView) itemView.findViewById(R.id.memoTotalName);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.memoTotalCheckBox);
            this.dataTV = (TextView) itemView.findViewById(R.id.totalDate);
        }
    }

    // 아이템 삭제
    public boolean removeItems() {
        boolean result = totalMemo.removeAll(mCheckedMemoTotal);
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
    public List<TotalMemoData> setCheckBox() {
        return mCheckedMemoTotal;
    }
}
