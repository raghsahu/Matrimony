package ics.hindu.matrimony.activity.editprofile;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import ics.hindu.matrimony.Models.LoginDTO;
import ics.hindu.matrimony.Models.UserDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.other.SysApplication;
import ics.hindu.matrimony.https.HttpsRequest;
import ics.hindu.matrimony.interfaces.Consts;
import ics.hindu.matrimony.interfaces.Helper;
import ics.hindu.matrimony.interfaces.OnSpinerItemClick;
import ics.hindu.matrimony.network.NetworkManager;
import ics.hindu.matrimony.sharedprefrence.SharedPrefrence;
import ics.hindu.matrimony.utils.ProjectUtils;
import ics.hindu.matrimony.utils.SpinnerDialog;
import ics.hindu.matrimony.view.CustomButton;
import ics.hindu.matrimony.view.CustomEditText;

import org.json.JSONObject;

import java.util.HashMap;

public class AboutMeActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = AboutMeActivity.class.getSimpleName();
    private Context mContext;
    private CustomEditText etAboutme, etWeight, etBodyType, etComplexion, etChallenged, etBloodgroup;
    private CustomButton btnSave;
    private LinearLayout llBack;
    private SysApplication sysApplication;
    private SpinnerDialog spinnerComplexion, spinnerChallenged, spinnerBodyType, spinnerBloodGroup;
    private RelativeLayout RRsncbar;
    private HashMap<String, String> parms = new HashMap<>();
    private SharedPrefrence prefrence;
    private UserDTO userDTO;
    private LoginDTO loginDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        mContext = AboutMeActivity.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        userDTO = prefrence.getUserResponse(Consts.USER_DTO);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
//        parms.put(Consts.USER_ID, loginDTO.getData().getId());
//        parms.put(Consts.TOKEN, loginDTO.getAccess_token());
        //************************************************************static******************
        parms.put(Consts.USER_ID, "1234");
        parms.put(Consts.TOKEN, "111111111111");
        //*******************************************************************
        sysApplication = SysApplication.getInstance(mContext);
        setUIAction();
    }

    public void setUIAction() {
        RRsncbar = findViewById(R.id.RRsncbar);
        etAboutme = findViewById(R.id.etAboutme);
        etWeight = findViewById(R.id.etWeight);
        etBodyType = findViewById(R.id.etBodyType);
        etComplexion = findViewById(R.id.etComplexion);
        etChallenged = findViewById(R.id.etChallenged);
        etBloodgroup = findViewById(R.id.etBloodgroup);
        btnSave = findViewById(R.id.btnSave);
        llBack = findViewById(R.id.llBack);

        llBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        etBodyType.setOnClickListener(this);
        etComplexion.setOnClickListener(this);
        etChallenged.setOnClickListener(this);
        etBloodgroup.setOnClickListener(this);



        etAboutme.addTextChangedListener(new MyTextWatcher(etAboutme, Consts.ABOUT_ME));
        etWeight.addTextChangedListener(new MyTextWatcher(etWeight, Consts.WEIGHT));
        showData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                if (!validation(etAboutme, getResources().getString(R.string.val_about_me))) {
                    return;
                } else if (!validation(etWeight, getResources().getString(R.string.val_weight))) {
                    return;
                } else if (!validation(etBodyType, getResources().getString(R.string.val_body_type))) {
                    return;
                } else if (!validation(etComplexion, getResources().getString(R.string.val_complexion))) {
                    return;
                } else if (!validation(etChallenged, getResources().getString(R.string.val_challanged))) {
                    return;
                } else if (!validation(etBloodgroup, getResources().getString(R.string.val_blood))) {
                    return;
                } else {
                    if (NetworkManager.isConnectToInternet(mContext)) {
                        request();

                    } else {
                        ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
                    }
                }
                break;
            case R.id.llBack:
                finish();
                overridePendingTransition(R.anim.stay, R.anim.slide_down);
                break;
            case R.id.etChallenged:
                spinnerChallenged.showSpinerDialog();
                break;
            case R.id.etComplexion:
                spinnerComplexion.showSpinerDialog();
                break;
            case R.id.etBodyType:
                spinnerBodyType.showSpinerDialog();
                break;
            case R.id.etBloodgroup:
                spinnerBloodGroup.showSpinerDialog();
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

    public void showData() {
        etAboutme.setText(userDTO.getAbout_me());
        etWeight.setText(userDTO.getWeight());
        etBodyType.setText(userDTO.getBody_type());
        etComplexion.setText(userDTO.getComplexion());
        etChallenged.setText(userDTO.getChallenged());
        etBloodgroup.setText(userDTO.getBlood_group());


        for (int j = 0; j < sysApplication.getComplexion().size(); j++) {
            if (sysApplication.getComplexion().get(j).getName().equalsIgnoreCase(userDTO.getComplexion())) {
                sysApplication.getComplexion().get(j).setSelected(true);
            }
        }
        spinnerComplexion = new SpinnerDialog((Activity) mContext, sysApplication.getComplexion(),
                getResources().getString(R.string.select_complexion), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation

        spinnerComplexion.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etComplexion.setText(item);
                parms.put(Consts.COMPLEXION, item);
            }
        });



        for (int j = 0; j < sysApplication.getChallenged().size(); j++) {
            if (sysApplication.getChallenged().get(j).getName().equalsIgnoreCase(userDTO.getChallenged())) {
                sysApplication.getChallenged().get(j).setSelected(true);
            }
        }
        spinnerChallenged = new SpinnerDialog((Activity) mContext, sysApplication.getChallenged(),
                getResources().getString(R.string.select_challenged), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerChallenged.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etChallenged.setText(item);
                parms.put(Consts.CHALLENGED, item);
            }
        });


        for (int j = 0; j < sysApplication.getBodyType().size(); j++) {
            if (sysApplication.getBodyType().get(j).getName().equalsIgnoreCase(userDTO.getBody_type())) {
                sysApplication.getBodyType().get(j).setSelected(true);
            }
        }
        spinnerBodyType = new SpinnerDialog((Activity) mContext, sysApplication.getBodyType(),
                getResources().getString(R.string.select_bodytype), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerBodyType.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etBodyType.setText(item);
                parms.put(Consts.BODY_TYPE, item);
            }
        });


        for (int j = 0; j < sysApplication.getBloodList().size(); j++) {
            if (sysApplication.getBloodList().get(j).getName().equalsIgnoreCase(userDTO.getBlood_group())) {
                sysApplication.getBloodList().get(j).setSelected(true);
            }
        }
        spinnerBloodGroup = new SpinnerDialog((Activity) mContext, sysApplication.getBloodList(),
                getResources().getString(R.string.select_blood), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerBloodGroup.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etBloodgroup.setText(item);
                parms.put(Consts.BLOOD_GROUP, id);
            }
        });
    }

}
