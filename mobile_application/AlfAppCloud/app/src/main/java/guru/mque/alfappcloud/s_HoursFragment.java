package guru.mque.alfappcloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class s_HoursFragment extends Fragment {
    private ArrayList<String> arrayList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView mRecyclerView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    View hoursView;
    private CollectionReference hoursRef = db.collection("Hours");
    HourAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        hoursView = inflater.inflate(R.layout.fragment_hours, container, false);


        FloatingActionButton buttonAddHour = hoursView.findViewById(R.id.button_add_hour);
        buttonAddHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewHourActivity.class));
            }
        });
setUpRecyclerView();
        return hoursView;
    }

    private void setUpRecyclerView() {
        Query query = hoursRef.whereEqualTo("studentAuth", mAuth.getCurrentUser().getUid());
        FirestoreRecyclerOptions<Hour> options = new FirestoreRecyclerOptions.Builder<Hour>()
                .setQuery(query, Hour.class).build();

        adapter = new HourAdapter(options);

        RecyclerView recyclerView = hoursView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            //Recycler View



       /*final ListView hoursMenu =  getActivity().findViewById(R.id.hoursMenu);
        userHoursRef.whereEqualTo("studentAuth", FirebaseAuth.getInstance().getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        String data ="";
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            Hour hour = documentSnapshot.toObject(Hour.class);

                            String date = hour.getDate();
                            String description = hour.getDescription();
                            String number = hour.getNumber();
                            String state = hour.getState();
                            String teacherAuth = hour.getTeacherAuth();


                            data+="Date: " + date+  "\nNumber: " + number +  "\nDescription: "
                                    + description +  "\nState: "+ state +  "\nteacherAuth: "
                                    + teacherAuth +"\n\n";

                            arrayList.add(data);

                            data ="";


                        }



                        System.out.println(data);
                        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);

                        adapter.notifyDataSetChanged();

                        hoursMenu.setAdapter(adapter);

                    }
                });


     /*   view.findViewById(R.id.btnLoadHours).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You're in hours", Toast.LENGTH_SHORT).show();



            }
        });*/
    }

}
