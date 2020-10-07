package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.view.View;

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
    private boolean colorBlindMode;

    private String[] applicationList = {
            "Calendar"
    };

    private Class<?>[] applicationActivities = {
            CalendarActivity.class
    };

    /* Voice recognition related variables */
    private SpeechRecognizer speechRecognizer;
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1; /* Used to handle permission request; */
    private Intent speechRecognizerIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkColorBlindMode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        applicationListView = findViewById(R.id.view_application_list);

        setupSpeechRecognizer();
        setApplicationListView();

        SwitchCompat toggle = findViewById(R.id.switch_color_blind);
        toggle.setChecked(colorBlindMode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (speechRecognizer != null) {
            speechRecognizer.startListening(speechRecognizerIntent);
        }
        applicationListManager.removeAllViews();
        applicationListView.setAdapter(applicationListAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speechRecognizer != null) {
            speechRecognizer.stopListening();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.cancel();
        speechRecognizer.destroy();
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

    private void checkColorBlindMode() {
        appPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        colorBlindMode = appPreferences.getBoolean("settingColorBlind", false);
        if (colorBlindMode) {
            setThemeMode(Theme.COLOR_BLIND);
        } else {
            setThemeMode(Theme.NORMAL);
        }
    }

    public void toggleColorBlindMode(View view) {

        SwitchCompat toggle = (SwitchCompat) view;

        SharedPreferences.Editor appEditor = appPreferences.edit();
        appEditor.putBoolean("settingColorBlind", toggle.isChecked());
        appEditor.apply();

        recreate();
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