package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.zip.Inflater;

public class ReviewFragment extends Fragment {
    private TextView textViewDate;
    private EditText et_reviewlist, et_scorereview;
    private TextView tv_book, tv_concert, tv_movie, tv_musical, tv_food, tv_exhibition, tv_party, tv_project, tv_trip;
    private TextView pastChoice;
    private RatingBar rb_review;
    private boolean isEdit = false;
    public static ReviewFragment newInstance() {
        return new ReviewFragment();
    }

    // 캘린더 객체 생성
    Calendar myCalendar = Calendar.getInstance();

    //데이트픽커 다이얼로그
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
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
        tv_book = viewGroup.findViewById(R.id.tv_book);

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy - MM - dd", Locale.KOREA);
        textViewDate.setText(simpleDateFormat.format(currentTime));
        tv_book = viewGroup.findViewById(R.id.tv_book);
        tv_concert = viewGroup.findViewById(R.id.tv_concert);
        tv_movie = viewGroup.findViewById(R.id.tv_movie);
        tv_musical = viewGroup.findViewById(R.id.tv_musical);
        tv_food = viewGroup.findViewById(R.id.tv_food);
        tv_exhibition = viewGroup.findViewById(R.id.tv_exhibition);
        tv_party = viewGroup.findViewById(R.id.tv_party);
        tv_project = viewGroup.findViewById(R.id.tv_project);
        tv_trip = viewGroup.findViewById(R.id.tv_trip);
        et_reviewlist = viewGroup.findViewById(R.id.et_reviewlist);
        rb_review = viewGroup.findViewById(R.id.rb_review);
        et_scorereview = viewGroup.findViewById(R.id.et_scorereview);
        pastChoice = tv_book;
        tv_book.setOnClickListener(v -> { setBg(pastChoice, tv_book); pastChoice=tv_book;});
        tv_concert.setOnClickListener(v -> { setBg(pastChoice, tv_concert); pastChoice=tv_concert;});
        tv_movie.setOnClickListener(v -> { setBg(pastChoice, tv_movie); pastChoice=tv_movie;});
        tv_musical.setOnClickListener(v -> { setBg(pastChoice, tv_musical); pastChoice=tv_musical;});
        tv_food.setOnClickListener(v -> { setBg(pastChoice, tv_food); pastChoice=tv_food;});
        tv_exhibition.setOnClickListener(v -> { setBg(pastChoice, tv_exhibition); pastChoice=tv_exhibition;});
        tv_party.setOnClickListener(v -> { setBg(pastChoice, tv_party); pastChoice=tv_party;});
        tv_project.setOnClickListener(v -> { setBg(pastChoice, tv_project); pastChoice=tv_project;});
        tv_trip.setOnClickListener(v -> { setBg(pastChoice, tv_trip); pastChoice=tv_trip;});
        et_reviewlist.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId){
                case EditorInfo.IME_ACTION_DONE:
                    et_reviewlist.setBackgroundResource(R.drawable.bg_selector);
                    pastChoice.setBackgroundColor(Color.parseColor("#00000000"));
                    break;
                default:
                    return false;
            }
            return true;
        });
        //레이팅 바
        rb_review.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if(isEdit) isEdit = false;
            else et_scorereview.setText(Integer.toString((int)rb_review.getRating()*20));
        });
        et_scorereview.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId){
                case EditorInfo.IME_ACTION_DONE:
                    isEdit = true;
                    int score = Integer.parseInt(et_scorereview.getText().toString());
                    rb_review.setRating(Math.round(score/20.0));
                    break;
                default:
                    return false;
            }
            return true;
        });
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 데이트픽커 띄우기
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // TODO: 2020-11-20 파이어베이스 연동

        return viewGroup;
    }
    public void setBg (TextView past, TextView current){
        et_reviewlist.setBackgroundResource(R.drawable.bg_line);
        past.setBackgroundColor(Color.parseColor("#00000000"));
        current.setBackgroundResource(R.drawable.bg_selector);
    }
}
