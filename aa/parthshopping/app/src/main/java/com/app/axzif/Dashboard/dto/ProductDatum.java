package com.app.axzif.Dashboard.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDatum {
    public String id;
    public String product_name;
    public String product_price;
    public String discount;
    public String main_stock;
    public String avl_stock;
    @SerializedName("product_img")
    @Expose
    public String product_img;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMain_stock() {
        return main_stock;
    }

    public void setMain_stock(String main_stock) {
        this.main_stock = main_stock;
    }

    public String getAvl_stock() {
        return avl_stock;
    }

    public void setAvl_stock(String avl_stock) {
        this.avl_stock = avl_stock;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

}
