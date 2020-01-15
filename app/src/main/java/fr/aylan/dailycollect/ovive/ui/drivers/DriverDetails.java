package fr.aylan.dailycollect.ovive.ui.drivers;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.model.OviveDriver;

public class DriverDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_driver);

        OviveDriver driver = getIntent().getParcelableExtra(getString(R.string.driver));

        ((TextView) findViewById(R.id.tvName)).setText(driver.getName());
        ((TextView) findViewById(R.id.tvCity)).setText(driver.getCity());
        ((TextView) findViewById(R.id.tvEmployementDate)).setText(driver.getEmployement_date());
        ((TextView) findViewById(R.id.tvMail)).setText(driver.getMail());
        ((TextView) findViewById(R.id.tvTel)).setText(driver.getTel());
        ImageView photoProfile = ((ImageView) findViewById(R.id.ivProfile));
        Picasso.get()
                .load(driver.getUrlPhoto())
                //.error(R.drawable.image2)
                //.placeholder(R.drawable.ic_drawer)
                .resize(200, 200)
                .transform(new ImageTrans_CircleTransform())
                .into(photoProfile);
    }
}
