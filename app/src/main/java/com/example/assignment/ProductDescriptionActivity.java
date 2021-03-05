package com.example.assignment;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ProductDescriptionActivity extends AppCompatActivity {
    private MaterialAlertDialogBuilder builder;
    private Button saveButton;
    private String code;
    private TextView name;
    private TextView grade;
    private TextView weight;
    private TextView calories;
    private TextView viewCode;
    private ImageView image;
    private URL imageURL;
    private Bitmap bmp;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_description);
        code = getIntent().getStringExtra("code");
    }

    @Override
    protected void onResume() {
        super.onResume();
        saveButton = findViewById(R.id.save_to_list);
        Executors.newSingleThreadExecutor().execute(this::loadFromDB);
        name = findViewById(R.id.product_name);
        weight = findViewById(R.id.product_weight);
        calories = findViewById(R.id.product_calories);
        image = findViewById(R.id.product_image);
        viewCode = findViewById(R.id.product_code);
        grade = findViewById(R.id.product_grade);

        builder = new MaterialAlertDialogBuilder(this);

        saveButton.setOnClickListener(v -> Executors.newSingleThreadExecutor().execute(()->{
            List<ListsEntity> listsEntities = MyDatabase.getINSTANCE(context).listsDao().getAllLists();
            final ListWithProductsDao listWithProductsDao = MyDatabase.getINSTANCE(this).listWithProductsDao();

            ArrayList<String> listNames = new ArrayList<>();
            ArrayList<Integer> singleIds = new ArrayList<>();


            //save the list ids and names (excluding the online/offline) in suitable structures
            for (int i = 0; i < listsEntities.size(); i++) {
                    listNames.add(listsEntities.get(i).getListName());
                    singleIds.add(listsEntities.get(i).getListId());
            }

            runOnUiThread(()->{
                CharSequence[] lists = new CharSequence[listNames.size()];
                lists = listNames.toArray(lists);
                builder.setTitle("Select a list");
                builder.setItems(lists, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("TAG", "onClick: test");
                        Executors.newSingleThreadExecutor().execute(()->{listWithProductsDao.addProductToList(new JunctionEntity(singleIds.get(which), code));
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);});
                    }
                });
                builder.show();
            });

        }));
    }

    private void loadFromDB() {
        final ProductsEntity entity =  MyDatabase
                .getINSTANCE(this)
                .productsDao()
                .getProduct(code);

        name.setText("Name: " + String.valueOf(entity.getProductName()));
        weight.setText("Weight: " + String.valueOf(entity.getWeight()));
        calories.setText("Calories per serving : " + String.valueOf(entity.getNutriments().getCalories()));
        viewCode.setText("Code: " + (entity.getCode()));
        grade.setText("Grade: " + String.valueOf(entity.getProductGrade()).toUpperCase());

        // make the string image information to url
        try {
            imageURL = new URL(String.valueOf(entity.getImage()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // Use the url to Bitmap to be able to be viewed on the imageView
        try {
            bmp = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
            runOnUiThread(()->{image.setImageBitmap(bmp);});
        } catch (IOException ex) {
            Log.d("Error", ex.getMessage());
            ex.printStackTrace();
            bmp = null;
        }
    }
}
