package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private Resources resources;
    private SharedPreferences appPreferences;
    private SharedPreferences.Editor appEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean colorBlindMode = appPreferences.getBoolean("toggle_color_blind", false);
        if (colorBlindMode) {
            setThemeMode(Theme.COLOR_BLIND);
        } else {
            setThemeMode(Theme.NORMAL);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        resources = getResources();
        appEditor = appPreferences.edit();
        appEditor.apply();

        loadPreferences();
    }

    public void backClick(View view) {
        finish();
    }

    private void loadPreferences() {
        // -- Dynamically set options based on preference key.
        Map<String, ?> preferences = appPreferences.getAll();
        for (Map.Entry<String, ?> preference : preferences.entrySet()) {
            String key = preference.getKey();
            // -- Key starts with toggle, it's a Switch view.
            if (key.startsWith("setting:toggle")) {
                Boolean value = (Boolean) preference.getValue();
                String resourceName = key.substring(8);
                int resourceID = resources.getIdentifier(
                        resourceName,
                        "id",
                        getPackageName()
                );
                SwitchCompat setting = findViewById(resourceID);
                if (setting != null) {
                    setting.setChecked(value);
                }
            }
        }
    }

    private void setThemeMode(Theme theme) {
        switch (theme) {
            case COLOR_BLIND:
                setTheme(R.style.ColorBlindMode);
                break;
            default:
            case NORMAL:
                setTheme(R.style.AppTheme);
        }
    }

    public void toggleSwitch(View view) {
        SwitchCompat setting = (SwitchCompat) view;
        String preferenceKey = "setting:" + getResources().getResourceEntryName(setting.getId());
        Log.i("SavePreference", preferenceKey + ": " + setting.isChecked());
        appEditor.putBoolean(preferenceKey, setting.isChecked());
        appEditor.apply();
    }
    public void stringArray(View view){
        //Save string with tutorial length
    }



}