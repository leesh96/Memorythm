package com.swp.memorythm;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

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
            default:
                return null;
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewPager parent = container.findViewById(R.id.pager);
        String Bgcolor = arrayList.get(position).getBgcolor();
        switch (Bgcolor) {
            case "yellow":
            default:
                parent.setBackgroundResource(R.drawable.template_style_bgyellow);
                break;
            case "pink":
                parent.setBackgroundResource(R.drawable.template_style_bgpink);
                break;
            case "mint":
                parent.setBackgroundResource(R.drawable.template_style_bgmint);
                break;
            case "sky":
                parent.setBackgroundResource(R.drawable.template_style_bgsky);
                break;
            case "gray":
                parent.setBackgroundResource(R.drawable.template_style_bggray);
                break;
        }
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        // 전체 페이지 수 3개로 고정
        return arrayList.size();
    }
}
