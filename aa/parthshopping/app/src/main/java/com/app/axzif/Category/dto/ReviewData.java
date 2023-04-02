package com.app.axzif.Category.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewData
{
    public String id;
    public String rating;
    public String title;
    public String description;
    @SerializedName("customer_id")
    @Expose
    public CustomerId customer_id;
    public String created_date;
    @SerializedName("images")
    @Expose
    public ArrayList<String> images;

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public CustomerId getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(CustomerId customer_id) {
        this.customer_id = customer_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
