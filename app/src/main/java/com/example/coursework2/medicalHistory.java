package com.example.coursework2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;

public class medicalHistory extends AppCompatActivity {

    settingsDatabaseHelper mySet = new settingsDatabaseHelper(this);
    medicalDatabaseHelper myMed = new medicalDatabaseHelper(this);

    boolean loggedIn = false;
    Integer storedAccountID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        storedAccountID = mySet.getAccountID();
        if (storedAccountID != 0) {
            loggedIn = true;
        }

        LinearLayout layout = findViewById(R.id.med_Layout);
        if (!mySet.getBackgroundColour().equals("default")) {
            String backgroundColour = mySet.getBackgroundColour();
            switch (backgroundColour) {
                case "Red":
                    layout.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
                case "Blue":
                    layout.setBackgroundColor(getResources().getColor(R.color.blue));
                    break;
                case "Green":
                    layout.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case "Yellow":
                    layout.setBackgroundColor(getResources().getColor(R.color.yellow));
                    break;
                case "Orange":
                    layout.setBackgroundColor(getResources().getColor(R.color.orange));
                    break;
                case "Purple":
                    layout.setBackgroundColor(getResources().getColor(R.color.purple));
                    break;
            }
        } else {
            layout.setBackgroundColor(getResources().getColor(R.color.defaultBackground));
        }

        if (loggedIn) {
            // Gets calculate risk button
            final Integer[] typeValue = {1};
            loadData(typeValue[0]);
            final Button typeBtn = findViewById(R.id.medHisOp_Btn);
            final TextView typeTxt = findViewById(R.id.medHisTypeHeader_Txt);
            typeBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (typeBtn.getText() == getString(R.string.medHisOp_Btn1)) {
                        typeBtn.setText(getString(R.string.medHisOp_Btn2));
                        typeTxt.setText(getString(R.string.medHisTypeHeader_Txt2));
                        typeValue[0] = 2;
                        loadData(typeValue[0]);
                    } else if (typeBtn.getText() == getString(R.string.medHisOp_Btn2)) {
                        typeBtn.setText(getString(R.string.medHisOp_Btn3));
                        typeValue[0] = 3;
                        typeTxt.setText(getString(R.string.medHisTypeHeader_Txt3));
                        loadData(typeValue[0]);
                    } else if (typeBtn.getText() == getString(R.string.medHisOp_Btn3)) {
                        typeBtn.setText(getString(R.string.medHisOp_Btn1));
                        typeValue[0] = 1;
                        typeTxt.setText(getString(R.string.medHisTypeHeader_Txt1));
                        loadData(typeValue[0]);
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (loggedIn) {
            invalidateOptionsMenu();
            menu.findItem(R.id.login_item).setVisible(false);
            menu.findItem(R.id.myAccount_item).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                startActivity(new Intent(medicalHistory.this, MainActivity.class));
                return true;
            case R.id.login_item:
                startActivity(new Intent(medicalHistory.this, login.class));
                return true;
            case R.id.myAccount_item:
                startActivity(new Intent(medicalHistory.this, myAccount.class));
                return true;
            case R.id.medicalHistory_item:
                if (storedAccountID != 0) {
                    startActivity(new Intent(medicalHistory.this, medicalHistory.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Please login into an account first", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.settings_item:
                startActivity(new Intent(medicalHistory.this, settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadData(int type) {
        Cursor data = null;

        if (type == 1) {
            data = myMed.getData("bloodPressure", mySet.getAccountID());
        } else if (type == 2) {
            data = myMed.getData("heartRate", mySet.getAccountID());
        } else if (type == 3) {
            data = myMed.getData("temperature", mySet.getAccountID());
        }

        if (data == null) {
            Toast.makeText(getApplicationContext(), "No Data Stored", Toast.LENGTH_LONG).show();
        } else {
            String[] date = new String[data.getCount()];
            String[] value = new String[data.getCount()];
            String[] risk = new String[data.getCount()];
            int i = 0;
            while (data.moveToNext()) {
                date[i] = data.getString(1).substring(4, 16);
                value[i] = data.getString(3);
                risk[i] = data.getString(4);
                i++;
            }
            ListView dateList = findViewById(R.id.medHisDate_List);
            Collections.reverse(Arrays.asList(date));
            ArrayAdapter dateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, date);
            dateList.setAdapter(dateAdapter);

            ListView valueList = findViewById(R.id.medHisVal_List);
            Collections.reverse(Arrays.asList(date));
            ArrayAdapter valueAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, value);
            valueList.setAdapter(valueAdapter);

            ListView riskList = findViewById(R.id.medHisRisk_List);
            Collections.reverse(Arrays.asList(date));
            ArrayAdapter riskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, risk);
            riskList.setAdapter(riskAdapter);
        }
    }
}
