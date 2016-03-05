package com.codyirving.timetotext;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends Activity implements ActionBar.TabListener, ScheduleFragment.OnScheduleFragmentListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    static ViewPager mViewPager;
    public static ArrayList<String> aList;
    public static ArrayAdapter<String> aAdapter;
    /*public static String textMessageContent;
    public static Date timeToText;
    static Date dateToText;
    static String numberToText;*/
    public static String CONFIRM_FRAG_ID = "CONFIRM_TAG";
    public static String SCHEDULE_FRAG_ID = "SCHEDULE_TAG";
    public static String VIEWTEXT_FRAG_ID = "VIEWTEXT_TAG";
    public static ScheduledTextObject scheduledTextObject;

    //public static boolean REGISTERED = false;
    // ConfirmFragment confFrag;


    //public int getConfirmFrag() {
    //   return CONFIRM_FRAG_ID;
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState != null) {


            Date dateToText = new Date(savedInstanceState.getLong("dateToText"));
            String numberToText = savedInstanceState.getString("numberToText");
            Date timeToText = new Date(savedInstanceState.getLong("timeToText"));
            String textMessageContent = savedInstanceState.getString("textMessageContent");

            scheduledTextObject = new ScheduledTextObject();
            scheduledTextObject.setNumberToText(numberToText);
            scheduledTextObject.setTimeToText(timeToText);
            scheduledTextObject.setTextMessageContent(textMessageContent);


            //aList = savedInstanceState.getStringArrayList("aList");
            CONFIRM_FRAG_ID = savedInstanceState.getString("CONFIRM_FRAG_ID");
            SCHEDULE_FRAG_ID = savedInstanceState.getString("SCHEDULE_FRAG_ID");
            VIEWTEXT_FRAG_ID = savedInstanceState.getString("VIEWTEXT_FRAG_ID");


            //CURRENT_VIEW = savedInstanceState.getInt("CURRENT_VIEW");
            // MainActivity.aList = savedInstanceState.getStringArrayList("aList");
            //MainActivity.aAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, MainActivity.aList);
        }/*else{
            MainActivity.aList = new ArrayList<String>();
            MainActivity.aAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, MainActivity.aList);
        }*/

        scheduledTextObject = new ScheduledTextObject();
        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
               // mViewPager.getAdapter().notifyDataSetChanged();
                actionBar.setSelectedNavigationItem(position);

                Log.e("main", "Selected Page: " + position);

            }

        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
        mViewPager.setCurrentItem(1, true);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {


        //Bundle savedSettings = new Bundle();
        //public static ArrayList<String> aList;
        //public static ArrayAdapter<String> aAdapter;


        // public static int CONFIRM_FRAG_ID;
        //public int SCHEDULE_FRAG_ID;
        // public int VIEWTEXT_FRAG_ID;
        //static Date dateToText;
        //  static String numberToText;

        Log.e("main", "onSavedInstanceState");
        //if (ScheduledTextObject.get != null) outState.putLong("dateToText", dateToText.getTime());
        if (scheduledTextObject.getNumberToText() != null) outState.putString("numberToText", scheduledTextObject.getNumberToText());
        if (scheduledTextObject.getTimeToText() != null) outState.putLong("timeToText", scheduledTextObject.getTimeToText().getTime());
        if (scheduledTextObject.getTextMessageContent() != null) outState.putString("textMessageContent", scheduledTextObject.getTextMessageContent());
        //if(aList != null)outState.putStringArrayList("aList", aList);
        outState.putString("CONFIRM_FRAG_ID", CONFIRM_FRAG_ID);
        outState.putString("SCHEDULE_FRAG_ID", SCHEDULE_FRAG_ID);
        outState.putString("VIEWTEXT_FRAG_ID", VIEWTEXT_FRAG_ID);
        //mViewPager.getAdapter().notifyDataSetChanged();
        super.onSaveInstanceState(outState);
    }

/*

    public static Date getTimeToText() {

        //if(timeToText!=null) return timeToText;
        //else return  timeToText = new Date();
        return timeToText;
    }

    public static void setTimeToText(Long lt) {
        timeToText = new Date(lt);

    }

    public static void setTextMessageContent(String m) {
        textMessageContent = m;
    }


    public static Date getDateToText() {
        // if(dateToText!=null) return dateToText;
        // else return  dateToText = new Date();
        return dateToText;
    }

    public static String getTextMessageContent() {
        return textMessageContent;
    }
*/
    public Updateable getUpdateCallback() {
        return updateCallback;
    }

    private static boolean RESET = false;

    public void reset() {
        RESET = true;
        //aList = null;
        //aAdapter = null;
       /* scheduledTextObject.setTextMessageContent(null);
        scheduledTextObject.setTimeToText(null);
        //dateToText = null;
        scheduledTextObject.setNumberToText(null);*/
        scheduledTextObject = new ScheduledTextObject();

        ViewTextsFragment sf = (ViewTextsFragment) getFragmentManager().findFragmentById(R.id.schedule_fragment);
        if (sf == null) Log.e("main: ", "Frag NULL");
        if (sf != null) {

            sf.update();

            //ViewTextsFragment vf2 = (ViewTextsFragment)getFragmentManager().findFragmentById(VIEWTEXT_FRAG_ID);
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(sf);
            ft.attach(sf);
            ft.commit();
        }
        mViewPager.getAdapter().notifyDataSetChanged();


        //final int CONFIRM_FRAG_ID = 0;
        //final int SCHEDULE_FRAG_ID = 0;
        // final int VIEWTEXT_FRAG_ID = 0;


        //mViewPager.getAdapter().notifyDataSetChanged();


        //mViewPager.setCurrentItem(1, true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            passedfm = fm;
        }
        FragmentManager passedfm;
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Log.e("main", " getItem: " + position);

            switch (position) {

                case 0:
                    Log.e("main", "case 0: " + position);

                    //setContentView(R.layout.fragment_main);
                    //getFragmentManager().beginTransaction().replace(R.id.container, new ViewTextsFragment(), "VIEW_FRAG").commit();
                    Log.e("main", "Calling ViewTextsFragment");
                    ViewTextsFragment vf = new ViewTextsFragment();

                    VIEWTEXT_FRAG_ID = "android:switcher:" + R.id.viewtext_fragment + ":0";

                    Bundle args = new Bundle();
                    args.putString("VIEWTEXT_FRAG_ID", VIEWTEXT_FRAG_ID);
                    vf.setArguments(args);
                    return vf;

                case 1:
                    Log.d("main", "case 1: " + position);
                    // getFragmentManager().beginTransaction().replace(R.id.container, new ScheduleFragment(), "SCHEDULE_FRAG").commit();
                    Log.e("main", "Calling ScheduleFragment");
                    ScheduleFragment sf = new ScheduleFragment();

                    SCHEDULE_FRAG_ID = "android:switcher:" + R.id.schedule_fragment + ":1";
                    Log.e("main", "sf.getId: " + sf.getId() + " R.id.schedule_fragment: " + SCHEDULE_FRAG_ID);
                    Bundle args1 = new Bundle();
                    args1.putString("VIEWTEXT_FRAG_ID", VIEWTEXT_FRAG_ID);
                    sf.setArguments(args1);
                    return sf;

                case 2:
                    Log.d("main", "case 2: " + position);
                    Log.e("main", "Calling ConfirmFragment");
                    ConfirmFragment cf = new ConfirmFragment();
                    CONFIRM_FRAG_ID = "android:switcher:" + R.id.confirm_fragment + ":2";
                    Log.e("main", "cf.getId: " + cf.getId() + " R.id.confirm_fragment: " + CONFIRM_FRAG_ID);
                    Bundle args3 = new Bundle();
                    args3.putString("VIEWTEXT_FRAG_ID", VIEWTEXT_FRAG_ID);
                    cf.setArguments(args3);
                    return cf;


                case 3:

                    Log.d("main", "case 3: " + position);
                    break;
                default:

                    Log.d("main", "default: " + position);
                    break;
            }


            //todo handle this error case

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getItemPosition(Object object) {
            String objectName = object.getClass().toString();
            Log.e("main", "getPosition - called ... classis: " + object.getClass().toString());
            //return POSITION_NONE;

            if(objectName.contains("com.codyirving.timetotext.ScheduleFragment")) {
                try {
                    Log.e("main", "scheduleFrag");
                    ScheduleFragment sf = (ScheduleFragment) object;
                    sf.update();

                } catch (Exception e) {
                    Log.e("main", "NOT updated:scheduleFrag");
                    e.printStackTrace();
                }
            }else if(objectName.contains("com.codyirving.timetotext.ConfirmFragment")) {
                Log.e("main", "FUCKIN FINALLY");
                try {
                    Log.e("main", "confirmFrag");
                    ConfirmFragment cf = (ConfirmFragment) object;
                    cf.update();

                } catch (Exception e) {
                    Log.e("main", "NOT updated: confirmFrag");
                    e.printStackTrace();
                }

            }else if(objectName.contains("com.codyirving.timetotext.ViewTextsFragment")) {
                try {
                    Log.e("main", "viewTextsFrag");
                    ViewTextsFragment vf = (ViewTextsFragment) object;
                    vf.update();

                    //ViewTextsFragment vf2 = (ViewTextsFragment)getFragmentManager().findFragmentById(VIEWTEXT_FRAG_ID);
                /*final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(vf);
                ft.attach(vf);
                ft.commit();*/
                } catch (Exception e) {
                    Log.e("main", "NOT updated: viewTextsFrag");
                    e.printStackTrace();
                }
            }
            return super.getItemPosition(object);
        }


        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }


    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    @Override
    public void onChangedTime(Long t) {
        Log.e("main", " Callback onChangedTime received, " + t);
        scheduledTextObject.setTimeToText(new Date(t));
        //dateToText = timeToText;
        mViewPager.getAdapter().notifyDataSetChanged();
    }


    @Override
    public void onChangedDate(Long d) {
        Log.e("main", " Callback onChangedDate received, " + d);
        //dateToText = new Date(d);
        //timeToText = dateToText;
        //mViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onChangedMessage(String message) {
        Log.e("main", "Callback onChangedMessage received, " + message);
        scheduledTextObject.setTextMessageContent(new String(message));
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onChangedPhone(String phone) {
        Log.e("main", "Callback onChangedPhone received, " + phone);
        scheduledTextObject.setNumberToText(new String(phone));
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    Updateable updateCallback;

    public interface Updateable {
        public void update();
    }

    public void setUpdateCallback(Updateable updateCallback) {
        this.updateCallback = updateCallback;
    }

    public static Object getObject() {
        return scheduledTextObject;
    }
    @Override
    protected void onResume() {
        mViewPager.getAdapter().notifyDataSetChanged();
        reset();
        ViewTextsFragment vf = (ViewTextsFragment) getFragmentManager().findFragmentById(R.id.viewtext_fragment);
        Log.e("main", "on resume: frag is  " + vf);
        if (vf != null) {

            Log.e("main", "onResume called, not null! hooray");
            vf.update();

            //ViewTextsFragment vf2 = (ViewTextsFragment)getFragmentManager().findFragmentById(VIEWTEXT_FRAG_ID);
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(vf);
            ft.attach(vf);
            ft.commit();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e("main", "onPause - MainActivity");

        super.onPause();
    }


}