package com.swp.memorythm;

import androidx.appcompat.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ShoppingFragment extends Fragment {
    private TextView textViewDate;
    private RecyclerView shoppingRecyclerView;
    private ImageButton btnAdd;
    private ArrayList<ShoppingData> mArrayList;
    private ShoppingAdapter mAdapter;

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

    // 텍스트뷰 날짜 업데이트
    private void updateLabel() {
        String DateFormat = "yyyy - MM - dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormat, Locale.KOREA);

        textViewDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_shopping, container, false);

        textViewDate = rootView.findViewById(R.id.tv_date);
        shoppingRecyclerView = rootView.findViewById(R.id.shopping_rcview);
        btnAdd = rootView.findViewById(R.id.btn_add);

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        textViewDate.setText(PreferenceManager.getString(getContext(), "currentDate"));
        textViewDate.setOnClickListener(new View.OnClickListener() { // 데이트픽커 띄우기
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // 리사이클러뷰 설정
        shoppingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        shoppingRecyclerView.setHasFixedSize(true);
        mArrayList = new ArrayList<>();
        mAdapter = new ShoppingAdapter(getActivity(), mArrayList);
        shoppingRecyclerView.setAdapter(mAdapter);
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

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

    void testSome() {
        Toast.makeText(getContext(), "성공", Toast.LENGTH_SHORT).show();
    }
}
