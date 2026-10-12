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

public class login extends AppCompatActivity {
    EditText username;
    EditText pass;
    Button login;
    SharedPreferences sp2;
    private static final String KEY_NAME = "name";
    private static final String KEY_PASSOWRD1= "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.user);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.login);
        sp2 = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = username.getText().toString();
                String pas = pass.getText().toString();
                SharedPreferences.Editor editor = sp2.edit();
                editor.putString(KEY_NAME,u);
                editor.putString("username",u);
                editor.putString(KEY_PASSOWRD1,pas);

                Intent intent = new Intent(login.this, home.class);
                startActivity(intent);
                Toast.makeText(login.this, "login Successful!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}