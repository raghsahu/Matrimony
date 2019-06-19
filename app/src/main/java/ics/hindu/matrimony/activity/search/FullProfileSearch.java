package ics.hindu.matrimony.activity.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import ics.hindu.matrimony.Models.ImageDTO;
import ics.hindu.matrimony.Models.LoginDTO;
import ics.hindu.matrimony.Models.UserDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.activity.myprofile.BioData;
import ics.hindu.matrimony.activity.profile_other.ImageSlider;
import ics.hindu.matrimony.activity.profile_other.ProfileOther;
import ics.hindu.matrimony.interfaces.Consts;
import ics.hindu.matrimony.sharedprefrence.SharedPrefrence;
import ics.hindu.matrimony.utils.ProjectUtils;
import ics.hindu.matrimony.view.CustomTextView;
import ics.hindu.matrimony.view.CustomTextViewBold;
import ics.hindu.matrimony.view.ProfilePageProfilePhotoImageView;

import java.io.Serializable;
import java.util.ArrayList;

public class FullProfileSearch extends AppCompatActivity implements View.OnClickListener{
    private ProfilePageProfilePhotoImageView ivProfileImage;
    private RelativeLayout rlImageGallery;
    private CustomTextViewBold tvName;
    private CustomTextView tvImageCount, tvOccupation, tvYearandheight, tvEducation, tvGotra, tvIncome, tvCity, tvMaritalStatus;
    private CustomTextView tvAbout, tvBodyType, tvManage, tvDob, tvBirthTime, tvBirthCity, tvLife, tvLanguage, tvInterests;
    private CustomTextView tvHobbies, tvFamilyPin, tvFamilyBackground, tvFamilyIncome, tvFatherOccupation, tvMotherOccupation;
    private CustomTextView tvBro, tvSis, tvFamilyAddress, tvFamilyBased, tvFamilyContact, tvFamilyWhatsup, tvFamilyEmail;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsing_toolbar;
    private LinearLayout back, llbiodata;
    private UserDTO userDTO;
    String dob = "";
    String[] arrOfStr;
    private Context mContext;
    private String TAG = ProfileOther.class.getSimpleName();
    ArrayList<ImageDTO> imageDatalist;
    private RelativeLayout rlGallaryClick;
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;
    private LinearLayout llContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_profile_search);
        mContext = FullProfileSearch.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);

        if (getIntent().hasExtra(Consts.USER_DTO)) {
            userDTO = (UserDTO) getIntent().getSerializableExtra(Consts.USER_DTO);
        }


        setUiaction();
    }

    public void setUiaction() {
        llContact = findViewById(R.id.llContact);
        rlGallaryClick = findViewById(R.id.rlGallaryClick);
        rlGallaryClick.setOnClickListener(this);


        collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        toolbar = findViewById(R.id.toolbar);
        collapsing_toolbar.setExpandedTitleColor(Color.TRANSPARENT);
        collapsing_toolbar.setCollapsedTitleTextColor(Color.WHITE);

        collapsing_toolbar.setTitle("Profile");
        back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(this);
        llbiodata = (LinearLayout) findViewById(R.id.llbiodata);
        llbiodata.setOnClickListener(this);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        rlImageGallery = findViewById(R.id.rlImageGallery);
        tvName = findViewById(R.id.tvName);

        tvImageCount = findViewById(R.id.tvImageCount);
        tvOccupation = findViewById(R.id.tvOccupation);
        tvYearandheight = findViewById(R.id.tvYearandheight);
        tvEducation = findViewById(R.id.tvEducation);
        tvGotra = findViewById(R.id.tvGotra);
        tvIncome = findViewById(R.id.tvIncome);
        tvCity = findViewById(R.id.tvCity);
        tvMaritalStatus = findViewById(R.id.tvMaritalStatus);
        tvAbout = findViewById(R.id.tvAbout);
        tvBodyType = findViewById(R.id.tvBodyType);
        tvManage = findViewById(R.id.tvManage);
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

        showData();
    }


    public void showData() {
        tvName.setText(userDTO.getName());

        if (userDTO.getGender().equalsIgnoreCase("M")) {
            Glide.with(mContext).
                    load(Consts.IMAGE_URL +  userDTO.getAvatar_medium())
                    .placeholder(R.drawable.dummy_m)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivProfileImage);

            if (userDTO.getProfile_for().equalsIgnoreCase("Self")) {
                tvManage.setText(getResources().getString(R.string.his_managed_self));

            } else {
                tvManage.setText(getResources().getString(R.string.his_managed_parent));

            }

        } else {
            Glide.with(mContext).
                    load(Consts.IMAGE_URL +  userDTO.getAvatar_medium())
                    .placeholder(R.drawable.dummy_f)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivProfileImage);
            if (userDTO.getProfile_for().equalsIgnoreCase("Self")) {
                tvManage.setText(getResources().getString(R.string.her_managed_self));

            } else {
                tvManage.setText(getResources().getString(R.string.her_managed_parent));

            }
        }


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
        tvLife.setText("Dietary Habits -" + userDTO.getDietary() + ", Drinking Habits -" + userDTO.getDrinking() + ", Smoking Habits -" + userDTO.getSmoking());
        tvLanguage.setText(userDTO.getLanguage());
        tvInterests.setText(userDTO.getInterests());
        tvHobbies.setText(userDTO.getHobbies());
        tvFamilyPin.setText(userDTO.getFamily_pin());
        tvFamilyBackground.setText(userDTO.getFamily_status() + "," + userDTO.getFamily_type() + "," + userDTO.getFamily_value());
        tvFamilyIncome.setText(userDTO.getFamily_income());
        tvFatherOccupation.setText(userDTO.getFather_occupation());
        tvMotherOccupation.setText(userDTO.getMother_occupation());
        tvBro.setText(userDTO.getBrother() + " brothers");
        tvSis.setText(userDTO.getSister() + " sisters");
        tvFamilyBased.setText(userDTO.getFamily_city() + "," + userDTO.getFamily_district() + "," + userDTO.getFamily_state());

        tvFamilyAddress.setText(userDTO.getPermanent_address());
        tvFamilyEmail.setText(getResources().getString(R.string.bio_email) + " " + userDTO.getEmail());
        tvFamilyWhatsup.setText(getResources().getString(R.string.bio_whatsup_no) + " " + userDTO.getWhatsapp_no());
        tvFamilyContact.setText(getResources().getString(R.string.bio_father_no) + " " + userDTO.getMobile2());

        Glide.with(mContext).
                load(Consts.IMAGE_URL + userDTO.getAvatar_medium())
                .placeholder(R.drawable.default_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivProfileImage);


        try {
            dob = userDTO.getDob();
            arrOfStr = dob.split("-", 3);

            Log.e("date of birth", arrOfStr[0] + " " + arrOfStr[1] + " " + arrOfStr[2]);
            tvYearandheight.setText(ProjectUtils.getAge(Integer.parseInt(arrOfStr[0]), Integer.parseInt(arrOfStr[1]), Integer.parseInt(arrOfStr[2])) + " years " + userDTO.getHeight());


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userDTO.getStatus() == 0) {
            llContact.setVisibility(View.GONE);
            llbiodata.setVisibility(View.GONE);
        } else {
            llContact.setVisibility(View.VISIBLE);
            llbiodata.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rlGallaryClick:
                if (imageDatalist.size() > 0) {
                    Intent intent = new Intent(mContext, ImageSlider.class);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) imageDatalist);
                    intent.putExtra(Consts.IMAGE_LIST, args);
                    startActivity(intent);
                } else {
                    ProjectUtils.showToast(mContext, getResources().getString(R.string.no_image));
                }

                break;
            case R.id.llbiodata:
                Intent intent = new Intent(mContext, BioData.class);
                intent.putExtra(Consts.USER_DTO, userDTO);
                intent.putExtra(Consts.TAG_PROFILE, 2);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }




    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}
