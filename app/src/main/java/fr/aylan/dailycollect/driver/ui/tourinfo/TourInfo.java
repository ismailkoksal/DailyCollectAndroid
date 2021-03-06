package fr.aylan.dailycollect.driver.ui.tourinfo;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import fr.aylan.dailycollect.App;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.ui.validatepointscollectlist.ValidateCollectPointsList;
import fr.aylan.dailycollect.model.CollectPoint;

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
    DatePicker datePicker;

    ArrayList<CollectPoint> selectedCPoints;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider_activity_tour_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        actionBarSettings();

        // get list of vehicles from firebase
        getVehicles();
        // get list of cities from firebase
        getCities();
        // get list of drivers from firebase
        getRiders();


        spinnerCity = findViewById(R.id.listvCitypinner);
        spinnerVehicle = findViewById(R.id.listVehiclepinner);
        spinnerRider = findViewById(R.id.listRiderpinner);
        datePicker = findViewById(R.id.datePicker);


        // set adapters
        vehicleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, vehicles);
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        riderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, riders);



        // populate spinners with lists
        spinnerVehicle.setAdapter(vehicleAdapter);
        spinnerCity.setAdapter(cityAdapter);
        spinnerRider.setAdapter(riderAdapter);


    }


    // go to next step
    public void next(View view){
        // get choosed informations
        String city = spinnerCity.getSelectedItem().toString();
        String rider = spinnerRider.getSelectedItem().toString();
        String vehicle = spinnerVehicle.getSelectedItem().toString();
        String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth()+1) + "/" + datePicker.getYear();

       selectedCPoints = getIntent().getParcelableArrayListExtra("selectedCPoints");

       // send choosed informations to newt activity
        Intent intent = new Intent(this, ValidateCollectPointsList.class);
        intent.putExtra("city",city);
        intent.putExtra("rider",rider);
        intent.putExtra("vehicle",vehicle);
        intent.putExtra("date",date);
        intent.putParcelableArrayListExtra("selectedCPoints", selectedCPoints);
        startActivity(intent);


    }



    // get list of vehicles from firebase.
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

    //get list of drivers from firebase
    public void getRiders()
    {
        ((App) (getApplication()) ).db.collection("drivers")
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

    // set action to back arrow button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // set icon to backarrow and remove shaddow
    public void actionBarSettings(){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        getSupportActionBar().setElevation(0);

    }



}
