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

public class GalleryTutorialActivity extends AppCompatActivity {

    private ArrayList<Fragment> fragments;
    private FragmentTransaction fragmentTransaction;
    private int count;
    GalleryTutorialFragment1 galleryTutorialFragment1;
    GalleryTutorialFragment2 galleryTutorialFragment2;
    GalleryTutorialFragment3 galleryTutorialFragment3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_tutorial);
        count = 1;
        galleryTutorialFragment1 = new GalleryTutorialFragment1();
        galleryTutorialFragment2 = new GalleryTutorialFragment2();
        galleryTutorialFragment3 = new GalleryTutorialFragment3();
        System.out.println("Activity loaded.");

        fragments = new ArrayList<>(4);
        // add overview (layout.activity_calendar_tutorial)
        fragments.add(galleryTutorialFragment1);
        fragments.add(galleryTutorialFragment2);
        fragments.add(galleryTutorialFragment3);


        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.gallery_fragment_container, fragments.get(0));
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
                fragmentTransaction.replace(R.id.gallery_fragment_container, fragments.get(0));
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.gallery_fragment_container, fragments.get(1));
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.gallery_fragment_container, fragments.get(2));
                fragmentTransaction.commit();
                break;
        }
    }

    protected void increaseFragmentState() {
        count += 1;

        switch (count) {
            case 1:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.gallery_fragment_container, fragments.get(0));
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.gallery_fragment_container, fragments.get(1));
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.gallery_fragment_container, fragments.get(2));
                fragmentTransaction.commit();
                break;
            case 4:
                count = 0;
                finish();
                Intent intent = new Intent(this, GalleryActivity.class);
                startActivity(intent);
                break;
        }
    }
}
