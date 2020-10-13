package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import au.edu.jcu.cp3402.almmrr.AppAssist.CalendarAdapter;
import au.edu.jcu.cp3402.almmrr.AppAssist.R;
import au.edu.jcu.cp3402.almmrr.AppAssist.Theme;
import au.edu.jcu.cp3402.almmrr.AppAssist.TutorialDialog;

public class ContactsActivity extends AppCompatActivity {
    private RecyclerView applicationListView;
    private ContactsAdapter applicationListAdapter;
    private RecyclerView.LayoutManager applicationListManager;
    private TutorialDialog tutorialDialog;
    private String[] applicationList = {
            "View My Contacts",
            "Add new Contact",
            "Find a Contact",
    };

    private Class<?>[] applicationActivities = {
            //Intents for Android contacts

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String ACTIVITY_NAME = "Contacts";
        SharedPreferences appPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean colorBlindMode = appPreferences.getBoolean("settingColorBlind", false);
        if (colorBlindMode) {
            setThemeMode(Theme.COLOR_BLIND);
        } else {
            setThemeMode(Theme.NORMAL);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        applicationListView = findViewById(R.id.view_contacts_list);
        setApplicationListView();
        tutorialDialog = new TutorialDialog(this);
        tutorialDialog.start(ACTIVITY_NAME);
    }

    private void setApplicationListView() {
        applicationListView.setHasFixedSize(true);

        applicationListManager = new LinearLayoutManager(this);
        applicationListView.setLayoutManager(applicationListManager);

        applicationListAdapter = new ContactsAdapter(
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