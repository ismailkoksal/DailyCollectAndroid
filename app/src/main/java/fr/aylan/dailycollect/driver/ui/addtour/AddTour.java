package fr.aylan.dailycollect.driver.ui.addtour;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

import fr.aylan.dailycollect.App;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.ui.tourinfo.TourInfo;
import fr.aylan.dailycollect.model.CollectPoint;

public class AddTour extends AppCompatActivity {

    TimePickerDialog picker;
    EditText eText;
    ImageButton btnAddTourItem;
    int nbrItems;
    LinearLayout listItemsLayout;
    ArrayList<CollectPoint> collectPoints = new ArrayList<>();
    ArrayList<Spinner> listSpinners = new ArrayList<>();
    String[] pointsNames;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider_activity_add_tour);

        listItemsLayout = findViewById(R.id.listItemsLayout);

        // enable "Go back" button
        actionBarSettings();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btnAddTourItem =findViewById(R.id.btnAddItemTour);
        // display Add Tour when lick on floating Button
        btnAddTourItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTourItem();
            }
        });
        // get Collect Points from firebase
        getCollectPoints();



    }

    // get collect Points from firebase
    private void getCollectPoints() {
        ((App) (getApplication()) ).db.collection("collectPoints")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {

                            int id = Integer.parseInt(doc.getId());
                            String clientId = doc.getString("clientId");
                            String name = doc.getString("name");
                            String approximativeTime  = doc.getString("approximativeTime");
                            String latitude  = doc.getString("latitude");
                            String longitude  = doc.getString("longitude");
                            CollectPoint cp = new CollectPoint( id, clientId,  name,  approximativeTime, latitude, longitude);
                            collectPoints.add(cp);
                        }
                        pointsNames = new String[collectPoints.size()];
                        for (int i=0; i< collectPoints.size(); i++){
                            pointsNames[i] = collectPoints.get(i).getName();
                        }
                        addTourItem();

                    }
                });
    }


    //
    public void addTourItem(){


        // Add another item of Collect Point to Tour
        nbrItems++;
        TourItemView itemView = new TourItemView(AddTour.this, nbrItems,pointsNames);

        Spinner spinner = itemView.findViewById(R.id.listCPNamesSpinner);
        listSpinners.add(spinner);

        itemView.findViewById(R.id.etMinTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar();
            }
        });
        itemView.findViewById(R.id.etMaxTime).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar();
            }
        });

        listItemsLayout.addView(itemView,listItemsLayout.getChildCount()-1);
    }


    // show Calendar
    public void showCalendar(){
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        picker = new TimePickerDialog(AddTour.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        eText.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, true);
        picker.show();
    }


    // Validate list of collect Points and go to next step
    public void validateList(View view){

        ArrayList<CollectPoint> selectedCPoints = new ArrayList<>();

        for (Spinner s : listSpinners){
            for (CollectPoint c: collectPoints){
                if(c.getName().equals(s.getSelectedItem().toString())){
                    selectedCPoints.add(c);
                }
            }

        }

        Intent intent = new Intent(this, TourInfo.class);
        intent.putParcelableArrayListExtra("selectedCPoints", selectedCPoints);
        startActivity(intent);

    }

    // set back arrow and remive elevation/shaddow
    public void actionBarSettings(){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        getSupportActionBar().setElevation(0);

    }
    // set action on back arrow
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
