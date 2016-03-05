/*
package com.codyirving.timetotext;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



*/
/**
 * Created by Cody on 4/26/14.
 *//*

public class PromptFragment extends Fragment {
    int CURRENT_VIEW;



    public PromptFragment newInstance(Bundle B) {
        PromptFragment frag = new PromptFragment();
        frag.setArguments(B);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {





        return inflater.inflate(R.layout.fragment_one, container, false);
    }


    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);

        //BUTTON1: Create new text
        if (getView().isActivated()) {


            Button b1 = (Button) getView().findViewById(R.id.newTextickleButton);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("error", "PromptFragment:New Text Click");
                    //getFragmentManager().beginTransaction().replace(R.id.pager, new ScheduleFragment()).addToBackStack(null).commit();

                }
            });
            //BUTTON2: View Current and past Textickles
            Button b2 = (Button) getView().findViewById(R.id.viewTextickles);
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("error", "PromptFragment:View Texts Click");
                    getFragmentManager().beginTransaction().replace(R.id.container, new ViewTextsFragment()).addToBackStack(null).commit();

                }
            });


        }else {
            Log.e("main", "Error, view not active");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CURRENT_VIEW = 1;

       */
/* //BUTTON1: Create new text
        if (getView().isActivated()) {


            Button b1 = (Button) getView().findViewById(R.id.newTextickleButton);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("error", "PromptFragment:New Text Click");
                    //getFragmentManager().beginTransaction().replace(R.id.pager, new ScheduleFragment()).addToBackStack(null).commit();

                }
            });
            //BUTTON2: View Current and past Textickles
            Button b2 = (Button) getView().findViewById(R.id.viewTextickles);
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("error", "PromptFragment:View Texts Click");
                    // getFragmentManager().beginTransaction().replace(R.id.pager, new ViewTextsFragment()).addToBackStack(null).commit();

                }
            });


        }else {
            Log.e("main", "Error, view not active");
        }

*//*

    }
}*/
