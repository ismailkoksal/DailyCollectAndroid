package fr.aylan.dailycollect.driver.ui.listcollectpoints;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.CollectPoint;


public class CollectPointAdapter extends ArrayAdapter<CollectPoint> {


    public CollectPointAdapter(@NonNull Context context, ArrayList<CollectPoint> collectPints) {
        super(context, 0, collectPints);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        CollectPoint collectPoint = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rider_list_item_collect_point, parent, false);
        }


        ((TextView)convertView.findViewById(R.id.tvTourName)).setText(collectPoint.getName());
        ((TextView)convertView.findViewById(R.id.tvApproximativeTime)).setText(collectPoint.getApproximativeTime());


        return convertView;

    }


}

