package fr.aylan.dailycollect.customer.ui.collects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.model.Collect;

public class AddCollectActivity extends AppCompatActivity implements
        View.OnClickListener {

    private final String TAG = "AddCollectActivity";

    private FirebaseFirestore db;

    private EditText addCollectDate;
    private EditText addCollectTime;
    private Button addCollectButton;
    private ProgressBar progressBar;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collect);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user");

        db = FirebaseFirestore.getInstance();

        addCollectDate = findViewById(R.id.add_collect_date);
        addCollectTime = findViewById(R.id.add_collect_time);
        addCollectButton = findViewById(R.id.add_collect_button);
        progressBar = findViewById(R.id.progress_horizontal);

        addCollectDate.setOnClickListener(this);
        addCollectTime.setOnClickListener(this);
        addCollectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == addCollectDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    c.set(year, month, dayOfMonth);
                    Date date = c.getTime();
                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRANCE);
                    String strDate = dateFormat.format(date);
                    addCollectDate.setText(strDate);
                }
            }, year, month, day);
            datePickerDialog.show();
        }

        if (v == addCollectTime) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            addCollectTime.setText(String.format(Locale.FRANCE, "%02d:%02d", hourOfDay, minute));
                        }
                    }, hour, minute, true);
            timePickerDialog.show();
        }

        if (v == addCollectButton) {
            if (!validateForm()) {
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            Collect collect = new Collect(addCollectDate.getText().toString(), addCollectTime.getText().toString());
            db.collection("clients").document(userId).collection("collects")
                    .add(collect)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                            progressBar.setVisibility(View.INVISIBLE);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast toast = Toast.makeText(AddCollectActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String date = addCollectDate.getText().toString();
        if (TextUtils.isEmpty(date)) {
            addCollectDate.setError("Obligatoire");
            valid = false;
        } else {
            addCollectDate.setError(null);
        }

        String time = addCollectTime.getText().toString();
        if (TextUtils.isEmpty(time)) {
            addCollectTime.setError("Obligatoire");
            valid = false;
        } else {
            addCollectTime.setError(null);
        }

        return valid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
