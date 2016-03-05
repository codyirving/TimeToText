package com.codyirving.timetotext;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by Cody on 4/10/14.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("main", "WORKING");
        SmsManager sms = SmsManager.getDefault();
        Bundle extras = intent.getExtras();
        String phoneNumber = (String) extras.get("phoneNumber");
        String textMessageContent = (String) extras.get("textMessageContent");
        String rowID = (String) extras.get("rowID");
        PendingIntent sentPI = (PendingIntent) extras.get("sentPI");
        PendingIntent deliveredPI = (PendingIntent) extras.get("deliveredPI");

        Log.e("main", "Phone: " +  phoneNumber);
        Log.e("main", "textMessage: " + textMessageContent);
        sms.sendTextMessage(phoneNumber, null, textMessageContent, sentPI,deliveredPI);

    }
}
