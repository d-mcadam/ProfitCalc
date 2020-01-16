package com.example.profitcalcapp.Utilities;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DataTypes {

    @IntDef({Auras.None, Auras.Vampyrism, Auras.Penance, Auras.Equilibrium, Auras.Sharpshooter})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Auras{
        int None = 0;
        int Vampyrism = 1;
        int Penance = 2;
        int Equilibrium = 3;
        int Sharpshooter = 4;
    }

}
