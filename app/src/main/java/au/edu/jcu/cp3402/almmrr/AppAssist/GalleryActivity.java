package au.edu.jcu.cp3402.almmrr.AppAssist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;


public class GalleryActivity extends AppCompatActivity {
    private RecyclerView applicationListView;
    private GalleryAdapter applicationListAdapter;
    private RecyclerView.LayoutManager applicationListManager;
    private TutorialDialog tutorialDialog;
    private String[] applicationList = {
            "View Gallery"
    };

    private Class<?>[] applicationActivities = {
            //Intents for gallery activity

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String ACTIVITY_NAME = "Gallery";
        SharedPreferences appPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean colorBlindMode = appPreferences.getBoolean("setting:toggle_color_blind", false);
        if (colorBlindMode) {
            setThemeMode(Theme.COLOR_BLIND);
        } else {
            setThemeMode(Theme.NORMAL);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        applicationListView = findViewById(R.id.view_gallery_list);
        setApplicationListView();

//        int tutorialOption = appPreferences.getInt("setting:option_tutorial_length", -1);
//        tutorialDialog = new TutorialDialog(this, ACTIVITY_NAME);
//        if (tutorialOption == -1) {
//            tutorialDialog.start();
//        } else {
//            tutorialDialog.chooseTutorialOption(tutorialOption);
//        }
    }

    private void setApplicationListView() {
        applicationListView.setHasFixedSize(true);

        applicationListManager = new LinearLayoutManager(this);
        applicationListView.setLayoutManager(applicationListManager);

        applicationListAdapter = new GalleryAdapter(this, applicationList, applicationActivities, applicationListView);
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