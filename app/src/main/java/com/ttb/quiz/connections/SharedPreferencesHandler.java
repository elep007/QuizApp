package com.ttb.quiz.connections;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHandler {
    private Activity activity;
    private Context context;
    private SharedPreferences sharedpref;

    public SharedPreferencesHandler(Activity activity) {
        this.activity = activity;
        sharedpref = activity.getSharedPreferences("quiz", Context.MODE_PRIVATE);
    }

    public SharedPreferencesHandler(Context context) {
        this.context = context;
        sharedpref = context.getSharedPreferences("quiz", Context.MODE_PRIVATE);
    }

    public void WritePreference(String key, String value) {
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void WritePreference(String key, int value) {
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void ClearPreferences() {
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.clear();
        editor.commit();
    }

    public String ReadPreferences(String key) {
        return sharedpref.getString(key, null);
    }
}
