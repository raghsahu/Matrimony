package com.samyotech.matrimony.activity.subscription;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samyotech.matrimony.Models.LoginDTO;
import com.samyotech.matrimony.Models.SubscriptionHistoryDto;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.adapter.SubscriptionHisAdapter;
import com.samyotech.matrimony.https.HttpsRequest;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.interfaces.Helper;
import com.samyotech.matrimony.network.NetworkManager;
import com.samyotech.matrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.matrimony.utils.ProjectUtils;
import com.samyotech.matrimony.view.CustomTextViewBold;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubscriptionHistory extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageView IVback;
    private RecyclerView RVitemlistt;
    private Context mContext;
    private SubscriptionHisAdapter subscriptionHisAdapter;
    private ArrayList<SubscriptionHistoryDto> subscriptionHistoryList;
    private CustomTextViewBold tvNo;
    private String TAG = SubscriptionHistory.class.getCanonicalName();
    private LinearLayoutManager mLayoutManager;
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView svSearch;
    private RelativeLayout rlSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectUtils.Fullscreen(this);
        setContentView(R.layout.activity_subscription_history);
        mContext = SubscriptionHistory.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        setUiAction();
    }

    private void setUiAction() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        rlSearch = findViewById(R.id.rlSearch);
        svSearch = findViewById(R.id.svSearch);
        tvNo = findViewById(R.id.tvNo);
        IVback = findViewById(R.id.IVback);
        RVitemlistt = findViewById(R.id.RVitemlistt);

        mLayoutManager = new LinearLayoutManager(mContext);
        RVitemlistt.setLayoutManager(mLayoutManager);

        IVback.setOnClickListener(this);

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() > 0) {
                    subscriptionHisAdapter.filter(newText.toString());

                } else {


                }
                return false;
            }
        });


        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        Log.e("Runnable", "FIRST");
                                        if (NetworkManager.isConnectToInternet(mContext)) {
                                            swipeRefreshLayout.setRefreshing(true);
                                            getSubHistory();


                                        } else {
                                            ProjectUtils.InternetAlertDialog(SubscriptionHistory.this, getString(R.string.internet_concation), getString(R.string.internet_concation));
                                            //    ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
                                        }
                                    }
                                }
        );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IVback:
                finish();
                break;

        }
    }


    public void getSubHistory() {
        new HttpsRequest(Consts.GET_MY_SUBSCRIPTION_HISTORY_API, getparm(), mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                swipeRefreshLayout.setRefreshing(false);
                if (flag) {
                    tvNo.setVisibility(View.GONE);
                    RVitemlistt.setVisibility(View.VISIBLE);
                    rlSearch.setVisibility(View.VISIBLE);
                    try {
                        subscriptionHistoryList = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<SubscriptionHistoryDto>>() {
                        }.getType();
                        subscriptionHistoryList = (ArrayList<SubscriptionHistoryDto>) new Gson().fromJson(response.getJSONArray("data").toString(), getpetDTO);
                        showData();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    // ProjectUtils.showToast(mContext, msg);
                    tvNo.setVisibility(View.VISIBLE);
                    RVitemlistt.setVisibility(View.GONE);
                    rlSearch.setVisibility(View.GONE);
                }
            }
        });
    }

    public HashMap<String, String> getparm() {
        HashMap<String, String> parms = new HashMap<>();
        parms.put(Consts.USER_ID, loginDTO.getData().getId());
        return parms;
    }

    public void showData() {
        subscriptionHisAdapter = new SubscriptionHisAdapter(mContext, subscriptionHistoryList);
        RVitemlistt.setAdapter(subscriptionHisAdapter);

    }

    @Override
    public void onRefresh() {
        Log.e("ONREFREST_Firls", "FIRS");
        getSubHistory();
    }
}
