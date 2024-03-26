package com.example.lchav62.classes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ExcursionData")
public class ExcursionsModel {

    @PrimaryKey(autoGenerate = true)
    private int excursionId;

    private int vacationId;
    private String excursionTitle;
    private String excursionStartDate;

    // Constructor with no arguments (required for Room database)
    @Ignore
    public ExcursionsModel() {
        // No args
    }

    // Constructor with arguments to initialize the ExcursionsModel object
    public ExcursionsModel(int excursionId, int vacationId, String excursionTitle, String excursionStartDate) {
        this.excursionId = excursionId;
        this.vacationId = vacationId;
        this.excursionTitle = excursionTitle;
        this.excursionStartDate = excursionStartDate;
    }

    // Override the toString method to provide a useful representation of the object
    @Override
    public String toString() {
        return "Excursion{" +
                "excursionId=" + excursionId +
                ", vacationId=" + vacationId +
                ", excursionTitle='" + excursionTitle + '\'' +
                ", excursionStartDate='" + excursionStartDate + '\'' +
                '}';
    }

    // Getter and Setter methods for the vacationId field
    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    // Getter and Setter methods for the excursionTitle field
    public String getExcursionTitle() {
        return excursionTitle;
    }

    public void setExcursionTitle(String excursionTitle) {
        this.excursionTitle = excursionTitle;
    }

    // Getter and Setter methods for the excursionStartDate field
    public String getExcursionStartDate() {
        return excursionStartDate;
    }

    public void setExcursionStartDate(String excursionStartDate) {
        this.excursionStartDate = excursionStartDate;
    }

    // Getter and Setter methods for the excursionId field
    public int getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(int excursionId) {
        this.excursionId = excursionId;
    }

}
