package fr.aylan.dailycollect.customer.ui.collects;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
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

    private FirebaseFirestore db;
    private ProgressBar progressBar;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private ListView collectsListView;
    private CollectsAdapter collectsAdapter;

    private String userId;

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

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        userId = pref.getString("userId", null);

        // Views
        collectsListView = view.findViewById(R.id.collects_listview);
        progressBar = view.findViewById(R.id.progress_horizontal);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddCollectActivity.class);
                intent.putExtra("user", userId);
                startActivity(intent);
            }
        });
    }

    private void getUserCollects() {
        progressBar.setVisibility(View.VISIBLE);
        db.collection("clients").document(userId).collection("collects")
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
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserCollects();
    }
}
