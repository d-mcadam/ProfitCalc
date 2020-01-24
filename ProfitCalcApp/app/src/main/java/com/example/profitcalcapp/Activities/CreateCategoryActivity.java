package com.example.profitcalcapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.profitcalcapp.Data.Category;
import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;
import com.example.profitcalcapp.Utilities.Commands;

import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;

public class CreateCategoryActivity extends AppCompatActivity {

    //<editor-fold defaultstate="collapsed" desc="Data Classes">
    private Storage storage;
    private final Commands cmds = new Commands();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Activity Views">
    private EditText fieldCategoryTitle;
    private Button buttonSave;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private final Activity thisActivity = this;
    //</editor-fold>

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332://this is the ID code for a generic "Back" button that's provided by the api
                if (UnsavedData()){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                    dialog.setCancelable(true);
                    dialog.setTitle("Unsaved data");
                    dialog.setMessage("You will lose any unsaved data, do you want to continue?");

                    dialog.setNegativeButton("Go Back", null);

                    dialog.setPositiveButton("Discard Changes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cmds.StartActivity(thisActivity, storage, CategoryActivity.class);
                        }
                    });

                    dialog.create().show();
                }else {
                    cmds.StartActivity(this, storage, CategoryActivity.class);
                }
                break;
        }
        return true;
    }
    private boolean UnsavedData(){
        String title = fieldCategoryTitle.getText().toString().trim();
        return !title.equals("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        init();
    }

    private void init(){

        //<editor-fold defaultstate="collapsed" desc="Get Storage from Intent">
        Intent intent = getIntent();
        storage = (Storage) intent.getSerializableExtra(STORAGE_CLASS_DATA);
        if (storage == null){
            Toast.makeText(this, "Error loading storage in CreateCategoryActivity.class", Toast.LENGTH_LONG).show();
            cmds.StartActivity(this, storage, MainActivity.class);
            return;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Reference activity Views">
        buttonSave = findViewById(R.id.buttonSaveCategory);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise title field">
        fieldCategoryTitle = findViewById(R.id.editTextCategoryTitle);
        fieldCategoryTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { CheckSaveEligibility(); }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { CheckSaveEligibility(); }
            @Override
            public void afterTextChanged(Editable editable) { CheckSaveEligibility(); }
        });
        //</editor-fold>

        CheckSaveEligibility();

    }

    private boolean DuplicateTitle(){

        for (Category category : storage.getCategories()){
            if (category.getTitle().toLowerCase().equals(fieldCategoryTitle.getText().toString().toLowerCase().trim())){
                fieldCategoryTitle.setError("Category name already exists.");
                return true;
            }
        }

        return false;
    }

    private void CheckSaveEligibility(){
        boolean titleExists = !fieldCategoryTitle.getText().toString().trim().equals("");
        if (!titleExists)
            fieldCategoryTitle.setError("Title cannot be empty");

        boolean duplicateTitle = DuplicateTitle();
        boolean eligible = titleExists && !duplicateTitle;
        int colour = eligible ? getResources().getColor(R.color.pureGreen, null) : getResources().getColor(R.color.greyedOut, null);

        buttonSave.setClickable(eligible);
        buttonSave.setBackgroundColor(colour);
        buttonSave.setTooltipText(eligible ? "Save Aura" : !titleExists ? "Need a title" : "Duplicate Title");
    }

}
