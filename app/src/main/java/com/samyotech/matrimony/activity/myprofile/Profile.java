package com.samyotech.matrimony.activity.myprofile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samyotech.matrimony.Models.ImageDTO;
import com.samyotech.matrimony.Models.LoginDTO;
import com.samyotech.matrimony.Models.UserDTO;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.editprofile.AboutFamilyActivity;
import com.samyotech.matrimony.activity.editprofile.AboutMeActivity;
import com.samyotech.matrimony.activity.editprofile.BasicDetailsActivity;
import com.samyotech.matrimony.activity.editprofile.CriticalFields;
import com.samyotech.matrimony.activity.editprofile.ImportantDetailsActivity;
import com.samyotech.matrimony.activity.editprofile.LifeStyleActivity;
import com.samyotech.matrimony.activity.imageselection.ImageshowActivity;
import com.samyotech.matrimony.activity.imageselection.MainActivity;
import com.samyotech.matrimony.https.HttpsRequest;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.interfaces.Helper;
import com.samyotech.matrimony.network.NetworkManager;
import com.samyotech.matrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.matrimony.utils.ProjectUtils;
import com.samyotech.matrimony.view.CustomTextView;
import com.samyotech.matrimony.view.CustomTextViewBold;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private String TAG = Profile.class.getSimpleName();
    private Context mContext;
    private CustomTextViewBold tvName;
    private ImageView ivEditSelf, ivEditAbout, ivEditImportant, ivEditCritical, ivEditLifestyle, ivEditFamily;
    private CustomTextView tvHeight, tvIncome, tvGotra, tvCity, tvDistrict, tvState, tvDob, tvMaritalStatus, tvAadhaar;
    private CustomTextView tvAbout, tvBodyType, tvBlood, tvCase, tvEducation, tvOccupation, tvWorkArea, tvBirthTime;
    private CustomTextView tvBirthCity, tvLife, tvLanguage, tvInterests, tvHobbies, tvFamilyAbout, tvFamilyBackground;
    private CustomTextView tvFamilyIncome, tvFatherOccupation, tvMotherOccupation, tvBro, tvSis, tvFamilyBased;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar;
    private LinearLayout back;
    private CircleImageView ivImage, ivCamera;
    private CustomTextView tvAddImage, tvImageCount;
    private RelativeLayout rlRed;
    private ArrayList<ImageDTO> imageDatalist;
    private LinearLayout imageLyout;
    private SharedPrefrence prefrence;
    private UserDTO userDTO;
    private LoginDTO loginDTO;
    private HashMap<String, String> parms = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mContext = Profile.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);

        parms.put(Consts.TOKEN, loginDTO.getAccess_token());
        parms.put(Consts.USER_ID, loginDTO.getUser_id());
        setUiaction();
    }

    public void setUiaction() {

        collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        toolbar = findViewById(R.id.toolbar);

        collapsing_toolbar.setExpandedTitleColor(Color.TRANSPARENT);
        collapsing_toolbar.setCollapsedTitleTextColor(Color.WHITE);
        collapsing_toolbar.setTitle("Profile");
        back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(this);
        tvName = findViewById(R.id.tvName);

        imageLyout = findViewById(R.id.imageLyout);
        imageLyout.setOnClickListener(this);
        ivCamera = findViewById(R.id.ivCamera);
        rlRed = findViewById(R.id.rlRed);
        tvImageCount = findViewById(R.id.tvImageCount);
        tvAddImage = findViewById(R.id.tvAddImage);
        ivImage = findViewById(R.id.ivImage);
        ivEditSelf = findViewById(R.id.ivEditSelf);
        ivEditAbout = findViewById(R.id.ivEditAbout);
        ivEditImportant = findViewById(R.id.ivEditImportant);
        ivEditCritical = findViewById(R.id.ivEditCritical);
        ivEditLifestyle = findViewById(R.id.ivEditLifestyle);
        ivEditFamily = findViewById(R.id.ivEditFamily);

        ivEditSelf.setOnClickListener(this);
        ivEditAbout.setOnClickListener(this);
        ivEditImportant.setOnClickListener(this);
        ivEditCritical.setOnClickListener(this);
        ivEditLifestyle.setOnClickListener(this);
        ivEditFamily.setOnClickListener(this);
        tvAddImage.setOnClickListener(this);

        tvDistrict = findViewById(R.id.tvDistrict);
        tvState = findViewById(R.id.tvState);
        tvAadhaar = findViewById(R.id.tvAadhaar);
        tvHeight = findViewById(R.id.tvHeight);
        tvBlood = findViewById(R.id.tvBlood);
        tvCase = findViewById(R.id.tvCase);
        tvWorkArea = findViewById(R.id.tvWorkArea);

        tvOccupation = findViewById(R.id.tvOccupation);
        tvEducation = findViewById(R.id.tvEducation);
        tvGotra = findViewById(R.id.tvGotra);
        tvIncome = findViewById(R.id.tvIncome);
        tvCity = findViewById(R.id.tvCity);
        tvMaritalStatus = findViewById(R.id.tvMaritalStatus);
        tvAbout = findViewById(R.id.tvAbout);
        tvBodyType = findViewById(R.id.tvBodyType);
        tvDob = findViewById(R.id.tvDob);
        tvBirthTime = findViewById(R.id.tvBirthTime);
        tvBirthCity = findViewById(R.id.tvBirthCity);
        tvLife = findViewById(R.id.tvLife);
        tvLanguage = findViewById(R.id.tvLanguage);
        tvInterests = findViewById(R.id.tvInterests);
        tvHobbies = findViewById(R.id.tvHobbies);
        tvFamilyAbout = findViewById(R.id.tvFamilyAbout);
        tvFamilyBackground = findViewById(R.id.tvFamilyBackground);
        tvFamilyIncome = findViewById(R.id.tvFamilyIncome);
        tvFatherOccupation = findViewById(R.id.tvFatherOccupation);
        tvMotherOccupation = findViewById(R.id.tvMotherOccupation);
        tvBro = findViewById(R.id.tvBro);
        tvSis = findViewById(R.id.tvSis);
        tvFamilyBased = findViewById(R.id.tvFamilyBased);




    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkManager.isConnectToInternet(mContext)) {
            getImages();

        } else {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
        }
    }

    public void showData() {
        tvName.setText(userDTO.getName());

        tvOccupation.setText(userDTO.getOccupation());
        tvEducation.setText(userDTO.getQualification());
        tvGotra.setText(userDTO.getGotra());
        tvIncome.setText(userDTO.getIncome());
        tvCity.setText(userDTO.getCity());
        tvMaritalStatus.setText(userDTO.getMarital_status());
        tvAbout.setText(userDTO.getAbout_me());
        tvBodyType.setText(userDTO.getBody_type() + "," + userDTO.getWeight() + "KG ," + userDTO.getComplexion());
        tvDob.setText(ProjectUtils.changeDateFormateDOB(userDTO.getDob()));
        tvBirthTime.setText(userDTO.getBirth_time());
        tvBirthCity.setText(userDTO.getBirth_place());
        tvLife.setText("Dietary Habits -" + userDTO.getDietary() + "Drinking Habits -" + userDTO.getDrinking() + "Smoking Habits -" + userDTO.getSmoking());
        tvLanguage.setText(userDTO.getLanguage());
        tvInterests.setText(userDTO.getInterests());
        tvHobbies.setText(userDTO.getHobbies());
        tvFamilyAbout.setText(userDTO.getFamily_about());
        tvFamilyBackground.setText(userDTO.getFamily_status() + "," + userDTO.getFamily_type() + "," + userDTO.getFamily_value());
        tvFamilyIncome.setText(userDTO.getFamily_income());
        tvFatherOccupation.setText(userDTO.getFather_occupation());
        tvMotherOccupation.setText(userDTO.getMother_occupation());
        tvBro.setText(userDTO.getBrother() + " brothers");
        tvSis.setText(userDTO.getSister() + " sisters");
        tvFamilyBased.setText(userDTO.getFamily_city() + "," + userDTO.getFamily_district() + "," + userDTO.getFamily_state());

        tvDistrict.setText(userDTO.getDistrict());
        tvState.setText(userDTO.getState());
        tvAadhaar.setText(userDTO.getAadhaar());
        tvHeight.setText(userDTO.getHeight());
        tvBlood.setText(userDTO.getBlood_group());
        tvCase.setText(userDTO.getChallenged());
        tvWorkArea.setText(userDTO.getWork_place());

        if (userDTO.getCritical().equalsIgnoreCase("0")) {
            ivEditCritical.setVisibility(View.VISIBLE);
        } else {
            ivEditCritical.setVisibility(View.GONE);
        }

        Glide.with(mContext).
                load(Consts.IMAGE_URL + userDTO.getAvatar_medium())
                .placeholder(R.drawable.default_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImage);


        loginDTO.setAvatar_big(userDTO.getAvatar_big());
        loginDTO.setAvatar_medium(userDTO.getAvatar_medium());
        loginDTO.setAvatar_thumb(userDTO.getAvatar_thumb());
        prefrence.setLoginResponse(loginDTO,Consts.LOGIN_DTO);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivEditAbout:
                startActivity(new Intent(mContext, AboutMeActivity.class));
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                break;
            case R.id.ivEditSelf:
                startActivity(new Intent(mContext, BasicDetailsActivity.class));
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                break;
            case R.id.ivEditImportant:
                startActivity(new Intent(mContext, ImportantDetailsActivity.class));
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                break;
            case R.id.ivEditCritical:
                startActivity(new Intent(mContext, CriticalFields.class));
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                break;
            case R.id.ivEditLifestyle:
                startActivity(new Intent(mContext, LifeStyleActivity.class));
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                break;
            case R.id.ivEditFamily:
                startActivity(new Intent(mContext, AboutFamilyActivity.class));
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                break;
            case R.id.tvAddImage:
                if (imageDatalist.size() > 0) {
                    Intent propic = new Intent(mContext, ProfilePicSelection.class);
                    propic.putExtra("imageList", imageDatalist);
                    startActivity(propic);
                    overridePendingTransition(R.anim.slide_up, R.anim.stay);
                } else {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra("front", 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.stay);
                }

                break;
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                break;
            case R.id.imageLyout:
                if (imageDatalist.size() > 0) {
                    Intent intent = new Intent(mContext, ImageshowActivity.class);
                   // intent.putExtra("imageList", imageDatalist);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.stay);
                } else {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra("front", 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_up, R.anim.stay);
                }
                break;

        }
    }


    public void getImages() {

        new HttpsRequest(Consts.GET_GALLARY_API, parms, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                getMyProfile();
                if (flag) {
                    try {
                        imageDatalist = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<ImageDTO>>() {
                        }.getType();
                        imageDatalist = (ArrayList<ImageDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), getpetDTO);

                        if (imageDatalist.size() > 0) {
                            rlRed.setVisibility(View.VISIBLE);
                            ivCamera.setVisibility(View.GONE);
                            tvAddImage.setText(getResources().getString(R.string.change_pic));
                            tvImageCount.setText(imageDatalist.size() + "");
                        } else {
                            rlRed.setVisibility(View.GONE);
                            ivCamera.setVisibility(View.VISIBLE);
                            imageDatalist = new ArrayList<>();
                            tvAddImage.setText(getResources().getString(R.string.add_img));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        rlRed.setVisibility(View.GONE);
                        ivCamera.setVisibility(View.VISIBLE);
                        imageDatalist = new ArrayList<>();
                        tvAddImage.setText(getResources().getString(R.string.add_img));
                    }

                } else {
                    rlRed.setVisibility(View.GONE);
                    ivCamera.setVisibility(View.VISIBLE);
                    imageDatalist = new ArrayList<>();
                    tvAddImage.setText(getResources().getString(R.string.add_img));
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    public void getMyProfile() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.MY_PROFILE_API, getparmProfile(), mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    try {
                        userDTO = new Gson().fromJson(response.getJSONObject("data").toString(), UserDTO.class);
                        prefrence.setUserResponse(userDTO, Consts.USER_DTO);
                        showData();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    ProjectUtils.showToast(mContext, msg);
                }
            }
        });
    }

    public HashMap<String, String> getparmProfile() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put(Consts.TOKEN, loginDTO.getAccess_token());
        Log.e(TAG + " My Profile", parms.toString());
        return parms;
    }


}
