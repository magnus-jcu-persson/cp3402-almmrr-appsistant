package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TutorialDialog {
    Activity activity;
    String activityName;
    AlertDialog dialog;
    TextView textView;
    Vibrator vibrator;
    CharSequence[] multiChoiceItems;
    boolean[] setMultiChoiceItems;
    SharedPreferences appPreferences;
    SharedPreferences.Editor appEditor;

    TutorialDialog(Context context, String activityName) {
        appPreferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        appEditor = appPreferences.edit();
        appEditor.apply();
        activity = (Activity) context;
        this.activityName = activityName;
        multiChoiceItems = new CharSequence[]{"Remember My Preference"};
        setMultiChoiceItems = new boolean[1];
        vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
    }

    void start() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.question_tutorial_type);
        builder.setItems(R.array.array_tutorial_options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //-- New options
                appEditor.putInt("setting:option_tutorial_length", i);
                Log.i("SavePreference", "setting:option_tutorial_length: " + i);
                appEditor.apply();
                chooseTutorialOption(i);
                dismiss();

            }
        });
        dialog = builder.create();
        dialog.show();


    }

    // Vibrate device when user clicks on an option throughout the tutorial
    void vibrateDevice() {
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            Log.i("Vibrator", "I am vibrating so hard right now!");
        } else {
            // Deprecated in API 26
            vibrator.vibrate(500);
        }
    }

    void setText(String text) {
        textView.setText(text);
    }

    void dismiss() {
        dialog.dismiss();
    }

    public void chooseTutorialOption(int choice) {
        System.out.println("tutorial choice: " + choice);
        switch (choice) {
            case 0:
                vibrateDevice();
                startFullTutorial();
                break;
            case 1:
                startShortTutorial();
                break;
            default:
                skipTutorial();
                break;
        }
    }


    private void skipTutorial() {

    }

    private void startFullTutorial() {
        Intent intent;
        switch (activityName) {
            case "Calendar":
            default:
                intent = new Intent(activity, TutorialActivity.class);
                break;
            case "Contacts":
                intent = new Intent(activity, ContactsTutorialActivity.class);
                break;
        }

        activity.startActivity(intent);
    }

    private void startShortTutorial() {

    }
}
