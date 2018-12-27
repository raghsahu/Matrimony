package com.samyotech.smmatrimony;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.samyotech.smmatrimony.activity.loginsignup.Login;
import com.samyotech.smmatrimony.adapter.AppIntroPagerAdapter;
import com.samyotech.smmatrimony.utils.ProjectUtils;
import com.samyotech.smmatrimony.view.CustomButton;
import com.samyotech.smmatrimony.view.CustomTextView;

import java.util.ArrayList;

public class AppIntro extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    ViewPager mViewPager;
    private AppIntroPagerAdapter mAdapter;
    private LinearLayout viewPagerCountDots;
    private int dotsCount;
    private ImageView[] dots;
    LinearLayout llSkip;
    CustomTextView tvSkip;
    private Context mContext;
    int[] mResources = {R.drawable.intro1, R.drawable.intro2, R.drawable.intro3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_app_intro);
        mContext = AppIntro.this;
        tvSkip = (CustomTextView) findViewById(R.id.tvSkip);
        llSkip = (LinearLayout) findViewById(R.id.llSkip);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPagerCountDots = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        mAdapter = new AppIntroPagerAdapter(AppIntro.this, mContext, mResources);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(this);
        setPageViewIndicator();


        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, Login.class);
                startActivity(in);
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);


            }
        });


    }

    private void setPageViewIndicator() {

        Log.d("###setPageViewIndicator", " : called");
        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(mContext);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    15,
                    15
            );

            params.setMargins(4, 0, 4, 0);

            final int presentPosition = i;
            dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mViewPager.setCurrentItem(presentPosition);
                    return true;
                }

            });


            viewPagerCountDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {
        Log.e("###onPageSelected, pos ", String.valueOf(position));
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

        if (position + 1 == dotsCount) {

        } else {

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void scrollPage(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        clickDone();

    }

    public void clickDone() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.title_dialog))
                .setMessage(getString(R.string.msg_dialog))
                .setPositiveButton(getString(R.string.yes_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.no_dialog), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
