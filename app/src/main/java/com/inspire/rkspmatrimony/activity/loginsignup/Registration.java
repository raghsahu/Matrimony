package com.inspire.rkspmatrimony.activity.loginsignup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.badoualy.stepperindicator.StepperIndicator;
import com.google.gson.Gson;
import com.inspire.rkspmatrimony.Models.LoginDTO;
import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.Splash;
import com.inspire.rkspmatrimony.SysApplication;
import com.inspire.rkspmatrimony.fragment.registration.ImportantDetails;
import com.inspire.rkspmatrimony.fragment.registration.LoginDetails;
import com.inspire.rkspmatrimony.fragment.registration.PersonalDetails;
import com.inspire.rkspmatrimony.fragment.registration.ProfileFor;
import com.inspire.rkspmatrimony.fragment.registration.SocialDetails;
import com.inspire.rkspmatrimony.https.HttpsRequest;
import com.inspire.rkspmatrimony.interfaces.Consts;
import com.inspire.rkspmatrimony.interfaces.Helper;
import com.inspire.rkspmatrimony.network.NetworkManager;
import com.inspire.rkspmatrimony.sharedprefrence.SharedPrefrence;
import com.inspire.rkspmatrimony.utils.ProjectUtils;
import com.inspire.rkspmatrimony.view.CustomTextViewBold;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class Registration extends AppCompatActivity {

    private ViewPager viewPager;
    private PageAdapter mAdapter;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private Context mContext;
    public ProfileFor profileFor = new ProfileFor();
    private PersonalDetails personalDetails = new PersonalDetails();
    public ImportantDetails importantDetails = new ImportantDetails();
    private SocialDetails socialDetails = new SocialDetails();
    private LoginDetails loginDetails = new LoginDetails();
    private String TAG = Registration.class.getSimpleName();
    public String type = "";
    public CustomTextViewBold tvHeader;
    private LinearLayout llBack;
    private StepperIndicator stepper_indicator;
    private RelativeLayout action_bar_menus;
    public SysApplication sysApplication;
    private RelativeLayout RRsncbar;
    String otp = "";
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;
    public SharedPreferences languageDetails;
    public String lang = "";
    private SharedPreferences firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mContext = Registration.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        firebase = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Log.e("tokensss", firebase.getString(Consts.FIREBASE_TOKEN, ""));

        sysApplication = SysApplication.getInstance(mContext);
        languageDetails = Registration.this.getSharedPreferences(Consts.LANGUAGE_PREF, MODE_PRIVATE);
        lang =  languageDetails.getString(Consts.SELECTED_LANGUAGE,"");

        tvHeader = findViewById(R.id.tvHeader);
        stepper_indicator = findViewById(R.id.stepper_indicator);

        action_bar_menus = findViewById(R.id.action_bar_menus);
        viewPager = findViewById(R.id.view_pager);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);
        llBack = findViewById(R.id.llBack);
        RRsncbar = findViewById(R.id.RRsncbar);
        layouts = new int[]{
                R.layout.fragment_profile_for,
                R.layout.fragment_profile_for,
                R.layout.fragment_profile_for,
                R.layout.fragment_profile_for,
                R.layout.fragment_profile_for};
        mAdapter = new PageAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(5);


        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.beginFakeDrag();
        stepper_indicator.setViewPager(viewPager, true);
        stepper_indicator.addOnStepClickListener(new StepperIndicator.OnStepClickListener() {
            @Override
            public void onStepClicked(int step) {
                //viewPager.setCurrentItem(step, true);
            }
        });

        btnSkip.setVisibility(View.GONE);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < layouts.length) {
                    if (current == 1) {

                        if (profileFor.profile.equalsIgnoreCase("")) {
                            showSickbar(getString(R.string.profile_for));
                        } else {
                            viewPager.setCurrentItem(current);
                        }
                    } else if (current == 2) {
                        if (personalDetails.rg_gender_options.getCheckedRadioButtonId() == -1) {
                            showSickbar(getString(R.string.val_gender));
                        } else if (!ProjectUtils.isEditTextFilled(personalDetails.etDOB)) {
                            showSickbar(getString(R.string.val_dob));
                        } else if (!ProjectUtils.isEditTextFilled(personalDetails.etHeight)) {
                            showSickbar(getString(R.string.val_height));
                        } else if (!ProjectUtils.isEditTextFilled(personalDetails.etState)) {
                            showSickbar(getString(R.string.val_state));
                        } else if (!ProjectUtils.isEditTextFilled(personalDetails.etDistrict)) {
                            showSickbar(getString(R.string.val_district));
                        } else if (!ProjectUtils.isEditTextFilled(personalDetails.etCity)) {
                            showSickbar(getString(R.string.val_city));
                        } else if (!ProjectUtils.isEditTextFilled(personalDetails.etPincode)) {
                            showSickbar(getString(R.string.val_pin));
                        } else {
                            viewPager.setCurrentItem(current);
                        }
                    } else if (current == 3) {
                        if (!ProjectUtils.isEditTextFilled(importantDetails.etEducation)) {
                            showSickbar(getString(R.string.val_education));
                        } else if (!validate()) {

                        }else if (!ProjectUtils.isEditTextFilled(importantDetails.etBlood)) {
                            showSickbar(getString(R.string.val_blood));
                        } else if (!ProjectUtils.isAddharValidate(importantDetails.etAadhar.getText().toString().trim())) {
                            showSickbar(getString(R.string.val_aadhar));
                        } else {
                            viewPager.setCurrentItem(current);
                        }
                    } else if (current == 4) {
                        if (!ProjectUtils.isEditTextFilled(socialDetails.etMaritial)) {
                            showSickbar(getString(R.string.val_maritial_status));
                        } else if (!ProjectUtils.isEditTextFilled(socialDetails.etGotra)) {
                            showSickbar(getString(R.string.val_gotra));
                        } else if (!ProjectUtils.isEditTextFilled(socialDetails.etManglik)) {
                            showSickbar(getString(R.string.val_manglik));
                        } else if (!ProjectUtils.isEditTextFilled(socialDetails.etBirthTime)) {
                            showSickbar(getString(R.string.val_birth_time));
                        } else if (!ProjectUtils.isEditTextFilled(socialDetails.etBirthPlace)) {
                            showSickbar(getString(R.string.val_birth_place));
                        } else if (!ProjectUtils.isEditTextFilled(socialDetails.etGotraNanihal)) {
                            showSickbar(getString(R.string.val_gotra_nanihal));
                        } else {
                            viewPager.setCurrentItem(current);
                        }
                    } else {
                        viewPager.setCurrentItem(current);

                    }
                } else {
                    clickForSubmit();
                }
                if (btnNext.getText().toString().equals("SUBMIT")) {
                    clickForSubmit();
                }
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItemPre(1);
                viewPager.setCurrentItem(current);
            }
        });

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() == 0) {
                    clickDone();

                } else {
                    int current = getItemPre(1);
                    viewPager.setCurrentItem(current);
                }
            }
        });

    }

    public void clickForSubmit() {

        if (profileFor.profile.equalsIgnoreCase("")) {
            showSickbar(getString(R.string.profile_for));
        } else if (personalDetails.rg_gender_options.getCheckedRadioButtonId() == -1) {
            showSickbar(getString(R.string.val_gender));
        } else if (!ProjectUtils.isEditTextFilled(personalDetails.etDOB)) {
            showSickbar(getString(R.string.val_dob));
        } else if (!ProjectUtils.isEditTextFilled(personalDetails.etHeight)) {
            showSickbar(getString(R.string.val_height));
        } else if (!ProjectUtils.isEditTextFilled(personalDetails.etState)) {
            showSickbar(getString(R.string.val_state));
        } else if (!ProjectUtils.isEditTextFilled(personalDetails.etDistrict)) {
            showSickbar(getString(R.string.val_district));
        } else if (!ProjectUtils.isEditTextFilled(personalDetails.etCity)) {
            showSickbar(getString(R.string.val_city));
        } else if (!ProjectUtils.isEditTextFilled(personalDetails.etPincode)) {
            showSickbar(getString(R.string.val_pin));
        } else if (!ProjectUtils.isEditTextFilled(importantDetails.etEducation)) {
            showSickbar(getString(R.string.val_education));
        } else if (!validate()) {

        } else if (!ProjectUtils.isEditTextFilled(importantDetails.etBlood)) {
            showSickbar(getString(R.string.val_blood));
        } else if (!ProjectUtils.isAddharValidate(importantDetails.etAadhar.getText().toString().trim())) {
            showSickbar(getString(R.string.val_aadhar));
        } else if (!ProjectUtils.isEditTextFilled(socialDetails.etMaritial)) {
            showSickbar(getString(R.string.val_maritial_status));
        } else if (!ProjectUtils.isEditTextFilled(socialDetails.etGotra)) {
            showSickbar(getString(R.string.val_gotra));
        } else if (!ProjectUtils.isEditTextFilled(socialDetails.etManglik)) {
            showSickbar(getString(R.string.val_manglik));
        } else if (!ProjectUtils.isEditTextFilled(socialDetails.etBirthTime)) {
            showSickbar(getString(R.string.val_birth_time));
        } else if (!ProjectUtils.isEditTextFilled(socialDetails.etBirthPlace)) {
            showSickbar(getString(R.string.val_birth_place));
        } else if (!ProjectUtils.isEditTextFilled(socialDetails.etGotraNanihal)) {
            showSickbar(getString(R.string.val_gotra_nanihal));
        } else if (!ProjectUtils.isEditTextFilled(loginDetails.etName)) {
            showSickbar(getString(R.string.val_full_name));
        } else if (!ProjectUtils.isEmailValid(loginDetails.etEmail.getText().toString().trim())) {
            showSickbar(getString(R.string.val_email));
        } else if (!ProjectUtils.IsPasswordValidation(loginDetails.etPassword.getText().toString().trim())) {
            showSickbar(getString(R.string.val_password));
        } else if (!ProjectUtils.isPhoneNumberValid(loginDetails.etphone.getText().toString().trim())) {
            showSickbar(getString(R.string.val_mobile));
        } else {
            if (NetworkManager.isConnectToInternet(mContext)) {

                Random otp1 = new Random();
                StringBuilder builder = new StringBuilder();
                for (int count = 0; count <= 3; count++) {
                    builder.append(otp1.nextInt(10));
                }
                otp = builder.toString();
                register();

            } else {
                ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
            }
        }


    }

    public boolean validate() {
        if (importantDetails.work.equalsIgnoreCase("1")) {
            if (!ProjectUtils.isEditTextFilled(importantDetails.etWorkArea)) {
                showSickbar(getString(R.string.val_work_area));
            } else if (!ProjectUtils.isEditTextFilled(importantDetails.etoccupations)) {
                showSickbar(getString(R.string.val_occupations));
            } else if (!ProjectUtils.isEditTextFilled(importantDetails.etorganization)) {
                showSickbar(getString(R.string.val_organization));
            } else if (!ProjectUtils.isEditTextFilled(importantDetails.etIncome)) {
                showSickbar(getString(R.string.val_income));
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private int getItemPre(int i) {
        return viewPager.getCurrentItem() - i;
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {

            if (position == layouts.length - 1) {
                btnNext.setText("Submit");
            } else {
                btnNext.setText("Next");
            }
            if (position == 0) {
                llBack.setVisibility(View.VISIBLE);
                btnSkip.setVisibility(View.GONE);

            } else {
                llBack.setVisibility(View.GONE);
                btnSkip.setVisibility(View.VISIBLE);

            }
            if (position == 0) {
                tvHeader.setText(getResources().getString(R.string.head_res_one));
            } else if (position == 1) {
                tvHeader.setText(getResources().getString(R.string.head_res_two));
            } else if (position == 2) {
                tvHeader.setText(getResources().getString(R.string.head_res_three));
            } else if (position == 3) {
                tvHeader.setText(getResources().getString(R.string.head_res_four));
            } else if (position == 4) {
                tvHeader.setText(getResources().getString(R.string.head_res_five));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {


        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


    public class PageAdapter extends FragmentStatePagerAdapter {
        // private Fragment fragment;
        public PageAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    tvHeader.setText(getResources().getString(R.string.head_res_one));
                    return profileFor;
                case 1:
                    tvHeader.setText(getResources().getString(R.string.head_res_two));
                    return personalDetails;
                case 2:
                    tvHeader.setText(getResources().getString(R.string.head_res_three));
                    return importantDetails;
                case 3:
                    tvHeader.setText(getResources().getString(R.string.head_res_four));
                    return socialDetails;
                case 4:
                    tvHeader.setText(getResources().getString(R.string.head_res_five));
                    return loginDetails;
                default:
                    tvHeader.setText(getResources().getString(R.string.head_res_one));
                    return profileFor;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

    }


    public void register() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.REGISTRATION, getparm(), mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    loginDTO = new Gson().fromJson(response.toString(), LoginDTO.class);
                    prefrence.setLoginResponse(loginDTO, Consts.LOGIN_DTO);
                    ProjectUtils.showToast(mContext, msg);

                    Intent in = new Intent(mContext, Otp.class);
                    in.putExtra(Consts.OTP, otp);
                    in.putExtra(Consts.MOBILE, ProjectUtils.getEditTextValue(loginDetails.etphone));
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
        parms.put(Consts.PROFILE_FOR, profileFor.profile);
        if (personalDetails.rb_gender_female.isChecked()) {
            parms.put(Consts.GENDER, "F");
        } else {
            parms.put(Consts.GENDER, "M");
        }
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        parms.put(Consts.DOB, String.valueOf(sdf.format(personalDetails.dob_timeStamp)));
        parms.put(Consts.HEIGHT, personalDetails.height);
        parms.put(Consts.STATE, personalDetails.state);
        parms.put(Consts.DISTRICT, personalDetails.district);
        parms.put(Consts.CITY, ProjectUtils.getEditTextValue(personalDetails.etCity));
        parms.put(Consts.PIN, ProjectUtils.getEditTextValue(personalDetails.etPincode));
        parms.put(Consts.QUALIFICATION, ProjectUtils.getEditTextValue(importantDetails.etEducation));
        parms.put(Consts.WORKING, importantDetails.work);

        if (importantDetails.work.equalsIgnoreCase("1")) {
            parms.put(Consts.WORK_PLACE, ProjectUtils.getEditTextValue(importantDetails.etWorkArea));
            parms.put(Consts.OCCUPATION, importantDetails.occupation);
            parms.put(Consts.ORGANIZATION, ProjectUtils.getEditTextValue(importantDetails.etorganization));
            parms.put(Consts.INCOME, importantDetails.income);
        }
        parms.put(Consts.BLOOD_GROUP, importantDetails.blood);
        parms.put(Consts.AADHAAR, ProjectUtils.getEditTextValue(importantDetails.etAadhar));
        parms.put(Consts.MARITAL_STATUS, socialDetails.marital);
        parms.put(Consts.GOTRA, ProjectUtils.getEditTextValue(socialDetails.etGotra));
        parms.put(Consts.MANGLIK, socialDetails.manglik);
        parms.put(Consts.BIRTH_TIME, ProjectUtils.getEditTextValue(socialDetails.etBirthTime));
        parms.put(Consts.BIRTH_PLACE, ProjectUtils.getEditTextValue(socialDetails.etBirthPlace));
        parms.put(Consts.GOTRA_NANIHAL, ProjectUtils.getEditTextValue(socialDetails.etGotraNanihal));
        parms.put(Consts.NAME, ProjectUtils.getEditTextValue(loginDetails.etName));
        parms.put(Consts.EMAIL, ProjectUtils.getEditTextValue(loginDetails.etEmail));
        parms.put(Consts.PASSWORD, ProjectUtils.getEditTextValue(loginDetails.etPassword));
        parms.put(Consts.MOBILE, ProjectUtils.getEditTextValue(loginDetails.etphone));
        parms.put(Consts.OTP, otp);
        parms.put(Consts.LANG, lang);
        parms.put(Consts.FIREBASE_TOKEN, firebase.getString(Consts.FIREBASE_TOKEN, ""));

        Log.e("parms", parms.toString());
        return parms;
    }


    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            clickDone();

        } else {
            int current = getItemPre(1);
            viewPager.setCurrentItem(current);
        }
    }

    public void clickDone() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage(R.string.exit_res)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.msg_res_exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();

                    }
                })
                .show();
    }

    public void showSickbar(String msg) {
        Snackbar snackbar = Snackbar.make(RRsncbar, msg, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }


}