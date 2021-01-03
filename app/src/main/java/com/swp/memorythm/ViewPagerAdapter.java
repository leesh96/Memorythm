package com.swp.memorythm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.swp.memorythm.template.DailyPlanFragment;
import com.swp.memorythm.template.GridMemoFragment;
import com.swp.memorythm.template.HealthTrackerFragment;
import com.swp.memorythm.template.LineMemoFragment;
import com.swp.memorythm.template.MonthTrackerFragment;
import com.swp.memorythm.template.MonthlyPlanFragment;
import com.swp.memorythm.template.NonlineMemoFragment;
import com.swp.memorythm.template.ReviewFragment;
import com.swp.memorythm.template.ShoppingFragment;
import com.swp.memorythm.template.StudyTrackerFragment;
import com.swp.memorythm.template.TodoFragment;
import com.swp.memorythm.template.WeeklyPlanFragment;
import com.swp.memorythm.template.WishFragment;
import com.swp.memorythm.template.YearlyPlanFragment;

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
            case "linememo":
                LineMemoFragment lineMemoFragment = new LineMemoFragment();
                lineMemoFragment.setArguments(bundle);
                lineMemoFragment.setFromFixedFragment(true);
                return lineMemoFragment;
            case "dailyplan":
                DailyPlanFragment dailyPlanFragment = new DailyPlanFragment();
                dailyPlanFragment.setArguments(bundle);
                dailyPlanFragment.setFromFixedFragment(true);
                return dailyPlanFragment;
            case "weeklyplan":
                WeeklyPlanFragment weeklyPlanFragment = new WeeklyPlanFragment();
                weeklyPlanFragment.setArguments(bundle);
                weeklyPlanFragment.setFromFixedFragment(true);
                return weeklyPlanFragment;
            case "monthlyplan":
                MonthlyPlanFragment monthlyPlanFragment = new MonthlyPlanFragment();
                monthlyPlanFragment.setArguments(bundle);
                monthlyPlanFragment.setFromFixedFragment(true);
                return monthlyPlanFragment;
            case "yearlyplan":
                YearlyPlanFragment yearlyPlanFragment = new YearlyPlanFragment();
                yearlyPlanFragment.setArguments(bundle);
                yearlyPlanFragment.setFromFixedFragment(true);
                return yearlyPlanFragment;
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
