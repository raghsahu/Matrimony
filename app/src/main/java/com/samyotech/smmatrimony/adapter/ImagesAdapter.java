package com.samyotech.smmatrimony.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.samyotech.smmatrimony.R;

import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mImagesList;
    int lastPosition = -1;


    public ImagesAdapter(Context context, ArrayList<String> imagesList) {
        mContext = context;
        mImagesList = imagesList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycle_view_images, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(mContext).load(mImagesList.get(holder.getAdapterPosition())).placeholder(R.drawable.default_error).centerCrop().into(holder.getImage());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastPosition = position;
                deleteimageDialog(position, view);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mImagesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image_view);
        }

        public ImageView getImage() {
            return image;
        }

    }


    public void deleteimageDialog(final int i, View view) {

        AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
        alertbox.setMessage("Do you want delete Image");
        alertbox.setTitle("Delete Image");
        alertbox.setIcon(R.mipmap.ic_launcher);

        alertbox.setPositiveButton("Remove",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0,
                                        int arg1) {

                        mImagesList.remove(lastPosition);
                        notifyDataSetChanged();
                    }
                });
        alertbox.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });


        alertbox.show();
    }


}
