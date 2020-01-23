package com.example.profitcalcapp.Utilities;

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

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private final Context context;
    private final Storage storage;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public AppDataStorage(Context context, Storage storage){
        this.context = context;
        this.storage = storage;
    }
    //</editor-fold>

    @Override
    protected String doInBackground(Boolean... booleans){

        //<editor-fold defaultstate="collapsed" desc="Variable declaration">
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        //</editor-fold>

        try{

            if (booleans.length > 0 && booleans[0]){

                //<editor-fold defaultstate="collapsed" desc="Loading data">
                fileInputStream = context.openFileInput(APP_STORAGE_DATA);
                objectInputStream = new ObjectInputStream(fileInputStream);

                ((MainActivity)context).storage = (Storage) objectInputStream.readObject();

                objectInputStream.close();
                fileInputStream.close();
                //</editor-fold>

            }else {

                //<editor-fold defaultstate="collapsed" desc="Saving data">
                fileOutputStream = context.openFileOutput(APP_STORAGE_DATA, Context.MODE_PRIVATE);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(storage);
                objectOutputStream.close();
                fileOutputStream.close();
                //</editor-fold>

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
