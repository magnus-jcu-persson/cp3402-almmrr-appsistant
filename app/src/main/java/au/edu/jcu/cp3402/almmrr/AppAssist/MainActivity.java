package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView applicationListView;
    private ApplicationAdapter applicationListAdapter;
    private LayoutManager applicationListManager;
    private SharedPreferences appPreferences;

    private String[] applicationList = {
            "Calendar",
            "Contacts",
            "Gallery",
            "Clock"
    };

    private Class<?>[] applicationActivities = {
            CalendarActivity.class,
            ContactsActivity.class,
            GalleryActivity.class,
            ClockActivity.class,
            CalendarTutorialActivity.class,
            ContactsTutorialActivity.class,
            GalleryTutorialActivity.class,
            ClockTutorialActivity.class
    };

    /* Voice recognition related variables */
    boolean speechRecognitionMode;

    private SpeechRecognizer speechRecognizer;
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1; /* Used to handle permission request; */
    private Intent speechRecognizerIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean colorBlindMode = appPreferences.getBoolean("setting:toggle_color_blind", false);
        speechRecognitionMode = appPreferences.getBoolean("setting:toggle_speech_recognition", false);
        if (colorBlindMode) {
            setThemeMode(Theme.COLOR_BLIND);
        } else {
            setThemeMode(Theme.NORMAL);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applicationListView = findViewById(R.id.view_application_list);

        if (speechRecognitionMode) {
            setupSpeechRecognizer();
        }

        setApplicationListView();
    }

    @Override
    protected void onResume() {
        appPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        boolean colorBlindMode = appPreferences.getBoolean("setting:toggle_color_blind", false);
        speechRecognitionMode = appPreferences.getBoolean("setting:toggle_speech_recognition", false);
        if (colorBlindMode) {
            setThemeMode(Theme.COLOR_BLIND);
        } else {
            setThemeMode(Theme.NORMAL);
        }
        if (speechRecognitionMode) {
            if (speechRecognizer != null) {
                speechRecognizer.startListening(speechRecognizerIntent);
            }
        }
        applicationListManager.removeAllViews();
        applicationListView.setAdapter(applicationListAdapter);
        super.onResume();
        setContentView(R.layout.activity_main);
        applicationListView = findViewById(R.id.view_application_list);
        setApplicationListView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speechRecognitionMode) {
            if (speechRecognizer != null) {
                speechRecognizer.stopListening();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognitionMode) {
            speechRecognizer.cancel();
            speechRecognizer.destroy();
        }
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openHelp(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    private void setupSpeechRecognizer() {

        // Check if user has given permission to record audio
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},
                    PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }

        // Initialize recognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String> receivedWords =
                        bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                assert receivedWords != null;
                final Context context = applicationListView.getContext();

                // Loop through each application item and compare with received words.
                for (int i = 0; i < applicationList.length; i++) {
                    if (receivedWords.get(0).equals(applicationList[i].toLowerCase())) {
                        Intent intent = new Intent(context, applicationActivities[i]);
                        context.startActivity(intent);
                    }
                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
            }
        });

        setSpeechRecognizerIntent();
        // Start speech recognizer
        speechRecognizer.startListening(speechRecognizerIntent);
    }

    private void setSpeechRecognizerIntent() {
        speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
    }

    private void setApplicationListView() {
        applicationListView.setHasFixedSize(true);

        applicationListManager = new LinearLayoutManager(this);
        applicationListView.setLayoutManager(applicationListManager);

        applicationListAdapter = new ApplicationAdapter(
                this,
                applicationList,
                applicationActivities,
                applicationListView
        );
        applicationListView.setAdapter(applicationListAdapter);
    }

    private void setThemeMode(Theme theme) {
        switch (theme) {
            case COLOR_BLIND:
                setTheme(R.style.ColorBlindMode);
                break;
            default:
            case NORMAL:
                setTheme(R.style.AppTheme);
        }
    }
}