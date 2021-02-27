package com.example.assignment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Arrays;

public class InputProductsActivity extends AppCompatActivity {

    private final String TAG ="DEBUGGING";
    private String URL = "https://world.openpetfoodfacts.org/api/v0/product";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_products);
    }

    public void go(View view) {
        URL = URL + "/" + getCodeText() +".json";

        // Asynchronous no need to run it in a separate thread with an Executor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final StringRequest request = new StringRequest(
                Request.Method.GET,
                URL,
                this::handleResponse,
                this::handleError );
        requestQueue.add(request);
    }

    private String getCodeText() {
        String codeText;
        // Get the edit text object and copy the text
        EditText code = findViewById(R.id.code_entry);
        codeText = String.valueOf(code.getText()).trim();
        // Remove the text from the Edit text and return the copped text
        code.getText().clear();
        Log.d(TAG, "SUCCESS code is: " + codeText);
        return codeText;
    }

    private void handleError(VolleyError error) {
        Log.d(TAG, "ERROR");
    }

    private void handleResponse(String response) {
        Log.d(TAG, "SUCCESS code is: " + response);
        ApiResponse apiResponse = new Gson().fromJson(response, ApiResponse.class);
        Log.d(TAG, "SUCCESS code is: " + apiResponse.getProduct());
        Log.d(TAG, "SUCCESS code is: " + apiResponse.getStatus_verbose());
        Log.d(TAG, "SUCCESS code is: " + apiResponse.getStatus());
        Log.d(TAG, "SUCCESS code is: " + apiResponse.getCode());
    }
}
