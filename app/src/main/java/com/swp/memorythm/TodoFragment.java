package com.swp.memorythm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoFragment extends Fragment {
    private TextView textViewDate;
    private RecyclerView todoRecyclerView;
    private ImageButton btnAdd;
    private ArrayList<TodoData> mArrayList;
    private TodoAdapter mAdapter;

    public static TodoFragment newInstance() { return new TodoFragment(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.template_todo, container, false);

        textViewDate = rootView.findViewById(R.id.tv_date);
        todoRecyclerView = rootView.findViewById(R.id.todo_rcview);
        btnAdd = rootView.findViewById(R.id.btn_add);

        // 텍스트뷰 초기 날짜 현재 날짜로 설정
        textViewDate.setText(PreferenceManager.getString(getContext(), "currentDate"));

        // 리사이클러뷰 설정
        todoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mArrayList = new ArrayList<>();
        mAdapter = new TodoAdapter(getActivity(), mArrayList);
        todoRecyclerView.setAdapter(mAdapter);
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

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

                        TodoData todoData = new TodoData();

                        todoData.setDone(false);
                        todoData.setContent(TodoContent);

                        mArrayList.add(todoData);
                        mAdapter.notifyDataSetChanged();

                        alertDialog.dismiss();
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

        // TODO : 저장, 로드 방법 생각

        return rootView;
    }
}
