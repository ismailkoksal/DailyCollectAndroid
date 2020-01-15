package fr.aylan.dailycollect.ovive.ui.drivers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;

import fr.aylan.dailycollect.App;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.model.OviveDriver;

public class AddDriver extends AppCompatActivity {
    private static final String TAG = "AddDriver";
    ArrayList<String> cities = new ArrayList<>();
    Spinner spinnerCity;
    ArrayAdapter<String> cityAdapter;
    int nbrDocuments = 0;

    String name;
    String mail;
    String tel;
    String city;
    String employement_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);

        getNbrTours();
        getCities();

        spinnerCity = findViewById(R.id.spinnerListvCitypinner);

        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        spinnerCity.setAdapter(cityAdapter);
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

    public void getNbrTours(){
        ((App) (getApplication()) ).db.collection("drivers")
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

    public void createDriver(View view) {

        OviveDriver driver = new OviveDriver( String.valueOf(nbrDocuments),  employement_date,  name,  mail,  tel,  city,  null);
        //Tour tour = new Tour(nbrDocuments, date, rider, city);
        ArrayList<String> collectPointsIds = new ArrayList<>();


        Task<Void> writeResult =
                ((App) (getApplication()) ).db
                        .collection("drivers")
                        .document(String.valueOf(nbrDocuments))
                        .set(driver, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddDriver.this, getString(R.string.added_successfuly),Toast.LENGTH_LONG).show();
                                AddDriver.this.finish();
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddDriver.this, getString(R.string.failed_to_add),Toast.LENGTH_LONG).show();
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
    }
}
