package ics.hindu.matrimony.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import ics.hindu.matrimony.models.ImageDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.activity.myprofile.DeleteImage;
import ics.hindu.matrimony.interfaces.Consts;
import ics.hindu.matrimony.utils.ProjectUtils;

import java.io.Serializable;
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

            holder.IVsetimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageDatalist.size() > 0) {
                        Intent intent = new Intent(mContext, DeleteImage.class);
                        Bundle args = new Bundle();
                        args.putSerializable("ARRAYLIST", (Serializable) imageDatalist);
                        intent.putExtra(Consts.IMAGE_LIST, args);
                        mContext.startActivity(intent);
                    } else {
                        ProjectUtils.showToast(mContext, mContext.getResources().getString(R.string.no_image));
                    }
                }
            });
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
