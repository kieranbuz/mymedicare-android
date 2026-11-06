package com.example.coursework2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class temperature extends AppCompatActivity {

    settingsDatabaseHelper mySet = new settingsDatabaseHelper(this);
    medicalDatabaseHelper myMed = new medicalDatabaseHelper(this);

    boolean loggedIn = false;
    Integer storedAccountID;

    String risk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        storedAccountID = mySet.getAccountID();
        if (storedAccountID != 0) {
            loggedIn = true;
        }

        LinearLayout layout = findViewById(R.id.tmp_Layout);
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

        // Gets calculate risk button
        Button calBtn = findViewById(R.id.tmpCal_Btn);
        calBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Gets risk type
                risk = temperatureCalculation();
                // Updates screen
                if (risk != null) {
                    updateScreen(risk);
                }
            }
        });

        // Gets result button
        Button resultBtn = findViewById(R.id.tmpResult_Btn);
        resultBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (risk.equals("Low")) {
                    // Calls routine to open web page
                    openWebPage("https://www.nhs.uk/conditions/fever-in-adults/");
                } else if (risk.equals("High")) {
                    // Calls routine to send text
                    sendSMS(mySet.getEmergencyContact());
                }
            }
        });
    }

    // Create menu inflater
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

    // Assigns each of the menu items to start a new activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_item:
                startActivity(new Intent(temperature.this, MainActivity.class));
                return true;
            case R.id.login_item:
                startActivity(new Intent(temperature.this, login.class));
                return true;
            case R.id.myAccount_item:
                startActivity(new Intent(temperature.this, myAccount.class));
                return true;
            case R.id.medicalHistory_item:
                if (storedAccountID != 0) {
                    startActivity(new Intent(temperature.this, medicalHistory.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Please login into an account first", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.settings_item:
                startActivity(new Intent(temperature.this, settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Calculates risk level
    private String temperatureCalculation() {
        // Variable used to store risk type
        String riskType = null;
        // Gets value of entered temperature
        EditText val = findViewById(R.id.tmpVal_Edit);
        if (val.length() != 0) {
            // Converts value to a string
            String stringVal = val.getText().toString();
            // Converts string to a double
            Double temperature = Double.parseDouble(stringVal);
            // Calculates the level of risk based on the temperature
            if (temperature <= 37) {
                riskType = "Normal";
            } else if (temperature > 37 && temperature < 38) {
                riskType = "Low";
            } else if (temperature >= 38) {
                riskType = "High";
            }
            addData(getDate(), temperature, riskType);
            // Returns risk type
            return riskType;
        } else {
            Toast.makeText(getApplicationContext(), "Please enter a value in the field above", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    // Updates the activity screen to show results based on risk
    private void updateScreen(String riskType) {
        // Normal Risk Type
        switch (riskType) {
            case "Normal": {
                // Gets header text view
                TextView header = findViewById(R.id.tmpHeader2_Txt);
                // Sets visibility of header text view
                header.setVisibility(View.VISIBLE);

                // Gets risk type text view
                TextView risk = findViewById(R.id.tmpRiskType_Txt);
                // Sets text of risk type text view
                risk.setText(getString(R.string.normalRiskType));
                // Sets visibility of risk type text view
                risk.setVisibility(View.VISIBLE);

                // Gets recommended action text view
                TextView action = findViewById(R.id.tmpRec_Txt);
                // Sets text of recommended action text view
                action.setText(getString(R.string.normalAction));
                // Sets visibility of recommended action text view
                action.setVisibility(View.VISIBLE);

                // Gets result button
                TextView result = findViewById(R.id.tmpResult_Btn);
                // Sets visibility of result button
                result.setVisibility(View.INVISIBLE);
                break;
            }
            // Low Risk Type
            case "Low": {
                // Gets header text view
                TextView header = findViewById(R.id.tmpHeader2_Txt);
                // Sets visibility of header text view
                header.setVisibility(View.VISIBLE);

                // Gets risk type text view
                TextView risk = findViewById(R.id.tmpRiskType_Txt);
                // Sets text of risk type text view
                risk.setText(getString(R.string.lowRiskType));
                // Sets visibility of risk type text view
                risk.setVisibility(View.VISIBLE);

                // Gets recommended action text view
                TextView action = findViewById(R.id.tmpRec_Txt);
                // Sets text of recommended action text view
                action.setText(getString(R.string.lowAction));
                // Sets visibility of recommended action text view
                action.setVisibility(View.VISIBLE);

                // Gets result button
                TextView result = findViewById(R.id.tmpResult_Btn);
                // Sets text of result button
                result.setText(getString(R.string.lowButton));
                // Sets visibility of result button
                result.setVisibility(View.VISIBLE);
                break;
            }
            // High Risk Type
            case "High": {
                // Gets header text view
                TextView header = findViewById(R.id.tmpHeader2_Txt);
                // Sets visibility of header text view
                header.setVisibility(View.VISIBLE);

                // Gets risk type text view
                TextView risk = findViewById(R.id.tmpRiskType_Txt);
                // Sets text of risk type text view
                risk.setText(getString(R.string.highRiskType));
                // Sets visibility of risk type text view
                risk.setVisibility(View.VISIBLE);

                // Gets recommended action text view
                TextView action = findViewById(R.id.tmpRec_Txt);
                // Sets text of recommended action text view
                action.setText(getString(R.string.highAction));
                // Sets visibility of recommended action text view
                action.setVisibility(View.VISIBLE);

                // Gets result button
                TextView result = findViewById(R.id.tmpResult_Btn);
                // Sets text of result button
                result.setText(getString(R.string.highButton));
                // Sets visibility of result button
                result.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    public void openWebPage(String url) {
        Uri webAddress = Uri.parse(url);
        Intent webPageActivity = new Intent(Intent.ACTION_VIEW, webAddress);
        if (webPageActivity.resolveActivity(getPackageManager()) != null) {
            startActivity(webPageActivity);
        }
    }

    // Uses phone number and message to open message activity with message pre-loaded
    public void sendSMS(String phoneNumber) {
        Intent messageActivity = new Intent(Intent.ACTION_VIEW);
        messageActivity.setData(Uri.parse("sms:"));
        messageActivity.putExtra("address", phoneNumber);
        messageActivity.putExtra("sms_body", "Dear " + phoneNumber + ", you are registered as my emergency contact in my myMediCare app, I have received a high risk alert based on my health which suggests I should seek further medical advice");
        startActivity(messageActivity);
    }

    private java.util.Date getDate() {
        return Calendar.getInstance().getTime();
    }

    public void addData(Date date, Double val, String risk) {
        boolean insertData = myMed.addData(date.toString(), "temperature", val.toString(), risk, mySet.getAccountID());
        if (insertData) {
            Toast.makeText(getApplicationContext(), "Successfully saved data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Failed to save data", Toast.LENGTH_SHORT).show();
        }
    }
}
