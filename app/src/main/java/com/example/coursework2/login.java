package com.example.coursework2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    settingsDatabaseHelper mySet = new settingsDatabaseHelper(this);
    accountDatabaseHelper myAcc = new accountDatabaseHelper(this);

    boolean loggedIn = false;
    Integer storedAccountID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        storedAccountID = mySet.getAccountID();
        if (storedAccountID != 0) {
            loggedIn = true;
        }

        LinearLayout layout = findViewById(R.id.log_Layout);
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

        Button logBtn = findViewById(R.id.logLogin_Btn);
        logBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText user = findViewById(R.id.logUser_Edit);
                EditText pass = findViewById(R.id.logPass_Edit);
                if (findAccount(user.getText().toString(), pass.getText().toString())) {
                    if (mySet.updateSettings(1, mySet.getNotificationTimer(), mySet.getEmergencyContact(), mySet.getBackgroundColour(), storedAccountID)) {
                        if (storedAccountID != 0) {
                            loggedIn = true;
                        }
                        startActivity(new Intent(login.this, myAccount.class));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect login details, please try again", Toast.LENGTH_LONG).show();
                }

            }
        });

        Button regBtn = findViewById(R.id.logReg_Btn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(login.this, register.class));
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
                startActivity(new Intent(login.this, MainActivity.class));
                return true;
            case R.id.login_item:
                startActivity(new Intent(login.this, login.class));
                return true;
            case R.id.myAccount_item:
                startActivity(new Intent(login.this, myAccount.class));
                return true;
            case R.id.medicalHistory_item:
                if (storedAccountID != 0) {
                    startActivity(new Intent(login.this, medicalHistory.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Please login into an account first", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.settings_item:
                startActivity(new Intent(login.this, settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean findAccount(String user, String pass) {
        Cursor data;
        data = myAcc.getData();
        boolean accountFound = false;

        if (data == null) {
            Toast.makeText(getApplicationContext(), "No accounts found", Toast.LENGTH_LONG).show();
        } else {
            Integer[] id = new Integer[data.getCount()];
            String[] username = new String[data.getCount()];
            String[] password = new String[data.getCount()];
            int i = 0;
            while (data.moveToNext()) {
                id[i] = data.getInt(0);
                username[i] = data.getString(3);
                password[i] = data.getString(5);
                i++;
            }
            for (int j = 0; j < data.getCount(); j++) {
                if (username[j].equals(user) || password[j].equals(pass)) {
                    accountFound = true;
                    storedAccountID = id[j];
                }
            }
        }
        return accountFound;
    }
}
