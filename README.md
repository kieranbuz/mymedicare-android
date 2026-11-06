# MyMediCare

BSc coursework: an Android app in Java that records blood pressure, temperature and heart rate readings in SQLite and rates each reading's risk level.

## The task

The original brief is not in this project. The write-up describes the app as follows:

> MyMediCare is a mobile application made for people who need to monitor their blood pressure, temperature, and heart rate. The application allows you to record and view your health readings and will send a message to an emergency contact number if any readings are cause for concern.

## Method

- Built in Android Studio in Java, with the screens defined as Android layouts.
- Three SQLite databases, each with its own helper class: account entries, medical entries and settings.
- Each health check screen takes a reading, calculates a risk level, stores the reading and updates the screen.
- For a low risk, a button opens a web page. For a high risk, a button opens a preloaded SMS to the emergency contact number from settings.
- `MainActivity` schedules a reminder notification with `AlarmManager`, using the time set in settings.
- The code stores account passwords in the SQLite table without hashing them.

Changes stated in the write-up's reflection:

> If I were to implement this project again I change the typical user walkthrough to start on the login page as this would make developing the rest of the application more efficient. I would also add more settings preferences such as font colour and font size.

## Results

The write-up records these test cases. They have not been re-run for this repository.

| Test No. | Description | Test Type | Expectation | Actual Outcome |
| --- | --- | --- | --- | --- |
| 1 | Upon the application loading, the user interface displays the welcome page. | Functional | Welcome page loads | As Expected |
| 2 | When selected the health check (temperature, heart rate, blood pressure) page display the selected health page in a separate screen. | Functional | Health check page loads correctly | As Expected |
| 3 | The user can register a new account and store their details. | Functional | New account details are stored | As Expected |
| 4 | Returning users can log in to their account and see their stored details. | Functional | Loaded stored account details | As Expected |
| 5 | Returning users can modify their details. | Functional | Modified details are stored | As Expected |
| 6 | The user can personalise the application. | Functional | Application settings can be changed | As Expected |
| 7 | The system calculates the risk level correctly based on the user’s entry. | Functional | The risk level is correct based on Appendix A | As Expected |
| 8 | The user can enter readings for temperature, blood pressure and heart rate. | Functional | User can enter data readings | As Expected |
| 9 | The system notifies chosen contact when the risk level is deemed high. | Functional | SMS message or email is sent to chosen contact | As Expected |
| 10 | The application can be available used on devices with various screen sizes. | Compatibility | The application will dynamically fit varying screen sizes | As Expected |
| 11 | The application can be used on various mobile devices | Compatibility | The application will be usable on multiple mobile device types | As Expected |
| 12 | All buttons are of a suitable size to be used | Usability | All buttons are usable | As Expected |
| 13 | All text is simple, readable, and visible to the user | Usability | Text is readable | As Expected |

## Data

No dataset or database file is included. The app creates its SQLite databases on the device.

Excluded from this repository:

- `.gradle/` and `.idea/`: tool folders.
- The coursework write-up.

## How to run

**Not verified.** This copy has not been built or run.

1. Open the project in Android Studio. `app/build.gradle` sets `compileSdkVersion 29` and `buildToolsVersion "29.0.3"`, and the wrapper properties name Gradle 5.4.1. The restored build.gradle uses jcenter() and Android Gradle plugin 3.5.3, which may need updating for current Android Studio.
2. Run the `app` configuration on a device or emulator. `minSdkVersion` is 15.
