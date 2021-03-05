package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@Entity(tableName = "Products_Table")
public class ProductsEntity implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("code") private String code;

    @SerializedName("product_name") String productName;

    @SerializedName("nutrition_grades") private char productGrade;

    @SerializedName("quantity") private String weight;

    @SerializedName("nutriments") private Nutriments nutriments;

    @SerializedName("image_url") private String image;

    // Constructor
    public ProductsEntity(String code, String productName, char productGrade, int calories, String weight, String image) {
        this.code = code;
        this.productName = productName;
        this.productGrade = productGrade;
        this.nutriments = new Nutriments();
        this.nutriments.setCalories(calories);
        this.image = image;
        this.weight = weight;
    }

    public ProductsEntity() {
        this.code = "code";
        this.productName = "productName";
        this.productGrade = 'X';
        this.weight = "100";
        this.nutriments = new Nutriments();
        this.image = "image";
    }

    //Setters

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductGrade(char productGrade) {
        this.productGrade = productGrade;
    }

    public void setNutriments(Nutriments nutriments) {
        this.nutriments = nutriments;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCode(@NonNull String code) { this.code = code;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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

    public Nutriments getNutriments() {
        return nutriments;
    }

    public String getImage() {
        return image;
    }

    public String getWeight() {
        return weight;
    }
}
