package com.swp.memorythm.template;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.viewpager2.widget.ViewPager2;

//EditText 밑줄 그어주는 클래스
public class LinedEditText extends AppCompatEditText {
    private Paint mPaint = new Paint();
    private int inc_count = 0; //count 값 증가해주기 위한 전역 변수

    public LinedEditText(Context context) {
        super(context);
        initPaint();
    }

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public LinedEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    private void initPaint() {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0x80000000);
    }

    @Override protected void onDraw(Canvas canvas) {
        int right = getRight();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int lineHeight = getLineHeight();
        int height = getHeight();
        float textSize = getTextSize();
        int count = (height-paddingTop-paddingBottom) / lineHeight + inc_count;

        for (int i = 0; i < count; i++) {
            float baseline = lineHeight * (i+1) + paddingTop - textSize / 2;
            canvas.drawLine(0, baseline, right-paddingRight, baseline, mPaint);
        }

        super.onDraw(canvas);
    }

    //스크롤 변할때마다 밑줄 추가해줌
    @Override
    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);

        inc_count++;
        invalidate(); //onDraw 다시 호출하는 함수
    }
}