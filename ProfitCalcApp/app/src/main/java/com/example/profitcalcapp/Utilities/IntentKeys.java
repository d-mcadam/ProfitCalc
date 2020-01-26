package com.example.profitcalcapp.Utilities;

public class IntentKeys {

    //for the entirety of the app data
    public static final String APP_STORAGE_DATA = "APP_STORAGE_DATA";

    //for passing storage class only
    public static final String STORAGE_CLASS_DATA = "STORAGE_CLASS_DATA";

    //for editing an aura
    public static final String EDIT_AURA_KEY = "EDIT_AURA_KEY";

    //for passing string values
    public static final String STRING_PASS_KEY = "STRING_PASS_KEY";

    //for passing category from category activity to create category activity for editing an activity
    public static final String EDITING_CATEGORY_PASS_KEY = "EDITING_CATEGORY_PASS_KEY";

    //for passing category from create category to create entry activity for creating a new entry
    public static final String NEW_CATEGORY_PASS_KEY = "NEW_CATEGORY_PASS_KEY";

    //for passing a reference to the original category object that was being entered for appropriate unsaved data check
    public static final String TEMPORARY_CATEGORY_PASS_KEY = "TEMPORARY_CATEGORY_PASS_KEY";

    //for passing data entry from create category to create entry activity for editing an existing entry
    public static final String DATA_ENTRY_PASS_KEY = "DATA_ENTRY_PASS_KEY";

}
