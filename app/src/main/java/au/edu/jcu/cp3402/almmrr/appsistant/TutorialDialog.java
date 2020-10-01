package au.edu.jcu.cp3402.almmrr.appsistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.widget.TextView;

public class TutorialDialog {
        Activity activity;
        AlertDialog dialog;
        TextView textView;
        TutorialDialog(Activity myActivity) {
            activity = myActivity;
        }
        void start() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.Tutorial_Dialog);
            builder.setItems(R.array.tutorial_selection, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0) {
                        fullTutorial();
                    } else if (i == 1) {
                        shortTutorial();
                    } else skipTutorial();
                }

            });
            dialog = builder.create();
            dialog.show();
        }

        private void skipTutorial(){
            dialog.dismiss();
        }
        private void fullTutorial(){
            Intent intent = new Intent(activity,Tutorial_Activity.class);
            activity.startActivity(intent);
        }
        private void shortTutorial(){

        }
    }

