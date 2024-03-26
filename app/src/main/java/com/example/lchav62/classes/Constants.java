package com.example.lchav62.classes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Constants {

    // Declare public static variables to be used as constants throughout the application
    public static int alertNumber; // Stores the number of alerts issued
    static int NUMBER_OF_THREADS = 4; // Number of threads to be used in the ExecutorService
    static final ExecutorService NEW_FIXED_THREAD_POOL = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    // Create a new fixed-size thread pool with the specified number of threads
    // This ExecutorService is used to manage background tasks with parallel execution
    static int notificationID = 0; // Stores the ID for each notification issued
}
