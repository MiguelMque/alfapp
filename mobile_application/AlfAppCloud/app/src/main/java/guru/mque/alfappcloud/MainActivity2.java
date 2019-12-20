package guru.mque.alfappcloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity2 extends AppCompatActivity {

    private FirebaseFirestore db  = FirebaseFirestore.getInstance();
    private CollectionReference  hoursRef = db.collection("Hours");
    private HourAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setUpRecyclerView();



    }

    private void setUpRecyclerView() {
        Query query = hoursRef.orderBy("number", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Hour> options = new FirestoreRecyclerOptions.Builder<Hour>()
                .setQuery(query, Hour.class).build();

        adapter = new HourAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
