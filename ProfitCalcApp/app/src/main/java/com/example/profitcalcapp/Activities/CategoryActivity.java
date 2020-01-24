package com.example.profitcalcapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.profitcalcapp.Data.Category;
import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;
import com.example.profitcalcapp.Utilities.CategoryAdapter;
import com.example.profitcalcapp.Utilities.Commands;

import java.util.ArrayList;
import java.util.List;

import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;

public class CategoryActivity extends AppCompatActivity {

    //<editor-fold defaultstate="collapsed" desc="Data classes">
    private Storage storage;
    private final Commands cmds = new Commands();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Activity Views">
    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerView;
    private EditText searchField;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private List<Category> items = new ArrayList<>();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Overridden Activity operations">

    //<editor-fold defaultstate="collapsed" desc="The provided button operation">
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 16908332:
                cmds.StartActivity(this, storage, MainActivity.class);
                break;
        }
        return true;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="The default create method">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        init();
    }
    //</editor-fold>

    //</editor-fold>

    private void init(){

        //<editor-fold defaultstate="collapsed" desc="Get Storage from Intent">
        Intent intent = getIntent();
        storage = (Storage) intent.getSerializableExtra(STORAGE_CLASS_DATA);
        if (storage == null){
            Toast.makeText(this, "Error loading storage in SettingsActivity.class", Toast.LENGTH_LONG).show();
            cmds.StartActivity(this, storage, MainActivity.class);
            return;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise recycler view">
        categoryAdapter = new CategoryAdapter(this, items, storage);
        recyclerView = findViewById(R.id.recyclerViewCategoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(categoryAdapter);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise search box">
        searchField = findViewById(R.id.editTextSearchCategories);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { RefreshList(); }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { RefreshList(); }
            @Override
            public void afterTextChanged(Editable editable) { RefreshList(); }
        });
        //</editor-fold>

        RefreshList();

    }

    //<editor-fold defaultstate="collapsed" desc="List operations">
    public void RefreshList(){
        items.clear();
        for (Category category : storage.getCategories())
            if (cmds.SatisfiesSearchQuery(category, searchField.getText().toString()))
                items.add(category);

        categoryAdapter.focusedPosition = -1;
        categoryAdapter.notifyDataSetChanged();
    }
    //</editor-fold>

}
