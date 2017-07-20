package com.example.android.medicationtracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.medicationtracker.data.MedicationContract.MedicationEntry;
import com.example.android.medicationtracker.data.MedicationDbHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class MainActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private MedicationDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new MedicationDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Helper method to display information in the onscreen TextView about the state of
     * the patient database.
     */
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                MedicationEntry._ID,
                MedicationEntry.COLUMN_PATIENT_NAME,
                MedicationEntry.COLUMN_PATIENT_CONDITION,
                MedicationEntry.COLUMN_PATIENT_MEDICATION,
                MedicationEntry.COLUMN_PATIENT_AGE,
                MedicationEntry.COLUMN_PATIENT_GENDER,
                MedicationEntry.COLUMN_PATIENT_WEIGHT,
                MedicationEntry.COLUMN_PATIENT_ASSESSMENT};

        // Perform a query on the patient table
        Cursor cursor = db.query(
                MedicationEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,  //// Don't filter by row groups
                null);                   // The sort order


        TextView displayView = (TextView) findViewById(R.id.text_view_patients);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The patients table contains <number of rows in Cursor> patients.
            // _id - name - condition - medication - age - gender - weight - assessment
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The patients table contains " + cursor.getCount() + " patients.\n\n");
            displayView.append(MedicationEntry._ID + " - " +
                    MedicationEntry.COLUMN_PATIENT_NAME + " - " +
                    MedicationEntry.COLUMN_PATIENT_CONDITION + " - " +
                    MedicationEntry.COLUMN_PATIENT_MEDICATION + " - " +
                    MedicationEntry.COLUMN_PATIENT_AGE + " - " +
                    MedicationEntry.COLUMN_PATIENT_GENDER + " - " +
                    MedicationEntry.COLUMN_PATIENT_WEIGHT + " - " +
                    MedicationEntry.COLUMN_PATIENT_ASSESSMENT + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(MedicationEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(MedicationEntry.COLUMN_PATIENT_NAME);
            int conditionColumnIndex = cursor.getColumnIndex(MedicationEntry.COLUMN_PATIENT_CONDITION);
            int medicationColumnIndex = cursor.getColumnIndex(MedicationEntry.COLUMN_PATIENT_MEDICATION);
            int ageColumnIndex = cursor.getColumnIndex(MedicationEntry.COLUMN_PATIENT_AGE);
            int genderColumnIndex = cursor.getColumnIndex(MedicationEntry.COLUMN_PATIENT_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(MedicationEntry.COLUMN_PATIENT_WEIGHT);
            int assessmentColumnIndex = cursor.getColumnIndex(MedicationEntry.COLUMN_PATIENT_ASSESSMENT);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentCondition = cursor.getString(conditionColumnIndex);
                int currentMedication = cursor.getInt(medicationColumnIndex);
                int currentAge = cursor.getInt(ageColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);
                int currentAssessment = cursor.getInt(assessmentColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentCondition + " - " +
                        currentMedication + " - " +
                        currentAge + " - " +
                        currentGender + " - " +
                        currentWeight + " - " +
                        currentAssessment));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded patient data into the database. For debugging purposes only.
     */
    private void insertPatient() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and dummy patient attributes are the values.
        ContentValues values = new ContentValues();
        values.put(MedicationEntry.COLUMN_PATIENT_NAME, "John");
        values.put(MedicationEntry.COLUMN_PATIENT_CONDITION, "Diabetic");
        values.put(MedicationEntry.COLUMN_PATIENT_MEDICATION, 2);
        values.put(MedicationEntry.COLUMN_PATIENT_AGE, 57);
        values.put(MedicationEntry.COLUMN_PATIENT_GENDER, MedicationEntry.GENDER_MALE);
        values.put(MedicationEntry.COLUMN_PATIENT_WEIGHT, 72);
        values.put(MedicationEntry.COLUMN_PATIENT_ASSESSMENT, 47);

        // Insert a new row for dummy patient in the database, returning the ID of that new row.
        // The first argument for db.insert() is the patients table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for John.
        long newRowId = db.insert(MedicationEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_main.xmlle.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPatient();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
