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

public class TemplateFragment extends Fragment implements View.OnClickListener {
    private View view;
    private RecyclerView templateRecyclerView;
    private ArrayList<Template> listTemplate;
    private TemplateFragAdapter templateFragAdapter;
    private ImageButton mixerBtn;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_template, container, false);
        templateRecyclerView = (RecyclerView)view.findViewById(R.id.templateRV);
        templateRecyclerView.setHasFixedSize(true);
        templateRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //어뎁터
        templateFragAdapter = new TemplateFragAdapter(getContext(),listTemplate);
        templateRecyclerView.setAdapter(templateFragAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listTemplate = new ArrayList<>();
        listTemplate.add(new Template("무지 메모",1));
        listTemplate.add(new Template("메모 (줄)",0));
        listTemplate.add(new Template("메모 (방안)",1));
        listTemplate.add(new Template("TODO LIST",0));
    }

    @Override
    public void onClick(View view) {

    }
}