package com.example.profitcalcapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;
import com.example.profitcalcapp.Utilities.AppDataStorage;

import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;

public class MainActivity extends AppCompatActivity {

    public Storage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){

        Intent intent = getIntent();
        storage = (Storage) intent.getSerializableExtra(STORAGE_CLASS_DATA);

        if (storage == null){

            Toast.makeText(getApplicationContext(), "Storage was null, loading data file...", Toast.LENGTH_LONG).show();
            new AppDataStorage(this, storage).execute(true);

            if (storage == null){

                Toast.makeText(getApplicationContext(), "Storage was empty, initialising...", Toast.LENGTH_SHORT).show();
                storage = new Storage();

            }

        }

    }

}
