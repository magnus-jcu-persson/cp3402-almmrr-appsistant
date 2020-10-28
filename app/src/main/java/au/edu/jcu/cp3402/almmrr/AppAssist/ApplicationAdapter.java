package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder>{
    RecyclerView applicationListView;
    private Context context;
    private String[] applicationList;
    private Class<?>[] applicationActivities;
    SharedPreferences appPreferences;
    View popup;

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;

        public ApplicationViewHolder(LinearLayout view) {
            super(view);
            linearLayout = view;

        }
    }

    public ApplicationAdapter(
            Context context,
            String[] applicationList,
            Class<?>[] applicationActivities,
            RecyclerView applicationListView
    ) {
        this.context = context;
        this.applicationList = applicationList;
        this.applicationActivities = applicationActivities;
        this.applicationListView = applicationListView;
        this.appPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_application, parent, false);

        return new ApplicationViewHolder(linearLayout);
    }

    public int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

    @Override
    public void onBindViewHolder(@NonNull final ApplicationViewHolder holder, final int position) {
        TextView viewApplicationName = holder.linearLayout
                .findViewById(R.id.application_name);
        final ImageButton viewApplicationDetail = holder.linearLayout
                .findViewById(R.id.imageButton_detail);
        final ImageButton viewApplicationVideo = holder.linearLayout
                .findViewById(R.id.imageButton_video);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popup = inflater.inflate(R.layout.popup_web_view, null);
        final WebView webView = popup.findViewById(R.id.VideoWebView);
        final PopupWindow popupWindow = new PopupWindow(popup, 850, 550);
        final boolean[] checkState = {false};
        final int tutorialOption = appPreferences.getInt("setting:option_tutorial_length", -1);

        viewApplicationDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, Html.fromHtml(context.getString(getStringIdentifier(context, String.format("html_%s",
                        applicationList[position].toLowerCase())))), Toast.LENGTH_LONG).show();
//                String path = String.format("https://appassist.s3-ap-southeast-2.amazonaws.com/%s.html",
//                        applicationList[position].toLowerCase());
//
//                if (!checkState[0]) {
//                    // Show Popup Window.
//                    webView.loadUrl(path);
//                    popupWindow.showAtLocation(applicationListView, Gravity.CENTER, 0, 0);
//                    checkState[0] = true;
//                } else {
//                    // Dismiss Popup Window.
//                    popupWindow.dismiss();
//                    checkState[0] = false;
//                }
            }
        });

        viewApplicationVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                webView.loadUrl(context.getString(getStringIdentifier(context, String.format("url_%s_video",
                        applicationList[position].toLowerCase()))));
            }
        });

        viewApplicationName.setText(applicationList[position]);

        viewApplicationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout.setOnClickListener(null);
                switch(tutorialOption){
                    case -1:
                    case 1:
                        startActivity(position);
                        break;
                    case 0:
                        startActivity(position + applicationList.length);
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return applicationList.length;
    }

    private void startActivity(int position) {
        Intent intent = new Intent(context, applicationActivities[position]);
        context.startActivity(intent);
    }
}
