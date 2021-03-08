package com.example.assignment;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;
@Dao
public interface ListWithProductsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addProductToList(JunctionEntity junctionEntity);

    @Transaction
    @Query("SELECT * FROM Lists_Table WHERE listId=:id")
    ListWithProducts getListWithProducts(int id);

    @Transaction
    @Query("SELECT * FROM Lists_Table")
    List<ListWithProducts> getListsWithProducts();
}
