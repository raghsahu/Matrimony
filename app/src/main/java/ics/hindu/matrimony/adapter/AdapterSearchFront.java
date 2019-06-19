package ics.hindu.matrimony.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import ics.hindu.matrimony.Models.UserDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.activity.loginsignup.Login;
import ics.hindu.matrimony.activity.search.FullProfileSearch;
import ics.hindu.matrimony.activity.search.SearchResult;
import ics.hindu.matrimony.interfaces.Consts;
import ics.hindu.matrimony.sharedprefrence.SharedPrefrence;
import ics.hindu.matrimony.utils.ProjectUtils;
import ics.hindu.matrimony.view.CustomTextView;
import ics.hindu.matrimony.view.CustomTextViewBold;

import java.util.ArrayList;

public class AdapterSearchFront extends RecyclerView.Adapter<AdapterSearchFront.MatchesHolder> {
    private Context context;
    private ArrayList<UserDTO> userDTOList;
    private SearchResult searchResult;
    String dob = "";
    String[] arrOfStr;
    private SharedPrefrence prefrence;

    public AdapterSearchFront(ArrayList<UserDTO> userDTOList, SearchResult searchResult) {
        this.userDTOList = userDTOList;
        this.searchResult = searchResult;
        this.context = searchResult.getApplicationContext();
        prefrence = SharedPrefrence.getInstance(context);
    }

    @NonNull
    @Override
    public MatchesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search_front, parent, false);
        MatchesHolder holder = new MatchesHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final MatchesHolder holder, final int position) {

        holder.rlClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullProfileSearch.class);
                intent.putExtra(Consts.USER_DTO, userDTOList.get(position));
                context.startActivity(intent);

            }
        });
        if (userDTOList.get(position).getGender().equalsIgnoreCase("M")) {
            holder.tvjoinedstatus.setText("He was joined " + ProjectUtils.changeDateFormate(userDTOList.get(position).getCreated_at()));

        } else {
            holder.tvjoinedstatus.setText("She was joined " + ProjectUtils.changeDateFormate(userDTOList.get(position).getCreated_at()));
        }
        holder.tvProfession.setText(userDTOList.get(position).getOccupation());

        try {
            dob = userDTOList.get(position).getDob();
            arrOfStr = dob.split("-", 3);

            holder.tvYearandheight.setText(ProjectUtils.getAge(Integer.parseInt(arrOfStr[0]), Integer.parseInt(arrOfStr[1]), Integer.parseInt(arrOfStr[2])) + " years " + userDTOList.get(position).getHeight());


        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tvEducation.setText(userDTOList.get(position).getQualification());
        holder.tvGotra.setText(userDTOList.get(position).getGotra());
        holder.tvIncome.setText(userDTOList.get(position).getIncome());
        holder.tvCity.setText(userDTOList.get(position).getCity());
        holder.tvmarrigestatus.setText(userDTOList.get(position).getMarital_status());
        holder.tvName.setText(userDTOList.get(position).getName());

        if (userDTOList.get(position).getGender().equalsIgnoreCase("M")) {
            Glide.with(context).
                    load(Consts.IMAGE_URL + userDTOList.get(position).getAvatar_medium())
                    .placeholder(R.drawable.dummy_m)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivProfileImage);

        } else {
            Glide.with(context).
                    load(Consts.IMAGE_URL + userDTOList.get(position).getAvatar_medium())
                    .placeholder(R.drawable.dummy_f)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivProfileImage);

        }



        holder.llShortList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(searchResult, Login.class);
                searchResult.startActivity(in);
                searchResult.overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
            }
        });
        holder.llInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(searchResult, Login.class);
                searchResult.startActivity(in);
                searchResult.overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
            }
        });
        holder.llContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(searchResult, Login.class);
                searchResult.startActivity(in);
                searchResult.overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
            }

        });
    }

    @Override
    public int getItemCount() {
        return userDTOList.size();
    }


    public class MatchesHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlClick;
        public ImageView ivProfileImage, ivShortList, ivInterest;
        public CustomTextView tvjoinedstatus, tvProfession, tvYearandheight, tvEducation, tvGotra, tvIncome,
                tvCity, tvmarrigestatus, tvInterest;
        public CustomTextViewBold tvName;
        public LinearLayout llShortList, llInterest, llContact;

        public MatchesHolder(View itemView) {
            super(itemView);
            rlClick = (RelativeLayout) itemView.findViewById(R.id.rlClick);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            ivInterest = (ImageView) itemView.findViewById(R.id.ivInterest);
            ivShortList = (ImageView) itemView.findViewById(R.id.ivShortList);
            tvjoinedstatus = (CustomTextView) itemView.findViewById(R.id.tvjoinedstatus);
            tvProfession = (CustomTextView) itemView.findViewById(R.id.tvProfession);
            tvYearandheight = (CustomTextView) itemView.findViewById(R.id.tvYearandheight);
            tvEducation = (CustomTextView) itemView.findViewById(R.id.tvEducation);
            tvGotra = (CustomTextView) itemView.findViewById(R.id.tvGotra);
            tvIncome = (CustomTextView) itemView.findViewById(R.id.tvIncome);
            tvCity = (CustomTextView) itemView.findViewById(R.id.tvCity);
            tvInterest = (CustomTextView) itemView.findViewById(R.id.tvInterest);
            tvmarrigestatus = (CustomTextView) itemView.findViewById(R.id.tvmarrigestatus);
            tvName = (CustomTextViewBold) itemView.findViewById(R.id.tvName);
            llShortList = (LinearLayout) itemView.findViewById(R.id.llShortList);
            llInterest = (LinearLayout) itemView.findViewById(R.id.llInterest);
            llContact = (LinearLayout) itemView.findViewById(R.id.llContact);

        }
    }
}
