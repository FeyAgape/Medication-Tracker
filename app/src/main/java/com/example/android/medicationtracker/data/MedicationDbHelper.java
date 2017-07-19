package com.example.android.medicationtracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.medicationtracker.data.MedicationContract.MedicationEntry;

/**
 * Database helper for Medications app. Manages database creation and version management.
 */
public class MedicationDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = MedicationDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "clinic.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link MedicationDbHelper}.
     *
     * @param context of the app
     */
    public MedicationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the patients table
        String SQL_CREATE_PATIENTS_TABLE =  "CREATE TABLE " + MedicationEntry.TABLE_NAME + " ("
                + MedicationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MedicationEntry.COLUMN_PATIENT_NAME + " TEXT NOT NULL, "
                + MedicationEntry.COLUMN_PATIENT_CONDITION + " TEXT NOT NULL, "
                + MedicationEntry.COLUMN_PATIENT_MEDICATION + "INTEGER NOT NULL DEFAULT 0); "
                + MedicationEntry.COLUMN_PATIENT_AGE + " INTEGER NOT NULL, "
                + MedicationEntry.COLUMN_PATIENT_GENDER + " INTEGER NOT NULL, "
                + MedicationEntry.COLUMN_PATIENT_WEIGHT + " INTEGER NOT NULL DEFAULT 0);"
                + MedicationEntry.COLUMN_PATIENT_ASSESSMENT + " INTEGER NOT NULL DEFAULT 0); ";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PATIENTS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}