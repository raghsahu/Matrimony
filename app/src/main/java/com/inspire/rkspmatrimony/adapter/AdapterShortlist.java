package com.inspire.rkspmatrimony.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.inspire.rkspmatrimony.Models.LoginDTO;
import com.inspire.rkspmatrimony.Models.UserDTO;
import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.activity.profile_other.ProfileOther;
import com.inspire.rkspmatrimony.fragment.ShortlistedFrag;
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

public class AdapterShortlist extends RecyclerView.Adapter<AdapterShortlist.MatchesHolder> {

    private Context context;
    private ArrayList<UserDTO> joinDTOList;
    private ShortlistedFrag shortlistedFrag;
    String dob = "";
    String[] arrOfStr;
    private HashMap<String, String> parms = new HashMap<>();
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;
    private String TAG = AdapterShortlist.class.getSimpleName();

    public AdapterShortlist(ArrayList<UserDTO> joinDTOList, ShortlistedFrag shortlistedFrag) {
        this.joinDTOList = joinDTOList;
        this.shortlistedFrag = shortlistedFrag;
        this.context = shortlistedFrag.getActivity();
        prefrence = SharedPrefrence.getInstance(context);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);

        parms.put(Consts.USER_ID, loginDTO.getUser_id());
        parms.put(Consts.TOKEN, loginDTO.getAccess_token());


    }

    @NonNull
    @Override
    public MatchesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shortlisted, parent, false);
        MatchesHolder holder = new MatchesHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final MatchesHolder holder, final int position) {

        holder.rlClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileOther.class);
                intent.putExtra(Consts.USER_DTO, joinDTOList.get(position));
                context.startActivity(intent);

            }
        });
        if (joinDTOList.get(position).getGender().equalsIgnoreCase("M")) {
            holder.tvjoinedstatus.setText("He was joined " + ProjectUtils.changeDateFormate(joinDTOList.get(position).getCreated_at()));

        } else {
            holder.tvjoinedstatus.setText("She was joined " + ProjectUtils.changeDateFormate(joinDTOList.get(position).getCreated_at()));
        }
        holder.tvProfession.setText(joinDTOList.get(position).getOccupation());

        Glide.with(context).
                load(Consts.IMAGE_URL+joinDTOList.get(position).getAvatar_medium())
                .placeholder(R.drawable.default_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivProfileImage);


        try {
            dob = joinDTOList.get(position).getDob();
            arrOfStr = dob.split("-", 3);

            Log.e("date of birth", arrOfStr[0] + " " + arrOfStr[1] + " " + arrOfStr[2]);
            holder.tvYearandheight.setText(ProjectUtils.getAge(Integer.parseInt(arrOfStr[0]), Integer.parseInt(arrOfStr[1]), Integer.parseInt(arrOfStr[2])) + " years " + joinDTOList.get(position).getHeight());


        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tvEducation.setText(joinDTOList.get(position).getQualification());
        holder.tvGotra.setText(joinDTOList.get(position).getGotra());
        holder.tvIncome.setText(joinDTOList.get(position).getIncome());
        holder.tvCity.setText(joinDTOList.get(position).getCity());
        holder.tvmarrigestatus.setText(joinDTOList.get(position).getMarital_status());
        holder.tvName.setText(joinDTOList.get(position).getName());

        if (joinDTOList.get(position).getShortlisted() == 0) {
            holder.ivShortList.setImageDrawable(shortlistedFrag.getResources().getDrawable(R.drawable.ic_shortlist));
        } else {
            holder.ivShortList.setImageDrawable(shortlistedFrag.getResources().getDrawable(R.drawable.ic_shortlist_done));
        }
        holder.llShortList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (joinDTOList.get(position).getShortlisted() == 0) {
                    shortListed(holder, position);

                } else {
                    removeShortListed(holder, position);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return joinDTOList.size();
    }


    public class MatchesHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlClick;
        public ImageView ivProfileImage,ivShortList;
        public CustomTextView tvjoinedstatus, tvProfession, tvYearandheight, tvEducation, tvGotra, tvIncome, tvCity, tvmarrigestatus;
        public CustomTextViewBold tvName;
        public LinearLayout llShortList, llWhatsApp, llContact;

        public MatchesHolder(View itemView) {
            super(itemView);
            rlClick = (RelativeLayout) itemView.findViewById(R.id.rlClick);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvjoinedstatus = (CustomTextView) itemView.findViewById(R.id.tvjoinedstatus);
            tvProfession = (CustomTextView) itemView.findViewById(R.id.tvProfession);
            tvYearandheight = (CustomTextView) itemView.findViewById(R.id.tvYearandheight);
            tvEducation = (CustomTextView) itemView.findViewById(R.id.tvEducation);
            tvGotra = (CustomTextView) itemView.findViewById(R.id.tvGotra);
            tvIncome = (CustomTextView) itemView.findViewById(R.id.tvIncome);
            tvCity = (CustomTextView) itemView.findViewById(R.id.tvCity);
            tvmarrigestatus = (CustomTextView) itemView.findViewById(R.id.tvmarrigestatus);
            tvName = (CustomTextViewBold) itemView.findViewById(R.id.tvName);
            llShortList = (LinearLayout) itemView.findViewById(R.id.llShortList);
            llWhatsApp = (LinearLayout) itemView.findViewById(R.id.llWhatsApp);
            llContact = (LinearLayout) itemView.findViewById(R.id.llContact);
            ivShortList = (ImageView) itemView.findViewById(R.id.ivShortList);
        }


    }

    public void shortListed(final MatchesHolder holder, int pos) {
        parms.put(Consts.SHORT_LISTED_ID, joinDTOList.get(pos).getId());
        new HttpsRequest(Consts.SET_SHORTLISTED_API, parms, context).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    holder.ivShortList.setImageDrawable(shortlistedFrag.getResources().getDrawable(R.drawable.ic_shortlist_done));
                } else {
                    ProjectUtils.showToast(context, msg);
                }
            }
        });
    }
    public void removeShortListed(final MatchesHolder holder, int pos) {
        parms.put(Consts.SHORT_LISTED_ID, joinDTOList.get(pos).getId());
        new HttpsRequest(Consts.REMOVE_SHORTLISTED_API, parms, context).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    holder.ivShortList.setImageDrawable(shortlistedFrag.getResources().getDrawable(R.drawable.ic_shortlist));
                } else {
                    ProjectUtils.showToast(context, msg);
                }
            }
        });
    }
}
