package com.samyotech.smmatrimony.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.samyotech.smmatrimony.R;
import com.samyotech.smmatrimony.activity.ChangePass;
import com.samyotech.smmatrimony.activity.ContactUs;
import com.samyotech.smmatrimony.activity.WebViewActivity;
import com.samyotech.smmatrimony.activity.dashboard.RateUs;
import com.samyotech.smmatrimony.activity.loginsignup.Login;
import com.samyotech.smmatrimony.activity.subscription.SubscriptionHistory;
import com.samyotech.smmatrimony.interfaces.Consts;
import com.samyotech.smmatrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.smmatrimony.utils.ProjectUtils;


public class SettingsFrag extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout llInvite, llContact, llRate, llChangePass, terms, privatePolicy, llLogout, llSubscription;
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
        llSubscription = view.findViewById(R.id.llSubscription);

        llInvite.setOnClickListener(this);
        llContact.setOnClickListener(this);
        llRate.setOnClickListener(this);
        llChangePass.setOnClickListener(this);
        terms.setOnClickListener(this);
        privatePolicy.setOnClickListener(this);
        llLogout.setOnClickListener(this);
        llSubscription.setOnClickListener(this);
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
            case R.id.llSubscription:
                startActivity(new Intent(getActivity(), SubscriptionHistory.class));
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
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "Hi! I have had a great experience with Matrimony and highly recommend that you register to find your perfeact life partner.. " + "\n" + " Use App link: http://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
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
