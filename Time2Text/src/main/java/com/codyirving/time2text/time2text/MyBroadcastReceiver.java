package com.codyirving.time2text.time2text;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Cody on 4/10/14.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    int MESSAGE_QUEUE;

    @Override
    public void onReceive(Context context, Intent intent) {
        MESSAGE_QUEUE++;
        Log.e("main", "WORKING queue: " + MESSAGE_QUEUE);
        SmsManager sms = SmsManager.getDefault();
        Bundle extras = intent.getExtras();
        String phoneNumber = (String) extras.get("phoneNumber");
        String textMessageContent = (String) extras.get("textMessageContent");
        String rowID = (String) extras.get("rowID");
        PendingIntent sentPI = (PendingIntent) extras.get("sentPI");
        PendingIntent deliveredPI = (PendingIntent) extras.get("deliveredPI");
        Log.e("main", "Phone: " +  phoneNumber);
        Log.e("main", "textMessage: " + textMessageContent);
        if(textMessageContent.length() < 161) {
            sms.sendTextMessage(phoneNumber, null, textMessageContent, sentPI, deliveredPI);
            Log.e("main", "short message");
        }else {
            Log.e("main", "long message");
            ArrayList<String> longtext = sms.divideMessage(textMessageContent);
            ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>(1);
            sentIntents.add(sentPI);
            ArrayList<PendingIntent> deliveredIntents = new ArrayList<PendingIntent>(1);
            deliveredIntents.add(deliveredPI);
            sms.sendMultipartTextMessage(phoneNumber, null, longtext, sentIntents, deliveredIntents);

        }




    }


}
