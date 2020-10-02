package au.edu.jcu.cp3402.almmrr.AppAssist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.*;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private RecyclerView applicationListView;
    private ApplicationAdapter applicationListAdapter;
    private LayoutManager applicationListManager;

    private SharedPreferences appPreferences;
    private boolean colorBlindMode;

    private String[] applicationList = {
            "Calendar"
    };

    private Class<?>[] applicationActivities = {
            CalendarActivity.class
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkColorBlindMode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applicationListView = findViewById(R.id.application_list);

        setApplicationListView();

        SwitchCompat toggle = findViewById(R.id.toggleColorBlind);
        toggle.setChecked(colorBlindMode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        applicationListManager.removeAllViews();
        applicationListView.setAdapter(applicationListAdapter);
    }

    private void setApplicationListView() {
        applicationListView.setHasFixedSize(true);

        applicationListManager = new LinearLayoutManager(this);
        applicationListView.setLayoutManager(applicationListManager);

        applicationListAdapter = new ApplicationAdapter(
                this,
                applicationList,
                applicationActivities,
                applicationListView
        );
        applicationListView.setAdapter(applicationListAdapter);
    }

    private void checkColorBlindMode() {
        appPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        colorBlindMode = appPreferences.getBoolean("settingColorBlind", false);
        if (colorBlindMode) {
            setThemeMode(Theme.COLOR_BLIND);
        } else {
            setThemeMode(Theme.NORMAL);
        }
    }

    public void toggleColorBlindMode(View view) {

        SwitchCompat toggle = (SwitchCompat) view;

        SharedPreferences.Editor appEditor = appPreferences.edit();
        appEditor.putBoolean("settingColorBlind", toggle.isChecked());
        appEditor.apply();

        recreate();
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