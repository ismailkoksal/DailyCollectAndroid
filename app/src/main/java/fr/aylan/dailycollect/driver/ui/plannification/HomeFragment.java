package fr.aylan.dailycollect.driver.ui.plannification;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.Tour;
import fr.aylan.dailycollect.driver.ui.detailtour.TourDetailActivity;



public class HomeFragment extends Fragment {

    private ArrayList listTours = new ArrayList<Tour>();
    ListView list ;
    private TourAdapter   tourAdapter;
    AlertDialog.Builder builder ;
    FirebaseFirestore db ;
    View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        db = FirebaseFirestore.getInstance();
        root = inflater.inflate(R.layout.fragment_home, container, false);
        tourAdapter = new TourAdapter(getContext(), listTours);
        list = root.findViewById(R.id.list_view_tours);
        list.setAdapter(tourAdapter);


        listenToMultiple();



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
                return true;
            }
        });

        super.onActivityCreated(savedInstanceState);

    }

    private void listenToMultiple() {

        Thread thread = new Thread() {

            @Override
            public void run() {

                db.collection("tours")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    return;
                                }

                                for (QueryDocumentSnapshot doc : value) {
                                    int id = Integer.parseInt(doc.getId());
                                    String date = doc.getString("date");
                                    String id_Rider = doc.getString("id_rider");
                                    ArrayList<String> list_collectPoints = (ArrayList<String>) doc.get("list_collectPoints");
                                    String city = doc.getString("city");

                                    Tour tour = new Tour(id, date, id_Rider, city);
                                    tour.setList_collectPoints(list_collectPoints);
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