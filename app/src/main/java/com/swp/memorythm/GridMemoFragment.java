package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class GridMemoFragment extends Fragment {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private int memoid;
    private TextView textViewDate;
    private EditText editTextContent;

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

    public Boolean saveData(String Mode, String Bgcolor, String title){ //저장 및 수정
        //userdate, content
        String userdate = textViewDate.getText().toString();
        String content = editTextContent.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        db = dbHelper.getReadableDatabase();
        if (content.equals("")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
            alert.setMessage("내용을 입력하세요!").setPositiveButton("확인", (dialog, which) -> dialog.dismiss()).show();
            return false;
        } else {
            switch (Mode) {
                case "write":
                    db.execSQL("INSERT INTO gridmemo('userdate', 'content', 'bgcolor', 'title') VALUES('" + userdate + "', '" + content + "', '"+Bgcolor+"', '"+title+"');");
                    // 작성하면 view 모드로 바꾸기 위해 최근 삽입한 레코드 id로 바꿔줌
                    final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                    cursor.moveToFirst();
                    memoid = cursor.getInt(0);
                    break;
                case "view":
                    if (getArguments() != null) {
                        memoid = getArguments().getInt("memoid");
                    }
                    db.execSQL("UPDATE gridmemo SET userdate = '"+userdate+"', content = '"+content+"', title = '"+title+"', editdate = '"+dateFormat.format(date.getTime()) + "' WHERE id = "+memoid+";");
                    break;
            }
        }
        return true;
    }

    public void setData(){
        String userdate=null, content=null;

        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();
        if (getArguments() != null) {
            memoid = getArguments().getInt("memoid");
            Cursor cursor = db.rawQuery("SELECT userdate, content FROM gridmemo WHERE id = "+memoid+"", null);
            while (cursor.moveToNext()) {
                userdate = cursor.getString(0);
                content = cursor.getString(1);
            }
            textViewDate.setText(userdate);
            editTextContent.setText(content);
        }
    }
}
