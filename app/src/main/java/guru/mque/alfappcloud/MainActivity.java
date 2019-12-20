package guru.mque.alfappcloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button goLogin;
    private Button Horas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Horas = findViewById(R.id.horasActivity);
        goLogin = findViewById(R.id.goLogin);


        goLogin.setOnClickListener(this);
        Horas.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == goLogin) {

            startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        }

        if(v == Horas){
            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        }
    }
}
