
package com.app.axzif.Category.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Category {


    String brand_name;
    String color_name;
    String count;

    public String getCount()
    {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ArrayList<subProduct> getSubProducts() {
        return subProducts;
    }

    public void setSubProducts(ArrayList<subProduct> subProducts) {
        this.subProducts = subProducts;
    }
    @SerializedName("subProduct")
    @Expose
    private ArrayList<subProduct> subProducts;

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getColor_name() {
        return color_name;
    }

    public void setColor_name(String color_name) {
        this.color_name = color_name;
    }

    @SerializedName("customer_id")
    @Expose
    private String customerId;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public   boolean isSelected;


    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("alt_mobile")
    @Expose
    private String altMobile;
    @SerializedName("additional_info")
    @Expose
    private String additionalInfo;
    @SerializedName("address_type")
    @Expose
    private String addressType;

    public ArrayList<SubTypeData> getSubTypeData() {
        return subTypeData;
    }

    public void setSubTypeData(ArrayList<SubTypeData> subTypeData) {
        this.subTypeData = subTypeData;
    }

    @SerializedName("subcategorytype")
    @Expose
    private ArrayList<SubTypeData> subTypeData;


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAltMobile() {
        return altMobile;
    }

    public void setAltMobile(String altMobile) {
        this.altMobile = altMobile;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    @SerializedName("product_img")
    @Expose
    private String product_img;

    @SerializedName("quantity")
    @Expose
    private String quantity;

 @SerializedName("product_quantity")
    @Expose
    private String product_quantity;

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    @SerializedName("product_name")
    @Expose
    private String productName;
   @SerializedName("order_date")
    @Expose
    private String order_date;

   String order_status;

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    @SerializedName("order_number")
    @Expose
    private String order_number;

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    @SerializedName("product_price")
    @Expose
    private double productPrice;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("main_stock")
    @Expose
    private String mainStock;
    @SerializedName("avl_stock")
    @Expose
    private String avlStock;




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

    @SerializedName("banner_img")
@Expose
private String banner_img;


    public String getBanner_img() {
        return banner_img;
    }

    public void setBanner_img(String banner_img) {
        this.banner_img = banner_img;
    }

    @SerializedName("subcategory_type")
@Expose
private String subcategory_type;




    @SerializedName("subcategory_id")
@Expose
private String subcategory_id;

    public String getSubcategory_type() {
        return subcategory_type;
    }

    public void setSubcategory_type(String subcategory_type) {
        this.subcategory_type = subcategory_type;
    }

    public String getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(String subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    @SerializedName("subcategory_name")
@Expose
private String subcategory_name;


    @SerializedName("category_id")
@Expose
private String category_id;



    @SerializedName("id")
@Expose
private String id;



    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("icon_img")
    @Expose
    private String iconImg;
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("modify_date")
    @Expose
    private String modifyDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }


    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }
}
