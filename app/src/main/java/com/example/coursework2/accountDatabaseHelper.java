package com.example.coursework2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class accountDatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName = "accountEntries.db";
    private static final String tableName = "accountEntries_data";
    private static final String col1 = "ID";
    private static final String col2 = "FORENAME";
    private static final String col3 = "SURNAME";
    private static final String col4 = "USERNAME";
    private static final String col5 = "EMAIL";
    private static final String col6 = "PASSWORD";
    private static final String col7 = "ADDRESS";
    private static final String col8 = "PHONE";
    private static final String col9 = "DOB";
    private static final String col10 = "HEIGHT";
    private static final String col11 = "WEIGHT";
    private static final String col12 = "BLOOD_TYPE";

    accountDatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + tableName + " (" + col1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col2 + " TEXT, " +
                col3 + " TEXT, " +
                col4 + " TEXT, " +
                col5 + " TEXT, " +
                col6 + " TEXT, " +
                col7 + " TEXT, " +
                col8 + " TEXT, " +
                col9 + " TEXT, " +
                col10 + " INTEGER, " +
                col11 + " INTEGER, " +
                col12 + " TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + tableName);
    }

    boolean addData(String forename, String surname, String username, String email, String password, String address, String phone, String dob, int height, int weight, String bloodType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(col2, forename);
        content.put(col3, surname);
        content.put(col4, username);
        content.put(col5, email);
        content.put(col6, password);
        content.put(col7, address);
        content.put(col8, phone);
        content.put(col9, dob);
        content.put(col10, height);
        content.put(col11, weight);
        content.put(col12, bloodType);

        long result = db.insert(tableName, null, content);

        return result != -1;
    }

    boolean updateData(int id, String forename, String surname, String username, String email, String password, String address, String phone, String dob, int height, int weight, String bloodType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(col1, id);
        content.put(col2, forename);
        content.put(col3, surname);
        content.put(col4, username);
        content.put(col5, email);
        content.put(col6, password);
        content.put(col7, address);
        content.put(col8, phone);
        content.put(col9, dob);
        content.put(col10, height);
        content.put(col11, weight);
        content.put(col12, bloodType);

        long result = db.update(tableName, content, col1 + " = " + id, null);

        return result != -1;
    }

    Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + tableName, null);
    }

    private Cursor getAccount(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + tableName + " WHERE " + col1 + "='" + id + "'", null);
    }

    String getForename(Integer ID) {
        Cursor data = getAccount(ID);
        String[] forename = new String[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            forename[i] = data.getString(1);
            i++;
        }
        return forename[0];
    }

    String getSurname(Integer ID) {
        Cursor data = getAccount(ID);
        String[] surname = new String[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            surname[i] = data.getString(2);
            i++;
        }
        return surname[0];
    }

    String getUsername(Integer ID) {
        Cursor data = getAccount(ID);
        String[] username = new String[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            username[i] = data.getString(3);
            i++;
        }
        return username[0];
    }

    String getEmail(Integer ID) {
        Cursor data = getAccount(ID);
        String[] email = new String[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            email[i] = data.getString(4);
            i++;
        }
        return email[0];
    }

    String getPassword(Integer ID) {
        Cursor data = getAccount(ID);
        String[] password = new String[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            password[i] = data.getString(5);
            i++;
        }
        return password[0];
    }

    String getAddress(Integer ID) {
        Cursor data = getAccount(ID);
        String[] address = new String[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            address[i] = data.getString(6);
            i++;
        }
        return address[0];
    }

    String getPhone(Integer ID) {
        Cursor data = getAccount(ID);
        String[] phone = new String[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            phone[i] = data.getString(7);
            i++;
        }
        return phone[0];
    }

    String getDOB(Integer ID) {
        Cursor data = getAccount(ID);
        String[] DOB = new String[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            DOB[i] = data.getString(8);
            i++;
        }
        return DOB[0];
    }

    Integer getHeight(Integer ID) {
        Cursor data = getAccount(ID);
        Integer[] height = new Integer[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            height[i] = data.getInt(9);
            i++;
        }
        return height[0];
    }

    Integer getWeight(Integer ID) {
        Cursor data = getAccount(ID);
        Integer[] weight = new Integer[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            weight[i] = data.getInt(10);
            i++;
        }
        return weight[0];
    }

    String getBloodType(Integer ID) {
        Cursor data = getAccount(ID);
        String[] bloodType = new String[data.getCount()];
        int i = 0;
        while (data.moveToNext()) {
            bloodType[i] = data.getString(11);
            i++;
        }
        return bloodType[0];
    }
}
