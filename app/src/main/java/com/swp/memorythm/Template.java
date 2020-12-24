package com.swp.memorythm;

public class Template {
    private String title;
    private int num;

    public Template() {
    }

    public Template(String title, int num) {
        this.title = title;
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
