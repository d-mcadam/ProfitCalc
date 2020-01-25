package com.example.profitcalcapp.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.profitcalcapp.Data.Aura;
import com.example.profitcalcapp.Data.Category;
import com.example.profitcalcapp.Data.DataEntry;
import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;
import com.example.profitcalcapp.Utilities.Commands;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.example.profitcalcapp.Utilities.IntentKeys.EDITING_CATEGORY_PASS_KEY;
import static com.example.profitcalcapp.Utilities.IntentKeys.NEW_CATEGORY_PASS_KEY;
import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;

public class CreateDataEntryActivity extends AppCompatActivity {

    //<editor-fold defaultstate="collapsed" desc="Data Classes">
    private Storage storage;
    private final Commands cmds = new Commands();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Activity Views">
    private Button buttonSave;
    private EditText fieldEntryTitle;
    private Spinner fieldAuraSpinner;
    private EditText fieldStartWealth;
    private EditText fieldFinishWealth;
    private TextView displayProfit;
    private EditText fieldHoursSpent;
    private TextView displayProfitPerHour;
    private EditText fieldKillCount;
    private TextView displayKillsPerHour;
    private EditText fieldExtraDetails;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private Category editingCategory = null;
    private Category newCategory = null;
    private DataEntry editingDataEntry = null;
    //</editor-fold>

    private void RestartCreateCategoryActivity(){
        Intent wnd = new Intent(this, CreateCategoryActivity.class);
        wnd.putExtra(STORAGE_CLASS_DATA, storage);

        if (editingCategory == null)
            wnd.putExtra(NEW_CATEGORY_PASS_KEY, newCategory);
        else
            wnd.putExtra(EDITING_CATEGORY_PASS_KEY, editingCategory);

        startActivity(wnd);
        finish();
    }

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
                            RestartCreateCategoryActivity();
                        }
                    });

                    dialog.create().show();
                }else{
                    RestartCreateCategoryActivity();
                }
                break;
            default:
                break;
        }
        return true;
    }
    private boolean UnsavedData(){

        //title
        String title = fieldEntryTitle.getText().toString().trim();

        //aura
        String auraText = fieldAuraSpinner.getSelectedItem().toString();

        //start & finish wealth
        String startValue = fieldStartWealth.getText().toString().trim();
        String finishValue = fieldFinishWealth.getText().toString().trim();
        BigDecimal startWealth = startValue.equals("") ? new BigDecimal("0") : new BigDecimal(startValue);
        BigDecimal finishWealth = finishValue.equals("") ? new BigDecimal("0") : new BigDecimal(finishValue);

        //hours spent
        String hoursValue = fieldHoursSpent.getText().toString().trim();
        BigDecimal hoursSpent = hoursValue.equals("") ? new BigDecimal("0") : new BigDecimal(hoursValue);

        //kill count
        String killValue = fieldKillCount.getText().toString().trim();
        BigDecimal killCount = killValue.equals("") ? new BigDecimal("0") : new BigDecimal(killValue);

        //additional details / text
        String extraDetails = fieldExtraDetails.getText().toString().trim();

        //check each result
        return editingDataEntry == null ?
                !title.equals("") ||
                !auraText.equals(getResources().getString(R.string.text_none)) ||
                startWealth.compareTo(new BigDecimal("0")) > 0 ||
                finishWealth.compareTo(new BigDecimal("0")) > 0 ||
                hoursSpent.compareTo(new BigDecimal("0")) > 0 ||
                killCount.compareTo(new BigDecimal("0")) > 0 ||
                !extraDetails.equals("")
               ://OR
                !title.equals(editingDataEntry.getTitle()) ||
                !auraText.equals(editingDataEntry.getAura().getTitle()) ||
                startWealth.compareTo(editingDataEntry.getStartWealth()) != 0 ||
                finishWealth.compareTo(editingDataEntry.getFinishWealth()) != 0 ||
                hoursSpent.compareTo(editingDataEntry.getHoursSpent()) != 0 ||
                killCount.compareTo(editingDataEntry.getKillCount()) != 0 ||
                !extraDetails.equals(editingDataEntry.getExtraDetails());
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

        //<editor-fold defaultstate="collapsed" desc="Initialise categories">
        editingCategory = (Category) intent.getSerializableExtra(EDITING_CATEGORY_PASS_KEY);
        if (editingCategory == null)
            newCategory = (Category) intent.getSerializableExtra(NEW_CATEGORY_PASS_KEY);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Reference activity Views">
        buttonSave = findViewById(R.id.buttonSaveDataEntry);
        displayProfit = findViewById(R.id.textViewDisplayProfit);
        displayProfitPerHour = findViewById(R.id.textViewDisplayProfitPerHour);
        displayKillsPerHour = findViewById(R.id.textViewDisplayKillsPerHour);
        fieldExtraDetails = findViewById(R.id.editTextDataEntryDetails);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise spinner">
        //id / reference the view
        fieldAuraSpinner = findViewById(R.id.spinnerAuraSelector);
        //create a list to hold the strings
        final List<String> stringList = new ArrayList<>();
        //add a default / initial value
        stringList.add(getResources().getString(R.string.text_none));
        //add the remaining auras held in storage
        for (Aura aura : storage.getAuras())
            stringList.add(aura.getTitle());
        //create an adapter with the list of strings created above
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, stringList);
        //set the drop down view layout
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //set the adapter to the view spinner object
        fieldAuraSpinner.setAdapter(arrayAdapter);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise title field">
        fieldEntryTitle = findViewById(R.id.editTextDataEntryTitle);
        fieldEntryTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { CheckSaveEligibility(); }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { CheckSaveEligibility(); }
            @Override
            public void afterTextChanged(Editable editable) { CheckSaveEligibility(); }
        });
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise generic text watcher for start and finish wealth value fields">
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { CalculateDisplayValues(); }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { CalculateDisplayValues(); }
            @Override
            public void afterTextChanged(Editable editable) { CalculateDisplayValues(); }
        };
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise start wealth">
        fieldStartWealth = findViewById(R.id.editTextStartWealthValue);
        fieldStartWealth.addTextChangedListener(textWatcher);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise finish wealth">
        fieldFinishWealth = findViewById(R.id.editTextFinishWealthValue);
        fieldFinishWealth.addTextChangedListener(textWatcher);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise hours spent">
        fieldHoursSpent = findViewById(R.id.editTextHoursValue);
        fieldHoursSpent.addTextChangedListener(textWatcher);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Initialise kill count">
        fieldKillCount = findViewById(R.id.editTextKillValue);
        fieldKillCount.addTextChangedListener(textWatcher);
        //</editor-fold>

        CheckSaveEligibility();
        CalculateDisplayValues();

    }

    private void CalculateDisplayValues(){
        //start & finish wealth
        String startValue = fieldStartWealth.getText().toString().trim();
        String finishValue = fieldFinishWealth.getText().toString().trim();
        BigDecimal startWealth = startValue.equals("") ? new BigDecimal("0") : new BigDecimal(startValue);
        BigDecimal finishWealth = finishValue.equals("") ? new BigDecimal("0") : new BigDecimal(finishValue);

        //hours spent
        String hoursValue = fieldHoursSpent.getText().toString().trim();
        BigDecimal hoursSpent = hoursValue.equals("") ? new BigDecimal("1") : new BigDecimal(hoursValue).compareTo(new BigDecimal("0")) <= 0 ? new BigDecimal("1") : new BigDecimal(hoursValue);


        //kill count
        String killValue = fieldKillCount.getText().toString().trim();
        BigDecimal killCount = killValue.equals("") ? new BigDecimal("0") : new BigDecimal(killValue);

        //total profit
        //calc
        BigDecimal profit = finishWealth.subtract(startWealth);
        //add to string
        StringBuilder sb = new StringBuilder();
        sb.append("Profit: ").append(cmds.BigDecimalFormatter().format(profit));
        //display
        displayProfit.setText(sb.toString());
        //modify colour
        displayProfit.setTextColor(profit.compareTo(new BigDecimal("0")) < 0 ?
                getResources().getColor(R.color.pureRed, null) : getResources().getColor(R.color.pureBlack, null));

        //profit / hour
        //calc
        BigDecimal profitPerHour = profit.divide(hoursSpent, 2, RoundingMode.HALF_UP);
        //add to string
        sb = new StringBuilder();
        sb.append("Profit: ").append(cmds.BigDecimalFormatter().format(profitPerHour)).append(" / hour");
        //display
        displayProfitPerHour.setText(sb.toString());
        //modify colour
        displayProfitPerHour.setTextColor(profitPerHour.compareTo(new BigDecimal("0")) < 0 ?
                getResources().getColor(R.color.pureRed, null) : getResources().getColor(R.color.pureBlack, null));

        //kills / hour
        //calc
        BigDecimal killsPerHour = killCount.divide(hoursSpent, 2, RoundingMode.HALF_UP);
        //add to string
        sb = new StringBuilder();
        sb.append(cmds.BigDecimalFormatter().format(killsPerHour)).append(" / hour");
        //display
        displayKillsPerHour.setText(sb.toString());
    }

    private boolean DuplicateTitle(){

        for (DataEntry dataEntry : editingCategory == null ? newCategory.getEntries() : editingCategory.getEntries()){
            if (dataEntry.getTitle().toLowerCase().equals(fieldEntryTitle.getText().toString().toLowerCase().trim())){
                fieldEntryTitle.setError("Entry name already exists");
                return true;
            }
        }

        return false;
    }

    private void CheckSaveEligibility(){
        boolean titleExists = !fieldEntryTitle.getText().toString().trim().equals("");
        if (!titleExists)
            fieldEntryTitle.setError("Title cannot be empty");

        boolean duplicateTitle = DuplicateTitle();
        boolean eligible = titleExists && !duplicateTitle;
        int colour = eligible ? getResources().getColor(R.color.pureGreen, null) : getResources().getColor(R.color.greyedOut, null);

        buttonSave.setClickable(eligible);
        buttonSave.setBackgroundColor(colour);
        buttonSave.setTooltipText(eligible ? "Save Entry" : !titleExists ? "Need a title" : "Duplicate Title");
    }

    public void SaveEntry(View view){
        String title = fieldEntryTitle.getText().toString().trim();
        BigDecimal startWealth = new BigDecimal(fieldStartWealth.getText().toString().trim());
        BigDecimal finishWealth = new BigDecimal(fieldFinishWealth.getText().toString().trim());
        BigDecimal hoursSpent = new BigDecimal(fieldHoursSpent.getText().toString().trim());
        BigDecimal killCount = new BigDecimal(fieldKillCount.getText().toString().trim());
        String extraDetails = fieldExtraDetails.getText().toString().trim();

        Aura aura = new Aura("Not an aura");
        for (Aura a : storage.getAuras())
            if (a.getTitle().equals(fieldAuraSpinner.getSelectedItem().toString())){
                aura = a;
                break;
            }

        DataEntry dataEntry = new DataEntry(title, startWealth, finishWealth, aura, hoursSpent, killCount, extraDetails);

//will need to check for data entry editing variable to be null here

        Intent wnd = new Intent(this, CreateCategoryActivity.class);
        wnd.putExtra(STORAGE_CLASS_DATA, storage);
        if (editingCategory == null) {
            newCategory.addEntry(dataEntry);
            wnd.putExtra(NEW_CATEGORY_PASS_KEY, newCategory);
        }else {
            editingCategory.addEntry(dataEntry);
            wnd.putExtra(EDITING_CATEGORY_PASS_KEY, editingCategory);
        }

        startActivity(wnd);
        finish();

    }

}
