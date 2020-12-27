package com.swp.memorythm;

public class Folder {
    private String title;
    private String num;

    public Folder() {
    }

    public Folder(String title, String num) {
        this.title = title;
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public String getNum() {
        return num;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
