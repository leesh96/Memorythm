package com.swp.memorythm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private TextView activityCategory, concertCategory, productCategory, bookCategory, foodCategory, tripCategory, clothCategory, movieCategory;
    private EditText customCategory;
    private RecyclerView wishRecyclerView;
    private ImageButton btnAdd;
    private ArrayList<WishData> mArrayList;
    private WishAdapter mAdapter;
    private int category;

    public static WishFragment newInstance() { return new WishFragment(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_wish, container, false);

        textViewDate = rootView.findViewById(R.id.tv_date);
        activityCategory = rootView.findViewById(R.id.tv_activity);
        concertCategory = rootView.findViewById(R.id.tv_concert);
        productCategory = rootView.findViewById(R.id.tv_product);
        bookCategory = rootView.findViewById(R.id.tv_book);
        foodCategory = rootView.findViewById(R.id.tv_food);
        tripCategory = rootView.findViewById(R.id.tv_trip);
        clothCategory = rootView.findViewById(R.id.tv_cloth);
        movieCategory = rootView.findViewById(R.id.tv_movie);
        customCategory = rootView.findViewById(R.id.et_customcate);
        wishRecyclerView = rootView.findViewById(R.id.wish_rcview);
        btnAdd = rootView.findViewById(R.id.btn_add);

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        textViewDate.setText(PreferenceManager.getString(getContext(), "currentDate"));

        // 카테고리 설정
        changeCategory(0);
        activityCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCategory(0);
            }
        });
        concertCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCategory(1);
            }
        });
        productCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCategory(2);
            }
        });
        bookCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCategory(3);
            }
        });
        foodCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCategory(4);
            }
        });
        tripCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCategory(5);
            }
        });
        clothCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCategory(6);
            }
        });
        movieCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCategory(7);
            }
        });
        customCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeCategory(8);
            }
        });

        // 리사이클러뷰 설정
        wishRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

                editTextWish = (EditText) dialogView.findViewById(R.id.et_wish);
                btnApply = (Button) dialogView.findViewById(R.id.btn_apply);
                btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String WishContent = editTextWish.getText().toString();

                        WishData wishData = new WishData();

                        wishData.setWished(false);
                        wishData.setContent(WishContent);

                        mArrayList.add(wishData);
                        mAdapter.notifyDataSetChanged();

                        alertDialog.dismiss();
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

        return rootView;
    }

    // 카테고리 변경 함수
    public void changeCategory(int category) {
        switch (category) {
            case 0:
                activityCategory.setBackgroundResource(R.color.purple_200);
                concertCategory.setBackgroundResource(android.R.color.transparent);
                productCategory.setBackgroundResource(android.R.color.transparent);
                bookCategory.setBackgroundResource(android.R.color.transparent);
                foodCategory.setBackgroundResource(android.R.color.transparent);
                tripCategory.setBackgroundResource(android.R.color.transparent);
                clothCategory.setBackgroundResource(android.R.color.transparent);
                movieCategory.setBackgroundResource(android.R.color.transparent);
                customCategory.setBackgroundResource(android.R.color.transparent);
                break;
            case 1:
                concertCategory.setBackgroundResource(R.color.purple_200);
                activityCategory.setBackgroundResource(android.R.color.transparent);
                productCategory.setBackgroundResource(android.R.color.transparent);
                bookCategory.setBackgroundResource(android.R.color.transparent);
                foodCategory.setBackgroundResource(android.R.color.transparent);
                tripCategory.setBackgroundResource(android.R.color.transparent);
                clothCategory.setBackgroundResource(android.R.color.transparent);
                movieCategory.setBackgroundResource(android.R.color.transparent);
                customCategory.setBackgroundResource(android.R.color.transparent);
                break;
            case 2:
                productCategory.setBackgroundResource(R.color.purple_200);
                activityCategory.setBackgroundResource(android.R.color.transparent);
                concertCategory.setBackgroundResource(android.R.color.transparent);
                bookCategory.setBackgroundResource(android.R.color.transparent);
                foodCategory.setBackgroundResource(android.R.color.transparent);
                tripCategory.setBackgroundResource(android.R.color.transparent);
                clothCategory.setBackgroundResource(android.R.color.transparent);
                movieCategory.setBackgroundResource(android.R.color.transparent);
                customCategory.setBackgroundResource(android.R.color.transparent);
                break;
            case 3:
                bookCategory.setBackgroundResource(R.color.purple_200);
                activityCategory.setBackgroundResource(android.R.color.transparent);
                productCategory.setBackgroundResource(android.R.color.transparent);
                concertCategory.setBackgroundResource(android.R.color.transparent);
                foodCategory.setBackgroundResource(android.R.color.transparent);
                tripCategory.setBackgroundResource(android.R.color.transparent);
                clothCategory.setBackgroundResource(android.R.color.transparent);
                movieCategory.setBackgroundResource(android.R.color.transparent);
                customCategory.setBackgroundResource(android.R.color.transparent);
                break;
            case 4:
                foodCategory.setBackgroundResource(R.color.purple_200);
                activityCategory.setBackgroundResource(android.R.color.transparent);
                productCategory.setBackgroundResource(android.R.color.transparent);
                concertCategory.setBackgroundResource(android.R.color.transparent);
                bookCategory.setBackgroundResource(android.R.color.transparent);
                tripCategory.setBackgroundResource(android.R.color.transparent);
                clothCategory.setBackgroundResource(android.R.color.transparent);
                movieCategory.setBackgroundResource(android.R.color.transparent);
                customCategory.setBackgroundResource(android.R.color.transparent);
                break;
            case 5:
                tripCategory.setBackgroundResource(R.color.purple_200);
                activityCategory.setBackgroundResource(android.R.color.transparent);
                productCategory.setBackgroundResource(android.R.color.transparent);
                concertCategory.setBackgroundResource(android.R.color.transparent);
                bookCategory.setBackgroundResource(android.R.color.transparent);
                foodCategory.setBackgroundResource(android.R.color.transparent);
                clothCategory.setBackgroundResource(android.R.color.transparent);
                movieCategory.setBackgroundResource(android.R.color.transparent);
                customCategory.setBackgroundResource(android.R.color.transparent);
                break;
            case 6:
                clothCategory.setBackgroundResource(R.color.purple_200);
                activityCategory.setBackgroundResource(android.R.color.transparent);
                productCategory.setBackgroundResource(android.R.color.transparent);
                concertCategory.setBackgroundResource(android.R.color.transparent);
                bookCategory.setBackgroundResource(android.R.color.transparent);
                foodCategory.setBackgroundResource(android.R.color.transparent);
                tripCategory.setBackgroundResource(android.R.color.transparent);
                movieCategory.setBackgroundResource(android.R.color.transparent);
                customCategory.setBackgroundResource(android.R.color.transparent);
                break;
            case 7:
                movieCategory.setBackgroundResource(R.color.purple_200);
                activityCategory.setBackgroundResource(android.R.color.transparent);
                productCategory.setBackgroundResource(android.R.color.transparent);
                concertCategory.setBackgroundResource(android.R.color.transparent);
                bookCategory.setBackgroundResource(android.R.color.transparent);
                foodCategory.setBackgroundResource(android.R.color.transparent);
                tripCategory.setBackgroundResource(android.R.color.transparent);
                clothCategory.setBackgroundResource(android.R.color.transparent);
                customCategory.setBackgroundResource(android.R.color.transparent);
                break;
            case 8:
                customCategory.setBackgroundResource(R.color.purple_200);
                activityCategory.setBackgroundResource(android.R.color.transparent);
                productCategory.setBackgroundResource(android.R.color.transparent);
                concertCategory.setBackgroundResource(android.R.color.transparent);
                bookCategory.setBackgroundResource(android.R.color.transparent);
                foodCategory.setBackgroundResource(android.R.color.transparent);
                tripCategory.setBackgroundResource(android.R.color.transparent);
                clothCategory.setBackgroundResource(android.R.color.transparent);
                movieCategory.setBackgroundResource(android.R.color.transparent);
                break;
        }
    }
}
