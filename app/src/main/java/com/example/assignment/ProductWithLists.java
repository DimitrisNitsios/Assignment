package com.example.assignment;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ProductWithLists {

    @Embedded
    public ProductsEntity productsEntity;
    @Relation(
            parentColumn = "code",
            entity = ListsEntity.class,
            entityColumn = "listId",
            associateBy = @Junction(
                    value = JunctionEntity.class,
                    parentColumn = "code",
                    entityColumn = "listId"
            )
    )
    public List<ListsEntity> listsEntities;
}
