package com.example.coursework2;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    settingsDatabaseHelper mySet = new settingsDatabaseHelper(this);

    boolean loggedIn = false;
    Integer storedAccountID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            storedAccountID = mySet.getAccountID();
        } catch (Exception e) {
            mySet.populateSettings();
        } finally {
            storedAccountID = mySet.getAccountID();
        }

        storedAccountID = mySet.getAccountID();
        if (storedAccountID != 0) {
            loggedIn = true;
        }

        if (!mySet.getNotificationTimer().equals("No Reminder")) {
            createNotificationChannel();
            Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            String notificationTime = mySet.getNotificationTimer();
            int notificationHour = Integer.parseInt(notificationTime.substring(0, 1));
            int notificationMinute = Integer.parseInt(notificationTime.substring(3));

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.set(Calendar.HOUR_OF_DAY, notificationHour);
            c.set(Calendar.MINUTE, notificationMinute);
            c.set(Calendar.SECOND, 0);

            long timeToNotify = c.getTimeInMillis();

            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeToNotify, pendingIntent);
            }
        }

        LinearLayout layout = findViewById(R.id.main_Layout);
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

        Button tmpBtn = findViewById(R.id.mainTMP_Btn);
        tmpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (storedAccountID != 0) {
                    startActivity(new Intent(MainActivity.this, temperature.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Please login into an account first", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button hrBtn = findViewById(R.id.mainHR_Btn);
        hrBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (storedAccountID != 0) {
                    startActivity(new Intent(MainActivity.this, heartRate.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Please login into an account first", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button bpBtn = findViewById(R.id.mainBP_Btn);
        bpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (storedAccountID != 0) {
                    startActivity(new Intent(MainActivity.this, bloodPressure.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Please login into an account first", Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                return true;
            case R.id.login_item:
                startActivity(new Intent(MainActivity.this, login.class));
                return true;
            case R.id.myAccount_item:
                startActivity(new Intent(MainActivity.this, myAccount.class));
                return true;
            case R.id.medicalHistory_item:
                if (storedAccountID != 0) {
                    startActivity(new Intent(MainActivity.this, medicalHistory.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Please login into an account first", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.settings_item:
                startActivity(new Intent(MainActivity.this, settings.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "MyMediCareReminderChannel";
            String description = "Channel for MyMediCare Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyMyMediCare", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
