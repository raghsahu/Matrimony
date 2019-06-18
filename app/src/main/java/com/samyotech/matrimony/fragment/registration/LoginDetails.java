package com.samyotech.matrimony.fragment.registration;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.loginsignup.Registration;
import com.samyotech.matrimony.view.CustomEditText;

public class LoginDetails extends Fragment {
    private View view;
    private Registration registration;
    public CustomEditText etName, etEmail, etPassword, etphone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login_details, container, false);
        setUiAction(view);
        return view;
    }

    public void setUiAction(View v) {
        etName = v.findViewById(R.id.etName);
        etEmail = v.findViewById(R.id.etEmail);
        etPassword = v.findViewById(R.id.etPassword);
        etphone = v.findViewById(R.id.etphone);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registration = (Registration) context;

    }
}
