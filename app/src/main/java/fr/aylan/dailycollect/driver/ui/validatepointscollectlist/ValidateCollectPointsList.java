package fr.aylan.dailycollect.driver.ui.validatepointscollectlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.CollectPoint;
import fr.aylan.dailycollect.driver.ui.listcollectpoints.ListCollectPoints;

public class ValidateCollectPointsList extends AppCompatActivity {


    String[] ressources = new String[]{};
    ConstraintLayout holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_collect_points_list);

        Spinner dropdown = findViewById(R.id.ressourcesSpinner);
        holder = findViewById(R.id.pontsCollectholder);

        ArrayList<CollectPoint> temp = new ArrayList<>();

        ListCollectPoints listCollectPoints = new ListCollectPoints(this,temp);

        holder.addView(listCollectPoints);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ressources);

        dropdown.setAdapter(adapter);
    }

    public void validateList(View view) {
    }


}
