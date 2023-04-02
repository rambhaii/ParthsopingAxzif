package com.app.axzif.Login.dto;

import com.app.axzif.Category.dto.ReviewData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import com.app.axzif.Category.dto.subProduct;

public class DataLogin {


    @SerializedName("id")
    @Expose
    private String id;
     @SerializedName("cnt")
    @Expose
    private String cnt;
    public ArrayList<subProduct> getSubProducts() {
        return subProducts;
    }

    public void setSubProducts(ArrayList<subProduct> subProducts) {
        this.subProducts = subProducts;
    }
    @SerializedName("subProduct")
    @Expose
    private ArrayList<subProduct> subProducts;

    @SerializedName("warranty")
    @Expose
    private String warranty;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    @SerializedName("order_id")
    @Expose
    private String order_id;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("return_policy")
    @Expose
    private String returnPolicy;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_price")
    @Expose
    private double productPrice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("product_size")
    @Expose
    private String productSize;
    @SerializedName("highlights")
    @Expose
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
    private String profile;
    private String company_name;
    private String retingAvg;

    public String getRetingAvg() {
        return retingAvg;
    }

    public void setRetingAvg(String retingAvg) {
        this.retingAvg = retingAvg;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

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

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
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





    @SerializedName("product_img")
    @Expose
    private String product_img;

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("X-API-KEY")
    @Expose
    private String xAPIKEY;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getXAPIKEY() {
        return xAPIKEY;
    }

    public void setXAPIKEY(String xAPIKEY) {
        this.xAPIKEY = xAPIKEY;
    }


    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("message")
    @Expose
    private String message;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
