package com.example.profitcalcapp.Utilities;

import android.app.Activity;
import android.content.Intent;

import com.example.profitcalcapp.Data.Storage;

import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;

public class Commands {

    public void StartActivity(Activity oldActivity, Storage storage, Class newActivity){
        Intent newWindow = new Intent(oldActivity.getApplicationContext(), newActivity);
        newWindow.putExtra(STORAGE_CLASS_DATA, storage);
        oldActivity.startActivity(newWindow);
        oldActivity.finish();
    }

    public void SaveAndStartActivity(Activity oldActivity, Storage storage, Class newActivity){
        new AppDataStorage(oldActivity.getApplicationContext(), storage).execute();
        this.StartActivity(oldActivity, storage, newActivity);
    }

}
