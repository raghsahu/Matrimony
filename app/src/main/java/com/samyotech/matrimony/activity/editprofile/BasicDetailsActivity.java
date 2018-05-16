package com.samyotech.matrimony.activity.editprofile;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.samyotech.matrimony.Models.CommanDTO;
import com.samyotech.matrimony.Models.LoginDTO;
import com.samyotech.matrimony.Models.UserDTO;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.SysApplication;
import com.samyotech.matrimony.activity.loginsignup.Registration;
import com.samyotech.matrimony.database.TestAdapter;
import com.samyotech.matrimony.https.HttpsRequest;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.interfaces.Helper;
import com.samyotech.matrimony.interfaces.OnSpinerItemClick;
import com.samyotech.matrimony.network.NetworkManager;
import com.samyotech.matrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.matrimony.utils.ProjectUtils;
import com.samyotech.matrimony.utils.SpinnerDialog;
import com.samyotech.matrimony.view.CustomButton;
import com.samyotech.matrimony.view.CustomEditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class BasicDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = BasicDetailsActivity.class.getSimpleName();
    private LinearLayout llBack;
    private CustomEditText etName, etgender, etHeight, etGotra, etGotraNanihal, etState, etDistrict, etCity, etAnnualIncome;
    private CustomButton btnSave;
    private Context mContext;
    private SpinnerDialog spinnerHeight, spinnerState, spinnerDistrict, spinnerIncome;
    private ArrayList<CommanDTO> stateList = new ArrayList<>();
    private ArrayList<CommanDTO> districtList = new ArrayList<>();
    private SysApplication sysApplication;
    private TestAdapter mDbHelper;
    private HashMap<String, String> parms = new HashMap<>();
    private RelativeLayout RRsncbar;
    private SharedPrefrence prefrence;
    private UserDTO userDTO;
    private LoginDTO loginDTO;
    String state_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_details);
        mContext = BasicDetailsActivity.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        userDTO = prefrence.getUserResponse(Consts.USER_DTO);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        parms.put(Consts.USER_ID, loginDTO.getUser_id());
        parms.put(Consts.TOKEN, loginDTO.getAccess_token());
        mDbHelper = new TestAdapter(mContext);
        mDbHelper.createDatabase();
        mDbHelper.open();
        sysApplication = SysApplication.getInstance();
        setUIAction();
    }

    public void setUIAction() {
        RRsncbar = findViewById(R.id.RRsncbar);
        llBack = findViewById(R.id.llBack);
        etName = findViewById(R.id.etName);
        etgender = findViewById(R.id.etgender);
        etHeight = findViewById(R.id.etHeight);
        etGotra = findViewById(R.id.etGotra);
        etGotraNanihal = findViewById(R.id.etGotraNanihal);
        etState = findViewById(R.id.etState);
        etDistrict = findViewById(R.id.etDistrict);
        etCity = findViewById(R.id.etCity);
        etAnnualIncome = findViewById(R.id.etAnnualIncome);
        btnSave = findViewById(R.id.btnSave);

        llBack.setOnClickListener(this);
        etHeight.setOnClickListener(this);
        etState.setOnClickListener(this);
        etDistrict.setOnClickListener(this);
        etAnnualIncome.setOnClickListener(this);
        btnSave.setOnClickListener(this);



        etName.addTextChangedListener(new MyTextWatcher(etName, Consts.NAME));
        showData();

    }


    public void showDistrict() {
        for (int j = 0; j < districtList.size(); j++) {
            if (districtList.get(j).getName().equalsIgnoreCase(userDTO.getDistrict())) {
                districtList.get(j).setSelected(true);
            }
        }
        spinnerDistrict = new SpinnerDialog((Activity) mContext, districtList,
                getResources().getString(R.string.select_district),
                R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
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
            case R.id.llBack:
                finish();
                overridePendingTransition(R.anim.stay, R.anim.slide_down);
                break;
            case R.id.etHeight:
                spinnerHeight.showSpinerDialog();
                break;
            case R.id.etState:
                if (stateList.size() > 0)
                    spinnerState.showSpinerDialog();
                else
                    ProjectUtils.showToast(mContext, "Please try after some time");
                break;
            case R.id.etDistrict:
                if (districtList.size() > 0)
                    spinnerDistrict.showSpinerDialog();
                else
                    ProjectUtils.showToast(mContext, "Please try after some time");
                break;

            case R.id.etAnnualIncome:
                spinnerIncome.showSpinerDialog();
                break;
            case R.id.btnSave:
                if (!validation(etName, getResources().getString(R.string.val_full_name))) {
                    return;
                } else if (!validation(etHeight, getResources().getString(R.string.val_height))) {
                    return;
                }  else if (!validation(etState, getResources().getString(R.string.val_state))) {
                    return;
                } else if (!validation(etDistrict, getResources().getString(R.string.val_district))) {
                    return;
                } else if (!validation(etCity, getResources().getString(R.string.val_city))) {
                    return;
                } else if (!validation(etAnnualIncome, getResources().getString(R.string.val_income))) {
                    return;
                } else {
                    if (NetworkManager.isConnectToInternet(mContext)) {
                        request();

                    } else {
                        ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
                    }
                }
                break;
        }
    }

    public boolean validation(EditText editText, String msg) {
        if (!ProjectUtils.isEditTextFilled(editText)) {
            Snackbar snackbar = Snackbar.make(RRsncbar, msg, Snackbar.LENGTH_LONG);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            snackbar.show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }

    public class MyTextWatcher implements TextWatcher {

        private EditText mEditText;
        private String key;

        public MyTextWatcher(EditText editText, String key) {
            this.mEditText = editText;
            this.key = key;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            parms.put(key, ProjectUtils.getEditTextValue(mEditText));
        }
    }
    public void request() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.UPDATE_PROFILE_API, parms, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    finish();
                    overridePendingTransition(R.anim.stay, R.anim.slide_down);
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }
            }
        });
    }

    public void showData(){
        etName.setText(userDTO.getName());
        if (userDTO.getGender().equalsIgnoreCase("M")){
            etgender.setText("Male");

        }else {
            etgender.setText("Female");
        }
        etHeight.setText(userDTO.getHeight());
        etGotra.setText(userDTO.getGotra());
        etGotraNanihal.setText(userDTO.getAbout_me());
        etState.setText(userDTO.getState());
        etDistrict.setText(userDTO.getDistrict());
        etCity.setText(userDTO.getCity());
        etAnnualIncome.setText(userDTO.getIncome());



        for (int j = 0; j < sysApplication.getHeightList().size(); j++) {
            if (sysApplication.getHeightList().get(j).getName().equalsIgnoreCase(userDTO.getHeight())) {
                sysApplication.getHeightList().get(j).setSelected(true);
            }
        }
        spinnerHeight = new SpinnerDialog((Activity) mContext, sysApplication.getHeightList(),
                getResources().getString(R.string.select_height), R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.close));// With 	Animation
        spinnerHeight.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etHeight.setText(item);
                parms.put(Consts.HEIGHT, id);
            }
        });


        stateList = new ArrayList<>();
        stateList = mDbHelper.getAllState();

        for (int j = 0; j < stateList.size(); j++) {
            if (stateList.get(j).getName().equalsIgnoreCase(userDTO.getFamily_state())) {
                stateList.get(j).setSelected(true);
                state_id = stateList.get(j).getId();

            }
        }

        spinnerState = new SpinnerDialog((Activity) mContext, stateList, getResources().getString(R.string.select_state), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerState.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etState.setText(item);

                parms.put(Consts.STATE, id);
                districtList = new ArrayList<>();
                districtList = mDbHelper.getAllDistrict(id);
                showDistrict();
            }
        });

        districtList = new ArrayList<>();
        districtList = mDbHelper.getAllDistrict(state_id);
        showDistrict();


        for (int j = 0; j < sysApplication.getIncomeList().size(); j++) {
            if (sysApplication.getIncomeList().get(j).getName().equalsIgnoreCase(userDTO.getIncome())) {
                sysApplication.getIncomeList().get(j).setSelected(true);
            }
        }
        spinnerIncome = new SpinnerDialog((Activity) mContext, sysApplication.getIncomeList(),
                getResources().getString(R.string.select_income), R.style.DialogAnimations_SmileWindow,
                getResources().getString(R.string.close));// With 	Animation
        spinnerIncome.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etAnnualIncome.setText(item);
                parms.put(Consts.INCOME, id);
            }
        });
    }

}