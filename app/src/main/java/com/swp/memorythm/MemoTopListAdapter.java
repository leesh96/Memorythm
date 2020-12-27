package com.swp.memorythm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoTopListAdapter extends RecyclerView.Adapter<MemoTopListAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<TopMemoData> listTopMemo;

    public MemoTopListAdapter(Context mContext, ArrayList<TopMemoData> listTopMemo) {
        this.mContext = mContext;
        this.listTopMemo = listTopMemo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_memo_top,parent, false);
        ViewHolder vHolder = new ViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTV.setText(listTopMemo.get(position).getTopMemoTitle());
    }

    @Override
    public int getItemCount() {
        return (null != listTopMemo ? listTopMemo.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView)itemView.findViewById(R.id.memoTopName);
        }
    }
}
