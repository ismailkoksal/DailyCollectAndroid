package fr.aylan.dailycollect.driver.ui.addtour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import fr.aylan.dailycollect.MainActivity;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.ui.validatepointscollectlist.ValidateCollectPointsList;

public class AddTour extends AppCompatActivity {

    TimePickerDialog picker;
    EditText eText;
    ImageButton btnAddTourItem;
    int nbrItems;
    LinearLayout listItemsLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);

        listItemsLayout = findViewById(R.id.listItemsLayout);


        btnAddTourItem =findViewById(R.id.btnAddItemTour);
        btnAddTourItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTourItem();
            }
        });

    }


    public void addTourItem(){

        String[] temp = {"item 1", "item 2", "item 3"};

        nbrItems++;
        TourItemView itemView = new TourItemView(this, nbrItems,temp);

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

        Intent intent = new Intent(this, ValidateCollectPointsList.class);
        startActivity(intent);

    }


}
