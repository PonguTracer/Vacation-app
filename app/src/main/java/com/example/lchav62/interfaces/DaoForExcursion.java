package com.example.lchav62.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lchav62.classes.ExcursionsModel;

import java.util.List;

@Dao
public interface DaoForExcursion
{
    // Method to insert a new ExcursionsModel into the database.
    // If there is a conflict with an existing record, the new record will be ignored.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ExcursionsModel excursionsModel);

    // Method to update an existing ExcursionsModel in the database.
    @Update
    void update(ExcursionsModel excursionsModel);

    // Method to delete an existing ExcursionsModel from the database.
    @Delete
    void delete(ExcursionsModel excursionsModel);

    // Method to retrieve all ExcursionsModels from the database and order them by excursion title in ascending order.
    @Query("SELECT * FROM EXCURSIONDATA ORDER BY EXCURSIONTITLE ASC")
    List<ExcursionsModel> getAllExcursions();
}
