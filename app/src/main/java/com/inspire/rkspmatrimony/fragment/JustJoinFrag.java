package com.inspire.rkspmatrimony.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inspire.rkspmatrimony.Models.LoginDTO;
import com.inspire.rkspmatrimony.Models.UserDTO;
import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.adapter.AdapterJustJoin;
import com.inspire.rkspmatrimony.https.HttpsRequest;
import com.inspire.rkspmatrimony.interfaces.Consts;
import com.inspire.rkspmatrimony.interfaces.Helper;
import com.inspire.rkspmatrimony.network.NetworkManager;
import com.inspire.rkspmatrimony.sharedprefrence.SharedPrefrence;
import com.inspire.rkspmatrimony.utils.ProjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JustJoinFrag extends Fragment {
    private String TAG = JustJoinFrag.class.getSimpleName();
    private View view;
    private RecyclerView rvMatch;
    LinearLayoutManager mLayoutManager;
    private AdapterJustJoin adapterJustJoin;
    private ArrayList<UserDTO> joinDTOList;
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;

    private int current = 1;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_just_join, container, false);
        prefrence = SharedPrefrence.getInstance(getActivity());
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        setUiAction(view);
        return view;
    }

    public void setUiAction(View view) {
        rvMatch = view.findViewById(R.id.rvMatch);


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
        new HttpsRequest(Consts.JUST_JOIN_API, getparm(), getActivity()).stringPost(TAG, new Helper() {
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
                    }


                } else {
                    ProjectUtils.showToast(getActivity(), msg);
                }
            }
        });
    }

    public HashMap<String, String> getparm() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put(Consts.USER_ID, loginDTO.getUser_id());
        parms.put(Consts.TOKEN, loginDTO.getAccess_token());

        if (loginDTO.getGender().equalsIgnoreCase("M")) {
            parms.put(Consts.GENDER, "M");
        } else {
            parms.put(Consts.GENDER, "F");
        }
        Log.e("parms", parms.toString());
        return parms;
    }

    public void showData() {
        adapterJustJoin = new AdapterJustJoin(joinDTOList, JustJoinFrag.this);
        rvMatch.setAdapter(adapterJustJoin);
    }

}