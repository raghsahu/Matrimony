package ics.hindu.matrimony.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ics.hindu.matrimony.R;
import ics.hindu.matrimony.activity.dashboard.Dashboard;

public class HomeFrag extends Fragment implements View.OnClickListener {

    private View view;
    private RelativeLayout tab_match, tab_join;
    private TextView match_selectedTV, join_selectedTV;
    private FrameLayout container;
    private FragmentManager fragmentManager;
    private MatchesFrag matchesFrag = new MatchesFrag();
    private JustJoinFrag justJoinFrag = new JustJoinFrag();
    private Dashboard dashboard;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        dashboard.headerNameTV.setText(getResources().getString(R.string.nav_home));

        setUiAction(view);
        fragmentManager = getChildFragmentManager();

        fragmentManager.beginTransaction().add(R.id.container, matchesFrag).commit();
        return view;
    }

    public void setUiAction(View v) {
        tab_match = v.findViewById(R.id.tab_match);
        tab_join = v.findViewById(R.id.tab_join);
        match_selectedTV = v.findViewById(R.id.match_selectedTV);
        join_selectedTV = v.findViewById(R.id.join_selectedTV);
        container = (FrameLayout) view.findViewById(R.id.container);
        tab_match.setOnClickListener(this);
        tab_join.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_match:
                setSelected(true, false);
                fragmentManager.beginTransaction().replace(R.id.container, matchesFrag).commit();
                break;
            case R.id.tab_join:
                setSelected(false, true);
                fragmentManager.beginTransaction().replace(R.id.container, justJoinFrag).commit();
                break;
        }
    }

    public void setSelected(boolean firstBTN, boolean secondBTN) {
        if (firstBTN) {
            match_selectedTV.setVisibility(View.VISIBLE);
            join_selectedTV.setVisibility(View.GONE);

        }
        if (secondBTN) {
            match_selectedTV.setVisibility(View.GONE);
            join_selectedTV.setVisibility(View.VISIBLE);

        }

        tab_match.setSelected(firstBTN);
        tab_join.setSelected(secondBTN);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dashboard = (Dashboard) activity;

    }


}
