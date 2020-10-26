package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


public class GalleryTutorialFragment3 extends Fragment {

    ImageView indicatorArrow;

    public GalleryTutorialFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_tutorial_1, container, false);
        // Inflate the layout for this fragment
        indicatorArrow = view.findViewById(R.id.calendar_indicator_arrow_1);
        return view;
    }
}