package com.example.coursework2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class settingsDatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName = "settings.db";
    private static final String tableName = "settings_data";
    private static final String col1 = "ID";
    private static final String col2 = "NOTIFICATION_TIMER";
    private static final String col3 = "EMERGENCY_CONTACT";
    private static final String col4 = "BACKGROUND_COLOUR";
    private static final String col5 = "ACCOUNT_ID";

    settingsDatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + tableName + " (" + col1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col2 + " TEXT, " +
                col3 + " TEXT, " +
                col4 + " TEXT, " +
                col5 + " INTEGER);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + tableName);
    }

    void populateSettings() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(col2, "No Reminder");
        content.put(col3, "5555");
        content.put(col4, "Default");
        content.put(col5, 0);

        db.insert(tableName, null, content);
    }

    boolean updateSettings(int id, String notificationTimer, String emergencyContact, String backgroundColour, Integer accountID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(col1, id);
        content.put(col2, notificationTimer);
        content.put(col3, emergencyContact);
        content.put(col4, backgroundColour);
        content.put(col5, accountID);

        long result = db.update(tableName, content, col1 + " = " + id, null);

        return result != -1;
    }

    Cursor getSettings() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + tableName, null);
    }

    String getNotificationTimer() {
        Cursor data = getSettings();
        String[] notificationTimer = new String[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            notificationTimer[i] = data.getString(1);
            i++;
        }
        return notificationTimer[0];
    }

    String getEmergencyContact() {
        Cursor data = getSettings();
        String[] emergencyContact = new String[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            emergencyContact[i] = data.getString(2);
            i++;
        }
        return emergencyContact[0];
    }

    String getBackgroundColour() {
        Cursor data = getSettings();
        String[] backgroundColour = new String[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            backgroundColour[i] = data.getString(3);
            i++;
        }
        return backgroundColour[0];
    }

    Integer getAccountID() {
        Cursor data = getSettings();
        Integer[] ID = new Integer[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            ID[i] = data.getInt(4);
            i++;
        }
        return ID[0];
    }

}
