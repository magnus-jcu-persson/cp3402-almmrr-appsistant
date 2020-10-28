package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryAdapter extends ApplicationAdapter {

    private Context context;
    private String[] applicationList;
    RecyclerView applicationListView;

    Button buttonGoAlbum;
    Button buttonCancel;
    View viewPopup;
    View viewWebPopup;


    public GalleryAdapter(Context context, String[] applicationList, Class<?>[] applicationActivities, RecyclerView applicationListView) {
        super(context, applicationList, applicationActivities, applicationListView);
        this.context = context;
        this.applicationList = applicationList;
        this.applicationListView = applicationListView;
    }

    @Override
    public void onBindViewHolder(@NonNull final ApplicationViewHolder holder, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewPopup = inflater.inflate(R.layout.popup_find_contact, null);
        buttonGoAlbum = viewPopup.findViewById(R.id.button_go);
        buttonCancel = viewPopup.findViewById(R.id.button_cancel);
        final ImageButton viewApplicationVideo = holder.linearLayout
                .findViewById(R.id.imageButton_video);
        final ImageButton viewApplicationDetail = holder.linearLayout
                .findViewById(R.id.imageButton_detail);
        viewWebPopup = inflater.inflate(R.layout.popup_web_view, null);
        final WebView webView = viewWebPopup.findViewById(R.id.VideoWebView);

        int width = 850;
        int height = 550;
        final PopupWindow popupWindow = new PopupWindow(viewPopup, width, height, true);

        Button viewApplicationName = holder.linearLayout
                .findViewById(R.id.application_name);

        viewApplicationName.setText(applicationList[position]);
        if (position == 0) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.linearLayout.setOnClickListener(null);
                    openGallery();
                }
            });
        } else if (position == 1) {
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.linearLayout.setOnClickListener(null);
                    newAlbum();
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

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        viewApplicationDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = applicationList[position].toLowerCase().replace(" ", "_");
                Toast.makeText(context, Html.fromHtml(context.getString(getStringIdentifier(context, String.format("html_%s",
                        path)))), Toast.LENGTH_LONG).show();
//                videoPopupWindow.showAtLocation(holder.linearLayout, Gravity.CENTER, 0, 0);
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

    private void newAlbum() { //method for album creation
//        Intent newAlbum = new Intent(Intent.ACTION_INSERT, MediaStore.Images.Media.INTERNAL_CONTENT_URI); // finds image storage in device
        Intent newAlbum = new Intent(Intent.ACTION_INSERT);
        context.startActivity(newAlbum);

    }

    private void openGallery() { //method for opening phone gallery
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        context.startActivity(openGalleryIntent);
    }

}

