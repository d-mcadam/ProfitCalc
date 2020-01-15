package com.example.profitcalcapp.Data;

import java.util.ArrayList;

public class Category {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private String title = "";
    private ArrayList<DataEntry> entries = new ArrayList<>();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Category(){}

    public Category(String title){
        this.title = title;
    }

    public Category(String title, ArrayList<DataEntry> entries){
        this.title = title;
        this.entries = entries;
    }
    //</editor-fold>

    public Integer EntryCount(){ return this.entries.size(); }

    public Integer TotalProfit(){
        Integer r = 0;

        for (DataEntry entry :
                entries) {

        }
    }

}
