package com.example.coursework2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class editAccount extends AppCompatActivity {

    settingsDatabaseHelper mySet = new settingsDatabaseHelper(this);
    accountDatabaseHelper myAcc = new accountDatabaseHelper(this);

    boolean loggedIn = false;
    Integer storedAccountID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        storedAccountID = mySet.getAccountID();
        if (storedAccountID != 0) {
            loggedIn = true;
        }

        TableLayout layout = findViewById(R.id.edit_Layout);
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

        Button saveBtn = findViewById(R.id.editSaveChanges_Btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText[] fields = new EditText[7];
                fields[0] = findViewById(R.id.editForename_Edit);
                fields[1] = findViewById(R.id.editSurname_Edit);
                fields[2] = findViewById(R.id.editUsername_Edit);
                fields[3] = findViewById(R.id.editEmail_Edit);
                fields[4] = findViewById(R.id.editPassword_Edit);
                EditText confirmPass = findViewById(R.id.editConfirmPassword_Edit);
                fields[5] = findViewById(R.id.editAddress_Edit);
                fields[6] = findViewById(R.id.editPhone_Edit);
                DatePicker dob = findViewById(R.id.editDOB_Date);
                EditText height = findViewById(R.id.editHeight_Edit);
                EditText weight = findViewById(R.id.editWeight_Edit);
                Spinner blood_type = findViewById(R.id.editBloodType_DDL);

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
                    updateData(mySet.getAccountID(), fields[0].getText().toString(), fields[1].getText().toString(), fields[2].getText().toString(), fields[3].getText().toString(), fields[4].getText().toString(), fields[5].getText().toString(), fields[6].getText().toString(), dateOfBirth, Integer.parseInt(height.getText().toString()), Integer.parseInt(weight.getText().toString()), blood_type.getSelectedItem().toString());
                    startActivity(new Intent(editAccount.this, myAccount.class));
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
                startActivity(new Intent(editAccount.this, MainActivity.class));
                return true;
            case R.id.login_item:
                startActivity(new Intent(editAccount.this, login.class));
                return true;
            case R.id.myAccount_item:
                startActivity(new Intent(editAccount.this, myAccount.class));
                return true;
            case R.id.medicalHistory_item:
                if (storedAccountID != 0) {
                    startActivity(new Intent(editAccount.this, medicalHistory.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Please login into an account first", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.settings_item:
                startActivity(new Intent(editAccount.this, settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadData(int ID) {
        EditText forename = findViewById(R.id.editForename_Edit);
        EditText surname = findViewById(R.id.editSurname_Edit);
        EditText username = findViewById(R.id.editUsername_Edit);
        EditText email = findViewById(R.id.editEmail_Edit);
        EditText password = findViewById(R.id.editPassword_Edit);
        EditText confirmPass = findViewById(R.id.editConfirmPassword_Edit);
        EditText address = findViewById(R.id.editAddress_Edit);
        EditText phone = findViewById(R.id.editPhone_Edit);
        DatePicker dob = findViewById(R.id.editDOB_Date);
        EditText height = findViewById(R.id.editHeight_Edit);
        EditText weight = findViewById(R.id.editWeight_Edit);
        Spinner bloodType = findViewById(R.id.editBloodType_DDL);

        String forenameTxt = myAcc.getForename(ID);
        String surnameTxt = myAcc.getSurname(ID);
        String usernameTxt = myAcc.getUsername(ID);
        String emailTxt = myAcc.getEmail(ID);
        String passwordTxt = myAcc.getPassword(ID);
        String addressTxt = myAcc.getAddress(ID);
        String phoneTxt = myAcc.getPhone(ID);
        String dobDate = myAcc.getDOB(ID);
        String heightTxt = myAcc.getHeight(ID).toString();
        String weightTxt = myAcc.getWeight(ID).toString();
        String bloodTypeTxt = myAcc.getBloodType(ID);

        int day = Integer.parseInt(dobDate.substring(0, 1));
        int month = Integer.parseInt(dobDate.substring(2, 3));
        int year = Integer.parseInt(dobDate.substring(4));

        forename.setText(forenameTxt);
        surname.setText(surnameTxt);
        username.setText(usernameTxt);
        email.setText(emailTxt);
        password.setText(passwordTxt);
        confirmPass.setText(passwordTxt);
        address.setText(addressTxt);
        phone.setText(phoneTxt);
        dob.updateDate(year, month, day);
        height.setText(heightTxt);
        weight.setText(weightTxt);
        bloodType.setSelection(getBloodTypePosition(bloodTypeTxt));
    }

    public void updateData(Integer id, String forename, String surname, String username, String email, String password, String address, String phone, String dob, int height, int weight, String bloodType) {
        boolean updateData = myAcc.updateData(id, forename, surname, username, email, password, address, phone, dob, height, weight, bloodType);
        if (updateData) {
            Toast.makeText(getApplicationContext(), "Successful updated account", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Failed to update account", Toast.LENGTH_SHORT).show();
        }
    }

    Integer getBloodTypePosition(String value) {
        int spinnerPosition = 0;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.bloodTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (value != null) {
            spinnerPosition = adapter.getPosition(value);
        }
        return spinnerPosition;
    }
}
