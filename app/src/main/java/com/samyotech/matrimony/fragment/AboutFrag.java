package com.samyotech.matrimony.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.dashboard.Dashboard;

public class AboutFrag extends Fragment {

    private View view;
    private WebView about_us;
    private Dashboard dashboard;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);
        dashboard.headerNameTV.setText(getResources().getString(R.string.nav_about_us));
        setUiAction(view);
        return view;
    }

    public void setUiAction(View view) {

        about_us = (WebView) view.findViewById(R.id.about_us);


        about_us.setWebViewClient(new MyBrowser());
        about_us.getSettings().setLoadsImagesAutomatically(true);
        about_us.getSettings().setJavaScriptEnabled(true);
        about_us.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        about_us.loadUrl("file:///android_asset/about_us/aboutus.html");


    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dashboard = (Dashboard) activity;

    }

}
