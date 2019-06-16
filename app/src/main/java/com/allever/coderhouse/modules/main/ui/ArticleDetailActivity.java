package com.allever.coderhouse.modules.main.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.allever.coderhouse.R;

/**
 * Created by allever on 17-6-4.
 */

public class ArticleDetailActivity extends AppCompatActivity {

    private static final String TAG = "ArticleDetailActivity";
    private String url;
    private String title;

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail_activity_layout);

        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");

        final Toolbar toolbar = (Toolbar)findViewById(R.id.id_article_detail_activity_toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        webView = (WebView)findViewById(R.id.id_article_detail_activity_web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.article_detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
/*            case android.R.id.home:
                //setResult(RESULT_OK);
                finish();
                break;*/
            case R.id.id_article_detail_menu_like:
                Toast.makeText(this,"like",Toast.LENGTH_LONG).show();
                break;
            case R.id.id_article_detail_menu_share:
                Toast.makeText(this,"share",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }


    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
