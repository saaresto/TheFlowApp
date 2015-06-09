package com.ilyaissakin.theflowapp.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.helpers.DBHelper;
import com.ilyaissakin.theflowapp.view.FavoriteItemView;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    private LinearLayout rootLayout;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rootLayout = (LinearLayout) getView().findViewById(R.id.favs_root_layout);

        DBHelper helper = new DBHelper(getView().getContext());
        HashMap<String, String> favs = helper.getFavsAsHashMap();

        for (Map.Entry entry : favs.entrySet()) {
            rootLayout.addView(new FavoriteItemView(getView().getContext(), (String)entry.getKey(), (String)entry.getValue()));
        }
    }
}
