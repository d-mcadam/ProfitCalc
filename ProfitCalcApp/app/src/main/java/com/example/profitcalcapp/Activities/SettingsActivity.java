package com.example.profitcalcapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 16908332:
                cmds.StartActivity(this, storage, MainActivity.class);
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private void init(){

        Intent intent = getIntent();
        storage = (Storage) intent.getSerializableExtra(STORAGE_CLASS_DATA);
        if (storage == null){
            Toast.makeText(this, "Error loading storage in SettingsActivity.class", Toast.LENGTH_LONG).show();
            cmds.StartActivity(this, storage, MainActivity.class);
            return;
        }

        final Switch toggle = findViewById(R.id.toggleUsingEval);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isOn) {
                if (isOn)
                    buttonView.setText(R.string.settings_toggle_button_on);
                else
                    buttonView.setText(R.string.settings_toggle_button_off);
            }
        });

        toggle.setChecked(!storage.usingEvaluator);
        if (toggle.isChecked())
            toggle.setText(R.string.settings_toggle_button_on);
        else
            toggle.setText(R.string.settings_toggle_button_off);

    }

}
