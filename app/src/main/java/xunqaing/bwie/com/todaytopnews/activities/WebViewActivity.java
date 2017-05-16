package xunqaing.bwie.com.todaytopnews.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import xunqaing.bwie.com.todaytopnews.R;

/**
 * Created by Huangminghuan on 2017/5/16.
 */

public class WebViewActivity extends Activity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Intent intent = getIntent();

        String url = intent.getStringExtra("url");

        webView = (WebView) findViewById(R.id.webView);

        webView.loadUrl(url);

    }
}
