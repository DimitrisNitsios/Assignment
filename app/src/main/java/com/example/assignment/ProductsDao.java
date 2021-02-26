package com.example.assignment;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductsDao {

    @Insert
    void insert(ProductsEntity product);

    @Update
    void update(ProductsEntity product);

    @Delete
    void delete(ProductsEntity product);

    @Query("DELETE FROM Products_Table")
    void deleteAllProducts();

    // any changes that happened to the table values will automatically be updated and activity will be notified
    @Query("SELECT * FROM Products_Table ORDER BY productCode")
    LiveData<List<ProductsEntity>> getAllProducts();

}
