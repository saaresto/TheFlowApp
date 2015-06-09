package com.ilyaissakin.theflowapp.helpers;

/**
 * Created by Ilya on 03.06.2015.
 */
public class ConstantStrings {
    public static String DATABASE_NAME = "theflowdb";
    public static String[] DATABASE_COLUMNS = {"link", "title"};

    public static String HASHMAP_IMAGE_LINK_KEY = "FEATURE_IMG_LINK";
    public static String HASHMAP_FEATURE_HEADER_KEY = "FEATURE_HEADER";
    public static String HASHMAP_FEATURE_DESCRIPTION_KEY = "FEATURE_DESCRIPTION";
    public static String HASHMAP_FEATURE_LINK_KEY = "FEATURE_LINK";
    public static String INTENT_HASHMAP_KEY = "VALUES";
    public static String INTENT_DSQID_KEY = "ID";
    public static String DISQUS_SHORT_NAME = "the-flow2014";

    /*
     * Parser selectors
     */
    public static String ROOT_LINK_WITH_PROTOCOL = "http://the-flow.ru";
    public static String MAINPAGE_PUB_ITEM_SELECTOR = ".items > .publication__item";
    public static String PUB_ITEM_PHOTO_SELECTOR = ".publication__item-photo";
    public static String PUB_ITEM_HEADER_SELECTOR = ".publication__item-title > a";
    public static String PUB_ITEM_DESCRIPTION_SELECTOR = ".publication__item-text";
    public static String PUB_ITEM_LINK_SELECTOR = "a.hover_shadow";

    public static String MAINPAGE_POPULAR_ITEM_SELECTOR = ".homepage_slider li.hover_shadow";
    public static String POPULAR_ITEM_HEADER_SELECTOR = ".homepage_slider__text > div";
    public static String POPULAR_ITEM_LINK_SELECTOR = "a";

    public static String POST_ARTICLE_SELECTOR = "div.article";
    public static String POST_COUNTERS_SELECTOR = ".article__counters";
}
