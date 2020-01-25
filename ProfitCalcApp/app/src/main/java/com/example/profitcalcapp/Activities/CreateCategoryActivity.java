package com.example.profitcalcapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.profitcalcapp.Data.Category;
import com.example.profitcalcapp.Data.DataEntry;
import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;
import com.example.profitcalcapp.Utilities.BooleanString;
import com.example.profitcalcapp.Utilities.Commands;
import com.example.profitcalcapp.Utilities.DataEntryAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.profitcalcapp.Utilities.IntentKeys.EDITING_CATEGORY_PASS_KEY;
import static com.example.profitcalcapp.Utilities.IntentKeys.NEW_CATEGORY_PASS_KEY;
import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;

public class CreateCategoryActivity extends AppCompatActivity {

    //<editor-fold defaultstate="collapsed" desc="Data Classes">
    private Storage storage;
    private final Commands cmds = new Commands();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Activity Views">
    private DataEntryAdapter dataEntryAdapter;
    private RecyclerView recyclerView;
    private EditText fieldCategoryTitle;
    private EditText searchField;
    private Button buttonSave;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private final Activity thisActivity = this;
    private List<DataEntry> items = new ArrayList<>();
    private Category tempCategory = null;
    private Category editingCategory = null;
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

        //<editor-fold defaultstate="collapsed" desc="Initialise categories">
        editingCategory = (Category) intent.getSerializableExtra(EDITING_CATEGORY_PASS_KEY);
        if (editingCategory == null) {
            tempCategory = (Category) intent.getSerializableExtra(NEW_CATEGORY_PASS_KEY);
            if (tempCategory == null) {
                tempCategory = new Category();
            }else{
                fieldCategoryTitle.setText(tempCategory.getTitle());
            }
        }else{
            fieldCategoryTitle.setText(editingCategory.getTitle());
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise recycler view list">
        dataEntryAdapter = new DataEntryAdapter(this, items, storage);
        recyclerView = findViewById(R.id.recyclerViewDataEntryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dataEntryAdapter);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise search box">
        searchField = findViewById(R.id.editTextSearchDataEntries);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { RefreshList(); }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { RefreshList(); }
            @Override
            public void afterTextChanged(Editable editable) { RefreshList(); }
        });
        //</editor-fold>

        CheckSaveEligibility();
        RefreshList();

    }

    public void RefreshList(){
        items.clear();
        for (DataEntry dataEntry : editingCategory == null ? tempCategory.getEntries() : editingCategory.getEntries())
            if (cmds.SatisfiesSearchQuery(dataEntry, searchField.getText().toString()))
                items.add(dataEntry);

        dataEntryAdapter.focusedPosition = -1;
        dataEntryAdapter.notifyDataSetChanged();
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
        buttonSave.setTooltipText(eligible ? "Save Category" : !titleExists ? "Need a title" : "Duplicate Title");
    }

    public void SaveCategory(View view){
        String title = fieldCategoryTitle.getText().toString().trim();

        if (editingCategory == null) {
            Category category = new Category(title);
            for (DataEntry dataEntry : tempCategory.getEntries())
                category.addEntry(dataEntry);

            BooleanString r = storage.addCategory(category);

            if (!r.result) {
                Toast.makeText(getApplicationContext(), r.msg, Toast.LENGTH_LONG).show();
                return;
            }
        }else{
            editingCategory.setTitle(title);
            //all entries should already be updated on the object
        }

        cmds.SaveAndStartActivity(this, storage, CategoryActivity.class);
    }

    public void AddDataEntry(View view){
        String title = fieldCategoryTitle.getText().toString().trim();

        Intent wnd = new Intent(this, CreateDataEntryActivity.class);
        wnd.putExtra(STORAGE_CLASS_DATA, storage);

        if (editingCategory == null) {
            tempCategory.setTitle(title);
            wnd.putExtra(NEW_CATEGORY_PASS_KEY, tempCategory);
        }else {
            editingCategory.setTitle(title);
            wnd.putExtra(EDITING_CATEGORY_PASS_KEY, editingCategory);
        }

        startActivity(wnd);
        finish();
    }

}
