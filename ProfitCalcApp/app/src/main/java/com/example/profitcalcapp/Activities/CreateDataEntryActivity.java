package com.example.profitcalcapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.profitcalcapp.Data.Aura;
import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;
import com.example.profitcalcapp.Utilities.Commands;

import java.util.ArrayList;
import java.util.List;

import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;

public class CreateDataEntryActivity extends AppCompatActivity {

    //<editor-fold defaultstate="collapsed" desc="Data Classes">
    private Storage storage;
    private final Commands cmds = new Commands();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Activity Views">
    private Spinner auraSpinner;
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

                    dialog.setPositiveButton("Discard Changes", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cmds.StartActivity(thisActivity, storage, CreateCategoryActivity.class);
                        }
                    });
                }else{
                    cmds.StartActivity(this, storage, CreateCategoryActivity.class);
                }
                break;
            default:
                break;
        }
        return true;
    }
    private boolean UnsavedData(){
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data_entry);
        init();
    }

    private void init(){

        //<editor-fold defaultstate="collapsed" desc="Get Storage from Intent">
        Intent intent = getIntent();
        storage = (Storage) intent.getSerializableExtra(STORAGE_CLASS_DATA);
        if (storage == null){
            Toast.makeText(this, "Error loading storage in CreateDataEntryActivity.class", Toast.LENGTH_LONG).show();
            cmds.StartActivity(this, storage, CreateCategoryActivity.class);
            return;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise spinner">
        //id / reference the view
        auraSpinner = findViewById(R.id.spinnerAuraSelector);
        //create a list to hold the strings
        final List<String> stringList = new ArrayList<>();
        //add a default / initial value
        stringList.add("None");
        //add the remaining auras held in storage
        for (Aura aura : storage.getAuras())
            stringList.add(aura.getTitle());
        //create an adapter with the list of strings created above
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, stringList);
        //set the drop down view layout
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //set the adapter to the view spinner object
        auraSpinner.setAdapter(arrayAdapter);
        //</editor-fold>

    }
}
