package fr.aylan.dailycollect.driver.ui.plannification;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.model.Tour;


public class TourAdapter  extends ArrayAdapter<Tour> {

    Context _context;

    public TourAdapter(@NonNull Context context, ArrayList<Tour> tours) {
        super(context, 0, tours);
        _context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Tour tour = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(R.layout.rider_list_item_tournee, parent, false);
        }


        ((TextView)convertView.findViewById(R.id.tvTourName)).setText(getContext().getString(R.string.tour)+" "+(tour.getId()+1)+" ");
        ((TextView)convertView.findViewById(R.id.tvDate)).setText(tour.getDate());
        ((TextView)convertView.findViewById(R.id.tvCity)).setText(tour.getCity());


        return convertView;

    }


}

