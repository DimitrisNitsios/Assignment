package com.example.assignment;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {ListsEntity.class, ProductsEntity.class}, version = 1)
public abstract class myDatabase  extends RoomDatabase {



    private static volatile myDatabase INSTANCE;

    public abstract ListsDao listsDao();

    public abstract ProductsDao productsDao();

    // public abstract RelationDao relationDao();

    // Only on thread at the time may access this method
    public static synchronized  myDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    myDatabase.class, "my_database" )
                    .build();
        }
        return INSTANCE;
    }


}