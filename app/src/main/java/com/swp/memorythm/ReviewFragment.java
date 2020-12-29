package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReviewFragment extends Fragment {
    private TextView textViewDate;
    private EditText et_reviewList, et_scoreReview, et_title, et_content;
    private final TextView[] tv_reviews = new TextView[9];
    private int pastChoice;
    private RatingBar rb_review;
    private boolean isEdit = false, isUser = false;
    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    // 캘린더 객체 생성
    Calendar myCalendar = Calendar.getInstance();

    //데이트픽커 다이얼로그
    DatePickerDialog.OnDateSetListener myDatePicker = (datePicker, year, month, dayOfMonth) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabel();
    };

    // 텍스트뷰 날짜 업데이트
    private void updateLabel() {
        String DateFormat = "yyyy - MM - dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat, Locale.KOREA);
        textViewDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.template_review, container, false);

        textViewDate = viewGroup.findViewById(R.id.write_date);
        tv_reviews[0] = viewGroup.findViewById(R.id.tv_book);
        tv_reviews[1] = viewGroup.findViewById(R.id.tv_concert);
        tv_reviews[2] = viewGroup.findViewById(R.id.tv_movie);
        tv_reviews[3] = viewGroup.findViewById(R.id.tv_musical);
        tv_reviews[4] = viewGroup.findViewById(R.id.tv_food);
        tv_reviews[5] = viewGroup.findViewById(R.id.tv_exhibition);
        tv_reviews[6] = viewGroup.findViewById(R.id.tv_party);
        tv_reviews[7] = viewGroup.findViewById(R.id.tv_project);
        tv_reviews[8] = viewGroup.findViewById(R.id.tv_trip);
        et_reviewList = viewGroup.findViewById(R.id.et_reviewlist);
        rb_review = viewGroup.findViewById(R.id.rb_review);
        et_scoreReview = viewGroup.findViewById(R.id.et_scorereview);
        et_title = viewGroup.findViewById(R.id.et_title);
        et_content = viewGroup.findViewById(R.id.et_content);

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        textViewDate.setText(PreferenceManager.getString(getContext(), "currentDate"));

        pastChoice = 0;
        for (int i = 0; i < tv_reviews.length ; i++) {
            int finalI = i;
            tv_reviews[i].setOnClickListener(v -> { setBg(pastChoice, tv_reviews[finalI]); pastChoice=finalI; isUser=false;});
        }
        et_reviewList.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                et_reviewList.setBackgroundResource(R.drawable.bg_selector);
                tv_reviews[pastChoice].setBackgroundColor(Color.parseColor("#00000000"));
                isUser=true;
            } else {
                return false;
            }
            return true;
        });
        //레이팅 바
        rb_review.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if(isEdit) isEdit = false;
            else {
                String string = Integer.toString((int)rb_review.getRating()*20);
                et_scoreReview.setText(string);
            }
        });
        et_scoreReview.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                isEdit = true;
                int score = Integer.parseInt(et_scoreReview.getText().toString());
                rb_review.setRating(Math.round(score / 20.0));
            } else {
                return false;
            }
            return true;
        });
        textViewDate.setOnClickListener(v -> { // 데이트픽커 띄우기
            new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        // TODO: 2020-11-20 파이어베이스 연동

        return viewGroup;
    }
    public void setBg (int past, TextView current){
        et_reviewList.setBackgroundResource(R.drawable.bg_line);
        tv_reviews[past].setBackgroundColor(Color.parseColor("#00000000"));
        current.setBackgroundResource(R.drawable.bg_selector);
    }

    public void saveData(){
        //String : userdate, categoryName, reviewTitle, reviewContent
        //int : categoryCheck, starNum, score
        String userdate = textViewDate.getText().toString();
        String reviewTitle = et_title.getText().toString();
        String reviewContent = et_content.getText().toString();
        int starNum = rb_review.getNumStars();
        int score = Integer.parseInt(et_scoreReview.getText().toString());
        String categoryName;
        int categoryCheck;
        if(isUser) {
            categoryName = et_reviewList.getText().toString();
            categoryCheck = 9;
        }
        else {
            categoryName = "null";
            categoryCheck = pastChoice;
        }
        // TODO: 2020-12-29 SQL에 저장
    }
    public void setData(){
        String userdate=null, categoryName=null, reviewTitle=null, reviewContent=null;
        int categoryCheck=0, starNum=0, score=0;
        // TODO: 2020-12-29 SQL에서 불러오기
        textViewDate.setText(userdate);
        et_title.setText(reviewTitle);
        et_content.setText(reviewContent);
        rb_review.setRating(starNum);
        et_scoreReview.setText(score);
        if(categoryCheck==9){
            et_reviewList.setText(categoryName);
            et_reviewList.setBackgroundResource(R.drawable.bg_selector);
            tv_reviews[pastChoice].setBackgroundColor(Color.parseColor("#00000000"));
        }else{
            setBg(pastChoice, tv_reviews[categoryCheck]);
        }
    }
}
