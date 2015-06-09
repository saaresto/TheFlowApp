package com.ilyaissakin.theflowapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.helpers.ConstantStrings;

public class DisqusActivity extends Activity {

    private WebView webView;
    private String disqusId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disqus);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        webView = (WebView) findViewById(R.id.webView);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);

        CookieManager cm = CookieManager.getInstance();
        cm.setAcceptCookie(true);

        Intent intent = getIntent();
        disqusId = intent.getStringExtra(ConstantStrings.INTENT_DSQID_KEY);

        webView.loadDataWithBaseURL("http://" + ConstantStrings.DISQUS_SHORT_NAME + ".disqus.com/",
                getDisqusThread(disqusId, ConstantStrings.DISQUS_SHORT_NAME),
                "text/html",
                "UTF-8",
                "");
    }

    private String getDisqusThread(String disqusId, String shortName) {

        return "<html><head></head><body><div id='disqus_thread'></div>"
                + "<script type=\"text/javascript\">\n" +
                "    var disqus_shortname = '" + shortName + "';\n" +
                "    var disqus_url = '" + disqusId + "';\n" +
                "    /* * * DON'T EDIT BELOW THIS LINE * * */\n" +
                "    (function() {\n" +
                "        var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;\n" +
                "        dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';\n" +
                "        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);\n" +
                "    })();\n" +
                "</script></body></html>";
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_disqus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
