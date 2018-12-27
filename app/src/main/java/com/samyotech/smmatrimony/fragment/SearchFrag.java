package com.samyotech.smmatrimony.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.samyotech.smmatrimony.Models.CommanDTO;
import com.samyotech.smmatrimony.Models.LoginDTO;
import com.samyotech.smmatrimony.R;
import com.samyotech.smmatrimony.SysApplication;
import com.samyotech.smmatrimony.activity.dashboard.Dashboard;
import com.samyotech.smmatrimony.activity.search.Search;
import com.samyotech.smmatrimony.activity.search.SearchResultMain;
import com.samyotech.smmatrimony.database.TestAdapter;
import com.samyotech.smmatrimony.interfaces.Consts;
import com.samyotech.smmatrimony.interfaces.OnSpinerItemClick;
import com.samyotech.smmatrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.smmatrimony.utils.SpinnerDialog;
import com.samyotech.smmatrimony.view.CustomButton;
import com.samyotech.smmatrimony.view.CustomEditText;
import com.samyotech.smmatrimony.view.CustomTextView;
import com.samyotech.smmatrimony.view.InputFieldView;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchFrag extends Fragment implements View.OnClickListener  {

    private View view;
    private Dashboard dashboard;
    private String TAG = SearchFrag.class.getSimpleName();
    private Context mContext;
    private LinearLayout llBride, llGroom, llBack;
    private CustomTextView tvBride, tvGroom;
    private InputFieldView inf_district;
    private CustomEditText etMaritial, etManglik, etState, etDistrict, etCaste;
    private ArrayList<CommanDTO> stateList = new ArrayList<>();
    private ArrayList<CommanDTO> districtList = new ArrayList<>();
    private SpinnerDialog spinnerMaritial, spinnerManglik, spinnerState, spinnerDistrict, spinnerCaste;
    public SysApplication sysApplication;
    HashMap<String, String> parms = new HashMap<>();
    TestAdapter mDbHelper;
    private CustomButton btnSearch;
    private ArrayList<CommanDTO> casteList = new ArrayList<>();
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        dashboard.headerNameTV.setText(getResources().getString(R.string.nav_search));
        sysApplication = SysApplication.getInstance(getActivity());
        mDbHelper = new TestAdapter(getActivity());

        prefrence = SharedPrefrence.getInstance(getActivity());
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        parms.put(Consts.USER_ID, loginDTO.getData().getId());
        parms.put(Consts.TOKEN, loginDTO.getAccess_token());

        mDbHelper.createDatabase();
        mDbHelper.open();
        parms.put(Consts.GENDER, "F");
        setUiAction(view);
        return view;

    }

    public void setUiAction(View v) {
        llBride = v.findViewById(R.id.llBride);
        llGroom = v.findViewById(R.id.llGroom);
        tvBride = v.findViewById(R.id.tvBride);
        tvGroom = v.findViewById(R.id.tvGroom);

        llBride.setOnClickListener(this);
        llGroom.setOnClickListener(this);

        etMaritial = v.findViewById(R.id.etMaritial);
        etManglik = v.findViewById(R.id.etManglik);
        etState = v.findViewById(R.id.etState);
        etDistrict = v.findViewById(R.id.etDistrict);



        etMaritial.setOnClickListener(this);
        etManglik.setOnClickListener(this);
        etState.setOnClickListener(this);
        etDistrict.setOnClickListener(this);


        btnSearch = v.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        inf_district = v.findViewById(R.id.inf_district);

        spinnerMaritial = new SpinnerDialog(getActivity(), sysApplication.getMaritalList(), getResources().getString(R.string.select_maritial_status), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation


        spinnerMaritial.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etMaritial.setText(item);
                parms.put(Consts.MARITAL_STATUS, id);
            }
        });
        spinnerManglik = new SpinnerDialog(getActivity(), sysApplication.getManglikList(), getResources().getString(R.string.select_manglik), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation


        spinnerManglik.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etManglik.setText(item);
                parms.put(Consts.MANGLIK, id);

            }
        });
        stateList = new ArrayList<>();
        stateList = mDbHelper.getAllState("en");
        spinnerState = new SpinnerDialog(getActivity(), stateList, getResources().getString(R.string.select_state), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerState.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etState.setText(item);
                parms.put(Consts.STATE, id);
                inf_district.setVisibility(View.VISIBLE);

                districtList = new ArrayList<>();
                districtList = mDbHelper.getAllDistrict(id, "en");
                showDistrict();
            }
        });

    }

    public void showDistrict() {
        spinnerDistrict = new SpinnerDialog(getActivity(), districtList, getResources().getString(R.string.select_district), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerDistrict.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etDistrict.setText(item);
                parms.put(Consts.DISTRICT, id);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etMaritial:
                spinnerMaritial.showSpinerDialog();
                break;
            case R.id.etManglik:
                spinnerManglik.showSpinerDialog();
                break;
            case R.id.etState:
                spinnerState.showSpinerDialog();
                break;
            case R.id.etDistrict:
                spinnerDistrict.showSpinerDialog();
                break;
            case R.id.llBride:
                setSelected(true, false);
                parms.put(Consts.GENDER, "F");
                break;
            case R.id.llGroom:
                setSelected(false, true);
                parms.put(Consts.GENDER, "M");
                break;
            case R.id.btnSearch:
                Intent in = new Intent(getActivity(), SearchResultMain.class);
                in.putExtra(Consts.SEARCH_PARAM, parms);
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                break;

        }
    }

    public void setSelected(boolean firstBTN, boolean secondBTN) {
        if (firstBTN) {
            llBride.setBackground(getResources().getDrawable(R.drawable.gender_selecte));
            llGroom.setBackground(getResources().getDrawable(R.drawable.gender_unselecte));
            tvBride.setTextColor(getResources().getColor(R.color.white));
            tvGroom.setTextColor(getResources().getColor(R.color.red_800));
        }
        if (secondBTN) {
            llBride.setBackground(getResources().getDrawable(R.drawable.gender_unselecte));
            llGroom.setBackground(getResources().getDrawable(R.drawable.gender_selecte));

            tvBride.setTextColor(getResources().getColor(R.color.red_800));
            tvGroom.setTextColor(getResources().getColor(R.color.white));
        }

        llBride.setSelected(firstBTN);
        llGroom.setSelected(secondBTN);
    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dashboard = (Dashboard) activity;

    }

}
