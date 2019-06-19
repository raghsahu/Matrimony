package ics.hindu.matrimony.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ics.hindu.matrimony.Models.ProfileForDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.fragment.registration.ProfileFor;
import ics.hindu.matrimony.view.CustomTextViewBold;

import java.util.ArrayList;

public class AdapterProfileFor extends RecyclerView.Adapter<AdapterProfileFor.ProfileForHolder> {

    private Context context;
    private ArrayList<ProfileForDTO> profileforList;
    private ProfileFor profileFor;
    private static SparseBooleanArray sSelectedItems;
    private static UpdateDataClickListener sClickListener;
    private static int sPosition;
    public AdapterProfileFor(ArrayList<ProfileForDTO> profileforList, ProfileFor profileFor) {
        this.context = profileFor.getActivity();
        this.profileforList = profileforList;
        this.profileFor = profileFor;
        sSelectedItems = new SparseBooleanArray();
    }

    @NonNull
    @Override
    public ProfileForHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_profile_for, parent, false);
        ProfileForHolder holder = new ProfileForHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ProfileForHolder holder, int position) {
        holder.title.setText(profileforList.get(position).getType());
        holder.thumbnail.setImageDrawable(ContextCompat.getDrawable(profileFor.getContext(), select(profileforList.get(position).getId())));

      //  holder.rlMain.setSelected(true);

        if (sSelectedItems.get(position)) {
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.title.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
        holder.rlMain.setSelected(sSelectedItems.get(position, false));

    }

    @Override
    public int getItemCount() {
        return profileforList.size();
    }

    public void setOnItemClickListener(UpdateDataClickListener clickListener) {
        sClickListener = clickListener;
    }


    public class ProfileForHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView thumbnail;
        public CustomTextViewBold title;
        public RelativeLayout rlMain;

        public ProfileForHolder(View itemView) {
            super(itemView);
            title = (CustomTextViewBold) itemView.findViewById(R.id.title);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            rlMain = (RelativeLayout) itemView.findViewById(R.id.rlMain);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (sSelectedItems.get(getAdapterPosition(), false)) {
                sSelectedItems.delete(getAdapterPosition());
                rlMain.setSelected(false);
                thumbnail.setSelected(false);
                title.setTextColor(ContextCompat.getColor(context,R.color.black));
            } else {
                sSelectedItems.put(sPosition, false);
                title.setTextColor(ContextCompat.getColor(context, R.color.white));
                sSelectedItems.put(getAdapterPosition(), true);
                rlMain.setSelected(true);
                thumbnail.setSelected(true);
            }
            sClickListener.onItemClick(getAdapterPosition());
        }




    }


    private int select(String str) {
        if ("1".equals(str)) {
            return R.drawable.selector_profile_self;
        }
        if ("2".equals(str)) {
            return R.drawable.selector_profile_family;
        }
        if ("3".equals(str)) {
            return R.drawable.selector_profile_son;
        }
        if ("4".equals(str)) {
            return R.drawable.selector_profile_daughter;
        }
        if ("5".equals(str)) {
            return R.drawable.selector_profile_brother;
        }
        if ("6".equals(str)) {
            return R.drawable.selector_profile_sister;
        }
        return R.drawable.self;
    }

    public void selected(int position) {
        sPosition = position;
        notifyDataSetChanged();
    }



    public interface UpdateDataClickListener {
        void onItemClick(int position);
    }
}
