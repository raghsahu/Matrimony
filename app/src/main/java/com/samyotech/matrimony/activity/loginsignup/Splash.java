package com.samyotech.matrimony.activity.loginsignup;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;
import com.samyotech.matrimony.other.AppIntro;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.LanguageSelection;
import com.samyotech.matrimony.activity.dashboard.Dashboard;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.network.NetworkManager;
import com.samyotech.matrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.matrimony.utils.ProjectUtils;

import io.fabric.sdk.android.Fabric;

import java.util.Locale;


public class Splash extends AppCompatActivity {

    private Handler handler = new Handler();
    private Context mContext;
    private static int SPLASH_TIME_OUT = 3000;
    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1003;
    private String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CALL_PRIVILEGED, Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};
    private boolean cameraAccepted, storageAccepted, accessNetState, call_privilage, callPhone, fineLoc, corasLoc, readSMS, receiveSMS;
    public SharedPrefrence prefference;
    SharedPreferences languageDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_splash);

        mContext = Splash.this;
        prefference = SharedPrefrence.getInstance(mContext);
        languageDetails = Splash.this.getSharedPreferences(Consts.LANGUAGE_PREF, MODE_PRIVATE);
        if (!hasPermissions(Splash.this, permissions)) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);

        } else {
            handler.postDelayed(mTask, SPLASH_TIME_OUT);
        }
        Log.e("<---Splash TAG", "" + prefference.getBooleanValue(Consts.IS_REGISTERED));
    }

    Runnable mTask = new Runnable() {
        @Override
        public void run() {
            if (languageDetails.getBoolean(Consts.LANGUAGE_SELECTION, false)) {
                language(languageDetails.getString(Consts.SELECTED_LANGUAGE, ""));
                if (NetworkManager.isConnectToInternet(mContext)) {

                    if (prefference.getBooleanValue(Consts.IS_REGISTERED)) {
                        startActivity(new Intent(mContext, Dashboard.class));
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();

                    } else {
                        startActivity(new Intent(mContext, AppIntro.class));
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();

                    }


                } else {
                    ProjectUtils.InternetAlertDialog(Splash.this, getString(R.string.error_connecting), getString(R.string.internet_concation));

                }
            } else {
                startActivity(new Intent(mContext, LanguageSelection.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }


        }

    };
//*******************************************************

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                try {

                    cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    prefference.setBooleanValue(Consts.CAMERA_ACCEPTED, cameraAccepted);

                    storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    prefference.setBooleanValue(Consts.STORAGE_ACCEPTED, storageAccepted);

                    accessNetState = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    prefference.setBooleanValue(Consts.MODIFY_AUDIO_ACCEPTED, accessNetState);

                    call_privilage = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    prefference.setBooleanValue(Consts.CALL_PRIVILAGE, call_privilage);

                    callPhone = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    prefference.setBooleanValue(Consts.CALL_PHONE, callPhone);

                    fineLoc = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    prefference.setBooleanValue(Consts.FINE_LOC, fineLoc);

                    corasLoc = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    prefference.setBooleanValue(Consts.CORAS_LOC, corasLoc);

                    readSMS = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    prefference.setBooleanValue(Consts.READ_SMS, readSMS);

                    receiveSMS = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    prefference.setBooleanValue(Consts.RECEIVE_SMS, receiveSMS);
                    handler.postDelayed(mTask, 3000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public void language(String language) {
        String languageToLoad = language; // your language

        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        mContext.getResources().updateConfiguration(config,
                mContext.getResources().getDisplayMetrics());


    }

}
