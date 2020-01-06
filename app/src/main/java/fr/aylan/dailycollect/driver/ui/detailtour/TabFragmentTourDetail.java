package fr.aylan.dailycollect.driver.ui.detailtour;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import fr.aylan.dailycollect.App;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.CollectPoint;
import fr.aylan.dailycollect.driver.model.Tour;
import fr.aylan.dailycollect.driver.ui.listcollectpoints.ListCollectPointsView;


public class TabFragmentTourDetail extends Fragment {

    Tour tour ;
    ArrayList<CollectPoint>  collectPoints = new ArrayList<>();
    List listIds;

    public TabFragmentTourDetail(Tour t) {
        tour = t;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        final View root = inflater.inflate(R.layout.fragment_detail_tour, container, false);
        ((TextView)root.findViewById(R.id.tvTourName)).setText("Tournée : " + (tour.getId()+1));
        ((TextView)root.findViewById(R.id.tvDate)).setText(tour.getDate());

        listIds = new ArrayList();
        List<String> temp =  tour.getList_collectPoints();
        for(String s : temp){
            listIds.add(s);
        }

        ((App) (getActivity().getApplication()) ).db.collection("collectPoints")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            for(int i = 0 ; i < listIds.size(); i++){
                                String docid = doc.getId();

                                if ( doc.getId().equals(listIds.get(i))){
                                    int _id = Integer.parseInt((String) listIds.get(i));
                                    String clientId = doc.getString("clientId");
                                    String name = doc.getString("name");
                                    String approximativeTime  = doc.getString("approximativeTime");
                                    String latitude  = doc.getString("latitude");
                                    String longitude  = doc.getString("longitude");
                                    CollectPoint cp = new CollectPoint( _id, clientId,  name,  approximativeTime, latitude, longitude);
                                    collectPoints.add(cp);
                                }
                            }
                        }
                        ListCollectPointsView cpView = new ListCollectPointsView(getContext(),collectPoints);

                        ConstraintLayout collectPointsHolder = root.findViewById(R.id.pontsCollectholder);
                        collectPointsHolder.addView(cpView);
                    }
                });

        return root;
    }
}