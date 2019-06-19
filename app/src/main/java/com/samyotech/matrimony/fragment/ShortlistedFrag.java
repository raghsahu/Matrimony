package com.samyotech.matrimony.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samyotech.matrimony.Models.LoginDTO;
import com.samyotech.matrimony.Models.UserDTO;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.dashboard.Dashboard;
import com.samyotech.matrimony.adapter.AdapterShortlist;
import com.samyotech.matrimony.https.HttpsRequest;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.interfaces.Helper;
import com.samyotech.matrimony.network.NetworkManager;
import com.samyotech.matrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.matrimony.utils.ProjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShortlistedFrag extends Fragment {

    private View view;
    public Dashboard dashboard;
    private String TAG = JustJoinFrag.class.getSimpleName();
    private RecyclerView rvMatch;
    LinearLayoutManager mLayoutManager;
    private AdapterShortlist adapterShortlist;
    private ArrayList<UserDTO> joinDTOList;
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;

    private int current = 1;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private LinearLayout rlView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shortlisted, container, false);
        dashboard.headerNameTV.setText(getResources().getString(R.string.nav_shortlisted));
        prefrence = SharedPrefrence.getInstance(getActivity());
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        setUiAction(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dashboard = (Dashboard) activity;

    }

    public void setUiAction(View view) {
        rvMatch = view.findViewById(R.id.rvMatch);
        rlView = view.findViewById(R.id.rlView);


        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvMatch.setLayoutManager(mLayoutManager);


    }

    @Override
    public void onResume() {
        super.onResume();
        if (NetworkManager.isConnectToInternet(getActivity())) {
            getUsers();

        } else {
            ProjectUtils.showToast(getActivity(), getResources().getString(R.string.internet_concation));
        }
    }

    public void getUsers() {
        ProjectUtils.showProgressDialog(getActivity(), true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.SHORTLISTED_API, getparm(), getActivity()).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    try {
                        joinDTOList = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<UserDTO>>() {
                        }.getType();
                        joinDTOList = (ArrayList<UserDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), getpetDTO);
                        showData();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        rlView.setVisibility(View.VISIBLE);
                        rvMatch.setVisibility(View.GONE);
                    }


                } else {
                    ProjectUtils.showToast(getActivity(), msg);
                    rlView.setVisibility(View.VISIBLE);
                    rvMatch.setVisibility(View.GONE);
                }
            }
        });
    }

    public HashMap<String, String> getparm() {
        HashMap<String, String> parms = new HashMap<>();
//        parms.put(Consts.USER_ID, loginDTO.getData().getId());
//        parms.put(Consts.TOKEN, loginDTO.getAccess_token());

        //************************************************************static******************
        parms.put(Consts.USER_ID, "1234");
        parms.put(Consts.TOKEN, "111111111111");
        //*******************************************************************

//        if (loginDTO.getData().getGender().equalsIgnoreCase("M")) {
//            parms.put(Consts.GENDER, "M");
//        } else {
//            parms.put(Consts.GENDER, "F");
//        }

        parms.put(Consts.GENDER, "F");
        Log.e("parms", parms.toString());
        return parms;
    }

    public void showData() {
        if (joinDTOList.size() > 0) {
            rlView.setVisibility(View.GONE);
            rvMatch.setVisibility(View.VISIBLE);
        } else {
            rlView.setVisibility(View.VISIBLE);
            rvMatch.setVisibility(View.GONE);
        }
        adapterShortlist = new AdapterShortlist(joinDTOList, ShortlistedFrag.this);
        rvMatch.setAdapter(adapterShortlist);
    }


}
