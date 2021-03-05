package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import java.util.List;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity  {

    public static String TAG = "TAG";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private Toolbar tool;
    private MaterialAlertDialogBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //reveal or hide the text message depending on connection
        if(isOnline()){
            findViewById(R.id.view_connection).setVisibility(View.INVISIBLE); // Online
        }else{
            findViewById(R.id.view_connection).setVisibility(View.VISIBLE); // Disconnected
        }

        builder = new MaterialAlertDialogBuilder(this);
        // set the clicklistener on the navigation button to delete the whole product database
        tool = findViewById(R.id.toolbar);
        tool.setNavigationOnClickListener((view)->{
            Log.d(TAG, "onResume: ");

                builder.setTitle("Warning").setMessage("Do you want to delete all the products in the Database?");
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do Nothing closes the dialogue
                            }
                        });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Executors.newSingleThreadExecutor().execute(()->{deleteDatabase();});
                    }
                });
                builder.show();

        });

        // check to see if there is a list if not create a new list and fill the database with 50 snack products
        Executors.newSingleThreadExecutor().execute(()->{

            List<ListsEntity> listsEntities = MyDatabase.getINSTANCE(this).listsDao().getAllLists();
            Log.d(TAG, "onResume: " + listsEntities.size());
            if (listsEntities.size() == 0 ) {

                Executors.newSingleThreadExecutor().execute(this::getApiCategory);
            } else {
                // update the recycler view every time on resume
                Executors.newSingleThreadExecutor().execute(this::updateDB);
            }
        });

    }

    // Returns whether or not the device is connected to the internet
    public boolean isOnline() {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return connected;
    }

    // remove all records from the Products table
    private void deleteDatabase() {
        MyDatabase.getINSTANCE(this).productsDao().deleteAllProducts();
        Executors.newSingleThreadExecutor().execute(this::updateDB);
        runOnUiThread(()->{
            Toast.makeText(this, "All Products deleted" , Toast.LENGTH_LONG).show();
        });
    }

    // method that updates the Database and then the RecyclerView
    private void updateDB() {
        final List<ProductsEntity>  productsEntities =  MyDatabase
                .getINSTANCE(this)
                .productsDao()
                .getAllProducts();

        // create a new instance of the recyclerview adaptor
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        Adapter adaptor = new Adapter();
        // Link RecyclerViw to the adaptor
        runOnUiThread(()->{recyclerView.setAdapter(adaptor);});
        adaptor.setProductsEntities(productsEntities);

        // Hide the progress bar
        progressBar.setVisibility(View.INVISIBLE);
    }

    // method that navigates to the Input products activity
    public void goToInputProductsActivity (View view) {
        Intent intent = new Intent(this, InputProductsActivity.class);
        startActivity(intent);
    }


    // method that navigates to the edit lists activity
    public void goToEditListsActivity(MenuItem item) {
        Intent intent = new Intent(this, EditListsActivity.class);
        startActivity(intent);
    }

    // Get the response from the categories
    private void getApiCategory(){
        String  URL = "https://world.openfoodfacts.org/category/snacks.json?page_size=50";
        // Asynchronous no need to run it in a separate thread with an Executor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final StringRequest request = new StringRequest(
                Request.Method.GET,
                URL,
                this::handleResponse,
                this::handleError );
        requestQueue.add(request);
    }

    // Get 50 products from the response
    private void handleResponse(String response)  {
        ApiResponseCategories apiResponse = new Gson().fromJson(response, ApiResponseCategories.class);

        // if it returns 50 products
        if (apiResponse.getPage_count().equals("50")) {
            List<ProductsEntity> productsEntities  = apiResponse.getProductsEntities();
            // for each product entity in the list insert it in the database
            for (ProductsEntity productsEntity: productsEntities) {
                Executors.newSingleThreadExecutor().execute(()->{
                    MyDatabase.getINSTANCE(this).productsDao().insert(productsEntity);});

            }
            ListsEntity entity = new ListsEntity("My Fist List", "My First List", "white");
            Executors.newSingleThreadExecutor().execute(()->{MyDatabase.getINSTANCE(this).listsDao().insert(entity);});
        }
        // Update the recycler view based on the new updated database
        Executors.newSingleThreadExecutor().execute(this::updateDB);
    }

    // When there is no internet or other errors with retrieving the response
    private void handleError(VolleyError error) {
        Toast.makeText(this, "ERROR : There is no access to the internet" , Toast.LENGTH_LONG).show();
        // even when there is no internet the recycler needs to display the offline data
        Executors.newSingleThreadExecutor().execute(this::updateDB);
    }



}