package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    public static int SIZE_OF_DATA = 20;

    public static String TAG = "TAG";
    private final Vector<Double> data = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create a new instance of the recyclerview adaptor
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        // initiate data
        for(int i = 0; i < SIZE_OF_DATA; i++) {
            this.data.add(i+0d);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        Adapter adaptor = new Adapter();
        // Link RecyclerViw to the adaptor
        runOnUiThread(()->{recyclerView.setAdapter(adaptor);});

        final List<ProductsEntity>  productsEntities =  myDatabase
                .getINSTANCE(this)
                .productsDao()
                .getAllProducts();

        adaptor.setProductsEntities(productsEntities);


        Toolbar tool = findViewById(R.id.toolbar);
        tool.setNavigationOnClickListener(this::goToProductDescriptionActivity);
    }


     // function that navigates to the Input products activity
    public void goToInputProductsActivity (View view) {
        Intent intent = new Intent(this, InputProductsActivity.class);
        startActivity(intent);
    }

    // function that navigates to the Product description activity
    public void goToProductDescriptionActivity (View view) {
        Intent intent = new Intent(this, ProductDescriptionActivity.class);
        startActivity(intent);
    }


    public void goToEditListsActivity(MenuItem item) {
        Intent intent = new Intent(this, EditListsActivity.class);
        startActivity(intent);
    }
}