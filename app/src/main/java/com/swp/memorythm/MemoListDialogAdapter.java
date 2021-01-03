package com.swp.memorythm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoListDialogAdapter extends RecyclerView.Adapter<MemoListDialogAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Folder> folderList;

    public MemoListDialogAdapter(Context context, ArrayList<Folder> folderList) {
        this.context = context;
        this.folderList = folderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dialog_folder_name, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTV.setText(folderList.get(position).getTitle());
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(view -> {


        });
    }

    @Override
    public int getItemCount() {
        return (null != folderList ? folderList.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView)itemView.findViewById(R.id.dialogFolderName);
        }
    }
}
