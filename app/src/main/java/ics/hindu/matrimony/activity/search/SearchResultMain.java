package ics.hindu.matrimony.activity.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import ics.hindu.matrimony.models.Friend;
import ics.hindu.matrimony.models.ListFriend;
import ics.hindu.matrimony.models.MatchesDTO;
import ics.hindu.matrimony.models.UserDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.activity.ChatActivity;
import ics.hindu.matrimony.adapter.AdapterSearchMain;
import ics.hindu.matrimony.database.FriendDB;
import ics.hindu.matrimony.https.HttpsRequest;
import ics.hindu.matrimony.interfaces.Consts;
import ics.hindu.matrimony.interfaces.Helper;
import ics.hindu.matrimony.network.NetworkManager;
import ics.hindu.matrimony.service.ServiceUtils;
import ics.hindu.matrimony.utils.EndlessRecyclerOnScrollListener;
import ics.hindu.matrimony.utils.ProjectUtils;
import ics.hindu.matrimony.utils.StaticConfig;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SearchResultMain extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private LinearLayout llBack;
    private String TAG = SearchResultMain.class.getSimpleName();
    private View view;
    private RecyclerView rvMatch;
    LinearLayoutManager mLayoutManager;
    private AdapterSearchMain adapterSearchMain;
    private ArrayList<UserDTO> userDTOList;
    private ArrayList<UserDTO> tempList;

    private MatchesDTO matchesDTO;

    private int currentVisibleItemCount = 0;
    int page = 1;
    boolean request = false;
    private ProgressBar pb;
    HashMap<String, String> parms = new HashMap<>();
    private FirebaseAuth mAuth;

    private ArrayList<String> listFriendID = null;
    private CountDownTimer detectFriendOnline;
    private ListFriend dataListFriend = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mContext = SearchResultMain.this;

        mAuth = FirebaseAuth.getInstance();
        checkUserFirebase();
        if (getIntent().hasExtra(Consts.SEARCH_PARAM)) {
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

                } else {
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
        new HttpsRequest(Consts.ALL_PROFILES_API + "?page=" + page, parms, mContext).stringPost(TAG, new Helper() {
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
        adapterSearchMain = new AdapterSearchMain(userDTOList, SearchResultMain.this);
        rvMatch.setAdapter(adapterSearchMain);
        rvMatch.smoothScrollToPosition(currentVisibleItemCount);
        rvMatch.scrollToPosition(currentVisibleItemCount - 1);

    }

    public void callLog(String emailID) {
        findIDEmail(emailID);

    }


    /**
     * TIm id cua email tren server
     *
     * @param em
     */
    private void findIDEmail(final String em) {
        FirebaseDatabase.getInstance().getReference().child("user").orderByChild("email").equalTo(em).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    //email not found
                } else {
                    String id = ((HashMap) dataSnapshot.getValue()).keySet().iterator().next().toString();
                    if (id.equals(StaticConfig.UID)) {
                    } else {
                        HashMap userMap = (HashMap) ((HashMap) dataSnapshot.getValue()).get(id);
                        Friend user = new Friend();
                        user.name = (String) userMap.get("name");
                        user.email = (String) userMap.get("email");
                        user.avata = (String) userMap.get("avata");
                        user.id = id;
                        user.idRoom = id.compareTo(StaticConfig.UID) > 0 ? (StaticConfig.UID + id).hashCode() + "" : "" + (id + StaticConfig.UID).hashCode();
                        checkBeforAddFriend(id, user, em);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Lay danh sach friend cua một UID
     */
    private void checkBeforAddFriend(final String idFriend, Friend userInfo, String emaill) {


        //Check xem da ton tai id trong danh sach id chua
        if (listFriendID.contains(idFriend)) {

        } else {
            addFriend(idFriend, true);
            listFriendID.add(idFriend);
            dataListFriend.getListFriend().add(userInfo);
            FriendDB.getInstance(mContext).addFriend(userInfo);
        }
        perfromclick(emaill);
        ;

    }

    /**
     * Add friend
     *
     * @param idFriend
     */
    private void addFriend(final String idFriend, boolean isIdFriend) {
        if (idFriend != null) {
            if (isIdFriend) {
                FirebaseDatabase.getInstance().getReference().child("friend/" + StaticConfig.UID).push().setValue(idFriend)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    addFriend(idFriend, false);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            } else {
                FirebaseDatabase.getInstance().getReference().child("friend/" + idFriend).push().setValue(StaticConfig.UID).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            addFriend(null, false);
                        }
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
            }
        } else {
        }
    }


    private void getListFriendUId() {
        FirebaseDatabase.getInstance().getReference().child("friend/" + StaticConfig.UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    HashMap mapRecord = (HashMap) dataSnapshot.getValue();
                    Iterator listKey = mapRecord.keySet().iterator();
                    while (listKey.hasNext()) {
                        String key = listKey.next().toString();
                        listFriendID.add(mapRecord.get(key).toString());
                    }
                    getAllFriendInfo(0);
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getAllFriendInfo(final int index) {
        if (index == listFriendID.size()) {

            detectFriendOnline.start();
        } else {
            final String id = listFriendID.get(index);
            FirebaseDatabase.getInstance().getReference().child("user/" + id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        Friend user = new Friend();
                        HashMap mapUserInfo = (HashMap) dataSnapshot.getValue();
                        user.name = (String) mapUserInfo.get("name");
                        user.email = (String) mapUserInfo.get("email");
                        user.avata = (String) mapUserInfo.get("avata");
                        user.id = id;
                        user.idRoom = id.compareTo(StaticConfig.UID) > 0 ? (StaticConfig.UID + id).hashCode() + "" : "" + (id + StaticConfig.UID).hashCode();
                        dataListFriend.getListFriend().add(user);
                        FriendDB.getInstance(mContext).addFriend(user);
                    }
                    getAllFriendInfo(index + 1);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void perfromclick(String em) {

        Friend listFriend = new Friend();
        listFriend = dataListFriend.getByEmail(em);

        if (listFriend.email.equalsIgnoreCase(em)) {
            final String name = listFriend.name;
            final String id = listFriend.id;
            final String idRoom = listFriend.idRoom;
            final String avata = listFriend.avata;

            Intent intent = new Intent(mContext, ChatActivity.class);
            intent.putExtra(StaticConfig.INTENT_KEY_CHAT_FRIEND, name);
            ArrayList<CharSequence> idFriend = new ArrayList<CharSequence>();
            idFriend.add(id);
            intent.putCharSequenceArrayListExtra(StaticConfig.INTENT_KEY_CHAT_ID, idFriend);
            intent.putExtra(StaticConfig.INTENT_KEY_CHAT_ROOM_ID, idRoom);
            ChatActivity.bitmapAvataFriend = new HashMap<>();
            if (!avata.equals(StaticConfig.STR_DEFAULT_BASE64)) {
                ChatActivity.bitmapAvataFriend.put(id, avata);
            } else {
                ChatActivity.bitmapAvataFriend.put(id, "");
            }
            startActivity(intent);
        }


    }


    public void checkUserFirebase() {
        detectFriendOnline = new CountDownTimer(System.currentTimeMillis(), StaticConfig.TIME_TO_REFRESH) {
            @Override
            public void onTick(long l) {
                ServiceUtils.updateFriendStatus(mContext, dataListFriend);
                ServiceUtils.updateUserStatus(mContext);
            }

            @Override
            public void onFinish() {

            }
        };
        if (dataListFriend == null) {
            dataListFriend = FriendDB.getInstance(mContext).getListFriend();
            if (dataListFriend.getListFriend().size() > 0) {
                listFriendID = new ArrayList<>();
                for (Friend friend : dataListFriend.getListFriend()) {
                    listFriendID.add(friend.id);
                }
                detectFriendOnline.start();
            }
        }

        if (listFriendID == null) {
            listFriendID = new ArrayList<>();
            getListFriendUId();
        }
    }
}
