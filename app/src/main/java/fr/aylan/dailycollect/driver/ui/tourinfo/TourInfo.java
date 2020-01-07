package fr.aylan.dailycollect.driver.ui.tourinfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;

import fr.aylan.dailycollect.App;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.CollectPoint;
import fr.aylan.dailycollect.driver.ui.validatepointscollectlist.ValidateCollectPointsList;

public class TourInfo extends AppCompatActivity {

    ArrayAdapter<String> vehicleAdapter;
    ArrayList<String> vehicles = new ArrayList<>();

    ArrayAdapter<String> cityAdapter;
    ArrayList<String>  cities = new ArrayList<>();

    ArrayAdapter<String> riderAdapter;
    ArrayList<String> riders = new ArrayList<>();

    Spinner spinnerCity;
    Spinner spinnerVehicle;
    Spinner spinnerRider;

    ArrayList<CollectPoint> selectedCPoints;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_info);

        getVehicles();
        getCities();
        getRiders();


        spinnerCity = findViewById(R.id.listvCitypinner);
        spinnerVehicle = findViewById(R.id.listVehiclepinner);
        spinnerRider = findViewById(R.id.listRiderpinner);


        vehicleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vehicles);
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        riderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, riders);



        spinnerVehicle.setAdapter(vehicleAdapter);
        spinnerCity.setAdapter(cityAdapter);
        spinnerRider.setAdapter(riderAdapter);


    }


    public void next(View view){
        String city = spinnerCity.getSelectedItem().toString();
        String rider = spinnerCity.getSelectedItem().toString();
        String vehivle = spinnerCity.getSelectedItem().toString();

       selectedCPoints = getIntent().getParcelableArrayListExtra("selectedCPoints");

        Intent intent = new Intent(this, ValidateCollectPointsList.class);
        intent.putExtra("city",city);
        intent.putExtra("rider",vehivle);
        intent.putParcelableArrayListExtra("selectedCPoints", selectedCPoints);
        startActivity(intent);


    }



    public void getVehicles()
    {
        ((App) (getApplication()) ).db.collection("vehicles")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            String name = doc.getString("name");
                            vehicles.add(name);
                        }
                        vehicleAdapter.notifyDataSetChanged();


                    }
                });
    }


    public void getRiders()
    {
        ((App) (getApplication()) ).db.collection("riders")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            String name = doc.getString("name");
                            riders.add(name);
                        }
                        riderAdapter.notifyDataSetChanged();


                    }
                });
    }

      public void getCities()
    {
        ((App) (getApplication()) ).db.collection("cities")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            String name = doc.getString("name");
                            cities.add(name);
                        }
                        cityAdapter.notifyDataSetChanged();


                    }
                });
    }



}
