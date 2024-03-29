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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import ics.hindu.matrimony.models.LoginDTO;
import ics.hindu.matrimony.models.UserDTO;
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

public class ImportantDetailsActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
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
    private LinearLayout llWork;
    private RadioGroup workRG;
    private RadioButton yesRB, noRB;
    public String work = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_details);
        mContext = ImportantDetailsActivity.this;
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

        workRG = (RadioGroup) findViewById(R.id.workRG);
        workRG.setOnCheckedChangeListener(this);
        yesRB = (RadioButton) findViewById(R.id.yesRadioBTN);
        noRB = (RadioButton) findViewById(R.id.noRadioBTN);
        llWork = findViewById(R.id.llWork);

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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.yesRadioBTN:
                work = "1";
                llWork.setVisibility(View.VISIBLE);
                parms.put(Consts.WORKING, work);
                break;
            case R.id.noRadioBTN:
                work = "0";
                llWork.setVisibility(View.GONE);
                parms.put(Consts.WORKING, work);
                break;
        }

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
        etEducation.setText(userDTO.getQualification());
        etWorkArea.setText(userDTO.getWork_place());
        etoccupations.setText(userDTO.getOccupation());
        etorganization.setText(userDTO.getOrganisation_name());

        if (userDTO.getWorking()==0){
            noRB.setChecked(true);
            yesRB.setChecked(false);
            work = "0";
        }else {
            yesRB.setChecked(true);
            noRB.setChecked(false);
            work = "1";
        }


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
