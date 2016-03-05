package com.codyirving.timetotext;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

/**
 * Created by Cody on 4/10/14.
 */
public class MyOnBootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            Log.e("main", "BootBroadcast on Receive!");
            //ConfirmFragment cf = new ConfirmFragment();
            //cf.restartReceivers();


            PendingTextDbHelper mDbHelper = new PendingTextDbHelper(context);
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            String[] projection = {
                    PendingTextContract.PendingTextEntry._ID,
                    PendingTextContract.PendingTextEntry.COLUMN_NAME_PHONENUMBER,
                    PendingTextContract.PendingTextEntry.COLUMN_NAME_TEXTMESSAGE,
                    PendingTextContract.PendingTextEntry.COLUMN_NAME_TIMETOTEXT,
                    PendingTextContract.PendingTextEntry.COLUMN_NAME_SENDSTATUS
            };
            Log.e("main", "setProjection");
            String sortOrder = PendingTextContract.PendingTextEntry._ID + " DESC";
            String selection = "";
            String[] selectionArgs = {};
            Cursor c = null;
            if (db != null) {
                c = db.query(
                        PendingTextContract.PendingTextEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
            }

            boolean hasNext = false;
            if(c.moveToFirst()) {
            hasNext = true;
            }
        Log.e("main", "checking text queue. HasNext: " + hasNext);

        while(hasNext){

            long itemId = c.getLong(c.getColumnIndex(PendingTextContract.PendingTextEntry._ID));
            String phoneNumber = c.getString(c.getColumnIndex(PendingTextContract.PendingTextEntry.COLUMN_NAME_PHONENUMBER));
            String textMessage = c.getString(c.getColumnIndex(PendingTextContract.PendingTextEntry.COLUMN_NAME_TEXTMESSAGE));
            String timeToText = c.getString(c.getColumnIndex(PendingTextContract.PendingTextEntry.COLUMN_NAME_TIMETOTEXT));
            String sendStatus = c.getString(c.getColumnIndex(PendingTextContract.PendingTextEntry.COLUMN_NAME_SENDSTATUS));
            if(sendStatus != null && sendStatus.contentEquals("SENT")) {
                Log.e("main", "Skipping sent message for message# " + itemId + ": sendStatus: " + sendStatus);
            }else{
                Log.e("main", "adding alarm for message# " + itemId + " at time: " + timeToText + " SENT STATUS: " + sendStatus);
                int uniqueAlarmID = (int) (itemId * Math.random());



                Intent smsSendIntent = new Intent("SMS_SENT").putExtra("ID",String.valueOf(itemId)).setAction("com.codyirving.timetotext.SMS_SENT");
                Intent smsDeliveredIntent = new Intent("SMS_DELIVERED").putExtra("ID",String.valueOf(itemId)).setAction("com.codyirving.timetotext.SMS_DELIVERED");
                PendingIntent sentPI = PendingIntent.getBroadcast(context, uniqueAlarmID, smsSendIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent deliveredPI = PendingIntent.getBroadcast(context, uniqueAlarmID+1, smsDeliveredIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                Intent alarmIntent = new Intent(context, MyBroadcastReceiver.class);
                alarmIntent.setAction("com.codyirving.timetotext.ALARM_INTENT");
                alarmIntent.putExtra("phoneNumber", phoneNumber);
                alarmIntent.putExtra("textMessageContent", textMessage);
                alarmIntent.putExtra("sentPI", sentPI);
                alarmIntent.putExtra("deliveredPI", deliveredPI);
                alarmIntent.putExtra("rowID", String.valueOf(itemId));
                alarmIntent.putExtra("ID", String.valueOf(itemId));

                PendingIntent fireSMS = PendingIntent.getBroadcast(context, uniqueAlarmID+2, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                am.setExact(AlarmManager.RTC_WAKEUP, new Date(timeToText).getTime(), fireSMS );
            }
            hasNext = c.moveToNext();
        }


    }



}
