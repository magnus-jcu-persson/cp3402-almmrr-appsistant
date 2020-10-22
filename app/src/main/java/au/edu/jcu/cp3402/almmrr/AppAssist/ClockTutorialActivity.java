package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class ClockTutorialActivity extends AppCompatActivity {
    private TextView textViewTutorial;
    private ArrayList<Fragment> fragments;
    private FragmentTransaction fragmentTransaction;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_tutorial);
        count = 1;
        ClockTutorialFragment1 clockTutorialFragment1 = new ClockTutorialFragment1();
        ClockTutorialFragment2 clockTutorialFragment2 = new ClockTutorialFragment2();
        ClockTutorialFragment3 clockTutorialFragment3 = new ClockTutorialFragment3();
        ClockTutorialFragment4 clockTutorialFragment4 = new ClockTutorialFragment4();

        fragments = new ArrayList<>(5);
        fragments.add(clockTutorialFragment1);
        fragments.add(clockTutorialFragment2);
        fragments.add(clockTutorialFragment3);
        fragments.add(clockTutorialFragment4);


        textViewTutorial = findViewById(R.id.clock_tutorial_information);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.clock_fragment_container, fragments.get(0));
        fragmentTransaction.commit();

        ImageButton buttonNextFragment = findViewById(R.id.next_fragment_button);
        buttonNextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseFragmentState();
            }
        });

        ImageButton buttonPreviousFragment = findViewById(R.id.previous_fragment_button);
        buttonPreviousFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseFragmentState();
            }
        });
    }

    protected void decreaseFragmentState() {
        if (count <= 0 | count == 1) {
            count = 1;
        } else {
            count -= 1;
        }

        switch (count) {
            case 1:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.clock_fragment_container, fragments.get(0));
                fragmentTransaction.commit();
                textViewTutorial.setText(R.string.clock_stage_one);
                break;
            case 2:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.clock_fragment_container, fragments.get(1));
                fragmentTransaction.commit();
                textViewTutorial.setText(R.string.clock_stage_two);
                break;
            case 3:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.clock_fragment_container, fragments.get(2));
                fragmentTransaction.commit();
                textViewTutorial.setText(R.string.clock_stage_three);
                break;
            case 4:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.clock_fragment_container, fragments.get(3));
                fragmentTransaction.commit();
                textViewTutorial.setText(R.string.clock_stage_four);
                break;
        }
    }

    protected void increaseFragmentState() {
        count += 1;

        switch (count) {
            case 1:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.clock_fragment_container, fragments.get(0));
                fragmentTransaction.commit();
                textViewTutorial.setText(R.string.clock_stage_one);
                break;
            case 2:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.clock_fragment_container, fragments.get(1));
                fragmentTransaction.commit();
                textViewTutorial.setText(R.string.clock_stage_two);
                break;
            case 3:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.clock_fragment_container, fragments.get(2));
                fragmentTransaction.commit();
                textViewTutorial.setText(R.string.clock_stage_three);
                break;
            case 4:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.clock_fragment_container, fragments.get(3));
                fragmentTransaction.commit();
                textViewTutorial.setText(R.string.clock_stage_four);
                break;
            case 5:
                count = 0;
                finish();
                Intent intent = new Intent(this, ClockActivity.class);
                startActivity(intent);
                break;
        }
    }
}
