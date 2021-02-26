package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigInteger;
import java.net.URL;

@Entity(tableName = "Products_Table")
public class ProductsEntity implements Serializable {


    @PrimaryKey
    @NonNull
    private String productCode;

    private String productName;

    private char productGrade;

    private int nova_group;

    private String ingredients;

    private String nutrients;

    private String image;

    // Constructor
    public ProductsEntity(String productCode, String productName, char productGrade, int nova_group, String ingredients, String nutrients, String image) {
        this.productCode = productCode;
        this.productName = productName;
        this.productGrade = productGrade;
        this.nova_group = nova_group;
        this.ingredients = ingredients;
        this.nutrients = nutrients;
        this.image = image;
    }

    //Setters
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductGrade(char productGrade) {
        this.productGrade = productGrade;
    }

    public void setNova_group(int nova_group) {
        this.nova_group = nova_group;
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
    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public char getProductGrade() {
        return productGrade;
    }

    public int getNova_group() {
        return nova_group;
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
