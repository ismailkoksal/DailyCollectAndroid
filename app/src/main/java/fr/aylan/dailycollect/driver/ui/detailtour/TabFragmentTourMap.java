package fr.aylan.dailycollect.driver.ui.detailtour;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import fr.aylan.dailycollect.App;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.Tour;


public class TabFragmentTourMap extends Fragment   {


    MapView mMapView;
    private GoogleMap googleMap;
    Tour tour ;
    List listIds;
    Boolean firstPoint = true;
    LatLng firstPointLatLong;


    public TabFragmentTourMap(Tour t) {
        tour = t;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_tour_map, container, false);
        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                //googleMap.setMyLocationEnabled(true);

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

                                        if ( doc.getId().equals(listIds.get(i))){

                                            String latitude  = doc.getString("latitude");
                                            String longitude  = doc.getString("longitude");
                                            String name = doc.getString("name");
                                            String approximativeTime  = doc.getString("approximativeTime");
                                            // For dropping a marker at a point on the Map
                                            LatLng  latlong = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                            if (firstPoint) {
                                                firstPointLatLong = latlong ;
                                                firstPoint = false;
                                            }

                                            googleMap.addMarker(new MarkerOptions().position(latlong).title(name).snippet(approximativeTime));

                                        }
                                    }
                                }
                                // For zooming automatically to the location of the marker
                                CameraPosition cameraPosition = new CameraPosition.Builder().target(firstPointLatLong).zoom(15).build();
                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            }
                        });

            }
        });


        return  rootView;


    }
}