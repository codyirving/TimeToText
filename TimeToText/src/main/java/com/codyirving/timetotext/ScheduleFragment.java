package com.codyirving.timetotext;

import android.animation.Animator;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Cody on 4/26/14.
 */
public class ScheduleFragment extends Fragment {

    ArrayList<String> contactArrayList;
    ArrayAdapter<String> contactArrayAdapter;
    static Long longtime = null;
    View rootView;
    ScheduledTextObject scheduleTextObject;
    public ScheduleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("main", "onCreateView: ScheduleFragment");
        //return super.onCreateView(inflater, container, savedInstanceState);
        //MainActivity.CURRENT_VIEW = 1;
        //todo add onSavedInstance for below.
        if(savedInstanceState != null) {
            Log.e("main", "SAVED INSTANCE NOT NULL, loading");
            //longtime = savedInstanceState.getLong("longtime");
            longtime = scheduleTextObject.getTimeToText().getTime();
            //contactArrayList = savedInstanceState.getStringArrayList("contactArrayList");
           // contactArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, contactArrayList);
            //contactArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_list_view, contactArrayList);
           // MainActivity.aAdapter = contactArrayAdapter;
           // MainActivity.aAdapter.notifyDataSetChanged();
        }
        rootView = inflater.inflate(R.layout.fragment_newmessage, container, false);
        return rootView;

    }




    public void update() {
    if(scheduleTextObject != null) {
        if (scheduleTextObject.getTimeToText() != null) {
            Log.e("main:schedulefrag", "update() getTimeToText");
            TextView textViewSchedule = (TextView) rootView.findViewById(R.id.timetotextdisplay);
            textViewSchedule.setText(scheduleTextObject.getTimeToText().toString());
        } else {

            TextView textViewSchedule = (TextView) rootView.findViewById(R.id.timetotextdisplay);
            textViewSchedule.setText("Today");
        }
        if (scheduleTextObject.getTextMessageContent() != null) {
            Log.e("main:schedulefrag", "update() getTextMsg");
            TextView textViewSchedule = (TextView) rootView.findViewById(R.id.messagetotextdisplay);
            textViewSchedule.setText(scheduleTextObject.getTextMessageContent());
        } else {
            TextView textViewSchedule = (TextView) rootView.findViewById(R.id.messagetotextdisplay);
            textViewSchedule.setText("Have a Nice Day!");
        }
        if (scheduleTextObject.getNumberToText() != null) {
            Log.e("main:schedulefrag", "update() getNumberToText");
            TextView textViewSchedule = (TextView) rootView.findViewById(R.id.contacttotextdisplay);
            textViewSchedule.setText(scheduleTextObject.getNumberToText());
        } else {
            TextView textViewSchedule = (TextView) rootView.findViewById(R.id.contacttotextdisplay);
            textViewSchedule.setText("");
        }

    }
    }
       @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);




          /*  //Toast.makeText(getActivity().getBaseContext(),"Here2", Toast.LENGTH_LONG).show();


            final DatePicker dp =  (DatePicker) getView().findViewById(R.id.datePicker);
            final TimePicker tp = (TimePicker) getView().findViewById(R.id.timePicker);

            if(longtime != null) {

                ((DatePicker) getView().findViewById(R.id.datePicker)).setMaxDate(longtime);
                Date thedate = (Date) new Date(longtime);
                Time thetime = (Time) new Time();
                thetime.set(thedate.getTime());
                ((TimePicker) getView().findViewById(R.id.timePicker)).setCurrentHour(thetime.hour);
                ((TimePicker) getView().findViewById(R.id.timePicker)).setCurrentMinute(thetime.minute);

            }*/
            ScheduledTextObject scheduleFragmentObject = (ScheduledTextObject) MainActivity.getObject();

            if(scheduleFragmentObject.getNumberToText() != null) {
                TextView messageToSend = (TextView) getView().findViewById(R.id.contacttotextdisplay);
                messageToSend.setText(scheduleFragmentObject.getNumberToText());
            }
           if(scheduleFragmentObject.getTextMessageContent() != null) {
               TextView messageToSend = (TextView) getView().findViewById(R.id.messagetotextdisplay);
               messageToSend.setText(scheduleFragmentObject.getTextMessageContent());
           }
           if(scheduleFragmentObject.getTimeToText() != null) {
               TextView timeToSend = (TextView) getView().findViewById(R.id.timetotextdisplay);
               timeToSend.setText(scheduleFragmentObject.getTimeToText().toString());
           }
          /* if(scheduleFragmentObject.getDateToText() != null) {
               TextView dateToSend = (TextView) getView().findViewById(R.id.datetotextdisplay);
               dateToSend.setText(scheduleFragmentObject.getDateToText().toString());

           }*/
           Button buttonPickTime = (Button) getView().findViewById(R.id.timetotext);
           Button buttonPickDate = (Button) getView().findViewById(R.id.datetotext);
           Button buttonPickMessage = (Button) getView().findViewById(R.id.messagetotext);
           Button buttonPickContact = (Button) getView().findViewById(R.id.contacttotext);





           buttonPickTime.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {


                   TextView tv2 = (TextView) getActivity().findViewById(R.id.timetotext);
                   float rotate = 360.0f;
                   tv2.animate().rotationYBy(2 * rotate).setDuration(500).setListener(new Animator.AnimatorListener() {
                       @Override
                       public void onAnimationStart(Animator animator) {

                       }

                       @Override
                       public void onAnimationEnd(Animator animator) {

                       }

                       @Override
                       public void onAnimationCancel(Animator animator) {

                       }

                       @Override
                       public void onAnimationRepeat(Animator animator) {

                       }
                   });


                   view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                   DialogFragment newFragment = new TimePickerFragment();
                   newFragment.show(getFragmentManager(), "timePicker");

               }
           });
               buttonPickDate.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                       DialogFragment newFragment = new DatePickerFragment();
                       newFragment.show(getFragmentManager(), "datePicker");

                   }
               });
                   buttonPickMessage.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                           DialogFragment newFragment = new MessageMakerFragment();
                           newFragment.show(getFragmentManager(), "messageMaker");



                       }
                   });


                       /* ListView contactListView = (ListView) getView().findViewById(R.id.contacttotextdisplay);
                        contactArrayList = new ArrayList<String>();
                        contactArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, contactArrayList);
                        //contactArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_list_view, contactArrayList);
                        contactListView.setAdapter(contactArrayAdapter);
                        Log.e("main", "setting MainActivity.aAdapter = contactArrayAdapter, ScheduleFragment.java");
                        MainActivity.aAdapter = contactArrayAdapter;*/

                        buttonPickContact.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                               Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                               startActivityForResult(contactPickerIntent, 1);
                               //DialogFragment newFragment = new TimePickerFragment();
                               //newFragment.show(getFragmentManager(), "timePicker");

                           }



             /*       int hr = tp.getCurrentHour();
                int min = tp.getCurrentMinute();
                tp.setSaveFromParentEnabled(false);
                dp.setSaveFromParentEnabled(false);
                GregorianCalendar selectedYear = new GregorianCalendar(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), hr, min);
                longtime = selectedYear.getTimeInMillis();
                MainActivity.timeToText = new Date(longtime);
                // Toast.makeText(getActivity().getBaseContext(), "You will text at: " + hr + ":" + min + " on " + selectedYear.toString(), Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction().replace(R.id.container, new ContactFragment()).addToBackStack(null).commit();*/


        });




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String phoneNo = null;
        if (resultCode != 0) {


            Uri uri = data.getData();
            Log.e("main", uri.toString());
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            String contactID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, )

            Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactID, null, null);
            phones.moveToFirst();

            String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


            Log.e("main", "Phone Number:  " + number);

           // contactArrayAdapter.clear();
            //contactArrayAdapter.add(number);
           // contactArrayAdapter.notifyDataSetChanged();
           // MainActivity.aAdapter = contactArrayAdapter;
            TextView contactToDisplay = (TextView) getView().findViewById(R.id.contacttotextdisplay);
            contactToDisplay.setText(number);
            mCallback.onChangedPhone(number);

    }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if(longtime != null)        outState.putLong("longtime", longtime);
        if(contactArrayList != null) outState.putStringArrayList("contactArrayList", contactArrayList);
        super.onSaveInstanceState(outState);
    }



    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        int callcount = 0;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }









        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i2) {

            if (callcount == 0) {
                Log.e("main", "onTimeSet");

                int hr = timePicker.getCurrentHour();
                int min = timePicker.getCurrentMinute();

                timePicker.setSaveFromParentEnabled(false);



                //ConfirmFragment cf = (ConfirmFragment) getChildFragmentManager().findFragmentById(R.id.confirm_fragment);

               /* if(scheduleTextObject.getTimeToText() != null) {
                    long l = MainActivity.dateToText.getTime();
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(l);
                    GregorianCalendar selectedYear = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), hr, min);
                    longtime = selectedYear.getTimeInMillis();
                }else */{
                    Calendar c = Calendar.getInstance();

                    GregorianCalendar selectedYear = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), hr, min);
                    longtime = selectedYear.getTimeInMillis();
                }

                mCallback.onChangedTime(longtime);

               /* if (cf != null) {
                    Log.e("main", "not null");
                    cf.updateTime(longtime);
                } else {
                    Log.e("main", "null");
                    cf = new ConfirmFragment();
                    cf.updateTime(longtime);
                }*/


                TextView tv1;
                //TextView tv2;
                tv1 = (TextView) getActivity().findViewById(R.id.timetotextdisplay);
                //tv2 = (TextView) getActivity().findViewById(R.id.timetotext);
                float rotate = 360.0f;
                float scaleX = 1.5f;
                float scaleY = 1.5f;
                tv1.setAlpha(0.0f);
                tv1.setVisibility(View.VISIBLE);

                tv1.animate().alpha(1f).rotationXBy(rotate).setDuration(600).setListener(null);


                //tv1.animate().rotationXBy(rotate);

                TextView tv2 = (TextView) getActivity().findViewById(R.id.timetotext);

                tv2.animate().rotationXBy(2 * rotate).setDuration(500).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });


                String ampm = "AM";
                if (i >= 12) {
                    if (i != 12) {
                        i = i - 12;
                    }
                    ampm = "PM";
                }
                if (i == 0) i = 12;
                String addZero = "";
                if (i2 < 10) addZero = "0";

                tv1.setText(i + ":" + addZero + i2 + ampm);
            }else {
                Log.e("main", "callcount not zero");
            }
            callcount++;
        }
    }
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private boolean positive = true;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int monthOfYear = c.get(Calendar.MONTH);
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of TimePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, monthOfYear, dayOfMonth);
        }
        Date datePicked;
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                TextView tv = (TextView) getActivity().findViewById(R.id.datetotextdisplay);
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int monthOfYear = c.get(Calendar.MONTH);
            Log.e("main", "MONTH OF YEAR: " + monthOfYear);
                int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                int direction = positive ? -1 : 1;
                float rotate = 360f * direction;
                positive = !positive;



                /*if(MainActivity.timeToText != null) {
                    Long t = MainActivity.timeToText.getTime();
                    Calendar d = Calendar.getInstance();
                    d.setTimeInMillis(t);
                    GregorianCalendar selectedYear = new GregorianCalendar(i, i2, i3, d.get(Calendar.HOUR_OF_DAY), d.get(Calendar.MINUTE));
                    longtime = selectedYear.getTimeInMillis();

                }else */{
                    Calendar d = Calendar.getInstance();

                    GregorianCalendar selectedYear = new GregorianCalendar(i, i2, i3, d.get(Calendar.HOUR_OF_DAY), d.get(Calendar.MINUTE));
                    longtime = selectedYear.getTimeInMillis();
                }

                mCallback.onChangedDate(longtime);
                /*datePicked = new Date();
                datePicked.setTime(longtime);
                mCallback.onChangedDate(datePicked);*/


            Log.e("main", "Positive: " + positive);
                if(year == i && monthOfYear == i2 && dayOfMonth == i3) {
                    tv.setText("Today");

                    tv.animate().rotationYBy(rotate).setDuration(1000).setListener(null);


                }else {
                    tv.setText((i2+1) + "/" + i3 + "/" + i);
                    tv.animate().rotationYBy(rotate).setDuration(1000).setListener(null);
                }

                tv.animate().rotationYBy(-rotate).setDuration(1000).setListener(null);

        }

    }



    public static class MessageMakerFragment extends DialogFragment implements EditText.OnEditorActionListener {
        private EditText messageInput;

       /* @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.message_input, container);
            messageInput = (EditText) view.findViewById(R.id.txt_message);
            getDialog().setTitle("Enter your message!");
            getDialog().setCancelable(true);

            messageInput.requestFocus();
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            messageInput.setOnEditorActionListener(this);
            return view;
        }*/

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            LayoutInflater layoutInflater = getActivity().getLayoutInflater();

            View view = layoutInflater.inflate(R.layout.message_input, null);
            messageInput = (EditText) view.findViewById(R.id.txt_message);

            //getDialog().setTitle("Enter your message!");
            //getDialog().setCancelable(true);

            TextView tv = (TextView) getActivity().findViewById(R.id.messagetotextdisplay);
            Log.e("main", "onCreateDialog mlength: " + tv.getText().length());
            if(tv.getText().length() != 0) messageInput.setText(tv.getText());

            messageInput.requestFocus();
            //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            //messageInput.setOnEditorActionListener(this);
            AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
            ad.setTitle("Enter Your Message!");
            ad.setView(view);
            ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    TextView tv = (TextView) getActivity().findViewById(R.id.messagetotextdisplay);

                    tv.setText(messageInput.getText());
                    String inputText = (String) tv.getText().toString();
                    mCallback.onChangedMessage(inputText);
                    tv.animate().rotationXBy(360f).setDuration(1000).setListener(null);
                    //tv.setMovementMethod(new ScrollingMovementMethod());
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            return ad.create();
            //return super.onCreateDialog(savedInstanceState);
        }
        String messagetotext;
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            /*Log.e("main", "onEditorAction");
            TextView tv = (TextView) getActivity().findViewById(R.id.messagetotextdisplay);
            messagetotext = textView.getText().toString();

            tv.setText(textView.getText());

            tv.animate().rotationXBy(360f);
            if (EditorInfo.IME_ACTION_DONE == actionId) {
                // Return input text to activity
                getActivity();
                this.dismiss();
                return true;
            }*/
            return false;
        }

        }




    static OnScheduleFragmentListener mCallback;

    public interface OnScheduleFragmentListener {
        public void onChangedTime(Long t);
        public void onChangedDate(Long d);
        public void onChangedMessage(String m);
        public void onChangedPhone(String p);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnScheduleFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnScheduleFragemntListener");
        }
    }



}