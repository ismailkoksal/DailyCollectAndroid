package fr.aylan.dailycollect;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;

public class App extends Application {

    public FirebaseFirestore db ;

    public void initialize(){
        db = FirebaseFirestore.getInstance();
    }



}
