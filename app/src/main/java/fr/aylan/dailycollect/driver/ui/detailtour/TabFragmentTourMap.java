package fr.aylan.dailycollect.driver.ui.detailtour;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import fr.aylan.dailycollect.App;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.CollectPoint;
import fr.aylan.dailycollect.driver.model.Tour;


public class TabFragmentTourMap extends Fragment   {


    MapView mMapView;
    private GoogleMap googleMap;
    Tour tour ;
    List listIds;
    Boolean firstPoint = true;
    LatLng firstPointLatLong;
    View detailView;
    HashMap<Integer,CollectPoint> lisPoints = new HashMap<>();


    public TabFragmentTourMap(Tour t) {
        tour = t;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView =  inflater.inflate(R.layout.fragment_tour_map, container, false);
        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        detailView = rootView.findViewById(R.id.collectPDetailView);
        detailView.setVisibility(View.GONE);
        (rootView.findViewById(R.id.navigationBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavigationClick();
            }
        });

        (rootView.findViewById(R.id.btnCloseDetail)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseDétailModal(detailView);
            }
        });

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
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        detailView.setVisibility(View.VISIBLE);
                        int position = (int)(marker.getTag());

                        CollectPoint c = lisPoints.get(position);

                        ((TextView)rootView.findViewById(R.id.tcNameCollectPoint)).setText(c.getName());
                        ((TextView)rootView.findViewById(R.id.tvAdressCollectPoint)).setText(c.getName());
                        ((TextView)rootView.findViewById(R.id.tvTimeCollectPoint)).setText(c.getApproximativeTime());

                        return false;
                    }
                });

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
                                            String clientId  = doc.getString("clientId");
                                            // For dropping a marker at a point on the Map
                                            LatLng  latlong = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                                            if (firstPoint) {
                                                firstPointLatLong = latlong ;
                                                firstPoint = false;
                                            }

                                            CollectPoint c = new CollectPoint( Integer.parseInt(listIds.get(i).toString()),  clientId,  name,  approximativeTime,  latitude,   longitude);
                                            lisPoints.put(Integer.parseInt(listIds.get(i).toString()),c);

                                            Marker marker = googleMap.addMarker(new MarkerOptions()
                                                            .position(latlong)
                                                            .title(name)
                                                            .snippet(approximativeTime)
                                                            .icon(BitmapDescriptorFactory.defaultMarker(170.0f)));
                                            marker.setTag(Integer.parseInt(listIds.get(i).toString()));

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


    public void onNavigationClick(){
        Uri navigation = Uri.parse("google.navigation:q="+firstPointLatLong.latitude+","+firstPointLatLong.longitude+"");
        Intent navigationIntent = new Intent(Intent.ACTION_VIEW, navigation);
        navigationIntent.setPackage("com.google.android.apps.maps");
        startActivity(navigationIntent);

    }

    public void onCloseDétailModal(View view){
        view.setVisibility(View.GONE);

    }
}