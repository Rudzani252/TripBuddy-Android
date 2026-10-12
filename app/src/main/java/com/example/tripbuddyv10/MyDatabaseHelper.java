package com.example.tripbuddyv10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "TripInfromation.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TRIPS = "trips";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESTINATION = "destination";
    private static final String COLUMN_START_DATE = "start_date";
    private static final String COLUMN_END_DATE= "end_date";
    private static final String COLUMN_TRANSPORT = "transport";
    private static final String COLUMN_RENT = "rent";
    private static final String COLUMN_MEAL = "meal";
    private static final String COLUMN_ACTIVITY = "activities";
    private static final String COLUMN_ADDITIONAL= "additional_expenses";
    private static final String TABLE_MEMORIES = "memories";
    private static final String COLUMN_MEM_ID = "_mem_id";
    private static final String COLUMN_MEM_TEXT = "memory_text";
    private static final String COLUMN_MEM_IMAGE = "memory_image"; // path or blob
    private static final String COLUMN_MEM_DATE = "created_at"; // metadata
    private static final String COLUMN_MEM_TRIP_ID = "trip_id";

    private static final String COLUMN_MEM = "memory";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_TRIPS + "(" + COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                   COLUMN_DESTINATION + "TEXT," + COLUMN_START_DATE +"TEXT"+ COLUMN_END_DATE + "TEXT" +
                        COLUMN_TRANSPORT+ "DOUBLE,"+COLUMN_RENT+ "DOUBLE,"+COLUMN_MEAL+ "DOUBLE,"+COLUMN_ACTIVITY+"DOUBLE,"+COLUMN_ADDITIONAL +"DOUBLE)";
        String createMemoriesQuery =
                "CREATE TABLE " + TABLE_MEMORIES + " (" +
                        COLUMN_MEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_MEM_TEXT + " TEXT, " +
                        COLUMN_MEM_IMAGE + " BLOB, " + // store as BLOB or file path
                        COLUMN_MEM_DATE + " TEXT, " +
                        COLUMN_MEM_TRIP_ID + " INTEGER, " +
                        "FOREIGN KEY(" + COLUMN_MEM_TRIP_ID + ") REFERENCES " + TABLE_TRIPS + "(" + COLUMN_ID + "));";


        db.execSQL(query);
        db.execSQL(createMemoriesQuery);;



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE  IF EXISTS " + TABLE_TRIPS);
        onCreate(db);

    }
    void planTrip(String destination, int transport, int rent, int meal, int activities, int addtional_expenses) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DESTINATION, destination);
        cv.put(COLUMN_TRANSPORT, transport);
        cv.put(COLUMN_RENT, rent);
        cv.put(COLUMN_MEAL, meal);
        cv.put(COLUMN_ACTIVITY, activities);
        cv.put(COLUMN_ADDITIONAL, addtional_expenses);
        long result=db.insert(TABLE_TRIPS, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Faild to save", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "Saved info", Toast.LENGTH_SHORT).show();
        }

    }

    Cursor showData() {
        String query = "SELECT * FROM " + TABLE_TRIPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    void addMemory(String text, byte[] imageBytes, String createdAt, int tripId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MEM_TEXT, text);
        cv.put(COLUMN_MEM_IMAGE, imageBytes);
        cv.put(COLUMN_MEM_DATE, createdAt);
        cv.put(COLUMN_MEM_TRIP_ID, tripId);

        long result = db.insert(TABLE_MEMORIES, null, cv);
        showResult(result, "Memory");

    }
    int updateMemory(int memId, String newText, byte[] newImageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MEM_TEXT, newText);
        cv.put(COLUMN_MEM_IMAGE, newImageBytes);

        return db.update(TABLE_MEMORIES, cv, COLUMN_MEM_ID + "=?", new String[]{String.valueOf(memId)});
    }
    Cursor showMemoriesForTrip(int tripId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MEMORIES + " WHERE " + COLUMN_MEM_TRIP_ID + "=?",
                new String[]{String.valueOf(tripId)});
    }
    private void showResult(long result, String type) {
        if (result == -1) {
            Toast.makeText(context, type + " save failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, type + " saved", Toast.LENGTH_SHORT).show();
        }
}}
