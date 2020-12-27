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

import java.util.ArrayList;

public class TrashFragment extends Fragment {
    private RecyclerView trashRecyclerView;
    private TrashFragAdapter trashAdapter;
    private ImageButton btnRestore, btnEmpty;
    private ArrayList<MemoData> trashList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trash, container, false);
        btnRestore = (ImageButton)view.findViewById(R.id.restoreBtn);
        btnEmpty = (ImageButton)view.findViewById(R.id.emptyBtn);
        trashRecyclerView = (RecyclerView)view.findViewById(R.id.trashRV);
        //리사이클러뷰 연결
        trashRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        trashAdapter = new TrashFragAdapter(getContext(),trashList);
        trashRecyclerView.setAdapter(trashAdapter);
//        trashList.clear();
//        trashAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trashList = new ArrayList<>();
        trashList.add(new MemoData("메모1","2020-11-30"));
        trashList.add(new MemoData("메모2","2020-11-30"));
        trashList.add(new MemoData("메모3","2020-11-30"));
        trashList.add(new MemoData("메모4","2020-11-30"));

    }
}