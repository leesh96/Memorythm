package com.swp.memorythm;

public class Folder {
    private String title;
    private int count;

    public Folder() {
    }

    public Folder(String title, int count) {
        this.title = title;
        this.count = count;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
