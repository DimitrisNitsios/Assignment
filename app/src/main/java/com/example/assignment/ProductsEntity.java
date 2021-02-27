package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;
import java.net.URL;

@Entity(tableName = "Products_Table")
public class ProductsEntity implements Serializable {


    @PrimaryKey
    @NonNull
    private String code;

    String productName;

    private char productGrade;

    private int novaGroup;

    private String ingredients;

    private String nutrients;

    private String image;

    // Constructor
    public ProductsEntity(String code, String productName, char productGrade, int novaGroup, String ingredients, String nutrients, String image) {
        this.code = code;
        this.productName = productName;
        this.productGrade = productGrade;
        this.novaGroup = novaGroup;
        this.ingredients = ingredients;
        this.nutrients = nutrients;
        this.image = image;
    }

    //Setters
    public void setProductCode(String productCode) {
        this.code = productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductGrade(char productGrade) {
        this.productGrade = productGrade;
    }

    public void setNova_group(int nova_group) {
        this.novaGroup = nova_group;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setNutrients(String nutrients) {
        this.nutrients = nutrients;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //Getters
    @NonNull
    public String getCode() {
        return code;
    }

    public String getProductName() {
        return productName;
    }

    public char getProductGrade() {
        return productGrade;
    }

    public int getNovaGroup() {
        return novaGroup;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getNutrients() {
        return nutrients;
    }

    public String getImage() {
        return image;
    }
}
