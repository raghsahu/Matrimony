package com.samyotech.matrimony.activity.dashboard;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.samyotech.matrimony.R;
import com.samyotech.matrimony.view.CustomButton;
import com.samyotech.matrimony.view.CustomTextView;
import com.samyotech.matrimony.view.CustomTextViewBold;

public class RateUs extends AppCompatActivity implements View.OnClickListener {
    private CustomTextViewBold buttonYes;
    private CustomTextView tvRemindMeLater;
    private ImageView iv_close_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);
        setUiAction();
    }

    public void setUiAction() {
        buttonYes = findViewById(R.id.buttonYes);
        tvRemindMeLater = findViewById(R.id.tvRemindMeLater);
        iv_close_screen = findViewById(R.id.iv_close_screen);

        buttonYes.setOnClickListener(this);
        tvRemindMeLater.setOnClickListener(this);
        iv_close_screen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonYes:
                rate();
                break;
            case R.id.tvRemindMeLater:
                finish();
                overridePendingTransition(R.anim.stay, R.anim.slide_down);
                break;
            case R.id.iv_close_screen:
                finish();
                overridePendingTransition(R.anim.stay, R.anim.slide_down);

                break;
        }
    }

    public void rate() {
        Uri uri = Uri.parse("market://details?id=" + RateUs.this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + RateUs.this.getPackageName())));
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }
}
