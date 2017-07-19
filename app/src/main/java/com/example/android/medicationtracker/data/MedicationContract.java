package com.example.android.medicationtracker.data;

import android.provider.BaseColumns;

/**
 * API Contract for the medication  app.
 */
public final class MedicationContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private MedicationContract() {}

    /**
     * Inner class that defines constant values for the medication database table.
     * Each entry in the table represents a single patient.
     */
    public static final class MedicationEntry implements BaseColumns {

        /**
         * Name of database table
         */
        public final static String TABLE_NAME = "patients";

        /**
         * Unique ID number for each patient (only for use in the database table).

         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the patient
         * Type: TEXT
         */
        public final static String COLUMN_PATIENT_NAME = "name";

        /**
         * Patient medical condition.
         * Type: TEXT
         */
        public final static String COLUMN_PATIENT_CONDITION = "condition";

        /**
         * Number of Medication of each patient.
         * Type: INTEGER
         */
        public final static String COLUMN_PATIENT_MEDICATION = "medication";


        /**
         * Age of the patient.
         * Type: INTEGER
         */
        public final static String COLUMN_PATIENT_AGE = "age";


        /**
         * Gender of the patient.
         *
         * The only possible values are {@link #GENDER_UNKNOWN}, {@link #GENDER_MALE}, {@link #GENDER_FEMALE}, {@link #GENDER_TRANSMALE},
         * or {@link #GENDER_TRANSFEMALE}.
         * Type: INTEGER
         */
        public final static String COLUMN_PATIENT_GENDER = "gender";

        /**
         * when to go go for next assessment
         * *
         * Type: INTEGER
         */
        public final static String COLUMN_PATIENT_ASSESSMENT = "assessment";

        /**
         * Weight of the patient.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_PATIENT_WEIGHT = "weight";

        /**
         * Possible values for the gender of the patient.
         */
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
        public static final int GENDER_TRANSMALE = 3;
        public static final int GENDER_TRANSFEMALE = 4;


    }

}

