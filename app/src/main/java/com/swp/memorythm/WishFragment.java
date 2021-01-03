package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class WishFragment extends Fragment {
    private TextView textViewDate;
    private TextView[] textViewsCategory = new TextView[9];
    private EditText editTextCustomCategory;
    private RecyclerView wishRecyclerView;
    private ImageButton btnAdd;
    private ArrayList<WishData> mArrayList;
    private WishAdapter mAdapter;

    private int selectCategory;
    private boolean selectCustomCategory;

    public int memoid;
    private String Userdate, CustomCategoryName;
    private StringBuilder Content, Wished;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public boolean fromFixedFragment;

    public boolean isFromFixedFragment() {
        return fromFixedFragment;
    }

    public void setFromFixedFragment(boolean fromFixedFragment) {
        this.fromFixedFragment = fromFixedFragment;
    }

    public static WishFragment newInstance() { return new WishFragment(); }

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        mArrayList = new ArrayList<>();

        if (getArguments() != null) {
            memoid = getArguments().getInt("memoid");
            getData(memoid);
        }
    }

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
        textViewsCategory[8] = rootView.findViewById(R.id.tv_study);
        editTextCustomCategory = rootView.findViewById(R.id.et_customcate);
        wishRecyclerView = rootView.findViewById(R.id.wish_rcview);
        btnAdd = rootView.findViewById(R.id.btn_add);

        // 리사이클러뷰 설정
        wishRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wishRecyclerView.setHasFixedSize(true);
        mAdapter = new WishAdapter(getActivity(), mArrayList);
        wishRecyclerView.setAdapter(mAdapter);

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

        if (getArguments() != null) {
            textViewDate.setText(Userdate);
            if (selectCustomCategory) {
                editTextCustomCategory.setText(CustomCategoryName);
                editTextCustomCategory.setBackgroundResource(R.drawable.bg_selector);
                textViewsCategory[selectCategory].setBackgroundColor(Color.TRANSPARENT);
            } else {
                changeCategory(0, selectCategory);
            }
            if (isFromFixedFragment()) {
                setClickable(rootView, false);
                btnAdd.setVisibility(View.GONE);
            }
        } else {
            // 텍스트뷰 초기 날짜 현재 날짜로 설정
            textViewDate.setText(PreferenceManager.getString(getContext(), "currentDate"));
            selectCategory = 0;
            selectCustomCategory = false;
            changeCategory(0, 0);
            mArrayList.clear();
            mAdapter.notifyDataSetChanged();
        }

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
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
                    if (WishFragment.this.getActivity().getCurrentFocus() == editTextCustomCategory) {
                        editTextCustomCategory.setBackgroundResource(R.drawable.bg_selector);
                        textViewsCategory[selectCategory].setBackgroundColor(Color.TRANSPARENT);
                        selectCustomCategory = true;
                        editTextCustomCategory.clearFocus();
                    }
                }
            }
        });

        return rootView;
    }

    // DB 닫기
    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    // 고정프래그먼트에서 뷰 이벤트 막는 함수
    private void setClickable(ViewGroup viewGroup, boolean enable) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                setClickable((ViewGroup) child, enable);
            } else {
                child.setClickable(enable);
            }
        }
    }

    // 카테고리 변경 함수
    public void changeCategory(int old, int current) {
        editTextCustomCategory.setBackgroundResource(R.drawable.border_d);
        textViewsCategory[old].setBackgroundColor(Color.TRANSPARENT);
        textViewsCategory[current].setBackgroundResource(R.drawable.bg_selector);
    }

    // 텍스트뷰 날짜 업데이트
    private void updateLabel() {
        String DateFormat = "yyyy - MM - dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat, Locale.KOREA);

        textViewDate.setText(sdf.format(myCalendar.getTime()));
    }

    // 메모아이디 가져오기
    public int getMemoid() {
        return memoid;
    }

    // 널 값 검증
    public boolean checkNull() {
        if (mArrayList.size() == 0) {
            return false;
        } else return true;
    }

    // 분리키 만들기
    public String makeKey(String txt){
        StringBuilder key = new StringBuilder();
        Random random = new Random();
        do {
            for (int i = 0; i < 8; i++) {
                int rIndex = random.nextInt(3);
                switch (rIndex) {
                    case 0:
                        // a-z
                        key.append((char) ((int) (random.nextInt(26)) + 97));
                        break;
                    case 1:
                        // A-Z
                        key.append((char) ((int) (random.nextInt(26)) + 65));
                        break;
                    case 2:
                        // 0-9
                        key.append(random.nextInt(10));
                        break;
                }
            }
        } while (txt.contains(key));
        return key.toString();
    }

    // 저장 및 수정
    public boolean saveData(String Mode, String Bgcolor, String Title) {
        boolean success = false;

        Userdate = textViewDate.getText().toString();
        Content = new StringBuilder();
        Wished = new StringBuilder();

        StringBuilder txt = new StringBuilder();

        for (WishData wishData : mArrayList) {
            txt.append(wishData.getContent());
            txt.append(wishData.isWished());
        }

        String splitkey = makeKey(txt.toString());

        for (WishData wishData : mArrayList) {
            Content.append(wishData.getContent()).append(splitkey);
            Wished.append(wishData.isWished()).append(splitkey);
        }

        int category;
        if (selectCustomCategory) {
            CustomCategoryName = editTextCustomCategory.getText().toString().replaceAll("'", "''");
            category = 9;
        } else {
            CustomCategoryName = "null";
            category = selectCategory;
        }

        // editdate 컬럼 업데이트 때문에
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        switch (Mode) {
            case "write":
                try {
                    db.execSQL("INSERT INTO wishlist('userdate', 'content', 'wished', 'splitkey', 'category', 'customcategory', 'title', 'bgcolor') " +
                            "VALUES('"+Userdate+"', '"+Content+"', '"+Wished+"', '"+splitkey+"', "+category+", '"+CustomCategoryName+"', '"+Title+"', '"+Bgcolor+"');");
                    final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                    cursor.moveToFirst();
                    memoid = cursor.getInt(0);
                    db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '메모';");
                    success = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "view":
                if (getArguments() != null) {
                    memoid = getArguments().getInt("memoid");
                }
                try {
                    db.execSQL("UPDATE wishlist SET userdate = '"+Userdate+"', content = '"+Content+"', wished = '"+Wished+"', splitkey = '"+splitkey+"', category = "+category+", customcategory = '"+CustomCategoryName+"', " +
                            "title = '"+Title+"', editdate = '"+dateFormat.format(date.getTime())+"' WHERE id = "+memoid+";");
                    success = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        return success;
    }

    // 데이터 로드
    private void getData(int id) {
        Cursor cursor;
        try {
            cursor = db.rawQuery("SELECT userdate, content, wished, splitkey, category, customcategory FROM wishlist WHERE id = " + memoid + "", null);
            cursor.moveToFirst();

            Userdate = cursor.getString(0);
            String allContent = cursor.getString(1);
            String allWished = cursor.getString(2);
            String splitkey = cursor.getString(3);
            int category = cursor.getInt(4);
            CustomCategoryName = cursor.getString(5);

            String[] contentArray = allContent.split(splitkey);
            String[] wishedArray = allWished.split(splitkey);

            for (int i = 0; i < contentArray.length; i++) {
                WishData wishData = new WishData();

                wishData.setContent(contentArray[i]);
                wishData.setWished(Boolean.parseBoolean(wishedArray[i]));

                mArrayList.add(wishData);
            }

            if (category == 9) {
                selectCustomCategory = true;
                selectCategory = 0;
            } else {
                selectCategory = category;
            }

            Toast.makeText(getContext(), "데이터 로드 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "데이터 로드 실패", Toast.LENGTH_SHORT).show();
        }
    }
}
