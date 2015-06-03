package com.ilyaissakin.theflowapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.activity.MainActivity;
import com.ilyaissakin.theflowapp.helpers.ConstantStrings;
import com.ilyaissakin.theflowapp.view.FeatureView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {

            HashMap map = new HashMap();
            map.put(ConstantStrings.HASHMAP_IMAGE_LINK_KEY, "http://the-flow.ru/uploads/images/resize/300x0/adaptiveResize/15/04/22/97/93/47ac2f540cd7.png");
            map.put(ConstantStrings.HASHMAP_FEATURE_DESCRIPTION_KEY, "Description");
            map.put(ConstantStrings.HASHMAP_FEATURE_HEADER_KEY, "Header");
            map.put(ConstantStrings.HASHMAP_FEATURE_LINK_KEY, "Link");


            LinearLayout rl = (LinearLayout)getView().findViewById(R.id.rootLayout);
            View v = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.news_item, null);
            rl.addView(v);
            rl.addView(new FeatureView(getView().getContext(), map));
            rl.addView(new FeatureView(getView().getContext(), map));

            ImageLoader.getInstance().displayImage("http://the-flow.ru/uploads/images/resize/300x0/adaptiveResize/15/04/22/97/93/47ac2f540cd7.png",
                    (ImageView) v.findViewById(R.id.featureImage));

            Log.d("LAYOUT", rl.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("EXCEPTION", e.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
