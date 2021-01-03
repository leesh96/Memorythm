package com.swp.memorythm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> items;

    public ViewPagerAdapter(@NonNull FragmentManager fm) {

        super(fm);
        items = new ArrayList<>();
    }

    public void addItem(Fragment item){
        items.add(item);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }
    @Override
    public int getCount() {
        // 전체 페이지 수 3개로 고정
        return items.size();
    }
/*
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        if(mContext != null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.,container,false);

        }
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //뷰페이저에서 삭제
        container.removeView((View) object);
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }
*/



}
