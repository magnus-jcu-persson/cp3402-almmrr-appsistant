package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class ContactsTutorialActivity extends AppCompatActivity {

    private TextView textViewTutorial;
    private ArrayList<Fragment> fragments;
    private ImageView settingsArrow;

    private FragmentTransaction fragmentTransaction;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_tutorial);
        count = 1;
        ContactsTutorialFragment1 fragmentContactsTutorial1 = new ContactsTutorialFragment1();
        ContactsTutorialFragment2 fragmentContactsTutorial2 = new ContactsTutorialFragment2();
        ContactsTutorialFragment3 fragmentContactsTutorial3 = new ContactsTutorialFragment3();

        System.out.println("Activity loaded.");

        fragments = new ArrayList<>(4);
        // add overview (layout.activity_calendar_tutorial)
        fragments.add(fragmentContactsTutorial1);
        fragments.add(fragmentContactsTutorial2);
        fragments.add(fragmentContactsTutorial3);

        textViewTutorial = findViewById(R.id.contacts_tutorial_information);
        settingsArrow = findViewById(R.id.settingsArrow);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contacts_fragment_container, fragments.get(0));
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
                fragmentTransaction.replace(R.id.contacts_fragment_container, fragments.get(0));
                fragmentTransaction.commit();
                settingsArrow.setVisibility(View.INVISIBLE);
                textViewTutorial.setText(R.string.contacts_stage_one);
                break;
            case 2:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contacts_fragment_container, fragments.get(1));
                fragmentTransaction.commit();
                textViewTutorial.setText(R.string.contacts_stage_two);
                break;
            case 3:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contacts_fragment_container, fragments.get(2));
                fragmentTransaction.commit();
                textViewTutorial.setText(R.string.contacts_stage_three);
                break;
        }
    }

    protected void increaseFragmentState() {
        count += 1;

        switch (count) {
            case 1:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contacts_fragment_container, fragments.get(0));
                fragmentTransaction.commit();
                settingsArrow.setVisibility(View.INVISIBLE);
                textViewTutorial.setText(R.string.contacts_stage_one);
                break;
            case 2:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contacts_fragment_container, fragments.get(1));
                fragmentTransaction.commit();
                textViewTutorial.setText(R.string.contacts_stage_two);
                break;
            case 3:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contacts_fragment_container, fragments.get(2));
                fragmentTransaction.commit();
                textViewTutorial.setText(R.string.contacts_stage_three);
                break;
            case 4:
                count = 0;
                finish();
                Intent intent = new Intent(this, ContactsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
