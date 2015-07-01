package com.ilyaissakin.theflowapp.helpers;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.activity.MainActivity;
import com.ilyaissakin.theflowapp.view.FeatureView;
import com.ilyaissakin.theflowapp.view.LoadMoreView;
import com.ilyaissakin.theflowapp.view.PopularView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ilya on 04.06.2015.
 */
public class PageInitializer {

    public static void initializeFeaturesFromDocument(LinearLayout rootLayout, Document mainPage) {
        mainPage.select(".shortnews").remove(); // среди обычных публикаций теперь блоки с короткими ссылками. убрать.
        Elements pItems = mainPage.select(ConstantStrings.MAINPAGE_PUB_ITEM_SELECTOR);
        MainActivity.mainPageElements.add(pItems);

        for (Element pItem : pItems) {
            if (pItem.select(".banner").size() > 0) continue; // они добавили рекламные блоки.

            String link = ConstantStrings.ROOT_LINK_WITH_PROTOCOL
                    + pItem.select(ConstantStrings.PUB_ITEM_LINK_SELECTOR).get(0).attr("href");

            String photoLink = ConstantStrings.ROOT_LINK_WITH_PROTOCOL
                    + pItem.select(ConstantStrings.PUB_ITEM_PHOTO_SELECTOR).get(0).attr("src");

            String header = pItem.select(ConstantStrings.PUB_ITEM_HEADER_SELECTOR).get(0).text();
            String description = pItem.select(ConstantStrings.PUB_ITEM_DESCRIPTION_SELECTOR).get(0).text();

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

    public static void initializePopularFromDocument(LinearLayout popularLayout, Document mainPage) {
        try {
            Elements pItems = mainPage.select(ConstantStrings.MAINPAGE_POPULAR_ITEM_SELECTOR);
            MainActivity.popularElements = pItems;

            for (Element pItem : pItems) {
                String link = ConstantStrings.ROOT_LINK_WITH_PROTOCOL
                        + pItem.select(ConstantStrings.POPULAR_ITEM_LINK_SELECTOR).get(0).attr("href");
                String text = pItem.select(ConstantStrings.POPULAR_ITEM_HEADER_SELECTOR).text();

                String style = pItem.attr("style");
                String bgLink = ConstantStrings.ROOT_LINK_WITH_PROTOCOL
                        + style.substring(style.indexOf("/up"), style.indexOf(")") - 1);

                HashMap map = new HashMap(3);
                map.put(ConstantStrings.HASHMAP_FEATURE_HEADER_KEY, text);
                map.put(ConstantStrings.HASHMAP_FEATURE_LINK_KEY, link);
                map.put(ConstantStrings.HASHMAP_IMAGE_LINK_KEY, bgLink);

                popularLayout.addView(new PopularView(popularLayout.getContext(), map));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initializeFeaturesFromStoredElements(LinearLayout rootLayout, ArrayList<Elements> elements) {
        for (Elements pItems : elements) {
            for (Element pItem : pItems) {
                if (pItem.select(".banner").size() > 0) continue; // они добавили рекламные блоки.
                
                String link = ConstantStrings.ROOT_LINK_WITH_PROTOCOL
                        + pItem.select(ConstantStrings.PUB_ITEM_LINK_SELECTOR).get(0).attr("href");

                String photoLink = ConstantStrings.ROOT_LINK_WITH_PROTOCOL
                        + pItem.select(ConstantStrings.PUB_ITEM_PHOTO_SELECTOR).get(0).attr("src");

                String header = pItem.select(ConstantStrings.PUB_ITEM_HEADER_SELECTOR).get(0).text();
                String description = pItem.select(ConstantStrings.PUB_ITEM_DESCRIPTION_SELECTOR).get(0).text();

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

    public static void initializePopularFromStoredElements(LinearLayout popularLayout) {
        try {
            Elements pItems = MainActivity.popularElements;

            for (Element pItem : pItems) {
                String link = ConstantStrings.ROOT_LINK_WITH_PROTOCOL
                        + pItem.select(ConstantStrings.POPULAR_ITEM_LINK_SELECTOR).get(0).attr("href");
                String text = pItem.select(ConstantStrings.POPULAR_ITEM_HEADER_SELECTOR).text();

                String style = pItem.attr("style");
                String bgLink = ConstantStrings.ROOT_LINK_WITH_PROTOCOL
                        + style.substring(style.indexOf("/up"), style.indexOf(")") - 1);

                HashMap map = new HashMap(3);
                map.put(ConstantStrings.HASHMAP_FEATURE_HEADER_KEY, text);
                map.put(ConstantStrings.HASHMAP_FEATURE_LINK_KEY, link);
                map.put(ConstantStrings.HASHMAP_IMAGE_LINK_KEY, bgLink);

                popularLayout.addView(new PopularView(popularLayout.getContext(), map));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
