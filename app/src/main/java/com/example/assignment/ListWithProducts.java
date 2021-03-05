package com.example.assignment;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ListWithProducts {
    @Embedded
    public ListsEntity listsEntity;
    @Relation(
            parentColumn = "listId",
            entity = ProductsEntity.class,
            entityColumn = "code",
            associateBy = @Junction(
                    value = JunctionEntity.class,
                    parentColumn = "listId",
                    entityColumn = "code"
            )
    )
    public List<ProductsEntity> productsEntities;
}
