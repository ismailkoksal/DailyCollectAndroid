package fr.aylan.dailycollect.driver.ui.validatepointscollectlist;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;

import fr.aylan.dailycollect.App;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.ui.listcollectpoints.ListCollectPointsView;
import fr.aylan.dailycollect.model.CollectPoint;
import fr.aylan.dailycollect.model.Tour;

public class ValidateCollectPointsList extends AppCompatActivity {

    private static final String TAG = "ValidateCollectPoints " ;
    ConstraintLayout holder;
    ArrayList<CollectPoint> selectedCPoints;
    String date;
    String city;
    String rider;
    String vehicle;

    int nbrDocuments = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNbrTours();


        setContentView(R.layout.rider_activity_validate_collect_points_list);
        actionBarSettings();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        holder = findViewById(R.id.pontsCollectholder);
        selectedCPoints = getIntent().getParcelableArrayListExtra("selectedCPoints");
        ((TextView) findViewById(R.id.tvStartTime)).setText(selectedCPoints.get(0).getApproximativeTime());
        ((TextView) findViewById(R.id.tvEndTime)).setText(selectedCPoints.get(selectedCPoints.size()-1).getApproximativeTime());




        // get information sent from previous activity
        date = getIntent().getStringExtra("date");
        vehicle = getIntent().getStringExtra("vehicle");
        rider = getIntent().getStringExtra("rider");
        city = getIntent().getStringExtra("city");

        // display information
        ((TextView) findViewById(R.id.tvDate)).setText(date);
        ((TextView) findViewById(R.id.tvVehicle)).setText(vehicle);
        ((TextView) findViewById(R.id.tvRider)).setText(rider);
        ((TextView) findViewById(R.id.tvCity)).setText(city);

        ListCollectPointsView listCollectPoints = new ListCollectPointsView(this,selectedCPoints);
        holder.addView(listCollectPoints);

    }


    // get number of tours in firebase
    public void getNbrTours(){
        ((App) (getApplication()) ).db.collection("tours")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        nbrDocuments = value.size();
                    }
                });
    }

    public void validateList(View view) {

        Tour tour = new Tour(nbrDocuments, date, rider, city);
        ArrayList<String> collectPointsIds = new ArrayList<>();
        for (CollectPoint c : selectedCPoints){
            collectPointsIds.add(String.valueOf(c.getId()));
        }
        tour.setList_collectPoints(collectPointsIds);


        Task<Void> writeResult =
                ((App) (getApplication()) ).db
                        .collection("tours")
                        .document(String.valueOf(nbrDocuments))
                        .set(tour, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ValidateCollectPointsList.this, getString(R.string.added_successfuly),Toast.LENGTH_LONG).show();
                                startActivity(((App) getApplication()).intent);
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ValidateCollectPointsList.this, getString(R.string.failed_to_add),Toast.LENGTH_LONG).show();
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
    }

    // set icon to backarrow and remive shaddow
    public void actionBarSettings(){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        getSupportActionBar().setElevation(0);

    }

    // set action on backarrow
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
