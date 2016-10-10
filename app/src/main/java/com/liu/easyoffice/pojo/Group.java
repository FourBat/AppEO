package com.liu.easyoffice.pojo;

/**
 * Created by hui on 2016/9/28.
 */

public class Group {
    private Long tgId;//组id
    private String tgName;//组name
    private Long parentTgId;//父组id
    private String description;//组描述
    private Long tcId;//公司id;
    public Long getTgId() {
        return tgId;
    }
    public void setTgId(Long tgId) {
        this.tgId = tgId;
    }
    public String getTgName() {
        return tgName;
    }
    public void setTgName(String tgName) {
        this.tgName = tgName;
    }
    public Long getParentTgId() {
        return parentTgId;
    }
    public void setParentTgId(Long parentTgId) {
        this.parentTgId = parentTgId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getTcId() {
        return tcId;
    }
    public void setTcId(Long tcId) {
        this.tcId = tcId;
    }

}
