package com.example.android.popularmovies2.Data;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private static final String PREF_SORT_SELECTED = "pref_selected";

    private static final String SHARED_PREF_NAME = "save_contents";
    private static AppPreferences mInstance;
    private final SharedPreferences pref;

    //constructor
    private AppPreferences(Context context) {
        pref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized AppPreferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppPreferences(context);
        }
        return mInstance;
    }

    public String getPrefUrlSelected() {
        return pref.getString(PREF_SORT_SELECTED, null);

    }

    public void setPrefUrlSelected(String selected) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_SORT_SELECTED, selected);
        editor.apply();
        editor.commit();
    }
}
