package com.example.profitcalcapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.profitcalcapp.Data.Aura;
import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;
import com.example.profitcalcapp.Utilities.BooleanString;
import com.example.profitcalcapp.Utilities.Commands;

import static com.example.profitcalcapp.Utilities.IntentKeys.EDIT_AURA_KEY;
import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;
import static com.example.profitcalcapp.Utilities.IntentKeys.STRING_PASS_KEY;

public class CreateAuraActivity extends AppCompatActivity {

    //<editor-fold defaultstate="collapsed" desc="Data Classes">
    private Storage storage;
    private final Commands cmds = new Commands();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Activity Views">
    private EditText fieldAuraTitle;
    private Button buttonSave;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private final Activity thisActivity = this;
    private Aura editingAura = null;
    //</editor-fold>

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 16908332:
                if (UnsavedData()){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);

                    dialog.setCancelable(true);
                    dialog.setTitle("Unsaved data");
                    dialog.setMessage("You will lose any unsaved data, do you want to continue?");

                    dialog.setNegativeButton("Go Back", null);

                    dialog.setPositiveButton("Discard Changes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cmds.StartActivity(thisActivity, storage, AuraManagementActivity.class);
                        }
                    });

                    dialog.create().show();
                }else {
                    cmds.StartActivity(this, storage, AuraManagementActivity.class);
                }
                break;
            default:
                break;
        }
        return true;
    }
    private boolean UnsavedData(){
        String title = fieldAuraTitle.getText().toString().trim();
        return editingAura == null ? !title.equals("") : !editingAura.getTitle().equals(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_aura);
        init();
    }

    private void init() {

        //<editor-fold defaultstate="collapsed" desc="Get Storage from Intent">
        Intent intent = getIntent();
        storage = (Storage) intent.getSerializableExtra(STORAGE_CLASS_DATA);
        if (storage == null){
            Toast.makeText(this, "Error loading storage in CreateAuraActivity.class", Toast.LENGTH_LONG).show();
            cmds.StartActivity(this, storage, MainActivity.class);
            return;
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Reference activity Views">
        buttonSave = findViewById(R.id.buttonSaveAura);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise title field">
        fieldAuraTitle = findViewById(R.id.editTextAuraTitle);
        fieldAuraTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { CheckSaveEligibility(); }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { CheckSaveEligibility(); }
            @Override
            public void afterTextChanged(Editable editable) { CheckSaveEligibility(); }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Set edit data, if any">
        editingAura = (Aura) intent.getSerializableExtra(EDIT_AURA_KEY);
        if (editingAura != null){
            buttonSave.setText(R.string.text_update);
            fieldAuraTitle.setText(editingAura.getTitle());
        }else{
            fieldAuraTitle.setText(intent.getStringExtra(STRING_PASS_KEY));
        }
        //</editor-fold>

        CheckSaveEligibility();

    }

    private boolean DuplicateTitle(){

        for (Aura aura : storage.getAuras()){
            if (editingAura != null && aura.getTitle().equals(editingAura.getTitle()))
                continue;

            if (aura.getTitle().toLowerCase().equals(fieldAuraTitle.getText().toString().toLowerCase().trim())){
                fieldAuraTitle.setError("Aura name already exists");
                return true;
            }
        }

        return false;
    }

    private void CheckSaveEligibility(){
        boolean titleExists = !fieldAuraTitle.getText().toString().trim().equals("");
        if (!titleExists)
            fieldAuraTitle.setError("Title cannot be empty");

        boolean duplicateTitle = DuplicateTitle();
        boolean eligible = titleExists && !duplicateTitle;
        int colour = eligible ? getResources().getColor(R.color.pureGreen, null) : getResources().getColor(R.color.greyedOut, null);

        buttonSave.setClickable(eligible);
        buttonSave.setBackgroundColor(colour);
        buttonSave.setTooltipText(eligible ? "Save Aura" : !titleExists ? "Need a title" : "Duplicate Title");
    }

    public void SaveAura(View view){
        String title = fieldAuraTitle.getText().toString().trim();

        if (editingAura == null) {

            Aura aura = new Aura(title);
            BooleanString r = storage.addAura(aura);

            if (!r.result) {
                Toast.makeText(getApplicationContext(), r.msg, Toast.LENGTH_LONG).show();
                return;
            }

        }else{

            for (Aura aura : storage.getAuras())
                if (aura.getTitle().equals(editingAura.getTitle())){
                    aura.setTitle(title);
                    break;
                }

        }

        cmds.SaveAndStartActivity(this, storage, AuraManagementActivity.class);
    }

}
