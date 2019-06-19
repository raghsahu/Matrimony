package com.samyotech.matrimony.activity.imageselection;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.samyotech.matrimony.Models.LoginDTO;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.adapter.ImagesAdapter;
import com.samyotech.matrimony.https.HttpsRequest;
import com.samyotech.matrimony.interfaces.Consts;
import com.samyotech.matrimony.interfaces.Helper;
import com.samyotech.matrimony.multipleimagepicker.MultiImageSelector;
import com.samyotech.matrimony.sharedprefrence.SharedPrefrence;
import com.samyotech.matrimony.utils.ProjectUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerViewImages;
    private GridLayoutManager gridLayoutManager;
    TextView TVimage;
    ImageView ivback;

    public ArrayList<String> mSelectedImagesList = new ArrayList<>();

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 401;
    private final int REQUEST_IMAGE = 301;

    private MultiImageSelector mMultiImageSelector;
    private ImagesAdapter mImagesAdapter;

    HashMap<String, File> Imagelist;
    HashMap<String, String> parms = new HashMap<>();
    ;
    HashMap<String, String> parmsHeader = new HashMap<>();
    ;
    int count = 0;
    int i;
    File file;
    private final int MAX_IMAGE_SELECTION_LIMIT = 5;
    int front = 0;
    int countMy = 0;
    int countImg = 5;
    private LoginDTO loginDTO;
    private SharedPrefrence prefrence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        prefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);

//        parms.put(Consts.USER_ID, loginDTO.getData().getId());
//        parms.put(Consts.TOKEN, loginDTO.getAccess_token());
        //************************************************************static******************
                parms.put(Consts.USER_ID, "12345");
        parms.put(Consts.TOKEN, "111111111111111111");
        //************************************************************static******************

        parmsHeader.put("Accept", "application/json");

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        recyclerViewImages = (RecyclerView) findViewById(R.id.recycler_view_images);
        TVimage = (TextView) findViewById(R.id.TVimage);


        gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerViewImages.setHasFixedSize(true);
        recyclerViewImages.setLayoutManager(gridLayoutManager);

        if (getIntent().hasExtra("front")) {
            front = getIntent().getIntExtra("front", 0);
        }
        if (getIntent().hasExtra("Count")) {
            countMy = getIntent().getIntExtra("Count", 0);
            countImg = countImg - countMy;
        }


        mMultiImageSelector = MultiImageSelector.create();

        if (checkAndRequestPermissions()) {
            mMultiImageSelector.showCamera(true);
            mMultiImageSelector.count(countImg);
            mMultiImageSelector.multi();
            mMultiImageSelector.origin(mSelectedImagesList);
            mMultiImageSelector.start(MainActivity.this, REQUEST_IMAGE, front);

        }


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkAndRequestPermissions()) {
                    mMultiImageSelector.showCamera(true);
                    mMultiImageSelector.count(countImg);
                    mMultiImageSelector.multi();
                    mMultiImageSelector.origin(mSelectedImagesList);
                    mMultiImageSelector.start(MainActivity.this, REQUEST_IMAGE, front);

                }
            }
        });


        TVimage.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.TVimage:
                Imagelist = new HashMap<>();
                count = mSelectedImagesList.size();
                for (int i = 0; i < mSelectedImagesList.size(); i++) {
                    file = new File(mSelectedImagesList.get(i));
                    Imagelist.put("images" + i, file);
                }
                try {
                    if (mSelectedImagesList.size() != 0) {
                        uploadimage();
                        Log.e("hashmap", Imagelist.toString());
                        Log.e("count", String.valueOf(count));
                    } else {
                    }
                } catch (Exception e) {
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            try {

                mSelectedImagesList = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);


                mImagesAdapter = new ImagesAdapter(this, mSelectedImagesList);
                recyclerViewImages.setAdapter(mImagesAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

/*
    @Override
    protected void onResume() {
        super.onResume();
        Imagelist = new HashMap<>();
        if (mSelectedImagesList.size() > 0) {
            count = mSelectedImagesList.size();
            for (int i = 0; i < mSelectedImagesList.size(); i++) {
                file = new File(mSelectedImagesList.get(i));
                Imagelist.put("userFiles" + i, file);

            }
        }

        uploadimage();
    }
*/

    private boolean checkAndRequestPermissions() {
        int externalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (externalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            floatingActionButton.performClick();
        }
    }


    public void uploadimage() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.IMAGES, parms, Imagelist, mContext).imagePost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    finish();
                } else {
                    ProjectUtils.showToast(mContext, msg);
                }

            }
        });
    }

/*
    public void uploadimage() {
        ProjectUtils.showProgressDialog(MainActivity.this, false, "Please wait...");
        AndroidNetworking.upload(Consts.BASE_URL + Consts.IMAGES)
                .addMultipartParameter(parms)
                .addHeaders(parmsHeader)
                .addMultipartFile(Imagelist)
                .setTag("uploadTest")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {


                    }
                })
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        ProjectUtils.pauseProgressDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String message = jsonObject.getString("message");
                            boolean status = jsonObject.getBoolean("success");


                            if (status) {


                                //String image = jsonObject1.getString("image");
                                finish();

                            } else {
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                            Log.e("Exception", e.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtils.pauseProgressDialog();
                        Log.e("Error", anError.getErrorBody() + " " + anError.getMessage() + " " + anError.getErrorCode());
                    }
                });
    }
*/


}
