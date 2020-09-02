package au.edu.jcu.cp3402.almmrr.appsistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.*;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private RecyclerView applicationListView;
    private Adapter applicationListAdapter;
    private LayoutManager applicationListManager;

    private String[] applicationList = {
            "Calendar"
    };

    private Class[] applicationActivities = {
            CalendarActivity.class
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        applicationListView = findViewById(R.id.application_list);

        setApplicationListView();

    }

    private void setApplicationListView() {
        applicationListView.setHasFixedSize(true);

        applicationListManager = new LinearLayoutManager(this);
        applicationListView.setLayoutManager(applicationListManager);

        applicationListAdapter = new ApplicationAdapter(
                this,
                applicationList,
                applicationActivities
        );
        applicationListView.setAdapter(applicationListAdapter);

    }
}