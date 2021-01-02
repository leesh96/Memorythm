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

public class TrashFragAdapter extends RecyclerView.Adapter<TrashFragAdapter.ViewHolder> {
    Context trashContext;
    private ArrayList<MemoData> trashList;
    private Map<MemoData, Boolean> mCheckedMap = new HashMap<>();
    private List<MemoData> mCheckedTrash = new ArrayList<>(); //체크한 항목 저장
    public boolean isTrash = false;

    public TrashFragAdapter(Context mcontext, ArrayList<MemoData> list) {
        this.trashContext = mcontext;
        this.trashList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(trashContext).inflate(R.layout.list_trash,parent, false);
        ViewHolder vHolder = new ViewHolder(view);
        vHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                MemoData trashData = trashList.get(vHolder.getAdapterPosition());
                mCheckedMap.put(trashData,isChecked);
                // 체크된거 mCheckedTrash 넣기
                if(isChecked){
                    mCheckedTrash.add(trashData);
                }else {
                    mCheckedTrash.remove(trashData);
                }
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MemoData trashData = trashList.get(position);

        holder.titleTV.setText(trashList.get(position).getMemoTitle());
        holder.DateTV.setText(trashList.get(position).getMemoDate());
        //체크박스 체크 여부
        boolean isChecked = mCheckedMap.get(trashData)==null?false:mCheckedMap.get(trashData);
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
    }

    @Override
    public int getItemCount() {
        return (null != trashList ? trashList.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV, DateTV;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView)itemView.findViewById(R.id.trashName);
            this.DateTV = (TextView)itemView.findViewById(R.id.trashDate);
            this.checkBox = (CheckBox)itemView.findViewById(R.id.trashCheckBox);
        }
    }
    // 아이템 삭제
    public boolean removeItems(){
        boolean result = trashList.removeAll(mCheckedTrash);
        if(result){ notifyDataSetChanged(); }
        return result;
    }
    // 아이템 갱신에 사용
    public void setItems(ArrayList<MemoData> items){
        trashList = items;
    }
    //체크박스 숨김에 사용
    public void setVisible(boolean trash){
        isTrash = trash;
    }
    //체크한 항목 전달
    public List<MemoData> setCheckBox(){
        return mCheckedTrash;
    }

}
