package fr.aylan.dailycollect.ovive.ui.drivers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.model.OviveDriver;


public class DriverAdapter extends ArrayAdapter<OviveDriver> {

    Context _context;

    public DriverAdapter(@NonNull Context context, ArrayList<OviveDriver> drivers) {
        super(context, 0, drivers);
        _context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        OviveDriver driver = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(R.layout.ovive_list_item_driver, parent, false);
        }


        ((TextView)convertView.findViewById(R.id.tvDriverName)).setText(driver.getName());
        ImageView photoProfile = (ImageView)convertView.findViewById(R.id.ivProfilePhoto);

        Picasso.get()
                .load(driver.getUrlPhoto())
                //.error(R.drawable.image2)
                //.placeholder(R.drawable.ic_drawer)
                .resize(200, 200)
                .transform(new ImageTrans_CircleTransform())
                .into(photoProfile);



        return convertView;

    }


}

