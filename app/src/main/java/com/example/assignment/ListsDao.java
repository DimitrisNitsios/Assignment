package com.example.assignment;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface ListsDao {

    @Insert
    void insert(ListsEntity list);

    @Update
    void update(ListsEntity list);

    @Delete
    void delete(ListsEntity list);

    @Query("DELETE FROM Lists_Table")
    void deleteAllLists();

    // any changes that happened to the table values will automatically be updated and activity will be notified
    @Query("SELECT * FROM Lists_Table ORDER BY listId")
    List<ListsEntity> getAllLists();

}
