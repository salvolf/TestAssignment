package io.backbase.backbasetestassignment.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class SharedPrefsHelper {
    //Not enough time to store in preference the user setting
    private final static String IS_METRIC = "is_metric_system_selected";
    @NonNull private final SharedPreferences sharedPreferences;

    public SharedPrefsHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("BB_TEST", Context.MODE_PRIVATE);
    }


    public void setPreferredUnitSystem(boolean isMetic) {
        sharedPreferences.edit().putBoolean(IS_METRIC, isMetic).apply();
    }
}
