package com.example.profitcalcapp.Data;

import com.example.profitcalcapp.Utilities.DataTypes.Auras;

import java.math.BigDecimal;

public class DataEntry {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    //<editor-fold defaultstate="collapsed" desc="Changeable">
    private String title = "";

    private int startWealth = 0;
    private int finishWealth = 0;

    @Auras
    private int aura = Auras.VAMPYRISM;
    private int hoursSpent = 0;
    private int killCount = 0;
    private StringBuilder extraDetails = new StringBuilder();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Change Once">
    private BigDecimal profit = new BigDecimal(0.0);
    private BigDecimal profitPerKill = new BigDecimal(0.0);
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public DataEntry(){}

    public DataEntry(String title){ this.title = title; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters">
    public String getTitle(){ return this.title; }
    public int getStartWealth(){ return this.startWealth; }
    public int getFinishWealth(){ return this.finishWealth; }
    public int getAura(){ return this.aura; }
    public int getHoursSpent(){ return this.hoursSpent; }
    public int getKillCount(){ return this.killCount; }
    public StringBuilder getExtraDetails(){ return this.extraDetails; }

    public BigDecimal getProfit(){ return this.profit; }
    public BigDecimal getProfitPerKill(){ return this.profitPerKill; }
    //</editor-fold>

    //Setters


}
