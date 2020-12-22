package com.swp.memorythm;

public class Folder {
    private String title;
    private int num;

    public Folder() {
    }

    public Folder(String title, int num) {
        this.title = title;
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public int getNum() {
        return num;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
