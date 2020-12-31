package com.swp.memorythm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class FixedMemoFragment extends Fragment {
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fixed_memo, container, false); // 레이아웃 연결

        //뷰페이저에 페이저어댑터 지정
        viewPager = (ViewPager)view.findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3); // 3개로 제한
        pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(pagerAdapter);
        return view;
    }
}
