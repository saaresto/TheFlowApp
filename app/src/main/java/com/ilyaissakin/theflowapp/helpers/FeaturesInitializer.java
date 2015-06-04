package com.ilyaissakin.theflowapp.helpers;

import android.widget.LinearLayout;

import com.ilyaissakin.theflowapp.activity.MainActivity;
import com.ilyaissakin.theflowapp.view.FeatureView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * Created by Ilya on 04.06.2015.
 */
public class FeaturesInitializer {

    public static void initialize(LinearLayout rootLayout, Document mainPage) {
        Elements pItems = mainPage.select(ConstantStrings.MAINPAGE_ITEM_SELECTOR);
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
}
