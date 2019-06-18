package com.samyotech.matrimony.activity.editprofile;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.samyotech.matrimony.Models.LoginDTO;
import com.samyotech.matrimony.Models.UserDTO;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.SysApplication;
import com.samyotech.matrimony.https.HttpsRequest;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.interfaces.Helper;
import com.samyotech.matrimony.interfaces.OnSpinerItemClick;
import com.samyotech.matrimony.network.NetworkManager;
import com.samyotech.matrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.matrimony.utils.ProjectUtils;
import com.samyotech.matrimony.utils.SpinnerDialog;
import com.samyotech.matrimony.view.CustomButton;
import com.samyotech.matrimony.view.CustomEditText;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LifeStyleActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = LifeStyleActivity.class.getSimpleName();
    private Context mContext;
    private CustomEditText etDietary, etDrink, etSmoking, etLanguage, etHobbies, etInterests;
    private CustomButton btnSave;
    private LinearLayout llBack;
    private SysApplication sysApplication;
    private SpinnerDialog spinnerDietary, spinnerDrink, spinnerSmoking, spinnerHobbies, spinnerInterests, spinnerLanguage;
    private RelativeLayout RRsncbar;
    private HashMap<String, String> parms = new HashMap<>();
    private SharedPrefrence prefrence;
    private UserDTO userDTO;
    private LoginDTO loginDTO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_style);
        mContext = LifeStyleActivity.this;
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
        setUiAction();
    }

    public void setUiAction() {
        RRsncbar = findViewById(R.id.RRsncbar);
        etDietary = findViewById(R.id.etDietary);
        etDrink = findViewById(R.id.etDrink);
        etSmoking = findViewById(R.id.etSmoking);
        etLanguage = findViewById(R.id.etLanguage);
        etHobbies = findViewById(R.id.etHobbies);
        etInterests = findViewById(R.id.etInterests);
        btnSave = findViewById(R.id.btnSave);
        llBack = findViewById(R.id.llBack);

        llBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        etLanguage.setOnClickListener(this);
        etInterests.setOnClickListener(this);
        etHobbies.setOnClickListener(this);
        etDietary.setOnClickListener(this);
        etDrink.setOnClickListener(this);
        etSmoking.setOnClickListener(this);




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
                if (!validation(etDietary, getResources().getString(R.string.val_dietary))) {
                    return;
                } else if (!validation(etDrink, getResources().getString(R.string.val_drink))) {
                    return;
                } else if (!validation(etSmoking, getResources().getString(R.string.val_smoking))) {
                    return;
                } else if (!validation(etLanguage, getResources().getString(R.string.val_language))) {
                    return;
                } else if (!validation(etHobbies, getResources().getString(R.string.val_hobbies))) {
                    return;
                } else if (!validation(etInterests, getResources().getString(R.string.val_interest))) {
                    return;
                } else {
                    if (NetworkManager.isConnectToInternet(mContext)) {
                        request();

                    } else {
                        ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
                    }
                }
                break;
            case R.id.etLanguage:
                spinnerLanguage.showSpinerDialogMultiple();
                break;
            case R.id.etHobbies:
                spinnerHobbies.showSpinerDialogMultiple();
                break;
            case R.id.etInterests:
                spinnerInterests.showSpinerDialogMultiple();
                break;
            case R.id.etDietary:
                spinnerDietary.showSpinerDialog();
                break;
            case R.id.etSmoking:
                spinnerSmoking.showSpinerDialog();
                break;
            case R.id.etDrink:
                spinnerDrink.showSpinerDialog();
                break;
        }
    }


    private String splitName(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        String replace = str.replace(" ", "").replace(",", ", ");
        if (replace.contains(",")) {
            if (replace.length() > 35) {
                String[] split = str.split(",");
                int length = split.length;
                int length2 = split.length;
                int i = 0;
                int i2 = 0;
                while (i < length2) {
                    String str2 = split[i];
                    if (str2.length() > 30 && stringBuilder.length() == 0) {
                        i = i2 + 1;
                        StringBuilder append = new StringBuilder().append(str2.substring(0, 30));
                        if (length - i > 0) {
                            replace = " + " + (length - i) + " more";
                        } else {
                            replace = "...";
                        }
                        return append.append(replace).toString();
                    } else if (stringBuilder.length() + str2.length() < 30) {
                        if (stringBuilder.length() == 0) {
                            stringBuilder.append(str2);
                        } else {
                            stringBuilder.append(", ").append(str2);
                        }
                        i2++;
                        i++;
                    } else {
                        return stringBuilder.toString() + (length - i2 > 0 ? " + " + (length - i2) + " more" : "");
                    }
                }
            }
            return str.replace(",", ", ").toString();
        } else if (str.length() > 38) {
            return str.substring(0, 37) + "...";
        } else {
            return str;
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

        etDietary.setText(userDTO.getDietary());
        etDrink.setText(userDTO.getDrinking());
        etSmoking.setText(userDTO.getSmoking());
        etLanguage.setText(userDTO.getLanguage());
        etHobbies.setText(userDTO.getHobbies());
        etInterests.setText(userDTO.getInterests());









        for (int j = 0; j < sysApplication.getDietary().size(); j++) {
            if (sysApplication.getDietary().get(j).getName().equalsIgnoreCase(userDTO.getDietary())) {
                sysApplication.getDietary().get(j).setSelected(true);
            }
        }
        spinnerDietary = new SpinnerDialog((Activity) mContext, sysApplication.getDietary(), getResources().getString(R.string.select_dietary), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerDietary.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etDietary.setText(item);
                parms.put(Consts.DIETARY, item);
            }
        });


        for (int j = 0; j < sysApplication.getHabitsDrink().size(); j++) {
            if (sysApplication.getHabitsDrink().get(j).getName().equalsIgnoreCase(userDTO.getDrinking())) {
                sysApplication.getHabitsDrink().get(j).setSelected(true);
            }
        }
        spinnerDrink = new SpinnerDialog((Activity) mContext, sysApplication.getHabitsDrink(), getResources().getString(R.string.select_drink), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerDrink.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etDrink.setText(item);
                parms.put(Consts.DRINKING, item);

            }
        });



        for (int j = 0; j < sysApplication.getHabitsSmok().size(); j++) {
            if (sysApplication.getHabitsSmok().get(j).getName().equalsIgnoreCase(userDTO.getSmoking())) {
                sysApplication.getHabitsSmok().get(j).setSelected(true);
            }
        }
        spinnerSmoking = new SpinnerDialog((Activity) mContext, sysApplication.getHabitsSmok(), getResources().getString(R.string.select_smoking), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerSmoking.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etSmoking.setText(item);
                parms.put(Consts.SMOKING, item);

            }
        });



        List<String> items = Arrays.asList(userDTO.getHobbies().split("\\s*,\\s*"));
        for (int i = 0; i < items.size(); i++) {
            items.get(i);
            for (int j = 0; j < sysApplication.getHobbiesList().size(); j++) {
                if (sysApplication.getHobbiesList().get(j).getName().equalsIgnoreCase(items.get(i))) {
                    sysApplication.getHobbiesList().get(j).setSelected(true);
                }
            }

        }
        spinnerHobbies = new SpinnerDialog((Activity) mContext, sysApplication.getHobbiesList(), getResources().getString(R.string.select_hobbies), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerHobbies.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etHobbies.setText(splitName(item));
                parms.put(Consts.HOBBIES, item);

            }
        });

        List<String> interests = Arrays.asList(userDTO.getInterests().split("\\s*,\\s*"));
        for (int i = 0; i < interests.size(); i++) {
            interests.get(i);
            for (int j = 0; j < sysApplication.getInterestsList().size(); j++) {
                if (sysApplication.getInterestsList().get(j).getName().equalsIgnoreCase(interests.get(i))) {
                    sysApplication.getInterestsList().get(j).setSelected(true);
                }
            }

        }
        spinnerInterests = new SpinnerDialog((Activity) mContext, sysApplication.getInterestsList(), getResources().getString(R.string.select_intersts), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerInterests.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etInterests.setText(splitName(item));
                parms.put(Consts.INTERESTS, item);
            }
        });


        List<String> language = Arrays.asList(userDTO.getLanguage().split("\\s*,\\s*"));
        for (int i = 0; i < language.size(); i++) {
            language.get(i);
            for (int j = 0; j < sysApplication.getLanguage().size(); j++) {
                if (sysApplication.getLanguage().get(j).getName().equalsIgnoreCase(language.get(i))) {
                    sysApplication.getLanguage().get(j).setSelected(true);
                }
            }

        }
        spinnerLanguage = new SpinnerDialog((Activity) mContext, sysApplication.getLanguage(), getResources().getString(R.string.select_language), R.style.DialogAnimations_SmileWindow, getResources().getString(R.string.close));// With 	Animation
        spinnerLanguage.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, String id, int position) {
                etLanguage.setText(splitName(item));
                parms.put(Consts.LANGUAGE, item);

            }
        });
    }

}
