package au.edu.jcu.cp3402.almmrr.AppAssist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

public class ClockActivity extends AppCompatActivity {
    private RecyclerView applicationListView;
    private ClockAdapter applicationListAdapter;
    private RecyclerView.LayoutManager applicationListManager;
    private TutorialDialog tutorialDialog;
    private String[] applicationList = {
            "Open My Clock"
    };

    private Class<?>[] applicationActivities = {
            //Intents for Android contacts

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String ACTIVITY_NAME = "Clock";
        SharedPreferences appPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean colorBlindMode = appPreferences.getBoolean("settingColorBlind", false);
        if (colorBlindMode) {
            setThemeMode(Theme.COLOR_BLIND);
        } else {
            setThemeMode(Theme.NORMAL);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        applicationListView = findViewById(R.id.view_clock_list);
        setApplicationListView();

        int tutorialOption = appPreferences.getInt("setting:option_tutorial_length", -1);
        tutorialDialog = new TutorialDialog(this, ACTIVITY_NAME);
        if (tutorialOption == -1) {
            tutorialDialog.start();
        } else {
            tutorialDialog.chooseTutorialOption(tutorialOption);
        }
    }

    private void setApplicationListView() {
        applicationListView.setHasFixedSize(true);

        applicationListManager = new LinearLayoutManager(this);
        applicationListView.setLayoutManager(applicationListManager);

        applicationListAdapter = new ClockAdapter(
                this,
                applicationList,
                applicationActivities,
                applicationListView

        );
        applicationListView.setAdapter(applicationListAdapter);
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