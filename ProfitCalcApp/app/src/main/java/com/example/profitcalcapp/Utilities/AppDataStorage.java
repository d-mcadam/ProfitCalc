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

    private final Context context;
    private final Storage storage;

    public AppDataStorage(Context context, Storage storage){
        this.context = context;
        this.storage = storage;
    }

    @Override
    protected String doInBackground(Boolean... booleans){

        //save data before leaving
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

        try{

            if (booleans.length > 0 && booleans[0]){

                //Load the data
                fileInputStream = context.openFileInput(APP_STORAGE_DATA);
                objectInputStream = new ObjectInputStream(fileInputStream);

                ((MainActivity)context).storage = (Storage) objectInputStream.readObject();

                objectInputStream.close();
                fileInputStream.close();

            }else {

                //Save the data
                fileOutputStream = context.openFileOutput(APP_STORAGE_DATA, Context.MODE_PRIVATE);
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
    }

}
