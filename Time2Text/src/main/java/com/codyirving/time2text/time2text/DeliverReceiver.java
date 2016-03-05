package com.codyirving.time2text.time2text;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class DeliverReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("main", "SMS_DELIVERED: on Receive");




        switch (getResultCode()) {
            case Activity.RESULT_OK:
                Toast.makeText(context, "SMS delivered",
                        Toast.LENGTH_SHORT).show();
              /*  PendingTextDbHelper mDbHelper = new PendingTextDbHelper(context);
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(PendingTextContract.PendingTextEntry.COLUMN_NAME_SENDSTATUS, "SENT");
                Bundle bundle = intent.getExtras();
                String rowID = (String) bundle.get("ID");
                String whereClause = PendingTextContract.PendingTextEntry._ID + " = " + rowID;
                String[] whereArgs = {};
                int rowsUpdated = db.update(PendingTextContract.PendingTextEntry.TABLE_NAME, values, whereClause, whereArgs);

                Log.e("main", "rowsUpdated: " + rowsUpdated + " with WHERE: " + whereClause);*/
                break;
            case Activity.RESULT_CANCELED:
                Toast.makeText(context, "SMS not delivered",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }


}