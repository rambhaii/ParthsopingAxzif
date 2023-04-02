package com.app.axzif.Dashboard.dto;

public class MyLeadData {


    private String description;
    private String detail;
    private String off;
    private String TIME;
    private String ordered;
    private String slocation;
    private String plocation;
    private int imgId;


    public MyLeadData(String description, int imgId, String off, String detail, String TIME, String ordered, String plocation, String slocation) {
        this.description = description;

        this.off = off;
        this.detail = detail;
        this.imgId = imgId;
        this.TIME = TIME;
        this.ordered = ordered;
        this.plocation = plocation;
        this.slocation = slocation;
    }


    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getOrdered() {
        return ordered;
    }

    public void setOrdered(String ordered) {
        this.ordered = ordered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }

    public String getSlocation() {
        return slocation;
    }

    public void setSlocation(String slocation) {
        this.slocation = slocation;
    }

    public String getPlocation() {
        return plocation;
    }

    public void setPlocation(String plocation) {
        this.plocation = plocation;
    }
}
