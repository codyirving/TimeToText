package com.codyirving.timetotext;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SendReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("main", "SMS_SENT: on Receive");
        //TODO: Call recursive send for multiple recipients from here
        //getActivity().unregisterReceiver(sendReceiver);
        switch (getResultCode()) {
            case Activity.RESULT_OK:



                        Toast.makeText(context, "SMS sent", Toast.LENGTH_SHORT).show();
                        PendingTextDbHelper mDbHelper = new PendingTextDbHelper(context);
                        SQLiteDatabase db = mDbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        values.put(PendingTextContract.PendingTextEntry.COLUMN_NAME_SENDSTATUS, "SENT");

                        Bundle bundle = intent.getExtras();
                        String rowID = (String) bundle.get("ID");
                        String whereClause = PendingTextContract.PendingTextEntry._ID + " = " + rowID;
                        String[] whereArgs = {};
                        int rowsUpdated = db.update(PendingTextContract.PendingTextEntry.TABLE_NAME, values, whereClause, whereArgs);

                        Log.e("main", "rowsUpdated: " + rowsUpdated + " with WHERE: " + whereClause);


                        //Toast.makeText(context, "SMS sent",Toast.LENGTH_SHORT).show();
                        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
                        notification.setContentTitle("Scheduled SMS Sent!").setContentText("Swipe to dismiss").setSmallIcon(R.drawable.ic_launcher);
                        //notification.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), 0));


                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                        notificationManager.notify(1, notification.build());

                        if(MainActivity.mViewPager != null) MainActivity.mViewPager.getAdapter().notifyDataSetChanged();

                        Log.e("main", "fired notification");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }

        }
    }