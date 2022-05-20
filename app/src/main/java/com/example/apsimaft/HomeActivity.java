package com.example.apsimaft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Button btntest,btnaboutus,btntentang,btnmenulist,btnmenuup;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btntest = findViewById(R.id.btntest);
        btnaboutus = findViewById(R.id.btnaboutus);
        btnmenulist = findViewById(R.id.btnmenulist);
        btnmenuup = findViewById(R.id.btnmenuup);
        btntentang = findViewById(R.id.btntentang);

        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                FirebaseAuth.getInstance().signOut();
                Intent a = new Intent(HomeActivity.this , LoginActivity.class);
                startActivity(a);
            }
        });
        btnmenuup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent goUpload = new Intent(HomeActivity.this, UpActivity.class);
                startActivity(goUpload);

            }
        });
        btnmenulist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent goList = new Intent(HomeActivity.this, ViewUploadsActivity.class);
                startActivity(goList);
            }
        });

        btntentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent goAboutUs = new Intent(HomeActivity.this, AboutAppActivity.class);
                startActivity(goAboutUs);
            }
        });
        btnaboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent goUs = new Intent(HomeActivity.this, AboutUsApp.class);
                startActivity(goUs);
            }
        });
        }
    }
