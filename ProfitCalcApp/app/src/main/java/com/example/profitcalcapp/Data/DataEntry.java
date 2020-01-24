package com.example.profitcalcapp.Data;

import java.io.Serializable;

public class DataEntry implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Variables">

    //<editor-fold defaultstate="collapsed" desc="Changeable">
    private boolean open = true;

    private String title = "";

    private int startWealth = 0;
    private int finishWealth = 0;

    private Aura aura = new Aura();
    private int hoursSpent = 0;
    private int killCount = 0;
    private StringBuilder extraDetails = new StringBuilder();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Change Once">
    private int profit = 0;
    private double profitPerHour = 0.0;
    private double profitPerKill = 0.0;
    //</editor-fold>

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public DataEntry(){}

    public DataEntry(String title){ this.title = title; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters">
    public boolean getOpenStatus(){ return this.open; }
    public String getTitle(){ return this.title; }
    public int getStartWealth(){ return this.startWealth; }
    public int getFinishWealth(){ return this.finishWealth; }
    public Aura getAura(){ return this.aura; }
    public int getHoursSpent(){ return this.hoursSpent; }
    public int getKillCount(){ return this.killCount; }
    public StringBuilder getExtraDetails(){ return this.extraDetails; }

    public int getProfit(){ return this.profit; }
    public double getProfitPerHour(){ return this.profitPerHour; }
    public double getProfitPerKill(){ return this.profitPerKill; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters">
    //Operation
    public void closeAndCalculate(){
        this.open = false;
        profit = finishWealth - startWealth;
        //Check for Zero values before dividing
        profitPerHour = profit / (hoursSpent <= 0 ? 1.0 : ((double) hoursSpent));
        profitPerKill = profit / (killCount <= 0 ? 1.0 : ((double) killCount));
    }

    //Regular
    public void setTitle(String title){ this.title = title; }
    public void setStartWealth(int startWealth){ this.startWealth = startWealth; }
    public void setFinishWealth(int finishWealth){ this.finishWealth = finishWealth; }
    public void setAura(Aura aura){ this.aura = aura; }
    public void setHoursSpent(int hoursSpent){ this.hoursSpent = hoursSpent; }
    public void setKillCount(int killCount){ this.killCount = killCount; }
    public void setExtraDetails(StringBuilder extraDetails){ this.extraDetails = extraDetails; }
    //</editor-fold>

}
