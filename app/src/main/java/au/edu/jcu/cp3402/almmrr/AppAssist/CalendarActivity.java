package au.edu.jcu.cp3402.almmrr.AppAssist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

public class CalendarActivity extends AppCompatActivity {
    private RecyclerView applicationListView;
    private CalendarAdapter applicationListAdapter;
    private RecyclerView.LayoutManager applicationListManager;
    private TutorialDialog tutorialDialog;
    private String[] applicationList = {
            "View Calendar",
            "Add Event",
            "Go to Date",
    };

    private Class<?>[] applicationActivities = {
            //Intents for Android calendar

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String ACTIVITY_NAME = "Calendar";
        SharedPreferences appPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean colorBlindMode = appPreferences.getBoolean("setting:toggle_color_blind", false);
        if (colorBlindMode) {
            setThemeMode(Theme.COLOR_BLIND);
        } else {
            setThemeMode(Theme.NORMAL);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        applicationListView = findViewById(R.id.view_calendar_list);
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

        applicationListAdapter = new CalendarAdapter(
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