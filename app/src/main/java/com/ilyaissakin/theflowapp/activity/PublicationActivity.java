package com.ilyaissakin.theflowapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.CookieManager;
import android.widget.Toast;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.helpers.ConstantStrings;
import com.ilyaissakin.theflowapp.helpers.DBHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class PublicationActivity extends Activity {

    private WebView webView;
    private HashMap values;
    private String disqusId = null;
    String link = null;
    String title = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);

        CookieManager cm = CookieManager.getInstance();

        Intent intent = getIntent();

        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            link = String.valueOf(intent.getData());
        } else {
            values = (HashMap)intent.getSerializableExtra(ConstantStrings.INTENT_HASHMAP_KEY);
            link = (String) (values).get(ConstantStrings.HASHMAP_FEATURE_LINK_KEY);
            title = (String) (values).get(ConstantStrings.HASHMAP_FEATURE_HEADER_KEY);
        }

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            (new PublicationLoaderTask(link)).execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_publication, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_favorite:
                DBHelper helper = new DBHelper(this);
                if (helper.insert(link, title)) {
                    Toast.makeText(this, "Добавлено в избранное.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Не удалось добавить в избранное. Скорее всего, статья уже там.", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_comments:
                if (link != null) {
                    Intent dsqIntent = new Intent(PublicationActivity.this, DisqusActivity.class);
                    dsqIntent.putExtra(ConstantStrings.INTENT_DSQID_KEY, link);
                    PublicationActivity.this.startActivity(dsqIntent);
                }
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    private class PublicationLoaderTask extends AsyncTask {

        private String link = null;
        Document document = null;
        private Element article = null;

        public PublicationLoaderTask(String link) {
            this.link = link;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(getBaseContext(), "Загрузка данных...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                document = Jsoup.connect(link).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (document == null) {
                Toast.makeText(getBaseContext(), "Не удалось загрузить документ.", Toast.LENGTH_LONG).show();
                return;
            }

            article = document.select(ConstantStrings.POST_ARTICLE_SELECTOR).get(0);
            article.select(ConstantStrings.POST_COUNTERS_SELECTOR).remove();
            article.select(".article__text").last().remove();

            title = article.select(".article__title").text();

            Elements images = article.select("img");
            for (Element img : images) {
                if (!img.attr("src").contains("http")
                        && img.attr("src").length() > 0) {
                    img.attr("src", ConstantStrings.ROOT_LINK_WITH_PROTOCOL + img.attr("src"));
                    img.attr("style", "");
                    img.attr("width", "");
                }
            }

            Elements links = document.head().select("link");

            for (Element elink : links) {
                if (elink.attr("rel") != null) {
                    elink.attr("href", ConstantStrings.ROOT_LINK_WITH_PROTOCOL + elink.attr("href"));
                } else {
                    elink.remove();
                }
            }

            Elements iframes = article.select("iframe");
            for (Element iframe : iframes) {
                if (iframe.attr("src").contains("youtu")) {
                    String thumbLink = "http://img.youtube.com/vi/" +
                            iframe.attr("src").split("/")[iframe.attr("src").split("/").length - 1] +
                            "/0.jpg";
                    Element img = new Element(Tag.valueOf("img"), "");
                    img.attr("src", thumbLink);

                    Element a = new Element(Tag.valueOf("a"), "");
                    a.attr("href", iframe.attr("src"));
                    a.text("Нажмите для просмотра видео:");
                    a.appendChild(img);
                    iframe.replaceWith(a);
                }
            }

            String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
            String page = header + "<html><head>"
                    + links.toString()
                    + "<style>" +
                    "img,iframe{width:100%; height:auto;}.article{padding:10px 10px; width: 100%; box-sizing: border-box; border: none;}.article__text{margin:0}" +
                    "</style></head>"
                    + "<body>"
                    + article.toString()
                    + "</body></html>";

            webView.loadData(page, "text/html; charset=UTF-8", null);
        }
    }
}
