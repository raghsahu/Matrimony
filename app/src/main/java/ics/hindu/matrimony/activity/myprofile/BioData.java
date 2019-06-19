package ics.hindu.matrimony.activity.myprofile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import ics.hindu.matrimony.Models.UserDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.interfaces.Consts;
import ics.hindu.matrimony.utils.ScreenshotUtils;
import ics.hindu.matrimony.view.CustomTextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BioData extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private RelativeLayout rlView;
    private ImageView ivProfilePic, ivShare;
    private CustomTextView tvName, tvMaritalStatus, tvBloodGroup, tvBirthDate, tvBirthTime;
    private CustomTextView tvBirthPlace, tvManglik, tvGotra, tvGotraNanihal, tvColor;
    private CustomTextView tvHeight, tvEducation, tvOccupation, tvIncome, tvWorkPlace, tvDadaJi;
    private CustomTextView tvFatherName, tvMotherName, tvBroSis, tvNanaJi, tvFatherOccupation;
    private CustomTextView tvAddress, tvPincode, tvEmail, tvWhatsupNo, tvFatherNumber;
    private UserDTO userDTO;
    private int tag_profile = 0;
    private LinearLayout llBack,llShare;
    private File screenShotFile;
    Bitmap b = null;
    private Dialog dialog;
    private CustomTextView tvYes, tvNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_data);
        mContext = BioData.this;
        if (getIntent().hasExtra(Consts.USER_DTO)) {
            userDTO = (UserDTO) getIntent().getSerializableExtra(Consts.USER_DTO);
            tag_profile = getIntent().getIntExtra(Consts.TAG_PROFILE, 0);
        }
       // setUiAction();
    }

    public void setUiAction() {
        rlView = findViewById(R.id.rlView);
        llBack = findViewById(R.id.llBack);
        llShare = findViewById(R.id.llShare);

        llBack.setOnClickListener(this);
        llShare.setOnClickListener(this);

        if (tag_profile == 2){
            llShare.setVisibility(View.GONE);
        }else {
            llShare.setVisibility(View.VISIBLE);
        }

        ivProfilePic = findViewById(R.id.ivProfilePic);

        tvName = findViewById(R.id.tvName);

        tvMaritalStatus = findViewById(R.id.tvMaritalStatus);
        tvBloodGroup = findViewById(R.id.tvBloodGroup);
        tvBirthDate = findViewById(R.id.tvBirthDate);
        tvBirthTime = findViewById(R.id.tvBirthTime);
        tvBirthPlace = findViewById(R.id.tvBirthPlace);
        tvManglik = findViewById(R.id.tvManglik);
        tvGotra = findViewById(R.id.tvGotra);
        tvGotraNanihal = findViewById(R.id.tvGotraNanihal);
        tvColor = findViewById(R.id.tvColor);
        tvHeight = findViewById(R.id.tvHeight);
        tvEducation = findViewById(R.id.tvEducation);
        tvOccupation = findViewById(R.id.tvOccupation);
        tvIncome = findViewById(R.id.tvIncome);
        tvWorkPlace = findViewById(R.id.tvWorkPlace);
        tvDadaJi = findViewById(R.id.tvDadaJi);
        tvFatherName = findViewById(R.id.tvFatherName);
        tvMotherName = findViewById(R.id.tvMotherName);
        tvBroSis = findViewById(R.id.tvBroSis);
        tvNanaJi = findViewById(R.id.tvNanaJi);
        tvFatherOccupation = findViewById(R.id.tvFatherOccupation);
        tvAddress = findViewById(R.id.tvAddress);
        tvPincode = findViewById(R.id.tvPincode);
        tvEmail = findViewById(R.id.tvEmail);
        tvWhatsupNo = findViewById(R.id.tvWhatsupNo);
        tvFatherNumber = findViewById(R.id.tvFatherNumber);

        //*****************************************imp**************************************
        Glide.with(mContext).
                load(Consts.IMAGE_URL + userDTO.getAvatar_medium())
                .placeholder(R.drawable.default_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivProfilePic);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //****************************************imp********************************************************
//        if (userDTO.getGender().equalsIgnoreCase("M")) {
//            tvName.setText(getResources().getString(R.string.bio_name_boy)+" " + userDTO.getName());
//            tvOccupation.setText(getResources().getString(R.string.bio_occu_boy)+" " + userDTO.getOccupation());
//
//        } else {
//            tvName.setText(getResources().getString(R.string.bio_name_girl)+" " + userDTO.getName());
//            tvOccupation.setText(getResources().getString(R.string.bio_occu_girl)+" " + userDTO.getOccupation());
//
//
//        }
//        tvMaritalStatus.setText(getResources().getString(R.string.bio_maritial_status)+" " + userDTO.getMarital_status());
//        tvBloodGroup.setText(getResources().getString(R.string.bio_blood_group)+" " + userDTO.getBlood_group());
//
//        tvBirthDate.setText(getResources().getString(R.string.bio_birth_date)+" " + userDTO.getDob());
//        tvBirthTime.setText(getResources().getString(R.string.bio_birth_time)+" " + userDTO.getBirth_time());
//        tvBirthPlace.setText(getResources().getString(R.string.bio_birth_place)+" " + userDTO.getBirth_place());
//        tvManglik.setText(getResources().getString(R.string.bio_manglik)+" " + userDTO.getManglik());
//        tvGotra.setText(getResources().getString(R.string.bio_gotra)+" " + userDTO.getGotra());
//        tvGotraNanihal.setText(getResources().getString(R.string.bio_gotra_ninihal)+" " + userDTO.getGotra_nanihal());
//        tvColor.setText(getResources().getString(R.string.bio_color)+" " + userDTO.getComplexion());
//        tvHeight.setText(getResources().getString(R.string.bio_height)+" " + userDTO.getHeight());
//        tvEducation.setText(getResources().getString(R.string.bio_education)+" " + userDTO.getQualification());
//        tvIncome.setText(getResources().getString(R.string.bio_income)+" " + userDTO.getIncome());
//        tvWorkPlace.setText(getResources().getString(R.string.bio_work_place)+" " + userDTO.getWork_place());
//        tvDadaJi.setText(getResources().getString(R.string.bio_dada_ji)+" " + userDTO.getGrand_father_name());
//        tvFatherName.setText(getResources().getString(R.string.bio_father_name)+" " + userDTO.getFather_name());
//        tvMotherName.setText(getResources().getString(R.string.bio_mother_name)+" " + userDTO.getMother_name());
//        tvNanaJi.setText(getResources().getString(R.string.bio_nana_ji)+" " + userDTO.getMaternal_grand_father_name_address());
//        tvFatherOccupation.setText(getResources().getString(R.string.bio_father_occu)+" " + userDTO.getFather_occupation());
//        tvAddress.setText(getResources().getString(R.string.bio_address)+" " + userDTO.getPermanent_address());
//        tvPincode.setText(getResources().getString(R.string.bio_pincode)+" " + userDTO.getFamily_pin());
//        tvEmail.setText(getResources().getString(R.string.bio_email)+" " + userDTO.getEmail());
//        tvWhatsupNo.setText(getResources().getString(R.string.bio_whatsup_no)+" " + userDTO.getWhatsapp_no());
//        tvFatherNumber.setText(getResources().getString(R.string.bio_father_no)+" " + userDTO.getMobile2());
//
//        if (userDTO.getBrother().equalsIgnoreCase("None")) {
//            tvBroSis.setText(getResources().getString(R.string.bio_bro_sis)+" " + userDTO.getSister() + " " + getResources().getString(R.string.sister));
//        } else if (userDTO.getSister().equalsIgnoreCase("None")) {
//            tvBroSis.setText(getResources().getString(R.string.bio_bro_sis)+" " + userDTO.getBrother() + " " + getResources().getString(R.string.brother));
//        } else {
//            tvBroSis.setText(getResources().getString(R.string.bio_bro_sis)+" " + userDTO.getBrother() + " " + getResources().getString(R.string.brother) + "-" + userDTO.getSister() + " " + getResources().getString(R.string.sister));
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llShare:
                takeScreenshot();
                break;
            case R.id.llBack:
                finish();
                overridePendingTransition(R.anim.stay, R.anim.slide_down);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }

    private void takeScreenshot() {


        b = ScreenshotUtils.screenShot(rlView);

        if (b != null) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            File saveFile = ScreenshotUtils.getMainDirectoryName(this);//get the path to save screenshot
            screenShotFile = ScreenshotUtils.store(b, Consts.APP_NMAE + timeStamp + ".jpg", saveFile);//save the screenshot to selected path
            dialogshare();
        } else {

        }

    }
    public void dialogshare() {
        dialog = new Dialog(mContext, android.R.style.Theme_Dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_share);


        tvYes = (CustomTextView) dialog.findViewById(R.id.tvYes);
        tvNo = (CustomTextView) dialog.findViewById(R.id.tvNo);
        ivShare = (ImageView) dialog.findViewById(R.id.ivShare);
        dialog.show();
        dialog.setCancelable(false);
        ivShare.setImageBitmap(b);
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                if (screenShotFile != null) {
                    screenShotFile.delete();
                }
            }
        });
        tvYes.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareMedia(b);
                        dialog.dismiss();
                    }
                });

    }

    public void shareMedia(Bitmap mBitmap) {
        try {
            Bitmap b = mBitmap;
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                    b, "Title", null);
            Uri imageUri = Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, "Select"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
