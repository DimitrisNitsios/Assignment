package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;


public class InputProductsActivity extends AppCompatActivity {

    private final String TAG ="DEBUGGING";
    private String URL = "https://world.openfoodfacts.org/api/v0/product/";
    private EditText inputCode;
    private TextView displayProduct;
    private ImageView img;
    private URL imageURL;
    private Bitmap bmp;
    private ProductsEntity productsEntity;
    private Button saveButton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_products);
    }

    @Override
    protected void onResume() {
        super.onResume();
        inputCode = findViewById(R.id.code_entry);
        displayProduct = findViewById(R.id.display_product);
        img = findViewById(R.id.view_product);
        saveButton = findViewById(R.id.save_Product);
    }

    // method to call the saveToDB method
    public void saveProduct(View view) {
        Executors.newSingleThreadExecutor().execute(this::saveToDB);
    }

    //method to save the product in the database and close the activity
    private void saveToDB(){
        MyDatabase.getINSTANCE(this).productsDao().insert(productsEntity);
        runOnUiThread(()->{Toast.makeText(this, "Product added to the Database" , Toast.LENGTH_LONG).show();});
        displayProduct.setText("");
        this.finish();
    }

    // method called by clicking the button to call the getAPI
    public void goGetAPI(View view) {
        // Close the soft keyboard first
        closeKeyboard();
        //Call the get api function to get the response form the server
        getProductAPI();
    }

    //method that is called when we want to get an api response by calling the getCodeText to retrieve the code imputed
    private void getProductAPI(){
        String  finalURL = URL + getCodeText() +".json";
        // Asynchronous no need to run it in a separate thread with an Executor
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final StringRequest request = new StringRequest(
                Request.Method.GET,
                finalURL,
                this::handleResponse,
                this::handleError );
        requestQueue.add(request);
    }

    // Gets the code imputed in the input text and return it after it is cleared
    private String getCodeText() {
        String codeText;
        // Get the edit text object and copy the text
        codeText = String.valueOf(inputCode.getText()).trim();

       // Log.d(TAG, "SUCCESS code is: " + codeText);
        return codeText;
    }


    // When there is no internet or other errors with retrieving the response
    private void handleError(VolleyError error) {
        Toast.makeText(this, "ERROR : There is no access to the internet" , Toast.LENGTH_LONG).show();
        Log.d(TAG, "ERROR " + error);

    }

    private void handleResponse(String response)  {
        Log.d(TAG, "code is: " + response);

        ApiResponse apiResponse = new Gson().fromJson(response, ApiResponse.class);
        if (apiResponse.getStatus() == 1) {
            displayProduct.setText("Name: " + apiResponse.getProduct().getProductName() + "\n"
                    + "Grade: " + apiResponse.getProduct().getProductGrade() + "\n"
                    +  "Calories: " + apiResponse.getProduct().getNutriments().getCalories() + "\n"
                    + " Weight " + apiResponse.getProduct().getWeight());

            // make the string image information to url
            Executors.newSingleThreadExecutor().execute( ()-> {
                // make the string image information to url
                try {
                    imageURL = new URL(apiResponse.getProduct().getImage());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                // Use the url to Bitmap to be able to be viewed on the imageView
                try {
                    bmp = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                    runOnUiThread(()->{img.setImageBitmap(bmp);});
                } catch (IOException ex) {
                    Log.d("Error", ex.getMessage());
                    ex.printStackTrace();
                    bmp = null;
                }
                productsEntity = apiResponse.getProduct();
                runOnUiThread(()->{saveButton.setEnabled(true);});
            });
        }
        if (apiResponse.getStatus() == 0){
            displayProduct.setText("No product found with code: " + apiResponse.getCode() + ". Please input a new code or scan a new barcode");
            saveButton.setEnabled(false);
        }

    }

    public void goScan(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        // in order to have the camera run in portrait mode i needed to run it in activity that in the manifest is set on portrait
        integrator.setCaptureActivity(Portrait.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning the code");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        final IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                runOnUiThread(()->{Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();});
            } else {
                final String scannedText = result.getContents();
                // put the scanned text on the input text and the call the api to get the response
                inputCode.setText(scannedText);
                getProductAPI();
                // Remove the text from the Edit text
                inputCode.getText().clear();
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
