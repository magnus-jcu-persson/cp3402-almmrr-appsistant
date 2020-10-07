package au.edu.jcu.cp3402.almmrr.AppAssist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class TutorialActivity extends AppCompatActivity {
    ImageButton buttonNextFragment;
    CalendarTutorialFragment1 fragmentCalendarTutorial1;
    TextView textViewTutorial;
    ArrayList<Fragment> fragments;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_tutorial);
        count = 0;
        fragmentCalendarTutorial1 = new CalendarTutorialFragment1();
        textViewTutorial = findViewById(R.id.view_tutorial_information);

        buttonNextFragment = findViewById(R.id.nextFragmentButton);
        buttonNextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count += 1;
                if (count == 1){

                }
            }
        });
    }

}