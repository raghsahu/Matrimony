package com.inspire.rkspmatrimony.activity.myprofile;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.inspire.rkspmatrimony.Models.ImageDTO;
import com.inspire.rkspmatrimony.Models.LoginDTO;
import com.inspire.rkspmatrimony.Models.UserDTO;
import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.activity.imageselection.ImageshowActivity;
import com.inspire.rkspmatrimony.adapter.AdapterProfilePicSelection;
import com.inspire.rkspmatrimony.https.HttpsRequest;
import com.inspire.rkspmatrimony.interfaces.Consts;
import com.inspire.rkspmatrimony.interfaces.Helper;
import com.inspire.rkspmatrimony.sharedprefrence.SharedPrefrence;
import com.inspire.rkspmatrimony.utils.ProjectUtils;
import com.inspire.rkspmatrimony.view.CustomTextView;
import com.inspire.rkspmatrimony.view.CustomTextViewBold;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfilePicSelection extends AppCompatActivity implements View.OnClickListener {
    private String TAG = ProfilePicSelection.class.getSimpleName();
    private Context mContext;
    private ArrayList<ImageDTO> imageDatalist;
    private ImageDTO imageDTO;
    private LinearLayout llBack;
    public CustomTextView tv_profile_photo_message;
    private RecyclerView recyclerView;
    private AdapterProfilePicSelection adapterProfilePicSelection;
    private UserDTO userDTO;
    private SharedPrefrence prefrence;

    private CustomTextViewBold tvNext;
    private HashMap<String, String> parms = new HashMap<>();
    private LoginDTO loginDTO;
    DialogInterface dialogMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic_selection);
        mContext = ProfilePicSelection.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        userDTO = prefrence.getUserResponse(Consts.USER_DTO);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);

        if (getIntent().hasExtra("imageList")) {
            imageDatalist = (ArrayList<ImageDTO>) getIntent().getSerializableExtra("imageList");
        }
        setUiAction();
    }

    public void setUiAction() {
        tvNext = findViewById(R.id.tvNext);
        llBack = findViewById(R.id.llBack);
        llBack.setOnClickListener(this);
        tvNext.setOnClickListener(this);

        tv_profile_photo_message = findViewById(R.id.tv_profile_photo_message);
        recyclerView = findViewById(R.id.recyclerView);

        for (int j = 0; j < imageDatalist.size(); j++) {
            if (imageDatalist.get(j).getThumb_url().equalsIgnoreCase(userDTO.getAvatar_thumb())) {
                tv_profile_photo_message.setVisibility(View.GONE);
                tvNext.setVisibility(View.GONE);
                imageDatalist.get(j).setSelected(true);
            }
        }


        adapterProfilePicSelection = new AdapterProfilePicSelection(ProfilePicSelection.this, imageDatalist);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterProfilePicSelection);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llBack:
                finish();
                overridePendingTransition(R.anim.stay, R.anim.slide_down);
                break;
            case R.id.tvNext:
                ProjectUtils.showAlertDialogWithCancel(mContext, getResources().getString(R.string.change_profile), getResources().getString(R.string.msg_change_img), getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogMy = dialog;
                        if (imageDTO != null) {
                            parms.put(Consts.TOKEN, loginDTO.getAccess_token());
                            parms.put(Consts.MEDIA_ID, imageDTO.getMedia_id());
                            ChangeProilePic();
                        } else {
                            ProjectUtils.showToast(mContext, getResources().getString(R.string.select_image_first));
                        }

                    }
                }, getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }

    public void updateList(int pos) {
        for (int i = 0; i < imageDatalist.size(); i++) {
            if (i == pos) {
                if (imageDatalist.get(i).getThumb_url().equalsIgnoreCase(userDTO.getAvatar_thumb())) {
                    tv_profile_photo_message.setVisibility(View.GONE);
                    tvNext.setVisibility(View.GONE);
                } else {
                    tv_profile_photo_message.setVisibility(View.VISIBLE);
                    tvNext.setVisibility(View.VISIBLE);
                }
                imageDatalist.get(i).setSelected(true);
                imageDTO = imageDatalist.get(i);

            } else {
                imageDatalist.get(i).setSelected(false);
            }
        }

        recyclerView.setAdapter(adapterProfilePicSelection);
        adapterProfilePicSelection.notifyDataSetChanged();
    }

    public void ChangeProilePic() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.SET_AVATAR_API, parms, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    dialogMy.dismiss();
                    finish();
                    overridePendingTransition(R.anim.stay, R.anim.slide_down);
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }
            }
        });
    }
}
