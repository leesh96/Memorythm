package com.swp.memorythm;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class StudyTrackerFragment extends Fragment {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private int memoid;
    private TextView textViewDate;
    private EditText et_comment;
    private final EditText[] et_comments = new EditText[24];
    private final ImageView[] iv_time = new ImageView[144];
    private final int[] num_time = new int[144];
    public static StudyTrackerFragment newInstance() {
        return new StudyTrackerFragment();
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
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.template_studytracker, container, false);
        dbHelper = new DBHelper(getContext());

        textViewDate = viewGroup.findViewById(R.id.write_date);
        et_comment = viewGroup.findViewById(R.id.et_comment);
        String packName = Objects.requireNonNull(this.getActivity()).getPackageName();
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 6 ; j++) {
                String amTime, pmTime;
                if(i<9) {
                    amTime = "am0"+(i+1)+"_"+(j+1); pmTime = "pm0"+(i+1)+"_"+(j+1);
                }
                else {
                    amTime="am"+(i+1)+"_"+(j+1); pmTime = "pm"+(i+1)+"_"+(j+1);
                }
                int amId = getResources().getIdentifier(amTime,"id",packName);
                int pmId = getResources().getIdentifier(pmTime,"id",packName);
                iv_time[i*6+j] = viewGroup.findViewById(amId);
                iv_time[72+i*6+j] = viewGroup.findViewById(pmId);
            }
        }
        for (int i = 0; i < 24 ; i++) {
            String name = "et"+i;
            int id=getResources().getIdentifier(name,"id",packName);
            et_comments[i] = viewGroup.findViewById(id);
        }


        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        textViewDate.setText(PreferenceManager.getString(getContext(), "currentDate"));

        textViewDate.setOnClickListener(v -> { // 데이트픽커 띄우기
            new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });


        for (int i = 0; i < iv_time.length ; i++) {
            int finalI = i;
            iv_time[i].setOnClickListener(v-> num_time[finalI]=changeBgColor(iv_time[finalI],num_time[finalI]));
        }


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

    //배경색 바꾸는 함수
    public int changeBgColor(ImageView imageView, int n){
        if(n==0){
            imageView.setBackgroundColor(Color.parseColor("#BFFFFFFF")); //투명도 75%
            return 1;
        }else{
            imageView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
            return 0;
        }
    }
    public void setBgColor(){
        for (int i = 0; i < iv_time.length ; i++) {
            if(num_time[i]==1) changeBgColor(iv_time[i],0);
            else changeBgColor(iv_time[i],1);
        }
    }
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

    public Boolean saveData(String Mode, String Bgcolor, String title){
        //String : userdate, studyTimecheck , commentAll , commentTime, splitKey
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String userdate = textViewDate.getText().toString();
        StringBuilder studyTimecheck = new StringBuilder();
        for (int value : num_time) studyTimecheck.append(value);
        String commentAll = et_comment.getText().toString().replaceAll("'", "''");
        StringBuilder txt = new StringBuilder(); //edit text 모두 합쳐서 넣을 String
        for (EditText etComment : et_comments) txt.append(etComment.getText().toString());
        String splitKey = makeKey(txt.toString());
        StringBuilder commentTime = new StringBuilder();
        for (EditText etComment : et_comments) {
            if (!etComment.getText().toString().equals(""))
                commentTime.append(etComment.getText().toString().replaceAll("'", "''")).append(splitKey);
            else commentTime.append(" ").append(splitKey);
        }
        db = dbHelper.getReadableDatabase();
        switch (Mode) {
            case "write":
                db.execSQL("INSERT INTO studytracker('userdate', 'studyTimecheck', 'commentAll', 'commentTime', 'splitKey', 'bgcolor', 'title') " +
                        "VALUES('" + userdate + "', '" + studyTimecheck + "', '" + commentAll + "', '" + commentTime + "','"+ splitKey +"', '"+Bgcolor+"', '"+title+"');");
                db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '메모';");
                break;
            case "view":
                if (getArguments() != null) {
                    memoid = getArguments().getInt("memoid");
                }
                db.execSQL("UPDATE studytracker SET userdate = '"+userdate+"', studyTimecheck = '"+studyTimecheck+"', commentAll = '"+commentAll+"', commentTime = '"+commentTime+"', splitKey = '"+splitKey+"', editdate = '"+dateFormat.format(date.getTime()) + "' WHERE id = "+memoid+";");
                break;
        }
        return true;
    }
    public void setData(){
        String userdate=null, studyTimecheck=null , commentAll =null, commentTime=null, splitKey=null;
        String[] array, array1;
        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();
        if (getArguments() != null) {
            memoid = getArguments().getInt("memoid");
            Cursor cursor = db.rawQuery("SELECT userdate, studyTimecheck , commentAll , commentTime, splitKey  FROM studytracker WHERE id = "+memoid+"", null);
            while (cursor.moveToNext()) {
                userdate = cursor.getString(0); studyTimecheck = cursor.getString(1); commentAll = cursor.getString(2); commentTime = cursor.getString(3); splitKey = cursor.getString(4);
            }
            textViewDate.setText(userdate);
            array = studyTimecheck.split("");
            for (int i = 0; i <array.length ; i++) num_time[i] = Integer.parseInt(array[i]);
            et_comment.setText(commentAll);
            array1 = commentTime.split(splitKey);
            for (int i = 0; i <array1.length ; i++) {
                if(!array1[i].equals(" ")) et_comments[i].setText(array1[i]);
            }
            setBgColor();
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
}
