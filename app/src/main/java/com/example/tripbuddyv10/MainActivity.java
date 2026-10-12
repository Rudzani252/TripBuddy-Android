package com.example.tripbuddyv10;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText password;
    EditText con_pass;

    Button reg;
    Button log;
    SharedPreferences sp1;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSOWRD1= "password";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        con_pass = findViewById(R.id.conPass);
        reg = findViewById(R.id.reg);
        log = findViewById(R.id.log);


        sp1 = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String  name1 = sp1.getString(KEY_NAME,null);
        if (name != null) {
            Intent intent = new Intent(MainActivity.this, home.class);
            startActivity(intent);
        }



        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                String e = email.getText().toString();
                String p = password.getText().toString();
                SharedPreferences.Editor editor = sp1.edit();
                editor.putString(KEY_NAME,n);
                editor.putString(KEY_EMAIL,e);
                editor.putString(KEY_PASSOWRD1,p);
                editor.commit();


                Intent intent = new Intent(MainActivity.this, home.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, login.class);

            }
        });



    }
}