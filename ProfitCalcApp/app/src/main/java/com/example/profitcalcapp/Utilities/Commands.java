package com.example.profitcalcapp.Utilities;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.profitcalcapp.Data.Aura;
import com.example.profitcalcapp.Data.Category;
import com.example.profitcalcapp.Data.DataEntry;
import com.example.profitcalcapp.Data.Storage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;
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

    /**
     * Checks the specified aura satisfies the search query.
     * @param aura  The aura being checked.
     * @param query The string search query.
     * @return      True if the Aura matches the search query in any way, false otherwise.
     */
    public boolean SatisfiesSearchQuery(Aura aura, String query){
        return query.toLowerCase().trim().equals("") ||
                aura.getTitle().toLowerCase().trim().contains(query.toLowerCase().trim());
    }

    /**
     * Checks the specified category satisfies the search query
     * @param category  The category being checked
     * @param query     The string search query
     * @return          True if the Category matches the search query, false otherwise.
     */
    public boolean SatisfiesSearchQuery(Category category, String query){
        boolean queryContainsLetters = query.matches(".*[a-zA-Z].*");
        return query.toLowerCase().trim().equals("") ||
                category.getTitle().toLowerCase().trim().contains(query.toLowerCase().trim()) ||
                category.getEntries().stream().anyMatch(item ->
                        item.getTitle().toLowerCase().trim().contains(query.toLowerCase().trim())) ||
                Objects.equals(category.getEntryCount(), queryContainsLetters ? null : Integer.parseInt(query));
    }

    /**
     * Checks the specified data entry satisfies the search query
     * @param dataEntry The DataEntry being checked
     * @param query     The string search query
     * @return          True if the DataEntry matches the search query, false otherwise.
     */
    public boolean SatisfiesSearchQuery(DataEntry dataEntry, String query){
        boolean queryContainsLetters = query.matches(".*[a-zA-Z].*");
        return query.toLowerCase().trim().equals("") ||
                dataEntry.getTitle().toLowerCase().trim().contains(query.toLowerCase().trim()) ||
                dataEntry.getAura().getTitle().toLowerCase().trim().contains(query.toLowerCase().trim()) ||
                dataEntry.getExtraDetails().toLowerCase().trim().contains(query.toLowerCase().trim()) ||
                Objects.equals(dataEntry.getStartWealth(), queryContainsLetters ? null : new BigDecimal(query)) ||
                Objects.equals(dataEntry.getFinishWealth(), queryContainsLetters ? null : new BigDecimal(query)) ||
                Objects.equals(dataEntry.getHoursSpent(), queryContainsLetters ? null : new BigDecimal(query)) ||
                Objects.equals(dataEntry.getKillCount(), queryContainsLetters ? null : new BigDecimal(query)) ||
                Objects.equals(dataEntry.getProfit(), queryContainsLetters ? null : new BigDecimal(query)) ||
                Objects.equals(dataEntry.getProfitPerHour(), queryContainsLetters ? null : new BigDecimal(query)) ||
                Objects.equals(dataEntry.getKillsPerHour(), queryContainsLetters ? null : new BigDecimal(query)) ||
                Objects.equals(dataEntry.getProfitPerKill(), queryContainsLetters ? null : new BigDecimal(query));
    }

    /**
     * Custom formatter for BigDecimal values
     * @return  a formatter used on BigDecimals to add commas
     */
    public DecimalFormat BigDecimalFormatter(){ return new DecimalFormat("###,###.##"); }

}
