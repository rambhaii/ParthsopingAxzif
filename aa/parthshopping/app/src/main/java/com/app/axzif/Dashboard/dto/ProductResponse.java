package com.app.axzif.Dashboard.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductResponse {
    public int status;
    public String message;
    public String limit;
    public int prpage;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public int getPrpage() {
        return prpage;
    }

    public void setPrpage(int prpage) {
        this.prpage = prpage;
    }

    public ArrayList<ProductDatum> getData() {
        return Data;
    }

    public void setData(ArrayList<ProductDatum> data) {
        Data = data;
    }
    @SerializedName("Data")
    @Expose
    public ArrayList<ProductDatum> Data;
}
