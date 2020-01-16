package com.example.profitcalcapp.Utilities;

public class BooleanString {

    private final String msg;
    private final boolean result;

    public BooleanString(){
        this.msg = "success";
        this.result = true;
    }

    public BooleanString(String msg){
        this.msg = msg;
        this.result = false;
    }

}
