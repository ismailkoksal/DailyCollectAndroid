package fr.aylan.dailycollect.driver.ui.detailtour;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.Tour;


public class TourDetailActivity extends AppCompatActivity   {

    private TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
    public TabLayout tabLayout;
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider_activity_detail_tour);

        viewPager =  findViewById(R.id.viewPager);
        tabLayout =  findViewById(R.id.tabLayout);

        Tour tour = getIntent().getExtras().getParcelable(getString(R.string.tour));
        Toast.makeText(this,String.valueOf(tour.getList_collectPoints().size()),Toast.LENGTH_LONG).show();


        adapter.addFragment(new TabFragmentTourDetail(tour), getString(R.string.detail));
        adapter.addFragment(new TabFragmentTourMap(tour), getString(R.string.map));
        setTitle(getString(R.string.tour));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        getSupportActionBar().setElevation(0);


    }


}
