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

public class TotalFragment extends Fragment {

    private RecyclerView totalRecyclerView;
    private TotalMemoAdapter totalMemoAdapter;
    private ArrayList<TotalMemoData> listTotal;
    private ImageButton btnDelete;
    private Boolean check = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total, container, false);
        // recyclerview
        totalRecyclerView = (RecyclerView)view.findViewById(R.id.totalRV);
        totalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        totalMemoAdapter = new TotalMemoAdapter(getContext(),listTotal);
        totalRecyclerView.setAdapter(totalMemoAdapter);
        //삭제버튼
        btnDelete = (ImageButton)view.findViewById(R.id.totalDelBtn);
        btnDelete.setOnClickListener(view1 -> {
            if(check){
                if((totalMemoAdapter.removeItems())){
                    Toast.makeText(getContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "삭제할 폴더를 선택하세요", Toast.LENGTH_SHORT).show();
                }
                totalMemoAdapter.setVisible(false);
                totalMemoAdapter.notifyDataSetChanged();
                check = false;
            }else {
                totalMemoAdapter.setVisible(true);
                totalMemoAdapter.notifyDataSetChanged();
                check = true;
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listTotal = new ArrayList<>();
        listTotal.add(new TotalMemoData("메모1"));
        listTotal.add(new TotalMemoData("메모2"));
        listTotal.add(new TotalMemoData("메모3"));
        listTotal.add(new TotalMemoData("메모4"));
        listTotal.add(new TotalMemoData("메모5"));
    }
}