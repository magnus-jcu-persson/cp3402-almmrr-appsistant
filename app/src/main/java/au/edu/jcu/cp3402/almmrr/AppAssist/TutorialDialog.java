package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.widget.TextView;

import au.edu.jcu.cp3402.almmrr.AppAssist.R;

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
                        //fullTutorial();
                    } else if (i == 1) {
                        //shortTutorial();
                    } else skipTutorial();
                }

            });
            dialog = builder.create();
            dialog.show();
        }

            void setText(String text) {
            textView.setText(text);
        }

        void dismiss(){
            dialog.dismiss();
        }
        private void skipTutorial(){

        }
    }

