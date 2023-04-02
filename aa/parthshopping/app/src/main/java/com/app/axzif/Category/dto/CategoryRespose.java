
package com.app.axzif.Category.dto;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryRespose {
    @SerializedName("Data")
    @Expose
    private ArrayList<Category> Data;
    @SerializedName("offer")
    @Expose
    private ArrayList<Offer> offer = null;
    public ArrayList<Category> getData() {
        return Data;
    }
    public void setData(ArrayList<Category> data) {
        Data = data;
    }
    public ArrayList<Offer> getOffer() {
        return offer;
    }
    public void setOffer(ArrayList<Offer> offer) {
        this.offer = offer;
    }


}
