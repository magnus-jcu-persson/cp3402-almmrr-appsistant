package au.edu.jcu.cp3402.almmrr.AppAssist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class CalendarActivity extends AppCompatActivity {
    private RecyclerView applicationListView;
    private CalendarAdapter applicationListAdapter;
    private RecyclerView.LayoutManager applicationListManager;
    private TutorialDialog tutorialDialog;
    private String[] applicationList = {
            "View My Calendar",
            "Add a new Appointment",
            "Go to A Specific Date",
    };

    private Class<?>[] applicationActivities = {
            //Intents for Android calendar

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeMode(Theme.COLOR_BLIND);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        applicationListView = findViewById(R.id.calendar_list);
        setApplicationListView();
        tutorialDialog = new TutorialDialog(this);
        tutorialDialog.start();
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