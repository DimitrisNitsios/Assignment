package com.example.assignment;

import com.google.gson.annotations.SerializedName;

public class Nutriments {
    @SerializedName("energy-kcal")
    private float calories;

    public Nutriments() {
        this.calories = 0;
    }
    public Nutriments(float calories) {
        this.calories = calories;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }
}
