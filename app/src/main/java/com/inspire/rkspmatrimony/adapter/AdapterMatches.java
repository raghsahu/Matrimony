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
import com.inspire.rkspmatrimony.Models.MatchesDTO;
import com.inspire.rkspmatrimony.Models.UserDTO;
import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.activity.editprofile.AboutFamilyActivity;
import com.inspire.rkspmatrimony.activity.profile_other.ProfileOther;
import com.inspire.rkspmatrimony.fragment.MatchesFrag;
import com.inspire.rkspmatrimony.fragment.registration.ProfileFor;
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

public class AdapterMatches extends RecyclerView.Adapter<AdapterMatches.MatchesHolder> {
    private String TAG = AdapterMatches.class.getSimpleName();
    private Context context;
    private ArrayList<UserDTO> userDTOList;
    private MatchesFrag matchesFrag;
    String dob = "";
    String[] arrOfStr;
    private HashMap<String, String> parms = new HashMap<>();
    private HashMap<String, String> parmsSendInterest = new HashMap<>();
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;

    public AdapterMatches(ArrayList<UserDTO> userDTOList, MatchesFrag matchesFrag) {
        this.userDTOList = userDTOList;
        this.matchesFrag = matchesFrag;
        this.context = matchesFrag.getActivity();
        prefrence = SharedPrefrence.getInstance(context);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);

        parms.put(Consts.USER_ID, loginDTO.getData().getId());
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
                load(Consts.IMAGE_URL + userDTOList.get(position).getAvatar_medium())
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


        if (userDTOList.get(position).getRequest() == 2) {
            holder.ivInterest.setImageDrawable(matchesFrag.getResources().getDrawable(R.drawable.ic_already_sent));
            holder.tvInterest.setText(matchesFrag.getResources().getString(R.string.interest_sent));
        } else if (userDTOList.get(position).getRequest() == 1) {
            holder.ivInterest.setImageDrawable(matchesFrag.getResources().getDrawable(R.drawable.ic_send_interest));
            holder.tvInterest.setText(matchesFrag.getResources().getString(R.string.interest_accept));
        } else {
            holder.ivInterest.setImageDrawable(matchesFrag.getResources().getDrawable(R.drawable.ic_send_interest));
            holder.tvInterest.setText(matchesFrag.getResources().getString(R.string.send_interest));
        }


        holder.llInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userDTOList.get(position).getRequest() == 0) {
                    sendInterest(holder, position);

                } else if (userDTOList.get(position).getRequest() == 2) {
                    ProjectUtils.showToast(context, matchesFrag.getResources().getString(R.string.interset_sent_msg));
                } else if (userDTOList.get(position).getRequest() == 1) {
                    updateInterest(holder, position);
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

    public void sendInterest(final MatchesHolder holder, int pos) {
        parmsSendInterest.put(Consts.USER_ID, userDTOList.get(pos).getId());
        parmsSendInterest.put(Consts.REQUESTED_ID, loginDTO.getData().getId());
        parmsSendInterest.put(Consts.TOKEN, loginDTO.getAccess_token());
        new HttpsRequest(Consts.SEND_INTEREST_API, parmsSendInterest, context).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    holder.ivInterest.setImageDrawable(matchesFrag.getResources().getDrawable(R.drawable.ic_already_sent));
                    holder.tvInterest.setText(matchesFrag.getResources().getString(R.string.interest_sent));
                } else {
                    ProjectUtils.showToast(context, msg);
                }
            }
        });
    }


    public void updateInterest(final MatchesHolder holder, int pos) {
        parmsSendInterest.put(Consts.USER_ID, userDTOList.get(pos).getId());
        parmsSendInterest.put(Consts.REQUESTED_ID, loginDTO.getData().getId());
        parmsSendInterest.put(Consts.TOKEN, loginDTO.getAccess_token());
        new HttpsRequest(Consts.UPDATE_INTEREST_API, parmsSendInterest, context).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    holder.ivInterest.setImageDrawable(matchesFrag.getResources().getDrawable(R.drawable.ic_already_sent));
                    holder.tvInterest.setText(matchesFrag.getResources().getString(R.string.interest_accepted));
                } else {
                    ProjectUtils.showToast(context, msg);
                }
            }
        });
    }

}
