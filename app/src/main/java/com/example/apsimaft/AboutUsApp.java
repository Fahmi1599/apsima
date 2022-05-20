package com.example.apsimaft;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AboutUsApp extends AppCompatActivity {

    Button backmenuus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        backmenuus = findViewById(R.id.backmenuus);

        backmenuus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent backmenuuss = new Intent(AboutUsApp.this, HomeActivity.class);
                startActivity(backmenuuss);
            }
        });
    }
}