package com.swp.memorythm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FolderFragAdapter extends RecyclerView.Adapter<FolderFragAdapter.ViewHolder> {

    Context mContext;
    private ArrayList<Folder> dataFolder;

    public FolderFragAdapter(Context mContext, ArrayList<Folder> listFolder) {
        this.mContext = mContext;
        this.dataFolder = listFolder;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_folder_view, parent, false);
        ViewHolder vHolder = new ViewHolder(v);
        return vHolder;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.titleTV.setText(listFolder.get(position).getTitle());
//        holder.numTV.setText(listFolder.get(position).getNum());
    }
    //getItemCount() : 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return (null != dataFolder ? dataFolder.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTV;
        TextView numTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView) itemView.findViewById(R.id.folderName);
            this.numTV = (TextView)itemView.findViewById(R.id.folderNum);
        }
    }
}
