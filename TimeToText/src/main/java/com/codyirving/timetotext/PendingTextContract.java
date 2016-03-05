package com.codyirving.timetotext;

import android.app.PendingIntent;
import android.provider.BaseColumns;

/**
 * Created by Cody on 4/10/14.
 */
public class PendingTextContract {

    public PendingTextContract() {

    }

    public static abstract class PendingTextEntry implements BaseColumns {
        public static final String TABLE_NAME = "message";
        public static final String COLUMN_NAME_PHONENUMBER = "phonenumber";
        public static final String COLUMN_NAME_TIMETOTEXT = "timetotext";
        public static final String COLUMN_NAME_TEXTMESSAGE = "textmessage";
        public static final String COLUMN_NAME_SENDSTATUS = "sendstatus";

        //added functionality columns
        public static final String COLUMN_NAME_CONTACTID = "contactid"; // cid for grabbing more info on pending/sent texts
        public static final String COLUMN_NAME_ACTUALTIMESENT = "actualtimesent"; // actual time sms was sent vs scheduled


    }


}


