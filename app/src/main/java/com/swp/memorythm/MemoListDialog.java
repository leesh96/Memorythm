package com.swp.memorythm;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

public class MemoListDialog extends Dialog {
    private OnDialogListener listener;
    private Context context;
    private Button transferBtn, transfercancelBtn;
    public MemoListDialog(@NonNull Context context, final int position, MemoData memoData) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_memo_list);

        //폴더이동버튼
        transferBtn = findViewById(R.id.btnTransfer);
        transferBtn.setOnClickListener(view -> {
            if(listener!=null){
                dismiss();
            }
        });

        //취소버튼
        transfercancelBtn = findViewById(R.id.btnTransferCancel);
        transfercancelBtn.setOnClickListener(view -> {
            dismiss();
        });

    }
    public void setDialogListener(OnDialogListener listener){ this.listener = listener; }

}
