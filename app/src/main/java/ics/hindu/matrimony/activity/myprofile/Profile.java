package ics.hindu.matrimony.activity.myprofile;

import android.annotation.SuppressLint;
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
import ics.hindu.matrimony.Models.ImageDTO;
import ics.hindu.matrimony.Models.LoginDTO;
import ics.hindu.matrimony.Models.UserDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.activity.editprofile.AboutFamilyActivity;
import ics.hindu.matrimony.activity.editprofile.AboutMeActivity;
import ics.hindu.matrimony.activity.editprofile.BasicDetailsActivity;
import ics.hindu.matrimony.activity.editprofile.CriticalFields;
import ics.hindu.matrimony.activity.editprofile.ImportantDetailsActivity;
import ics.hindu.matrimony.activity.editprofile.LifeStyleActivity;
import ics.hindu.matrimony.activity.imageselection.ImageshowActivity;
import ics.hindu.matrimony.activity.imageselection.MainActivity;
import ics.hindu.matrimony.https.HttpsRequest;
import ics.hindu.matrimony.interfaces.Consts;
import ics.hindu.matrimony.interfaces.Helper;
import ics.hindu.matrimony.network.NetworkManager;
import ics.hindu.matrimony.sharedprefrence.SharedPrefrence;
import ics.hindu.matrimony.utils.ProjectUtils;
import ics.hindu.matrimony.view.CustomTextView;
import ics.hindu.matrimony.view.CustomTextViewBold;

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
    private CustomTextView tvBirthCity, tvLife, tvLanguage, tvInterests, tvHobbies, tvFamilyPin, tvFamilyBackground;
    private CustomTextView tvFamilyIncome, tvFatherOccupation, tvMotherOccupation, tvBro, tvSis;
    private CustomTextView tvFamilyAddress, tvFamilyBased, tvFamilyContact, tvFamilyWhatsup,tvFamilyEmail;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar;
    private LinearLayout back, llbiodata;
    private CircleImageView ivImage, ivCamera;
    private CustomTextView tvAddImage, tvImageCount;
    private RelativeLayout rlRed;
    private ArrayList<ImageDTO> imageDatalist = new ArrayList<>();
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

//        parms.put(Consts.TOKEN, loginDTO.getAccess_token());
//        parms.put(Consts.USER_ID, loginDTO.getData().getId());

        //************************************************************static******************
        parms.put(Consts.USER_ID, "1234");
        parms.put(Consts.TOKEN, "111111111111");
        //*******************************************************************
        setUiaction();
    }

    public void setUiaction() {

        collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        toolbar = findViewById(R.id.toolbar);

        collapsing_toolbar.setExpandedTitleColor(Color.TRANSPARENT);
        collapsing_toolbar.setCollapsedTitleTextColor(Color.WHITE);
        collapsing_toolbar.setTitle("Profile");
        llbiodata = (LinearLayout) findViewById(R.id.llbiodata);
        llbiodata.setOnClickListener(this);
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
        tvFamilyPin = findViewById(R.id.tvFamilyPin);
        tvFamilyBackground = findViewById(R.id.tvFamilyBackground);
        tvFamilyIncome = findViewById(R.id.tvFamilyIncome);
        tvFatherOccupation = findViewById(R.id.tvFatherOccupation);
        tvMotherOccupation = findViewById(R.id.tvMotherOccupation);
        tvBro = findViewById(R.id.tvBro);
        tvSis = findViewById(R.id.tvSis);
        tvFamilyBased = findViewById(R.id.tvFamilyBased);
        tvFamilyWhatsup = findViewById(R.id.tvFamilyWhatsup);
        tvFamilyContact = findViewById(R.id.tvFamilyContact);
        tvFamilyAddress = findViewById(R.id.tvFamilyAddress);
        tvFamilyEmail = findViewById(R.id.tvFamilyEmail);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkManager.isConnectToInternet(mContext)) {


           // getImages();

        } else {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
        }
    }

    @SuppressLint("SetTextI18n")
    public void showData() {
        tvName.setText(userDTO.getName());

        tvOccupation.setText(userDTO.getOccupation());
        tvEducation.setText(userDTO.getQualification());
        tvGotra.setText(userDTO.getGotra());
        tvIncome.setText(userDTO.getIncome());
        tvCity.setText(userDTO.getCity());
        tvMaritalStatus.setText(userDTO.getMarital_status());
        tvAbout.setText(userDTO.getAbout_me());
        tvBodyType.setText(userDTO.getBody_type() + ", " + userDTO.getWeight() + getResources().getString(R.string.kg) + ", " + userDTO.getComplexion());
        tvDob.setText(ProjectUtils.changeDateFormateDOB(userDTO.getDob()));
        tvBirthTime.setText(userDTO.getBirth_time());
        tvBirthCity.setText(userDTO.getBirth_place());
        tvLife.setText(getResources().getString(R.string.txt_dietary_hb) + userDTO.getDietary()+ ", " + getResources().getString(R.string.txt_drink_hb) + userDTO.getDrinking()+ ", " + getResources().getString(R.string.txt_smok_hb) + userDTO.getSmoking());
        tvLanguage.setText(userDTO.getLanguage());
        tvInterests.setText(userDTO.getInterests());
        tvHobbies.setText(userDTO.getHobbies());
        tvFamilyPin.setText(userDTO.getFamily_pin());
        tvFamilyBackground.setText(userDTO.getFamily_status() + "," + userDTO.getFamily_type() + "," + userDTO.getFamily_value());
        tvFamilyIncome.setText(userDTO.getFamily_income());
        tvFatherOccupation.setText(userDTO.getFather_occupation());
        tvMotherOccupation.setText(userDTO.getMother_occupation());
        tvBro.setText(userDTO.getBrother() + getResources().getString(R.string.bros));
        tvSis.setText(userDTO.getSister() + getString(R.string.siss));
        tvFamilyBased.setText(userDTO.getFamily_city() + "," + userDTO.getFamily_district() + "," + userDTO.getFamily_state());

        tvDistrict.setText(userDTO.getDistrict());
        tvState.setText(userDTO.getState());
        tvAadhaar.setText(userDTO.getAadhaar());
        tvHeight.setText(userDTO.getHeight());
        tvBlood.setText(userDTO.getBlood_group());
        tvCase.setText(userDTO.getChallenged());
        tvWorkArea.setText(userDTO.getWork_place());

        tvFamilyAddress.setText(userDTO.getPermanent_address());
        tvFamilyEmail.setText(getResources().getString(R.string.bio_email)+" " + userDTO.getEmail());
        tvFamilyWhatsup.setText(getResources().getString(R.string.bio_whatsup_no)+" " + userDTO.getWhatsapp_no());
        tvFamilyContact.setText(getResources().getString(R.string.bio_father_no)+" " + userDTO.getMobile2());

        if (userDTO.getCritical().equalsIgnoreCase("0")) {
            ivEditCritical.setVisibility(View.VISIBLE);
        } else {
            ivEditCritical.setVisibility(View.GONE);
        }



        if (userDTO.getGender().equalsIgnoreCase("M")) {
            Glide.with(mContext).
                    load(Consts.IMAGE_URL +  userDTO.getAvatar_medium())
                    .placeholder(R.drawable.dummy_m)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivImage);

        } else {
            Glide.with(mContext).
                    load(Consts.IMAGE_URL +  userDTO.getAvatar_medium())
                    .placeholder(R.drawable.dummy_f)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivImage);

        }


        loginDTO.setData(userDTO);
        prefrence.setLoginResponse(loginDTO, Consts.LOGIN_DTO);

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
            case R.id.llbiodata:
                Intent intentbio = new Intent(mContext, BioData.class);
                intentbio.putExtra(Consts.USER_DTO,userDTO);
                intentbio.putExtra(Consts.TAG_PROFILE,1);
                startActivity(intentbio);
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
