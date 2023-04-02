package com.app.axzif.Category.dto;

public class SubTypeData {
    private String id;
    private String category_id;
    private String subcategory_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getSubcategory_id() {
        return subcategory_id;
    }

    public void setSubcategory_id(String subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    public String getSubcategory_type() {
        return subcategory_type;
    }

    public void setSubcategory_type(String subcategory_type) {
        this.subcategory_type = subcategory_type;
    }

    private String subcategory_type;

}
