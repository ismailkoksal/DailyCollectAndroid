package fr.aylan.dailycollect.driver.ui.addtour;

import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import java.util.Calendar;

import fr.aylan.dailycollect.R;


public class TourItemView extends FrameLayout {


    private int _id;
    TimePickerDialog picker;

    public TourItemView(@NonNull Context context, int id, String[] listPointsCollect) {
        super(context);

        _id = id;
        inflate(context, R.layout.add_tour_item, this);

        Spinner dropdown = findViewById(R.id.collectPointSpinner);

        ((EditText)findViewById(R.id.etMinTime)).setInputType(InputType.TYPE_NULL);
        ((EditText)findViewById(R.id.etMaxTime)).setInputType(InputType.TYPE_NULL);
        ((TextView)findViewById(R.id.tvTitle)).setText("Point 1");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, listPointsCollect);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


    }



}
