package com.ilyaissakin.theflowapp.helpers;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.activity.MainActivity;
import com.ilyaissakin.theflowapp.view.FeatureView;
import com.ilyaissakin.theflowapp.view.LoadMoreView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ilya on 04.06.2015.
 */
public class FeaturesInitializer {

    public static void initializeFromDocument(LinearLayout rootLayout, Document mainPage) {
        Log.d("INITIALIZATION", "Initializing from document.");
        Elements pItems = mainPage.select(ConstantStrings.MAINPAGE_ITEM_SELECTOR);
        MainActivity.mainPageElements.add(pItems);

        for (Element pItem : pItems) {
            String link = ConstantStrings.ROOT_LINK_WITH_PROTOCOL
                    + pItem.getElementsByClass(ConstantStrings.ITEM_LINK_SELECTOR).attr("href");

            String photoLink = ConstantStrings.ROOT_LINK_WITH_PROTOCOL
                    + pItem.getElementsByClass(ConstantStrings.ITEM_PHOTO_SELECTOR).attr("src");

            Element headerE = pItem.select(ConstantStrings.ITEM_HEADER_SELECTOR).get(0);
            String header = headerE.text();
            String description = pItem.getElementsByClass(ConstantStrings.ITEM_DESCRIPTION_SELECTOR).get(0).text();

            HashMap map = new HashMap(4);
            map.put(ConstantStrings.HASHMAP_FEATURE_HEADER_KEY, header);
            map.put(ConstantStrings.HASHMAP_FEATURE_LINK_KEY, link);
            map.put(ConstantStrings.HASHMAP_FEATURE_DESCRIPTION_KEY, description);
            map.put(ConstantStrings.HASHMAP_IMAGE_LINK_KEY, photoLink);

            rootLayout.addView(new FeatureView(rootLayout.getContext(), map));
        }

        View loadMoewView = new LoadMoreView(rootLayout.getContext());
        loadMoewView.setId(R.id.loadMoreItem);
        rootLayout.addView(loadMoewView);
    }

    public static void initializeFromStoredElements(LinearLayout rootLayout, ArrayList<Elements> elements) {
        Log.d("INITIALIZATION", "Initializing from stored.");
        for (Elements pItems : elements) {
            for (Element pItem : pItems) {
                String link = ConstantStrings.ROOT_LINK_WITH_PROTOCOL
                        + pItem.getElementsByClass(ConstantStrings.ITEM_LINK_SELECTOR).attr("href");

                String photoLink = ConstantStrings.ROOT_LINK_WITH_PROTOCOL
                        + pItem.getElementsByClass(ConstantStrings.ITEM_PHOTO_SELECTOR).attr("src");

                Element headerE = pItem.select(ConstantStrings.ITEM_HEADER_SELECTOR).get(0);
                String header = headerE.text();
                String description = pItem.getElementsByClass(ConstantStrings.ITEM_DESCRIPTION_SELECTOR).get(0).text();

                HashMap map = new HashMap(4);
                map.put(ConstantStrings.HASHMAP_FEATURE_HEADER_KEY, header);
                map.put(ConstantStrings.HASHMAP_FEATURE_LINK_KEY, link);
                map.put(ConstantStrings.HASHMAP_FEATURE_DESCRIPTION_KEY, description);
                map.put(ConstantStrings.HASHMAP_IMAGE_LINK_KEY, photoLink);

                rootLayout.addView(new FeatureView(rootLayout.getContext(), map));
            }
        }

        View loadMoewView = new LoadMoreView(rootLayout.getContext());
        loadMoewView.setId(R.id.loadMoreItem);
        rootLayout.addView(loadMoewView);
    }
}
