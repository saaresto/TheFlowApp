package com.ilyaissakin.theflowapp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.helpers.DBHelper;

/**
 * Created by Ilya on 09.06.2015.
 */
public class FavoriteItemView extends RelativeLayout {

    private String link = null;
    private String title = null;


    public FavoriteItemView(Context context) {
        super(context);
    }

    public FavoriteItemView(Context context, final String link, String title) {
        super(context);

        this.link = link;
        this.title = title;

        inflate(getContext(), R.layout.favorite_item, this);

        TextView textView = (TextView) findViewById(R.id.favoriteTitle);
        textView.setText(title);

        ImageButton imageButton = (ImageButton) findViewById(R.id.deleteFavorite);
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.deleteFavorite:
                        break;
                    default:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                        getContext().startActivity(intent);
                }
            }
        });
    }

    public void delete() {
        if ((new DBHelper(getContext()).delete(link))) {
            Toast.makeText(getContext(), "Запись удалена.", Toast.LENGTH_SHORT).show();
            this.setVisibility(View.GONE);
        } else {
            Toast.makeText(getContext(), "Произошла ошибка при удалении.", Toast.LENGTH_LONG).show();
        }
    }
}
