package fr.aylan.dailycollect.driver.ui.plannification;

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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import fr.aylan.dailycollect.App;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.TourList;
import fr.aylan.dailycollect.driver.ui.addtour.AddTour;
import fr.aylan.dailycollect.driver.ui.detailtour.TourDetailActivity;
import fr.aylan.dailycollect.model.Tour;



public class HomeFragment extends Fragment {

    private ArrayList listTours = new ArrayList<Tour>();
    ListView list ;
    private TourAdapter   tourAdapter;
    AlertDialog.Builder builder ;
    public static FirebaseFirestore db ;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        // get firestore instance
        db = FirebaseFirestore.getInstance();
        // set layout
        root = inflater.inflate(R.layout.rider_fragment_home, container, false);
        // set listView adapter
        tourAdapter = new TourAdapter(getContext(), listTours);
        list = root.findViewById(R.id.list_view_tours);
        list.setAdapter(tourAdapter);


        // get tours from firebase
        listenToMultiple();

        // add action to floating button
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((App) getActivity().getApplication()).setIntent(new Intent(getContext(), TourList.class));
                ((App) getActivity().getApplication()).intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Intent intent = new Intent(getContext(), AddTour.class);
                startActivity(intent);
            }
        });


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

        // set click listeners on listView items
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TourDetailActivity.class);
                Tour tour = (Tour)listTours.get(position);
                intent.putExtra(getString(R.string.tour),tour);
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

        super.onActivityCreated(savedInstanceState);

    }

    // get Tours from firebase
    private void listenToMultiple() {

        Thread thread = new Thread() {

            @Override
            public void run() {

                ((App) (getParentFragment().getActivity().getApplication()) ).db.collection("tours")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    return;
                                }
                                listTours.clear();
                                tourAdapter.clear();

                                for (QueryDocumentSnapshot doc : value) {
                                    int id = Integer.parseInt(doc.getId());
                                    String date = doc.getString("date");
                                    String id_Rider = doc.getString("id_rider");
                                    //ArrayList<String> list_collectPoints = (ArrayList<String>) doc.get("list_collectPoints");
                                    String city = doc.getString("city");

                                    Tour tour = new Tour(id, date, id_Rider, city);
                                    //tour.setList_collectPoints((List<String>) doc.get("list_collectPoints"));
                                    List<String> group = (List<String>) doc.get("list_collectPoints");
                                    if (group != null){
                                        for (String item : group)
                                            tour.addCollectPoint(item);
                                    }

                                    listTours.add(tour);
                                }
                                tourAdapter.notifyDataSetChanged();

                            }
                        });
            }
        };

        thread.start();

    }


    private void updateList() {
        //tourAdapter.clear();
        //tourAdapter.addAll(listTours);
        tourAdapter.notifyDataSetChanged();
    }


}