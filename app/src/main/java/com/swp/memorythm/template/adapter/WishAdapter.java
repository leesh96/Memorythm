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
import com.swp.memorythm.template.data.WishData;

import java.util.ArrayList;

public class WishAdapter extends RecyclerView.Adapter<WishAdapter.ViewHolder> {
    private ArrayList<WishData> mArrayList = null;
    private Activity context = null;

    public WishAdapter(Activity context, ArrayList<WishData> list) {
        this.context = context;
        this.mArrayList = list;
    }

    // 뷰 초기화
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        protected CheckBox wishCheckBox;
        protected TextView textViewWish;

        public ViewHolder(View itemView) {
            super(itemView);

            this.wishCheckBox = itemView.findViewById(R.id.item_checkbox);
            this.textViewWish = itemView.findViewById(R.id.item_content);

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
                        View view = LayoutInflater.from(context).inflate(R.layout.dialog_wish, null, false);
                        builder.setView(view);

                        TextView textViewTitle;
                        EditText editTextWish;
                        Button btnApply, btnCancel;

                        textViewTitle = view.findViewById(R.id.tv_title);
                        editTextWish = view.findViewById(R.id.et_wish);
                        btnApply = view.findViewById(R.id.btn_apply);
                        btnCancel = view.findViewById(R.id.btn_cancel);

                        textViewTitle.setText("위시리스트 편집");
                        editTextWish.setText(mArrayList.get(getAdapterPosition()).getContent());

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        btnApply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String WishContent = editTextWish.getText().toString();

                                // 빈 입력 예외처리
                                if (WishContent.equals("") | WishContent == null) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {
                                            dialog.dismiss();
                                        }
                                    });
                                    alert.setMessage("위시리스트를 입력하세요!");
                                    alert.show();
                                } else {
                                    WishData wishData = new WishData();

                                    wishData.setWished(false);
                                    wishData.setContent(WishContent);

                                    mArrayList.set(getAdapterPosition(), wishData);
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_todowish, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.wishCheckBox.setChecked(mArrayList.get(position).isWished());
        viewHolder.textViewWish.setText(mArrayList.get(position).getContent());

        viewHolder.wishCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mArrayList.get(position).setWished(isChecked);
                if (isChecked) {
                    viewHolder.textViewWish.setPaintFlags(viewHolder.textViewWish.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    viewHolder.textViewWish.setPaintFlags(0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mArrayList.size());
    }
}
