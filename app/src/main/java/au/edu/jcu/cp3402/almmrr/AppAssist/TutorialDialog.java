package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

public class TutorialDialog {
    Activity activity;
    AlertDialog dialog;
    TextView textView;
    Vibrator vibrator;

    TutorialDialog(Activity myActivity) {
        activity = myActivity;
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
                    startFullTutorial();
                } else if (i == 1) {
                    startShortTutorial();
                } else skipTutorial();
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

    private void skipTutorial(){
        dialog.dismiss();
    }
    private void startFullTutorial(){
        Intent intent = new Intent(activity, TutorialActivity.class);
        activity.startActivity(intent);
    }
    private void startShortTutorial(){

    }
}
