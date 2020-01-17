package com.example.profitcalcapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;
import com.example.profitcalcapp.Utilities.Commands;

import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;

public class SettingsActivity extends AppCompatActivity {

    //<editor-fold defaultstate="collapsed" desc="Data Classes">
    private Storage storage;
    private final Commands cmds = new Commands();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Activity Views">

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Overridden Activity operations">

    //<editor-fold defaultstate="collapsed" desc="The provided back button operation">
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
        setContentView(R.layout.activity_settings);
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

        //<editor-fold defaultstate="collapsed" desc="Set up toggle">
        final TextView toggleWarning = findViewById(R.id.textViewToggleWarning);
        final Switch toggle = findViewById(R.id.toggleUsingEval);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isOn) {
                SwitchToggle(compoundButton, toggleWarning);
            }
        });

        toggle.setChecked(!storage.usingEvaluator);
        SwitchToggle(toggle, toggleWarning);
        //</editor-fold>

    }

    private void SwitchToggle(CompoundButton compoundButton, View view){
        boolean isOn = compoundButton.isChecked();
        compoundButton.setText(isOn ? R.string.settings_toggle_button_on : R.string.settings_toggle_button_off);
        view.setVisibility(isOn ? View.INVISIBLE : View.VISIBLE);
    }

}
