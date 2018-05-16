package com.samyotech.matrimony.adapter;

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
import com.samyotech.matrimony.Models.LoginDTO;
import com.samyotech.matrimony.Models.MatchesDTO;
import com.samyotech.matrimony.Models.UserDTO;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.editprofile.AboutFamilyActivity;
import com.samyotech.matrimony.activity.profile_other.ProfileOther;
import com.samyotech.matrimony.fragment.MatchesFrag;
import com.samyotech.matrimony.fragment.registration.ProfileFor;
import com.samyotech.matrimony.https.HttpsRequest;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.interfaces.Helper;
import com.samyotech.matrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.matrimony.utils.ProjectUtils;
import com.samyotech.matrimony.view.CustomTextView;
import com.samyotech.matrimony.view.CustomTextViewBold;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterMatches extends RecyclerView.Adapter<AdapterMatches.MatchesHolder> {
    private String TAG = AdapterMatches.class.getSimpleName();
    private Context context;
    private ArrayList<UserDTO> userDTOList;
    private MatchesFrag matchesFrag;
    String dob = "";
    String[] arrOfStr;
    private HashMap<String, String> parms = new HashMap<>();
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;

    public AdapterMatches(ArrayList<UserDTO> userDTOList, MatchesFrag matchesFrag) {
        this.userDTOList = userDTOList;
        this.matchesFrag = matchesFrag;
        this.context = matchesFrag.getActivity();
        prefrence = SharedPrefrence.getInstance(context);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);

        parms.put(Consts.USER_ID, loginDTO.getUser_id());
        parms.put(Consts.TOKEN, loginDTO.getAccess_token());

    }

    @NonNull
    @Override
    public MatchesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_matches, parent, false);
        MatchesHolder holder = new MatchesHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final MatchesHolder holder, final int position) {

        holder.rlClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileOther.class);
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

            Log.e("date of birth", arrOfStr[0] + " " + arrOfStr[1] + " " + arrOfStr[2]);
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

        Glide.with(context).
                load(Consts.IMAGE_URL+userDTOList.get(position).getAvatar_medium())
                .placeholder(R.drawable.default_error)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivProfileImage);

        if (userDTOList.get(position).getShortlisted() == 0) {
            holder.ivShortList.setImageDrawable(matchesFrag.getResources().getDrawable(R.drawable.ic_shortlist));
        } else {
            holder.ivShortList.setImageDrawable(matchesFrag.getResources().getDrawable(R.drawable.ic_shortlist_done));
        }

        holder.llShortList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userDTOList.get(position).getShortlisted() == 0) {
                    shortListed(holder, position);

                } else {
                    removeShortListed(holder, position);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return userDTOList.size();
    }


    public class MatchesHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlClick;
        public ImageView ivProfileImage, ivShortList;
        public CustomTextView tvjoinedstatus, tvProfession, tvYearandheight, tvEducation, tvGotra, tvIncome, tvCity, tvmarrigestatus;
        public CustomTextViewBold tvName;
        public LinearLayout llShortList, llWhatsApp, llContact;

        public MatchesHolder(View itemView) {
            super(itemView);
            rlClick = (RelativeLayout) itemView.findViewById(R.id.rlClick);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            ivShortList = (ImageView) itemView.findViewById(R.id.ivShortList);
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

        }


    }

    public void shortListed(final MatchesHolder holder, int pos) {
        parms.put(Consts.SHORT_LISTED_ID, userDTOList.get(pos).getId());
        new HttpsRequest(Consts.SET_SHORTLISTED_API, parms, context).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    holder.ivShortList.setImageDrawable(matchesFrag.getResources().getDrawable(R.drawable.ic_shortlist_done));
                } else {
                    ProjectUtils.showToast(context, msg);
                }
            }
        });
    }
  public void removeShortListed(final MatchesHolder holder, int pos) {
        parms.put(Consts.SHORT_LISTED_ID, userDTOList.get(pos).getId());
        new HttpsRequest(Consts.REMOVE_SHORTLISTED_API, parms, context).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    holder.ivShortList.setImageDrawable(matchesFrag.getResources().getDrawable(R.drawable.ic_shortlist));
                } else {
                    ProjectUtils.showToast(context, msg);
                }
            }
        });
    }

}
