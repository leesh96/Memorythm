package com.swp.memorythm;

import android.os.Parcel;
import android.os.Parcelable;

public class MemoData implements Parcelable {
    private String memoTitle;
    private String memoDate;
    private String template;
    private int memoid;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public int getMemoid() {
        return memoid;
    }

    public void setMemoid(int memoid) {
        this.memoid = memoid;
    }

    public MemoData() {
    }

    public MemoData(String trashTitle, String trashDate) {
        this.memoTitle = trashTitle;
        this.memoDate = trashDate;
    }

    protected MemoData(Parcel in) {
        memoTitle = in.readString();
        memoDate = in.readString();
    }

    public static final Creator<MemoData> CREATOR = new Creator<MemoData>() {
        @Override
        public MemoData createFromParcel(Parcel in) {
            return new MemoData(in);
        }

        @Override
        public MemoData[] newArray(int size) {
            return new MemoData[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(memoTitle);
        parcel.writeString(memoDate);
    }
}
