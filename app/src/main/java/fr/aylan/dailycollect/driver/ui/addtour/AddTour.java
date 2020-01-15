package fr.aylan.dailycollect.driver.ui.addtour;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Calendar;
import fr.aylan.dailycollect.App;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.model.CollectPoint;
import fr.aylan.dailycollect.driver.ui.tourinfo.TourInfo;

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


        btnAddTourItem =findViewById(R.id.btnAddItemTour);
        btnAddTourItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTourItem();
            }
        });
        getCollectPoints();

    }

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

                    }
                });
    }


    public void addTourItem(){



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


}
