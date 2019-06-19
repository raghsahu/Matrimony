package ics.hindu.matrimony.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import ics.hindu.matrimony.Models.NewsDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.interfaces.Consts;
import ics.hindu.matrimony.view.CustomTextView;
import ics.hindu.matrimony.view.SquareImageView;

import java.util.ArrayList;

/**
 * Created by varunverma on 25/1/18.
 */

public class AdapterNews extends BaseAdapter {
    private ArrayList<NewsDTO> newsDTOSList;
    private Context mContext;
    private LayoutInflater inflater;

    public AdapterNews(ArrayList<NewsDTO> newsDTOSList, Context mContext, LayoutInflater inflater) {
        this.mContext = mContext;
        this.newsDTOSList = newsDTOSList;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return newsDTOSList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsDTOSList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder = new ViewHolder();
        View itemView = inflater.inflate(R.layout.adapter_news, null, false);

        holder.tvTitle = (CustomTextView) itemView.findViewById(R.id.tvTitle);
        holder.tvComment = (CustomTextView) itemView.findViewById(R.id.tvComment);
        holder.post_image = (SquareImageView) itemView.findViewById(R.id.post_image);


        holder.tvComment.setText(newsDTOSList.get(position).getDescription());

        holder.tvTitle.setText(newsDTOSList.get(position).getHeading());

        if (newsDTOSList.get(position).getImage().equalsIgnoreCase("")) {
            holder.post_image.setVisibility(View.GONE);


        } else {
            holder.post_image.setVisibility(View.VISIBLE);
            Glide
                    .with(mContext)
                    .load(Consts.IMAGE_URL+newsDTOSList.get(position).getImage())
                    .placeholder(R.drawable.default_error)
                    .fitCenter()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.post_image);

        }
        return itemView;
    }


    static class ViewHolder {
        public CustomTextView tvComment, tvTitle;
        public SquareImageView post_image;
    }

}
