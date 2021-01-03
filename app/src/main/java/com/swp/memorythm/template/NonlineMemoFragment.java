package com.swp.memorythm.template;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.swp.memorythm.DBHelper;
import com.swp.memorythm.PreferenceManager;
import com.swp.memorythm.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NonlineMemoFragment extends Fragment {
    private TextView textViewDate;
    private EditText editTextContent;

    public int memoid;
    private String Userdate, Content;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    // 메인액티비티에서 고정메모 보는 프래그먼트로 만들어졌으면 수정 불가능하게 뷰 조정
    public boolean fromFixedFragment;

    public boolean isFromFixedFragment() {
        return fromFixedFragment;
    }

    public void setFromFixedFragment(boolean fromFixedFragment) {
        this.fromFixedFragment = fromFixedFragment;
    }

    public static NonlineMemoFragment newInstance() {
        return new NonlineMemoFragment();
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        if (getArguments() != null) {
            memoid = getArguments().getInt("memoid");
            getData(memoid);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_nonlinememo, container, false);

        textViewDate = rootView.findViewById(R.id.write_date);
        editTextContent = rootView.findViewById(R.id.nonlinememo_content);

        if (getArguments() != null) {
            textViewDate.setText(Userdate);
            editTextContent.setText(Content);
            // 수정 불가하게 만들기
            if (isFromFixedFragment()) {
                invaildTouch(rootView);
            }
        } else {
            // 텍스트뷰 초기 날짜 현재 날짜로 설정
            textViewDate.setText(PreferenceManager.getString(getContext(), "currentDate"));
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

        textViewDate.setOnClickListener(new View.OnClickListener() { // 데이트픽커 띄우기
            @Override
            public void onClick(View v) {
                // 뷰 모드면 날짜 맞게 해줘야댐
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
    private void invaildTouch(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                invaildTouch((ViewGroup) child);
            } else {
                child.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
            }
        }
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
        Content = editTextContent.getText().toString();

        if (Content.equals("") | Content == null) {
            return false;
        } else {
            return true;
        }
    }

    // 저장 및 수정
    public boolean saveData(String Mode, String Bgcolor, String Title) {
        boolean success = false;

        Userdate = textViewDate.getText().toString();
        Content = editTextContent.getText().toString();

        // editdate 컬럼 업데이트 때문에
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        switch (Mode) {
            case "write":   // 메모 최초 작성
                try {
                    db.execSQL("INSERT INTO nonlinememo('userdate', 'content', 'bgcolor', 'title') VALUES('"+Userdate+"', '"+Content+"', '"+Bgcolor+"', '"+Title+"');");
                    // 작성하면 view 모드로 바꾸기 위해 최근 삽입한 레코드 id로 바꿔줌
                    final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                    cursor.moveToFirst();
                    memoid = cursor.getInt(0);
                    db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '메모';");
                    success = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "view":    // 메모 수정
                if (getArguments() != null) {   // 메모리스트에서 view 모드로 넘어왔을 때  id 업데이트
                    memoid = getArguments().getInt("memoid");
                }
                try {
                    db.execSQL("UPDATE nonlinememo SET userdate = '"+Userdate+"', content = '"+Content+"', Title = '"+Title+"', editdate = '"+dateFormat.format(date.getTime())+"' WHERE id = "+memoid+";");
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
            cursor = db.rawQuery("SELECT userdate, content FROM nonlinememo WHERE id = "+memoid+"", null);
            cursor.moveToFirst();
            Userdate = cursor.getString(0);
            Content = cursor.getString(1);
            Toast.makeText(getContext(), "데이터 로드 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "데이터 로드 실패", Toast.LENGTH_SHORT).show();
        }
    }
}
