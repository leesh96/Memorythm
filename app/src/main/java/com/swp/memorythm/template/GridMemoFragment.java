package com.swp.memorythm.template;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.swp.memorythm.CommonUtils;
import com.swp.memorythm.DBHelper;
import com.swp.memorythm.PreferenceManager;
import com.swp.memorythm.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GridMemoFragment extends Fragment {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private int memoid;
    private TextView textViewDate;
    private EditText editTextContent;
    private boolean fromFixedFragment;
    View activityRootView;

    public boolean isFromFixedFragment() {
        return fromFixedFragment;
    }

    public void setFromFixedFragment(boolean fromFixedFragment) {
        this.fromFixedFragment = fromFixedFragment;
    }

    public static GridMemoFragment newInstance() {
        return new GridMemoFragment();
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.template_gridmemo, container, false);
        dbHelper = new DBHelper(getContext());

        textViewDate = viewGroup.findViewById(R.id.write_date);
        editTextContent = viewGroup.findViewById(R.id.memo_content);

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        textViewDate.setText(PreferenceManager.getString(getContext(), "currentDate"));

        textViewDate.setOnClickListener(v -> { // 데이트픽커 띄우기
            new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        activityRootView = viewGroup.findViewById(R.id.parentLayout);
        // 수정 불가하게 만들기
        if (isFromFixedFragment()) CommonUtils.setTouchable(activityRootView);
        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setData();
    }


    public int getMemoid() {
        return memoid;
    }

    public Boolean saveData(String Mode, String Bgcolor, String title) { //저장 및 수정
        //userdate, content
        String userdate = textViewDate.getText().toString();
        String content = editTextContent.getText().toString().replaceAll("'", "''");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        db = dbHelper.getReadableDatabase();
        switch (Mode) {
            case "write":
                db.execSQL("INSERT INTO gridmemo('userdate', 'content', 'bgcolor', 'title') VALUES('" + userdate + "', '" + content + "', '" + Bgcolor + "', '" + title + "');");
                // 작성하면 view 모드로 바꾸기 위해 최근 삽입한 레코드 id로 바꿔줌
                @SuppressLint("Recycle") final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                cursor.moveToFirst();
                memoid = cursor.getInt(0);
                db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '메모';");
                break;
            case "view":
                if (getArguments() != null) {
                    memoid = getArguments().getInt("memoid");
                }
                db.execSQL("UPDATE gridmemo SET userdate = '" + userdate + "', content = '" + content + "', title = '" + title + "', editdate = '" + dateFormat.format(date.getTime()) + "' WHERE id = " + memoid + ";");
                break;
        }
        return true;
    }

    public void setData() {
        String userdate = null, content = null;

        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();
        if (getArguments() != null) {
            memoid = getArguments().getInt("memoid");
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT userdate, content FROM gridmemo WHERE id = " + memoid + "", null);
            while (cursor.moveToNext()) {
                userdate = cursor.getString(0);
                content = cursor.getString(1);
            }
            textViewDate.setText(userdate);
            editTextContent.setText(content);

            String toDate = textViewDate.getText().toString();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat stringtodate = new SimpleDateFormat("yyyy - MM - dd");
            try {
                Date fromString = stringtodate.parse(toDate);
                myCalendar.setTime(fromString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            textViewDate.setOnClickListener(v -> {
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            });
        }
    }

    // 널 값 검증
    public boolean checkNull() {
        String content = editTextContent.getText().toString();
        return !(content.equals("") | content == null);
    }
}
