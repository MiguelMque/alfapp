package guru.mque.alfappcloud;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewHourActivity extends AppCompatActivity {
    private EditText editTextDate;
    private EditText editTextDescription;
    private EditText editTextnumber;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Spinner spinner_jornadas;
    private Spinner spinner_sedes;

    private  List<String> jornadaList = new ArrayList<>();

    private  List<String> sedeList = new ArrayList<>();
    private  List<String> nameList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference hoursRef = db.collection("Users");
    private String jornada;
    private String sede;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_hour);

        editTextDate = findViewById(R.id.edit_text_dateN);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextnumber = findViewById(R.id.edit_text_hoursN);
        spinner_sedes = findViewById(R.id.spinner_sedes);
        spinner_jornadas = findViewById(R.id.spinner_jornadas);


       db.collection("Time").document("iecm").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                sedeList = ((List<String>)document.get("sedes"));

                System.out.println(sedeList);

                ArrayAdapter<String> adapter= new ArrayAdapter<String>(NewHourActivity.this,android.R.layout.simple_spinner_item, sedeList);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_sedes.setAdapter(adapter);



            }
        });




       db.collection("Time").document("iecm").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                jornadaList = ((List<String>)document.get("jornadas"));

                System.out.println(jornadaList);

                ArrayAdapter<String> adapter= new ArrayAdapter<String>(NewHourActivity.this,android.R.layout.simple_spinner_item, jornadaList);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_jornadas.setAdapter(adapter);



            }
        });

        spinner_jornadas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                jornada = spinner_jornadas.getSelectedItem().toString();
                sede = spinner_sedes.getSelectedItem().toString();

                hoursRef.whereEqualTo("jornada", jornada).whereEqualTo("sede", sede).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    }
                });


            }



            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });




    }

    private void displayUserData(User user){
             String name = user.getName();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_hour_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_hour:
                saveHour();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveHour(){
        String date =   editTextDate.getText().toString();
        String description = editTextDescription.getText().toString();
        String number = editTextnumber.getText().toString();

        if(date.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "COLOCA LA HORA", Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference hourRef = FirebaseFirestore.getInstance().collection("Hours");
    hourRef.add(new Hour(date, Integer.parseInt(number), "Verificado", mAuth.getCurrentUser().getUid() ,description));
        Toast.makeText(this, "Hora agregada", Toast.LENGTH_SHORT).show();
        finish();
    }
}
