package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


public class CalendarTutorialFragment1 extends Fragment {
ImageView openCalendarArrow;
ImageView calendarScreen1;
    public CalendarTutorialFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_tutorial_1, container, false);
        // Inflate the layout for this fragment
        openCalendarArrow = view.findViewById(R.id.openCalendarArrow);
        calendarScreen1 = view.findViewById(R.id.calendarScreen1);
        return view;
    }
}