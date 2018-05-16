package com.samyotech.matrimony.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.samyotech.matrimony.Models.ImageDTO;
import com.samyotech.matrimony.Models.UserDTO;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.interfaces.Consts;

import java.util.ArrayList;

public class GridImageshowAdapter extends RecyclerView.Adapter<GridImageshowAdapter.ViewHolder> {

    private Context mContext;
    ArrayList<ImageDTO> imageDatalist;
    int lastPosition = -1;


    public GridImageshowAdapter(Context mContext, ArrayList<ImageDTO> imageDatalist) {
        this.mContext = mContext;
        this.imageDatalist = imageDatalist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adaptergridimage, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

            if (position == 0) {
                holder.IVsetimage.setImageResource(R.drawable.ic_add_more);

            } else {
                Glide.with(mContext)
                        .load(Consts.IMAGE_URL+imageDatalist.get(position).getMedium_url())
                        .placeholder(R.drawable.default_error)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.getImage());
            }
    }

    @Override
    public int getItemCount() {
        return imageDatalist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView IVsetimage;

        public ViewHolder(View v) {
            super(v);
            IVsetimage = (ImageView) v.findViewById(R.id.IVsetimage);
        }

        public ImageView getImage() {
            return IVsetimage;
        }
    }


}
