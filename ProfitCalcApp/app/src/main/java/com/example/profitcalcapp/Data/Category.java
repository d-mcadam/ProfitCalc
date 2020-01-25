package com.example.profitcalcapp.Data;

import com.example.profitcalcapp.Utilities.BooleanString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Category implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private String title = "";
    private final ArrayList<DataEntry> entries = new ArrayList<>();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Category(){}

    public Category(String title){ this.title = title; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Entry list modification">
    public BooleanString addEntry(DataEntry entry){
        if (entry == null)
            return new BooleanString("Entry was null.");
        return entries.add(entry) ? OrderAndReturn() : new BooleanString("Unable to add entry.");
    }
    private BooleanString OrderAndReturn(){
        Collections.sort(entries, new Comparator<DataEntry>() {
            @Override
            public int compare(DataEntry o1, DataEntry o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
        return new BooleanString();
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
    public BigDecimal getTotalProfit(){
        BigDecimal r = new BigDecimal("0");
        for (DataEntry entry : entries)
            r = r.add(entry.getProfit());
        return r;
    }
    public BigDecimal getTotalHoursSpent(){
        BigDecimal r = new BigDecimal("0");
        for (DataEntry entry : entries)
            r = r.add(entry.getHoursSpent());
        return r;
    }
    public BigDecimal getTotalKills(){
        BigDecimal r = new BigDecimal("0");
        for (DataEntry entry : entries)
            r = r.add(entry.getKillCount());
        return r;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Averages">
    public BigDecimal getAverageProfitPerHour(){
        BigDecimal hoursSpent = this.getTotalHoursSpent();
        return this.getTotalProfit().divide(
                hoursSpent.compareTo(new BigDecimal("0")) <= 0 ? new BigDecimal("1") : hoursSpent,
                2, RoundingMode.HALF_UP);
    }
    public BigDecimal getAverageKillsPerHour(){
        BigDecimal hoursSpent = this.getTotalHoursSpent();
        return this.getTotalKills().divide(
                hoursSpent.compareTo(new BigDecimal("0")) <= 0 ? new BigDecimal("1") : hoursSpent,
                2, RoundingMode.HALF_UP);
    }
    public BigDecimal getAverageProfitPerKill(){
        BigDecimal kills = this.getTotalKills();
        return this.getTotalProfit().divide(
                kills.compareTo(new BigDecimal("0")) <= 0 ? new BigDecimal("1") : kills,
                2, RoundingMode.HALF_UP);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Useful data">

    //<editor-fold defaultstate="collapsed" desc="Highest and Lowest">
    public BigDecimal getHighestGainInHour(){
        BigDecimal r = new BigDecimal("0");

        for (DataEntry entry : entries)
            if (entry.getProfitPerHour().compareTo(r) > 0)
                r = entry.getProfitPerHour();

        return r;
    }
    public BigDecimal getLowestGainInHour(){
        BigDecimal r = new BigDecimal("-1");

        for (DataEntry entry : entries)
            if (r.compareTo(new BigDecimal("0")) < 0 || entry.getProfitPerHour().compareTo(r) < 0)
                r = entry.getProfitPerHour();

        return r;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Averages per entry">
    public BigDecimal getAverageProfitPerEntry(){
        BigDecimal count = new BigDecimal(this.getEntryCount());
        return this.getTotalProfit().divide(count.compareTo(new BigDecimal("0")) <= 0 ? new BigDecimal("1") : count);
    }
    public BigDecimal getAverageHoursPerEntry(){
        BigDecimal count = new BigDecimal(this.getEntryCount());
        return this.getTotalHoursSpent().divide(count.compareTo(new BigDecimal("0")) <= 0 ? new BigDecimal("1") : count);
    }
    public BigDecimal getAverageKillsPerEntry(){
        BigDecimal count = new BigDecimal(this.getEntryCount());
        return this.getTotalKills().divide(count.compareTo(new BigDecimal("0")) <= 0 ? new BigDecimal("1") : count);
    }
    public BigDecimal getAverageProfitPerKillPerEntry(){
        BigDecimal total = new BigDecimal("0");

        for (DataEntry entry : entries)
            total = total.add(entry.getProfitPerKill());

        BigDecimal count = new BigDecimal(this.getEntryCount());
        return total.divide(count.compareTo(new BigDecimal("0")) <= 0 ? new BigDecimal("1") : count);
    }
    //</editor-fold>

    //</editor-fold>

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters">
    public void setTitle(String title){ this.title = title; }
    //</editor-fold>

}
