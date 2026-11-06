package com.example.coursework2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {

    settingsDatabaseHelper mySet = new settingsDatabaseHelper(this);
    accountDatabaseHelper myAcc = new accountDatabaseHelper(this);

    boolean loggedIn = false;
    Integer storedAccountID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        storedAccountID = mySet.getAccountID();
        if (storedAccountID != 0) {
            loggedIn = true;
        }

        TableLayout layout = findViewById(R.id.reg_Layout);
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

        Button regBtn = findViewById(R.id.regRegisterAccount_Btn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText[] fields = new EditText[7];
                fields[0] = findViewById(R.id.regForename_Edit);
                fields[1] = findViewById(R.id.regSurname_Edit);
                fields[2] = findViewById(R.id.regUsername_Edit);
                fields[3] = findViewById(R.id.regEmail_Edit);
                fields[4] = findViewById(R.id.regPassword_Edit);
                EditText confirmPass = findViewById(R.id.regConfirmPassword_Edit);
                fields[5] = findViewById(R.id.regAddress_Edit);
                fields[6] = findViewById(R.id.regPhone_Edit);
                DatePicker dob = findViewById(R.id.regDOB_Date);
                EditText height = findViewById(R.id.regHeight_Edit);
                EditText weight = findViewById(R.id.regWeight_Edit);
                Spinner blood_type = findViewById(R.id.regBloodType_DDL);

                boolean insertOkay = true;
                for (int i = 0; i < 7; i++) {
                    if (fields[i].getText().toString().equals("")) {
                        insertOkay = false;
                        Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    }
                }

                if (!confirmPass.getText().toString().equals(fields[4].getText().toString())) {
                    insertOkay = false;
                    Toast.makeText(getApplicationContext(), "Password is not the same", Toast.LENGTH_SHORT).show();
                }

                int day = dob.getDayOfMonth();
                int month = dob.getMonth() + 1;
                int year = dob.getYear();
                String dateOfBirth = day + "/" + month + "/" + year;

                if (insertOkay) {
                    addData(fields[0].getText().toString(), fields[1].getText().toString(), fields[2].getText().toString(), fields[3].getText().toString(), fields[4].getText().toString(), fields[5].getText().toString(), fields[6].getText().toString(), dateOfBirth, Integer.parseInt(height.getText().toString()), Integer.parseInt(weight.getText().toString()), blood_type.getSelectedItem().toString());
                    startActivity(new Intent(register.this, login.class));
                }
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
                startActivity(new Intent(register.this, MainActivity.class));
                return true;
            case R.id.login_item:
                startActivity(new Intent(register.this, login.class));
                return true;
            case R.id.myAccount_item:
                startActivity(new Intent(register.this, myAccount.class));
                return true;
            case R.id.medicalHistory_item:
                if (storedAccountID != 0) {
                    startActivity(new Intent(register.this, medicalHistory.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Please login into an account first", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.settings_item:
                startActivity(new Intent(register.this, settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addData(String forename, String surname, String username, String email, String password, String address, String phone, String dob, int height, int weight, String bloodType) {
        boolean insertData = myAcc.addData(forename, surname, username, email, password, address, phone, dob, height, weight, bloodType);
        if (insertData) {
            Toast.makeText(getApplicationContext(), "Successful created account", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Failed to add account", Toast.LENGTH_SHORT).show();
        }
    }
}
