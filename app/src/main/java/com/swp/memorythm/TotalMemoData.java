package com.swp.memorythm;

class TotalMemoData {
    int totalID;
    String totalTitle;
    String totalDate;
    String template;

    public TotalMemoData() {
    }

    public TotalMemoData(int totalID, String totalTitle, String totalDate, String template) {
        this.totalID = totalID;
        this.totalTitle = totalTitle;
        this.totalDate = totalDate;
        this.template = template;
    }

    public int getTotalID() {
        return totalID;
    }

    public void setTotalID(int totalID) {
        this.totalID = totalID;
    }

    public String getTotalTitle() {
        return totalTitle;
    }

    public void setTotalTitle(String totalTitle) {
        this.totalTitle = totalTitle;
    }

    public String getTotalDate() {
        return totalDate;
    }

    public void setTotalDate(String totalDate) {
        this.totalDate = totalDate;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}