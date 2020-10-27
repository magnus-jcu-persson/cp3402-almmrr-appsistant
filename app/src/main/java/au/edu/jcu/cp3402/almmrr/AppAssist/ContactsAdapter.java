package au.edu.jcu.cp3402.almmrr.AppAssist;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactsAdapter extends ApplicationAdapter {
    private Context context;
    private String[] applicationList;
    RecyclerView applicationListView;

    EditText editTextContactName;
    Button buttonGoContact;
    Button buttonCancel;
    View viewPopup;
    View viewWebPopup;
    String contactName;


    public ContactsAdapter(Context context, String[] applicationList, Class<?>[] applicationActivities, RecyclerView applicationListView) {
        super(context, applicationList, applicationActivities, applicationListView);
        this.context = context;
        this.applicationList = applicationList;
        this.applicationListView = applicationListView;
    }

    @Override
    public void onBindViewHolder(@NonNull final ApplicationViewHolder holder, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewPopup = inflater.inflate(R.layout.popup_find_contact, null);
        editTextContactName = (EditText) viewPopup.findViewById(R.id.edit_contact_name);
        buttonGoContact = viewPopup.findViewById(R.id.button_go);
        buttonCancel = viewPopup.findViewById(R.id.button_cancel);
        final ImageButton viewApplicationVideo = holder.linearLayout
                .findViewById(R.id.imageButton_video);
        viewWebPopup = inflater.inflate(R.layout.popup_web_view, null);
        final WebView webView = viewWebPopup.findViewById(R.id.VideoWebView);

        int width = 850;
        int height = 550;
        final PopupWindow popupWindow = new PopupWindow(viewPopup, width, height, true);

        Button viewApplicationName = holder.linearLayout
                .findViewById(R.id.application_name);

        viewApplicationName.setText(applicationList[position]);
        if (position == 0) {
            viewApplicationName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.linearLayout.setOnClickListener(null);
                    openContacts();
                }
            });
        } else if (position == 1) {
            viewApplicationName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.linearLayout.setOnClickListener(null);
                    newContact();
                }
            });
        } else {
            viewApplicationName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.showAtLocation(applicationListView, Gravity.CENTER, 0, 0);
                }
            });
        }

        buttonGoContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonGoContact.setOnClickListener(null);
                contactName = editTextContactName.getText().toString();
                viewContact(contactName);
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

    private void newContact() {
        Intent newContact = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
        context.startActivity(newContact);
    }

    private void openContacts() {
        Intent openContacts = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
        context.startActivity(openContacts);
    }

    private void viewContact(String name) {
        if (name != null) {
            Uri lookupUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(name));
            String[] displayNameProjection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.LOOKUP_KEY, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
            try (Cursor cur = context.getContentResolver().query(lookupUri, displayNameProjection, null, null, null)) {
                assert cur != null;
                if (cur.moveToFirst()) {
                    String contactId = cur.getString(0);
                    Uri contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(contactId));
                    Intent viewContact = new Intent(Intent.ACTION_VIEW, contactUri);
                    context.startActivity(viewContact);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}

