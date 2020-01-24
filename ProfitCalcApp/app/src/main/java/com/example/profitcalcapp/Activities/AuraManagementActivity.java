package com.example.profitcalcapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.profitcalcapp.Data.Aura;
import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;
import com.example.profitcalcapp.Utilities.AuraAdapter;
import com.example.profitcalcapp.Utilities.Commands;

import java.util.ArrayList;
import java.util.List;

import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;

public class AuraManagementActivity extends AppCompatActivity {

    //<editor-fold defaultstate="collapsed" desc="Data Classes">
    private Storage storage;
    private final Commands cmds = new Commands();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Activity Views">
    private AuraAdapter auraAdapter;
    private RecyclerView recyclerView;
    private EditText searchField;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private List<Aura> items = new ArrayList<>();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Overridden Activity operations">

    //<editor-fold defaultstate="collapsed" desc="The provided button operation">
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332://this is the ID code for a generic "Back" button that's provided by the api
                cmds.StartActivity(this, storage, SettingsActivity.class);
                break;
            default:
                break;
        }
        return true;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="The default create method">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aura_management);
        init();
    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Activity initialisation">
    private void init() {

        //<editor-fold defaultstate="collapsed" desc="Get Storage from Intent">
        Intent intent = getIntent();
        storage = (Storage) intent.getSerializableExtra(STORAGE_CLASS_DATA);
        if (storage == null){
            Toast.makeText(this, "Error loading storage in AuraManagementActivity.class", Toast.LENGTH_LONG).show();
            cmds.StartActivity(this, storage, SettingsActivity.class);
            return;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise recycler view list">
        auraAdapter = new AuraAdapter(this, items, storage);
        recyclerView = findViewById(R.id.recyclerViewAuraList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(auraAdapter);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise search box">
        searchField = findViewById(R.id.editTextSearchBox);
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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="List operations">
    public void RefreshList(){
        items.clear();
        for (Aura aura : storage.getAuras())
            if (cmds.SatisfiesSearchQuery(aura, searchField.getText().toString()))
                items.add(aura);

        auraAdapter.focusedPosition = -1;
        auraAdapter.notifyDataSetChanged();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="View actions">
    public void AddAura(View view){
        cmds.StartActivity(this, storage, CreateAuraActivity.class);
    }
    //</editor-fold>

}
