package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Map;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Resources resources;
    private SharedPreferences appPreferences;
    private SharedPreferences.Editor appEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean colorBlindMode = appPreferences.getBoolean("setting:toggle_color_blind", false);
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

        Spinner spinnerTutorialLength = findViewById(R.id.option_tutorial_length);
        spinnerTutorialLength.setOnItemSelectedListener(this);

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
            String resourceName = key.substring(8);
            int resourceID = resources.getIdentifier(
                    resourceName,
                    "id",
                    getPackageName()
            );
            // -- Key starts with toggle, it's a Switch view.
            if (key.startsWith("setting:toggle")) {
                Boolean value = (Boolean) preference.getValue();
                SwitchCompat setting = findViewById(resourceID);
                if (setting != null) {
                    setting.setChecked(value);
                }
            } else if (key.startsWith("setting:option")) {
                Integer value = (Integer) preference.getValue();
                Spinner setting = findViewById(resourceID);
                if (setting != null) {
                    setting.setSelection(value);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner setting = (Spinner) adapterView;
        String preferenceKey = "setting:" + getResources().getResourceEntryName(setting.getId());
        Log.i("SavePreference", preferenceKey + ": " + i);
        appEditor.putInt(preferenceKey, i);
        appEditor.apply();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // -- PASS
    }
}