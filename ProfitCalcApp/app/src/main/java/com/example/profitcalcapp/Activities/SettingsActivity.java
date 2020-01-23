package com.example.profitcalcapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
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
    private Switch toggle;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private final Activity thisActivity = this;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Overridden Activity operations">

    //<editor-fold defaultstate="collapsed" desc="The provided button operation">
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 16908332://this is the ID code for a generic "Back" button that's provided by the api
                if (CheckForUnsavedData()){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                    dialog.setCancelable(true);
                    dialog.setTitle("Unsaved data");
                    dialog.setMessage("You will lose any unsaved data, do you want to continue?");

                    dialog.setNegativeButton("Go Back", null);

                    dialog.setPositiveButton("Discard Changes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cmds.StartActivity(thisActivity, storage, MainActivity.class);
                        }
                    });

                    dialog.create().show();
                }else {
                    cmds.StartActivity(this, storage, MainActivity.class);
                }
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

    //<editor-fold defaultstate="collapsed" desc="Activity initialisation">
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
        toggle = findViewById(R.id.toggleUsingEval);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isOn) {
                SwitchToggle(toggleWarning);
            }
        });

        //If the toggle is UNCHECKED, that means 'usingEvaluator' is true
        //ie. toggle false = eval. true
        toggle.setChecked(!storage.usingEvaluator);
        SwitchToggle(toggleWarning);
        //</editor-fold>

    }

    //<editor-fold defaultstate="collapsed" desc="Used only in init()">
    private void SwitchToggle(View view){
        boolean isOn = toggle.isChecked();
        toggle.setText(isOn ? R.string.settings_toggle_button_on : R.string.settings_toggle_button_off);
        view.setVisibility(isOn ? View.INVISIBLE : View.VISIBLE);
    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="'Loose' methods">
    private boolean CheckForUnsavedData(){
        //If the toggle is UNCHECKED, that means 'usingEvaluator' is true
        //ie. toggle false = eval. true
        return toggle.isChecked() != !storage.usingEvaluator;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="View actions">
    public void SaveData(View view){
        storage.usingEvaluator = !toggle.isChecked();
        cmds.SaveAndStartActivity(this, storage, MainActivity.class);
    }
    //</editor-fold>

}
