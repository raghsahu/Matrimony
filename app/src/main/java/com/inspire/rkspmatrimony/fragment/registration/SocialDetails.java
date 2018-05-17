package com.inspire.rkspmatrimony.fragment.registration;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.activity.loginsignup.Registration;
import com.inspire.rkspmatrimony.interfaces.OnSpinerItemClick;
import com.inspire.rkspmatrimony.utils.ProjectUtils;
import com.inspire.rkspmatrimony.utils.SpinnerDialog;
import com.inspire.rkspmatrimony.view.CustomEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SocialDetails extends Fragment implements View.OnClickListener {
    private View view;
    private Registration registration;
    public CustomEditText etMaritial, etGotra, etManglik, etBirthTime, etBirthPlace,etGotraNanihal;
    SpinnerDialog spinnerMaritial, spinnerManglik;
    public String marital = "", manglik = "";
    private ProjectUtils.CustomTimePickerDialog dialog;
    private Calendar myCalendar = Calendar.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_social_details, container, false);
        setUiAction(view);
        return view;
    }

    public void setUiAction(View v) {
        etMaritial = v.findViewById(R.id.etMaritial);
        etGotra = v.findViewById(R.id.etGotra);
        etGotraNanihal = v.findViewById(R.id.etGotraNanihal);
        etManglik = v.findViewById(R.id.etManglik);
        etBirthTime = v.findViewById(R.id.etBirthTime);
        etBirthPlace = v.findViewById(R.id.etBirthPlace);

        etMaritial.setOnClickListener(this);
        etManglik.setOnClickListener(this);
        etBirthTime.setOnClickListener(this);

        spinnerMaritial = new SpinnerDialog(getActivity(), registration.sysApplication.getMaritalList(), getResources().getString(R.string.select_maritial_status), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation


        spinnerMaritial.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id,int position) {
                etMaritial.setText(item);
                marital =id;
            }
        });
        spinnerManglik = new SpinnerDialog(getActivity(), registration.sysApplication.getManglikList(), getResources().getString(R.string.select_manglik), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation


        spinnerManglik.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item,String id, int position) {
                etManglik.setText(item);
                manglik =id;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etMaritial:
                spinnerMaritial.showSpinerDialog();
                break;
            case R.id.etManglik:
                spinnerManglik.showSpinerDialog();
                break;
            case R.id.etBirthTime:

                    dialog = new ProjectUtils.CustomTimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            myCalendar.set(Calendar.MINUTE, minute);

                            SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
                            etBirthTime.setText(sdf1.format(myCalendar.getTime()));
                        }
                    },
                            myCalendar.getTime().getHours(), myCalendar.getTime().getMinutes(), false);
                    dialog.show();

                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registration = (Registration) context;

    }

}
