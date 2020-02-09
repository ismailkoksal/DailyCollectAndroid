package fr.aylan.dailycollect;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import fr.aylan.dailycollect.model.Tour;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {



    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("fr.aylan.dailycollect", appContext.getPackageName());
    }


    // testConstructor of Tour
    @Test
    public void testTourConstructor() {
        int id = 0;
        String date = "01/02/2020";
        String id_Rider = "19";
        String city = "La Rcohelle";

        Tour tour = new Tour( id,  date,  id_Rider,  city);

        Assert.assertEquals(id, tour.getId());
        Assert.assertEquals(date, tour.getDate());
        Assert.assertEquals(id_Rider, tour.getId_Rider());
        Assert.assertEquals(city, tour.getCity());

    }


    // test getting data from firbase
    @Test
    public void testFirbaseData(Activity activity) {
        final ArrayList listTours = new ArrayList<Tour>();

        ((App) (activity.getApplication()) ).db.collection("tours")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            int id = Integer.parseInt(doc.getId());
                            String date = doc.getString("date");
                            String id_Rider = doc.getString("id_rider");
                            String city = doc.getString("city");

                            Tour tour = new Tour(id, date, id_Rider, city);
                            List<String> group = (List<String>) doc.get("list_collectPoints");
                            if (group != null){
                                for (String item : group)
                                    tour.addCollectPoint(item);
                            }

                            listTours.add(tour);
                        }

                    }
                });


        Assert.assertNotNull(listTours);
        Assert.assertTrue(listTours.size() > 0);
        Assert.assertTrue(((Tour)listTours.get(0)).getList_collectPoints().size() > 0);

    }

    // test nbr of data get from firebase
    @Test
    public void testFirbaseDataNumber(Activity activity) {
        final ArrayList listTours = new ArrayList<Tour>();

        ((App) (activity.getApplication()) ).db.collection("tours")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            int id = Integer.parseInt(doc.getId());
                            String date = doc.getString("date");
                            String id_Rider = doc.getString("id_rider");
                            String city = doc.getString("city");

                            Tour tour = new Tour(id, date, id_Rider, city);
                            List<String> group = (List<String>) doc.get("list_collectPoints");
                            if (group != null){
                                for (String item : group)
                                    tour.addCollectPoint(item);
                            }

                            listTours.add(tour);
                        }

                    }
                });



        ((App) (activity.getApplication()) ).db.collection("tours")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }

                        // test number of documents
                        Assert.assertEquals(value.size(), listTours.size());


                    }
                });




    }





}
