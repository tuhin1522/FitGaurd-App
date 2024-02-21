package com.tuhin.fitgaurdapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "reminder";
    private static final String TABLE_NAME = "tbl_reminder";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";

    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_DATE + " TEXT," +
                COLUMN_TIME + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public String addReminder(String title, String date, String time) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME, time);

        long result = database.insert(TABLE_NAME, null, values);

        if (result == -1) {
            return "Failed";
        } else {
            return "Successfully inserted";
        }
    }

    public Cursor readallreminders() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM tbl_reminder ORDER BY id ASC";
        return database.rawQuery(query, null);
    }


    public void deleteReminder(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    public void updateReminder(NotificationModel updatedNotification) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, updatedNotification.getTitle());
        values.put(COLUMN_DATE, updatedNotification.getDate());
        values.put(COLUMN_TIME, updatedNotification.getTime());

        database.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(updatedNotification.getId())});

        database.close();
    }

}
