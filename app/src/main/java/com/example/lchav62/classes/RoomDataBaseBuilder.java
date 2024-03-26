package com.example.lchav62.classes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.lchav62.interfaces.DaoForExcursion;
import com.example.lchav62.interfaces.DaoForVacation;

@Database(entities = {VacationModel.class, ExcursionsModel.class}, version = 1, exportSchema = false)
public abstract class RoomDataBaseBuilder extends RoomDatabase {

    // Define abstract methods to get Data Access Objects (DAOs)
    public abstract DaoForVacation DaoForVacation();
    public abstract DaoForExcursion DaoForExcursion();

    // Create a volatile instance of the RoomDatabase
    private static volatile RoomDataBaseBuilder INSTANCE;

    // Static method to get the RoomDatabase instance
    static RoomDataBaseBuilder getDatabase(final Context context) {
        // Check if the instance is null
        if (INSTANCE == null) {
            // Synchronize the class to ensure thread safety
            synchronized (RoomDataBaseBuilder.class) {
                // Check again if the instance is still null to avoid race condition
                if (INSTANCE == null) {
                    // Build the RoomDatabase instance using Room's databaseBuilder method
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    RoomDataBaseBuilder.class, "MyVacationDatabase.db")
                            .fallbackToDestructiveMigration() // Allow destructive migrations (drops tables and recreates on version change)
                            .allowMainThreadQueries() // Allow running database operations on the main thread (not recommended for production)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
