package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import au.edu.jcu.cp3402.almmrr.AppAssist.R;

public class CalendarAdapter extends ApplicationAdapter {
    private Context context;
    private String[] applicationList;
    RecyclerView applicationListView;

    EditText date;
    Button goToDate;
    Button cancel;
    View popup;
    String dateFormat;


    public CalendarAdapter(Context context, String[] applicationList, Class[] applicationActivities, RecyclerView applicationListView) {
        super(context, applicationList, applicationActivities, applicationListView);
        this.context = context;
        this.applicationList = applicationList;
        this.applicationListView = applicationListView;
    }

    @Override
    public void onBindViewHolder(@NonNull final ApplicationViewHolder holder, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popup = inflater.inflate(R.layout.popup_date, null);
        date = (EditText) popup.findViewById(R.id.editTextDate);
        goToDate = popup.findViewById(R.id.goToDate);
        cancel = popup.findViewById(R.id.cancel);


        int width = 850;
        int height = 550;
        final PopupWindow popupWindow = new PopupWindow(popup, width, height, true);

        TextView viewApplicationName = holder.linearLayout
                .findViewById(R.id.application_name);

        viewApplicationName.setText(applicationList[position]);
        if (position == 0) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.linearLayout.setOnClickListener(null);
                    openCalendar();
                }
            });
        } else if (position == 1) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.linearLayout.setOnClickListener(null);
                    newAppointment();
                }
            });
        } else {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.showAtLocation(applicationListView, Gravity.CENTER, 0, 0);
                }
            });
        }

        goToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDate.setOnClickListener(null);
                dateFormat = date.getText().toString();
                goToDate(dateFormat);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private void newAppointment() {
        Intent addAppointment = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI);
        context.startActivity(addAppointment);
    }
  
    private void openCalendar() {
        long start = System.currentTimeMillis();
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, start);

        Intent openCalendar = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        context.startActivity(openCalendar);
    }

    private void goToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date dateParsed = sdf.parse(date);
            long currentTimeMillis = dateParsed.getTime();
            Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
            builder.appendPath("time");
            ContentUris.appendId(builder, currentTimeMillis);
            Intent openCalendar = new Intent(Intent.ACTION_VIEW)
                    .setData(builder.build());
            context.startActivity(openCalendar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

