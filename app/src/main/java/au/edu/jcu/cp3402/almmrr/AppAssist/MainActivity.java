package au.edu.jcu.cp3402.almmrr.AppAssist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.*;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private RecyclerView applicationListView;
    private ApplicationAdapter applicationListAdapter;
    private LayoutManager applicationListManager;

    private String[] applicationList = {
            "Calendar"
    };

    private Class<?>[] applicationActivities = {
            CalendarActivity.class
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeMode(Theme.COLOR_BLIND);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applicationListView = findViewById(R.id.application_list);

        setApplicationListView();
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