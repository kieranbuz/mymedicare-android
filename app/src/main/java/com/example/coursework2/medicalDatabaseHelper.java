package com.example.coursework2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class medicalDatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName = "userEntries.db";
    private static final String tableName = "userEntries_data";
    private static final String col1 = "ID";
    private static final String col2 = "DATE";
    private static final String col3 = "TYPE";
    private static final String col4 = "VALUE";
    private static final String col5 = "RISK_TYPE";
    private static final String col6 = "ACCOUNT_ID";

    medicalDatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + tableName + " (" + col1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col2 + " TEXT, " +
                col3 + " TEXT, " +
                col4 + " TEXT, " +
                col5 + " TEXT, " +
                col6 + " INTEGER);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + tableName);
    }

    boolean addData(String date, String type, String value, String riskType, Integer accountID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(col2, date);
        content.put(col3, type);
        content.put(col4, value);
        content.put(col5, riskType);
        content.put(col6, accountID);

        long result = db.insert(tableName, null, content);

        return result != -1;
    }

    Cursor getData(String type, Integer ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + tableName + " WHERE " + col3 + "='" + type + "'" + " AND " + col6 + "='" + ID + "'", null);
    }

}
