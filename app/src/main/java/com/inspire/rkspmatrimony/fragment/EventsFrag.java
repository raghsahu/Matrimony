package com.inspire.rkspmatrimony.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inspire.rkspmatrimony.Models.LoginDTO;
import com.inspire.rkspmatrimony.Models.NewsDTO;
import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.activity.dashboard.Dashboard;
import com.inspire.rkspmatrimony.adapter.AdapterNews;
import com.inspire.rkspmatrimony.https.HttpsRequest;
import com.inspire.rkspmatrimony.interfaces.Consts;
import com.inspire.rkspmatrimony.interfaces.Helper;
import com.inspire.rkspmatrimony.sharedprefrence.SharedPrefrence;
import com.inspire.rkspmatrimony.utils.ProjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsFrag extends Fragment {

    private View view;
    private Dashboard dashboard;
    private String TAG = EventsFrag.class.getSimpleName();
    private AdapterNews adapterNews;
    private ArrayList<NewsDTO> newsDTOSList;
    private ListView rvWall;
    private LoginDTO loginDTO;
    private SharedPrefrence prefrence;
    private LayoutInflater adaptorInflator;
    HashMap<String, String> params = new HashMap<>();
    private LinearLayout rlView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);
        dashboard.headerNameTV.setText(getResources().getString(R.string.nav_events));

        adaptorInflator = LayoutInflater.from(getActivity());
        prefrence = SharedPrefrence.getInstance(getActivity());
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        setUi(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dashboard = (Dashboard) activity;

    }


    public void setUi(View view) {
        rlView = view.findViewById(R.id.rlView);
        rvWall = view.findViewById(R.id.rvWall);
    }


    @Override
    public void onResume() {
        super.onResume();
        getPost();
    }

    public void showDataList() {
        if (newsDTOSList.size() != 0) {
            rvWall.setVisibility(View.VISIBLE);
            rlView.setVisibility(View.GONE);
            adapterNews = new AdapterNews(newsDTOSList, getActivity(), adaptorInflator);
            rvWall.setAdapter(adapterNews);
        } else {
            rlView.setVisibility(View.VISIBLE);
            rvWall.setVisibility(View.GONE);

        }

    }


    public void getPost() {
          ProjectUtils.showProgressDialog(getActivity(), false, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.GET_EVENTS_API, getPostParam(), getActivity()).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {

                if (flag) {
                    Type listType = new TypeToken<List<NewsDTO>>() {
                    }.getType();
                    try {
                        newsDTOSList = new ArrayList<>();
                        newsDTOSList = (ArrayList<NewsDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), listType);

                        showDataList();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    ProjectUtils.showToast(getActivity(), msg);

                }
            }

        });
    }

    public Map<String, String> getPostParam() {
        params.put(Consts.TOKEN, loginDTO.getAccess_token());
        Log.e(TAG + " --- Params", params.toString());
        return params;
    }
}
