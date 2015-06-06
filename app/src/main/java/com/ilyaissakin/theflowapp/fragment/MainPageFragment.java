package com.ilyaissakin.theflowapp.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.activity.MainActivity;
import com.ilyaissakin.theflowapp.helpers.ConstantStrings;
import com.ilyaissakin.theflowapp.helpers.PageInitializer;

import org.jsoup.Jsoup;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {

    private LinearLayout rootLayout;
    private LinearLayout popularLayout;
    public static int pageToLoad = 1;

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

        rootLayout = (LinearLayout) getView().findViewById(R.id.featureRootLayout);
        popularLayout = (LinearLayout) getView().findViewById(R.id.popularLayout);

        rootLayout.setOnClickListener(new View.OnClickListener() {
            // TODO убрать костыль. Клик идёт не по вьюшке с лоадмоар, а по всему лэйауту.
            @Override
            public void onClick(View view) {
                //Log.d("LOADMORE", "clicked " + view.getId());
                Log.d("LOADMORE", "loading " + pageToLoad);
                (new MainPageLoaderTask(++pageToLoad)).execute();
                rootLayout.removeView(rootLayout.findViewById(R.id.loadMoreItem));
            }
        });

        if (MainActivity.lastRequestedMainPage == null) {
            (new MainPageLoaderTask(pageToLoad)).execute();
        } else {
            initializePage(true);
        }


    }

    private void initializePage(boolean fromStored) {
        if (fromStored) {
            PageInitializer.initializeFeaturesFromStoredElements(rootLayout, MainActivity.mainPageElements);
            PageInitializer.initializePopularFromStoredElements(popularLayout);
        } else {
            PageInitializer.initializeFeaturesFromDocument(rootLayout, MainActivity.lastRequestedMainPage);
            PageInitializer.initializePopularFromDocument(popularLayout, MainActivity.lastRequestedMainPage);
        }
    }

    private class MainPageLoaderTask extends AsyncTask {

        /*
        1 - default
        2 - more
         */
        private int page;

        public MainPageLoaderTask(int page) {
            this.page = page;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getView().getContext(), "Загрузка данных...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                MainActivity.lastRequestedMainPage = Jsoup.connect(ConstantStrings.ROOT_LINK_WITH_PROTOCOL + "/?page=" + page).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (MainActivity.lastRequestedMainPage == null) {
                Toast.makeText(getView().getContext(), "Не удалось загрузить данные", Toast.LENGTH_LONG).show();
                return;
            }
            initializePage(false);
        }
    }
}
