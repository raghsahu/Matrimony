package com.samyotech.smmatrimony.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.samyotech.smmatrimony.Models.LoginDTO;
import com.samyotech.smmatrimony.R;
import com.samyotech.smmatrimony.activity.loginsignup.Login;
import com.samyotech.smmatrimony.https.HttpsRequest;
import com.samyotech.smmatrimony.interfaces.Consts;
import com.samyotech.smmatrimony.interfaces.Helper;
import com.samyotech.smmatrimony.network.NetworkManager;
import com.samyotech.smmatrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.smmatrimony.utils.ProjectUtils;
import com.samyotech.smmatrimony.view.CustomButton;
import com.samyotech.smmatrimony.view.CustomEditText;

import org.json.JSONObject;

import java.util.HashMap;

public class ChangePass extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = ChangePass.class.getSimpleName();
    private CustomEditText etOldPassword, etNewPassword, etConfirmNewPassword;
    private CustomButton UpdateBtn;
    private Context mContext;
    private LinearLayout llBack;
    private HashMap<String, String> parms = new HashMap<>();
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        mContext = ChangePass.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        setUiAction();
    }

    public void setUiAction() {

        etOldPassword = (CustomEditText) findViewById(R.id.etOldPassword);
        etNewPassword = (CustomEditText) findViewById(R.id.etNewPassword);
        etConfirmNewPassword = (CustomEditText) findViewById(R.id.etConfirmNewPassword);
        UpdateBtn = (CustomButton) findViewById(R.id.UpdateBtn);
        llBack = (LinearLayout) findViewById(R.id.llBack);
        UpdateBtn.setOnClickListener(this);
        llBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.UpdateBtn:
                Submit();
                break;
            case R.id.llBack:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }

    private void Submit() {
        if (!passwordValidation()) {
            return;
        } else if (!checkpass()) {
            return;
        } else {
            if (NetworkManager.isConnectToInternet(mContext)) {
                 updatePassword();
            } else {
                ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_connection));
            }

        }
    }

    public boolean passwordValidation() {
        if (!ProjectUtils.IsPasswordValidation(etOldPassword.getText().toString().trim())) {
            etOldPassword.setError(getResources().getString(R.string.val_pass_c));
            etOldPassword.requestFocus();
            return false;
        } else if (!ProjectUtils.IsPasswordValidation(etNewPassword.getText().toString().trim())) {
            etNewPassword.setError(getResources().getString(R.string.val_pass_c));
            etNewPassword.requestFocus();
            return false;
        } else
            return true;

    }

    private boolean checkpass() {
        if (etNewPassword.getText().toString().trim().equals("")) {
            etNewPassword.setError(getResources().getString(R.string.val_new_pas));
            return false;
        } else if (etConfirmNewPassword.getText().toString().trim().equals("")) {
            etConfirmNewPassword.setError(getResources().getString(R.string.val_c_pas));
            return false;
        } else if (!etNewPassword.getText().toString().trim().equals(etConfirmNewPassword.getText().toString().trim())) {
            etConfirmNewPassword.setError(getResources().getString(R.string.val_n_c_pas));
            return false;
        }
        return true;
    }

    public void updatePassword(){
        parms.put(Consts.TOKEN, loginDTO.getAccess_token());
        parms.put(Consts.OLD_PASSWORD, ProjectUtils.getEditTextValue(etOldPassword));
        parms.put(Consts.NEW_PASSWORD, ProjectUtils.getEditTextValue(etNewPassword));
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.CHANGE_PASSWORD_API, parms, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    ProjectUtils.showToast(mContext, msg);
                    prefrence.clearAllPreference();
                    Intent intent = new Intent(mContext, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.stay, R.anim.slide_down);
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }
            }
        });
    }

}
