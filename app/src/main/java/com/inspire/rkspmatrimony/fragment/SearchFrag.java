package com.inspire.rkspmatrimony.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.activity.dashboard.Dashboard;

public class SearchFrag extends Fragment {

    private View view;
    private Dashboard dashboard;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        dashboard.headerNameTV.setText(getResources().getString(R.string.nav_search));
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dashboard = (Dashboard) activity;

    }

}
