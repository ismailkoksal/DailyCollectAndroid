package fr.aylan.dailycollect.customer.ui.collects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.model.Collect;

public class CollectsAdapter extends ArrayAdapter<Collect> {

    public CollectsAdapter(Context context, List<Collect> collects) {
        super(context, 0, collects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        Collect collect = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.collect_item, parent, false);
        }
        // Lookup view for data population
        TextView collectDate = convertView.findViewById(R.id.collect_date);
        TextView collectTime = convertView.findViewById(R.id.collect_time);
        // Populate the data into the template view using the data object
        collectDate.setText(collect.getDate());
        collectTime.setText(collect.getTime());
        // Return the completed view to render on screen
        return convertView;
    }
}
