package com.example.android.medicationtracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.medicationtracker.data.MedicationContract.MedicationEntry;
import com.example.android.medicationtracker.data.MedicationDbHelper;

public class EditorActivity extends AppCompatActivity {

    /** EditText field to enter the patient's name */
    private EditText mNameEditText;

    /** EditText field to enter the  patient's condition */
    private EditText mConditionEditText;

    /** EditText field to enter the  patient's medication */
    private EditText mMedicationEditText;

    /** EditText field to enter the  patient's age */
    private EditText mAgeEditText;

    /** EditText field to enter the  patient's weight */
    private EditText mWeightEditText;

    /** EditText field to enter the  patient's next visit */
    private EditText mAssessmentEditText;

    /** EditText field to enter the  patient's gender */
    private Spinner mGenderSpinner;

    /**
     * Gender of the patient. The possible valid values are in the MedicationContract.java file:
     * {@link MedicationEntry#GENDER_UNKNOWN}, {@link MedicationEntry#GENDER_MALE},
     * {@link MedicationEntry#GENDER_FEMALE}, {@link MedicationEntry#GENDER_TRANSMALE} or {@link MedicationEntry#GENDER_TRANSFEMALE}.
     */
    private int mGender = MedicationEntry.GENDER_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_patient_name);
        mConditionEditText = (EditText) findViewById(R.id.edit_patient_condition);
        mMedicationEditText = (EditText) findViewById(R.id.edit_medication);
        mAgeEditText = (EditText) findViewById(R.id.edit_age);
        mWeightEditText = (EditText) findViewById(R.id.edit_patient_weight);
        mAssessmentEditText = (EditText) findViewById(R.id.edit_assessment);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the patient.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = MedicationEntry.GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = MedicationEntry.GENDER_FEMALE;
                    } else if (selection.equals(getString(R.string.trans_female))) {
                        mGender = MedicationEntry.GENDER_TRANSFEMALE;
                    } else if (selection.equals(getString(R.string.trans_male))){
                        mGender = MedicationEntry.GENDER_TRANSMALE;
                    } else {
                        mGender = MedicationEntry.GENDER_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = MedicationEntry.GENDER_UNKNOWN;
            }
        });
    }

    /**
     * Get user input from editor and save new patient into database.
     */
    private void insertPatient() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String conditionString = mConditionEditText.getText().toString().trim();
        String medicationString = mMedicationEditText.getText().toString().trim();
        int medication = Integer.parseInt (medicationString);
        String ageString = mAgeEditText.getText().toString().trim();
        int age = Integer.parseInt(ageString);
        String weightString = mWeightEditText.getText().toString().trim();
        int weight = Integer.parseInt(weightString);
        String assessmentString = mAssessmentEditText.getText().toString().trim();
        int assessment = Integer.parseInt(assessmentString);

        // Create database helper
        MedicationDbHelper mDbHelper = new MedicationDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(MedicationEntry.COLUMN_PATIENT_NAME, nameString);
        values.put(MedicationEntry.COLUMN_PATIENT_CONDITION, conditionString);
        values.put(MedicationEntry.COLUMN_PATIENT_MEDICATION, medication);
        values.put(MedicationEntry.COLUMN_PATIENT_AGE, age);
        values.put(MedicationEntry.COLUMN_PATIENT_GENDER, mGender);
        values.put(MedicationEntry.COLUMN_PATIENT_WEIGHT, weight);
        values.put(MedicationEntry.COLUMN_PATIENT_ASSESSMENT, assessment);

        // Insert a new row for patients in the database, returning the ID of that new row.
        long newRowId = db.insert(MedicationEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving patient", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Patient saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save patient to database
                insertPatient();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (MainActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}