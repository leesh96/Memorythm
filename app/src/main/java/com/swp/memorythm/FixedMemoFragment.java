package com.swp.memorythm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class FixedMemoFragment extends Fragment {
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private ArrayList<FixedMemoData> arrayList;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        arrayList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM (SELECT id, editdate, template_case, bgcolor FROM nonlinememo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                    "SELECT id, editdate, template_case, bgcolor FROM linememo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                    "SELECT id, editdate, template_case, bgcolor FROM gridmemo WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                    "SELECT id, editdate, template_case, bgcolor FROM todolist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                    "SELECT id, editdate, template_case, bgcolor FROM wishlist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                    "SELECT id, editdate, template_case, bgcolor FROM shoppinglist WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                    "SELECT id, editdate, template_case, bgcolor FROM review WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                    "SELECT id, editdate, template_case, bgcolor FROM dailyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                    "SELECT id, editdate, template_case, bgcolor FROM weeklyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                    "SELECT id, editdate, template_case, bgcolor FROM monthlyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                    "SELECT id, editdate, template_case, bgcolor FROM yearlyplan WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                    "SELECT id, editdate, template_case, bgcolor FROM healthtracker WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                    "SELECT id, editdate, template_case, bgcolor FROM monthtracker WHERE fixed = 1 AND deleted = 0 UNION ALL " +
                    "SELECT id, editdate, template_case, bgcolor FROM studytracker WHERE fixed = 1 AND deleted = 0) ORDER BY editdate DESC", null);
            while (cursor.moveToNext()) {
                FixedMemoData fixedMemoData = new FixedMemoData();
                fixedMemoData.setMemoid(cursor.getInt(0));
                fixedMemoData.setTemplate(cursor.getString(2));
                fixedMemoData.setBgcolor(cursor.getString(3));
                arrayList.add(fixedMemoData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fixed_memo, container, false); // 레이아웃 연결
        //뷰페이저에 페이저어댑터 지정
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3); // 3개로 제한
        pagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), arrayList);
        viewPager.setAdapter(pagerAdapter);
        if (!arrayList.isEmpty()) {
            viewPager.setCurrentItem(0);
            setBackground(arrayList.get(0).getBgcolor());
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setBackground(arrayList.get(position).getBgcolor());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    private void setBackground(String Bgcolor) {
        switch (Bgcolor) {
            case "yellow":
            default:
                viewPager.setBackgroundResource(R.drawable.template_style_bgyellow);
                break;
            case "pink":
                viewPager.setBackgroundResource(R.drawable.template_style_bgpink);
                break;
            case "mint":
                viewPager.setBackgroundResource(R.drawable.template_style_bgmint);
                break;
            case "sky":
                viewPager.setBackgroundResource(R.drawable.template_style_bgsky);
                break;
            case "gray":
                viewPager.setBackgroundResource(R.drawable.template_style_bggray);
                break;
        }
    }
}
