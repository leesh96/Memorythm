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
    private RecyclerView topRecyclerView, memoRecyclerView;
    private ArrayList<MemoData> listMemo;
    private ArrayList<TopMemoData> listTopMemo;
    private MemoListAdapter memoListAdapter;
    private MemoTopListAdapter memoTopListAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_list, container, false);
        //중간부분
        memoRecyclerView = (RecyclerView)view.findViewById(R.id.memoListBottomRV);
        memoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        memoListAdapter = new MemoListAdapter(getContext(),listMemo);
        memoRecyclerView.setAdapter(memoListAdapter);
        //위에 3개 부분
        topRecyclerView = (RecyclerView)view.findViewById(R.id.memoListTopRV);
        topRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        memoTopListAdapter = new MemoTopListAdapter(getContext(),listTopMemo);
        topRecyclerView.setAdapter(memoTopListAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //위에 3개 부분
        listMemo = new ArrayList<>();
        listMemo.add(new MemoData("메모1","2020-11-30"));
        listMemo.add(new MemoData("메모2","2020-11-30"));
        listMemo.add(new MemoData("메모3","2020-11-30"));
        listMemo.add(new MemoData("메모4","2020-11-30"));
        //위에 3개 부분
        listTopMemo = new ArrayList<>();
        listTopMemo.add(new TopMemoData("무지메모1"));
        listTopMemo.add(new TopMemoData("무지메모2"));
        listTopMemo.add(new TopMemoData("무지메모3"));
    }
}
// 위에 3개 부분 데이터 클래스
class TopMemoData{
    String topMemoTitle;

    public TopMemoData(String topMemoTitle) {
        this.topMemoTitle = topMemoTitle;
    }

    public String getTopMemoTitle() {
        return topMemoTitle;
    }

    public void setTopMemoTitle(String topMemoTitle) {
        this.topMemoTitle = topMemoTitle;
    }
}
