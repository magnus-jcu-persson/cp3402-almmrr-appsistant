package au.edu.jcu.cp3402.almmrr.AppAssist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TutorialActivity extends AppCompatActivity {
    ImageButton buttonNextFragment;
    CalendarTutorialFragment1 fragmentCalendarTutorial1;
    CalendarTutorialFragment2 fragmentCalendarTutorial2;
    TextView textViewTutorial;
    ArrayList<Fragment> fragments;
    ImageView settingsArrow;
    FrameLayout fragmentContainer;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_tutorial);
        count = 0;
        fragmentContainer = findViewById(R.id.fragmentContainer);
        fragmentCalendarTutorial1 = new CalendarTutorialFragment1();
        fragmentCalendarTutorial2 = new CalendarTutorialFragment2();
        fragments = new ArrayList<>(4);
        fragments.add(fragmentCalendarTutorial1);
        fragments.add(fragmentCalendarTutorial2);

        textViewTutorial = findViewById(R.id.view_tutorial_information);
        settingsArrow = findViewById(R.id.settingsArrow);



        buttonNextFragment = findViewById(R.id.nextFragmentButton);
        buttonNextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count += 1;
                if (count == 1){
                    settingsArrow.setVisibility(View.INVISIBLE);
                    textViewTutorial.setVisibility(View.INVISIBLE);
                }else if (count == 2){
                    FragmentTransaction fragmentTransaction;
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragments.get(1));
                    fragmentTransaction.commit();
                }
            }
        });
    }

}