package com.swp.memorythm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

//폴더 프레그먼트
public class FolderFragment extends Fragment implements View.OnClickListener {

    private RecyclerView folderRecyclerView;
    private ArrayList<Folder> listFolder;
    private FolderFragAdapter folderFragAdapter;
    private ImageButton addBtn, deleteBtn;
    private Boolean check = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder, container, false);

        folderRecyclerView = (RecyclerView)view.findViewById(R.id.folderRV);
        folderRecyclerView.setHasFixedSize(true);

        folderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listFolder = new ArrayList<>();
        //어뎁터 연결
        folderFragAdapter = new FolderFragAdapter(getContext(),listFolder);
        folderRecyclerView.setAdapter(folderFragAdapter);
        //추가 버튼 구현
        addBtn = (ImageButton)view.findViewById(R.id.addFolderBtn);
        addBtn.setOnClickListener(this);
        //삭제 버튼
        deleteBtn = (ImageButton)view.findViewById(R.id.deleteFolderBtn);
        deleteBtn.setOnClickListener(this);

        //아이템 갱신
        folderFragAdapter.setItems(listFolder);
        folderFragAdapter.notifyDataSetChanged();

        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //  addBtn 누르면 폴더 추가 다이얼로그 생성
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addFolderBtn:
                // 데이터를 다이얼로그로 보내는 코드
                Bundle args = new Bundle();
                args.putString("key", "value");

                FragmentDialog dialog = new FragmentDialog();
                dialog.setArguments(args); // 데이터 전달
                dialog.show(getActivity().getSupportFragmentManager(),"tag");
                //다이얼로그에서 받아온 데이터
                dialog.setFolderDialogResult(new FragmentDialog.FolderDialogResult(){
                    @Override
                    public void finish(String result) {
                        Folder folder = new Folder(result,"1");
                        listFolder.add(folder);
                        folderFragAdapter.notifyDataSetChanged();
                    }
                });
                break;
            //삭제 기능 구현하기
            case R.id.deleteFolderBtn:
                if(check){
                    if(folderFragAdapter.removeItems()){
                        Toast.makeText(getContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "삭제할 폴더를 선택하세요", Toast.LENGTH_SHORT).show();
                    }
                    folderFragAdapter.setVisible(false);
                    folderFragAdapter.notifyDataSetChanged();
                    check = false;
                } else {
                    folderFragAdapter.setVisible(true);
                    folderFragAdapter.notifyDataSetChanged();
                    check = true;
                }
                break;



        }
    }

    }