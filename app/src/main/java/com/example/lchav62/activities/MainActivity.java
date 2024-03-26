package com.example.lchav62.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import com.example.lchav62.R;

public class MainActivity extends AppCompatActivity {

    // Declare a Button variable to represent the "Start" button in the UI
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the "Start" button by finding it in the UI layout
        startBtn = findViewById(R.id.startBtn);

        // Set a click listener for the "Start" button
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the VacationsActivity
                Intent intent = new Intent(MainActivity.this, VacationsActivity.class);
                // Start the VacationsActivity using the created Intent
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource "main_menu" and display it in the ActionBar
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
