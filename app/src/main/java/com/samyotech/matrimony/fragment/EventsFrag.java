package com.samyotech.matrimony.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.dashboard.Dashboard;

public class EventsFrag extends Fragment {

    private View view;
    private Dashboard dashboard;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);
        dashboard.headerNameTV.setText(getResources().getString(R.string.nav_events));
        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dashboard = (Dashboard) activity;

    }


}
