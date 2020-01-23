package com.example.profitcalcapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;
import com.example.profitcalcapp.Utilities.AppDataStorage;
import com.example.profitcalcapp.Utilities.Commands;

import java.util.concurrent.ExecutionException;

import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;

public class MainActivity extends AppCompatActivity {

    //<editor-fold defaultstate="collapsed" desc="Data Classes">
    public Storage storage;
    private final Commands cmds = new Commands();
    //</editor-fold>

    //<editor-fold defautlstate="collapsed" desc="Overridden Activity operations">
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Activity Initialisation">
    public void init(){

        //<editor-fold defaultstate="collapsed" desc="Get Storage from Intent">
        Intent intent = getIntent();
        storage = (Storage) intent.getSerializableExtra(STORAGE_CLASS_DATA);

        if (storage == null){
            Toast.makeText(getApplicationContext(), "Loading data file...", Toast.LENGTH_LONG).show();
            try {
                new AppDataStorage(this, storage).execute(true).get();
                if (storage == null){
                    Toast.makeText(getApplicationContext(), "Storage was empty, initialising...", Toast.LENGTH_LONG).show();
                    storage = new Storage();
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Failed to retrieve data, initialising...", Toast.LENGTH_LONG).show();
                storage = new Storage();
            }

        }
        //</editor-fold>

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="View actions">
    public void OpenCategories(View view){
        cmds.StartActivity(this, storage, CategoryActivity.class);
    }

    public void OpenSettings(View view){
        cmds.StartActivity(this, storage, SettingsActivity.class);
    }
    //</editor-fold>

}
