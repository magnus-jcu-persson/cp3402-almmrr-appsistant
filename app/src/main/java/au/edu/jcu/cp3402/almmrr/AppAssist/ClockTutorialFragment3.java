package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class ClockTutorialFragment3 extends Fragment {

    ImageView contactsStageView;
    ImageView indicatorArrow;

    public ClockTutorialFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clock_tutorial_3, container, false);
        // Inflate the layout for this fragment
        contactsStageView = view.findViewById(R.id.clock_stage_three);
        indicatorArrow = view.findViewById(R.id.clock_indicator_arrow_3);
        return view;
    }
}
