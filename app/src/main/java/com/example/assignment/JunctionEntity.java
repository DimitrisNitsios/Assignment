package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "JunctionEntity_Table", primaryKeys = {"listId","code" })
public class JunctionEntity implements Serializable {

    @NonNull
    private int listId;

    @NonNull
    private String code;

    public JunctionEntity(int listId, @NonNull String code) {
        this.listId = listId;
        this.code = code;
    }

    // getters
    public int getListId() {
        return listId;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    //setters
    public void setCode(@NonNull String code) {
        this.code = code;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }
}