package fr.aylan.dailycollect.driver;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import fr.aylan.dailycollect.R;


public class TourList extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider_activity_list_tour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_tours, R.id.nav_clients, R.id.nav_drivers)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        getSupportActionBar().setElevation(0);

        View hView =  navigationView.getHeaderView(0);


        // check if user is not null to show information

        try{
            if (auth.getCurrentUser().getDisplayName() != null ){
                ((TextView) hView.findViewById(R.id.tvName)).setText(auth.getCurrentUser().getDisplayName());
                ((TextView) hView.findViewById(R.id.tvFonction)).setText("Chauffeur");
            }else{
                ((TextView) hView.findViewById(R.id.tvName)).setText("User");
                ((TextView) hView.findViewById(R.id.tvFonction)).setText("Chauffeur");
            }
        }catch (Exception e){
            ((TextView) hView.findViewById(R.id.tvName)).setText("User");
            ((TextView) hView.findViewById(R.id.tvFonction)).setText("Chauffeur");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tour_list, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
