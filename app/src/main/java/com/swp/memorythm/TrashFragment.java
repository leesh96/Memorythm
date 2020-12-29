package com.swp.memorythm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class TrashFragment extends Fragment implements View.OnClickListener {
    private RecyclerView trashRecyclerView;
    private TrashFragAdapter trashAdapter;
    private ImageButton btnRestore, btnEmpty;
    private ArrayList<MemoData> trashList;
    private Boolean check = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trash, container, false);
        //복구 버튼
        btnRestore = (ImageButton) view.findViewById(R.id.restoreBtn);
        btnRestore.setOnClickListener(this);
        //비우기 버튼
        btnEmpty = (ImageButton) view.findViewById(R.id.emptyBtn);
        btnEmpty.setOnClickListener(this);
        //리사이클러뷰 연결
        trashRecyclerView = (RecyclerView) view.findViewById(R.id.trashRV);
        trashRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        trashAdapter = new TrashFragAdapter(getContext(), trashList);
        trashRecyclerView.setAdapter(trashAdapter);
        // 아이템 갱신
        trashAdapter.setItems(trashList);
        trashAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trashList = new ArrayList<>();
        trashList.add(new MemoData("메모1", "2020-11-30"));
        trashList.add(new MemoData("메모2", "2020-11-30"));
        trashList.add(new MemoData("메모3", "2020-11-30"));
        trashList.add(new MemoData("메모4", "2020-11-30"));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.emptyBtn:
                if(check){
                    if(trashAdapter.removeItems()){
                        Toast.makeText(getContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "삭제할 폴더를 선택하세요", Toast.LENGTH_SHORT).show();
                    }
                    trashAdapter.setVisible(false);
                    trashAdapter.notifyDataSetChanged();
                    check = false;
                }else {
                    trashAdapter.setVisible(true);
                    trashAdapter.notifyDataSetChanged();
                    check = true;
                }
                break;

            case R.id.restoreBtn:
                if(check){
                    if(trashAdapter.removeItems()){
                        Toast.makeText(getContext(), "복구버튼 눌렀음", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "복구할 폴더를 선택하세요", Toast.LENGTH_SHORT).show();
                    }
                    trashAdapter.setVisible(false);
                    trashAdapter.notifyDataSetChanged();
                    check = false;
                }else {
                    trashAdapter.setVisible(true);
                    trashAdapter.notifyDataSetChanged();
                    check = true;
                }
                break;

        }
    }
}