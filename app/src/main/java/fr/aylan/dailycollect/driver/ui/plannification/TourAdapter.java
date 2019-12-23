package fr.aylan.dailycollect.driver.ui.plannification;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.Tour;


public class TourAdapter  extends ArrayAdapter<Tour> {


    public TourAdapter(@NonNull Context context, ArrayList<Tour> tours) {
        super(context, 0, tours);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Tour tour = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_tournee, parent, false);
        }


        ((TextView)convertView.findViewById(R.id.tvCollectPointName)).setText(tour.getCollectPointName());
        ((TextView)convertView.findViewById(R.id.tvDate)).setText(tour.getDate().toString());
        ((TextView)convertView.findViewById(R.id.tvCity)).setText(tour.getCity());


        return convertView;

    }


}

