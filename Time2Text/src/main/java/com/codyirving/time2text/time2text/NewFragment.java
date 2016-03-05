package com.codyirving.time2text.time2text;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

/**
 * Created by Cody on 6/5/2014.
 */
public class NewFragment extends Fragment {
    String phoneNumberSelected;
    String contactNameSelected;
    String inputMessage;
    Uri photoURI;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {

            getView().findViewById(R.id.button_contacts_new).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(contactPickerIntent, 1);
                }
            });
            final TextView messageEditText = (TextView) getView().findViewById(R.id.editText_message);
            Button okButton = (Button) getView().findViewById(R.id.button_ok_new);
            final TextView phoneNumberView = (TextView) getView().findViewById(R.id.textView_phone_new);
            phoneNumberView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (textView.getText().length() != 0)phoneNumberView.setError(null);
                    return false;
                }
            });
            phoneNumberView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    phoneNumberSelected = charSequence.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
         /*   messageEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    Log.e("main", "got the touch..textView.getText().length(): " + textView.getText().length());
                    if(textView.getText().length() > 0) messageEditText.setError(null);
                    return false;
                }

            });*/
            messageEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    if(messageEditText.getText().length() > 0 ) messageEditText.setError(null);
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(messageEditText.getText().length() > 0 ) messageEditText.setError(null);
                }
            });

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TextView messageEditText = (TextView) getView().findViewById(R.id.editText_message);
                    CharSequence cs = messageEditText.getText();
                    inputMessage = cs.toString();
                    //phoneNumberView = (TextView) getView().findViewById(R.id.textView_phone_new);
                    //TextView messageView = (TextView) getView().findViewById(R.id.editText_message);
                    if (phoneNumberSelected == null) {

                        phoneNumberView.setError("Phone number required!");
                    } else if (inputMessage == null || inputMessage.equals("") ) {
                        phoneNumberView.setError(null);

                        messageEditText.setError("Message required!");
                    } else {
                        messageEditText.setError(null);


                        getFragmentManager().beginTransaction().replace(R.id.main_layout, new TimeSendFragment(phoneNumberSelected, contactNameSelected, inputMessage, photoURI), "FRAG_TIMESEND").addToBackStack("FRAG_NEW").commit();
                    }
                }
            });

            getView().findViewById(R.id.button_cancel_new).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().popBackStack();
                }
            });










        } catch (Exception e) {
            e.printStackTrace();
        }





    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Cursor cursor = getActivity().getContentResolver().query(uri, null,null,null,null);
            cursor.moveToFirst();
            int selectedContactID = new Integer(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));

            cursor.close();
            cursor = getActivity().getContentResolver().query(
                                                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                                                null,
                                                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + selectedContactID,
                                                                null,
                                                                null
                                                                );
            cursor.moveToFirst();
            phoneNumberSelected = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactNameSelected = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME));
            String contactPictureSelected = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.PHOTO_THUMBNAIL_URI));

            if(contactPictureSelected != null) photoURI = Uri.parse(contactPictureSelected);

            cursor.close();
            TextView phoneView = (TextView) getView().findViewById(R.id.textView_phone_new);
            TextView contactView = (TextView) getView().findViewById(R.id.textView_contactName_new);
            phoneView.setText(phoneNumberSelected);
            phoneView.setError(null);
            contactView.setText(contactNameSelected);


        }else if(resultCode == Activity.RESULT_CANCELED) {
            Log.d("main:newfrag", "Canceled: " + requestCode);
            //do nothing?
        }



    }






}
