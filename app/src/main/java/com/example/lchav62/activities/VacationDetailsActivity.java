package com.example.lchav62.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lchav62.R;
import com.example.lchav62.classes.AlertReceiver;
import com.example.lchav62.classes.Constants;
import com.example.lchav62.classes.DbRepository;
import com.example.lchav62.classes.ExcursionRecyclerViewAdapter;
import com.example.lchav62.classes.ExcursionsModel;
import com.example.lchav62.classes.VacationModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetailsActivity extends AppCompatActivity {

    // Declare variables for UI elements and data
    EditText vacationTitleEt, vacationLodgingEt, vacationStartEt, vacationEndEt;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    int vacationId, numExcursion;
    String titleString, endDateString, lodgingString, startDateString;
    VacationModel currentVacationModel;
    Calendar calendarStartDate = Calendar.getInstance(), calendarEndDate = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDateListener, endDateListener;
    private DbRepository dbRepository;
    ExcursionRecyclerViewAdapter excursionRecyclerViewAdapter;
    List<ExcursionsModel> filteredExcursions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_details);

        // Initialize UI elements
        vacationTitleEt = findViewById(R.id.vacationTitleEt);
        vacationLodgingEt = findViewById(R.id.vacationLoggingEt);
        vacationStartEt = findViewById(R.id.startDate);
        vacationEndEt = findViewById(R.id.endDate);
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.enter_excursion_details);

        // Get data from the Intent
        vacationId = getIntent().getIntExtra("vacationId", -1);
        titleString = getIntent().getStringExtra("vacationTitle");
        lodgingString = getIntent().getStringExtra("vacationLodging");
        startDateString = getIntent().getStringExtra("vacationStartDate");
        endDateString = getIntent().getStringExtra("vacationEndDate");

        // Set data to UI elements if not null
        if (titleString != null) {
            vacationTitleEt.setText(titleString);
            vacationLodgingEt.setText(lodgingString);
            vacationStartEt.setText(startDateString);
            vacationEndEt.setText(endDateString);
        }

        // Create listeners for the start and end date pickers
        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // Update the start date EditText with the selected date
                calendarStartDate.set(Calendar.YEAR, year);
                calendarStartDate.set(Calendar.MONTH, monthOfYear);
                calendarStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                vacationStartEt.setText(sdf.format(calendarStartDate.getTime()));
            }
        };

        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // Update the end date EditText with the selected date
                calendarEndDate.set(Calendar.YEAR, year);
                calendarEndDate.set(Calendar.MONTH, monthOfYear);
                calendarEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                vacationEndEt.setText(sdf.format(calendarEndDate.getTime()));
            }
        };

        // Set click listeners for the start and end date EditText fields
        vacationStartEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the start date picker dialog when clicked
                new DatePickerDialog(VacationDetailsActivity.this, startDateListener, calendarStartDate
                        .get(Calendar.YEAR), calendarStartDate.get(Calendar.MONTH),
                        calendarStartDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        vacationEndEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the end date picker dialog when clicked
                new DatePickerDialog(VacationDetailsActivity.this, endDateListener, calendarEndDate
                        .get(Calendar.YEAR), calendarEndDate.get(Calendar.MONTH),
                        calendarEndDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Set a click listener for the floating action button to add excursions
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the ExcursionDetailsActivity with the current vacation ID
                Intent intent = new Intent(VacationDetailsActivity.this, ExcursionDetailsActivity.class);
                intent.putExtra("vacationId", vacationId);
                startActivity(intent);
            }
        });

        // Initialize the filteredExcursions list and the database repository
        filteredExcursions = new ArrayList<>();
        dbRepository = new DbRepository(getApplication());

        // Filter excursions by the current vacation ID and set the RecyclerView adapter
        for (ExcursionsModel e : dbRepository.getAllExcursions()) {
            if (e.getVacationId() == vacationId) filteredExcursions.add(e);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionRecyclerViewAdapter = new ExcursionRecyclerViewAdapter(this);
        excursionRecyclerViewAdapter.setExcursions(filteredExcursions);
        recyclerView.setAdapter(excursionRecyclerViewAdapter);
    }

    // Method to check if the start date is before the end date
    private boolean dateCheck() {
        Date startDateVac = new Date();
        try {
            startDateVac = new SimpleDateFormat("MM/dd/yy").parse(startDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDateVac = new Date();
        try {
            endDateVac = new SimpleDateFormat("MM/dd/yy").parse(endDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Check if the start date is before the end date
        return startDateVac.before(endDateVac);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource "vacation_details_menu" and display it in the ActionBar
        getMenuInflater().inflate(R.menu.vacation_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle the click on the save button in the action bar
        if (id == R.id.saveItem) {
            startDateString = vacationStartEt.getText().toString();
            endDateString = vacationEndEt.getText().toString();

            // Check if the start date is before the end date and they are not empty
            if (dateCheck() && !(startDateString.equals("")) && !(endDateString.equals(""))) {
                if (vacationId == -1) {
                    // Save a new vacation entry to the database
                    String title = vacationTitleEt.getText().toString();
                    String lodging = vacationLodgingEt.getText().toString();
                    VacationModel newVacation = new VacationModel(0, title, lodging, startDateString, endDateString);
                    dbRepository.insert(newVacation);
                    Toast.makeText(VacationDetailsActivity.this, "Vacation added successfully", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    // Update the existing vacation entry in the database
                    String title = vacationTitleEt.getText().toString();
                    String lodging = vacationLodgingEt.getText().toString();
                    String start = vacationStartEt.getText().toString();
                    String end = vacationEndEt.getText().toString();
                    VacationModel newVacation = new VacationModel(vacationId, title, lodging, start, end);
                    dbRepository.update(newVacation);
                    Toast.makeText(VacationDetailsActivity.this, "Vacation updated successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
                return true;
            } else {
                // Show a toast if the dates are invalid or empty
                Toast.makeText(VacationDetailsActivity.this, "Please make the end date after the start date and fill all fields", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        // Handle the click on the delete button in the action bar
        if (id == R.id.deleteItem) {
            // Check if the vacation has any associated excursions
            for (VacationModel vac : dbRepository.getAllVacations()) {
                if (vac.getVacationId() == Integer.parseInt(String.valueOf(vacationId)))
                    currentVacationModel = vac;
            }
            numExcursion = 0;
            for (ExcursionsModel exc : dbRepository.getAllExcursions()) {
                if (exc.getVacationId() == Integer.parseInt(String.valueOf(vacationId)))
                    ++numExcursion;
            }

            // If there are no excursions, delete the vacation
            if (numExcursion == 0) {
                dbRepository.delete(currentVacationModel);
                Toast.makeText(VacationDetailsActivity.this, currentVacationModel.getVacationTitle() + " was deleted", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                // Show a toast if there are associated excursions
                Toast.makeText(VacationDetailsActivity.this, "Can't delete a vacation with excursions", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        // Handle the click on the share button in the action bar
        if (id == R.id.shareItem) {
            // Create and start an intent to share the vacation details via other apps
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, vacationTitleEt.getText().toString() + " " + vacationLodgingEt.getText().toString() + " " +
                    vacationStartEt.getText().toString() + " " + vacationEndEt.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TITLE, "This destination looks amazing!");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        // Handle the click on the start alert button in the action bar
        if (id == R.id.startAlertItem) {
            // Set an alert for the start date of the vacation
            String dateFromScreen = vacationStartEt.getText().toString();
            String myFormat = "MM/dd/yy"; //In which you need to put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Long trigger = myDate.getTime();
                Intent intent = new Intent(VacationDetailsActivity.this, AlertReceiver.class);
                intent.putExtra("key", titleString + " is starting");
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetailsActivity.this, ++Constants.alertNumber, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        // Handle the click on the end alert button in the action bar
        if (id == R.id.endAlertItem) {
            // Set an alert for the end date of the vacation
            String dateFromScreen = vacationStartEt.getText().toString();
            String myFormat = "MM/dd/yy"; //In which you need to put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Long trigger = myDate.getTime();
                Intent intent = new Intent(VacationDetailsActivity.this, AlertReceiver.class);
                intent.putExtra("key", titleString + " is ending");
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetailsActivity.this, ++Constants.alertNumber, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        // Handle the click on the refresh button in the action bar
        if (id == R.id.refreshItem) {
            // Refresh the RecyclerView with the latest excursion data
            filteredExcursions = new ArrayList<>();
            for (ExcursionsModel e : dbRepository.getAllExcursions()) {
                if (e.getVacationId() == vacationId) filteredExcursions.add(e);
            }
            excursionRecyclerViewAdapter.setExcursions(filteredExcursions);
            excursionRecyclerViewAdapter.notifyDataSetChanged();
            Toast.makeText(VacationDetailsActivity.this, "Data is now refreshed", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the RecyclerView with the latest excursion data upon resuming the activity
        filteredExcursions = new ArrayList<>();
        for (ExcursionsModel e : dbRepository.getAllExcursions()) {
            if (e.getVacationId() == vacationId) filteredExcursions.add(e);
        }
        excursionRecyclerViewAdapter.setExcursions(filteredExcursions);
        excursionRecyclerViewAdapter.notifyDataSetChanged();
    }
}
