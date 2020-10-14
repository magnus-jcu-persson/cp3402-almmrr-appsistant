package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarAdapter extends ApplicationAdapter {
    private Context context;
    private String[] applicationList;
    RecyclerView applicationListView;
    Uri uri;
    EditText date;
    Button goToDate;
    Button cancel;
    View popup;
    View videoPopup;
    EditText editTextDate;
    Button buttonGoToDate;
    Button buttonCancel;
    View viewPopup;
    View viewWebPopup;
    String dateFormat;
    WebView videoView;

    public CalendarAdapter(Context context, String[] applicationList, Class<?>[] applicationActivities, RecyclerView applicationListView) {
        super(context, applicationList, applicationActivities, applicationListView);
        this.context = context;
        this.applicationList = applicationList;
        this.applicationListView = applicationListView;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onBindViewHolder(@NonNull final ApplicationViewHolder holder, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popup = inflater.inflate(R.layout.popup_date, null);
        date = (EditText) popup.findViewById(R.id.editTextDate);
        goToDate = popup.findViewById(R.id.goToDate);
        cancel = popup.findViewById(R.id.cancel);
        videoPopup = inflater.inflate(R.layout.popup_video, null);
        videoView = videoPopup.findViewById(R.id.VideoWebView);
        WebSettings webSettings = videoView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        ImageButton viewApplicationDetail = holder.linearLayout
                .findViewById(R.id.imageButton);
        int width = 850;
        int height = 550;
        final PopupWindow videoPopupWindow = new PopupWindow(videoPopup, width, height, true);

        viewPopup = inflater.inflate(R.layout.popup_date, null);
        editTextDate = (EditText) viewPopup.findViewById(R.id.edit_date);
        buttonGoToDate = viewPopup.findViewById(R.id.button_go);
        buttonCancel = viewPopup.findViewById(R.id.button_cancel);
        final ImageButton viewApplicationVideo = holder.linearLayout
                .findViewById(R.id.imageButton_video);
        viewWebPopup = inflater.inflate(R.layout.popup_web_view, null);
        final WebView webView = viewWebPopup.findViewById(R.id.VideoWebView);
        final PopupWindow popupWindow = new PopupWindow(viewPopup, width, height, true);

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
            videoView.loadUrl("https://appassist.s3-ap-southeast-2.amazonaws.com/openCalendar.html");
        } else if (position == 1) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.linearLayout.setOnClickListener(null);
                    newAppointment();
                }
            });
            videoView.loadUrl("https://appassist.s3-ap-southeast-2.amazonaws.com/addNewEvent.html");
        } else {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.showAtLocation(applicationListView, Gravity.CENTER, 0, 0);
                }
            });
            videoView.loadUrl("https://appassist.s3-ap-southeast-2.amazonaws.com/goToDate.html");
        }

        buttonGoToDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                buttonGoToDate.setOnClickListener(null);
                dateFormat = editTextDate.getText().toString();
                goToDate(dateFormat);
            }
        });

        viewApplicationDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoPopupWindow.showAtLocation(holder.linearLayout, Gravity.CENTER, 0, 0);
                Toast.makeText(context, "Video should be shgowing", Toast.LENGTH_SHORT).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {

                                      }
                                  });
                buttonCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

        viewApplicationVideo.setOnClickListener(new View.OnClickListener() {

            public int getStringIdentifier(Context context, String name) {
                return context.getResources().getIdentifier(name, "string", context.getPackageName());
            }

            @Override
            public void onClick(View view) {
                String path = applicationList[position].toLowerCase().replace(" ", "_");
                webView.loadUrl(context.getString(getStringIdentifier(context, String.format("url_%s_video",
                        path))));
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
            assert dateParsed != null;

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

