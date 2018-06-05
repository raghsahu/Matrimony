package com.inspire.rkspmatrimony.activity.dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.inspire.rkspmatrimony.Models.LoginDTO;
import com.inspire.rkspmatrimony.Models.UserDTO;
import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.activity.myprofile.Profile;
import com.inspire.rkspmatrimony.fragment.EventsFrag;
import com.inspire.rkspmatrimony.fragment.HomeFrag;
import com.inspire.rkspmatrimony.fragment.InterestFrag;
import com.inspire.rkspmatrimony.fragment.SearchFrag;
import com.inspire.rkspmatrimony.fragment.AboutFrag;
import com.inspire.rkspmatrimony.fragment.SettingsFrag;
import com.inspire.rkspmatrimony.fragment.ShortlistedFrag;
import com.inspire.rkspmatrimony.fragment.VisitorsFrag;
import com.inspire.rkspmatrimony.https.HttpsRequest;
import com.inspire.rkspmatrimony.interfaces.Consts;
import com.inspire.rkspmatrimony.interfaces.Helper;
import com.inspire.rkspmatrimony.sharedprefrence.SharedPrefrence;
import com.inspire.rkspmatrimony.utils.CustomTypeFaceSpan;
import com.inspire.rkspmatrimony.utils.ProjectUtils;
import com.inspire.rkspmatrimony.view.CustomTextView;
import com.inspire.rkspmatrimony.view.CustomTextViewBold;
import com.inspire.rkspmatrimony.view.FontCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity {
    public static RelativeLayout rlheader;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView menuLeftIV;
    CircleImageView imgProfile;
    public static CustomTextViewBold headerNameTV;
    public static ImageView searchiv;
    public CustomTextViewBold ctvbName;
    public CustomTextView tvPercent;
    public static int navItemIndex = 0;
    public static final String TAG_MAIN = "main";
    public static final String TAG_SEARCH = "Search";
    public static final String TAG_SHORTLISTED = "Shortlisted";
    public static final String TAG_VISITORS = "Visitors";
    public static final String TAG_INTEREST = "interest";
    public static final String TAG_EVENTS = "events";
    public static final String TAG_ABOUT_US = "about_us";
    public static final String TAG_SETTINGS = "settings";

    public static String CURRENT_TAG = TAG_MAIN;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private Context mContext;
    InputMethodManager inputManager;
    private View contentView;
    private static final float END_SCALE = 0.8f;
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;
    private String TAG = Dashboard.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mContext = Dashboard.this;

        prefrence = SharedPrefrence.getInstance(mContext);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mHandler = new Handler();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        contentView = findViewById(R.id.content);
        menuLeftIV = (ImageView) findViewById(R.id.menuLeftIV);

        rlheader = (RelativeLayout) findViewById(R.id.rlheader);
        headerNameTV = (CustomTextViewBold) findViewById(R.id.headerNameTV);
        headerNameTV.setText(getResources().getString(R.string.app_name));
        searchiv = (ImageView) findViewById(R.id.searchiv);
        searchiv.setVisibility(View.GONE);

        navHeader = navigationView.getHeaderView(0);
        ctvbName = (CustomTextViewBold) navHeader.findViewById(R.id.ctvbName);
        tvPercent = (CustomTextView) navHeader.findViewById(R.id.tvPercent);

        imgProfile = (CircleImageView) navHeader.findViewById(R.id.img_profile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, Profile.class));
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
                drawer.closeDrawers();
            }
        });

        setUpNavigationView();


        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_MAIN;
            loadHomeFragment();
        }


        menuLeftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerOpen();
            }
        });


        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyCustomFont(subMenuItem);
                }
            }
            applyCustomFont(mi);
        }


        drawer.setScrimColor(Color.TRANSPARENT);
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                                     @Override
                                     public void onDrawerSlide(View drawerView, float slideOffset) {

                                         // Scale the View based on current slide offset
                                         final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                                         final float offsetScale = 1 - diffScaledOffset;
                                         contentView.setScaleX(offsetScale);
                                         contentView.setScaleY(offsetScale);

                                         // Translate the View, accounting for the scaled width
                                         final float xOffset = drawerView.getWidth() * slideOffset;
                                         final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                                         final float xTranslation = xOffset - xOffsetDiff;
                                         contentView.setTranslationX(xTranslation);
                                     }

                                     @Override
                                     public void onDrawerClosed(View drawerView) {
                                     }
                                 }
        );
    }

    public void drawerOpen() {

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    public void applyCustomFont(MenuItem mi) {
        Typeface customFont = FontCache.getTypeface("Ubuntu-Medium.ttf", Dashboard.this);
        SpannableString spannableString = new SpannableString(mi.getTitle());
        spannableString.setSpan(new CustomTypeFaceSpan("", customFont), 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(spannableString);
    }

    private void loadHomeFragment() {

        selectNavMenu();


        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        drawer.closeDrawers();

        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                HomeFrag homeFrag = new HomeFrag();
                return homeFrag;
            case 1:
                SearchFrag searchFrag = new SearchFrag();
                return searchFrag;
            case 2:
                ShortlistedFrag shortlistedFrag = new ShortlistedFrag();
                return shortlistedFrag;
            case 3:
                VisitorsFrag visitorsFrag = new VisitorsFrag();
                return visitorsFrag;
            case 4:
                InterestFrag interestFrag = new InterestFrag();
                return interestFrag;
            case 5:
                EventsFrag eventsFrag = new EventsFrag();
                return eventsFrag;
            case 6:
                AboutFrag aboutFrag = new AboutFrag();
                return aboutFrag;
            case 7:
                SettingsFrag settingsFrag = new SettingsFrag();
                return settingsFrag;
            default:
                return new HomeFrag();
        }
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_MAIN;
                        break;
                    case R.id.nav_search:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_SEARCH;
                        break;
                    case R.id.nav_shortlisted:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_SHORTLISTED;
                        break;
                    case R.id.nav_visitor:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_VISITORS;
                        break;
                    case R.id.nav_interest:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_INTEREST;
                        break;
                    case R.id.nav_events:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_EVENTS;
                        break;
                    case R.id.nav_about_us:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_ABOUT_US;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    default:
                        navItemIndex = 0;

                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {

            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_MAIN;
                loadHomeFragment();
                return;
            }
        }

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


    @Override
    protected void onResume() {
        super.onResume();
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        ctvbName.setText(loginDTO.getData().getName());
        tvPercent.setText(loginDTO.getData().getProfile_completion() + " %");
        Glide.with(mContext).
                load(Consts.IMAGE_URL + loginDTO.getData().getAvatar_medium())
                .placeholder(R.drawable.default_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

    /*    if (loginDTO.getData().getProfile_completion()>80) {

        }else {
            showDialog();
        }*/

    }


/*
    public void showDialog(){

        ProjectUtils.showSweetDialog(mContext, "Profile Full Fill", getResources().getString(R.string.profile_msg), "Ok", "Cancel", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent = new Intent(mContext, Profile.class);
                startActivity(intent);
            }
        }, new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });

    }
*/
}
