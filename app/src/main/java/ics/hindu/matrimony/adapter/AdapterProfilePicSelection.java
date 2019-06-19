package ics.hindu.matrimony.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import ics.hindu.matrimony.models.ImageDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.activity.myprofile.ProfilePicSelection;
import ics.hindu.matrimony.interfaces.Consts;

import java.util.ArrayList;

public class AdapterProfilePicSelection extends RecyclerView.Adapter<AdapterProfilePicSelection.MatchesHolder> {

    private ProfilePicSelection context;
    private ArrayList<ImageDTO> imageDatalist;

    public AdapterProfilePicSelection(ProfilePicSelection context, ArrayList<ImageDTO> imageDatalist) {
        this.context = context;
        this.imageDatalist = imageDatalist;
    }

    @NonNull
    @Override
    public MatchesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_profile_pic_selection, parent, false);
        MatchesHolder holder = new MatchesHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final MatchesHolder holder, final int position) {

        Glide.with(context).
                load(Consts.IMAGE_URL + imageDatalist.get(position).getMedium_url())
                .placeholder(R.drawable.default_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_album_photo);



        if (imageDatalist.get(position).isSelected()) {
            holder.rl_profile_pic_selection.setVisibility(View.VISIBLE);
        } else {
            holder.rl_profile_pic_selection.setVisibility(View.GONE);
        }
        holder.iv_album_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.updateList(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return imageDatalist.size();
    }


    public class MatchesHolder extends RecyclerView.ViewHolder {
        public ImageView iv_album_photo;
        public RelativeLayout rl_profile_pic_selection;

        public MatchesHolder(View itemView) {
            super(itemView);
            iv_album_photo = itemView.findViewById(R.id.iv_album_photo);
            rl_profile_pic_selection = itemView.findViewById(R.id.rl_profile_pic_selection);

        }


    }

}
