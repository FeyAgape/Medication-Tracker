# Medication-Tracker

A Habit Tracker App

The structure of a Habit Tracking app which would allow a user to store and track their habits over time. 
This project will not have any UI components; instead, will focus on what happens behind the scenes, 
practicing how to design and implement a simple database.

This project is about combining various ideas and skills we’ve been practicing throughout the course. They include:

Creating a SQLite table in your app

Populating that table with new entries

Modifying the entries

Displaying the contents of the table to users

Overall Layout: No UI is required for this project.

Table Definition: There exists a contract class that defines name of table and constants. Inside the contract class, there is an inner class for each table created.

Table Creation: There exists a subclass of SQLiteOpenHelper that overrides onCreate() and onUpgrade().

Data Insertion: There is a single insert method that adds at least two values of two different datatypes (e.g. INTEGER, STRING) into the database using a ContentValues object and the insert() method.

Data Reading: There is a single read method that returns a Cursor object. It should get the data repository in read and use the query() method to retrieve at least one column of data.

External Libraries and Packages: No external libraries (e.g. Realm) are used for the database code, and no Content Providers is used. All data insertion and reading should be done using direct method calls to the SQLite database in the SQLiteOpenHelper class.
