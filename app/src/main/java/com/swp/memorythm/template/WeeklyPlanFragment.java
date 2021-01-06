package com.swp.memorythm.template;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.swp.memorythm.DBHelper;
import com.swp.memorythm.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class WeeklyPlanFragment extends Fragment implements TextWatcher {
    private TextView textViewDate;
    private EditText editTextMon, editTextTue, editTextWed, editTextThu, editTextFri, editTextSat, editTextSun,
            editTextDayMon, editTextDayTue, editTextDayWed, editTextDayThu, editTextDayFri, editTextDaySat, editTextDaySun;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private EditText[] weekView = new EditText[7], dayView = new EditText[7];
    public int memoid;
    private String userDate;
    private String[] setContent, setDay;
    private StringBuilder contentWeek, contentDay;

    // 메인액티비티에서 고정메모 보는 프래그먼트로 만들어졌으면 수정 불가능하게 뷰 조정
    public boolean fromFixedFragment;

    public boolean isFromFixedFragment() {
        return fromFixedFragment;
    }

    public void setFromFixedFragment(boolean fromFixedFragment) {
        this.fromFixedFragment = fromFixedFragment;
    }

    public static WeeklyPlanFragment newInstance() {
        return new WeeklyPlanFragment();
    }

    // 캘린더 객체 생성
    Calendar myCalendar = Calendar.getInstance();
    Calendar calCalendar = Calendar.getInstance();

    //데이트픽커 다이얼로그
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            updateLabel();
        }
    };

    // 텍스트뷰 날짜 업데이트
    private void updateLabel() {
        String DateFormat = "yyyy - MM";
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat, Locale.KOREA);

        textViewDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();

        if (getArguments() != null) {
            memoid = getArguments().getInt("memoid");
        }
        Cursor cursor = db.rawQuery("SELECT userdate, contentWeek, contentDay, splitKey FROM weeklyplan WHERE id = " + memoid + "", null);
        while (cursor.moveToNext()) {
            userDate = cursor.getString(0);
            String splitKey = cursor.getString(3);
            setContent = cursor.getString(1).split(splitKey);
            setDay = cursor.getString(2).split(",");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_weeklyplan, container, false);

        textViewDate = rootView.findViewById(R.id.write_date);
        editTextMon = rootView.findViewById(R.id.memo_content_mon);
        editTextTue = rootView.findViewById(R.id.memo_content_tue);
        editTextWed = rootView.findViewById(R.id.memo_content_wed);
        editTextThu = rootView.findViewById(R.id.memo_content_thu);
        editTextFri = rootView.findViewById(R.id.memo_content_fri);
        editTextSat = rootView.findViewById(R.id.memo_content_sat);
        editTextSun = rootView.findViewById(R.id.memo_content_sun);
        editTextDayMon = rootView.findViewById(R.id.day_mon);
        editTextDayTue = rootView.findViewById(R.id.day_tue);
        editTextDayWed = rootView.findViewById(R.id.day_wed);
        editTextDayThu = rootView.findViewById(R.id.day_thu);
        editTextDayFri = rootView.findViewById(R.id.day_fri);
        editTextDaySat = rootView.findViewById(R.id.day_sat);
        editTextDaySun = rootView.findViewById(R.id.day_sun);

        weekView[0] = editTextMon;
        weekView[1] = editTextTue;
        weekView[2] = editTextWed;
        weekView[3] = editTextThu;
        weekView[4] = editTextFri;
        weekView[5] = editTextSat;
        weekView[6] = editTextSun;

        dayView[0] = editTextDayMon;
        dayView[1] = editTextDayTue;
        dayView[2] = editTextDayWed;
        dayView[3] = editTextDayThu;
        dayView[4] = editTextDayFri;
        dayView[5] = editTextDaySat;
        dayView[6] = editTextDaySun;

        addListener();

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy - MM", Locale.KOREA);
        textViewDate.setText(simpleDateFormat.format(currentTime));

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 데이트픽커 띄우기
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {

            textViewDate.setText(userDate);
            String[] setDate = userDate.split(" - ");
            myCalendar.set(Calendar.YEAR, Integer.parseInt(setDate[0]));
            myCalendar.set(Calendar.MONTH, Integer.parseInt(setDate[1]) - 1);

            for (int i = 0; i < weekView.length; i++) {

                weekView[i].setText(setContent[i]);

            }
            removeListener();
            for (int i = 0; i < dayView.length; i++) {

                if (setDay[i].equals(" ")) dayView[i].setText("");
                else dayView[i].setText(setDay[i]);
            }
            addListener();

            // 수정 불가하게 만들기
            if (isFromFixedFragment()) {
                textViewDate.setEnabled(false);

                for (EditText editText : weekView) {

                    editText.setEnabled(false);
                }
                for (EditText editText : dayView) {

                    editText.setEnabled(false);
                }
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[0]) {

            calculateDay(0);
        } else if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[1]) {

            calculateDay(1);
        } else if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[2]) {

            calculateDay(2);
        } else if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[3]) {

            calculateDay(3);
        } else if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[4]) {

            calculateDay(4);
        } else if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[5]) {

            calculateDay(5);
        } else if (WeeklyPlanFragment.this.getActivity().getCurrentFocus() == dayView[6]) {

            calculateDay(6);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void removeListener() {

        for (EditText editText : dayView) {

            editText.removeTextChangedListener(this);
        }
    }

    public void addListener() {

        for (EditText editText : dayView) {

            editText.addTextChangedListener(this);
        }
    }

    // 메모아이디 가져오기
    public int getMemoid() {
        return memoid;
    }

    // 저장 및 수정
    public boolean saveData(String Mode, String Bgcolor, String title) {
        db = dbHelper.getReadableDatabase();

        userDate = textViewDate.getText().toString();
        contentWeek = new StringBuilder();
        contentDay = new StringBuilder();
        String splitKey = makeKey();

        for (EditText editText : weekView) {

            if (editText.getText().toString().equals("")) {

                contentWeek.append(" ").append(splitKey);
            } else {

                contentWeek.append(editText.getText().toString().replaceAll("'", "''")).append(splitKey);
            }
        }

        for (EditText editText : dayView) {

            if (editText.getText().toString().equals("")) {

                contentDay.append(" ").append(",");
            } else {

                contentDay.append(editText.getText().toString()).append(",");
            }
        }

        //editdate 컬럼 업데이트 때문에
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        switch (Mode) {
            case "write":
                db.execSQL("INSERT INTO weeklyplan('userdate', 'contentWeek', 'contentDay', 'splitKey', 'title', 'bgcolor') VALUES('" + userDate + "', '" + contentWeek + "', '" + contentDay + "', '" + splitKey + "', '" + title + "', '" + Bgcolor + "');");
                // 작성하면 view 모드로 바꾸기 위해 최근 삽입한 레코드 id로 바꿔줌
                final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                cursor.moveToFirst();
                memoid = cursor.getInt(0);
                db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '메모';");
                break;
            case "view":
                // 메모 수정
                if (getArguments() == null) {
                    db.execSQL("UPDATE weeklyplan SET userdate = '" + userDate + "', contentWeek = '" + contentWeek + "', contentDay = '" + contentDay + "', splitKey = '" + splitKey + "', title = '" + title + "', editdate = '" + dateFormat.format(date.getTime()) + "' WHERE id = " + memoid + ";");
                } else {
                    memoid = getArguments().getInt("memoid");
                    db.execSQL("UPDATE weeklyplan SET userdate = '" + userDate + "', contentWeek = '" + contentWeek + "', contentDay = '" + contentDay + "', splitKey = '" + splitKey + "', title = '" + title + "', editdate = '" + dateFormat.format(date.getTime()) + "' WHERE id = " + memoid + ";");
                }
                break;
        }
        return true;
    }

    public String makeKey() {
        StringBuilder key = new StringBuilder();
        Random random = new Random();

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
        return key.toString();
    }

    public void calculateDay(int index) {

        removeListener();

        int std, diff, day;
        String input;

        if (dayView[index].getText().toString().equals("")) {

            addListener();
            return;
        }

        std = Integer.parseInt(dayView[index].getText().toString());

        for (int i = 0; i < dayView.length; i++) {

            diff = i - index;
            day = std + diff;
            input = Integer.toString(day);

            if (i == index) continue;
            if (day < 1) {

                calCalendar.set(Calendar.MONTH, myCalendar.get(Calendar.MONTH) - 1);
                input = Integer.toString(calCalendar.getActualMaximum(Calendar.DATE) + day);
            } else if (day == myCalendar.getActualMaximum(Calendar.DATE)) {

                index = i;
                std = 0;
            } else if (day > myCalendar.getActualMaximum(Calendar.DATE)) {

                input = Integer.toString(diff);
            }
            dayView[i].setText(input);
        }
        addListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
    }
}
