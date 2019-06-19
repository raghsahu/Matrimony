package ics.hindu.matrimony.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import ics.hindu.matrimony.R;

public class ContactUs extends AppCompatActivity {

    private WebView about_us;
    private LinearLayout llBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setUiAction();
    }

    public void setUiAction() {

        llBack = (LinearLayout) findViewById(R.id.llBack);
        about_us = (WebView) findViewById(R.id.about_us);


        about_us.setWebViewClient(new MyBrowser());
        about_us.getSettings().setLoadsImagesAutomatically(true);
        about_us.getSettings().setJavaScriptEnabled(true);
        about_us.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        about_us.loadUrl("file:///android_asset/contact_us/contactus.html");

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

}
