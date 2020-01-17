package fr.aylan.dailycollect.ovive.ui.clients;

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
import fr.aylan.dailycollect.model.Client;
import fr.aylan.dailycollect.ovive.ui.detailclient.ClientDetailActivity;


public class ClientFragment extends Fragment {

    private ArrayList listClients = new ArrayList<Client>();
    ListView list ;
    private ClientAdapter clientAdapter;
    AlertDialog.Builder builder ;
    public static FirebaseFirestore db ;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.ovive_fragment_clients, container, false);


        clientAdapter = new ClientAdapter(getContext(), listClients);
        list = root.findViewById(R.id.listViewClients);
        list.setAdapter(clientAdapter);


        listenToMultiple();


        String[] array = new String[]{getString(R.string.modify),getString(R.string.delete)};

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

                ((App) (getParentFragment().getActivity().getApplication()) ).db.collection("client")
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
                                    String adresse = doc.getString("adresse");
                                    String subscription_date = doc.getString("subscription_date");
                                    String signature_date = doc.getString("signature_date");
                                    String contract_end_date = doc.getString("contract_end_date");
                                    String collect_day = doc.getString("collect_day");




                                    Client client = new Client(id, logo, mail, tel, director, id_collect_point, name
                                    , adresse, subscription_date, signature_date, contract_end_date, collect_day);
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
                Intent intent = new Intent(getContext(), ClientDetailActivity.class);
                Client client = (Client) listClients.get(position);
                intent.putExtra(getString(R.string.client),client);
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