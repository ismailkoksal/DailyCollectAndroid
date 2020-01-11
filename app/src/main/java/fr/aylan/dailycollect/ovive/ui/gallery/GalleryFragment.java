package fr.aylan.dailycollect.ovive.ui.gallery;

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

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import fr.aylan.dailycollect.App;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.Client;
import fr.aylan.dailycollect.driver.model.Tour;
import fr.aylan.dailycollect.driver.ui.detailtour.TourDetailActivity;


public class GalleryFragment extends Fragment {

    private ArrayList listClients = new ArrayList<Client>();
    ListView list ;
    private ClientAdapter clientAdapter;
    AlertDialog.Builder builder ;
    public static FirebaseFirestore db ;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.ovive_fragment_gallery, container, false);


        clientAdapter = new ClientAdapter(getContext(), listClients);
        list = root.findViewById(R.id.listViewClients);
        list.setAdapter(clientAdapter);


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


    private void listenToMultiple() {

        Thread thread = new Thread() {

            @Override
            public void run() {

                ((App) (getParentFragment().getActivity().getApplication()) ).db.collection("clients")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    return;
                                }
                                listClients.clear();
                                clientAdapter.clear();

                                for (QueryDocumentSnapshot doc : value) {
                                    String id = doc.getId();

                                    String logo = doc.getString("logo");
                                    String mail = doc.getString("mail");
                                    String tel = doc.getString("tel");
                                    String director = doc.getString("director");
                                    String id_collect_point =  doc.getString("id_collect_point");
                                    String name = doc.getString("name");


                                    Client client = new Client(id, logo, mail, tel, director, id_collect_point, name);
                                    //tour.setList_collectPoints((List<String>) doc.get("list_collectPoints"));

                                    listClients.add(client);
                                }
                                clientAdapter.notifyDataSetChanged();

                            }
                        });
            }
        };

        thread.start();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TourDetailActivity.class);
                Tour tour = (Tour)listClients.get(position);
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

    }
}