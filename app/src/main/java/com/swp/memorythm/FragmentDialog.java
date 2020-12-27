package com.swp.memorythm;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

// 프레그먼트 위에 다이얼로그 띄우기
public class FragmentDialog extends DialogFragment implements View.OnClickListener {
    FolderDialogResult folderDialogResult;
    private Fragment fragment;
    private EditText editTextFolderName;
    private ArrayList<Folder> listFolder;
    private FolderFragAdapter folderFragAdapter;

    public FragmentDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.folder_add_dialog,container,false);
        Bundle args = getArguments();
        String value = args.getString("key");
        fragment = getActivity().getSupportFragmentManager().findFragmentByTag("tag");
        listFolder = new ArrayList<>();
        Button addButton = (Button)view.findViewById(R.id.folderAddBtn);
        addButton.setOnClickListener(this);
        Button cancelButton = (Button)view.findViewById(R.id.folderCancelBtn);
        cancelButton.setOnClickListener(this);
        editTextFolderName = (EditText)view.findViewById(R.id.folderNameEd);
        setCancelable(false); //dialog 영역 밖에 터치할때 종료되는거 방지
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            // 추가 버튼
            case R.id.folderAddBtn:
                if(fragment!=null){
                    if(folderDialogResult != null){
                        String result = editTextFolderName.getText().toString();
                        folderDialogResult.finish(result); // 입력한 폴더 이름으로 폴더 생성
                    }
                    DialogFragment dialogFragment = (DialogFragment) fragment;
                    dialogFragment.dismiss(); //화면 사라짐
                }
                break;
            //취소 버튼
            case R.id.folderCancelBtn:
                if(fragment!=null){
                    DialogFragment dialogFragment = (DialogFragment) fragment;
                    dialogFragment.dismiss(); //화면 사라짐
                }
                break;
        }
    }
    // 다이얼로그에서 폴더프레그먼트로 값 보내기 위해서 필요
    public void setFolderDialogResult(FolderDialogResult folderDialogResult){
        this.folderDialogResult = folderDialogResult;
    }
    public interface FolderDialogResult{
        void finish(String result);
    }
}
