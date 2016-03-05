package com.codyirving.time2text.time2text;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Cody on 6/5/2014.
 */
public class NavigationFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigator, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        boolean result = setupNavigationButtons();

        //record result of button setup
        String message = result ? "NavButtons Connected Successfully" : "NavButtons not added";
        Log.d("main", message);
    }

    //method to connect buttons to listeners
    boolean setupNavigationButtons() {

        try {
            //attach buttons to on click listeners
            Button manageButton = (Button) getView().findViewById(R.id.manage_button);
            Button newButton = (Button) getView().findViewById(R.id.new_button);
            manageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().beginTransaction().replace(R.id.main_layout, new ManageFragment(), "FRAG_MANAGE").addToBackStack("FRAG_NAVIGATION").commit();

                }
            });
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().beginTransaction().replace(R.id.main_layout, new NewFragment(), "FRAG_NEW").addToBackStack("FRAG_NAVIGATION").commit();
                }
            });
            //true if successful
            return true;
        } catch (Exception e) {
            Log.e("t2t", "Exception: " + e);
        }
        //false if error
        return false;



    }

}
