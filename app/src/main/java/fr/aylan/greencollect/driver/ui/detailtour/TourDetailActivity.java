package fr.aylan.greencollect.driver.ui.detailtour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;

import fr.aylan.greencollect.R;

public class TourDetailActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener, OnMapReadyCallback {

    private TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
    public TabLayout tabLayout;
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tour);

        viewPager =  findViewById(R.id.viewPager);
        tabLayout =  findViewById(R.id.tabLayout);

        adapter.addFragment(new TourDetail(), getString(R.string.detail));
        adapter.addFragment(new TourMap(), getString(R.string.map));
        setTitle(getString(R.string.tour));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        getSupportActionBar().setElevation(0);


        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-33.852, 151.211);
        LatLng sydney2 = new LatLng(-33.9, 151.211);
        LatLng sydney3 = new LatLng(-33.5, 151.211);

        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.addMarker(new MarkerOptions().position(sydney2).title("sydney 2"));
        googleMap.addMarker(new MarkerOptions().position(sydney3).title("sydene3"));


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
