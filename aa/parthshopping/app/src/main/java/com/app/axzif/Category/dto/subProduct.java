package com.app.axzif.Category.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class subProduct {
    private String id;
    private String warranty;
    private String cod;
    @SerializedName("return_policy")
    @Expose
    private String returnPolicy;

    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    private String discount;
    @SerializedName("product_size")
    @Expose
    private String productSize;
    private String highlights;
    @SerializedName("product_description")
    @Expose
    private String productDescription;
    @SerializedName("sort_description")
    @Expose
    private String sortDescription;
    @SerializedName("additional_info")
    @Expose
    private String additionalInfo;
    @SerializedName("main_stock")
    @Expose
    private String mainStock;
    @SerializedName("avl_stock")
    @Expose
    private String avlStock;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getReturnPolicy() {
        return returnPolicy;
    }

    public void setReturnPolicy(String returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getSortDescription() {
        return sortDescription;
    }

    public void setSortDescription(String sortDescription) {
        this.sortDescription = sortDescription;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getMainStock() {
        return mainStock;
    }

    public void setMainStock(String mainStock) {
        this.mainStock = mainStock;
    }

    public String getAvlStock() {
        return avlStock;
    }

    public void setAvlStock(String avlStock) {
        this.avlStock = avlStock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getUnitFullName() {
        return unitFullName;
    }

    public void setUnitFullName(String unitFullName) {
        this.unitFullName = unitFullName;
    }

    public String getUnitSortName() {
        return unitSortName;
    }

    public void setUnitSortName(String unitSortName) {
        this.unitSortName = unitSortName;
    }

    @SerializedName("seller_name")
    @Expose
    private String sellerName;
    @SerializedName("color_name")
    @Expose
    private String colorName;
    @SerializedName("unit_full_name")
    @Expose
    private String unitFullName;
    @SerializedName("unit_sort_name")
    @Expose
    private String unitSortName;
}
