package com.ilyaissakin.theflowapp.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.activity.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppInfoFragment extends Fragment {


    public AppInfoFragment() {
        // Required empty public constructor
    }

    public static AppInfoFragment newInstance() {
        return new AppInfoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_info, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).onSectionAttached(2);
    }
}
