package com.samuelbernard147.moviecataloguelocalstorage;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsPreference {
    private static final String PREFS_NAME = "settings_pref";

    private static final String LANG = "lang";

    private final SharedPreferences preferences;

    public SettingsPreference(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setLang(String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LANG, value);
        editor.apply();
    }

    //    Nilai default dari bahasanya adalah bahasa Indonesia
    public String getLang() {
        return preferences.getString(LANG, "in");
    }
}
