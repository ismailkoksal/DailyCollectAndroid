package fr.aylan.dailycollect.driver.ui.plannification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Date;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.Tour;


public class HomeFragment extends Fragment {

    private ArrayList myDataset = new ArrayList<Tour>();
    ListView list ;
    private TourAdapter   tourAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        tourAdapter = new TourAdapter(getContext(), myDataset);
        list = root.findViewById(R.id.list_view_tours);
        list.setAdapter(tourAdapter);





        return root;
    }




}