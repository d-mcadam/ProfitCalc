package com.example.profitcalcapp.Utilities;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.profitcalcapp.Data.Storage;

import java.util.concurrent.ExecutionException;

import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;

public class Commands {

    /**
     * Starts any activity.
     * @param oldActivity   The current activity (you're probably coding in it).
     * @param storage       The storage class (Passed between activities and class because idk what/how else to do).
     * @param newActivity   The new activity you are going to load. You need the Activities CLASS, so name the activity and add '.class'.
     */
    public void StartActivity(Activity oldActivity, Storage storage, Class newActivity){
        Intent newWindow = new Intent(oldActivity.getApplicationContext(), newActivity);
        newWindow.putExtra(STORAGE_CLASS_DATA, storage);
        oldActivity.startActivity(newWindow);
        oldActivity.finish();
    }

    /**
     * Saves all app data and Starts any activity.
     * <p>
     *     This method will initially save the app data, and the new activity will load from the async task.
     * </p>
     * @param oldActivity   The current activity (you're probably coding in it).
     * @param storage       The storage class (Passed between activities and class because idk what/how else to do).
     * @param newActivity   The new activity you are going to load. You need the Activities CLASS, so name the activity and add '.class'.
     */
    public void SaveAndStartActivity(Activity oldActivity, Storage storage, Class newActivity){
        try {
            new AppDataStorage(oldActivity, storage).execute().get();
            StartActivity(oldActivity, storage, newActivity);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(oldActivity.getApplicationContext(), "An error occurred attempting to save the data.", Toast.LENGTH_LONG).show();
        }
    }

}
