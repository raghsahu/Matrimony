package com.samyotech.smmatrimony.activity.editprofile;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.samyotech.smmatrimony.Models.LoginDTO;
import com.samyotech.smmatrimony.Models.UserDTO;
import com.samyotech.smmatrimony.R;
import com.samyotech.smmatrimony.SysApplication;
import com.samyotech.smmatrimony.https.HttpsRequest;
import com.samyotech.smmatrimony.interfaces.Consts;
import com.samyotech.smmatrimony.interfaces.Helper;
import com.samyotech.smmatrimony.interfaces.OnSpinerItemClick;
import com.samyotech.smmatrimony.network.NetworkManager;
import com.samyotech.smmatrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.smmatrimony.utils.ProjectUtils;
import com.samyotech.smmatrimony.utils.SpinnerDialog;
import com.samyotech.smmatrimony.view.CustomButton;
import com.samyotech.smmatrimony.view.CustomEditText;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CriticalFields extends AppCompatActivity implements View.OnClickListener {
    private String TAG = CriticalFields.class.getSimpleName();
    private Context mContext;
    private CustomEditText etMarital, etDOB, etGotra, etManglik,
            etBirthTime, etBirthPlace, etGotraNanihal, etAadhar;
    private CustomButton btnSave;
    private LinearLayout llBack;
    private SysApplication sysApplication;
    private SpinnerDialog spinnerMaritial, spinnerManglik;
    private RelativeLayout RRsncbar;
    private HashMap<String, String> parms = new HashMap<>();
    private ProjectUtils.CustomTimePickerDialog dialog;
    private Calendar myCalendar = Calendar.getInstance();
    private Calendar refCalender = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private Date dob_timeStamp;

    private SharedPrefrence prefrence;
    private UserDTO userDTO;
    private LoginDTO loginDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critical_fields);
        mContext = CriticalFields.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        userDTO = prefrence.getUserResponse(Consts.USER_DTO);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        parms.put(Consts.USER_ID, loginDTO.getData().getId());
        parms.put(Consts.TOKEN, loginDTO.getAccess_token());
        parms.put(Consts.CRITICAL, "1");
        sysApplication = SysApplication.getInstance(mContext);
        setUiAction();
    }

    public void setUiAction() {
        RRsncbar = findViewById(R.id.RRsncbar);
        etAadhar = findViewById(R.id.etAadhar);
        etDOB = findViewById(R.id.etDOB);
        etMarital = findViewById(R.id.etMarital);
        etGotra = findViewById(R.id.etGotra);
        etGotraNanihal = findViewById(R.id.etGotraNanihal);
        etManglik = findViewById(R.id.etManglik);
        etBirthTime = findViewById(R.id.etBirthTime);
        etBirthPlace = findViewById(R.id.etBirthPlace);
        btnSave = findViewById(R.id.btnSave);
        llBack = findViewById(R.id.llBack);

        etMarital.setOnClickListener(this);
        etManglik.setOnClickListener(this);
        etBirthTime.setOnClickListener(this);
        etDOB.setOnClickListener(this);
        llBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);


        etAadhar.addTextChangedListener(new MyTextWatcher(etAadhar, Consts.AADHAAR));
        etGotra.addTextChangedListener(new MyTextWatcher(etGotra, Consts.GOTRA));
        etGotraNanihal.addTextChangedListener(new MyTextWatcher(etGotraNanihal, Consts.GOTRA_NANIHAL));
        etBirthPlace.addTextChangedListener(new MyTextWatcher(etBirthPlace, Consts.BIRTH_PLACE));
        showData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etMarital:
                spinnerMaritial.showSpinerDialog();
                break;
            case R.id.llBack:
                finish();
                overridePendingTransition(R.anim.stay, R.anim.slide_down);
                break;
            case R.id.btnSave:
                if (!validation(etDOB, getResources().getString(R.string.val_dob))) {
                    return;
                } else if (!validation(etMarital, getResources().getString(R.string.val_maritial_status))) {
                    return;
                } else if (!validation(etAadhar, getResources().getString(R.string.val_aadhar))) {
                    return;
                } else if (!validation(etGotra, getResources().getString(R.string.val_gotra))) {
                    return;
                } else if (!validation(etGotraNanihal, getResources().getString(R.string.val_gotra_nanihal))) {
                    return;
                } else if (!validation(etManglik, getResources().getString(R.string.val_manglik))) {
                    return;
                } else if (!validation(etBirthTime, getResources().getString(R.string.val_birth_time))) {
                    return;
                } else if (!validation(etBirthPlace, getResources().getString(R.string.val_birth_place))) {
                    return;
                } else {
                    if (NetworkManager.isConnectToInternet(mContext)) {
                        request();

                    } else {
                        ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
                    }
                }
                break;
            case R.id.etManglik:
                spinnerManglik.showSpinerDialog();
                break;
            case R.id.etBirthTime:

                dialog = new ProjectUtils.CustomTimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCalendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
                        etBirthTime.setText(sdf1.format(myCalendar.getTime()));
                        parms.put(Consts.BIRTH_TIME, ProjectUtils.getEditTextValue(etBirthTime));
                    }
                },
                        myCalendar.getTime().getHours(), myCalendar.getTime().getMinutes(), false);
                dialog.show();

                break;
            case R.id.etDOB:
                openDatePickerDOB();
                break;
        }
    }

    public void openDatePickerDOB() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                calendar.set(Calendar.YEAR, y);
                calendar.set(Calendar.MONTH, m);
                calendar.set(Calendar.DAY_OF_MONTH, d);

                if (calendar.getTimeInMillis() <= refCalender.getTimeInMillis()) {
                    String myFormat = "dd-MMM-yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    etDOB.setText(sdf.format(calendar.getTime()));
                    dob_timeStamp = calendar.getTime();

                    String myFormatsend = "yyyy-MM-dd"; //In which you need put here
                    SimpleDateFormat sdf1 = new SimpleDateFormat(myFormatsend, Locale.US);
                    parms.put(Consts.DOB, String.valueOf(sdf1.format(dob_timeStamp)));
                } else {
                    ProjectUtils.showToast(mContext, "Cannot select future date");
                }

            }


        }, year - 18, monthOfYear, dayOfMonth);
        calendar.set(year - 18, monthOfYear, dayOfMonth);
        long value = calendar.getTimeInMillis();
        datePickerDialog.setTitle(getResources().getString(R.string.select_dob));

        datePickerDialog.getDatePicker().setMaxDate(value);
        datePickerDialog.show();
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
        etAadhar.setText(userDTO.getAadhaar());
        etDOB.setText(userDTO.getDob());
        etMarital.setText(userDTO.getMarital_status());
        etGotra.setText(userDTO.getGotra());
        etGotraNanihal.setText(userDTO.getGotra_nanihal());
        etManglik.setText(userDTO.getManglik());
        etBirthTime.setText(userDTO.getBirth_time());
        etBirthPlace.setText(userDTO.getBirth_place());


        for (int j = 0; j < sysApplication.getMaritalList().size(); j++) {
            if (sysApplication.getMaritalList().get(j).getName().equalsIgnoreCase(userDTO.getMarital_status())) {
                sysApplication.getMaritalList().get(j).setSelected(true);
            }
        }
        spinnerMaritial = new SpinnerDialog(CriticalFields.this, sysApplication.getMaritalList(), getResources().getString(R.string.select_maritial_status), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerMaritial.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etMarital.setText(item);
                parms.put(Consts.MARITAL_STATUS, id);

            }
        });


        for (int j = 0; j < sysApplication.getManglikList().size(); j++) {
            if (sysApplication.getManglikList().get(j).getName().equalsIgnoreCase(userDTO.getManglik())) {
                sysApplication.getManglikList().get(j).setSelected(true);
            }
        }
        spinnerManglik = new SpinnerDialog(CriticalFields.this, sysApplication.getManglikList(), getResources().getString(R.string.select_manglik), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerManglik.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etManglik.setText(item);
                parms.put(Consts.MANGLIK, id);

            }
        });

    }

}
