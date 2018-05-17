package com.inspire.rkspmatrimony.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.inspire.rkspmatrimony.Models.ImageDTO;
import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.activity.profile_other.ImageSlider;
import com.inspire.rkspmatrimony.interfaces.Consts;

import java.util.ArrayList;


public class AdapterImageSlider extends PagerAdapter {
    private Context mContext;
    LayoutInflater mLayoutInflater;
    private ImageSlider activity;
    private ArrayList<ImageDTO> imageDatalist;

    public AdapterImageSlider(ImageSlider activity, Context context, ArrayList<ImageDTO> imageDatalist) {
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageDatalist = imageDatalist;
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.adapter_image_slider, container, false);
        final ImageView image = (ImageView) itemView.findViewById(R.id.image);


        Glide.with(mContext)
                .load(Consts.IMAGE_URL+imageDatalist.get(position).getBig_url())
                .asBitmap()
                .placeholder(R.drawable.default_error)
                .signature(new StringSignature(Consts.IMAGE_URL+imageDatalist.get(position).getBig_url()))
                .skipMemoryCache(true)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);


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

}