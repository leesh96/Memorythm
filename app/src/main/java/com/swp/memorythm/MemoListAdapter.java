package com.swp.memorythm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoListAdapter extends RecyclerView.Adapter<MemoListAdapter.ViewHolder> {
    ArrayList<MemoData> listMemo;
    Context memoContext;

    public MemoListAdapter(Context memoContext,ArrayList<MemoData> listMemo) {
        this.listMemo = listMemo;
        this.memoContext = memoContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(memoContext).inflate(R.layout.list_memo,parent, false);
        ViewHolder vHolder = new ViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTV.setText(listMemo.get(position).getMemoTitle());
        holder.DateTV.setText(listMemo.get(position).getMemoDate());
    }

    @Override
    public int getItemCount() {
        return (null != listMemo ? listMemo.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV, DateTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView)itemView.findViewById(R.id.MemoName);
            this.DateTV = (TextView)itemView.findViewById(R.id.MemoDate);
        }
    }
}
