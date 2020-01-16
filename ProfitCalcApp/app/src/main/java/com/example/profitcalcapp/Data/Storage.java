package com.example.profitcalcapp.Data;

import com.example.profitcalcapp.Utilities.BooleanString;

import java.util.ArrayList;

public class Storage {

    //<editor-fold defaultstate="collapsed" desc="Categories">
    private ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Category> getCategories(){ return this.categories; }

    public BooleanString addCategory(Category category){
        if (category == null)
            return new BooleanString("Category was null.");
        return this.categories.add(category) ? new BooleanString() : new BooleanString("Unable to add Category.");
    }
    public BooleanString deleteCategory(Category category){
        if (category == null)
            return new BooleanString("Category was null.");
        return this.categories.remove(category) ? new BooleanString() : new BooleanString("Unable to delete Category.");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Auras">
    private ArrayList<Aura> auras = new ArrayList<>();

    public ArrayList<Aura> getAuras(){ return this.auras; }

    public BooleanString addAura(Aura aura){
        if (aura == null)
            return new BooleanString("Aura was null.");
        return this.auras.add(aura) ? new BooleanString() : new BooleanString("Unable to add Aura.");
    }
    public BooleanString deleteAura(Aura aura){
        if (aura == null)
            return new BooleanString("Aura was null.");
        return this.auras.remove(aura) ? new BooleanString() : new BooleanString("Unable to delete Aura.");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Default Entry Objects">
    private ArrayList<DefaultEntryObject> defaultEntryObjects = new ArrayList<>();

    public ArrayList<DefaultEntryObject> getDefaultEntryObjects(){ return this.defaultEntryObjects; }

    public BooleanString addDefaultEntryObject(DefaultEntryObject defaultEntryObject){
        if (defaultEntryObject == null)
            return new BooleanString("Default Entry Object was null.");
        return this.defaultEntryObjects.add(defaultEntryObject) ?
                new BooleanString() : new BooleanString("Unable to add Default Entry Object.");
    }

    public BooleanString deleteDefaultEntryObject(DefaultEntryObject defaultEntryObject){
        if (defaultEntryObject == null)
            return new BooleanString("Default Entry Object was null.");
        return this.defaultEntryObjects.remove(defaultEntryObject) ?
                new BooleanString() : new BooleanString("Unable to delete Default Entry Object.");
    }
    //</editor-fold>

}
