package com.example.profitcalcapp.Utilities;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DataTypes {

    @IntDef({Auras.NONE, Auras.VAMPYRISM, Auras.PENANCE, Auras.EQUILIBRIUM, Auras.SHARPSHOOTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Auras{
        int NONE = 0;
        int VAMPYRISM = 1;
        int PENANCE = 2;
        int EQUILIBRIUM = 3;
        int SHARPSHOOTER = 4;
    }

}
