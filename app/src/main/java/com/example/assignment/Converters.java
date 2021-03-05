package com.example.assignment;

import androidx.room.TypeConverter;

// Created to tell the database how to read the data
public class Converters {

    @TypeConverter
    public float intFromNutriments(Nutriments nutriments) {
        return nutriments.getCalories();
    }

    @TypeConverter
    public Nutriments intToNutriments(float calories) {
        return new Nutriments(calories);
    }

}
