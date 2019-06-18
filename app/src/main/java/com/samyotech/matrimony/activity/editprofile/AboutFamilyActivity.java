package com.samyotech.matrimony.activity.editprofile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.samyotech.matrimony.Models.CommanDTO;
import com.samyotech.matrimony.Models.LoginDTO;
import com.samyotech.matrimony.Models.UserDTO;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.SysApplication;
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

public class AboutFamilyActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = AboutFamilyActivity.class.getSimpleName();
    private Context mContext;
    private CustomEditText etPinfamily, etFatheroccupation, etMotheroccupation;
    private CustomEditText etBrothers, etSisters, etFamilyIncome, etFamilystatus;
    private CustomEditText etFamilytype, etFamilyvalue, etState, etDistrict, etCity;
    private CustomEditText etGrandFather, etMaternalGrandFather, etFatherName, etMotherName;
    private CustomEditText etAddress, etWhatsup, etMobile;
    private CustomButton btnSave;
    private LinearLayout llBack;
    private SpinnerDialog spinnerFatherOCC, spinnerMotherOCC, spinnerState, spinnerDistrict;
    private SpinnerDialog spinnerFamilystatus, spinnerFamilytype, spinnerFamilyvalue, spinnerIncome;
    private SpinnerDialog spinnerBrother, spinnerSister;
    private ArrayList<CommanDTO> stateList = new ArrayList<>();
    private ArrayList<CommanDTO> districtList = new ArrayList<>();
    private SysApplication sysApplication;
    private TestAdapter mDbHelper;
    private RelativeLayout RRsncbar;
    private HashMap<String, String> parms = new HashMap<>();
    private SharedPrefrence prefrence;
    private UserDTO userDTO;
    private LoginDTO loginDTO;
    String state_id = "";
    public SharedPreferences languageDetails;
    private String lang = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_family);
        mContext = AboutFamilyActivity.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        languageDetails = getSharedPreferences(Consts.LANGUAGE_PREF, MODE_PRIVATE);
        lang =  languageDetails.getString(Consts.SELECTED_LANGUAGE,"");
        userDTO = prefrence.getUserResponse(Consts.USER_DTO);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);

//        parms.put(Consts.USER_ID, loginDTO.getData().getId());
//        parms.put(Consts.TOKEN, loginDTO.getAccess_token());
        //************************************************************static******************
        parms.put(Consts.USER_ID, "1234");
        parms.put(Consts.TOKEN, "111111111111");
        //*******************************************************************

        mDbHelper = new TestAdapter(mContext);
        mDbHelper.createDatabase();
        mDbHelper.open();
        sysApplication = SysApplication.getInstance(mContext);
        init();
    }

    public void init() {

        RRsncbar = findViewById(R.id.RRsncbar);
        etMobile = findViewById(R.id.etMobile);
        etGrandFather = findViewById(R.id.etGrandFather);
        etMaternalGrandFather = findViewById(R.id.etMaternalGrandFather);
        etFatherName = findViewById(R.id.etFatherName);
        etMotherName = findViewById(R.id.etMotherName);
        etAddress = findViewById(R.id.etAddress);
        etWhatsup = findViewById(R.id.etWhatsup);
        etPinfamily = findViewById(R.id.etPinfamily);
        etFatheroccupation = findViewById(R.id.etFatheroccupation);
        etMotheroccupation = findViewById(R.id.etMotheroccupation);
        etBrothers = findViewById(R.id.etBrothers);
        etSisters = findViewById(R.id.etSisters);
        etFamilyIncome = findViewById(R.id.etFamilyIncome);
        etFamilystatus = findViewById(R.id.etFamilystatus);
        etFamilytype = findViewById(R.id.etFamilytype);
        etFamilyvalue = findViewById(R.id.etFamilyvalue);
        etState = findViewById(R.id.etState);
        etDistrict = findViewById(R.id.etDistrict);
        etCity = findViewById(R.id.etCity);
        btnSave = findViewById(R.id.btnSave);
        llBack = findViewById(R.id.llBack);


        llBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        etState.setOnClickListener(this);
        etDistrict.setOnClickListener(this);
        etFatheroccupation.setOnClickListener(this);
        etMotheroccupation.setOnClickListener(this);
        etFamilyIncome.setOnClickListener(this);
        etFamilystatus.setOnClickListener(this);
        etFamilytype.setOnClickListener(this);
        etFamilyvalue.setOnClickListener(this);
        etSisters.setOnClickListener(this);
        etBrothers.setOnClickListener(this);





        etPinfamily.addTextChangedListener(new MyTextWatcher(etPinfamily, Consts.FAMILY_PIN));
        etCity.addTextChangedListener(new MyTextWatcher(etCity, Consts.FAMILY_CITY));
        etGrandFather.addTextChangedListener(new MyTextWatcher(etGrandFather, Consts.GRAND_FATHER_NAME));
        etMaternalGrandFather.addTextChangedListener(new MyTextWatcher(etMaternalGrandFather, Consts.MATERNAL_GRAND_FATHER_NAME_ADDRESS));
        etFatherName.addTextChangedListener(new MyTextWatcher(etFatherName, Consts.FATHER_NAME));
        etMotherName.addTextChangedListener(new MyTextWatcher(etMotherName, Consts.MOTHER_NAME));
        etAddress.addTextChangedListener(new MyTextWatcher(etAddress, Consts.PERMANENT_ADDRESS));
        etWhatsup.addTextChangedListener(new MyTextWatcher(etWhatsup, Consts.WHATSAPP_NO));
        etMobile.addTextChangedListener(new MyTextWatcher(etMobile, Consts.MOBILE2));

        showData();

    }

    public void showDistrict() {
        for (int j = 0; j < districtList.size(); j++) {
            if (districtList.get(j).getName().equalsIgnoreCase(userDTO.getFamily_district())) {
                districtList.get(j).setSelected(true);


            }
        }

        spinnerDistrict = new SpinnerDialog((Activity) mContext, districtList, getResources().getString(R.string.select_district), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerDistrict.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etDistrict.setText(item);
                parms.put(Consts.FAMILY_DISTRICT, item);
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
            case R.id.btnSave:
                if (!validation(etGrandFather, getResources().getString(R.string.val_grandf))) {
                    return;
                } else if (!validation(etMaternalGrandFather, getResources().getString(R.string.val_mgrandf))) {
                    return;
                } else if (!validation(etFatherName, getResources().getString(R.string.val_father_name))) {
                    return;
                } else if (!validation(etMotherName, getResources().getString(R.string.val_mother_name))) {
                    return;
                } else if (!validation(etAddress, getResources().getString(R.string.val_address))) {
                    return;
                } else if (!ProjectUtils.isPhoneNumberValid(etWhatsup.getText().toString().trim())) {
                    showSickbar(getString(R.string.val_mobile_whats));
                    return;
                } else if (!ProjectUtils.isPhoneNumberValid(etMobile.getText().toString().trim())) {
                    showSickbar(getString(R.string.val_mobile));
                    return;
                } else if (!validation(etPinfamily, getResources().getString(R.string.val_about_fam))) {
                    return;
                } else if (!validation(etFatheroccupation, getResources().getString(R.string.val_father_occupation))) {
                    return;
                } else if (!validation(etMotheroccupation, getResources().getString(R.string.val_mother_occupation))) {
                    return;
                } else if (!validation(etBrothers, getResources().getString(R.string.select_brother))) {
                    return;
                } else if (!validation(etSisters, getResources().getString(R.string.select_sister))) {
                    return;
                } else if (!validation(etFamilyIncome, getResources().getString(R.string.val_select_family_income))) {
                    return;
                } else if (!validation(etFamilystatus, getResources().getString(R.string.val_family_status))) {
                    return;
                } else if (!validation(etFamilytype, getResources().getString(R.string.val_family_type))) {
                    return;
                } else if (!validation(etFamilyvalue, getResources().getString(R.string.val_family_value))) {
                    return;
                } else if (!validation(etState, getResources().getString(R.string.val_state))) {
                    return;
                } else if (!validation(etDistrict, getResources().getString(R.string.val_district))) {
                    return;
                } else if (!validation(etCity, getResources().getString(R.string.val_city))) {
                    return;
                } else {
                    if (NetworkManager.isConnectToInternet(mContext)) {
                        request();

                    } else {
                        ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
                    }


                }

                break;
            case R.id.etFatheroccupation:
                spinnerFatherOCC.showSpinerDialog();
                break;
            case R.id.etMotheroccupation:
                spinnerMotherOCC.showSpinerDialog();
                break;
            case R.id.etFamilyIncome:
                spinnerIncome.showSpinerDialog();
                break;
            case R.id.etFamilystatus:
                spinnerFamilystatus.showSpinerDialog();
                break;
            case R.id.etFamilytype:
                spinnerFamilytype.showSpinerDialog();
                break;
            case R.id.etFamilyvalue:
                spinnerFamilyvalue.showSpinerDialog();
                break;
            case R.id.etSisters:
                spinnerSister.showSpinerDialog();
                break;
            case R.id.etBrothers:
                spinnerBrother.showSpinerDialog();
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
        }
    }

    public void showSickbar(String msg) {
        Snackbar snackbar = Snackbar.make(RRsncbar, msg, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
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


    public void showData() {
        etMobile.setText(userDTO.getMobile2());
        etGrandFather.setText(userDTO.getGrand_father_name());
        etMaternalGrandFather.setText(userDTO.getMaternal_grand_father_name_address());
        etFatherName.setText(userDTO.getFather_name());
        etMotherName.setText(userDTO.getMother_name());
        etAddress.setText(userDTO.getPermanent_address());
        etWhatsup.setText(userDTO.getWhatsapp_no());
        etPinfamily.setText(userDTO.getFamily_pin());
        etFatheroccupation.setText(userDTO.getFather_occupation());
        etMotheroccupation.setText(userDTO.getMother_occupation());
        if (userDTO.getBrother().equalsIgnoreCase("")){
            etBrothers.setText("");

        }else {
            etBrothers.setText(userDTO.getBrother() + " brothers");

        }
        if (userDTO.getSister().equalsIgnoreCase("")){
            etSisters.setText("");

        }else {
            etSisters.setText(userDTO.getSister() + " sisters");

        }
        etFamilyIncome.setText(userDTO.getFamily_income());
        etFamilystatus.setText(userDTO.getFamily_status());
        etFamilytype.setText(userDTO.getFamily_type());
        etFamilyvalue.setText(userDTO.getFamily_value());
        etState.setText(userDTO.getFamily_state());
        etDistrict.setText(userDTO.getFamily_district());
        etCity.setText(userDTO.getFamily_city());


        for (int j = 0; j < sysApplication.getMotherOccupationList().size(); j++) {
            if (sysApplication.getMotherOccupationList().get(j).getName().equalsIgnoreCase(userDTO.getMother_occupation())) {
                sysApplication.getMotherOccupationList().get(j).setSelected(true);
            }
        }
        spinnerMotherOCC = new SpinnerDialog((Activity) mContext, sysApplication.getMotherOccupationList(), getResources().getString(R.string.mother_occupation), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerMotherOCC.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etMotheroccupation.setText(item);
                parms.put(Consts.MOTHER_OCCUPATION, item);
            }
        });


        for (int j = 0; j < sysApplication.getFatherOccupationList().size(); j++) {
            if (sysApplication.getFatherOccupationList().get(j).getName().equalsIgnoreCase(userDTO.getFather_occupation())) {
                sysApplication.getFatherOccupationList().get(j).setSelected(true);
            }
        }
        spinnerFatherOCC = new SpinnerDialog((Activity) mContext, sysApplication.getFatherOccupationList(), getResources().getString(R.string.father_occupation), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerFatherOCC.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etFatheroccupation.setText(item);
                parms.put(Consts.FATHER_OCCUPATION, item);
            }
        });


        for (int j = 0; j < sysApplication.getIncomeList().size(); j++) {
            if (sysApplication.getIncomeList().get(j).getName().equalsIgnoreCase(userDTO.getFamily_income())) {
                sysApplication.getIncomeList().get(j).setSelected(true);
            }
        }
        spinnerIncome = new SpinnerDialog((Activity) mContext, sysApplication.getIncomeList(), getResources().getString(R.string.select_income), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerIncome.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etFamilyIncome.setText(item);
                parms.put(Consts.FAMILY_INCOME, item);
            }
        });


        for (int j = 0; j < sysApplication.getFamilyStatus().size(); j++) {
            if (sysApplication.getFamilyStatus().get(j).getName().equalsIgnoreCase(userDTO.getFamily_status())) {
                sysApplication.getFamilyStatus().get(j).setSelected(true);
            }
        }
        spinnerFamilystatus = new SpinnerDialog((Activity) mContext, sysApplication.getFamilyStatus(), getResources().getString(R.string.select_family_status), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerFamilystatus.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etFamilystatus.setText(item);
                parms.put(Consts.FAMILY_STATUS, item);
            }
        });


        for (int j = 0; j < sysApplication.getFamilyType().size(); j++) {
            if (sysApplication.getFamilyType().get(j).getName().equalsIgnoreCase(userDTO.getFamily_type())) {
                sysApplication.getFamilyType().get(j).setSelected(true);
            }
        }
        spinnerFamilytype = new SpinnerDialog((Activity) mContext, sysApplication.getFamilyType(), getResources().getString(R.string.select_family_type), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerFamilytype.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etFamilytype.setText(item);
                parms.put(Consts.FAMILY_TYPE, item);
            }
        });


        for (int j = 0; j < sysApplication.getFamilyValues().size(); j++) {
            if (sysApplication.getFamilyValues().get(j).getName().equalsIgnoreCase(userDTO.getFamily_value())) {
                sysApplication.getFamilyValues().get(j).setSelected(true);
            }
        }
        spinnerFamilyvalue = new SpinnerDialog((Activity) mContext, sysApplication.getFamilyValues(), getResources().getString(R.string.select_family_value), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerFamilyvalue.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etFamilyvalue.setText(item);
                parms.put(Consts.FAMILY_VALUE, item);
            }
        });


        for (int j = 0; j < sysApplication.getBrother().size(); j++) {
            if (sysApplication.getBrother().get(j).getName().equalsIgnoreCase(userDTO.getBrother())) {
                sysApplication.getBrother().get(j).setSelected(true);
            }
        }
        spinnerBrother = new SpinnerDialog((Activity) mContext, sysApplication.getBrother(), getResources().getString(R.string.brothers), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation// With 	Animation
        spinnerBrother.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etBrothers.setText(item);
                parms.put(Consts.BROTHER, item);
            }
        });


        for (int j = 0; j < sysApplication.getSister().size(); j++) {
            if (sysApplication.getSister().get(j).getName().equalsIgnoreCase(userDTO.getSister())) {
                sysApplication.getSister().get(j).setSelected(true);
            }
        }
        spinnerSister = new SpinnerDialog((Activity) mContext, sysApplication.getSister(), getResources().getString(R.string.sisters), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerSister.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etSisters.setText(item);
                parms.put(Consts.SISTER, item);
            }
        });



        stateList = new ArrayList<>();
        stateList = mDbHelper.getAllState(lang);

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

                parms.put(Consts.FAMILY_STATE, item);
                districtList = new ArrayList<>();
                districtList = mDbHelper.getAllDistrict(id,lang);
                showDistrict();
            }
        });

        districtList = new ArrayList<>();
        districtList = mDbHelper.getAllDistrict(state_id,lang);
        showDistrict();

    }

}
