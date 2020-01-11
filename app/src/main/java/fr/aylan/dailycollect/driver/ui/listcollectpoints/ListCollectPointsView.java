package fr.aylan.dailycollect.driver.ui.listcollectpoints;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.CollectPoint;


public class ListCollectPointsView extends FrameLayout {

    TimePickerDialog picker;
    private ArrayList lisCPNames = new ArrayList<CollectPoint>();
    ListView list ;
    private CollectPointAdapter   collectPointAdapter;

    public ListCollectPointsView(@NonNull Context context, ArrayList<CollectPoint> listPointsCollect) {
        super(context);


        collectPointAdapter = new CollectPointAdapter(getContext(), listPointsCollect);
        inflate(context, R.layout.rider_list_collect_points_view, this);
        list = findViewById(R.id.list_view_collect_points);
        list.setAdapter(collectPointAdapter);

        for (CollectPoint item : listPointsCollect){
            lisCPNames.add(item.getName());
        }

    }

}
