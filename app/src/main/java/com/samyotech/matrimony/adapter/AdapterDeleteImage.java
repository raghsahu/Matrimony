package com.samyotech.matrimony.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.samyotech.matrimony.Models.ImageDTO;
import com.samyotech.matrimony.Models.LoginDTO;
import com.samyotech.matrimony.Models.UserDTO;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.myprofile.DeleteImage;
import com.samyotech.matrimony.activity.profile_other.ImageSlider;
import com.samyotech.matrimony.https.HttpsRequest;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.interfaces.Helper;
import com.samyotech.matrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.matrimony.utils.ProjectUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterDeleteImage extends PagerAdapter {
    private Context mContext;
    LayoutInflater mLayoutInflater;
    private DeleteImage activity;
    private ArrayList<ImageDTO> imageDatalist;
    DialogInterface dialogMy;
    private LoginDTO loginDTO;
    private UserDTO userDTO;
    private SharedPrefrence prefrence;
    private String TAG = AdapterDeleteImage.class.getSimpleName();
    private HashMap<String, String> parms = new HashMap<>();

    public AdapterDeleteImage(DeleteImage activity, Context context, ArrayList<ImageDTO> imageDatalist) {
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageDatalist = imageDatalist;
        this.activity = activity;

        prefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        userDTO = prefrence.getUserResponse(Consts.USER_DTO);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.adapter_delete_image, container, false);
        final ImageView image = (ImageView) itemView.findViewById(R.id.image);
        final ImageView ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);


        Glide.with(mContext)
                .load(Consts.IMAGE_URL + imageDatalist.get(position).getBig_url())
                .asBitmap()
                .placeholder(R.drawable.default_error)
                .signature(new StringSignature(Consts.IMAGE_URL + imageDatalist.get(position).getBig_url()))
                .skipMemoryCache(true)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userDTO.getAvatar_medium().equalsIgnoreCase(imageDatalist.get(position).getMedium_url())) {
                    ProjectUtils.showAlertDialog(mContext, "", mContext.getResources().getString(R.string.not_delete_msg), mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                } else {
                    ProjectUtils.showAlertDialogWithCancel(mContext, mContext.getResources().getString(R.string.delete_pic), mContext.getResources().getString(R.string.msg_delete_img), mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialogMy = dialog;

                            parms.put(Consts.TOKEN, loginDTO.getAccess_token());
                            parms.put(Consts.MEDIA_ID, imageDatalist.get(position).getMedia_id());
                            DeleteProilePic();


                        }
                    }, mContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                }


            }
        });

/*
        Glide.with(mContext)
                .load(imageDatalist.get(position).getPet_img_path())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .crossFade()
                .error(R.drawable.dummy_img)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        image.setMaxZoom(0);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(image);
*/
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return imageDatalist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public void DeleteProilePic() {
        ProjectUtils.showProgressDialog(mContext, true, mContext.getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.DELETE_IMAGE_API, parms, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    dialogMy.dismiss();
                    activity.finish();
                    activity.overridePendingTransition(R.anim.stay, R.anim.slide_down);
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }
            }
        });
    }


}