package fr.aylan.dailycollect.customer.ui.collects;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import fr.aylan.dailycollect.R;
import fr.aylan.dailycollect.model.Collect;
import fr.aylan.dailycollect.model.Customer;
import fr.aylan.dailycollect.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectsFragment extends Fragment {

    private final String TAG = "CollectsFragment";

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private User user;

    private ListView collectsListView;
    private CollectsAdapter collectsAdapter;

    public CollectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.customer_fragment_collects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();

        // Views
        collectsListView = view.findViewById(R.id.collects_listview);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddCollectActivity.class);
                intent.putExtra("user", user.getId());
                startActivity(intent);
            }
        });
    }

    private void getUser() {
        db.collection("users").document(currentUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user = documentSnapshot.toObject(User.class);
                        getUserCollects();
                    }
                });
    }

    private void getUserCollects() {
        db.collection("clients").document(user.getId()).collection("collects")
                .orderBy("date")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    ArrayList<Collect> collects = new ArrayList<>();
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Collect collect = document.toObject(Collect.class);
                                collects.add(collect);
                            }
                            collectsAdapter = new CollectsAdapter(getContext(), collects);
                            collectsListView.setAdapter(collectsAdapter);
                        } else {
                            Log.d(TAG, "Error getting collects", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "OnResume CollectsFragment");
        getUser();
    }
}
