package com.swp.memorythm.template.data;

public class TodoData {
    private boolean isDone;
    private String content;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
