package com.example.profitcalcapp.Data;

import java.io.Serializable;

public class Aura implements Serializable {

    private String title = "";

    public Aura(){}

    public Aura(String title){ this.title = title; }

    public String getTitle(){ return this.title; }

    public void setTitle(String title){ this.title = title; }

}
