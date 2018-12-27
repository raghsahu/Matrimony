package com.samyotech.smmatrimony.activity.loginsignup;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.samyotech.smmatrimony.R;
import com.samyotech.smmatrimony.https.HttpsRequest;
import com.samyotech.smmatrimony.interfaces.Consts;
import com.samyotech.smmatrimony.interfaces.Helper;
import com.samyotech.smmatrimony.network.NetworkManager;
import com.samyotech.smmatrimony.utils.ProjectUtils;
import com.samyotech.smmatrimony.view.CustomButton;
import com.samyotech.smmatrimony.view.CustomEditText;

import org.json.JSONObject;

import java.util.HashMap;

public class ForgotPass extends AppCompatActivity {
    private Context mContext;
    private CustomEditText etMobile;
    private CustomButton btnSubmit;
    private HashMap<String, String> parms = new HashMap<>();
    private String TAG = ForgotPass.class.getSimpleName();
    private LinearLayout llBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        mContext = ForgotPass.this;
        setUiAction();
    }

    public void setUiAction() {
        llBack = findViewById(R.id.llBack);
        etMobile = findViewById(R.id.etMobile);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void submitForm() {
        if (!ValidateMobile()) {
            return;
        } else {
            if (NetworkManager.isConnectToInternet(mContext)) {
                updatepass();

            } else {
                ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_connection));
            }
        }
    }


    public boolean ValidateMobile() {
        if (!ProjectUtils.isPhoneNumberValid(etMobile.getText().toString().trim())) {
            etMobile.setError(getResources().getString(R.string.val_mobile));
            etMobile.requestFocus();
            return false;
        }
        return true;
    }

    public void updatepass() {
        parms.put(Consts.MOBILE, ProjectUtils.getEditTextValue(etMobile));
        ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.FORGET_PASSWORD_API, parms, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    ProjectUtils.showToast(mContext, msg);
                    finish();
                    overridePendingTransition(R.anim.anim_slide_in_left,
                            R.anim.anim_slide_out_left);
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }
            }
        });
    }

}
