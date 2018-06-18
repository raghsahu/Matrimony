package com.samyotech.matrimony.fragment.registration;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.samyotech.matrimony.Models.CommanDTO;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.loginsignup.Registration;
import com.samyotech.matrimony.database.TestAdapter;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.interfaces.OnSpinerItemClick;
import com.samyotech.matrimony.utils.ProjectUtils;
import com.samyotech.matrimony.utils.SpinnerDialog;
import com.samyotech.matrimony.view.CustomEditText;
import com.samyotech.matrimony.view.InputFieldView;
import com.samyotech.matrimony.view.InputOpenFieldView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class PersonalDetails extends Fragment implements View.OnClickListener {
    private View view;
    public RadioGroup rg_gender_options;
    public RadioButton rb_gender_female, rb_gender_male;

    public CustomEditText etDOB, etHeight, etState, etDistrict, etCity, etPincode;
    private ArrayList<CommanDTO> stateList = new ArrayList<>();
    private ArrayList<CommanDTO> districtList = new ArrayList<>();
    private static final int allowedDOB = 21;
    SpinnerDialog spinnerHeight, spinnerState, spinnerDistrict;
    public String height = "", state = "", district = "";
    public Date dob_timeStamp;
    InputFieldView inf_district;
    InputOpenFieldView inf_city, til_pin_code;
    private String TAG = PersonalDetails.class.getSimpleName();
    HashMap<String, String> parmsDistrict = new HashMap<>();
    TestAdapter mDbHelper;
    private Registration registration;
    private Calendar refCalender = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_personal_details, container, false);
        mDbHelper = new TestAdapter(getActivity());

        mDbHelper.createDatabase();
        mDbHelper.open();
        setUiAction(view);
        return view;
    }

    public void setUiAction(View view) {

        inf_district = view.findViewById(R.id.inf_district);
        inf_city = view.findViewById(R.id.inf_city);
        til_pin_code = view.findViewById(R.id.til_pin_code);

        rg_gender_options = view.findViewById(R.id.rg_gender_options);
        rb_gender_male = view.findViewById(R.id.rb_gender_male);
        rb_gender_female = view.findViewById(R.id.rb_gender_female);
        etDOB = view.findViewById(R.id.etDOB);
        etHeight = view.findViewById(R.id.etHeight);
        etState = view.findViewById(R.id.etState);
        etDistrict = view.findViewById(R.id.etDistrict);
        etCity = view.findViewById(R.id.etCity);
        etPincode = view.findViewById(R.id.etPincode);

        etDOB.setOnClickListener(this);
        etHeight.setOnClickListener(this);
        etState.setOnClickListener(this);
        etDistrict.setOnClickListener(this);
        etCity.setOnClickListener(this);
        etPincode.setOnClickListener(this);

        //spinnerHeight = new spinnerHeight(getActivity(), heightList, "Select or Search City", "Close Button Text");// With No Animation
        spinnerHeight = new SpinnerDialog(getActivity(), registration.sysApplication.getHeightList(), getResources().getString(R.string.select_height), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation


        spinnerHeight.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etHeight.setText(item);
                height = id;
            }
        });
        stateList = new ArrayList<>();
        stateList = mDbHelper.getAllState(registration.lang);
        spinnerState = new SpinnerDialog(getActivity(), stateList, getResources().getString(R.string.select_state), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerState.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etState.setText(item);
                state = id;

                inf_district.setVisibility(View.VISIBLE);

                districtList = new ArrayList<>();
                districtList = mDbHelper.getAllDistrict(id,registration.lang);
                showDistrict();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etDOB:
                openDatePickerDOB();
                break;
            case R.id.etHeight:
                spinnerHeight.showSpinerDialog();
                break;
            case R.id.etState:
                if (stateList.size() > 0)
                    spinnerState.showSpinerDialog();
                else
                    ProjectUtils.showToast(getActivity(), "Please try after some time");
                break;
            case R.id.etDistrict:
                if (districtList.size() > 0)
                    spinnerDistrict.showSpinerDialog();
                else
                    ProjectUtils.showToast(getActivity(), "Please try after some time");
                break;
        }
    }

    public void showDistrict() {
        spinnerDistrict = new SpinnerDialog(getActivity(), districtList, getResources().getString(R.string.select_district), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerDistrict.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etDistrict.setText(item);
                district = id;
                inf_city.setVisibility(View.VISIBLE);
                til_pin_code.setVisibility(View.VISIBLE);

            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registration = (Registration) context;

    }
    public void openDatePickerDOB() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                } else {
                    ProjectUtils.showToast(getActivity(), "Cannot select future date");
                }

            }


        }, year-18, monthOfYear, dayOfMonth);
        calendar.set(year-18,monthOfYear,dayOfMonth);
        long value = calendar.getTimeInMillis();
        datePickerDialog.setTitle(getResources().getString(R.string.select_dob));

        datePickerDialog.getDatePicker().setMaxDate(value);
        datePickerDialog.show();
    }

}

