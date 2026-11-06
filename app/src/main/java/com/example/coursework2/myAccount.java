package com.example.coursework2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class myAccount extends AppCompatActivity {

    settingsDatabaseHelper mySet = new settingsDatabaseHelper(this);
    accountDatabaseHelper myAcc = new accountDatabaseHelper(this);

    boolean loggedIn = false;
    Integer storedAccountID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        storedAccountID = mySet.getAccountID();
        if (storedAccountID != 0) {
            loggedIn = true;
        }

        LinearLayout layout = findViewById(R.id.acc_Layout);
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

        loadData(storedAccountID);

        Button editBtn = findViewById(R.id.accEditAccount_Btn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(myAccount.this, editAccount.class));
            }
        });

        Button medBtn = findViewById(R.id.accMedicalHistory_Btn);
        medBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(myAccount.this, medicalHistory.class));
            }
        });

        Button logOutBtn = findViewById(R.id.accLogOut_Btn);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mySet.updateSettings(1, mySet.getNotificationTimer(), mySet.getEmergencyContact(), mySet.getBackgroundColour(), 0);
                startActivity(new Intent(myAccount.this, MainActivity.class));
            }
        });
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
                startActivity(new Intent(myAccount.this, MainActivity.class));
                return true;
            case R.id.login_item:
                startActivity(new Intent(myAccount.this, login.class));
                return true;
            case R.id.myAccount_item:
                startActivity(new Intent(myAccount.this, myAccount.class));
                return true;
            case R.id.medicalHistory_item:
                if (storedAccountID != 0) {
                    startActivity(new Intent(myAccount.this, medicalHistory.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Please login into an account first", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.settings_item:
                startActivity(new Intent(myAccount.this, settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadData(int ID) {
        TextView username = findViewById(R.id.accUsername_Txt);
        TextView forename = findViewById(R.id.accMedForename_Txt);
        TextView surname = findViewById(R.id.accMedSurname_Txt);
        TextView dob = findViewById(R.id.accMedDOB_Txt);
        TextView height = findViewById(R.id.accMedHeight_Txt);
        TextView weight = findViewById(R.id.accMedWeight_Txt);
        TextView bloodType = findViewById(R.id.accMedBloodType_Txt);

        String usernameTxt = myAcc.getUsername(ID);
        String forenameTxt = getString(R.string.accMedForename_Txt) + " " + myAcc.getForename(ID);
        String surnameTxt = getString(R.string.accMedSurname_Txt) + " " + myAcc.getSurname(ID);
        String dobTxt = getString(R.string.accMedDOB_Txt) + " " + myAcc.getDOB(ID);
        String heightTxt = getString(R.string.accMedHeight_Txt) + " " + myAcc.getHeight(ID);
        String weightTxt = getString(R.string.accMedWeight_Txt) + " " + myAcc.getWeight(ID);
        String bloodTypeTxt = getString(R.string.accMedBloodType_Txt) + " " + myAcc.getBloodType(ID);

        username.setText(usernameTxt);
        forename.setText(forenameTxt);
        surname.setText(surnameTxt);
        dob.setText(dobTxt);
        height.setText(heightTxt);
        weight.setText(weightTxt);
        bloodType.setText(bloodTypeTxt);
    }
}
