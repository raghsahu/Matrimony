package com.samyotech.matrimony.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.ChangePass;
import com.samyotech.matrimony.activity.ContactUs;
import com.samyotech.matrimony.activity.dashboard.RateUs;
import com.samyotech.matrimony.utils.ProjectUtils;


public class SettingsFrag extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout llInvite, llContact, llRate, llChangePass, terms, privatePolicy, llLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        setUiAction(view);
        return view;
    }

    public void setUiAction(View view) {
        llInvite = view.findViewById(R.id.llInvite);
        llContact = view.findViewById(R.id.llContact);
        llRate = view.findViewById(R.id.llRate);
        llChangePass = view.findViewById(R.id.llChangePass);
        terms = view.findViewById(R.id.terms);
        privatePolicy = view.findViewById(R.id.privatePolicy);
        llLogout = view.findViewById(R.id.llLogout);

        llInvite.setOnClickListener(this);
        llContact.setOnClickListener(this);
        llRate.setOnClickListener(this);
        llChangePass.setOnClickListener(this);
        terms.setOnClickListener(this);
        privatePolicy.setOnClickListener(this);
        llLogout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llInvite:
                share();
                break;
            case R.id.llContact:
                startActivity(new Intent(getActivity(), ContactUs.class));
                break;
            case R.id.llRate:
                startActivity(new Intent(getActivity(), RateUs.class));
                break;
            case R.id.llChangePass:
                startActivity(new Intent(getActivity(), ChangePass.class));
                break;
            case R.id.terms:
                ProjectUtils.showToast(getActivity(),"we are working");
                break;
            case R.id.privatePolicy:
                ProjectUtils.showToast(getActivity(),"we are working");
                break;
            case R.id.llLogout:
                ProjectUtils.showToast(getActivity(),"we are working");
                break;
        }
    }


    public void share() {
        try {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            //sharingIntent.putExtra(Intent.EXTRA_TEXT, "Get one free drink everyday at the best bars in Mumbai. Join BehindBars, use invite code " + code+ " to register.\niOS link: https://goo.gl/UGsW1C"+"\n" +"Android link: https://goo.gl/DZHVMu");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hi! I have had a great experience with RKSP and highly recommend that you register to find your perfeact life partner.. " + "\n" + " Use App link: https://goo.gl/DZHVMu");
            startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));
        } catch (Exception e) {
            e.printStackTrace();
            ProjectUtils.showToast(getActivity(), "Opps!! It seems that you have not installed any sharing app.");
        }


    }
}
