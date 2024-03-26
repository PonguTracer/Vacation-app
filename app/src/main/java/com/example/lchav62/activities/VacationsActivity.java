package com.example.lchav62.activities;

// Import statements
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.lchav62.R;
import com.example.lchav62.classes.DbRepository;
import com.example.lchav62.classes.VacationModel;
import com.example.lchav62.classes.VacationRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VacationsActivity extends AppCompatActivity {

    // Declare variables for UI elements and data
    DbRepository dbRepository;
    RecyclerView recyclerView;
    VacationRecyclerViewAdapter vacationAdapter;
    List<VacationModel> dbRepositoryAllVacations;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacations);

        // Initialize UI elements
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.vacationListfab);

        // Create a new instance of the RecyclerViewAdapter
        vacationAdapter = new VacationRecyclerViewAdapter(this);

        // Initialize the database repository and retrieve all vacations from the database
        dbRepository = new DbRepository(getApplication());
        dbRepositoryAllVacations = dbRepository.getAllVacations();

        // Set the retrieved vacations to the RecyclerViewAdapter and display them in the RecyclerView
        vacationAdapter.setVacations(dbRepositoryAllVacations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(vacationAdapter);

        // Set a click listener to the Floating Action Button (FAB) for adding a new vacation
        // When clicked, it opens the VacationDetailsActivity to add a new vacation
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationsActivity.this, VacationDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource file for the activity
        getMenuInflater().inflate(R.menu.vacation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu item clicks
        int id = item.getItemId();

        // If the Refresh option is clicked, update the RecyclerViewAdapter with the latest data
        if (id == R.id.refreshItem) {
            vacationAdapter.setVacations(dbRepository.getAllVacations());
            vacationAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update the RecyclerViewAdapter with the latest data upon resuming the activity
        vacationAdapter.setVacations(dbRepository.getAllVacations());
        vacationAdapter.notifyDataSetChanged();
    }
}
