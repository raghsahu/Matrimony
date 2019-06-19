package ics.hindu.matrimony.activity.loginsignup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import ics.hindu.matrimony.Models.LoginDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.activity.dashboard.Dashboard;
import ics.hindu.matrimony.https.HttpsRequest;
import ics.hindu.matrimony.interfaces.Consts;
import ics.hindu.matrimony.interfaces.Helper;
import ics.hindu.matrimony.network.NetworkManager;
import ics.hindu.matrimony.sharedprefrence.SharedPrefrence;
import ics.hindu.matrimony.utils.ProjectUtils;
//import ics.hindu.smmatrimony.interfaces.SmsListener;
//import ics.hindu.smmatrimony.utils.SmsReceiver;
import ics.hindu.matrimony.view.CustomButton;
import ics.hindu.matrimony.view.CustomEditText;
import ics.hindu.matrimony.view.CustomTextView;
import ics.hindu.matrimony.view.CustomTextViewBold;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Otp extends AppCompatActivity implements View.OnClickListener {
    private String TAG = Otp.class.getSimpleName();
    private Context mContext;
    private LinearLayout back;
    private CustomButton btnVerify;
    private CustomEditText etOne;
    private CustomEditText etTwo;
    private CustomEditText etThree;
    private CustomEditText etFour;
    private CustomTextView tvMobile;
    private String otp, mobile;
    private CustomTextViewBold tvResend;
    String resendotp;
    private HashMap<String, String> parms_resend = new HashMap<>();
    public static final String OTP_REGEX = "[0-9]{1,6}";
    private LoginDTO loginDTO;
    private SharedPrefrence prefrence;
    private HashMap<String, String> parms_active = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mContext = Otp.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);

//        parms_active.put(Consts.USER_ID, loginDTO.getData().getId());
//        parms_active.put(Consts.TOKEN, loginDTO.getAccess_token());
        //************************************************************static******************
        parms_active.put(Consts.USER_ID, "1234");
        parms_active.put(Consts.TOKEN, "111111111111");
        //*******************************************************************

        parms_active.put(Consts.IS_ACTIVE, "1");

        if (getIntent().hasExtra(Consts.OTP)) {
            otp = getIntent().getStringExtra(Consts.OTP);
            mobile = getIntent().getStringExtra(Consts.MOBILE);


        }
        setUiAction();
    }

    private void setUiAction() {
        tvResend = findViewById(R.id.tvResend);
        tvMobile = findViewById(R.id.tvMobile);
        tvMobile.setText(mobile);
        btnVerify = findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(this);
        etOne = findViewById(R.id.etOne);
        etTwo = findViewById(R.id.etTwo);
        etThree = findViewById(R.id.etThree);
        etFour = findViewById(R.id.etFour);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        tvResend.setOnClickListener(this);



        etOne.addTextChangedListener(new GenericTextWatcher(etOne));
        etTwo.addTextChangedListener(new GenericTextWatcher(etTwo));
        etThree.addTextChangedListener(new GenericTextWatcher(etThree));
        etFour.addTextChangedListener(new GenericTextWatcher(etFour));

        showOTP(otp);


    }

    /*@Override
    protected void onResume() {
        super.onResume();
        SmsReceiver.bindListener(this, "Matrimony");
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tvResend:
                if (NetworkManager.isConnectToInternet(mContext)) {
                    resendotp = getOtp();
                    otp = resendotp;
                    Log.e("OTP", otp + "  " + resendotp);
                    //  resendOTP();
                } else {
                    ProjectUtils.showToast(mContext, getString(R.string.internet_concation));
                }
                break;
            case R.id.btnVerify:
               // approve();

                //**********************************
                Intent in = new Intent(mContext, Dashboard.class);
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);


                //**************************************
                break;
        }
    }

    private void approve() {
        String otpVerify = otp;
        Log.e("OTP", otpVerify);
        if (otp.equals(otpVerify)) {
            // SmsReceiver.bindListener(null, "Varun");
            if (NetworkManager.isConnectToInternet(mContext)) {
                request();
            } else {
                ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
            }
        } else {
            ProjectUtils.showToast(mContext, "Incorrect OTP");
        }
    }

    public void request() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.UPDATE_PROFILE_API, parms_active, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    prefrence.setBooleanValue(Consts.IS_REGISTERED, true);
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

    @Override
    public void onBackPressed() {
        finish();
    }

    public String getOtp() {
        Random otp1 = new Random();

        StringBuilder builder = new StringBuilder();
        for (int count = 0; count <= 3; count++) {
            builder.append(otp1.nextInt(10));
        }
        return builder.toString();
    }

/*
    public void resendOTP() {
        parms_resend.put(Consts.TOKEN, loginDTO.getAccess_token());
        parms_resend.put(Consts.MOBILE, mobile);
        parms_resend.put(Consts.OTP, otp);

        new HttpsRequest(Consts.RESEND_OTP_API, parms_resend, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    ProjectUtils.showToast(mContext, msg);
                } else {
                    ProjectUtils.showToast(mContext, msg);

                }

            }
        });
    }
*/

    public void showOTP(String otp) {
        try {


            Log.d("Text", otp);
            //ProjectUtils.showToast(mContext,messageText);
            Pattern pattern = Pattern.compile(OTP_REGEX);
            Matcher matcher = pattern.matcher(otp);
            String otp_one = "";
            while (matcher.find()) {
                otp_one = matcher.group();
                Log.e("While", otp_one);
            }
            Log.e("ONE", otp_one.charAt(0) + "");
            etOne.setText("" + otp_one.charAt(0));
            etTwo.setText("" + otp_one.charAt(1));
            etThree.setText("" + otp_one.charAt(2));
            etFour.setText("" + otp_one.charAt(3));
            etFour.setSelection(etFour.getText().length());

            // btnSubmit.performClick();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

/*
    @Override
    public void messageReceived(String messageText) {
        try {


            Log.d("Text", messageText);
            //ProjectUtils.showToast(mContext,messageText);
            Pattern pattern = Pattern.compile(OTP_REGEX);
            Matcher matcher = pattern.matcher(messageText);
            String otp_one = "";
            while (matcher.find()) {
                otp_one = matcher.group();
                Log.e("While", otp_one);
            }
            Log.e("ONE", otp_one.charAt(0) + "");
            etOne.setText("" + otp_one.charAt(0));
            etTwo.setText("" + otp_one.charAt(1));
            etThree.setText("" + otp_one.charAt(2));
            etFour.setText("" + otp_one.charAt(3));
            etFour.setSelection(etFour.getText().length());

            // btnSubmit.performClick();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/

    /**
     * automatic moving text on verification
     */

    public class GenericTextWatcher implements TextWatcher {
        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {

                case R.id.etOne:
                    if (text.length() == 1)
                        etTwo.requestFocus();
                    break;
                case R.id.etTwo:
                    if (text.length() == 1)
                        etThree.requestFocus();
                    else
                        etOne.requestFocus();
                    break;
                case R.id.etThree:
                    if (text.length() == 1)
                        etFour.requestFocus();
                    else
                        etTwo.requestFocus();
                    break;
                case R.id.etFour:
                    if (text.length() == 0)
                        etThree.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }

/*
    @Override
    protected void onDestroy() {
        super.onDestroy();

        SmsReceiver.bindListener(null, "Varun");
    }
*/
}
