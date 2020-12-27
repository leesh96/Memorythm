package com.swp.memorythm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FolderFragAdapter extends RecyclerView.Adapter<FolderFragAdapter.ViewHolder>{

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
        holder.titleTV.setText(dataFolder.get(position).getTitle());
        holder.numTV.setText(dataFolder.get(position).getNum());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String curName = holder.titleTV.getText().toString();
                Log.v("알림","버튼 눌렀음.");
                Toast.makeText(mContext, curName, Toast.LENGTH_SHORT).show();

                // 프레그먼트 교체하는 부분(이상해서 수정해야 함)
                FragmentActivity activity = (FragmentActivity)view.getContext();
                MemoListFragment memoListFragment = new MemoListFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.folderID,memoListFragment).addToBackStack(null).commit();
                notifyDataSetChanged();

            }
        });
    }
    //getItemCount() : 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return (null != dataFolder ? dataFolder.size():0);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView titleTV, numTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView) itemView.findViewById(R.id.folderName);
            this.numTV = (TextView)itemView.findViewById(R.id.folderNum);


            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {

        }
    }
}
