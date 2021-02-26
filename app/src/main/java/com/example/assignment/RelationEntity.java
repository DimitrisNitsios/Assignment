package com.example.assignment;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "RelationEntity_Table", primaryKeys = {"productCode", "listId"})
public class RelationEntity implements Serializable {

    private String productCode;

    private int listId;


}