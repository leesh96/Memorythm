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

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private ShoppingAdapter.OnItemClickListener mListener = null;

    public void setOnItemClickListener (ShoppingAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

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

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) mListener.onItemClick(view, pos);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_shopping, null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    // 아이템 내용 넣기
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.shoppingCheckBox.setChecked(mArrayList.get(position).isBought());
        viewHolder.textViewShopping.setText(mArrayList.get(position).getContent());
        viewHolder.textViewAmount.setText(Integer.toString(mArrayList.get(position).getAmount()));

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (mArrayList.size());
    }
}
