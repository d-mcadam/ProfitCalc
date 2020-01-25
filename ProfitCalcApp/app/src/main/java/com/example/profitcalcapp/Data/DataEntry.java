package com.example.profitcalcapp.Data;

import com.example.profitcalcapp.Utilities.BooleanString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DataEntry implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Variables">

    //<editor-fold defaultstate="collapsed" desc="Changeable">
    private boolean open = true;

    private String title;

    private BigDecimal startWealth;
    private BigDecimal finishWealth;

    private Aura aura;
    private BigDecimal hoursSpent;
    private BigDecimal killCount;
    private String extraDetails;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Change Once">
    private BigDecimal profit = new BigDecimal("0");
    private BigDecimal profitPerHour = new BigDecimal("0");
    private BigDecimal profitPerKill = new BigDecimal("0");
    //</editor-fold>

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public DataEntry(String title, BigDecimal startWealth, BigDecimal finishWealth, Aura aura,
                     BigDecimal hoursSpent, BigDecimal killCount, String extraDetails){
        this.title = title;
        this.startWealth = startWealth;
        this.finishWealth = finishWealth;
        this.aura = aura;
        this.hoursSpent = hoursSpent;
        this.killCount = killCount;
        this.extraDetails = extraDetails;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters">
    public boolean getOpenStatus(){ return this.open; }
    public String getTitle(){ return this.title; }
    public BigDecimal getStartWealth(){ return this.startWealth; }
    public BigDecimal getFinishWealth(){ return this.finishWealth; }
    public Aura getAura(){ return this.aura; }
    public BigDecimal getHoursSpent(){ return this.hoursSpent; }
    public BigDecimal getKillCount(){ return this.killCount; }
    public String getExtraDetails(){ return this.extraDetails; }

    public BigDecimal getProfit(){ return this.profit; }
    public BigDecimal getProfitPerHour(){ return this.profitPerHour; }
    public BigDecimal getProfitPerKill(){ return this.profitPerKill; }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setters">
    //Operation
    public BooleanString closeAndCalculate(){
        if (this.startWealth.compareTo(new BigDecimal("0")) <= 0)
            return new BooleanString("Cant have Start Wealth less than 0");
        if (this.finishWealth.compareTo(new BigDecimal("0")) <= 0)
            return new BooleanString("Cant have Finish Wealth less than 0");
        if (this.hoursSpent.compareTo(new BigDecimal("0")) <= 0)
            return new BooleanString("Cant have Hours Spent less than 0");

        this.open = false;
        profit = finishWealth.subtract(startWealth);
        //Check for Zero values before dividing
        profitPerHour = profit.divide(hoursSpent.compareTo(new BigDecimal("0")) <= 0 ? new BigDecimal("1") : hoursSpent, 2, RoundingMode.HALF_UP);
        profitPerKill = profit.divide(killCount.compareTo(new BigDecimal("0")) <= 0 ? new BigDecimal("1") : killCount, 2, RoundingMode.HALF_UP);
        return new BooleanString();
    }

    //Regular
    public void setTitle(String title){ this.title = title; }
    public void setStartWealth(BigDecimal startWealth){ this.startWealth = startWealth; }
    public void setFinishWealth(BigDecimal finishWealth){ this.finishWealth = finishWealth; }
    public void setAura(Aura aura){ this.aura = aura; }
    public void setHoursSpent(BigDecimal hoursSpent){ this.hoursSpent = hoursSpent; }
    public void setKillCount(BigDecimal killCount){ this.killCount = killCount; }
    public void setExtraDetails(String extraDetails){ this.extraDetails = extraDetails; }
    //</editor-fold>

}
