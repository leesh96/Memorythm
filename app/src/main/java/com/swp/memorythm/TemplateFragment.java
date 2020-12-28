package com.swp.memorythm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    private ItemTouchHelper helper;
    private ImageButton btnRange;




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
        // 정렬 버튼
        btnRange = (ImageButton)view.findViewById(R.id.rangeBtn);
        btnRange.setOnClickListener(this);
        //ItemTouchHelper 생성
//        helper = new ItemTouchHelper(new ItemTouchHelperCallback(templateFragAdapter));
        //RecyclerView에 ItemTouchHelper 붙이기
//        helper.attachToRecyclerView(templateRecyclerView);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


/*
        Template template1 = new Template("무지 메모","0");
        Template template2 = new Template("메모 (줄)","0");
        Template template3 = new Template("메모 (방안)","0");
        Template template4 = new Template("TODOLIST","0");
        Template template5 = new Template("WISH LIST","0");
        Template template6 = new Template("SHOPPING LIST","0");
        Template template7 = new Template("Review LIST","0");
        Template template8 = new Template("Health Tracker","0");
        Template template9 = new Template("Study Tracker","0");
        templateFragAdapter.addItem(template1);
        templateFragAdapter.addItem(template2);
        templateFragAdapter.addItem(template3);
        templateFragAdapter.addItem(template4);
        templateFragAdapter.addItem(template5);
        templateFragAdapter.addItem(template6);
        templateFragAdapter.addItem(template7);
        templateFragAdapter.addItem(template8);
        templateFragAdapter.addItem(template9);

 */


        listTemplate = new ArrayList<>();
        listTemplate.add(new Template("무지 메모","0"));
        listTemplate.add(new Template("메모 (줄)","0"));
        listTemplate.add(new Template("메모 (방안)","0"));
        listTemplate.add(new Template("TODO LIST","0"));
        listTemplate.add(new Template("WISH LIST","0"));
        listTemplate.add(new Template("SHOPPING LIST","0"));
        listTemplate.add(new Template("Review LIST","0"));
        listTemplate.add(new Template("Health Tracker","0"));
        listTemplate.add(new Template("Study Tracker","0"));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rangeBtn:

                break;
        }

    }
}