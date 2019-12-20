package guru.mque.alfappcloud.m_Firebase;

import android.support.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import guru.mque.alfappcloud.Spacecraft;

public class FirebaseHelper {

    private FirebaseFirestore db;


    Boolean saved = null;


    public FirebaseHelper(FirebaseFirestore db){
        this.db = db;
    }


    public Boolean save(Spacecraft spacecraft){
        if(spacecraft==null){
            saved = false;
        } else {

        }

        return saved;
    }




    //Read
    public ArrayList<String> retrieve(){
        final ArrayList<String> teachers = new ArrayList<>();

        db.collection("cities")
                .whereEqualTo("state", "CA")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                    }

                    public void onEvent(@Nullable QuerySnapshot snapshots) {

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    System.out.println("New city: " + dc.getDocument().getData());
                                    break;
                                case MODIFIED:
                                    System.out.println("Modified city: " + dc.getDocument().getData());
                                    break;
                                case REMOVED:
                                    System.out.println("Removed city: " + dc.getDocument().getData());
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });
        return teachers;
    }




}
