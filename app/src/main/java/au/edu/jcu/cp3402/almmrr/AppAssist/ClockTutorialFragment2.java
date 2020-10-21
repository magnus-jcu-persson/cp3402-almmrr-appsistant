package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ClockTutorialFragment2 extends Fragment {

    ImageView contactsStageView;
    ImageView indicatorArrow;

    public ClockTutorialFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_tutorial_2, container, false);
        // Inflate the layout for this fragment
        contactsStageView = view.findViewById(R.id.clock_stage_two);
        indicatorArrow = view.findViewById(R.id.clock_indicator_arrow_2);
        return view;
    }
}