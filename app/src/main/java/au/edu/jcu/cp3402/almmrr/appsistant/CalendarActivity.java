package au.edu.jcu.cp3402.almmrr.appsistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {
    private RecyclerView applicationListView;
    private RecyclerView.Adapter applicationListAdapter;
    private RecyclerView.LayoutManager applicationListManager;

    private String[] applicationList = {
            "View My Calendar",
            "Add a new Appointment",
            "Go to A Specific Date",
    };

    private Class[] applicationActivities = {
            //Intents for Android calendar

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        applicationListView = findViewById(R.id.calendar_list);

        setApplicationListView();
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



}