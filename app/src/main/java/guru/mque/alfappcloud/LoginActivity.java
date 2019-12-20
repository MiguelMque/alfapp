package guru.mque.alfappcloud;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_RANGE = "range";
    private EditText edtEmail, edtPassword;
    private String userId;



    private TextView textViewRegister, textViewForgot;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference userRef = db.document("Users" + "/" + userId);
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Elements Id
        textViewForgot = findViewById(R.id.textViewForgot);
        textViewRegister = findViewById(R.id.textViewRegister);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);



        //Get Firebase instance
        mAuth = FirebaseAuth.getInstance();


        if ((mAuth.getCurrentUser()) != null) {

            finish();
            startActivity(new Intent(getApplicationContext(), s_HomeActivity.class));

        }

        btnLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
        textViewForgot.setOnClickListener(this);

        edtPassword.addTextChangedListener(mTextWatcher);
        edtEmail.addTextChangedListener(mTextWatcher);
        checkFieldsForEmptyValues();

    }




    public void userLogin() {

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString();

        final Map<String, Object> user = new HashMap<>();
        user.put(KEY_EMAIL, email);
        user.put(KEY_PASSWORD, password);


        //If it is empty email, it'll stop here.
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, getString(R.string.emptyFields), Toast.LENGTH_SHORT).show();
            return;
        }

        //Signing with email and password
        mAuth.signInWithEmailAndPassword(email, password)
                //If email and password match Auth database, it's succesfull
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        finish();

                        Toast.makeText(LoginActivity.this, getString(R.string.logged),
                                Toast.LENGTH_SHORT).show();

                        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                if(documentSnapshot.exists()){
                                    String range = documentSnapshot.getString(KEY_RANGE);


                                    if(range.equals("2")){
                                        finish();
                                        startActivity(new Intent(LoginActivity.this,s_HomeActivity.class));
                                    } else if(range.equals("3")){
                                        finish();
                                        startActivity(new Intent(LoginActivity.this,s_HomeActivity_Teacher.class));
                                    } else if(range.equals("1")){
                                        finish();
                                        startActivity(new Intent(LoginActivity.this,s_HomeActivity_Administrator.class));
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "No tienes rango", Toast.LENGTH_SHORT);
                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "FAILURE", Toast.LENGTH_SHORT);
                            }
                        });




                    }
                })
                //  //If does not match password and email
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, getString(R.string.error),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

    void checkFieldsForEmptyValues(){
        btnLogin = (Button) findViewById(R.id.btnLogin);

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if(email.equals("")|| password.equals("")){
            btnLogin.setEnabled(false);
        } else {
            btnLogin.setEnabled(true);
        }
    }


    //Here it'll open the Sign Up activity
    public void openRegisterWindow(){
        startActivity(new Intent(this, SignUpActivity.class));

    }

    @Override
    public void onClick(View v) {

        if (v == btnLogin) {
            userLogin();
        }

        if(v == textViewRegister){
            openRegisterWindow();
        }



    }


}
