package com.inspire.rkspmatrimony.fragment.registration;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.SysApplication;
import com.inspire.rkspmatrimony.activity.loginsignup.Registration;
import com.inspire.rkspmatrimony.interfaces.OnSpinerItemClick;
import com.inspire.rkspmatrimony.utils.SpinnerDialog;
import com.inspire.rkspmatrimony.view.CustomEditText;

public class ImportantDetails extends Fragment implements View.OnClickListener {
    private View view;
    public CustomEditText etEducation, etWorkArea, etIncome, etBlood, etAadhar, etoccupations, etorganization;
    SpinnerDialog spinnerIncome, spinnerBlood, spinneroccupation;
    private Registration registration;
    public String income="", blood="", occupation="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_important_details, container, false);
        setUiAction(view);
        return view;
    }

    public void setUiAction(View v) {
        etEducation = v.findViewById(R.id.etEducation);
        etWorkArea = v.findViewById(R.id.etWorkArea);
        etIncome = v.findViewById(R.id.etIncome);
        etBlood = v.findViewById(R.id.etBlood);
        etAadhar = v.findViewById(R.id.etAadhar);
        etoccupations = v.findViewById(R.id.etoccupations);
        etorganization = v.findViewById(R.id.etorganization);

        etoccupations.setOnClickListener(this);
        etIncome.setOnClickListener(this);
        etBlood.setOnClickListener(this);

        spinnerIncome = new SpinnerDialog(getActivity(), registration.sysApplication.getIncomeList(), getResources().getString(R.string.select_income), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation


        spinnerIncome.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etIncome.setText(item);
                income = id;
            }
        });
        spinnerBlood = new SpinnerDialog(getActivity(), registration.sysApplication.getBloodList(), getResources().getString(R.string.select_blood), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation


        spinnerBlood.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etBlood.setText(item);
                blood = id;
            }
        });

        spinneroccupation = new SpinnerDialog(getActivity(), registration.sysApplication.getOccupationList(), getResources().getString(R.string.select_occupation), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation

        spinneroccupation.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etoccupations.setText(item);
                occupation = id;
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etIncome:
                spinnerIncome.showSpinerDialog();
                break;
            case R.id.etBlood:
                spinnerBlood.showSpinerDialog();
                break;
            case R.id.etoccupations:
                spinneroccupation.showSpinerDialog();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registration = (Registration) context;

    }
}
