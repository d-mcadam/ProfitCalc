package com.example.profitcalcapp.Data;

import java.io.Serializable;

public class DefaultEntryObject implements Serializable {

    private String title = "";
    private double priceEach = 0.0;

    public DefaultEntryObject(){}

    public DefaultEntryObject(String title){ this.title = title; }

    public DefaultEntryObject(String title, double priceEach){
        this.title = title;
        this.priceEach = priceEach;
    }

    public String getTitle(){ return this.title; }
    public double getPriceEach(){ return this.priceEach; }

    public void setTitle(String title){ this.title = title; }
    public void setPriceEach(double priceEach){ this.priceEach = priceEach; }

}
