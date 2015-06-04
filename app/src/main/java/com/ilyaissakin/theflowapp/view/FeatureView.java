package com.ilyaissakin.theflowapp.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.helpers.ConstantStrings;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;

/**
 * Created by Ilya on 03.06.2015.
 */
public class FeatureView extends RelativeLayout {

    private ImageView featureImage;
    private TextView featureHeader;
    private TextView featureDescription;

    private String featureLink;

    public FeatureView(Context context) {
        super(context);
    }

    public FeatureView(Context context, HashMap values) {
        super(context);

        inflate(getContext(), R.layout.news_item, this);

        featureImage = (ImageView) this.findViewById(R.id.featureImage);
        featureHeader = (TextView) this.findViewById(R.id.featureHeader);
        featureDescription = (TextView) this.findViewById(R.id.featureDescription);

        ImageLoader.getInstance().displayImage((String) values.get(ConstantStrings.HASHMAP_IMAGE_LINK_KEY), featureImage);

        featureHeader.setText((String) values.get(ConstantStrings.HASHMAP_FEATURE_HEADER_KEY));
        featureDescription.setText((String) values.get(ConstantStrings.HASHMAP_FEATURE_DESCRIPTION_KEY));

        featureLink = (String) values.get(ConstantStrings.HASHMAP_IMAGE_LINK_KEY);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO put link in intent and start PostActivity
                Toast.makeText(view.getContext(), featureHeader.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
