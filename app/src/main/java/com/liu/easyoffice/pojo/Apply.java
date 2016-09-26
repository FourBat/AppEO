package com.liu.easyoffice.pojo;

/**
 * Created by hui on 2016/9/19.
 */
public class Apply {
    private int userId;
    private int applyKind;
    private int applyStatus;
    private String applyTitle;
    private String applyContent;
    private String date;
    public Apply(){

    }
    public Apply(int userId, int applyKind, int applyStatus, String applyTitle,
                 String applyContent, String date) {
        super();
        this.userId = userId;
        this.applyKind = applyKind;
        this.applyStatus = applyStatus;
        this.applyTitle = applyTitle;
        this.applyContent = applyContent;
        this.date = date;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getApplyKind() {
        return applyKind;
    }
    public void setApplyKind(int applyKind) {
        this.applyKind = applyKind;
    }
    public int getApplyStatus() {
        return applyStatus;
    }
    public void setApplyStatus(int applyStatus) {
        this.applyStatus = applyStatus;
    }
    public String getApplyTitle() {
        return applyTitle;
    }
    public void setApplyTitle(String applyTitle) {
        this.applyTitle = applyTitle;
    }
    public String getApplyContent() {
        return applyContent;
    }
    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
