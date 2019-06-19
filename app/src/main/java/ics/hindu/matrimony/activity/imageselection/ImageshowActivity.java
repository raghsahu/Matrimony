package ics.hindu.matrimony.activity.imageselection;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ics.hindu.matrimony.models.ImageDTO;
import ics.hindu.matrimony.models.LoginDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.activity.myprofile.DeleteImage;
import ics.hindu.matrimony.adapter.GridImageshowAdapter;
import ics.hindu.matrimony.adapter.ImagesAdapter;
import ics.hindu.matrimony.https.HttpsRequest;
import ics.hindu.matrimony.interfaces.Consts;
import ics.hindu.matrimony.interfaces.Helper;
import ics.hindu.matrimony.multipleimagepicker.MultiImageSelector;
import ics.hindu.matrimony.sharedprefrence.SharedPrefrence;
import ics.hindu.matrimony.utils.ProjectUtils;
import ics.hindu.matrimony.view.ProfilePageProfilePhotoImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ImageshowActivity extends AppCompatActivity {
    private HashMap<String, String> parms = new HashMap<>();
    private ProfilePageProfilePhotoImageView IVfirst;
    ImageView IVsetimage;
    RecyclerView RVlist;
    Context mContext;
    ArrayList<ImageDTO> imageDatalist;
    public static ArrayList<String> mSelectedImagesList = new ArrayList<>();

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 401;
    private final int REQUEST_IMAGE = 301;

    private MultiImageSelector mMultiImageSelector;
    private ImagesAdapter mImagesAdapter;

    HashMap<String, File> Imagelist;
    int count = 0;
    int i;
    File file;
    private final int MAX_IMAGE_SELECTION_LIMIT = 5;
    private String TAG = ImageshowActivity.class.getSimpleName();
    private SharedPrefrence prefrence;
    private LoginDTO loginDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageshow);
        mMultiImageSelector = MultiImageSelector.create();
        mContext = ImageshowActivity.this;

        init();
    }

    public void init() {
        RVlist = findViewById(R.id.RVlist);
        IVfirst = findViewById(R.id.IVfirst);
        IVsetimage = findViewById(R.id.IVsetimage);

        final GestureDetector mGestureDetector =
                new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }
                });

        RVlist.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {


                View child = rv.findChildViewUnder(e.getX(), e.getY());

                if (child != null && mGestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildLayoutPosition(child);

                    if (position == 0) {

                        if (count == 5) {
                            ProjectUtils.showToast(mContext, "You can add only 5 images");
                        } else {
                            if (checkAndRequestPermissions()) {
                                Intent intent = new Intent(mContext, MainActivity.class);
                                intent.putExtra("Count", count);
                                startActivity(intent);
                            }

                        }

                    }


                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        IVsetimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("Count", count);
                startActivity(intent);
            }
        });

        IVfirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageDatalist.size() > 0) {
                    Intent intent = new Intent(mContext, DeleteImage.class);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) imageDatalist);
                    intent.putExtra(Consts.IMAGE_LIST, args);
                    startActivity(intent);
                } else {
                    ProjectUtils.showToast(mContext, getResources().getString(R.string.no_image));
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        prefrence = SharedPrefrence.getInstance(mContext);
        loginDTO = prefrence.getLoginResponse(Consts.LOGIN_DTO);

        parms.put(Consts.TOKEN, loginDTO.getAccess_token());
        parms.put(Consts.USER_ID, loginDTO.getData().getId());

        getImages();
    }

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

        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }


    public void getImages() {

        new HttpsRequest(Consts.GET_GALLARY_API, parms, mContext).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    try {
                        imageDatalist = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<ImageDTO>>() {
                        }.getType();
                        imageDatalist = (ArrayList<ImageDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), getpetDTO);


                        if (imageDatalist.size() > 0) {

                            count = imageDatalist.size();
                            GridLayoutManager gLayout = new GridLayoutManager(mContext, 3);
                            GridImageshowAdapter adapter = new GridImageshowAdapter(mContext, imageDatalist);
                            RVlist.setLayoutManager(gLayout);
                            RVlist.setLayoutManager(gLayout);
                            RVlist.setAdapter(adapter);
                            IVsetimage.setVisibility(View.GONE);
                            try {

                                Glide.with(mContext).
                                        load(Consts.IMAGE_URL + imageDatalist.get(0).getBig_url())
                                        .placeholder(R.drawable.default_error)
                                        .dontAnimate()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(IVfirst);

                            } catch (Exception e) {

                            }
                        } else {
                            IVsetimage.setVisibility(View.VISIBLE);
                            imageDatalist = new ArrayList<>();
                            count = imageDatalist.size();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        imageDatalist = new ArrayList<>();
                    }

                } else {
                    imageDatalist = new ArrayList<>();
                }
            }
        });

    }

}
