package com.example.lchav62.classes;

import static com.example.lchav62.classes.Constants.NEW_FIXED_THREAD_POOL;

import android.app.Application;

import com.example.lchav62.interfaces.DaoForExcursion;
import com.example.lchav62.interfaces.DaoForVacation;

import java.util.List;

public class DbRepository {

    // Declare DAOs and lists to hold vacation and excursion data
    DaoForVacation daoForVacation;
    DaoForExcursion daoForExcursion;
    List<ExcursionsModel> excursionsModelList;
    List<VacationModel> vacationModelList;
    List<VacationModel> vacationModelListById;

    // Constructor: Initialize the database and DAOs when the repository is created
    public DbRepository(Application application) {
        RoomDataBaseBuilder db = RoomDataBaseBuilder.getDatabase(application);
        daoForVacation = db.DaoForVacation();
        daoForExcursion = db.DaoForExcursion();
    }

    // Retrieve all vacations from the database using a background thread
    public List<VacationModel> getAllVacations() {
        NEW_FIXED_THREAD_POOL.execute(() -> {
            vacationModelList = daoForVacation.getAllVacations();
        });
        try {
            Thread.sleep(1000); // Wait for the background thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return vacationModelList; // Return the list of vacations
    }

    // Retrieve all vacations by ID from the database using a background thread
    public List<VacationModel> getAllVacationsById() {
        NEW_FIXED_THREAD_POOL.execute(() -> {
            vacationModelListById = daoForVacation.getAllVacationsById();
        });
        try {
            Thread.sleep(1000); // Wait for the background thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return vacationModelListById; // Return the list of vacations by ID
    }

    // Insert a new vacation into the database using a background thread
    public void insert(VacationModel vacationModel) {
        NEW_FIXED_THREAD_POOL.execute(() -> {
            daoForVacation.insert(vacationModel);
        });
        try {
            Thread.sleep(1000); // Wait for the background thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Update an existing vacation in the database using a background thread
    public void update(VacationModel vacationModel) {
        NEW_FIXED_THREAD_POOL.execute(() -> {
            daoForVacation.update(vacationModel);
        });
        try {
            Thread.sleep(1000); // Wait for the background thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Delete a vacation from the database using a background thread
    public void delete(VacationModel vacationModel) {
        NEW_FIXED_THREAD_POOL.execute(() -> {
            daoForVacation.delete(vacationModel);
        });
        try {
            Thread.sleep(1000); // Wait for the background thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all excursions from the database using a background thread
    public List<ExcursionsModel> getAllExcursions() {
        NEW_FIXED_THREAD_POOL.execute(() -> {
            excursionsModelList = daoForExcursion.getAllExcursions();
        });
        try {
            Thread.sleep(1000); // Wait for the background thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return excursionsModelList; // Return the list of excursions
    }

    // Insert a new excursion into the database using a background thread
    public void insert(ExcursionsModel excursionsModel) {
        NEW_FIXED_THREAD_POOL.execute(() -> {
            daoForExcursion.insert(excursionsModel);
        });
        try {
            Thread.sleep(1000); // Wait for the background thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Update an existing excursion in the database using a background thread
    public void update(ExcursionsModel excursionsModel) {
        NEW_FIXED_THREAD_POOL.execute(() -> {
            daoForExcursion.update(excursionsModel);
        });
        try {
            Thread.sleep(1000); // Wait for the background thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Delete an excursion from the database using a background thread
    public void delete(ExcursionsModel excursionsModel) {
        NEW_FIXED_THREAD_POOL.execute(() -> {
            daoForExcursion.delete(excursionsModel);
        });
        try {
            Thread.sleep(1000); // Wait for the background thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
