package com.example.assignment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.concurrent.Executors;

public class EditListsActivity extends AppCompatActivity {

    public static String TAG = "Debugging: ";

    private Button saveButton;
    private EditText listName;
    private EditText listDescription;
    private Toolbar tool;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private MaterialAlertDialogBuilder builder;

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
        radioGroup = findViewById(R.id.radio_group);


        // Add a TextWatcher listener to the inputs to Validate input from the user (check if either of the fields are empty)
        listName.addTextChangedListener(listTextWatcher);
        listDescription.addTextChangedListener(listTextWatcher);

        // update the recycler view every time on resume
        Executors.newSingleThreadExecutor().execute(this::updateDB);

        builder = new MaterialAlertDialogBuilder(this);
        // set the clicklistener on the navigation button to delete the whole List database
        tool = findViewById(R.id.toolbar);
        tool.setNavigationOnClickListener((view)-> {
            Log.d(TAG, "onResume: ");

            builder.setTitle("Warning").setMessage("Do you want to delete all the Lists in the Database?");
            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do Nothing closes the dialogue
                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        deleteDatabase();
                    });
                }
            });
            builder.show();
        });
    }

    private void deleteDatabase() {
        MyDatabase.getINSTANCE(this).listsDao().deleteAllLists();
        Executors.newSingleThreadExecutor().execute(this::updateDB);
        runOnUiThread(()->{
            Toast.makeText(this, "All Lists deleted" , Toast.LENGTH_LONG).show();
        });
    }

    // Create the methods that will handle the validation of the input
    private TextWatcher listTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String name = listName.getText().toString().trim();
            String disc = listDescription.getText().toString().trim();

            // make the button Visible when BOTH of the fields have value
            if (!name.isEmpty() && !disc.isEmpty()){
                runOnUiThread(()->{saveButton.setEnabled(true);});
            } else
                runOnUiThread(()->{saveButton.setEnabled(false);});
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    // method that updates the Database and then the RecyclerView( never runs on the main thread)
    private void updateDB(){
        final List<ListsEntity> list =  MyDatabase
                .getINSTANCE(this)
                .listsDao()
                .getAllLists();

        // create a new instance of the recyclerview adaptor
        RecyclerView recyclerView = findViewById(R.id.recycler_view9);

        Adapter2 adaptor = new Adapter2();
        // Link RecyclerViw to the adaptor
        runOnUiThread(()->{recyclerView.setAdapter(adaptor);});
        adaptor.setListsEntities(list);
    }



    // Method that is called when clicking the save button and
    public void saveList(View view){
        closeKeyboard();
        Executors.newSingleThreadExecutor().execute(this::saveToDB);
    }


    // Method that Saves the field values in the ListsEntity (lists table) and calls the UpdateDB on a different thread Not executed on the main thread
    private void saveToDB(){
        // Check what button is checked
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        final String color = radioButton.getText().toString().trim();
        final String name = listName.getText().toString().trim();
        final String disc = listDescription.getText().toString().trim();
        // Save to database
        ListsEntity entity = new ListsEntity(name, disc, color);
        MyDatabase.getINSTANCE(this).listsDao().insert(entity);
        // Update the recycler view with the new database
        Executors.newSingleThreadExecutor().execute(this::updateDB);
    }


    // Navigates to the Product Edit list activity
    public void goToEditListsActivity(MenuItem item) {
        Intent intent = new Intent(this, EditListsActivity.class);
        startActivity(intent);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
