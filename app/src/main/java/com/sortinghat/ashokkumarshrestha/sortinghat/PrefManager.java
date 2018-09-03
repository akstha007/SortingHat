package com.sortinghat.ashokkumarshrestha.sortinghat;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lincoln on 05/05/16.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "uchihaashuke-pottermore";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setPoints(String level, int point) {
        editor.putInt(level, point);
        editor.commit();
    }

    public int getPoints(String level) {
        return pref.getInt(level, 0);
    }

    public void setStringValue(String prefName, String quote) {
        editor.putString(prefName, quote);
        editor.commit();
    }

    public String getStringValue(String prefName) {
        return pref.getString(prefName, "");
    }


}
