package com.samyotech.matrimony.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samyotech.matrimony.Models.LoginDTO;
import com.samyotech.matrimony.Models.MatchesDTO;
import com.samyotech.matrimony.Models.UserDTO;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.dashboard.Dashboard;
import com.samyotech.matrimony.adapter.AdapterReceiveInterest;
import com.samyotech.matrimony.https.HttpsRequest;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.interfaces.Helper;
import com.samyotech.matrimony.network.NetworkManager;
import com.samyotech.matrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.matrimony.utils.EndlessRecyclerOnScrollListener;
import com.samyotech.matrimony.utils.ProjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReceviedInterest extends Fragment {
    private View view;
    private String TAG = ReceviedInterest.class.getSimpleName();
    private RecyclerView rvMatch;
    LinearLayoutManager mLayoutManager;
    private AdapterReceiveInterest adapterReceiveInterest;
    private ArrayList<UserDTO> receiveInterestList;
    private ArrayList<UserDTO> tempList;
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;

    private int currentVisibleItemCount = 0;
    int page = 1;
    boolean request = false;
    private MatchesDTO matchesDTO;
    private ProgressBar pb;
    private LinearLayout rlView;
    public Dashboard dashboard;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recevied_interest, container, false);
        prefrence = SharedPrefrence.getInstance(getActivity());
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        setUiAction(view);
        return view;
    }

    public void setUiAction(View view) {
        tempList = new ArrayList<>();
        rlView = view.findViewById(R.id.rlView);
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

                } else {
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
        new HttpsRequest(Consts.GET_INTEREST_API + "?page=" + page, getparm(), getActivity()).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    matchesDTO = new Gson().fromJson(response.toString(), MatchesDTO.class);
                    request = matchesDTO.isHas_more_pages();
                    pb.setVisibility(View.GONE);
                    showData();
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
        parms.put(Consts.USER_ID, loginDTO.getData().getId());
        parms.put(Consts.TOKEN, loginDTO.getAccess_token());
        parms.put(Consts.TYPE, Consts.RECIEVED);

        if (loginDTO.getData().getGender().equalsIgnoreCase("M")) {
            parms.put(Consts.GENDER, "M");
        } else {
            parms.put(Consts.GENDER, "F");
        }
        Log.e("parms", parms.toString());
        return parms;
    }

    public void showData() {
        receiveInterestList = new ArrayList<>();
        receiveInterestList = matchesDTO.getData();
        tempList.addAll(receiveInterestList);
        receiveInterestList = tempList;
        if (receiveInterestList.size()>0){
            rlView.setVisibility(View.GONE);
            rvMatch.setVisibility(View.VISIBLE);
            adapterReceiveInterest = new AdapterReceiveInterest(receiveInterestList, ReceviedInterest.this);
            rvMatch.setAdapter(adapterReceiveInterest);
            rvMatch.smoothScrollToPosition(currentVisibleItemCount);
            rvMatch.scrollToPosition(currentVisibleItemCount - 1);
        }else {
            rlView.setVisibility(View.VISIBLE);
            rvMatch.setVisibility(View.GONE);
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dashboard = (Dashboard) getActivity();
    }
}
