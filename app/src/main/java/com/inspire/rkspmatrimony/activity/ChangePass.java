package com.inspire.rkspmatrimony.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.network.NetworkManager;
import com.inspire.rkspmatrimony.utils.ProjectUtils;
import com.inspire.rkspmatrimony.view.CustomButton;
import com.inspire.rkspmatrimony.view.CustomEditText;

public class ChangePass extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = ChangePass.class.getSimpleName();
    private CustomEditText etOldPassword, etNewPassword, etConfirmNewPassword;
    private CustomButton UpdateBtn;
    private Context mContext;
    private LinearLayout llBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        mContext = ChangePass.this;
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
                // updatePassword();
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

}
