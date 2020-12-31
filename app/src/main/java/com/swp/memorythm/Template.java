package com.swp.memorythm;

public class Template {
    private String title, template;
    private int count;

    public Template(String title, String template, int count) {
        this.title = title;
        this.template = template;
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

    public String getTemplate() { return template; }

    public void setTemplate(String template) { this.template = template; }
}
