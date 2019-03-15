package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.JavaBean.Workout;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    /**
     * Keep track of the database version
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * Create the name of the Database
     */
    public static final String DATABASE_NAME = "fitness4u";

    /**
     * Create the name of our tables
     */
    public static final String TABLE_WORKOUTS = "workout";
    public static final String TABLE_EXERCISE_IMAGES = "exercise_image";


    /**
     * CREATE column names
     */
    public static final String COLUMN_ID = "id";

    /**
     *  Workout table column names
     */
    public static final String COLUMN_JUMPINGJACK = "jumpingJack"; //exercise
    public static final String COLUMN_PLANK = "plank"; //exercise
    public static final String COLUMN_BURPEE = "burpee"; //exercise
    public static final String COLUMN_LUNGE = "lunge"; //exercise
    public static final String COLUMN_PUSHUP = "pushup"; //exercise
    public static final String COLUMN_SITUP = "situp"; //exercise
    public static final String COLUMN_HIGHKNEE = "highKnee"; //exercise
    public static final String COLUMN_WALLSIT = "wallSit"; //exercise
    public static final String COLUMN_SIDEPLANK = "sidePlank"; //exercise
    public static final String COLUMN_TRICEPDIP = "tricepDip"; //exercise
    public static final String COLUMN_LEGLIFT = "legLift"; //exercise
    public static final String COLUMN_RUSSIANTWIST = "russianTwist"; //exercise
    public static final String COLUMN_SQUAT = "squat"; //exercise

    public static final String COLUMN_COMPLETE = "complete"; //how many workouts completed
    public static final String COLUMN_DATE = "date"; //date completed the workout

    /**
     * CRUD OPERATIONS
     *
     * CREATE
     * READ
     * UPDATE
     * DELETE
     *
     * @author chrisdias
     */

    /**
     * CREATE statements for the Workout table
     */
    public static final String CREATE_WORKOUT_TABLE = "CREATE TABLE " +
            TABLE_WORKOUTS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_JUMPINGJACK + " INTEGER,"
            + COLUMN_PLANK + " INTEGER,"
            + COLUMN_BURPEE + " INTEGER,"
            + COLUMN_LUNGE + " INTEGER,"
            + COLUMN_PUSHUP + " INTEGER,"
            + COLUMN_SITUP + " INTEGER,"
            + COLUMN_HIGHKNEE + " INTEGER,"
            + COLUMN_WALLSIT + " INTEGER,"
            + COLUMN_SIDEPLANK + " INTEGER,"
            + COLUMN_TRICEPDIP + " INTEGER,"
            + COLUMN_LEGLIFT + " INTEGER,"
            + COLUMN_RUSSIANTWIST + " INTEGER,"
            + COLUMN_SQUAT + " INTEGER,"

            + COLUMN_COMPLETE + " INTEGER,"
            + COLUMN_DATE + " TEXT)";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORKOUT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * CREATE the workout table
     */
    public void addWorkout(Workout workout){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_JUMPINGJACK, workout.getJumpingJack());
        values.put(COLUMN_PLANK, workout.getPlank());
        values.put(COLUMN_BURPEE, workout.getBurpee());
        values.put(COLUMN_LUNGE, workout.getLunge());
        values.put(COLUMN_PUSHUP, workout.getPushup());
        values.put(COLUMN_SITUP, workout.getSitup());
        values.put(COLUMN_HIGHKNEE, workout.getHighKnee());
        values.put(COLUMN_WALLSIT, workout.getWallSit());
        values.put(COLUMN_SIDEPLANK, workout.getSidePlank());
        values.put(COLUMN_TRICEPDIP, workout.getTricepDip());
        values.put(COLUMN_LEGLIFT, workout.getLegLift());
        values.put(COLUMN_RUSSIANTWIST, workout.getRussianTwist());
        values.put(COLUMN_SQUAT, workout.getSquat());

        values.put(COLUMN_COMPLETE, workout.getComplete());
        values.put(COLUMN_DATE, workout.getDate());
        db.insert(TABLE_WORKOUTS, null, values);
        db.close();
    }

    /**
     * READ the workout table
     */
    public Workout getWorkout(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Workout workout = null;

        Cursor cursor = db.query(TABLE_WORKOUTS,
                new String[]{COLUMN_ID,
                        COLUMN_JUMPINGJACK,
                        COLUMN_PLANK,
                        COLUMN_BURPEE,
                        COLUMN_LUNGE,
                        COLUMN_PUSHUP,
                        COLUMN_SITUP,
                        COLUMN_HIGHKNEE,
                        COLUMN_WALLSIT,
                        COLUMN_SIDEPLANK,
                        COLUMN_TRICEPDIP,
                        COLUMN_LEGLIFT,
                        COLUMN_RUSSIANTWIST,
                        COLUMN_SQUAT,
                        COLUMN_COMPLETE,
                        COLUMN_DATE},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null,null);

        if(cursor != null){
            cursor.moveToFirst();
            workout = new Workout(
                    Integer.parseInt(cursor.getString(0)),
                    Integer.parseInt(cursor.getString(1)),
                    Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6)),
                    Integer.parseInt(cursor.getString(7)),
                    Integer.parseInt(cursor.getString(8)),
                    Integer.parseInt(cursor.getString(9)),
                    Integer.parseInt(cursor.getString(10)),
                    Integer.parseInt(cursor.getString(11)),
                    Integer.parseInt(cursor.getString(12)),
                    Integer.parseInt(cursor.getString(13)),
                    Integer.parseInt(cursor.getString(14)),
                    cursor.getString(15)
            );
        }
        db.close();
        return workout;
    }

    /**
     * READ all the workouts
     */
    public ArrayList<Workout> getAllWorkouts(){
        ArrayList<Workout> workoutList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_WORKOUTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                workoutList.add(new Workout(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4)),
                        Integer.parseInt(cursor.getString(5)),
                        Integer.parseInt(cursor.getString(6)),
                        Integer.parseInt(cursor.getString(7)),
                        Integer.parseInt(cursor.getString(8)),
                        Integer.parseInt(cursor.getString(9)),
                        Integer.parseInt(cursor.getString(10)),
                        Integer.parseInt(cursor.getString(11)),
                        Integer.parseInt(cursor.getString(12)),
                        Integer.parseInt(cursor.getString(13)),
                        Integer.parseInt(cursor.getString(14)),
                        cursor.getString(15)
                ));
            }while(cursor.moveToNext());
        }
        db.close();
        return workoutList;
    }

    /**
     * UPDATE the workout
     */
    public int updateWorkout(Workout workout){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_JUMPINGJACK, workout.getJumpingJack());
        values.put(COLUMN_PLANK, workout.getPlank());
        values.put(COLUMN_BURPEE, workout.getBurpee());
        values.put(COLUMN_LUNGE, workout.getLunge());
        values.put(COLUMN_PUSHUP, workout.getPushup());
        values.put(COLUMN_SITUP, workout.getSitup());
        values.put(COLUMN_HIGHKNEE, workout.getHighKnee());
        values.put(COLUMN_WALLSIT, workout.getWallSit());
        values.put(COLUMN_SIDEPLANK, workout.getSidePlank());
        values.put(COLUMN_TRICEPDIP, workout.getTricepDip());
        values.put(COLUMN_LEGLIFT, workout.getLegLift());
        values.put(COLUMN_RUSSIANTWIST, workout.getRussianTwist());
        values.put(COLUMN_SQUAT, workout.getSquat());

        values.put(COLUMN_COMPLETE, workout.getComplete());
        values.put(COLUMN_DATE, workout.getDate());
        return db.update(TABLE_WORKOUTS, values, COLUMN_ID + "=?",
                new String[]{String.valueOf(workout.getId())});
    }

    /**
     * DELETE the workout
     */
    public void deleteWorkout(int workout){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORKOUTS, COLUMN_ID + "=?",
                new String[]{String.valueOf(workout)});
        db.close();
    }

}
