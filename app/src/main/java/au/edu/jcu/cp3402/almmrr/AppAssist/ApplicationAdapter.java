package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {
    RecyclerView applicationListView;
    private Context context;
    private String[] applicationList;
    private Class<?>[] applicationActivities;
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
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_application, parent, false);

        return new ApplicationViewHolder(linearLayout);
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
        popup = inflater.inflate(R.layout.popup_pre, null);
        final WebView webView= popup.findViewById(R.id.VideoWebView);
        final PopupWindow popupWindow= new PopupWindow(popup,850,550);
        popup = inflater.inflate(R.layout.popup_web_view, null);
        final boolean[] checkState = {false};

        viewApplicationDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = String.format("https://appassist.s3-ap-southeast-2.amazonaws.com/%s.html",
                        applicationList[position].toLowerCase());

                if (!checkState[0]) {
                    // Show Popup Window.
                    webView.loadUrl(path);
                    popupWindow.showAtLocation(applicationListView, Gravity.CENTER, 0, 0);
                    checkState[0] = true;
                } else {
                    // Dismiss Popup Window.
                    popupWindow.dismiss();
                    checkState[0] = false;
                }
            }
        });

        viewApplicationVideo.setOnClickListener(new View.OnClickListener() {

            public int getStringIdentifier(Context context, String name) {
                return context.getResources().getIdentifier(name, "string", context.getPackageName());
            }

            @Override
            public void onClick(View view){
                webView.loadUrl(context.getString(getStringIdentifier(context, String.format("url_%s_video",
                        applicationList[position].toLowerCase()))));
            }
        });

        viewApplicationName.setText(applicationList[position]);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout.setOnClickListener(null);
                startActivity(position);
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
