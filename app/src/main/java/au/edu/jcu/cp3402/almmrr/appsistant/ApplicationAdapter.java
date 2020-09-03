package au.edu.jcu.cp3402.almmrr.appsistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {

    private Context context;
    private String[] applicationList;
    private Class[] applicationActivities;
    private int activityDelay;

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
            Class[] applicationActivities
    ) {
        this.context = context;
        this.applicationList = applicationList;
        this.applicationActivities = applicationActivities;
        activityDelay = context.getResources().getInteger(R.integer.activity_delay);
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

        TextView viewApplicationName = (TextView) holder.linearLayout
                .findViewById(R.id.application_name);

        viewApplicationName.setText(applicationList[position]);


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout.setOnClickListener(null);
                final LoadingDialog loadingDialog = new LoadingDialog((Activity) context);
                loadingDialog.start();
                loadingDialog.setText("Loading " + applicationList[position]);

                // -- FORCE DELAY FOR HYPOTHESIS
                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismiss();
                        startActivity(position);
                    }
                }, activityDelay * 1000);
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
