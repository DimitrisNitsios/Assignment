package com.example.assignment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RelationDao  {

    @Insert
    void insert(RelationEntity product);

    @Update
    void update(RelationEntity product);

    @Delete
    void delete(RelationEntity product);

    @Query("DELETE FROM RelationEntity_Table")
    void deleteAllProducts();

}
