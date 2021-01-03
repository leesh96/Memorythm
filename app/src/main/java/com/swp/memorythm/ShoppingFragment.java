package com.swp.memorythm;

import androidx.appcompat.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

public class ShoppingFragment extends Fragment {
    private TextView textViewDate;
    private RecyclerView shoppingRecyclerView;
    private ImageButton btnAdd;
    private ArrayList<ShoppingData> mArrayList;
    private ShoppingAdapter mAdapter;

    public int memoid;
    private String Userdate;
    private StringBuilder Content, Amount, Bought;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public boolean fromFixedFragment;

    public boolean isFromFixedFragment() {
        return fromFixedFragment;
    }

    public void setFromFixedFragment(boolean fromFixedFragment) {
        this.fromFixedFragment = fromFixedFragment;
    }

    public static ShoppingFragment newInstance() { return new ShoppingFragment(); }

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
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_shopping, container, false);

        textViewDate = rootView.findViewById(R.id.tv_date);
        shoppingRecyclerView = rootView.findViewById(R.id.shopping_rcview);
        btnAdd = rootView.findViewById(R.id.btn_add);

        // 리사이클러뷰 설정
        shoppingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shoppingRecyclerView.setHasFixedSize(true);
        mAdapter = new ShoppingAdapter(getActivity(), mArrayList);
        shoppingRecyclerView.setAdapter(mAdapter);

        if (getArguments() != null) {
            textViewDate.setText(Userdate);
            mAdapter.notifyDataSetChanged();
            if (isFromFixedFragment()) {
                setEnable(rootView, false);
                btnAdd.setVisibility(View.GONE);
            }
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
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // list 항목 추가
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_shopping, null, false);
                builder.setView(dialogView);

                EditText editTextShopping, editTextAmount;
                Button btnApply, btnCancel;

                editTextShopping = dialogView.findViewById(R.id.et_shopping);
                editTextAmount = dialogView.findViewById(R.id.et_amount);
                btnApply = dialogView.findViewById(R.id.btn_apply);
                btnCancel = dialogView.findViewById(R.id.btn_cancel);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ShoppingContent = editTextShopping.getText().toString();
                        String ShoppingAmount = editTextAmount.getText().toString();

                        if (ShoppingContent.equals("") | ShoppingContent == null) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.dismiss();
                                }
                            });
                            alert.setMessage("사야할 물건을 입력하세요!");
                            alert.show();
                        } else if (ShoppingAmount.equals("") | ShoppingAmount == null) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.dismiss();
                                }
                            });
                            alert.setMessage("수량을 입력하세요!");
                            alert.show();
                        } else {
                            ShoppingData shoppingData = new ShoppingData();

                            shoppingData.setBought(false);
                            shoppingData.setContent(ShoppingContent);
                            shoppingData.setAmount(ShoppingAmount);

                            mArrayList.add(shoppingData);
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

    // DB 닫기
    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private void setEnable(ViewGroup viewGroup, boolean enable) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                setEnable((ViewGroup) child, enable);
            } else {
                child.setEnabled(enable);
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
        Bought = new StringBuilder();
        Amount = new StringBuilder();

        StringBuilder txt = new StringBuilder();

        for (ShoppingData shoppingData : mArrayList) {
            txt.append(shoppingData.getContent());
            txt.append(shoppingData.isBought());
            txt.append(shoppingData.getAmount());
        }

        String splitkey = makeKey(txt.toString());

        for (ShoppingData shoppingData : mArrayList) {
            Content.append(shoppingData.getContent()).append(splitkey);
            Bought.append(shoppingData.isBought()).append(splitkey);
            Amount.append(shoppingData.getAmount()).append(splitkey);
        }

        // editdate 컬럼 업데이트 때문에
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        switch (Mode) {
            case "write":   // 저장
                try {
                    db.execSQL("INSERT INTO shoppinglist('userdate', 'content', 'bought', 'amount', 'splitkey', 'title', 'bgcolor') " +
                            "VALUES('"+Userdate+"', '"+Content+"', '"+Bought+"', '"+Amount+"', '"+splitkey+"', '"+Title+"', '"+Bgcolor+"');");
                    final Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
                    cursor.moveToFirst();
                    memoid = cursor.getInt(0);
                    db.execSQL("UPDATE folder SET count = count + 1 WHERE name = '메모';");
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
                    db.execSQL("UPDATE shoppinglist SET userdate = '"+Userdate+"', content = '"+Content+"', bought = '"+Bought+"', amount = '"+Amount+"', splitkey = '"+splitkey+"', title = '"+Title+"', editdate = '"+dateFormat.format(date.getTime())+"' WHERE id = "+memoid+";");
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
            cursor = db.rawQuery("SELECT userdate, content, bought, amount, splitkey FROM shoppinglist WHERE id = "+memoid+"", null);
            cursor.moveToFirst();

            Userdate = cursor.getString(0);
            String allContent = cursor.getString(1);
            String allBought = cursor.getString(2);
            String allAmount = cursor.getString(3);
            String splitkey = cursor.getString(4);

            String[] contentArray = allContent.split(splitkey);
            String[] boughtArray = allBought.split(splitkey);
            String[] amountArray = allAmount.split(splitkey);

            for (int i = 0; i < contentArray.length; i++) {
                ShoppingData shoppingData = new ShoppingData();

                shoppingData.setContent(contentArray[i]);
                shoppingData.setBought(Boolean.parseBoolean(boughtArray[i]));
                shoppingData.setAmount(amountArray[i]);

                mArrayList.add(shoppingData);
            }
            Toast.makeText(getContext(), "데이터 로드 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "데이터 로드 실패", Toast.LENGTH_SHORT).show();
        }
    }
}
