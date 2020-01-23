package com.example.profitcalcapp.Utilities;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.example.profitcalcapp.Activities.MainActivity;
import com.example.profitcalcapp.Data.Storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.example.profitcalcapp.Utilities.IntentKeys.APP_STORAGE_DATA;

public class AppDataStorage extends AsyncTask<Boolean, String, String> {

    private final Activity oldActivity;
    private final Storage storage;
    private final Class newActivity;
    private final Commands cmds = new Commands();

    private final boolean savingData;

    public AppDataStorage(Activity oldActivity, Storage storage){
        this.oldActivity = oldActivity;
        this.storage = storage;
        this.newActivity = null;
        savingData = false;
    }

    public AppDataStorage(Activity oldActivity, Storage storage, Class newActivity){
        this.oldActivity = oldActivity;
        this.storage = storage;
        this.newActivity = newActivity;
        savingData = true;
    }

    @Override
    protected String doInBackground(Boolean... booleans){

        //save data before leaving
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

        try{

            if (savingData){

                //Load the data
                fileInputStream = oldActivity.getApplicationContext().openFileInput(APP_STORAGE_DATA);
                objectInputStream = new ObjectInputStream(fileInputStream);

                ((MainActivity)oldActivity.getApplicationContext()).storage = (Storage) objectInputStream.readObject();

                objectInputStream.close();
                fileInputStream.close();

            }else {

                //Save the data
                fileOutputStream = oldActivity.getApplicationContext().openFileOutput(APP_STORAGE_DATA, Context.MODE_PRIVATE);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(storage);
                objectOutputStream.close();
                fileOutputStream.close();

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result){
        if (savingData && newActivity != null)
            cmds.StartActivity(oldActivity, storage, newActivity);
    }

}
