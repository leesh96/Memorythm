package com.swp.memorythm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<FixedMemoData> arrayList;

    public ViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<FixedMemoData> arrayList) {
        super(fm);
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        int memoid = arrayList.get(position).getMemoid();
        String Template = arrayList.get(position).getTemplate();

        Bundle bundle = new Bundle();
        bundle.putInt("memoid", memoid);

        // TODO: 2021-01-03 템플릿 별로 프래그먼트 띄우는거 추가하기
        switch (Template) {
            case "nonlinememo":
                NonlineMemoFragment nonlineMemoFragment = new NonlineMemoFragment();
                nonlineMemoFragment.setArguments(bundle);
                nonlineMemoFragment.setFromFixedFragment(true);
                return nonlineMemoFragment;
            case "todolist":
                TodoFragment todoFragment = new TodoFragment();
                todoFragment.setArguments(bundle);
                todoFragment.setFromFixedFragment(true);
                return todoFragment;
            case "wishlist":
                WishFragment wishFragment = new WishFragment();
                wishFragment.setArguments(bundle);
                wishFragment.setFromFixedFragment(true);
                return wishFragment;
            case "shoppinglist":
                ShoppingFragment shoppingFragment = new ShoppingFragment();
                shoppingFragment.setArguments(bundle);
                shoppingFragment.setFromFixedFragment(true);
                return shoppingFragment;
            case "review":
                ReviewFragment reviewFragment = new ReviewFragment();
                reviewFragment.setArguments(bundle);
                reviewFragment.setFromFixedFragment(true);
                return reviewFragment;
            case "healthtracker":
                HealthTrackerFragment healthTrackerFragment = new HealthTrackerFragment();
                healthTrackerFragment.setArguments(bundle);
                healthTrackerFragment.setFromFixedFragment(true);
                return healthTrackerFragment;
            case "studytracker":
                StudyTrackerFragment studyTrackerFragment = new StudyTrackerFragment();
                studyTrackerFragment.setArguments(bundle);
                studyTrackerFragment.setFromFixedFragment(true);
                return studyTrackerFragment;
            case "monthtracker":
                MonthTrackerFragment monthTrackerFragment = new MonthTrackerFragment();
                monthTrackerFragment.setArguments(bundle);
                monthTrackerFragment.setFromFixedFragment(true);
                return monthTrackerFragment;
            case "gridmemo":
                GridMemoFragment gridMemoFragment = new GridMemoFragment();
                gridMemoFragment.setArguments(bundle);
                gridMemoFragment.setFromFixedFragment(true);
                return gridMemoFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // 전체 페이지 수 3개로 고정
        return arrayList.size();
    }
}
