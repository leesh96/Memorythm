package com.swp.memorythm;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

public final class CommonUtils {
    @SuppressLint("ClickableViewAccessibility")
    public static void setTouchable(View view) {
        view.setOnTouchListener((view1, motionEvent) -> true);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                setTouchable(group.getChildAt(i));
            }
        }
    }

    // 고정프래그먼트에서 뷰 이벤트 막는 함수
    public static void invaildTouch(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                invaildTouch((ViewGroup) child);
            } else {
                child.setOnTouchListener(null);
            }
        }
    }

    public static String makeKey(String txt) {
        StringBuilder key = new StringBuilder();
        Random random = new Random();
        do {
            for (int i = 0; i < 8; i++) {
                int rIndex = random.nextInt(3);
                switch (rIndex) {
                    case 0:
                        // a-z
                        key.append((char) ((random.nextInt(26)) + 97));
                        break;
                    case 1:
                        // A-Z
                        key.append((char) ((random.nextInt(26)) + 65));
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
}
