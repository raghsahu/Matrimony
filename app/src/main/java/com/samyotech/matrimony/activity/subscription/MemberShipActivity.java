package com.samyotech.matrimony.activity.subscription;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.samyotech.matrimony.Models.LoginDTO;
import com.samyotech.matrimony.Models.PackagesDTO;
import com.samyotech.matrimony.Models.SubscriptionDto;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.adapter.PackageslistAdapter;
import com.samyotech.matrimony.https.HttpsRequest;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.interfaces.Helper;
import com.samyotech.matrimony.network.NetworkManager;
import com.samyotech.matrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.matrimony.utils.ProjectUtils;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberShipActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {

    private String TAG = MemberShipActivity.class.getCanonicalName();
    private Context mContext;
    private ArrayList<PackagesDTO> packagesDTOlist;
    private PackageslistAdapter packageslistAdapter;
    private SharedPrefrence prefrence;
    private PackagesDTO packagesDTO;
    private DiscreteScrollView itemPicker;
    private LoginDTO loginDTO;
    private ImageView ivBack;
    private HashMap<String, String> parms = new HashMap<>();
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    HashMap<String, String> parmsSubs = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProjectUtils.Fullscreen(this);
        setContentView(R.layout.activity_member_ship);
        ProjectUtils.statusbarBackgroundTrans(this, R.drawable.headergradient);
        mContext = MemberShipActivity.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);
        parms.put(Consts.USER_ID, loginDTO.getData().getId());
        parmsSubs.put(Consts.USER_ID, loginDTO.getData().getId());

        init();
    }

    public void init() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        itemPicker = (DiscreteScrollView) findViewById(R.id.item_picker);
        itemPicker.setOrientation(DSVOrientation.HORIZONTAL);



        if (NetworkManager.isConnectToInternet(mContext)) {
            getAllPackege();

        } else {
            //  ProjectUtils.showToast(mContext, getResources().getString(R.string.internet_concation));
            ProjectUtils.InternetAlertDialog(MemberShipActivity.this, getString(R.string.internet_concation), getString(R.string.internet_concation));
        }
    }

    public void getAllPackege() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.GET_ALL_PACKAGES_API, mContext).stringGet(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                ProjectUtils.pauseProgressDialog();
                if (flag) {
                    try {
                        packagesDTOlist = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<PackagesDTO>>() {
                        }.getType();
                        packagesDTOlist = (ArrayList<PackagesDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), getpetDTO);
                        showData();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    //  ProjectUtils.showToast(mContext, msg);

                }
            }
        });
    }

    public void showData() {

        packageslistAdapter = new PackageslistAdapter(this, packagesDTOlist);
        itemPicker.setAdapter(packageslistAdapter);
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }

    public void updateList(int pos) {
        for (int i = 0; i < packagesDTOlist.size(); i++) {
            if (i == pos) {
                packagesDTOlist.get(i).setSelected(true);
                packagesDTO = packagesDTOlist.get(i);
                Log.e("dto", "" + packagesDTO.getSubscription_type() + packagesDTO.getId());
            } else {
                packagesDTOlist.get(i).setSelected(false);
            }
        }
    }

    public void payment() {
        parms.put(Consts.PACKAGE_ID, packagesDTO.getId());
        parms.put(Consts.ORDER_ID, getOrderID());

        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.SUBSCRIPTION_API,parms, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                ProjectUtils.pauseProgressDialog();
                if (flag) {
                    // ProjectUtils.showToast(mContext, msg);
                    getMySubscription();
                } else {
                    // ProjectUtils.showToast(mContext, msg);

                }
            }
        });
    }

    public void getMySubscription() {
        // ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.GET_MY_SUBSCRIPTION_API, parmsSubs, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    try {
                        SubscriptionDto subscriptionDto = new Gson().fromJson(response.getJSONObject("data").toString(), SubscriptionDto.class);
                        prefrence.setSubscription(subscriptionDto, Consts.SUBSCRIPTION_DTO);
                        prefrence.setBooleanValue(Consts.IS_SUBSCRIBE, true);

                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    prefrence.setBooleanValue(Consts.IS_SUBSCRIBE, false);
                }
            }
        });
    }


    public String getOrderID() {
        Random txnID = new Random();

        StringBuilder builder = new StringBuilder();
        for (int count = 0; count <= 5; count++) {
            builder.append(txnID.nextInt(10));
        }
        return builder.toString();
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            int mony = Integer.parseInt(packagesDTO.getPrice()) *100;
            JSONObject options = new JSONObject();
            options.put("name", loginDTO.getData().getName());
            options.put("description", "Add Money to wallet");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", mony+"");

            JSONObject preFill = new JSONObject();
            preFill.put("email", loginDTO.getData().getEmail());
            preFill.put("contact", loginDTO.getData().getMobile());

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            ProjectUtils.showToast(mContext, "Payment Successful");
            parms.put(Consts.TXN_ID, razorpayPaymentID + "");
            payment();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            ProjectUtils.showToast(mContext, "Payment failed");
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}
