package com.swp.memorythm;

import android.content.Context;
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

public class FolderFragment extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private ArrayList<Folder> listFolder;
    private FolderFragAdapter folderFragAdapter;
    private ImageButton addBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder, container, false);
        Context context = view.getContext();
        recyclerView = (RecyclerView)view.findViewById(R.id.folderRV);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        listFolder = new ArrayList<>();
        //어뎁터 연결
        folderFragAdapter = new FolderFragAdapter(getActivity(),listFolder);
        recyclerView.setAdapter(folderFragAdapter);

        //추가 버튼 구현
        addBtn = (ImageButton)view.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        Folder folder = new Folder("폴더1",1);
        listFolder.add(folder);
        folderFragAdapter.notifyDataSetChanged();
    }
}