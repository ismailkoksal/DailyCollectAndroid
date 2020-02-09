package fr.aylan.dailycollect.ovive.ui.drivers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import fr.aylan.dailycollect.App;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.model.Client;
import fr.aylan.dailycollect.model.OviveDriver;


public class DriversFragment extends Fragment {

    private ArrayList listDrivers = new ArrayList<Client>();
    ListView list ;
    private DriverAdapter driverAdapter;
    AlertDialog.Builder builder ;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.ovive_fragment_driver_list, container, false);

        driverAdapter = new DriverAdapter(getContext(), listDrivers);
        list = root.findViewById(R.id.listViewDrivers);
        list.setAdapter(driverAdapter);


        listenToMultiple();


        String[] array = new String[]{getString(R.string.modify),getString(R.string.delete)};

        builder = new AlertDialog.Builder(getActivity());

        builder.setItems(array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddDriver.class);
                startActivity(intent);
            }
        });

        return root;
    }


    // get list of drivers from firebase
    private void listenToMultiple() {

        Thread thread = new Thread() {

            @Override
            public void run() {

                ((App) (getParentFragment().getActivity().getApplication()) ).db.collection("drivers")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    return;
                                }
                                listDrivers.clear();
                                driverAdapter.clear();

                                for (QueryDocumentSnapshot doc : value) {
                                    String id = doc.getId();

                                    String employement_date = doc.getString("employement_date");
                                    String name = doc.getString("name");
                                    String mail = doc.getString("mail");
                                    String tel = doc.getString("tel");
                                    String city = doc.getString("city");
                                    String urlPhoto = doc.getString("urlPhoto");


                                    OviveDriver driver = new OviveDriver(id, employement_date, name, mail, tel, city, urlPhoto);

                                    listDrivers.add(driver);
                                }
                                driverAdapter.notifyDataSetChanged();

                            }
                        });
            }
        };

        thread.start();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // set click listeners on drivers' listView items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DriverDetails.class);
                OviveDriver driver = (OviveDriver) listDrivers.get(position);
                intent.putExtra(getString(R.string.driver),driver);
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                builder.show();
                return true;
            }
        });

    }
}