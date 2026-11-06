package com.example.coursework2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class settings extends AppCompatActivity {

    settingsDatabaseHelper mySet = new settingsDatabaseHelper(this);

    boolean loggedIn = false;
    Integer storedAccountID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        storedAccountID = mySet.getAccountID();
        if (storedAccountID != 0) {
            loggedIn = true;
        }

        LinearLayout layout = findViewById(R.id.set_Layout);
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

        loadData();

        Button saveBtn = findViewById(R.id.setSave_Btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Spinner noteTimer = findViewById(R.id.setNoteTimer_DDL);
                final EditText emergCon = findViewById(R.id.setEmergCont_Edit);
                final Spinner backCol = findViewById(R.id.setBackColour_DDL);

                if (!emergCon.getText().toString().equals("")) {
                    updateSettings(noteTimer.getSelectedItem().toString(), emergCon.getText().toString(), backCol.getSelectedItem().toString());
                    startActivity(new Intent(settings.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter an Emergency Contact Number", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(settings.this, MainActivity.class));
                return true;
            case R.id.login_item:
                startActivity(new Intent(settings.this, login.class));
                return true;
            case R.id.myAccount_item:
                startActivity(new Intent(settings.this, myAccount.class));
                return true;
            case R.id.medicalHistory_item:
                if (storedAccountID != 0) {
                    startActivity(new Intent(settings.this, medicalHistory.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Please login into an account first", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.settings_item:
                startActivity(new Intent(settings.this, settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadData() {
        Spinner notificationTimer = findViewById(R.id.setNoteTimer_DDL);
        EditText emergencyContact = findViewById(R.id.setEmergCont_Edit);
        Spinner backgroundColour = findViewById(R.id.setBackColour_DDL);

        String notificationTimerVal = mySet.getNotificationTimer();
        String emergencyContactVal = mySet.getEmergencyContact();
        String backgroundColourVal = mySet.getBackgroundColour();

        notificationTimer.setSelection(getNoteTimerPosition(notificationTimerVal));
        emergencyContact.setText(emergencyContactVal);
        backgroundColour.setSelection(getColourPosition(backgroundColourVal));
    }

    Integer getNoteTimerPosition(String value) {
        int spinnerPosition = 0;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.notificationTime, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (value != null) {
            spinnerPosition = adapter.getPosition(value);
        }
        return spinnerPosition;
    }

    Integer getColourPosition(String value) {
        int spinnerPosition = 0;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colours, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (value != null) {
            spinnerPosition = adapter.getPosition(value);
        }
        return spinnerPosition;
    }

    public void updateSettings(String noteTimer, String emergencyContact, String backgroundColour) {
        int id = 1;

        boolean updateData = mySet.updateSettings(id, noteTimer, emergencyContact, backgroundColour, mySet.getAccountID());
        if (updateData) {
            Toast.makeText(getApplicationContext(), "Settings Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Failed to Update Settings", Toast.LENGTH_SHORT).show();
        }
    }
}
