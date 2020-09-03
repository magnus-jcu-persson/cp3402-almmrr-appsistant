package au.edu.jcu.cp3402.almmrr.appsistant;

import android.app.Activity;
import android.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

class LoadingDialog {
    Activity activity;
    AlertDialog dialog;
    TextView textView;

    LoadingDialog(Activity myActivity) {
        activity = myActivity;
    }
        void start(){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            LayoutInflater inflater = activity.getLayoutInflater();

            View view = inflater.inflate(R.layout.custom_dialog,null);

            builder.setView(view);
            builder.setCancelable(false);

            textView = view.findViewById(R.id.textView);

            dialog = builder.create();
            dialog.show();
        }

        void setText(String text) {
            textView.setText(text);
        }

        void dismiss(){
        dialog.dismiss();
        }
    }

