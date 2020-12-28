package com.swp.memorythm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FolderFragAdapter extends RecyclerView.Adapter<FolderFragAdapter.ViewHolder> {

    Context mContext;

    private ArrayList<Folder> dataFolder;
    private Map<Folder, Boolean> mCheckedMap = new HashMap<>();
    private List<Folder> mCheckedFolder = new ArrayList<>(); //체크한 항목 저장
    public boolean isTrash = false;


    public FolderFragAdapter(Context mContext, ArrayList<Folder> listFolder) {
        this.mContext = mContext;
        this.dataFolder = listFolder;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_folder_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Folder folder = dataFolder.get(viewHolder.getAdapterPosition());
                mCheckedMap.put(folder,isChecked);
                // 체크된거 mCheckedFolder 넣기
                if(isChecked){
                    mCheckedFolder.add(folder);
                }else {
                    mCheckedFolder.remove(folder);
                }
            }
        });
        return viewHolder;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Folder folder = dataFolder.get(position);

        holder.titleTV.setText(dataFolder.get(position).getTitle());
        holder.numTV.setText(dataFolder.get(position).getNum());
        //체크박스 체크 여부
        boolean isChecked = mCheckedMap.get(folder)==null?false:mCheckedMap.get(folder);
        holder.checkBox.setChecked(isChecked);

        //리사이클러뷰 아이템 클릭
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 프레그먼트 교체하는 부분
                FragmentActivity activity = (FragmentActivity)view.getContext();
                MemoListFragment memoListFragment = new MemoListFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.folderID,memoListFragment).addToBackStack(null).commit();
                notifyDataSetChanged();
            }
        });
        //삭제
        if(isTrash) {
            holder.numTV.setVisibility(View.GONE);
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
        }
        else {
            holder.numTV.setVisibility(View.VISIBLE);
            holder.checkBox.setVisibility(View.GONE);
            holder.itemView.setClickable(true);
        }
    }
    //getItemCount() : 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return (null != dataFolder ? dataFolder.size():0);
    }

    //ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTV, numTV;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titleTV = (TextView) itemView.findViewById(R.id.folderName);
            this.numTV = (TextView)itemView.findViewById(R.id.folderNum);
            this.checkBox = (CheckBox)itemView.findViewById(R.id.folderCheckBox);
        }

    }

    // 아이템 삭제
    public boolean removeItems(){
        boolean result = dataFolder.removeAll(mCheckedFolder);
        if(result){ notifyDataSetChanged(); }
        return  result;
    }
    // 아이템 갱신에 사용
    public void setItems(ArrayList<Folder> items){
        dataFolder = items;
    }


    public void setVisible(boolean trash){
        isTrash = trash;
    }
}
