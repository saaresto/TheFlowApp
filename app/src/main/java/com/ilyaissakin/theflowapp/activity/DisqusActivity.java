package com.ilyaissakin.theflowapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ilyaissakin.theflowapp.R;
import com.ilyaissakin.theflowapp.helpers.ConstantStrings;

public class DisqusActivity extends Activity {

    private WebView webView;
    private RelativeLayout root;
    private String disqusId;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disqus);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        root = (RelativeLayout) findViewById(R.id.popupRoot);
        webView = (WebView) findViewById(R.id.webView);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                menu.setGroupVisible(R.id.popup_group, true);

                WebView loginView = new WebView(DisqusActivity.this);
                WebSettings settings = loginView.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setJavaScriptCanOpenWindowsAutomatically(true);
                settings.setSupportMultipleWindows(true);
                settings.setUseWideViewPort(false);
                loginView.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        if (url.contains("success")) {
                            view.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
                            menu.setGroupVisible(R.id.popup_group, false);
                        }
                        return false;
                    }
                });
                loginView.setWebChromeClient(new WebChromeClient());

                loginView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                root.addView(loginView);
                root.setVisibility(View.VISIBLE);

                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(loginView);
                resultMsg.sendToTarget();

                return true;
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

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
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_close_popup:
                menu.setGroupVisible(R.id.popup_group, false);
                root.setVisibility(View.GONE);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
