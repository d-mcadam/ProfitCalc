package com.example.profitcalcapp.Data;

import com.example.profitcalcapp.Utilities.BooleanString;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private String title = "";
    private ArrayList<DataEntry> entries = new ArrayList<>();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Category(){}

    public Category(String title){ this.title = title; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Entry list modification">
    public BooleanString addEntry(DataEntry entry){
        if (entry == null)
            return new BooleanString("Entry was null.");
        return entries.add(entry) ? new BooleanString() : new BooleanString("Unable to add entry.");
    }
    public BooleanString deleteEntry(DataEntry entry){
        if (entry == null)
            return new BooleanString("Entry was null.");
        return entries.remove(entry) ? new BooleanString() : new BooleanString("Unable to delete entry.");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters">

    //<editor-fold defaultstate="collapsed" desc="Static values">
    public String getTitle(){ return this.title; }
    public ArrayList<DataEntry> getEntries(){ return this.entries; }
    public int getEntryCount(){ return this.entries.size(); }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Totals">
    public int getTotalProfit(){
        int r = 0;
        for (DataEntry entry : entries)
            r += entry.getProfit();
        return r;
    }
    public int getTotalHoursSpent(){
        int r = 0;
        for (DataEntry entry : entries)
            r += entry.getHoursSpent();
        return r;
    }
    public int getTotalKills(){
        int r = 0;
        for (DataEntry entry : entries)
            r += entry.getKillCount();
        return r;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Averages">
    public double getAverageProfitPerHour(){
        int h = this.getTotalHoursSpent();
        return this.getTotalProfit() / (h <= 0 ? 1.0 : ((double) h));
    }
    public double getAverageKillsPerHour(){
        int h = this.getTotalHoursSpent();
        return this.getTotalKills() / (h <= 0 ? 1.0 : ((double) h));
    }
    public double getAverageProfitPerKill(){
        int k = this.getTotalKills();
        return this.getTotalProfit() / (k <= 0 ? 1.0 : ((double) k));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Useful data">

    //<editor-fold defaultstate="collapsed" desc="Highest and Lowest">
    public double getHighestGainInHour(){
        double r = 0.0;

        for (DataEntry entry : entries)
            if (entry.getProfitPerHour() > r)
                r = entry.getProfitPerHour();

        return r;
    }
    public double getLowestGainInHour(){
        double r = Double.MAX_VALUE;

        for (DataEntry entry : entries)
            if (entry.getProfitPerHour() < r)
                r = entry.getProfitPerHour();

        return r;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Averages per entry">
    public double getAverageProfitPerEntry(){
        int c = this.getEntryCount();
        return this.getTotalProfit() / (c <= 0 ? 1.0 : ((double) c));
    }
    public double getAverageHoursPerEntry(){
        int c = this.getEntryCount();
        return this.getTotalHoursSpent() / (c <= 0 ? 1.0 : ((double) c));
    }
    public double getAverageKillsPerEntry(){
        int c = this.getEntryCount();
        return this.getTotalKills() / (c <= 0 ? 1.0 : ((double) c));
    }
    public double getAverageProfitPerKillPerEntry(){
        double total = 0.0;

        for (DataEntry entry : entries)
            total += entry.getProfitPerKill();

        int c = this.getEntryCount();
        return total / (c <= 0 ? 1.0 : ((double) c));
    }
    //</editor-fold>

    //</editor-fold>

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters">
    public void setTitle(String title){ this.title = title; }
    //</editor-fold>

}
