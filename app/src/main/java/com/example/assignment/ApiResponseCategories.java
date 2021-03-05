package com.example.assignment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiResponseCategories {
    private final int count;
    private final int page;
    @SerializedName("products")
    private final List<ProductsEntity> productsEntities;
    private final int page_size;
    private final String page_count;


    public ApiResponseCategories(int count, int page, List<ProductsEntity> productsEntities, int page_size, String page_count) {
        this.count = count;
        this.page = page;
        this.productsEntities = productsEntities;
        this.page_size = page_size;
        this.page_count = page_count;
    }

    public int getCount() {
        return count;
    }

    public int getPage() {
        return page;
    }

    public List<ProductsEntity> getProductsEntities() {
        return productsEntities;
    }

    public int getPage_size() {
        return page_size;
    }

    public String getPage_count() {
        return page_count;
    }
}
