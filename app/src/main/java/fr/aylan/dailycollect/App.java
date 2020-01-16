package fr.aylan.dailycollect;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.firestore.FirebaseFirestore;

public class App extends Application {

    public FirebaseFirestore db ;
    public Intent intent;

    public void initialize(){
        db = FirebaseFirestore.getInstance();
    }

    public void setIntent(Intent intent){
        this.intent = intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initialize();
    }
}
