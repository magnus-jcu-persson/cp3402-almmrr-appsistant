package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class ContactsTutorialFragment3 extends Fragment {

    ImageView openCalendarArrow;
    ImageView contactStageView;

    public ContactsTutorialFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_tutorial_3, container, false);
        // Inflate the layout for this fragment
        openCalendarArrow = view.findViewById(R.id.contacts_indicator_arrow_3);
        contactStageView = view.findViewById(R.id.contacts_stage_three);
        return view;
    }
}
