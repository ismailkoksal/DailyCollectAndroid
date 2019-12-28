package fr.aylan.dailycollect.driver.ui.detailtour;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.CollectPoint;
import fr.aylan.dailycollect.driver.ui.listcollectpoints.ListCollectPoints;


public class TourDetail extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_detail_tour, container, false);
        
        ArrayList<CollectPoint> temp = new ArrayList<>();

        ListCollectPoints listCollectPoints = new ListCollectPoints(getContext(),temp);

        ConstraintLayout collectPointsHolder = root.findViewById(R.id.pontsCollectholder);
        collectPointsHolder.addView(listCollectPoints);


        return root;
    }
}