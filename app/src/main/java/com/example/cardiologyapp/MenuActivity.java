package com.example.cardiologyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {

    private ImageButton imageButtonConsultant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        imageButtonConsultant = findViewById(R.id.imageButtonConsultant);

        imageButtonConsultant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainIntent = new Intent(MenuActivity.this, ConsultantActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
