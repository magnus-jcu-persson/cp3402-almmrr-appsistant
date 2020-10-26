package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    private SharedPreferences appPreferences;

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
        setContentView(R.layout.activity_help);
    }

    public void backClick(View view) {
        finish();
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

}