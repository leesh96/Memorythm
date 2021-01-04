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
import com.swp.memorythm.template.data.ShoppingData;

import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {
    private ArrayList<ShoppingData> mArrayList = null;
    private Activity context = null;

    public ShoppingAdapter(Activity context, ArrayList<ShoppingData> list) {
        this.context = context;
        this.mArrayList = list;
    }

    // 뷰 초기화
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        protected CheckBox shoppingCheckBox;
        protected TextView textViewShopping, textViewAmount;

        public ViewHolder(View itemView) {
            super(itemView);

            this.shoppingCheckBox = itemView.findViewById(R.id.item_checkbox);
            this.textViewShopping = itemView.findViewById(R.id.item_content);
            this.textViewAmount = itemView.findViewById(R.id.item_amount);

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
                        View view = LayoutInflater.from(context).inflate(R.layout.dialog_shopping, null, false);
                        builder.setView(view);

                        TextView textViewTitle;
                        EditText editTextShopping, editTextAmount;
                        Button btnApply, btnCancel;

                        textViewTitle = view.findViewById(R.id.tv_title);
                        editTextShopping = view.findViewById(R.id.et_shopping);
                        editTextAmount = view.findViewById(R.id.et_amount);
                        btnApply = view.findViewById(R.id.btn_apply);
                        btnCancel = view.findViewById(R.id.btn_cancel);

                        textViewTitle.setText("쇼핑리스트 편집");
                        editTextShopping.setText(mArrayList.get(getAdapterPosition()).getContent());
                        editTextAmount.setText(mArrayList.get(getAdapterPosition()).getAmount());

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        btnApply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String ShoppingContent = editTextShopping.getText().toString();
                                String ShoppingAmount = editTextAmount.getText().toString();

                                if (ShoppingContent.equals("") | ShoppingContent == null) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {
                                            dialog.dismiss();
                                        }
                                    });
                                    alert.setMessage("사야할 물건을 입력하세요!");
                                    alert.show();
                                } else if (ShoppingAmount.equals("") | ShoppingAmount == null) {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int i) {
                                            dialog.dismiss();
                                        }
                                    });
                                    alert.setMessage("수량을 입력하세요!");
                                    alert.show();
                                } else {
                                    ShoppingData shoppingData = new ShoppingData();

                                    shoppingData.setBought(false);
                                    shoppingData.setContent(ShoppingContent);
                                    shoppingData.setAmount(ShoppingAmount);

                                    mArrayList.set(getAdapterPosition(), shoppingData);
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_shopping, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    // 아이템 내용 넣기
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.shoppingCheckBox.setChecked(mArrayList.get(position).isBought());
        viewHolder.textViewShopping.setText(mArrayList.get(position).getContent());
        viewHolder.textViewAmount.setText(mArrayList.get(position).getAmount());

        // 할일 완료 체크
        viewHolder.shoppingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 완료 여부 변경
                mArrayList.get(position).setBought(isChecked);
                // 취소 선 긋기
                if (isChecked) {
                    viewHolder.textViewShopping.setPaintFlags(viewHolder.textViewShopping.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    viewHolder.textViewAmount.setPaintFlags(viewHolder.textViewAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    viewHolder.textViewShopping.setPaintFlags(0);
                    viewHolder.textViewAmount.setPaintFlags(0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mArrayList.size());
    }
}
