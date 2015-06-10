package com.ilyaissakin.theflowapp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.activity.MainActivity;
import com.ilyaissakin.theflowapp.activity.PublicationActivity;
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
    private boolean isFullHD = true;

    public PopularView(Context context) {
        super(context);
    }

    public PopularView(final Context context, final HashMap values) {
        super(context);

        inflate(getContext(), R.layout.popular_item, this);

        background = (ImageView) this.findViewById(R.id.popular_item_background);
        text = (TextView) this.findViewById(R.id.popular_item_text);

        //ImageLoader.getInstance().displayImage((String) values.get(ConstantStrings.HASHMAP_IMAGE_LINK_KEY), background);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = wm.getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
                || size.x <= 1080)
            isFullHD = false;


        ImageLoader.getInstance()
                .displayImage((String) values.get(ConstantStrings.HASHMAP_IMAGE_LINK_KEY),
                        background,
                        new DisplayImageOptions.Builder()
                                //.cacheInMemory(true)
                                //.cacheOnDisk(true)
                                .postProcessor(new BitmapProcessor() {
                                    @Override
                                    public Bitmap process(Bitmap bmp) {
                                        if (!isFullHD) {
                                            return Bitmap.createScaledBitmap(bmp, size.x, (int) (size.x * 0.489), false);
                                        } else {
                                            return Bitmap.createScaledBitmap(bmp, size.x / 2, (int) (size.x * 0.489 / 2), false);
                                        }
                                    }
                                })
                                .build());

        text.setWidth(isFullHD ? size.x / 2 : size.x);
        text.setText((String) values.get(ConstantStrings.HASHMAP_FEATURE_HEADER_KEY));

        link = (String) values.get(ConstantStrings.HASHMAP_FEATURE_LINK_KEY);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                Intent intent = new Intent(context, PublicationActivity.class);
                intent.putExtra(ConstantStrings.INTENT_HASHMAP_KEY, values);
                context.startActivity(intent);
            }
        });

    }
}
