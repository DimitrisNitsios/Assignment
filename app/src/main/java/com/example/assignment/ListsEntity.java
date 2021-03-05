package com.example.assignment;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Lists_Table")
public class ListsEntity implements Serializable {

    //Define the attributes (columns) of the Table

    // automatically increment the value of listId
    @PrimaryKey(autoGenerate = true)
    private int listId;

    private String color;

    private String listName;

    private String description;


    // constructor that the room uses to create
    public ListsEntity(String listName, String description, String color) {
        this.listName = listName;
        this.description = description;
        this.color = color;
    }


    //Setters
    public void setListId(int listId) {
        this.listId = listId;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColor(String color) { this.color = color; }

    //Getters
    public int getListId() {
        return listId;
    }

    public String getListName() {
        return listName;
    }

    public String getDescription() {
        return description;
    }

    public String getColor() { return color; }


}
