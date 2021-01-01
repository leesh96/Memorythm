package com.swp.memorythm;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthAdapter extends BaseAdapter {
    Calendar cal, cal_sun;
    Context mContext;
    EditText[] editTexts;
    String[] setContent;
    ArrayList<Integer> dayList = new ArrayList<Integer>();
    int curYear;
    int curMonth;

    public MonthAdapter(Context context, Calendar cal){
        super();
        mContext = context;
        this.cal = cal;
        this.setContent = null;
        init();
    }

    public MonthAdapter(Context context, Calendar cal, String[] setContent){
        super();
        mContext = context;
        this.cal = cal;
        this.setContent = setContent;
        init();
    }

    public void init(){

        calculate();//날짜 계산해서 dayList 배열 값 설정
    }

    public void calculate(){

        dayList.clear();

        cal.set(Calendar.DAY_OF_MONTH, 1); //1일로 설정

        int startDay = cal.get(Calendar.DAY_OF_WEEK); //현재 달 1일의 요일 (1: 일요일, . . . 7: 토요일)
        int lastDay = cal.getActualMaximum(Calendar.DATE); //달의 마지막 날짜
        Log.d("day", startDay+"");
        int cnt = 1;

        for(int i=0; i<startDay-1; i++) { //1일이 일요일이 아니면 그 앞부분에 0넣음

            dayList.add(0);
        }

        for(int i=startDay-1; i<startDay-1+lastDay; i++){ /* 1일의 요일에 따라 시작위치 다르고 마지막 날짜까지 값 지정*/

            dayList.add(cnt);
            cnt++;
        }

        curYear = cal.get(Calendar.YEAR);
        curMonth = cal.get(Calendar.MONTH);
        editTexts = new EditText[dayList.size()];
    }

    @Override
    public int getCount() {
        return dayList.size();
    }

    // 전개된 뷰의 참조값을 저장할 객체
    public class ViewHolder {
        private TextView textView;
        public EditText editText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;
        String date;

        if(convertView == null) {
            // convertView가 null이면 Holder 객체를 생성하고
            // 생성한 Holder 객체에 inflating 한 뷰의 참조값을 저장
            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.item_calendar_gridview, parent, false);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.editText = (EditText) convertView.findViewById(R.id.memo_content);
            editTexts[position] = viewHolder.editText;

            // View의 태그에 Holder 객체를 저장
            convertView.setTag(viewHolder);
        } else {
            // convertView가 null이 아니면 뷰를 생성할때 태그에 저장했던 Holder 객체가 존재
            // 이 Holder 객체는 자신을 inflating한 참조값(다시 전개할 필요가 없다.)
            viewHolder = (ViewHolder) convertView.getTag();
            editTexts[position] = viewHolder.editText;
        }

        int day = dayList.get(position); //몇일

        if(day == 0) {

            date = "";
        }
        else {

            date = Integer.toString(day);
        }

        viewHolder.textView.setText(date); //날짜 값이 0이면 ""으로, 아니면 날짜값으로 TextView의 Text 지정

        if(setContent != null) {

            viewHolder.editText.setText(setContent[position]);
        }

        cal_sun = Calendar.getInstance();
        cal_sun.set(Calendar.YEAR, curYear);
        cal_sun.set(Calendar.MONTH, curMonth);
        cal_sun.set(Calendar.DAY_OF_MONTH, day);

        int startDay = cal_sun.get(Calendar.DAY_OF_WEEK); //현재 달 1일의 요일 (1: 일요일, . . . 7: 토요일)

        if(startDay==1){ //일요일은 날짜 색 빨간색으로
            viewHolder.textView.setTextColor(Color.RED);
        }

        GridView gridView = (GridView) parent.findViewById(R.id.container_date);
        int divide = dayList.size() / 6;
        int height = gridView.getHeight() / divide;
        GridView.LayoutParams params = new GridView.LayoutParams( GridView.LayoutParams.MATCH_PARENT, height-2); //한칸의 크기 지정
        convertView.setLayoutParams(params);

        return convertView; //뷰 뿌려주기
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getCurYear(){
        return curYear;
    }

    public int getCurMonth(){
        return curMonth;
    }

    public EditText[] getEditTexts() { return editTexts; }
}