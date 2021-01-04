package com.swp.memorythm.template.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.swp.memorythm.R;
import com.swp.memorythm.template.data.TodoData;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private ArrayList<TodoData> mArrayList = null;
    private Activity context = null;

    public TodoAdapter(Activity context, ArrayList<TodoData> list) {
        this.context = context;
        this.mArrayList = list;
    }

    // 뷰 초기화
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        protected CheckBox todoCheckBox;
        protected TextView textViewTodo;

        public ViewHolder(View itemView) {
            super(itemView);

            this.todoCheckBox = itemView.findViewById(R.id.item_checkbox);
            this.textViewTodo = itemView.findViewById(R.id.item_content);

            itemView.setOnCreateContextMenuListener(this);
        }

        // 꾹 눌렀을 때 컨텍스트 메뉴 나오게
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "편집");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1001:  // 편집 항목을 선택
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        View view = LayoutInflater.from(context).inflate(R.layout.dialog_todo, null, false);
                        builder.setView(view);

                        TextView textViewTitle;
                        EditText editTextTodo;
                        Button btnApply, btnCancel;

                        textViewTitle = view.findViewById(R.id.tv_title);
                        editTextTodo = view.findViewById(R.id.et_todo);
                        btnApply = view.findViewById(R.id.btn_apply);
                        btnCancel = view.findViewById(R.id.btn_cancel);

                        textViewTitle.setText("할 일 편집");
                        editTextTodo.setText(mArrayList.get(getAdapterPosition()).getContent());

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        btnApply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String TodoContent = editTextTodo.getText().toString();

                                // 빈 입력 예외처리
                                if (TodoContent.equals("") | TodoContent == null) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {
                                            dialog.dismiss();
                                        }
                                    });
                                    alert.setMessage("할 일을 입력하세요!");
                                    alert.show();
                                } else {
                                    TodoData todoData = new TodoData();

                                    todoData.setDone(false);
                                    todoData.setContent(TodoContent);

                                    mArrayList.set(getAdapterPosition(), todoData);
                                    notifyItemChanged(getAdapterPosition());

                                    alertDialog.dismiss();
                                }
                            }
                        });
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                        break;
                    case 1002:
                        mArrayList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), mArrayList.size());
                        break;
                }
                return true;
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_todowish, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    // 아이템 내용 넣기
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.todoCheckBox.setChecked(mArrayList.get(position).isDone());
        viewHolder.textViewTodo.setText(mArrayList.get(position).getContent());

        // 할 일 완료 체크
        viewHolder.todoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mArrayList.get(position).setDone(isChecked);
                // 취소선 긋기
                if (isChecked) viewHolder.textViewTodo.setPaintFlags(viewHolder.textViewTodo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                else viewHolder.textViewTodo.setPaintFlags(0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mArrayList.size());
    }
}
