package com.codyirving.timetotext;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Cody on 4/10/14.
 */
public class PendingTextDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PendingTextContract.PendingTextEntry.TABLE_NAME + " (" +
                    PendingTextContract.PendingTextEntry._ID + " INTEGER PRIMARY KEY," +
                    PendingTextContract.PendingTextEntry.COLUMN_NAME_PHONENUMBER + TEXT_TYPE + COMMA_SEP +
                    PendingTextContract.PendingTextEntry.COLUMN_NAME_TIMETOTEXT + TEXT_TYPE + COMMA_SEP +
                    PendingTextContract.PendingTextEntry.COLUMN_NAME_TEXTMESSAGE + TEXT_TYPE + COMMA_SEP +
                    PendingTextContract.PendingTextEntry.COLUMN_NAME_SENDSTATUS + TEXT_TYPE +

                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PendingTextContract.PendingTextEntry.TABLE_NAME;


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TextMessages.db";

    public PendingTextDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        Log.e("main", "onCreate SQLite");
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}