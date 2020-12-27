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

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {
    private ArrayList<ShoppingData> mArrayList = null;
    private Activity context = null;

    public ShoppingAdapter(Activity context, ArrayList<ShoppingData> list) {
        this.context = context;
        this.mArrayList = list;
    }

    // 뷰 초기화
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;

        protected CheckBox shoppingCheckBox;
        protected TextView textViewShopping, textViewAmount;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            this.shoppingCheckBox = view.findViewById(R.id.item_checkbox);
            this.textViewShopping = view.findViewById(R.id.item_content);
            this.textViewAmount = view.findViewById(R.id.item_amount);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_shopping, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    // 아이템 내용 넣기
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.shoppingCheckBox.setChecked(mArrayList.get(position).isBought());
        viewHolder.textViewShopping.setText(mArrayList.get(position).getContent());
        viewHolder.textViewAmount.setText(mArrayList.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return (mArrayList.size());
    }
}
