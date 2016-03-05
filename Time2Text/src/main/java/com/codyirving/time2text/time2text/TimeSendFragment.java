package com.codyirving.time2text.time2text;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Cody on 6/5/2014.
 */
public class TimeSendFragment extends Fragment {
    String recipientPhone;
    String recipientName;
    String senderMessage;
    Uri photoURI;
    int defaultphotoid = R.drawable.ic_launcher;
    Calendar calendar;
    Date readableDate;
    TimeSendFragment(String phone, String name, String message, Uri photo) {
        Log.d("main:timesendfrag", "setting vars: " + phone + " " + name + " " + message);
        this.photoURI = photo;
        this.recipientPhone = phone;
        this.recipientName = name;
        this.senderMessage = message;
    }
    TimeSendFragment() {
        Log.d("main:timesendfrag", "empty constructor called");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timesend, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button confirmbutton = (Button) getView().findViewById(R.id.button_ok_timesend);
        Button backbutton = (Button) getView().findViewById(R.id.button_back_timesend);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker date = (DatePicker) getView().findViewById(R.id.datePicker);
                int day = date.getDayOfMonth();
                int month = date.getMonth();
                int year = date.getYear();

                TimePicker time = (TimePicker) getView().findViewById(R.id.timePicker);
                int hour = time.getCurrentHour();
                int min = time.getCurrentMinute();
                calendar = Calendar.getInstance();
                calendar.set(year, month, day, hour, min, 0);
                readableDate = calendar.getTime();

                DialogFragment confirmationFragment = new ConfirmDialog();
                confirmationFragment.show(getFragmentManager(), "confirmSend");

            }
        });
    }
    class ConfirmDialog extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().setTitle("Schedule this text?");
            return inflater.inflate(R.layout.fragment_confirm_dialog, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            final TextView phoneNumber = (TextView) getView().findViewById(R.id.timerName_confirm_dialog);
            phoneNumber.setText(""+recipientPhone);
            TextView senderName = (TextView) getView().findViewById(R.id.recipientName_confirm_dialog);
            if(recipientName != null) {
                senderName.setText(""+recipientName);

            }else {
                senderName.setText("");
            }

            TextView sendTimeDate = (TextView) getView().findViewById(R.id.sendDate_confirm_dialog);
            sendTimeDate.setText(""+readableDate.toString());
            TextView sendMessage = (TextView) getView().findViewById(R.id.message_dialog);
            sendMessage.setText(senderMessage);
            if(photoURI != null) {
                ImageView photoOfrecipient = (ImageView) getView().findViewById(R.id.icon_confirm_dialog);
                photoOfrecipient.setImageURI(photoURI);
            }


            final Button send = (Button) getView().findViewById(R.id.button_ok_confirm_dialog);
            Button cancel = (Button) getView().findViewById(R.id.button_back_confirm_dialog);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();

                }
            });

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    send.setEnabled(false);  //made send final for this

                    AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    PendingTextDbHelper mDbHelper = new PendingTextDbHelper(getActivity());
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    //check photo uri
                    if(photoURI == null) {

                        values.put(PendingTextContract.PendingTextEntry.COLUMN_NAME_PHOTOURI, defaultphotoid);
                    }else{
                        values.put(PendingTextContract.PendingTextEntry.COLUMN_NAME_PHOTOURI, photoURI.toString());
                    }

                    values.put(PendingTextContract.PendingTextEntry.COLUMN_NAME_PHONENUMBER, recipientPhone);
                    values.put(PendingTextContract.PendingTextEntry.COLUMN_NAME_TEXTMESSAGE, senderMessage);
                    values.put(PendingTextContract.PendingTextEntry.COLUMN_NAME_TIMETOTEXT, readableDate.getTime());

                    values.put(PendingTextContract.PendingTextEntry.COLUMN_NAME_SENDSTATUS, "Pending...");
                    long newRowID = -1;

                    if (db != null) {
                        newRowID = db.insert(PendingTextContract.PendingTextEntry.TABLE_NAME, null, values);
                        Log.e("main", "ConfirmFragment.java: newRowID (DB != null) = " + newRowID);

                        db.close();
                    }
                    String newRowIDstring = String.valueOf(newRowID);
                    int uniqueAlarmID = (int) (newRowID * Math.random());

                    PendingIntent sentPI = PendingIntent.getBroadcast(getActivity(), uniqueAlarmID, new Intent("SMS_SENT1").setAction("com.codyirving.time2text.time2text.SMS_SENT").putExtra("ID", newRowIDstring), PendingIntent.FLAG_UPDATE_CURRENT);
                    PendingIntent deliveredPI = PendingIntent.getBroadcast(getActivity(), uniqueAlarmID + 1, new Intent("SMS_DELIVERED1").setAction("com.codyirving.time2text.time2text.SMS_DELIVERED"), PendingIntent.FLAG_UPDATE_CURRENT);
                    Log.e("main", "ConfirmFragment.java: newRowID = " + newRowID);


                    Log.e("main", "newRowID " + newRowID);
                    Intent alarmIntent = new Intent(getActivity(), MyBroadcastReceiver.class);
                    alarmIntent.putExtra("phoneNumber", recipientPhone);
                    alarmIntent.putExtra("textMessageContent", senderMessage);
                    //check photo uri
                    if(photoURI != null) alarmIntent.putExtra("photoUri", photoURI.toString());
                    else alarmIntent.putExtra("photoUri", defaultphotoid);

                    alarmIntent.putExtra("rowID", newRowIDstring);
                    alarmIntent.putExtra("sentPI", sentPI);
                    alarmIntent.putExtra("deliveredPI", deliveredPI);

                    PendingIntent fireSMS = PendingIntent.getBroadcast(getActivity(), uniqueAlarmID + 2, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    am.set(AlarmManager.RTC_WAKEUP, readableDate.getTime(), fireSMS);  //changed from .setExact to support API <19


                    boolean alarmUp = (PendingIntent.getBroadcast(getActivity(), uniqueAlarmID + 2, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null);
                    if (alarmUp) {
                        Log.e("main", "alarm is UUUUP already...");

                    }
                    AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                    ad.setTitle("Text Created!");
                    ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getFragmentManager().popBackStack();
                            getFragmentManager().popBackStack();
                            getDialog().dismiss();


                        }
                    }).show();
                }
            });








        }
    }
}
