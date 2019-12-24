package fr.aylan.dailycollect.driver.ui.plannification;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Date;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.Tour;
import fr.aylan.dailycollect.driver.ui.detailtour.TourDetailActivity;


public class HomeFragment extends Fragment {

    private ArrayList myDataset = new ArrayList<Tour>();
    ListView list ;
    private TourAdapter   tourAdapter;
    AlertDialog.Builder builder ;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_home, container, false);

        tourAdapter = new TourAdapter(getContext(), myDataset);
        list = root.findViewById(R.id.list_view_tours);
        list.setAdapter(tourAdapter);


        String[] array = new String[]{getString(R.string.modify),getString(R.string.delete), getString(R.string.start_tour)};

        builder = new AlertDialog.Builder(getActivity());

        builder.setItems(array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TourDetailActivity.class);
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                builder.show();

                return false;
            }
        });

        super.onActivityCreated(savedInstanceState);

    }
}