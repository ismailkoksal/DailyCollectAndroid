package fr.aylan.dailycollect.ovive.ui.detailclient;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.driver.model.Client;

public class ClientDetailActivity extends AppCompatActivity {

    Client client ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ovive_activity_detail_client);
        setTitle(getString(R.string.client));


        client = getIntent().getParcelableExtra(getString(R.string.client));

        ((TextView)findViewById(R.id.tvDirector)).setText(client.getDirector());
        ((TextView)findViewById(R.id.tvName)).setText(client.getName());
        ((TextView)findViewById(R.id.tvMail)).setText(client.getMail());
        ((TextView)findViewById(R.id.tvTel)).setText(client.getTel());
        ((TextView)findViewById(R.id.tvAdress)).setText(client.getAdresse());
        ((TextView)findViewById(R.id.tvSubscriptionDate)).setText(client.getSubscription_date());
        ((TextView)findViewById(R.id.tvSignatureDate)).setText(client.getSigning_date());
        ((TextView)findViewById(R.id.tvContractEnd)).setText(client.getContract_end_date());
        ((TextView)findViewById(R.id.tvCollectDay)).setText(client.getCollect_day());



    }
}
