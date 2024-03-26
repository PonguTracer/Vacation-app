package com.example.lchav62.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lchav62.classes.VacationModel;

import java.util.List;

@Dao
public interface DaoForVacation
{
    // Method to insert a new VacationModel into the database.
    // If there is a conflict with an existing record, the new record will be ignored.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(VacationModel vacationModel);

    // Method to update an existing VacationModel in the database.
    @Update
    void update(VacationModel vacationModel);

    // Method to delete an existing VacationModel from the database.
    @Delete
    void delete(VacationModel vacationModel);

    // Method to retrieve all VacationModels from the database and order them by vacation ID in ascending order.
    @Query("SELECT * FROM VACATIONDATA ORDER BY VACATIONID ASC")
    List<VacationModel> getAllVacations();

    // Method to retrieve all VacationModels with a specific vacation ID from the database and order them by vacation ID in ascending order.
    // Note: The parameter "VACATIONID = VACATIONID" seems redundant and will likely be corrected to a valid condition (e.g., "VACATIONID = :vacationId").
    @Query("SELECT * FROM VACATIONDATA WHERE VACATIONID = VACATIONID ORDER BY VACATIONID ASC")
    List<VacationModel> getAllVacationsById();
}
