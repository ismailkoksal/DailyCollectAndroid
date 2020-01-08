package fr.aylan.dailycollect.driver.ui.validatepointscollectlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;
import fr.aylan.dailycollect.App;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.CollectPoint;
import fr.aylan.dailycollect.driver.model.Tour;
import fr.aylan.dailycollect.driver.ui.listcollectpoints.ListCollectPointsView;

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

        setContentView(R.layout.activity_validate_collect_points_list);
        holder = findViewById(R.id.pontsCollectholder);
        selectedCPoints = getIntent().getParcelableArrayListExtra("selectedCPoints");
        ((TextView) findViewById(R.id.tvStartTime)).setText(selectedCPoints.get(0).getApproximativeTime());
        ((TextView) findViewById(R.id.tvEndTime)).setText(selectedCPoints.get(selectedCPoints.size()-1).getApproximativeTime());




        date = getIntent().getStringExtra("date");
        vehicle = getIntent().getStringExtra("vehicle");
        rider = getIntent().getStringExtra("rider");
        city = getIntent().getStringExtra("city");

        ((TextView) findViewById(R.id.tvDate)).setText(date);
        ((TextView) findViewById(R.id.tvVehicle)).setText(vehicle);
        ((TextView) findViewById(R.id.tvRider)).setText(rider);
        ((TextView) findViewById(R.id.tvCity)).setText(city);

        ListCollectPointsView listCollectPoints = new ListCollectPointsView(this,selectedCPoints);
        holder.addView(listCollectPoints);

    }


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
                        .set(tour, SetOptions.merge());

    }



}
