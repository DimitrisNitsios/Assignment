package com.example.assignment;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;

import static com.example.assignment.MainActivity.TAG;


@Database(entities = {ListsEntity.class, ProductsEntity.class, JunctionEntity.class}, version = 3, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MyDatabase extends RoomDatabase {
    private Activity contextA;

    private static volatile MyDatabase INSTANCE;

    public abstract ListsDao listsDao();

    public abstract ProductsDao productsDao();


    public abstract ListWithProductsDao listWithProductsDao();

    // Only on thread at the time may access this method
    public static synchronized MyDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            // Initialize database (if Empty)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MyDatabase.class, "my_database")
                    .build();
        }
        return INSTANCE;
    }

}
