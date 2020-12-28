package com.swp.memorythm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class WriteFragment extends Fragment {

    private ImageButton nolineBtn, lineBtn, todoBtn, gridBtn, wishBtn, shoppingBtn, reviewBtn, healthTBtn, studyTBtn, monthlyTBtn, dailyBtn, weeklyTBtn, monthlyPBtn, yearlyBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, container, false);
        // 버튼 불러오기
        nolineBtn = (ImageButton)view.findViewById(R.id.nolineMemoImg);
        lineBtn = (ImageButton)view.findViewById(R.id.lineMemoImg);
        todoBtn = (ImageButton)view.findViewById(R.id.todoListImg);
        gridBtn = (ImageButton)view.findViewById(R.id.gridMemoImg);
        wishBtn = (ImageButton)view.findViewById(R.id.wishMemoImg);
        shoppingBtn = (ImageButton)view.findViewById(R.id.shoppingListImg);
        reviewBtn = (ImageButton)view.findViewById(R.id.reviewMemoImg);
        healthTBtn = (ImageButton)view.findViewById(R.id.healthTrackerImg);
        studyTBtn = (ImageButton)view.findViewById(R.id.studyTrackerImg);
        monthlyTBtn = (ImageButton)view.findViewById(R.id.monthlyTrackerImg);
        dailyBtn = (ImageButton)view.findViewById(R.id.dailyImg);
        weeklyTBtn = (ImageButton)view.findViewById(R.id.weeklyTrackerImg);
        monthlyPBtn = (ImageButton)view.findViewById(R.id.monthlyPlanImg);
        yearlyBtn = (ImageButton)view.findViewById(R.id.yearlyTrackerImg);
        //버튼 클릭
        //무지메모
        nolineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "무지메모", Toast.LENGTH_SHORT).show();
            }
        });
        //줄메모
        lineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "줄메모", Toast.LENGTH_SHORT).show();
            }
        });
        //todolist
        todoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "todolist", Toast.LENGTH_SHORT).show();
            }
        });
        //grid memo
        gridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "grid memo", Toast.LENGTH_SHORT).show();
            }
        });
        //wish list
        wishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "wish list", Toast.LENGTH_SHORT).show();
            }
        });
        //shoppinglist
        shoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "shopping list", Toast.LENGTH_SHORT).show();
            }
        });
        //review
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "review", Toast.LENGTH_SHORT).show();
            }
        });
        //health tracker
        healthTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "health tracker", Toast.LENGTH_SHORT).show();
            }
        });
        //study tracker
        studyTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "study tracker", Toast.LENGTH_SHORT).show();
            }
        });
        //monthly tracker
        monthlyTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "monthly tracker", Toast.LENGTH_SHORT).show();
            }
        });
        //daily plan
        dailyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "daily plan", Toast.LENGTH_SHORT).show();
            }
        });
        //weekly plan
        weeklyTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "weekly plan", Toast.LENGTH_SHORT).show();
            }
        });
        //monthly plan
        monthlyPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "monthly plan", Toast.LENGTH_SHORT).show();
            }
        });
        //yearly plan
        yearlyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "yearly plan", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}