package com.ilyaissakin.theflowapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.helpers.ConstantStrings;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.util.HashMap;

/**
 * Created by Ilya on 06.06.2015.
 */
public class PopularView extends RelativeLayout {
    private ImageView background;
    private TextView text;
    private String link;

    public PopularView(Context context) {
        super(context);
    }

    public PopularView(Context context, HashMap values) {
        super(context);

        inflate(getContext(), R.layout.popular_item, this);

        background = (ImageView) this.findViewById(R.id.popular_item_background);
        text = (TextView) this.findViewById(R.id.popular_item_text);

        //ImageLoader.getInstance().displayImage((String) values.get(ConstantStrings.HASHMAP_IMAGE_LINK_KEY), background);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = wm.getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        ImageLoader.getInstance()
                .displayImage((String) values.get(ConstantStrings.HASHMAP_IMAGE_LINK_KEY),
                        background,
                        new DisplayImageOptions.Builder()
                                .cacheInMemory(true)
                                .cacheOnDisk(true)
                                .postProcessor(new BitmapProcessor() {
                                    @Override
                                    public Bitmap process(Bitmap bmp) {
                                        return Bitmap.createScaledBitmap(bmp, size.x, (int) (size.x * 0.489), false);
                                    }
                                })
                                .build());

        text.setWidth(size.x);

        text.setText((String) values.get(ConstantStrings.HASHMAP_FEATURE_HEADER_KEY));

        link = (String) values.get(ConstantStrings.HASHMAP_FEATURE_LINK_KEY);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO put link in intent and start PostActivity
                Toast.makeText(view.getContext(), link, Toast.LENGTH_LONG).show();
            }
        });

    }
}
