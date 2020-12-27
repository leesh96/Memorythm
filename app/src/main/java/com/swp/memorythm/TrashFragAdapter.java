package com.swp.memorythm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TrashFragAdapter extends RecyclerView.Adapter<TrashFragAdapter.ViewHolder> {
    ArrayList<MemoData> trashList;
    Context trashContext;

    public TrashFragAdapter(Context mcontext, ArrayList<MemoData> list) {
        this.trashContext = mcontext;
        this.trashList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(trashContext).inflate(R.layout.list_trash,parent, false);
        ViewHolder vHolder = new ViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTV.setText(trashList.get(position).getMemoTitle());
        holder.DateTV.setText(trashList.get(position).getMemoDate());

    }

    @Override
    public int getItemCount() {
        return (null != trashList ? trashList.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV, DateTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView)itemView.findViewById(R.id.trashMemoN);
            this.DateTV = (TextView)itemView.findViewById(R.id.trashDate);
        }
    }
}
