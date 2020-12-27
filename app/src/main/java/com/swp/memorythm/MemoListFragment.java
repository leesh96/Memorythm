package com.swp.memorythm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemoListFragment extends Fragment {
    private RecyclerView topRecyclerView, MemoRecyclerView;
    private ArrayList<MemoData> listMemo;
    private MemoListAdapter memoListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_list, container, false);
        MemoRecyclerView = (RecyclerView)view.findViewById(R.id.memoListBottomRV);
        MemoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        memoListAdapter = new MemoListAdapter(getContext(),listMemo);
        MemoRecyclerView.setAdapter(memoListAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listMemo = new ArrayList<>();
        listMemo.add(new MemoData("메모1","2020-11-30"));
        listMemo.add(new MemoData("메모2","2020-11-30"));
        listMemo.add(new MemoData("메모3","2020-11-30"));
        listMemo.add(new MemoData("메모4","2020-11-30"));
    }
}
