package com.ilyaissakin.theflowapp.fragment;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.activity.MainActivity;
import com.ilyaissakin.theflowapp.helpers.ConstantStrings;
import com.ilyaissakin.theflowapp.helpers.FeaturesInitializer;
import com.ilyaissakin.theflowapp.view.FeatureView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {

    private LinearLayout rootLayout;

    public MainPageFragment() {
        // Required empty public constructor
    }

    public static MainPageFragment newInstance() {
        MainPageFragment fragment = new MainPageFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rootLayout = (LinearLayout) getView().findViewById(R.id.rootLayout);

        if (MainActivity.mainPage == null) {
            (new MainPageLoaderTask()).execute();
        } else {
            initializeFeatures();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(1);
    }

    private void initializeFeatures() {
        FeaturesInitializer.initialize(rootLayout, MainActivity.mainPage);
    }

    private class MainPageLoaderTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                MainActivity.mainPage = Jsoup.connect(ConstantStrings.ROOT_LINK_WITH_PROTOCOL).get();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getView().getContext(), "Не удалось загрузить данные", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            initializeFeatures();
        }
    }
}
