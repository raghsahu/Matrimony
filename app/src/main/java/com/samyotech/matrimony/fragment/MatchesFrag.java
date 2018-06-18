package com.samyotech.matrimony.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.samyotech.matrimony.Models.LoginDTO;
import com.samyotech.matrimony.Models.MatchesDTO;
import com.samyotech.matrimony.Models.UserDTO;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.loginsignup.Login;
import com.samyotech.matrimony.adapter.AdapterMatches;
import com.samyotech.matrimony.https.HttpsRequest;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.interfaces.Helper;
import com.samyotech.matrimony.network.NetworkManager;
import com.samyotech.matrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.matrimony.utils.EndlessRecyclerOnScrollListener;
import com.samyotech.matrimony.utils.ProjectUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MatchesFrag extends Fragment {
    private String TAG = MatchesFrag.class.getSimpleName();
    private View view;
    private RecyclerView rvMatch;
    LinearLayoutManager mLayoutManager;
    private AdapterMatches adapterMatches;
    private ArrayList<UserDTO> userDTOList;
    private ArrayList<UserDTO> tempList;
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;
    private MatchesDTO matchesDTO;

    private int currentVisibleItemCount = 0;
    int page = 1;
    boolean request = false;
    private ProgressBar pb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_matches, container, false);
        prefrence = SharedPrefrence.getInstance(getActivity());
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        setUiAction(view);
        return view;
    }

    public void setUiAction(View view) {
        tempList = new ArrayList<>();
        rvMatch = view.findViewById(R.id.rvMatch);
        pb = view.findViewById(R.id.pb);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rvMatch.setLayoutManager(mLayoutManager);

        rvMatch.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page, int totalItemCount) {
                currentVisibleItemCount = totalItemCount;
                if (request) {
                    page = page + 1;
                    pb.setVisibility(View.VISIBLE);
                    getUsers();

                }else {
                    page = 1;

                }

            }
        });
        if (NetworkManager.isConnectToInternet(getActivity())) {
            getUsers();

        } else {
            ProjectUtils.showToast(getActivity(), getResources().getString(R.string.internet_concation));
        }

    }


    public void getUsers() {
        ProjectUtils.showProgressDialog(getActivity(), true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.ALL_PROFILES_API + "?page=" + page, getparm(), getActivity()).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    matchesDTO = new Gson().fromJson(response.toString(), MatchesDTO.class);
                    request = matchesDTO.isHas_more_pages();
                    pb.setVisibility(View.GONE);
                    showData();
                } else {
                    ProjectUtils.showToast(getActivity(), msg);
                }
            }
        });
    }

    public HashMap<String, String> getparm() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put(Consts.USER_ID, loginDTO.getData().getId());
        parms.put(Consts.TOKEN, loginDTO.getAccess_token());

        if (loginDTO.getData().getGender().equalsIgnoreCase("M")) {
            parms.put(Consts.GENDER, "M");
        } else {
            parms.put(Consts.GENDER, "F");
        }
        Log.e("parms", parms.toString());
        return parms;
    }

    public void showData() {
        userDTOList = new ArrayList<>();
        userDTOList = matchesDTO.getData();
        tempList.addAll(userDTOList);
        userDTOList = tempList;
        adapterMatches = new AdapterMatches(userDTOList, MatchesFrag.this);
        rvMatch.setAdapter(adapterMatches);
        rvMatch.smoothScrollToPosition(currentVisibleItemCount);
        rvMatch.scrollToPosition(currentVisibleItemCount - 1);

    }

}
