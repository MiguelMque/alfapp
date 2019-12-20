package guru.mque.alfappcloud;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtPassword, edtEmail, edtName;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        btnSignUp = findViewById(R.id.btnSignUp);

        edtPassword.addTextChangedListener(mTextWatcher);
        edtEmail.addTextChangedListener(mTextWatcher);
        edtName.addTextChangedListener(mTextWatcher);


        checkFieldsForEmptyValues();

        btnSignUp.setOnClickListener(this);
    }

public void registerUser(){
        final String email =  edtEmail.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();
        final String name = edtName.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Invalid email");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            Map<String, Object> user = new HashMap<>();
            user.put("range", "2");
            user.put("name", name);
            db.collection("Users").document(mAuth.getUid()).set(user);
            startActivity(new Intent(SignUpActivity.this, s_HomeActivity.class));
        }
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
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if(name.equals("") || email.equals("") || password.equals("")){
            btnSignUp.setEnabled(false);
        } else {
            btnSignUp.setEnabled(true);
        }
    }


    @Override
    public void onClick(View v) {
        if(v == btnSignUp){
    registerUser();
        }
    }
}
