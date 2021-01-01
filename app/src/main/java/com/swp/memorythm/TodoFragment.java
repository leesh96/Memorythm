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

public class TodoFragment extends Fragment {
    private TextView textViewDate;
    private RecyclerView todoRecyclerView;
    private ImageButton btnAdd;
    private ArrayList<TodoData> mArrayList;
    private TodoAdapter mAdapter;

    public int memoid;
    private String Userdate;
    private StringBuilder Content, Done;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public static TodoFragment newInstance() { return new TodoFragment(); }

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

        mArrayList = new ArrayList<>();

        if (getArguments() != null) {
            memoid = getArguments().getInt("memoid");
            getData(memoid);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_todo, container, false);

        textViewDate = rootView.findViewById(R.id.tv_date);
        todoRecyclerView = rootView.findViewById(R.id.todo_rcview);
        btnAdd = rootView.findViewById(R.id.btn_add);

        // 리사이클러뷰 설정
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        todoRecyclerView.setHasFixedSize(true);
        mAdapter = new TodoAdapter(getActivity(), mArrayList);
        todoRecyclerView.setAdapter(mAdapter);

        if (getArguments() != null) {
            textViewDate.setText(Userdate);
            mAdapter.notifyDataSetChanged();
        } else {
            // 텍스트뷰 초기 날짜 현재 날짜로 설정
            textViewDate.setText(PreferenceManager.getString(getContext(), "currentDate"));
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
                // 뷰 모드면 날짜 맞게 해줘야댐
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // todolist 항목 추가
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_todo, null, false);
                builder.setView(dialogView);

                EditText editTextTodo;
                Button btnApply, btnCancel;

                editTextTodo = dialogView.findViewById(R.id.et_todo);
                btnApply = dialogView.findViewById(R.id.btn_apply);
                btnCancel = dialogView.findViewById(R.id.btn_cancel);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String TodoContent = editTextTodo.getText().toString();

                        // 빈 입력 예외처리
                       if (TodoContent.equals("") | TodoContent == null) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.dismiss();
                                }
                            });
                            alert.setMessage("할 일을 입력하세요!");
                            alert.show();
                        } else {
                            TodoData todoData = new TodoData();

                            todoData.setDone(false);
                            todoData.setContent(TodoContent);

                            mArrayList.add(todoData);
                            Log.d("list", String.valueOf(mArrayList));
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

        return rootView;
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
        Userdate = textViewDate.getText().toString();

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
        db = dbHelper.getReadableDatabase();

        boolean success = false;

        Content = new StringBuilder();
        Done = new StringBuilder();

        StringBuilder txt = new StringBuilder();

        for (TodoData todoData : mArrayList) {
            txt.append(todoData.getContent());
            txt.append(todoData.isDone());
        }

        String splitkey = makeKey(txt.toString());

        for (TodoData todoData : mArrayList) {
            Content.append(todoData.getContent()).append(splitkey);
            Done.append(todoData.isDone()).append(splitkey);
        }

        // editdate 컬럼 업데이트 때문에
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        switch (Mode) {
            case "write":   // 저장
                try {
                    db.execSQL("INSERT INTO todolist('userdate', 'content', 'done', 'bgcolor', 'title', 'splitkey') VALUES('"+Userdate+"', '"+Content.toString()+"', '"+Done.toString()+"', '"+Bgcolor+"', '"+Title+"', '"+splitkey+"');");
                    // 작성하면 view 모드로 바꾸기 위해 최근 삽입한 레코드 id로 바꿔줌
                    final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                    cursor.moveToFirst();
                    memoid = cursor.getInt(0);
                    success = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "view":    // 수정
                if (getArguments() != null) {   // 메모리스트에서 view 모드로 왔을 때 id 업데이트
                    memoid = getArguments().getInt("memoid");
                }
                try {
                    db.execSQL("UPDATE todolist SET userdate = '"+Userdate+"', content = '"+Content+"', done = '"+Done+"', Title = '"+Title+"', splitkey = '"+splitkey+"', editdate = '"+dateFormat.format(date.getTime())+"' WHERE id = "+memoid+";");
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
        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        try {
            cursor = db.rawQuery("SELECT userdate, content, done, splitkey FROM todolist WHERE id = "+memoid+"", null);
            cursor.moveToFirst();

            Userdate = cursor.getString(0);
            String content = cursor.getString(1);
            String done = cursor.getString(2);
            String splitkey = cursor.getString(3);

            String[] array = content.split(splitkey);
            String[] array2 = done.split(splitkey);

            for (int i = 0; i < array.length; i++) {
                TodoData todoData = new TodoData();

                todoData.setContent(array[i]);
                todoData.setDone(Boolean.parseBoolean(array2[i]));

                mArrayList.add(todoData);
            }
            Toast.makeText(getContext(), "데이터 로드 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "데이터 로드 실패", Toast.LENGTH_SHORT).show();

        }
    }
}
