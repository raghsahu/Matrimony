package com.inspire.rkspmatrimony.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.interfaces.Consts;
import com.inspire.rkspmatrimony.view.CustomTextViewBold;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llBack;
    private CustomTextViewBold tvHeader;
    private WebView webView;
    private Context mContext;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mContext = WebViewActivity.this;
        if (getIntent().hasExtra(Consts.WEB_VIEW_FLAG)) {
            flag = getIntent().getIntExtra(Consts.WEB_VIEW_FLAG, 0);
        }
        setUiAction();
    }

    public void setUiAction() {
        webView = (WebView) findViewById(R.id.webView);
        llBack = findViewById(R.id.llBack);
        tvHeader = findViewById(R.id.tvHeader);

        llBack.setOnClickListener(this);

        if (flag == 1) {
            tvHeader.setText(getResources().getString(R.string.terms_header));
            webView.setWebViewClient(new MyBrowser());
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("http://52.201.90.251:8082/fama/view/TermsAndConditions.html");
        } else if (flag == 2) {
            tvHeader.setText(getResources().getString(R.string.privacy_header));
            webView.setWebViewClient(new MyBrowser());
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("http://52.201.90.251:8082/fama/view/PrivacyPolicy.html");

        }

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llBack:
                finish();
                break;
        }
    }
}
