package au.edu.jcu.cp3402.almmrr.appsistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Tutorial_Activity extends AppCompatActivity {
    ImageButton nextFragmentButton;
    Tut_fragment1 fragment1;
    TextView tutTextView;
    ArrayList<Fragment> fragments;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_);
         count = 0;
        fragment1 = new Tut_fragment1();
        tutTextView.findViewById(R.id.tutTextView);

        nextFragmentButton = findViewById(R.id.nextFragmentButton);
        nextFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count += 1;
                if (count == 1){

                }
            }
        });
    }

}