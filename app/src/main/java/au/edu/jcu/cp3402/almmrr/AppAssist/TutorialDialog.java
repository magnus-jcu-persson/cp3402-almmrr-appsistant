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
    AlertDialog dialog;
    TextView textView;
    Vibrator vibrator;
    CharSequence[] multiChoiceItems;
    boolean[] setMultiChoiceItems;
    SharedPreferences appPreferences;
    SharedPreferences.Editor appEditor;

    TutorialDialog(Activity myActivity, Context context) {
        appPreferences = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        appEditor=appPreferences.edit();
        appEditor.apply();
        activity = myActivity;
        multiChoiceItems = new CharSequence[]{"Remember My Preference"};
        setMultiChoiceItems = new boolean[1];
    }

    void start() {
        vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.question_tutorial_type);
        builder.setItems(R.array.array_tutorial_options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    vibrateDevice();
                    appEditor.putString("setting:set_tutorial_length","long");
                    appEditor.apply();
                    startFullTutorial();
                } else if (i == 1) {
                    appEditor.putString("setting:set_tutorial_length","short");
                    appEditor.apply();
                    startShortTutorial();
                } else{
                    appEditor.putString("setting:set_tutorial_length","none");
                    appEditor.apply();
                    skipTutorial();
                }
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
            //deprecated in API 26
            vibrator.vibrate(500);
        }
    }

    void setText(String text) {
        textView.setText(text);
    }

    void dismiss() {
        dialog.dismiss();
    }


    private void skipTutorial() {
        dialog.dismiss();
    }

    private void startFullTutorial() {
        Intent intent = new Intent(activity, TutorialActivity.class);
        activity.startActivity(intent);
    }

    private void startShortTutorial() {

    }
}
