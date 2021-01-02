package com.swp.memorythm;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WishFragment extends Fragment {
    private TextView textViewDate;
    private TextView[] textViewsCategory = new TextView[8];
    private EditText customCategory;
    private RecyclerView wishRecyclerView;
    private ImageButton btnAdd;
    private ArrayList<WishData> mArrayList;
    private WishAdapter mAdapter;

    private int selectCategory = 0;
    private boolean selectCustomCategory;

    public static WishFragment newInstance() { return new WishFragment(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_wish, container, false);

        textViewDate = rootView.findViewById(R.id.tv_date);
        textViewsCategory[0] = rootView.findViewById(R.id.tv_activity);
        textViewsCategory[1] = rootView.findViewById(R.id.tv_concert);
        textViewsCategory[2] = rootView.findViewById(R.id.tv_product);
        textViewsCategory[3] = rootView.findViewById(R.id.tv_book);
        textViewsCategory[4] = rootView.findViewById(R.id.tv_food);
        textViewsCategory[5] = rootView.findViewById(R.id.tv_trip);
        textViewsCategory[6] = rootView.findViewById(R.id.tv_cloth);
        textViewsCategory[7] = rootView.findViewById(R.id.tv_movie);
        customCategory = rootView.findViewById(R.id.et_customcate);
        wishRecyclerView = rootView.findViewById(R.id.wish_rcview);
        btnAdd = rootView.findViewById(R.id.btn_add);

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        textViewDate.setText(PreferenceManager.getString(getContext(), "currentDate"));

        for (int i = 0; i < textViewsCategory.length; i++) {
            int finalI = i;
            textViewsCategory[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeCategory(selectCategory, finalI);
                    selectCategory = finalI;
                    selectCustomCategory = false;
                }
            });
        }

        // 리사이클러뷰 설정
        wishRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wishRecyclerView.setHasFixedSize(true);
        mArrayList = new ArrayList<>();
        mAdapter = new WishAdapter(getActivity(), mArrayList);
        wishRecyclerView.setAdapter(mAdapter);
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        // wishlist 항목 추가
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_wish, null, false);
                builder.setView(dialogView);

                EditText editTextWish;
                Button btnApply, btnCancel;

                editTextWish = dialogView.findViewById(R.id.et_wish);
                btnApply = dialogView.findViewById(R.id.btn_apply);
                btnCancel = dialogView.findViewById(R.id.btn_cancel);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String WishContent = editTextWish.getText().toString();

                        if (WishContent.equals("") | WishContent == null) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.dismiss();
                                }
                            });
                            alert.setMessage("위시리스트를 입력하세요!");
                            alert.show();
                        } else {
                            WishData wishData = new WishData();

                            wishData.setWished(false);
                            wishData.setContent(WishContent);

                            mArrayList.add(wishData);
                            mAdapter.notifyDataSetChanged();

                            alertDialog.dismiss();
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });

        final View activityRootView = rootView.findViewById(R.id.parentLayout);

        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(rect);

                int heightDiff = activityRootView.getRootView().getHeight() - rect.height();
                if (heightDiff < 0.25*activityRootView.getRootView().getHeight()&& WishFragment.this.getActivity() != null) {
                    if (WishFragment.this.getActivity().getCurrentFocus() == customCategory) {
                        customCategory.setBackgroundResource(R.drawable.bg_selector);
                        textViewsCategory[selectCategory].setBackgroundColor(Color.TRANSPARENT);
                        selectCustomCategory = true;
                        customCategory.clearFocus();
                    }
                }
            }
        });

        return rootView;
    }

    // 카테고리 변경 함수
    public void changeCategory(int old, int current) {
        customCategory.setBackgroundResource(R.drawable.border_d);
        textViewsCategory[old].setBackgroundColor(Color.TRANSPARENT);
        textViewsCategory[current].setBackgroundResource(R.drawable.bg_selector);
    }
}
