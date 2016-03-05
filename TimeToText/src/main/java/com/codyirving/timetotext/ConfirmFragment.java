package com.codyirving.timetotext;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.Date;

/**
 * Created by Cody on 4/26/14.
 */
public class ConfirmFragment extends Fragment implements MainActivity.Updateable {

    private String phoneNumber;
    private PendingIntent sentPI;
    private PendingIntent deliveredPI;
    private PendingIntent fireSMS;
    //SendReceiver sendReceiver;
    //DeliverReceiver deliverReceiver;
    //IntentFilter smsSent;
    //IntentFilter smsDelivered;
    View rootView;
    ScheduledTextObject scheduledTextObject;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_confirm, container, false);
        Log.e("main", "onCreateView: ConfirmFragment");
        scheduledTextObject = (ScheduledTextObject) MainActivity.getObject();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("main", "ConfirmFragment: onActivityCreated");

        //ConfirmFragment Cancel Button - reset text inputs.
        Button cancel = (Button) getView().findViewById(R.id.buttonCancelText);
        final Button send = (Button) getView().findViewById(R.id.buttonSendText);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder areYouSure = new AlertDialog.Builder(getActivity());
                areYouSure.setTitle("Are You Sure?");
                areYouSure.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //todo Reset Fragments!

                        MainActivity m = (MainActivity)getActivity();

                        m.reset();

                        //ScheduleFragment sf = (ScheduleFragment) getFragmentManager().findFragmentByTag("android:switcher:" + R.id.schedule_fragment + ":1");
                        //FragmentTransaction ft = getFragmentManager().beginTransaction();
                        //ScheduleFragment sf = (ScheduleFragment) getFragmentManager().findFragmentById(R.id.schedule_fragment);

                        //getFragmentManager().executePendingTransactions();

                        //ft.replace(R.id.schedule_fragment, sf);
                        //ft.commit();


                        //sf.update();
                        MainActivity.mViewPager.getAdapter().notifyDataSetChanged();
                        MainActivity.mViewPager.setCurrentItem(1);

                       /* ConfirmFragment cf = (ConfirmFragment) getFragmentManager().findFragmentById(R.id.confirm_fragment);
                        ScheduleFragment sf = (ScheduleFragment) getFragmentManager().findFragmentById(R.id.schedule_fragment);
                        ViewTextsFragment vf = (ViewTextsFragment) getFragmentManager().findFragmentById(R.id.viewtext_fragment);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        if(sf != null) {
                            sf.update();
                            ft.detach(sf);
                            ft.attach(sf);
                            ft.commit();
                        }*/
                       //onActivityCreated(null);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

            }
                
                
        });
        
        //Check if all neccessary inputs are non-null

        if(scheduledTextObject.getNumberToText() != null) {
                   // if(MainActivity.aAdapter != null) {
            //    MainActivity.aAdapter.notifyDataSetChanged();
            //    int countOfNumbersToText = MainActivity.aAdapter.getCount();
               // if(countOfNumbersToText == 0) {
            //getFragmentManager().beginTransaction().replace(R.id.container, new ContactFragment(),"CONTACT_FRAG").addToBackStack(null).commit();
       // } else {
            //phoneNumber = MainActivity.aAdapter.getItem(countOfNumbersToText - 1);
            updateFields();
            TextView textViewMessage = (TextView) getView().findViewById(R.id.textViewMessageConfirm);
            textViewMessage.setText(scheduledTextObject.getTextMessageContent());
            TextView textViewSchedule = (TextView) getView().findViewById(R.id.textViewTime);


            textViewSchedule.setText(scheduledTextObject.getTimeToText().toString());
            TextView textViewRecipients = (TextView) getView().findViewById(R.id.textViewRecipients);
            textViewRecipients.setText(scheduledTextObject.getNumberToText());

            phoneNumber = scheduledTextObject.getNumberToText();
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    send.setEnabled(false);

                    updateFields();
                    AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

                    Log.e("main", "timeToText:" + scheduledTextObject.getTimeToText() + " :: current time :: " + new Date());
                    Log.e("main", "message:" + scheduledTextObject.getTextMessageContent() + " :: vs nogetter :: " + scheduledTextObject.textMessageContent);

                    PendingTextDbHelper mDbHelper = new PendingTextDbHelper(getActivity());
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();

                    values.put(PendingTextContract.PendingTextEntry.COLUMN_NAME_PHONENUMBER, phoneNumber);
                    values.put(PendingTextContract.PendingTextEntry.COLUMN_NAME_TEXTMESSAGE, scheduledTextObject.getTextMessageContent());
                    values.put(PendingTextContract.PendingTextEntry.COLUMN_NAME_TIMETOTEXT, scheduledTextObject.getTimeToText().toString());
                    values.put(PendingTextContract.PendingTextEntry.COLUMN_NAME_SENDSTATUS, "Pending...");
                    long newRowID = -1;

                    if (db != null) {
                        newRowID = db.insert(PendingTextContract.PendingTextEntry.TABLE_NAME, null, values);
                        Log.e("main", "ConfirmFragment.java: newRowID (DB != null) = " + newRowID);
                    }
                    String newRowIDstring = String.valueOf(newRowID);
                    int uniqueAlarmID = (int) (newRowID * Math.random());

                    sentPI = PendingIntent.getBroadcast(getActivity(), uniqueAlarmID, new Intent("SMS_SENT1").setAction("com.codyirving.timetotext.SMS_SENT").putExtra("ID", newRowIDstring), PendingIntent.FLAG_UPDATE_CURRENT);
                    deliveredPI = PendingIntent.getBroadcast(getActivity(), uniqueAlarmID + 1, new Intent("SMS_DELIVERED1").setAction("com.codyirving.timetotext.SMS_DELIVERED"), PendingIntent.FLAG_UPDATE_CURRENT);
                    Log.e("main", "ConfirmFragment.java: newRowID = " + newRowID);


                    Log.e("main", "newRowID " + newRowID);
                    Intent alarmIntent = new Intent(getActivity(), MyBroadcastReceiver.class);
                    alarmIntent.putExtra("phoneNumber", phoneNumber);
                    alarmIntent.putExtra("textMessageContent", scheduledTextObject.getTextMessageContent());
                    alarmIntent.putExtra("rowID", newRowIDstring);
                    alarmIntent.putExtra("sentPI", sentPI);
                    alarmIntent.putExtra("deliveredPI", deliveredPI);

                    fireSMS = PendingIntent.getBroadcast(getActivity(), uniqueAlarmID + 2, alarmIntent, 0);
                    am.setExact(AlarmManager.RTC_WAKEUP, scheduledTextObject.getTimeToText().getTime(), fireSMS);


                    boolean alarmUp = (PendingIntent.getBroadcast(getActivity(), uniqueAlarmID + 2, alarmIntent, PendingIntent.FLAG_NO_CREATE) != null);
                    if (alarmUp) {
                        Log.e("main", "alarm is UUUUP already...");

                    }



                }
            });







        }

    }
    Long timeinlong = (long) 0;
    public void updateTime(Long t){
        Log.e("main", "updateTime " + t.toString());
        timeinlong = t;
        try {
            TextView textViewSchedule = (TextView) rootView.findViewById(R.id.textViewTime);
            textViewSchedule.setText(t.toString());
        }catch (Exception e) {
            Log.e("main", "error e: " + e);
        }
    }
    private String DEFAULT_TIME = "Now!";
    private String DEFAULT_MESSAGE = "Have a Nice Day!";
    @Override
    public void update() {


        Log.e("main:confirmfrag", "MainActivity.getTextMessageContent(): " + scheduledTextObject.getTextMessageContent());
        Log.e("main:confirmfrag", "MainActivity.getTimeToText(): " + scheduledTextObject.getTimeToText());
        Log.e("main:confirmfrag", "MainActivity.getNumberToText(): " + scheduledTextObject.getNumberToText());




        if(scheduledTextObject.getNumberToText() != null) {
            Log.e("main:confirmfrag", "get/set NumberToText");
            TextView numberToText = (TextView) rootView.findViewById(R.id.textViewRecipients);
            numberToText.setText(scheduledTextObject.getNumberToText().toString());
        }else{
            Log.e("main:confirmfrag", "default_contact");
            TextView numberToText = (TextView) rootView.findViewById(R.id.textViewRecipients);
            numberToText.setText("Set Contact!");
        }

        if(scheduledTextObject.getTextMessageContent() != null) {
            Log.e("main:confirmfrag", "get/set MessageToText");
            TextView textViewContent = (TextView) rootView.findViewById(R.id.textViewMessageConfirm);
            textViewContent.setText(scheduledTextObject.textMessageContent.toString());
        }else{
            Log.e("main:confirmfrag", "default_message");
            TextView textViewContent = (TextView) rootView.findViewById(R.id.textViewMessageConfirm);
            textViewContent.setText(DEFAULT_MESSAGE);
        }

        if(scheduledTextObject.getTimeToText() != null) {
            Log.e("main:confirmfrag", "get/set TimeToText");
            TextView textViewSchedule = (TextView) rootView.findViewById(R.id.textViewTime);
            textViewSchedule.setText(scheduledTextObject.timeToText.toString());
        }else {
            Log.e("main:confirmfrag", "default_time");
            TextView textViewSchedule = (TextView) rootView.findViewById(R.id.textViewTime);
            textViewSchedule.setText(DEFAULT_TIME);

        }
        if(scheduledTextObject.getNumberToText() != null) {
            Log.e("main:confirmfrag", "get/set all ok -- ready to send");
            this.onActivityCreated(null);
        }

        if(scheduledTextObject.getNumberToText() == null && scheduledTextObject.getTimeToText() == null && scheduledTextObject.getTextMessageContent() == null) {
            Log.e("main:confirmfrag", "get/set all null -- reset");
            this.onActivityCreated(null);
        }
/*
        if(MainActivity.getNumberToText() != null && MainActivity.getTimeToText() != null && MainActivity.getTextMessageContent() != null) {
            Log.e("main:confirmfrag", "get/set all ok -- ready to send");
            this.onActivityCreated(null);
        }*/




        //MainActivity.mViewPager.getAdapter().notifyDataSetChanged();
    }


    void updateFields() {
        Log.e("main:confirmfrag:fields", "MainActivity.getTextMessageContent(): " + scheduledTextObject.getTextMessageContent());
        Log.e("main:confirmfrag:fields", "MainActivity.getTimeToText(): " + scheduledTextObject.getTimeToText());
        Log.e("main:confirmfrag:fields", "MainActivity.getNumberToText(): " + scheduledTextObject.getNumberToText());

        if(scheduledTextObject.getNumberToText() != null) {
            Log.e("main:confirmfrag:fields", "get/set NumberToText");
            TextView numberToText = (TextView) rootView.findViewById(R.id.textViewRecipients);
            numberToText.setText(scheduledTextObject.getNumberToText().toString());
        }else{
            Log.e("main:confirmfrag:fields", "default_contact");
            TextView numberToText = (TextView) rootView.findViewById(R.id.textViewRecipients);
            numberToText.setText("Set Contact!");
        }

        if(scheduledTextObject.getTextMessageContent() != null) {
            Log.e("main:confirmfrag:fields", "get/set MessageToText");
            TextView textViewContent = (TextView) rootView.findViewById(R.id.textViewMessageConfirm);
            textViewContent.setText(scheduledTextObject.textMessageContent.toString());
        }else{
            Log.e("main:confirmfrag:fields", "default_message");
            TextView textViewContent = (TextView) rootView.findViewById(R.id.textViewMessageConfirm);
            textViewContent.setText(DEFAULT_MESSAGE);
            scheduledTextObject.setTextMessageContent(DEFAULT_MESSAGE);
        }

        if(scheduledTextObject.getTimeToText() != null) {
            Log.e("main:confirmfrag:fields", "get/set TimeToText");
            TextView textViewSchedule = (TextView) rootView.findViewById(R.id.textViewTime);
            textViewSchedule.setText(scheduledTextObject.timeToText.toString());
        }else {
            Log.e("main:confirmfrag:fields", "default_time");
            TextView textViewSchedule = (TextView) rootView.findViewById(R.id.textViewTime);
            textViewSchedule.setText(DEFAULT_TIME);
            scheduledTextObject.setTimeToText(new Date());

        }
    }

}
