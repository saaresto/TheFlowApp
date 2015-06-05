package com.ilyaissakin.theflowapp.view;

import android.content.Context;
import android.widget.RelativeLayout;

import com.ilyaissakin.theflowapp.R;

/**
 * Created by Ilya on 04.06.2015.
 */
public class LoadMoreView extends RelativeLayout {
    public LoadMoreView(Context context) {
        super(context);

        inflate(getContext(), R.layout.load_more_item, this);
    }
}
