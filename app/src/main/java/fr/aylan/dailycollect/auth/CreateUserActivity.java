package fr.aylan.dailycollect.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.model.Client;
import fr.aylan.dailycollect.model.UserType;

public class CreateUserActivity extends AppCompatActivity {

    private static final String TAG = "CreateUser";

    private ProgressBar progressBar;
    private EditText nameField;
    private EditText companyNameField;
    private EditText mailField;
    private EditText phoneField;
    private EditText streetNameField;
    private EditText postalCodeField;
    private EditText cityField;
    private EditText passwordField;
    private EditText confirmPasswordField;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Views
        progressBar = findViewById(R.id.progressBarCreateUser);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        nameField = findViewById(R.id.cuName);
        companyNameField = findViewById(R.id.cuCompanyName);
        mailField = findViewById(R.id.cuMail);
        phoneField = findViewById(R.id.cuPhoneNumber);
        streetNameField = findViewById(R.id.cuStreetName);
        postalCodeField = findViewById(R.id.cuPostalCode);
        cityField = findViewById(R.id.cuCity);
        passwordField = findViewById(R.id.cuPassword);
        confirmPasswordField = findViewById(R.id.cuConfirmPassword);

        Button createUserButton = findViewById(R.id.createUserButton);
        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(mailField.getText().toString(), passwordField.getText().toString());
            }
        });

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        progressBar.setVisibility(ProgressBar.VISIBLE);

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user = auth.getCurrentUser();
                            getClients();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateUserActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                    }
                });
    }

    private void getClients() {
        db.collection("clients").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                insertClientData(queryDocumentSnapshots.size());
            }
        });
    }

    private void insertClientData(final int clientsSize) {
        Map<String, Object> data = new HashMap<>();
        data.put("nom", nameField.getText().toString());
        data.put("raisonSociale", companyNameField.getText().toString());
        data.put("email", mailField.getText().toString());
        data.put("telephone", phoneField.getText().toString());
        data.put("rue", streetNameField.getText().toString());
        data.put("cp", postalCodeField.getText().toString());
        data.put("ville", cityField.getText().toString());

        db.collection("clients").document(Integer.toString(clientsSize))
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        insertUserData(Integer.toString(clientsSize));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void insertUserData(String clientId) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", clientId);
        data.put("type", UserType.CLIENT);

        db.collection("users").document(user.getUid())
                .set(data);
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
