package com.samyotech.matrimony.activity.myprofile;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.samyotech.matrimony.Models.ImageDTO;
import com.samyotech.matrimony.R;
import com.samyotech.matrimony.activity.profile_other.ImageSlider;
import com.samyotech.matrimony.adapter.AdapterDeleteImage;
import com.samyotech.matrimony.adapter.AdapterImageSlider;
import com.samyotech.matrimony.interfaces.Consts;

import java.util.ArrayList;

public class DeleteImage extends AppCompatActivity {
    private ViewPager viewpager;
    private ArrayList<ImageDTO> imageDatalist;
    private AdapterDeleteImage mAdapter;
    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_delete_image);
        mcontext = DeleteImage.this;
        viewpager = (ViewPager) findViewById(R.id.viewpager);

        if (getIntent().hasExtra(Consts.IMAGE_LIST)) {
            Bundle args = getIntent().getBundleExtra(Consts.IMAGE_LIST);
            imageDatalist = new ArrayList<>();
            imageDatalist = (ArrayList<ImageDTO>) args.getSerializable("ARRAYLIST");

        }
        mAdapter = new AdapterDeleteImage(DeleteImage.this, mcontext, imageDatalist);
        viewpager.setAdapter(mAdapter);
        //viewpager.setPageTransformer(true, new ZoomOutSlideTransformer());

        //viewpager.setOnPageChangeListener(this);


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}