package au.edu.jcu.cp3402.almmrr.appsistant;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class CalendarAdapter extends ApplicationAdapter {
    private Context context;
    private String[] applicationList;

    public CalendarAdapter(Context context, String[] applicationList, Class[] applicationActivities) {
        super(context, applicationList, applicationActivities);
        this.context = context;
        this.applicationList = applicationList;

    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, final int position){
        TextView viewApplicationName = (TextView) holder.linearLayout
                .findViewById(R.id.application_name);

        viewApplicationName.setText(applicationList[position]);
        if (position==0){
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"View My Calendar",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(position==1){
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Add A new appointment",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"Go to a specific date",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void startCalendar(){

    }
}