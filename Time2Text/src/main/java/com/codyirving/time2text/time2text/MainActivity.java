package com.codyirving.time2text.time2text;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.security.KeyStore;


public class MainActivity extends Activity {
    int FRAGMENT_ACTIVE;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int position = 0;
        Bundle b = getIntent().getExtras();

        if (b != null  && b.isEmpty()) position = b.getInt("position");
        if (position == 1) {
            Fragment manageFragment = new ManageFragment();
            fragmentTransaction.add(R.id.main_layout, manageFragment, "FRAG_MANAGE");
            fragmentTransaction.commit();
        } else {

            Fragment navigationFragment = new NavigationFragment();
            fragmentTransaction.add(R.id.main_layout, navigationFragment, "FRAG_NAVIGATION");
            fragmentTransaction.commit();
        }
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        ctx = context;
        return super.onCreateView(name, context, attrs);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        Fragment fragment = (Fragment) getFragmentManager().findFragmentById(R.id.main_layout);
        String objectname = fragment.getClass().toString();
        Log.e("main", "onCreate - called ... classis: " + fragment.getClass().toString());
        if (objectname.contains("com.codyirving.time2text.time2text.ManageFragment")) {
            Log.e("main:", "manageFrag found");

            ((ManageFragment) fragment).update();

        }else{
            menu.removeItem(R.id.action_refresh);
            Log.e("main", "not manage frag");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                Log.e("main:", "Calling update()");

                Fragment fragment;
                String objectname;

                fragment = (Fragment) getFragmentManager().findFragmentById(R.id.main_layout);
                objectname = fragment.getClass().toString();
                Log.e("main", "getPosition - called ... classis: " + fragment.getClass().toString());
                if (objectname.contains("com.codyirving.time2text.time2text.ManageFragment")) {
                    Log.e("main:", "manageFrag found");

                    ((ManageFragment) fragment).update();

                }else{
                    Log.e("main", "not manage frag");
                }
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_clear:
                final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Clear All Sent Texts?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Fragment fragment;
                        String objectname;
                        fragment = (Fragment) getFragmentManager().findFragmentById(R.id.main_layout);
                        objectname = fragment.getClass().toString();
                        Log.e("main", "getPosition - called ... classis: " + fragment.getClass().toString());
                        if (objectname.contains("com.codyirving.time2text.time2text.ManageFragment")) {
                            ((ManageFragment) fragment).clearSent();
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();


            default:
                return super.onOptionsItemSelected(item);


        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("main", "onResume, MainActivity Called");
    }
}
