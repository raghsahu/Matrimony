package com.inspire.rkspmatrimony.adapter;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.inspire.rkspmatrimony.Models.LoginDTO;
import com.inspire.rkspmatrimony.Models.UserDTO;
import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.activity.profile_other.ProfileOther;
import com.inspire.rkspmatrimony.fragment.JustJoinFrag;
import com.inspire.rkspmatrimony.https.HttpsRequest;
import com.inspire.rkspmatrimony.interfaces.Consts;
import com.inspire.rkspmatrimony.interfaces.Helper;
import com.inspire.rkspmatrimony.sharedprefrence.SharedPrefrence;
import com.inspire.rkspmatrimony.utils.ProjectUtils;
import com.inspire.rkspmatrimony.utils.SpinnerDialog;
import com.inspire.rkspmatrimony.view.CustomButton;
import com.inspire.rkspmatrimony.view.CustomTextView;
import com.inspire.rkspmatrimony.view.CustomTextViewBold;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterJustJoin extends RecyclerView.Adapter<AdapterJustJoin.MatchesHolder> {

    private Context context;
    private ArrayList<UserDTO> joinDTOList;
    private JustJoinFrag justJoinFrag;
    String dob = "";
    String[] arrOfStr;
    private HashMap<String, String> parms = new HashMap<>();
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;
    private String TAG = AdapterJustJoin.class.getSimpleName();
    private HashMap<String, String> parmsSendInterest = new HashMap<>();
    private SpinnerDialog spinnerDialog;
    int CALL_PERMISSION = 101;
    public AdapterJustJoin(ArrayList<UserDTO> joinDTOList, JustJoinFrag justJoinFrag) {
        this.joinDTOList = joinDTOList;
        this.justJoinFrag = justJoinFrag;
        this.context = justJoinFrag.getActivity();
        prefrence = SharedPrefrence.getInstance(context);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);

        parms.put(Consts.USER_ID, loginDTO.getData().getId());
        parms.put(Consts.TOKEN, loginDTO.getAccess_token());


    }

    @NonNull
    @Override
    public MatchesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_just_join, parent, false);
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
            holder.ivShortList.setImageDrawable(justJoinFrag.getResources().getDrawable(R.drawable.ic_shortlist));
        } else {
            holder.ivShortList.setImageDrawable(justJoinFrag.getResources().getDrawable(R.drawable.ic_shortlist_done));
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
        if (joinDTOList.get(position).getStatus() == 0) {
        if (joinDTOList.get(position).getRequest() == 1) {
            holder.ivInterest.setImageDrawable(justJoinFrag.getResources().getDrawable(R.drawable.ic_already_sent));
            holder.tvInterest.setText(justJoinFrag.getResources().getString(R.string.interest_sent));
        } else if (joinDTOList.get(position).getRequest() == 2) {
            holder.ivInterest.setImageDrawable(justJoinFrag.getResources().getDrawable(R.drawable.ic_send_interest));
            holder.tvInterest.setText(justJoinFrag.getResources().getString(R.string.interest_accept));
        } else {
            holder.ivInterest.setImageDrawable(justJoinFrag.getResources().getDrawable(R.drawable.ic_send_interest));
            holder.tvInterest.setText(justJoinFrag.getResources().getString(R.string.send_interest));
        }
        } else {
            holder.ivInterest.setImageDrawable(justJoinFrag.getResources().getDrawable(R.drawable.ic_send_interest));
            holder.tvInterest.setText(justJoinFrag.getResources().getString(R.string.interest_accepted));
        }

        holder.llInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (joinDTOList.get(position).getStatus() == 0) {
                if (joinDTOList.get(position).getRequest() == 0) {
                    sendInterest(holder, position);

                } else if (joinDTOList.get(position).getRequest() == 1) {
                    ProjectUtils.showToast(context, justJoinFrag.getResources().getString(R.string.interset_sent_msg));
                } else if (joinDTOList.get(position).getRequest() == 2) {
                    updateInterest(holder, position);
                }
                }else {
                    ProjectUtils.showToast(context, justJoinFrag.getResources().getString(R.string.interset_accept_msg));
                }
            }
        });
        holder.llContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (joinDTOList.get(position).getMobile2().equalsIgnoreCase("")){
                    ProjectUtils.showToast(context,"Mobile number not available");
                }else {
                    if (joinDTOList.get(position).getStatus() == 0) {
                        spinnerDialog = new SpinnerDialog(justJoinFrag.getActivity(), joinDTOList.get(position).getName(), joinDTOList.get(position).getAvatar_medium(), R.style.DialogAnimations_SmileWindow);
                        spinnerDialog.showConatactDialog();
                    }else {
                        dialogshow(position);
                    }
                }


            }

        });
    }
    public void dialogshow(final int pos) {
        final Dialog dialog = new Dialog(context/*, android.R.style.Theme_Dialog*/);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_call);

        CustomButton cbOk = (CustomButton) dialog.findViewById(R.id.cbOk);
        CustomButton cbCancel = (CustomButton) dialog.findViewById(R.id.cbCancel);


        dialog.show();
        dialog.setCancelable(false);
        cbCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cbOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProjectUtils.hasPermissionInManifest(context, CALL_PERMISSION, Manifest.permission.CALL_PHONE)) {
                    try {

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + joinDTOList.get(pos).getMobile2()));
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        justJoinFrag.startActivity(callIntent);
                        dialog.dismiss();
                    } catch (Exception e) {
                        Log.e("Exception", "" + e);
                    }
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
        public ImageView ivProfileImage,ivShortList,ivInterest;
        public CustomTextView tvjoinedstatus, tvProfession, tvYearandheight, tvEducation, tvGotra, tvIncome,
                tvCity, tvmarrigestatus, tvInterest;
        public CustomTextViewBold tvName;
        public LinearLayout llShortList, llInterest, llContact;

        public MatchesHolder(View itemView) {
            super(itemView);
            rlClick = (RelativeLayout) itemView.findViewById(R.id.rlClick);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            ivInterest = (ImageView) itemView.findViewById(R.id.ivInterest);
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
            ivShortList = (ImageView) itemView.findViewById(R.id.ivShortList);
        }


    }

    public void shortListed(final MatchesHolder holder, int pos) {
        parms.put(Consts.SHORT_LISTED_ID, joinDTOList.get(pos).getId());
        new HttpsRequest(Consts.SET_SHORTLISTED_API, parms, context).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    holder.ivShortList.setImageDrawable(justJoinFrag.getResources().getDrawable(R.drawable.ic_shortlist_done));
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
                    holder.ivShortList.setImageDrawable(justJoinFrag.getResources().getDrawable(R.drawable.ic_shortlist));
                } else {
                    ProjectUtils.showToast(context, msg);
                }
            }
        });
    }

    public void sendInterest(final MatchesHolder holder, int pos) {
        parmsSendInterest.put(Consts.USER_ID, loginDTO.getData().getId());
        parmsSendInterest.put(Consts.REQUESTED_ID, joinDTOList.get(pos).getId());
        parmsSendInterest.put(Consts.TOKEN, loginDTO.getAccess_token());
        new HttpsRequest(Consts.SEND_INTEREST_API, parmsSendInterest, context).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    holder.ivInterest.setImageDrawable(justJoinFrag.getResources().getDrawable(R.drawable.ic_already_sent));
                    holder.tvInterest.setText(justJoinFrag.getResources().getString(R.string.interest_sent));
                } else {
                    ProjectUtils.showToast(context, msg);
                }
            }
        });
    }
    public void updateInterest(final MatchesHolder holder, int pos) {
        parmsSendInterest.put(Consts.USER_ID, loginDTO.getData().getId());
        parmsSendInterest.put(Consts.REQUESTED_ID, joinDTOList.get(pos).getId());
        parmsSendInterest.put(Consts.TOKEN, loginDTO.getAccess_token());
        new HttpsRequest(Consts.UPDATE_INTEREST_API, parmsSendInterest, context).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    holder.ivInterest.setImageDrawable(justJoinFrag.getResources().getDrawable(R.drawable.ic_already_sent));
                    holder.tvInterest.setText(justJoinFrag.getResources().getString(R.string.interest_accepted));
                } else {
                    ProjectUtils.showToast(context, msg);
                }
            }
        });
    }

}
