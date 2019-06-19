package ics.hindu.matrimony.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ics.hindu.matrimony.other.AppIntro;
import ics.hindu.matrimony.Models.LanguageDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.activity.dashboard.Dashboard;
import ics.hindu.matrimony.adapter.AdapterLanguage;
import ics.hindu.matrimony.interfaces.Consts;
import ics.hindu.matrimony.network.NetworkManager;
import ics.hindu.matrimony.sharedprefrence.SharedPrefrence;
import ics.hindu.matrimony.utils.ProjectUtils;
import ics.hindu.matrimony.view.CustomTextView;

import java.util.ArrayList;
import java.util.Locale;

public class LanguageSelection extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = LanguageSelection.class.getSimpleName();
    private CustomTextView CTVskip;
    private SharedPrefrence sharedPrefrence;
    private Context mContext;
    private static String SKIP = "2";
    private AdapterLanguage adapterLanguage;
    private ArrayList<LanguageDTO> languageDTOList;
    private RecyclerView rvLanguage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        mContext = LanguageSelection.this;
        sharedPrefrence = SharedPrefrence.getInstance(mContext);
        init();


    }

    private void init() {
        rvLanguage = (RecyclerView) findViewById(R.id.rvLanguage);
        CTVskip = findViewById(R.id.CTVskip);
        CTVskip.setOnClickListener(this);
        shoLAnguage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CTVskip:

                if (NetworkManager.isConnectToInternet(mContext)) {
                    if (sharedPrefrence.getBooleanValue(Consts.IS_REGISTERED)) {
                        startActivity(new Intent(mContext, Dashboard.class));
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                    } else {
                        startActivity(new Intent(mContext, AppIntro.class));
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                    language("en");
                    finish();

                } else {
                    ProjectUtils.InternetAlertDialog(LanguageSelection.this, getString(R.string.error_connecting), getString(R.string.internet_concation));

                }

                break;
        }
    }


    private void shoLAnguage() {
        languageDTOList = new ArrayList<>();
        languageDTOList.add(new LanguageDTO("English"));
        languageDTOList.add(new LanguageDTO(getString(R.string.hindi)));
        GridLayoutManager gLayout = new GridLayoutManager(mContext, 2);
        adapterLanguage = new AdapterLanguage(languageDTOList, LanguageSelection.this);
        rvLanguage.setLayoutManager(gLayout);
        rvLanguage.setHasFixedSize(true);
        rvLanguage.setLayoutManager(gLayout);
        rvLanguage.setAdapter(adapterLanguage);
    }

    @Override
    public void onBackPressed() {
        finish();
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
