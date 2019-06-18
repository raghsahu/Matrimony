package com.samyotech.matrimony.activity.dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.samyotech.matrimony.Models.Friend;
import com.samyotech.matrimony.Models.ListFriend;
import com.samyotech.matrimony.Models.LoginDTO;
import com.samyotech.matrimony.Models.SubscriptionDto;
import com.samyotech.matrimony.Models.UserFire;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.ChatActivity;
import com.samyotech.matrimony.activity.myprofile.Profile;
import com.samyotech.matrimony.activity.subscription.MemberShipActivity;
import com.samyotech.matrimony.database.FriendDB;
import com.samyotech.matrimony.fragment.Chats;
import com.samyotech.matrimony.fragment.EventsFrag;
import com.samyotech.matrimony.fragment.HomeFrag;
import com.samyotech.matrimony.fragment.InterestFrag;
import com.samyotech.matrimony.fragment.SearchFrag;
import com.samyotech.matrimony.fragment.AboutFrag;
import com.samyotech.matrimony.fragment.SettingsFrag;
import com.samyotech.matrimony.fragment.ShortlistedFrag;
import com.samyotech.matrimony.fragment.VisitorsFrag;
import com.samyotech.matrimony.https.HttpsRequest;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.interfaces.Helper;
import com.samyotech.matrimony.network.NetworkManager;
import com.samyotech.matrimony.service.ServiceUtils;
import com.samyotech.matrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.matrimony.utils.CustomTypeFaceSpan;
import com.samyotech.matrimony.utils.ProjectUtils;
import com.samyotech.matrimony.utils.StaticConfig;
import com.samyotech.matrimony.view.CustomTextViewBold;
import com.samyotech.matrimony.view.FontCache;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
    public CustomTextViewBold ctvbName, tvSubscription;
    public static int navItemIndex = 0;
    public static final String TAG_MAIN = "main";
    public static final String TAG_SEARCH = "Search";
    public static final String TAG_SHORTLISTED = "Shortlisted";
    public static final String TAG_VISITORS = "Visitors";
    public static final String TAG_INTEREST = "interest";
    public static final String TAG_EVENTS = "events";
    public static final String TAG_CHATS = "chats";
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
    HashMap<String, String> parmsSubs = new HashMap<>();
    SubscriptionDto subscriptionDto;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private boolean firstTimeAccess;

    private DatabaseReference userDB;
    private UserFire myAccount;

    private ArrayList<String> listFriendID = null;
    private CountDownTimer detectFriendOnline;
    private ListFriend dataListFriend = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mContext = Dashboard.this;

        firstTimeAccess = true;
        initFirebase();
        checkUserFirebase();
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
        tvSubscription = (CustomTextViewBold) navHeader.findViewById(R.id.tvSubscription);

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
        tvSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prefrence.getBooleanValue(Consts.IS_SUBSCRIBE)) {
                    ProjectUtils.showToast(mContext, getResources().getString(R.string.already_subscribe_user));
                } else {
                    startActivity(new Intent(mContext, MemberShipActivity.class));
                    overridePendingTransition(R.anim.anim_slide_in_left,
                            R.anim.anim_slide_out_left);
                    drawer.closeDrawers();
                }

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

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
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
                Chats chats = new Chats();
                return chats;
            case 7:
                AboutFrag aboutFrag = new AboutFrag();
                return aboutFrag;
            case 8:
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
                    case R.id.nav_chat:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_CHATS;
                        break;
                    case R.id.nav_about_us:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_ABOUT_US;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 8;
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
        parmsSubs.put(Consts.USER_ID, loginDTO.getData().getId());
        ctvbName.setText(loginDTO.getData().getName());
        Glide.with(mContext).
                load(Consts.IMAGE_URL + loginDTO.getData().getAvatar_medium())
                .placeholder(R.drawable.default_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        uploadImg(Consts.IMAGE_URL + loginDTO.getData().getAvatar_medium());
        if (NetworkManager.isConnectToInternet(mContext)) {
            getMySubscription();
        } else {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
        }

    }

    public void getMySubscription() {
        // ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.GET_MY_SUBSCRIPTION_API, parmsSubs, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    try {
                        subscriptionDto = new Gson().fromJson(response.getJSONObject("data").toString(), SubscriptionDto.class);
                        prefrence.setSubscription(subscriptionDto, Consts.SUBSCRIPTION_DTO);
                        prefrence.setBooleanValue(Consts.IS_SUBSCRIBE, true);
                        tvSubscription.setText(getResources().getString(R.string.subscribed));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    tvSubscription.setText(getResources().getString(R.string.upgrade_now));
                    prefrence.setBooleanValue(Consts.IS_SUBSCRIBE, false);
                }
            }
        });
    }

    private void initFirebase() {
        //Khoi tao thanh phan de dang nhap, dang ky
        userDB = FirebaseDatabase.getInstance().getReference().child("user").child(StaticConfig.UID);
        userDB.addListenerForSingleValueEvent(userListener);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    StaticConfig.UID = user.getUid();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if (firstTimeAccess) {
                        saveUserInfo();
                    }
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    signIn(loginDTO.getData().getEmail(), "sam@123");
                }
                firstTimeAccess = false;
            }
        };

    }

    void saveUserInfo() {
        FirebaseDatabase.getInstance().getReference().child("user/" + StaticConfig.UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap hashUser = (HashMap) dataSnapshot.getValue();
                UserFire userInfo = new UserFire();
                userInfo.name = (String) hashUser.get("name");
                userInfo.email = (String) hashUser.get("email");
                userInfo.avata = (String) hashUser.get("avata");
                prefrence.saveUserInfo(userInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Dashboard.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            saveUserInfo();
                        } else {

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    public void callLog(String emailID) {
        findIDEmail(emailID);

    }


    /**
     * TIm id cua email tren server
     *
     * @param em
     */
    private void findIDEmail(final String em) {
        FirebaseDatabase.getInstance().getReference().child("user").orderByChild("email").equalTo(em).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    //email not found
                } else {
                    String id = ((HashMap) dataSnapshot.getValue()).keySet().iterator().next().toString();
                    if (id.equals(StaticConfig.UID)) {
                    } else {
                        HashMap userMap = (HashMap) ((HashMap) dataSnapshot.getValue()).get(id);
                        Friend user = new Friend();
                        user.name = (String) userMap.get("name");
                        user.email = (String) userMap.get("email");
                        user.avata = (String) userMap.get("avata");
                        user.id = id;
                        user.idRoom = id.compareTo(StaticConfig.UID) > 0 ? (StaticConfig.UID + id).hashCode() + "" : "" + (id + StaticConfig.UID).hashCode();
                        checkBeforAddFriend(id, user, em);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Lay danh sach friend cua một UID
     */
    private void checkBeforAddFriend(final String idFriend, Friend userInfo, String emaill) {


        //Check xem da ton tai id trong danh sach id chua
        if (listFriendID.contains(idFriend)) {

        } else {
            addFriend(idFriend, true);
            listFriendID.add(idFriend);
            dataListFriend.getListFriend().add(userInfo);
            FriendDB.getInstance(mContext).addFriend(userInfo);
        }
        perfromclick(emaill);
        ;

    }

    /**
     * Add friend
     *
     * @param idFriend
     */
    private void addFriend(final String idFriend, boolean isIdFriend) {
        if (idFriend != null) {
            if (isIdFriend) {
                FirebaseDatabase.getInstance().getReference().child("friend/" + StaticConfig.UID).push().setValue(idFriend)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    addFriend(idFriend, false);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            } else {
                FirebaseDatabase.getInstance().getReference().child("friend/" + idFriend).push().setValue(StaticConfig.UID).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            addFriend(null, false);
                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
            }
        } else {
        }
    }


    private void getListFriendUId() {
        FirebaseDatabase.getInstance().getReference().child("friend/" + StaticConfig.UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    HashMap mapRecord = (HashMap) dataSnapshot.getValue();
                    Iterator listKey = mapRecord.keySet().iterator();
                    while (listKey.hasNext()) {
                        String key = listKey.next().toString();
                        listFriendID.add(mapRecord.get(key).toString());
                    }
                    getAllFriendInfo(0);
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getAllFriendInfo(final int index) {
        if (index == listFriendID.size()) {

            detectFriendOnline.start();
        } else {
            final String id = listFriendID.get(index);
            FirebaseDatabase.getInstance().getReference().child("user/" + id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        Friend user = new Friend();
                        HashMap mapUserInfo = (HashMap) dataSnapshot.getValue();
                        user.name = (String) mapUserInfo.get("name");
                        user.email = (String) mapUserInfo.get("email");
                        user.avata = (String) mapUserInfo.get("avata");
                        user.id = id;
                        user.idRoom = id.compareTo(StaticConfig.UID) > 0 ? (StaticConfig.UID + id).hashCode() + "" : "" + (id + StaticConfig.UID).hashCode();
                        dataListFriend.getListFriend().add(user);
                        FriendDB.getInstance(mContext).addFriend(user);
                    }
                    getAllFriendInfo(index + 1);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void perfromclick(String em) {

        Friend listFriend = new Friend();
        listFriend = dataListFriend.getByEmail(em);

        if (listFriend.email.equalsIgnoreCase(em)) {
            final String name = listFriend.name;
            final String id = listFriend.id;
            final String idRoom = listFriend.idRoom;
            final String avata = listFriend.avata;

            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.putExtra(StaticConfig.INTENT_KEY_CHAT_FRIEND, name);
            ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
            idFriend.add(id);
            intent.putCharSequenceArrayListExtra(StaticConfig.INTENT_KEY_CHAT_ID, idFriend);
            intent.putExtra(StaticConfig.INTENT_KEY_CHAT_ROOM_ID, idRoom);
            ChatActivity.bitmapAvataFriend = new HashMap<>();
            if (!avata.equals(StaticConfig.STR_DEFAULT_BASE64)) {

                ChatActivity.bitmapAvataFriend.put(id, avata);
            } else {
                ChatActivity.bitmapAvataFriend.put(id, "");
            }
            startActivity(intent);
        }


    }


    public void checkUserFirebase() {
        detectFriendOnline = new CountDownTimer(System.currentTimeMillis(), StaticConfig.TIME_TO_REFRESH) {
            @Override
            public void onTick(long l) {
                ServiceUtils.updateFriendStatus(mContext, dataListFriend);
                ServiceUtils.updateUserStatus(mContext);
            }

            @Override
            public void onFinish() {

            }
        };
        if (dataListFriend == null) {
            dataListFriend = FriendDB.getInstance(mContext).getListFriend();
            if (dataListFriend.getListFriend().size() > 0) {
                listFriendID = new ArrayList<>();
                for (Friend friend : dataListFriend.getListFriend()) {
                    listFriendID.add(friend.id);
                }
                detectFriendOnline.start();
            }
        }

        if (listFriendID == null) {
            listFriendID = new ArrayList<>();
            getListFriendUId();
        }
    }

    private ValueEventListener userListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            //Lấy thông tin của user về và cập nhật lên giao diện
            myAccount = dataSnapshot.getValue(UserFire.class);

            prefrence.saveUserInfo(myAccount);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            //Có lỗi xảy ra, không lấy đc dữ liệu
            Log.e(TAG, "loadPost:onCancelled", databaseError.toException());
        }
    };

    public void uploadImg(String url) {
        userDB.child("avata").setValue(url)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            prefrence.saveUserInfo(myAccount);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Update Avatar", "failed");
                    }
                });

    }

}
