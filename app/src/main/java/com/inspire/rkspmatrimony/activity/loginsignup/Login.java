package com.inspire.rkspmatrimony.activity.loginsignup;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.inspire.rkspmatrimony.Models.LoginDTO;
import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.activity.dashboard.Dashboard;
import com.inspire.rkspmatrimony.https.HttpsRequest;
import com.inspire.rkspmatrimony.interfaces.Consts;
import com.inspire.rkspmatrimony.interfaces.Helper;
import com.inspire.rkspmatrimony.network.NetworkManager;
import com.inspire.rkspmatrimony.sharedprefrence.SharedPrefrence;
import com.inspire.rkspmatrimony.utils.ProjectUtils;
import com.inspire.rkspmatrimony.view.CustomButton;
import com.inspire.rkspmatrimony.view.CustomEditText;
import com.inspire.rkspmatrimony.view.CustomTextView;
import com.inspire.rkspmatrimony.view.ExtendedEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private ExtendedEditText etNumber;
    private CustomEditText etPassword;
    private CustomButton btnLogin;
    private CustomTextView tvCreateNewAC, tvForgotPass;
    private Context mContext;
    private String TAG = Login.class.getSimpleName();
    private RelativeLayout RRsncbar;
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = Login.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        setUIAction();
    }

    public void setUIAction() {
        RRsncbar = findViewById(R.id.RRsncbar);
        etPassword = findViewById(R.id.etPassword);
        etNumber = findViewById(R.id.etNumber);
        etNumber.setPrefix("+91 ");
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        tvCreateNewAC = findViewById(R.id.tvCreateNewAC);
        tvForgotPass = findViewById(R.id.tvForgotPass);
        tvCreateNewAC.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                clickForSubmit();
                break;
            case R.id.tvCreateNewAC:
                startActivity(new Intent(mContext, Registration.class));
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                break;
            case R.id.tvForgotPass:
                break;
        }
    }

    public void clickForSubmit() {
        if (!ProjectUtils.isPhoneNumberValid(etNumber.getText().toString().trim())) {
            showSickbar(getString(R.string.val_mobile));
        } else if (!ProjectUtils.IsPasswordValidation(etPassword.getText().toString().trim())) {
            showSickbar(getString(R.string.val_password));
        } else {
            if (NetworkManager.isConnectToInternet(mContext)) {
                login();
            } else {
                ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
            }
        }


    }

    public void login() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.LOGIN_API, getparm(), mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    ProjectUtils.showToast(mContext, msg);
                    loginDTO = new Gson().fromJson(response.toString(), LoginDTO.class);
                    prefrence.setLoginResponse(loginDTO, Consts.LOGIN_DTO);
                    prefrence.setBooleanValue(Consts.IS_REGISTERED,true);
                    ProjectUtils.showToast(mContext, msg);
                    Intent in = new Intent(mContext, Dashboard.class);
                    startActivity(in);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_left,
                            R.anim.anim_slide_out_left);
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }
            }
        });
    }

    public HashMap<String, String> getparm() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put(Consts.MOBILE, ProjectUtils.getEditTextValue(etNumber));
        parms.put(Consts.PASSWORD, ProjectUtils.getEditTextValue(etPassword));
        Log.e(TAG + " Login", parms.toString());
        return parms;
    }



    public void showSickbar(String msg) {
        Snackbar snackbar = Snackbar.make(RRsncbar, msg, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }
}