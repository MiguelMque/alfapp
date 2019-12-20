package guru.mque.alfappcloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class s_HomeActivity_Teacher extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String KEY_NAME= "name";
    private static final String KEY_LASTNAME = "lastName";
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DocumentReference userRef = db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private TextView TextViewCompleteName, TextViewEmail;
    private String completeName;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teacher);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle(getString(R.string.hours));

        drawer = findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);


        navigationView.setNavigationItemSelectedListener(this);
        View navHeaderView = navigationView.getHeaderView(0);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        TextViewCompleteName = navHeaderView.findViewById(R.id.TextViewCompleteName);
        TextViewEmail = navHeaderView.findViewById(R.id.TextViewEmail);


        loadUser();
       TextViewEmail.setText(mAuth.getCurrentUser().getEmail());

       if(savedInstanceState == null) {

           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                   new s_HoursFragment()).commit();
           navigationView.setCheckedItem(R.id.nav_home);
       }



    }

    //Switch cases for NavBar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_home:
                //This getSupport thing allows us to put the s_HoursFragment in the FrameLayout
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new s_HoursFragment()).commit();
                break;


            case R.id.nav_send:
                //This getSupport thing allows us to put the Send in the FrameLayout
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SendFragment()).commit();
                break;

            case R.id.nav_logout:
                mAuth.signOut();
                sendUserToLoginActivity();
                break;
        }

        //Hidding drawer, NavBar
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadUser(){
        userRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                   User user = documentSnapshot.toObject(User.class);

                   String name = user.getName();

                        TextViewCompleteName.setText(name);

                        System.out.println(user.getId());
                        System.out.println(user.getRange());

                    } else{
                        System.out.println("Doesn't work loadUser");
                    }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });




    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    private void sendUserToLoginActivity(){

        Intent loginIntent = new Intent(s_HomeActivity_Teacher.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();

    }
}