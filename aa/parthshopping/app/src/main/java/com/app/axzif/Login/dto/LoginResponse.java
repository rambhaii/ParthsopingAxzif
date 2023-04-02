package com.app.axzif.Login.dto;

import com.app.axzif.Category.dto.ReviewData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponse {


    @SerializedName("Data")
    @Expose
    private DataLogin data;
    @SerializedName("ReviewData")
    @Expose
    private ArrayList<ReviewData> reviewData;

    public ArrayList<ReviewData> getReviewData() {
        return reviewData;
    }

    public void setReviewData(ArrayList<ReviewData> reviewData) {
        this.reviewData = reviewData;
    }

    public DataLogin getData() {
        return data;
    }

    public void setData(DataLogin data) {
        this.data = data;
    }
}
