package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

public class IndividualListsActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_list);
        id = getIntent().getExtras().getInt("listId");
        Log.d(TAG, "onCreate: " + id);

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
                .listWithProductsDao()
                .getListWithProducts(id)
                .productsEntities;

        // create a new instance of the recyclerview adaptor
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        Adapter3 adaptor = new Adapter3();
        // Link RecyclerViw to the adaptor
        runOnUiThread(()->{recyclerView.setAdapter(adaptor);});
        adaptor.setProductsEntities(productsEntities);
    }
}
