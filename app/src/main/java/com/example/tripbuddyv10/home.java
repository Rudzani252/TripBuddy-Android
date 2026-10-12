package com.example.tripbuddyv10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class home extends AppCompatActivity {
    Button plan;
    Button mem;
    Button logout;
    TextView name;
    SharedPreferences sp3;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        plan = findViewById(R.id.plan);
        mem = findViewById(R.id.memories);
        logout = findViewById(R.id.logout);
        sp3 = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sp3.getString(KEY_NAME, null);
        String email = sp3.getString(KEY_EMAIL, null);



        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp3.edit();
                editor.clear();
                editor.commit();
                finish();;
                Toast.makeText(home.this, "Logout successfully", Toast.LENGTH_SHORT).show();
                
            }
        });

        mem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100)
                        .withEndAction(() -> {
                            v.animate().scaleX(1f).scaleY(1f).setDuration(100);

                            // Start activity AFTER animation
                            Intent intent = new Intent(home.this, memories.class);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        });
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.animate().scaleX(0.9f).scaleY(0.9f).setDuration(100)
                        .withEndAction(() -> {
                            v.animate().scaleX(1f).scaleY(1f).setDuration(100);

                            // Start activity AFTER animation
                            Intent intent = new Intent(home.this, login.class);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        });
            }
        });

           }
       }


