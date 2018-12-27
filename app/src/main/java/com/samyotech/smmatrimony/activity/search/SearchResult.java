package com.samyotech.smmatrimony.activity.search;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.samyotech.smmatrimony.Models.LoginDTO;
import com.samyotech.smmatrimony.Models.MatchesDTO;
import com.samyotech.smmatrimony.Models.UserDTO;
import com.samyotech.smmatrimony.R;
import com.samyotech.smmatrimony.adapter.AdapterMatches;
import com.samyotech.smmatrimony.adapter.AdapterSearchFront;
import com.samyotech.smmatrimony.fragment.MatchesFrag;
import com.samyotech.smmatrimony.https.HttpsRequest;
import com.samyotech.smmatrimony.interfaces.Consts;
import com.samyotech.smmatrimony.interfaces.Helper;
import com.samyotech.smmatrimony.network.NetworkManager;
import com.samyotech.smmatrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.smmatrimony.utils.EndlessRecyclerOnScrollListener;
import com.samyotech.smmatrimony.utils.ProjectUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchResult extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private LinearLayout llBack;
    private String TAG = SearchResult.class.getSimpleName();
    private View view;
    private RecyclerView rvMatch;
    LinearLayoutManager mLayoutManager;
    private AdapterSearchFront adapterSearchFront;
    private ArrayList<UserDTO> userDTOList;
    private ArrayList<UserDTO> tempList;
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;
    private MatchesDTO matchesDTO;

    private int currentVisibleItemCount = 0;
    int page = 1;
    boolean request = false;
    private ProgressBar pb;
    HashMap<String, String> parms = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mContext = SearchResult.this;
        if(getIntent().hasExtra(Consts.SEARCH_PARAM)){
            parms = (HashMap<String, String>) getIntent().getSerializableExtra(Consts.SEARCH_PARAM);
        }
        setUiAction();
    }
    public void setUiAction() {
        llBack = findViewById(R.id.llBack);
        llBack.setOnClickListener(this);
        tempList = new ArrayList<>();
        rvMatch = findViewById(R.id.rvMatch);
        pb = findViewById(R.id.pb);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
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
        if (NetworkManager.isConnectToInternet(mContext)) {
            getUsers();

        } else {
            ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llBack:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right,
                        R.anim.anim_slide_out_right);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    public void getUsers() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.SEARCH_API + "?page=" + page, parms, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    matchesDTO = new Gson().fromJson(response.toString(), MatchesDTO.class);
                    request = matchesDTO.isHas_more_pages();
                    pb.setVisibility(View.GONE);
                    showData();
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }
            }
        });
    }


    public void showData() {
        userDTOList = new ArrayList<>();
        userDTOList = matchesDTO.getData();
        tempList.addAll(userDTOList);
        userDTOList = tempList;
        adapterSearchFront = new AdapterSearchFront(userDTOList, SearchResult.this);
        rvMatch.setAdapter(adapterSearchFront);
        rvMatch.smoothScrollToPosition(currentVisibleItemCount);
        rvMatch.scrollToPosition(currentVisibleItemCount - 1);

    }
}
