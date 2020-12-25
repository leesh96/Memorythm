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

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private ArrayList<TodoData> mArrayList = null;
    private Activity context = null;

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener (OnItemClickListener listener) {
        this.mListener = listener;
    }

    public TodoAdapter(Activity context, ArrayList<TodoData> list) {
        this.context = context;
        this.mArrayList = list;
    }

    // 뷰 초기화
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        protected CheckBox todoCheckBox;
        protected TextView textViewTodo;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            this.todoCheckBox = (CheckBox) view.findViewById(R.id.item_checkbox);
            this.textViewTodo = (TextView) view.findViewById(R.id.item_content);

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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_todowish, null);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    // 아이템 내용 넣기
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.todoCheckBox.setChecked(mArrayList.get(position).isDone());
        viewHolder.textViewTodo.setText(mArrayList.get(position).getContent());

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
