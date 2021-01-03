package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReviewFragment extends Fragment {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private int memoid;
    private TextView textViewDate;
    private EditText et_reviewList, et_scoreReview, et_title, et_content;
    private final TextView[] tv_reviews = new TextView[9];
    private int pastChoice;
    private RatingBar rb_review;
    private boolean isEdit = false, isUser = false;
    private boolean fromFixedFragment;

    public boolean isFromFixedFragment() {
        return fromFixedFragment;
    }

    public void setFromFixedFragment(boolean fromFixedFragment) {
        this.fromFixedFragment = fromFixedFragment;
    }

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
        dbHelper = new DBHelper(getContext());

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
        for (int i = 0; i < tv_reviews.length; i++) {
            int finalI = i;
            tv_reviews[i].setOnClickListener(v -> {
                setBg(pastChoice, tv_reviews[finalI]);
                pastChoice = finalI;
                isUser = false;
            });
        }

        //레이팅 바
        rb_review.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (isEdit) isEdit = false;
            else {
                String string = Integer.toString((int) rb_review.getRating() * 20);
                et_scoreReview.setText(string);
            }
        });

        textViewDate.setOnClickListener(v -> { // 데이트픽커 띄우기
            new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });


        final View activityRootView = viewGroup.findViewById(R.id.parentLayout);

        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            //r will be populated with the coordinates of your view that area still visible.
            activityRootView.getWindowVisibleDisplayFrame(r);

            int heightDiff = activityRootView.getRootView().getHeight() - r.height();

            if (heightDiff < 0.25 * activityRootView.getRootView().getHeight() && ReviewFragment.this.getActivity() != null) {
                Log.d("키보드 ", "내려감?");
                if (ReviewFragment.this.getActivity().getCurrentFocus() == et_reviewList) {
                    et_reviewList.setBackgroundResource(R.drawable.bg_selector);
                    tv_reviews[pastChoice].setBackgroundColor(Color.parseColor("#00000000"));
                    isUser = true;
                    et_reviewList.clearFocus();
                } else if (ReviewFragment.this.getActivity().getCurrentFocus() == et_scoreReview) {
                    isEdit = true;
                    if (!et_scoreReview.getText().toString().equals("")) {
                        int score = Integer.parseInt(et_scoreReview.getText().toString());
                        rb_review.setRating(Math.round(score / 20.0));
                        et_scoreReview.clearFocus();
                    }
                } else if (ReviewFragment.this.getActivity().getCurrentFocus() == et_content)
                    et_content.clearFocus();
                else if (ReviewFragment.this.getActivity().getCurrentFocus() == et_title)
                    et_title.clearFocus();

            }
            activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this::updateLabel);
        });

        // 수정 불가하게 만들기
        if (isFromFixedFragment()) setClickable((ViewGroup) activityRootView);

        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setData();
    }

    public void setClickable(View view){
        view.setClickable(false);
        if(view instanceof ViewGroup){
            ViewGroup group = (ViewGroup)view;
            for (int i = 0; i < group.getChildCount() ; i++) {
                setClickable(group.getChildAt(i));
            }
        }
    }

    public int getMemoid() {
        return memoid;
    }

    public void setBg(int past, TextView current) {
        et_reviewList.setBackgroundResource(R.drawable.bg_line);
        tv_reviews[past].setBackgroundColor(Color.parseColor("#00000000"));
        current.setBackgroundResource(R.drawable.bg_selector);
    }

    public Boolean saveData(String Mode, String Bgcolor, String title) {
        //String : userdate, categoryName, reviewTitle, reviewContent
        //int : categoryCheck, starNum, score
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String userdate = textViewDate.getText().toString();
        String reviewTitle = et_title.getText().toString().replaceAll("'", "''");
        String reviewContent = et_content.getText().toString().replaceAll("'", "''");
        int starNum = (int) rb_review.getRating();
        int score = 0;
        if (!et_scoreReview.getText().toString().equals("")) {
            score = Integer.parseInt(et_scoreReview.getText().toString());
        }

        String categoryName;
        int categoryCheck;
        if (isUser) {
            categoryName = et_reviewList.getText().toString().replaceAll("'", "''");
            categoryCheck = 9;
        } else {
            categoryName = "null";
            categoryCheck = pastChoice;
        }
        db = dbHelper.getReadableDatabase();
        switch (Mode) {
            case "write":
                db.execSQL("INSERT INTO review('userdate', 'categoryName', 'reviewTitle', 'reviewContent', 'categoryCheck', 'starNum', 'score', 'bgcolor', 'title') " +
                        "VALUES('" + userdate + "', '" + categoryName + "', '" + reviewTitle + "', '" + reviewContent + "', '" + categoryCheck + "', '" + starNum + "', '" + score + "', '" + Bgcolor + "', '" + title + "');");
                final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                cursor.moveToFirst();
                memoid = cursor.getInt(0);
                db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '메모';");
                break;
            case "view":
                if (getArguments() != null) {
                    memoid = getArguments().getInt("memoid");
                }
                db.execSQL("UPDATE review SET userdate = '" + userdate + "', categoryName = '" + categoryName + "', reviewTitle = '" + reviewTitle + "', reviewContent = '" + reviewContent + "', categoryCheck = '" + categoryCheck + "', starNum = '" + starNum + "', score = '" + score + "'," +
                        "editdate = '" + dateFormat.format(date.getTime()) + "' WHERE id = " + memoid + ";");
                break;
        }
        return true;
    }

    public void setData() {
        String userDate = null, categoryName = null, reviewTitle = null, reviewContent = null;
        int categoryCheck = 0, starNum = 0, score = 0;
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();
        if (getArguments() != null) {
            memoid = getArguments().getInt("memoid");
            Cursor cursor = db.rawQuery("SELECT userdate, categoryName, reviewTitle, reviewContent, categoryCheck, starNum, score FROM review WHERE id = " + memoid + "", null);
            while (cursor.moveToNext()) {
                userDate = cursor.getString(0);
                categoryName = cursor.getString(1);
                reviewTitle = cursor.getString(2);
                reviewContent = cursor.getString(3);
                categoryCheck = cursor.getInt(4);
                starNum = cursor.getInt(5);
                score = cursor.getInt(6);
            }
            textViewDate.setText(userDate);
            et_title.setText(reviewTitle);
            et_content.setText(reviewContent);
            rb_review.setRating(starNum);
            isEdit = true;
            et_scoreReview.setText(Integer.toString(score));
            isEdit = false;
            if (categoryCheck == 9) {
                et_reviewList.setText(categoryName);
                et_reviewList.setBackgroundResource(R.drawable.bg_selector);
                tv_reviews[pastChoice].setBackgroundColor(Color.parseColor("#00000000"));
            } else setBg(pastChoice, tv_reviews[categoryCheck]);

            // 데이트픽커 다이얼로그에 userdate로 뜨게 하는 코드
            String toDate = textViewDate.getText().toString();
            SimpleDateFormat stringtodate = new SimpleDateFormat("yyyy - MM - dd");
            try {
                Date fromString = stringtodate.parse(toDate);
                myCalendar.setTime(fromString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // 데이트픽커 띄우기
            textViewDate.setOnClickListener(v -> {
                // 뷰 모드면 날짜 맞게 해줘야댐
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            });
        }

    }

    // 널 값 검증
    public boolean checkNull() {
        String reviewTitle = et_title.getText().toString();
        return !(reviewTitle.equals("") | reviewTitle == null);
    }
}

