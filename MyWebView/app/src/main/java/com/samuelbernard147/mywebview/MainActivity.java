package com.samuelbernard147.mywebview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView myWebView = findViewById(R.id.webView);
        myWebView.getSettings().setJavaScriptEnabled(true);


        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                Toast.makeText(MainActivity.this, "Web dicoding berhasil dimuat", Toast.LENGTH_SHORT).show();
                view.loadUrl("javascript:alert('Web dicoding berhasil dimuat')");
            }
        });

        myWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
//                return super.onJsAlert(view, url, message, result);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                result.confirm();
                return true;
            }
        });

        myWebView.loadUrl("https://www.dicoding.com");
    }
}
