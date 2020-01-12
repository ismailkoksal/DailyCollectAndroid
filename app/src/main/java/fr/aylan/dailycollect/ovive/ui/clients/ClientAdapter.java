package fr.aylan.dailycollect.ovive.ui.clients;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.Client;


public class ClientAdapter extends ArrayAdapter<Client> {

    Context _context;

    public ClientAdapter(@NonNull Context context, ArrayList<Client> clients) {
        super(context, 0, clients);
        _context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Client client = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(R.layout.ovive_list_item_rider, parent, false);
        }


        ((TextView)convertView.findViewById(R.id.tvRiderName)).setText(client.getName());


        return convertView;

    }


}

