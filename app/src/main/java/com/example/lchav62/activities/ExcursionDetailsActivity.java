package com.example.lchav62.activities;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.lchav62.classes.ExcursionsModel;
import com.example.lchav62.classes.VacationModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExcursionDetailsActivity extends AppCompatActivity {

    // Declare variables for UI elements and data
    EditText excursionTitleEt, excursionDateEt;
    int excursionId, vacationId;
    String titleString, date, vacStartDate, vacEndDate;
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener excursionDateListener;
    DbRepository dbRepository;
    ExcursionsModel excursionsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        // Initialize database repository and UI elements
        dbRepository = new DbRepository(getApplication());
        excursionTitleEt = findViewById(R.id.excursionTitleEt);
        excursionDateEt = findViewById(R.id.excursionDateEt);

        // Get data from the Intent
        vacationId = getIntent().getIntExtra("vacationId", -1);
        excursionId = getIntent().getIntExtra("excursionId", -1);
        titleString = getIntent().getStringExtra("excursionTitle");
        date = getIntent().getStringExtra("excursionStartDate");

        // Retrieve vacation start and end dates from the database
        List<VacationModel> vacationModelList = dbRepository.getAllVacations();
        for (VacationModel v : vacationModelList) {
            if (v.getVacationId() == vacationId) {
                vacStartDate = v.getVacationStartDate();
                vacEndDate = v.getVacationEndDate();
                break;
            }
        }

        // Set the title and date if they are not null
        if (titleString != null) {
            excursionTitleEt.setText(titleString);
            excursionDateEt.setText(date);
        }

        // Create a listener for the excursion date picker
        excursionDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                // Update the label with the selected date
                updateLabelDate();
            }
        };

        // Set the click listener for the excursion date EditText
        excursionDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ExcursionDetailsActivity.this, excursionDateListener, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.excursion_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle the click on the back button in the action bar
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        // Handle the click on the save button in the action bar
        if (id == R.id.saveItem) {
            date = excursionDateEt.getText().toString();
            // Check if the date is within the vacation date range and not empty
            if (dateCheckExcursion() && !(date.equals(""))) {
                if (excursionId == -1) {
                    // Insert a new excursion if the excursionId is -1
                    String title = excursionTitleEt.getText().toString();
                    ExcursionsModel newExcursion = new ExcursionsModel(0, vacationId, title, date);
                    dbRepository.insert(newExcursion);
                    Toast.makeText(ExcursionDetailsActivity.this, "Excursion Added", Toast.LENGTH_LONG).show();
                    finish();
                } else if (excursionId >= 0) {
                    // Update the existing excursion if excursionId is greater than or equal to 0
                    String title = excursionTitleEt.getText().toString();
                    ExcursionsModel newExcursion = new ExcursionsModel(excursionId, vacationId, title, date);
                    dbRepository.update(newExcursion);
                    Toast.makeText(ExcursionDetailsActivity.this, "Excursion Updated", Toast.LENGTH_LONG).show();
                    finish();
                }
            } else {
                Toast.makeText(ExcursionDetailsActivity.this, "Please enter a date within the vacation date range", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        // Handle the click on the delete button in the action bar
        if (id == R.id.deleteItem) {
            for (ExcursionsModel exc : dbRepository.getAllExcursions()) {
                if (exc.getExcursionId() == excursionId) excursionsModel = exc;
            }
            dbRepository.delete(excursionsModel);
            Toast.makeText(ExcursionDetailsActivity.this, excursionsModel.getExcursionTitle() + " was deleted", Toast.LENGTH_LONG).show();
            finish();
        }

        // Handle the click on the alert button in the action bar
        if (id == R.id.alertIem) {
            String title = excursionTitleEt.getText().toString();
            String dateFromScreen = excursionDateEt.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Long trigger = myDate.getTime();
                Intent intent = new Intent(ExcursionDetailsActivity.this, AlertReceiver.class);
                intent.putExtra("key", title);
                PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetailsActivity.this, ++Constants.alertNumber, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Method to check if the selected excursion date is within the vacation date range
    public boolean dateCheckExcursion() {
        Date excursionStartDate = new Date();
        try {
            excursionStartDate = new SimpleDateFormat("MM/dd/yy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date startDateVac = new Date();
        try {
            startDateVac = new SimpleDateFormat("MM/dd/yy").parse(vacStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date endDateVac = new Date();
        try {
            endDateVac = new SimpleDateFormat("MM/dd/yy").parse(vacEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Check if the excursion date is within the vacation date range
        if (startDateVac.after(excursionStartDate) || endDateVac.before(excursionStartDate)) {
            return false;
        } else {
            return true;
        }
    }

    // Method to update the date label with the selected date
    private void updateLabelDate() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        excursionDateEt.setText(sdf.format(calendar.getTime()));
    }
}
