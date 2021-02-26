package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

public class EditListsActivity extends AppCompatActivity {

    public static String TAG = "Debugging: ";

   // private final Vector<ListsEntity> data = new Vector<>();
//    private final Vector<String> data2 = new Vector<>();

    private Button saveButton;
    private EditText listName;
    private EditText listDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_lists);
    }

    @Override
    protected void onResume() {
        super.onResume();

        listName = findViewById(R.id.list_edit_name);
        listDescription = findViewById(R.id.list_edit_description);
        saveButton = findViewById(R.id.saveButton);

        // Add a TextWatcher listener to the inputs to Validate input from the user (check if either of the fields are empty)
        listName.addTextChangedListener(listTextWatcher);
        listDescription.addTextChangedListener(listTextWatcher);

        // Executed by a different thread than the main to not freeze the app
        Executors.newSingleThreadExecutor().execute(this::updateDB);
    }

    // Create the methods that will handle the validation of the input
    private TextWatcher listTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String name = listName.getText().toString().trim();
            String disc = listDescription.getText().toString().trim();

            // make the button Visible when the fields have value
            if (!name.isEmpty() && !disc.isEmpty()){
                saveButton.setVisibility(View.VISIBLE);
            } else
                saveButton.setVisibility(View.INVISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    // method that updates the Database and then the RecyclerView( never runs on the main thread)
    private void updateDB(){
        final List<ListsEntity> list =  myDatabase
                .getINSTANCE(this)
                .listsDao()
                .getAllLists();
//        for (ListsEntity entity :list) {
//            data.add(entity.getListName());
//            data.add(entity.getDescription());
//        }
        // create a new instance of the recyclerview adaptor
        RecyclerView recyclerView = findViewById(R.id.recycler_view9);

        Adapter2 adaptor = new Adapter2();
        // Link RecyclerViw to the adaptor
        runOnUiThread(()->{recyclerView.setAdapter(adaptor);});
        adaptor.setListEntity(list);
    }



    // Method that is called when clicking the save button and assigns tasks(save and update the database) to other threads(onClick)
    public void saveList(View view){
        Executors.newSingleThreadExecutor().execute(this::saveToDB);
        Executors.newSingleThreadExecutor().execute(this::updateDB);
    }

//    public void deleteListRecord(View view){
//        Executors.newSingleThreadExecutor().execute(()->{deleteRecord();});
//        Executors.newSingleThreadExecutor().execute(this::updateDB);
//    }

//    private void deleteRecord(){
//        ListsEntity listsEntity;
//        myDatabase.getINSTANCE(this).listsDao().delete(listsEntity);
//
//    }


    // Method that Saves the field values in the ListsEntity (lists table) Not executed on the main thread
    private void saveToDB(){
        final String name = listName.getText().toString().trim();
        final String disc = listDescription.getText().toString().trim();
        ListsEntity entity = new ListsEntity(name, disc);
        myDatabase.getINSTANCE(this).listsDao().insert(entity);
    }


    // Navigates to the Product Edit list activity
    public void goToEditListsActivity(MenuItem item) {
        Intent intent = new Intent(this, EditListsActivity.class);
        startActivity(intent);
    }

    //Navigates to the Product description activity
    public void goToProductDescriptionActivity (View view) {
        Intent intent = new Intent(this, ProductDescriptionActivity.class);
        startActivity(intent);
    }
}
