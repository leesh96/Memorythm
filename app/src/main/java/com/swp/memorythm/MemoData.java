package com.swp.memorythm;

public class MemoData {
    private String memoTitle;
    private String memoDate;

    public MemoData(String trashTitle, String trashDate) {
        this.memoTitle = trashTitle;
        this.memoDate = trashDate;
    }

    public String getMemoTitle() {
        return memoTitle;
    }

    public void setMemoTitle(String memoTitle) {
        this.memoTitle = memoTitle;
    }

    public String getMemoDate() {
        return memoDate;
    }

    public void setMemoDate(String memoDate) {
        this.memoDate = memoDate;
    }
}
