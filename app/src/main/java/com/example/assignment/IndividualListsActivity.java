package com.example.assignment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.Executors;

public class IndividualListsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_list);

    }

    @Override
    protected void onResume() {
        super.onResume();


        // update the recycler view every time on resume
        Executors.newSingleThreadExecutor().execute(this::updateDB);
    }

    private void updateDB() {
        final List<ProductsEntity> productsEntities =  MyDatabase
                .getINSTANCE(this)
                .productsDao()
                .getAllProducts();
    }
}
