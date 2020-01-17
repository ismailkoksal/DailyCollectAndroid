package fr.aylan.dailycollect.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.customer.CustomerMainActivity;
import fr.aylan.dailycollect.driver.TourList;
import fr.aylan.dailycollect.model.User;
import fr.aylan.dailycollect.ovive.OviveMAinActivity;

public class SignInActivity extends AppCompatActivity implements
        View.OnClickListener {

    private static final String TAG = "SignIn";

    private EditText emailField;
    private EditText passwordField;
    private ProgressBar progressBar;

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        // Views
        emailField = findViewById(R.id.fieldEmail);
        passwordField = findViewById(R.id.fieldPassword);
        progressBar = findViewById(R.id.progress_horizontal);

        // Buttons
        findViewById(R.id.emailSignInButton).setOnClickListener(this);
        findViewById(R.id.buttonInscription).setOnClickListener(this);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null && pref.contains("userId") && pref.contains("userType")) {
            User user = new User(pref.getString("userId", null), pref.getString("userType", null));
            startUserActivity(user);
        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        progressBar.setVisibility(ProgressBar.VISIBLE);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            getUser(user);
                            // StartRiderMainActivity();
                            // Toast.makeText(SignInActivity.this, "Authentication success " + user.getUid(),
                                    // Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                    }
                });
    }

    private void getUser(FirebaseUser user) {
        progressBar.setVisibility(ProgressBar.VISIBLE);

        db.collection("users").document(user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                User user = document.toObject(User.class);
                                saveUser(user);
                                startUserActivity(user);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required");
            valid = false;
        } else {
            emailField.setError(null);
        }

        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Required");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        return valid;
    }

    private void StartCustomerMainActivity(User user) {
        Intent intent = new Intent(this, CustomerMainActivity.class);
        intent.putExtra("user", user.getId());
        startActivity(intent);
        finish();
    }

    private void StartOviveMainActivity(User user) {
        Intent intent = new Intent(this, OviveMAinActivity.class);
        intent.putExtra("user", user.getId());
        startActivity(intent);
        finish();
    }

    private void StartRiderMainActivity(User user) {
        Intent intent = new Intent(this, TourList.class);
        intent.putExtra("user", user.getId());
        startActivity(intent);
        finish();
    }

    private void startUserActivity(User user) {
        switch (user.getType()) {
            case "CLIENT":
                StartCustomerMainActivity(user);
                break;
            case "CHAUFFEUR":
                StartRiderMainActivity(user);
                break;
            case "OVIVE":
                StartOviveMainActivity(user);
                break;
        }
    }

    private void saveUser(User user) {
        editor.putString("userId", user.getId());
        editor.putString("userType", user.getType());

        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.emailSignInButton:
                signIn(emailField.getText().toString(), passwordField.getText().toString());
                break;
            case R.id.buttonInscription:
                Intent intent = new Intent(this, CreateUserActivity.class);
                startActivity(intent);
                break;
        }
    }
}
