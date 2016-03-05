package com.codyirving.timetotext;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cody on 4/26/14.
 */
public class ViewTextsFragment extends Fragment implements MainActivity.Updateable {
    //List<Map<String, String>> data;
    //SimpleAdapter adapter;
    SimpleAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //MainActivity.CURRENT_VIEW = 4;
        Log.e("main", "onCreateView: ViewTextsFragment");
        return inflater.inflate(R.layout.fragment_viextexts, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView allTexts = (ListView) getView().findViewById(R.id.viewTextListView);
        final List<Map<String, String>> data = new ArrayList<Map<String, String>>();


        PendingTextDbHelper mDbHelper = new PendingTextDbHelper(getActivity());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                PendingTextContract.PendingTextEntry._ID,
                PendingTextContract.PendingTextEntry.COLUMN_NAME_PHONENUMBER,
                PendingTextContract.PendingTextEntry.COLUMN_NAME_TEXTMESSAGE,
                PendingTextContract.PendingTextEntry.COLUMN_NAME_TIMETOTEXT,
                PendingTextContract.PendingTextEntry.COLUMN_NAME_SENDSTATUS
        };

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
        Log.e("view:", "DB is " + db);
        boolean hasNext = false;
        if (c.moveToFirst()) {
            hasNext = true;


            while (hasNext) {
                long itemId = c.getLong(c.getColumnIndex(PendingTextContract.PendingTextEntry._ID));
                String phoneNumber = c.getString(c.getColumnIndex(PendingTextContract.PendingTextEntry.COLUMN_NAME_PHONENUMBER));
                String textMessage = c.getString(c.getColumnIndex(PendingTextContract.PendingTextEntry.COLUMN_NAME_TEXTMESSAGE));
                String timeToText = c.getString(c.getColumnIndex(PendingTextContract.PendingTextEntry.COLUMN_NAME_TIMETOTEXT));
                String sendStatus = c.getString(c.getColumnIndex(PendingTextContract.PendingTextEntry.COLUMN_NAME_SENDSTATUS));
                Log.e("view:", "sendStatus = " + sendStatus + " ID = " + itemId );


                if (sendStatus != null && !sendStatus.contentEquals("SENT") && !sendStatus.contentEquals("Pending...")) {
                    Log.e("view:", "THIS SHOULDN'T HAPPEN EVER:Skipping sent message: sendStatus = " + sendStatus);
                } else {
                    Map<String, String> messageInfo = new HashMap<String, String>(5);
                    messageInfo.put("Number", phoneNumber);
                    messageInfo.put("Date", timeToText);
                    messageInfo.put("ID", String.valueOf(itemId));
                    messageInfo.put("Message", textMessage);
                    messageInfo.put("Status", sendStatus);
                    Log.e("view:", "keyset" + messageInfo.keySet());
                    data.add(messageInfo);
                }
                hasNext = c.moveToNext();
            }










            adapter = new SimpleAdapter(getActivity(), data, R.layout.custom_list_view, new String[]{"Number", "Date", "Status"}, new int[]{R.id.timerName, R.id.sendDate, R.id.sendStatus});

            allTexts.setAdapter(adapter);

            adapter.notifyDataSetChanged();
            Log.e("view:", " FirstCheck,  DATA IS " + (data == null ? "NULL" : "Not NULL") );

            SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(allTexts, new SwipeDismissListViewTouchListener.DismissCallbacks() {
                public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                    for (int position : reverseSortedPositions) {
                        //adapter.remove(adapter.getItem(position));
                        Log.e("view:", "Position = " + position);
                        removeView(adapter, data, position);
                    }
                    //
                    adapter.notifyDataSetChanged();
                }

                @Override
                public boolean canDismiss(int position) {
                    return true;
                }
            });
            allTexts.setOnTouchListener(touchListener);
            allTexts.setOnScrollListener(touchListener.makeScrollListener());


           /* allTexts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int arg1, final long l) {
                    DialogInterface.OnClickListener yesnoListener = new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.e("view::view", "CLICK!");
                            switch (i) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    Log.e("view::view", "Removing arg1= " + arg1 + "  and/or long l =" + l);
                                    removeView(adapter, data, arg1);
                                case DialogInterface.BUTTON_NEGATIVE:
                                    //DO NOTHING
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Delete this Text!").setPositiveButton("Yes", yesnoListener).setNegativeButton("No", yesnoListener).show();
                }
            });
*/
        } else {

            Log.e("view:", " Empty List");
            //todo: fix dis (wrongly mapped info/names for empty list)
            Map<String, String> messageInfo = new HashMap<String, String>(4);
            messageInfo.put("Number", "No current or past Textickles");
            messageInfo.put("Date", "");
            data.add(messageInfo);
            final SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, android.R.layout.simple_list_item_2, new String[]{"Number", "Date"}, new int[]{android.R.id.text1, android.R.id.text2});

            allTexts.setAdapter(adapter);

            adapter.notifyDataSetChanged();

        }

    }


    public void update() {
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("view:", "ViewTextsFrag onDestroy()");
    }

    void removeView(SimpleAdapter adapter, List<Map<String, String>> data, long l) {

        PendingTextDbHelper dbHelper = new PendingTextDbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Map<String, String> messageInfo = new HashMap<String, String>(3);
        Log.e("view:", "data.indexOf( String.valueOf(l)));  : " + String.valueOf(l)  + "  DATA IS " + (data == null ? "NULL" : "Not NULL") );



        int index = Integer.valueOf(String.valueOf(l));
        Object row = data.toArray()[index];
        messageInfo = (Map<String, String>) data.toArray()[index];
        Log.e("view:", "object row to string" + row);

        String mid = messageInfo.get((Object) messageInfo);
        String where = PendingTextContract.PendingTextEntry._ID + " = " + messageInfo.get("ID");
        String[] whereArgs = {};
        Log.e("view:", "Delete where: " + where);
        if (db != null) {
            Log.e("view:", "Deleting...");
            int rowsDeleted = db.delete(PendingTextContract.PendingTextEntry.TABLE_NAME, where, whereArgs);
            Log.e("view:", rowsDeleted + " Rows...");
        }
        boolean valueRemoved = data.remove(messageInfo);
        Log.e("view:", "value: " + String.valueOf(l) + "   Removed?: " + valueRemoved);
        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(getActivity(), MyBroadcastReceiver.class);
        PendingIntent sentPI = PendingIntent.getBroadcast(getActivity(), 1, new Intent("SMS_SENT").putExtra("ID", String.valueOf(messageInfo.get("ID"))), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(getActivity(), 2, new Intent("SMS_DELIVERED").putExtra("ID", String.valueOf(messageInfo.get("ID"))), 0);
        alarmIntent.putExtra("phoneNumber", messageInfo.get("Number"));
        alarmIntent.putExtra("textMessageContent", messageInfo.get("Message"));
        alarmIntent.putExtra("sentPI", sentPI);
        alarmIntent.putExtra("deliveredPI", deliveredPI);
        alarmIntent.putExtra("rowID", String.valueOf(messageInfo.get("ID")));
        if (PendingIntent.getBroadcast(getActivity(), Integer.valueOf(messageInfo.get("ID")), alarmIntent, PendingIntent.FLAG_NO_CREATE) != null) {
            PendingIntent fireSMS = PendingIntent.getBroadcast(getActivity(), Integer.valueOf(messageInfo.get("ID")), alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
            Log.e("view:", "ALARM IS RUNING");
            fireSMS.cancel();
            am.cancel(fireSMS);
        } else {
            Log.e("view:", "You're ALL WRONG!");
        }


        adapter.notifyDataSetChanged();
    }
}