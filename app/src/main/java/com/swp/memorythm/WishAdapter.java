package com.swp.memorythm;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WishAdapter extends RecyclerView.Adapter<WishAdapter.ViewHolder> {
    private ArrayList<WishData> mArrayList = null;
    private Activity context = null;

    public WishAdapter(Activity context, ArrayList<WishData> list) {
        this.context = context;
        this.mArrayList = list;
    }

    // 뷰 초기화
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        protected CheckBox wishCheckBox;
        protected TextView textViewWish;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            this.wishCheckBox = view.findViewById(R.id.item_checkbox);
            this.textViewWish = view.findViewById(R.id.item_content);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_todowish, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.wishCheckBox.setChecked(mArrayList.get(position).isWished());
        viewHolder.textViewWish.setText(mArrayList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return (mArrayList.size());
    }
}
