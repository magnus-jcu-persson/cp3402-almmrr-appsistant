package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.provider.AlarmClock;
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

public class ClockAdapter extends ApplicationAdapter {
    private final Context context;
    private final String[] applicationList;
    RecyclerView applicationListView;
    View videoPopup;
    EditText editTextDate;
    Button buttonGoToDate;
    Button buttonCancel;
    View viewPopup;
    View viewWebPopup;
    WebView infoPopupView;

    private final Button[] quickTimerButtons = new Button[8];
    private final int[] quickTimerIds = {R.id.quick_timer_1, R.id.quick_timer_2, R.id.quick_timer_3,
            R.id.quick_timer_4, R.id.quick_timer_5, R.id.quick_timer_6, R.id.quick_timer_7, R.id.quick_timer_8};
    private String[] quickTimerStringArray;

    public ClockAdapter(Context context, String[] applicationList, Class<?>[] applicationActivities, RecyclerView applicationListView) {
        super(context, applicationList, applicationActivities, applicationListView);
        this.context = context;
        this.applicationList = applicationList;
        this.applicationListView = applicationListView;
    }

    @SuppressLint({"SetJavaScriptEnabled", "InflateParams"})
    @Override
    public void onBindViewHolder(@NonNull final ApplicationViewHolder holder, final int position) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final int WIDTH = 850;
        final int HEIGHT = 300;

        videoPopup = inflater.inflate(R.layout.popup_video, null);
        infoPopupView = videoPopup.findViewById(R.id.VideoWebView);
        WebSettings webSettings = infoPopupView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        ImageButton viewApplicationDetail = holder.linearLayout
                .findViewById(R.id.imageButton_detail);

        final PopupWindow videoPopupWindow = new PopupWindow(videoPopup, WIDTH, HEIGHT, true);

        viewPopup = inflater.inflate(R.layout.popup_date, null);
        editTextDate = (EditText) viewPopup.findViewById(R.id.edit_date);
        buttonGoToDate = viewPopup.findViewById(R.id.button_go);
        buttonCancel = viewPopup.findViewById(R.id.button_cancel);
        final ImageButton viewApplicationVideo = holder.linearLayout
                .findViewById(R.id.imageButton_video);
        viewWebPopup = inflater.inflate(R.layout.popup_web_view, null);
        final WebView webView = viewWebPopup.findViewById(R.id.VideoWebView);
        final PopupWindow popupWindow = new PopupWindow(viewPopup, WIDTH, HEIGHT, true);

        // Quick Timers
        int[] screenDimensions = getQuickTimerPopupDimensions();
        quickTimerStringArray = context.getResources().getStringArray(R.array.clock_quick_timer_options);
        View viewQuickTimerPopup = inflater.inflate(R.layout.popup_quick_timers, null);
        final PopupWindow quickTimersPopupWindow = new PopupWindow(viewQuickTimerPopup,
                screenDimensions[0] *= 0.75, screenDimensions[1] *= 0.75, true);
        setQuickTimerButtonViews(viewQuickTimerPopup);
        setQuickTimerButtonListeners(quickTimersPopupWindow);



        TextView viewApplicationName = holder.linearLayout
                .findViewById(R.id.application_name);

        viewApplicationName.setText(applicationList[position]);

        if (position == 0) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openClock();
                }
            });
            infoPopupView.loadUrl("https://appassist.s3-ap-southeast-2.amazonaws.com/openCalendar.html");
        } else if (position == 1) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.linearLayout.setOnClickListener(null);
                    openTimers();
                }
            });
        } else if (position == 2) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.linearLayout.setOnClickListener(null);
                    setAlarm();
                }
            });
        } else if (position == 3) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quickTimersPopupWindow.showAtLocation(holder.linearLayout, Gravity.CENTER, 0, 0);
                    holder.itemView.refreshDrawableState();
                }
            });
        } else {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.showAtLocation(applicationListView, Gravity.CENTER, 0, 0);
                }
            });
            infoPopupView.loadUrl("https://appassist.s3-ap-southeast-2.amazonaws.com/goToDate.html");
        }

        viewApplicationDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoPopupWindow.showAtLocation(holder.linearLayout, Gravity.CENTER, 0, 0);
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

    private int[] getQuickTimerPopupDimensions() {
        return new int[]{Resources.getSystem().getDisplayMetrics().widthPixels,
                Resources.getSystem().getDisplayMetrics().heightPixels};
    }

    private void setQuickTimerButtonViews(View view) {
        for (int i = 0; i <= quickTimerButtons.length - 1; i++) {
            quickTimerButtons[i] = view.findViewById(quickTimerIds[i]);
            quickTimerButtons[i].setText(quickTimerStringArray[i]);
        }
    }

    private void setQuickTimerButtonListeners(final PopupWindow popupWindow) {
        final int[] timerLength = {0};
        for (int i = 0; i <= quickTimerButtons.length - 1; i++) {
            final int finalI = i;
            quickTimerButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (finalI) {
                        case 0:
                            timerLength[0] = 60; // 1 minute
                            break;
                        case 1:
                            timerLength[0] = 300; // 5 minutes
                            break;
                        case 2:
                            timerLength[0] = 600; // 10 minutes
                            break;
                        case 3:
                            timerLength[0] = 900; // 15 minutes
                            break;
                        case 4:
                            timerLength[0] = 1200; // 20 minutes
                            break;
                        case 5:
                            timerLength[0] = 1800; // 30 minutes
                            break;
                        case 6:
                            timerLength[0] = 2700; // 45 minutes
                            break;
                        case 7:
                            timerLength[0] = 3600; // 60 minutes
                            break;
                    }
                    // Start timer activity
                    context.startActivity(new Intent(AlarmClock.ACTION_SET_TIMER)
                            .putExtra(AlarmClock.EXTRA_LENGTH, timerLength[0])
                            .putExtra(AlarmClock.EXTRA_SKIP_UI, true));
                    Toast.makeText(context, String.valueOf(timerLength[0] / 60) + " minute timer has started!", Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                }
            });
        }
    }

    private void openClock() {
        context.startActivity(new Intent(AlarmClock.ACTION_SHOW_ALARMS));
    }

    private void openTimers() {
        context.startActivity(new Intent(AlarmClock.ACTION_SET_TIMER));
    }

    private void setAlarm() {
        context.startActivity(new Intent(AlarmClock.ACTION_SET_ALARM));
    }


}
