package com.inspire.rkspmatrimony.activity.editprofile;

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

import com.inspire.rkspmatrimony.Models.LoginDTO;
import com.inspire.rkspmatrimony.Models.UserDTO;
import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.SysApplication;
import com.inspire.rkspmatrimony.https.HttpsRequest;
import com.inspire.rkspmatrimony.interfaces.Consts;
import com.inspire.rkspmatrimony.interfaces.Helper;
import com.inspire.rkspmatrimony.interfaces.OnSpinerItemClick;
import com.inspire.rkspmatrimony.network.NetworkManager;
import com.inspire.rkspmatrimony.sharedprefrence.SharedPrefrence;
import com.inspire.rkspmatrimony.utils.ProjectUtils;
import com.inspire.rkspmatrimony.utils.SpinnerDialog;
import com.inspire.rkspmatrimony.view.CustomButton;
import com.inspire.rkspmatrimony.view.CustomEditText;

import org.json.JSONObject;

import java.util.HashMap;

public class ImportantDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = ImportantDetailsActivity.class.getSimpleName();
    private Context mContext;
    private CustomEditText etEducation, etWorkArea, etoccupations, etorganization;
    private CustomButton btnSave;
    private LinearLayout llBack;
    private SysApplication sysApplication;
    private SpinnerDialog spinneroccupation;
    private RelativeLayout RRsncbar;
    private HashMap<String, String> parms = new HashMap<>();
    private SharedPrefrence prefrence;
    private UserDTO userDTO;
    private LoginDTO loginDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_details);
        mContext = ImportantDetailsActivity.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        userDTO = prefrence.getUserResponse(Consts.USER_DTO);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        parms.put(Consts.USER_ID, loginDTO.getUser_id());
        parms.put(Consts.TOKEN, loginDTO.getAccess_token());
        sysApplication = SysApplication.getInstance();
        init();
    }

    public void init() {
        RRsncbar = findViewById(R.id.RRsncbar);
        etEducation = findViewById(R.id.etEducation);
        etWorkArea = findViewById(R.id.etWorkArea);
        etoccupations = findViewById(R.id.etoccupations);
        etorganization = findViewById(R.id.etorganization);
        llBack = findViewById(R.id.llBack);
        btnSave = findViewById(R.id.btnSave);

        llBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        etoccupations.setOnClickListener(this);




        etEducation.addTextChangedListener(new MyTextWatcher(etEducation, Consts.QUALIFICATION));
        etorganization.addTextChangedListener(new MyTextWatcher(etorganization, Consts.ORGANIZATION));
        etWorkArea.addTextChangedListener(new MyTextWatcher(etWorkArea, Consts.WORK_PLACE));
showData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llBack:
                finish();
                overridePendingTransition(R.anim.stay, R.anim.slide_down);
                break;
            case R.id.btnSave:
                if (!validation(etEducation, getResources().getString(R.string.val_education))) {
                    return;
                } else if (!validation(etWorkArea, getResources().getString(R.string.val_work_area))) {
                    return;
                } else if (!validation(etoccupations, getResources().getString(R.string.val_occupations))) {
                    return;
                } else if (!validation(etorganization, getResources().getString(R.string.val_organization))) {
                    return;
                } else {
                    if (NetworkManager.isConnectToInternet(mContext)) {
                        request();

                    } else {
                        ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
                    }
                }
                break;
            case R.id.etoccupations:
                spinneroccupation.showSpinerDialog();
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
        etEducation.setText(userDTO.getQualification());
        etWorkArea.setText(userDTO.getWork_place());
        etoccupations.setText(userDTO.getOccupation());
        etorganization.setText(userDTO.getOrganisation_name());



        for (int j = 0; j < sysApplication.getOccupationList().size(); j++) {
            if (sysApplication.getOccupationList().get(j).getName().equalsIgnoreCase(userDTO.getOccupation())) {
                sysApplication.getOccupationList().get(j).setSelected(true);
            }
        }
        spinneroccupation = new SpinnerDialog((Activity) mContext, sysApplication.getOccupationList(), getResources().getString(R.string.select_occupation), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinneroccupation.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etoccupations.setText(item);
                parms.put(Consts.OCCUPATION, id);
            }
        });
    }




}
