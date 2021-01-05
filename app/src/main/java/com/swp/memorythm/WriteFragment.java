package com.swp.memorythm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.swp.memorythm.template.MemoViewActivity;

public class WriteFragment extends Fragment {

    private ImageButton nolineBtn, lineBtn, gridBtn, todoBtn, wishBtn, shoppingBtn, reviewBtn, dailyBtn, weeklyTBtn, monthlyPBtn, yearlyBtn, monthlyTBtn, healthTBtn, studyTBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, container, false);
        // 버튼 불러오기
        nolineBtn = (ImageButton) view.findViewById(R.id.nolineMemoImg);
        lineBtn = (ImageButton) view.findViewById(R.id.lineMemoImg);
        gridBtn = (ImageButton) view.findViewById(R.id.gridMemoImg);
        todoBtn = (ImageButton) view.findViewById(R.id.todoListImg);
        wishBtn = (ImageButton) view.findViewById(R.id.wishMemoImg);
        shoppingBtn = (ImageButton) view.findViewById(R.id.shoppingListImg);
        reviewBtn = (ImageButton) view.findViewById(R.id.reviewMemoImg);
        dailyBtn = (ImageButton) view.findViewById(R.id.dailyImg);
        weeklyTBtn = (ImageButton) view.findViewById(R.id.weeklyPlanImg);
        monthlyPBtn = (ImageButton) view.findViewById(R.id.monthlyPlanImg);
        yearlyBtn = (ImageButton) view.findViewById(R.id.yearlyPlanImg);
        monthlyTBtn = (ImageButton) view.findViewById(R.id.monthlyTrackerImg);
        healthTBtn = (ImageButton) view.findViewById(R.id.healthTrackerImg);
        studyTBtn = (ImageButton) view.findViewById(R.id.studyTrackerImg);

        //버튼 클릭
        //무지메모
        nolineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "nonlinememo");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });
        //줄메모
        lineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "linememo");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });
        //grid memo
        gridBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "gridmemo");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });
        //todolist
        todoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "todolist");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });
        //wish list
        wishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "wishlist");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });
        //shoppinglist
        shoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "shoppinglist");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });
        //review
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "review");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });
        //daily plan
        dailyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "dailyplan");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });
        //weekly plan
        weeklyTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "weeklyplan");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });
        //monthly plan
        monthlyPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "monthlyplan");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });
        //yearly plan
        yearlyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "yearlyplan");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });
        //monthtracker
        monthlyTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "monthtracker");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });
        //health tracker
        healthTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "healthtracker");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });
        //study tracker
        studyTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MemoViewActivity.class);
                intent.putExtra("template", "studytracker");
                intent.putExtra("mode", "write");
                startActivity(intent);
            }
        });

        return view;
    }
}