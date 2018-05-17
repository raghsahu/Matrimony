package com.inspire.rkspmatrimony.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.activity.ChangePass;
import com.inspire.rkspmatrimony.activity.ContactUs;
import com.inspire.rkspmatrimony.activity.WebViewActivity;
import com.inspire.rkspmatrimony.activity.dashboard.RateUs;
import com.inspire.rkspmatrimony.activity.loginsignup.Login;
import com.inspire.rkspmatrimony.interfaces.Consts;
import com.inspire.rkspmatrimony.sharedprefrence.SharedPrefrence;
import com.inspire.rkspmatrimony.utils.ProjectUtils;


public class SettingsFrag extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout llInvite, llContact, llRate, llChangePass, terms, privatePolicy, llLogout;
    private SharedPrefrence prefrence;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        prefrence = SharedPrefrence.getInstance(getActivity());
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
                openWebViewActivity(0);
                break;
            case R.id.privatePolicy:
                openWebViewActivity(1);
                break;
            case R.id.llLogout:
                confirmLogout();
                break;
        }
    }

    public void openWebViewActivity(int value) {
        Intent termsIntent = new Intent(getActivity(), WebViewActivity.class);
        switch (value) {
            case 0:
                termsIntent.putExtra(Consts.WEB_VIEW_FLAG, 1);
                startActivity(termsIntent);
                break;
            case 1:
                termsIntent.putExtra(Consts.WEB_VIEW_FLAG, 2);
                startActivity(termsIntent);
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

    public void confirmLogout() {
        try {
            new AlertDialog.Builder(getActivity())
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle(getResources().getString(R.string.app_name))
                    .setMessage(getResources().getString(R.string.logout_msg))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            prefrence.clearAllPreference();
                            Intent intent = new Intent(getActivity(), Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no_dialog), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
