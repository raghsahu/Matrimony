package ics.hindu.matrimony.activity.profile_other;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import ics.hindu.matrimony.models.ImageDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.adapter.AdapterImageSlider;
import ics.hindu.matrimony.interfaces.Consts;

import java.util.ArrayList;

public class ImageSlider extends AppCompatActivity {
    private ViewPager viewpager;
    private ArrayList<ImageDTO> imageDatalist;
    private AdapterImageSlider mAdapter;
    private Context mcontext;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_slider);
        mcontext = ImageSlider.this;
        viewpager = (ViewPager) findViewById(R.id.viewpager);

        if (getIntent().hasExtra(Consts.IMAGE_LIST)) {
            Bundle args = getIntent().getBundleExtra(Consts.IMAGE_LIST);
            imageDatalist = new ArrayList<>();
            imageDatalist = (ArrayList<ImageDTO>) args.getSerializable("ARRAYLIST");
            
        }

        mAdapter = new AdapterImageSlider(ImageSlider.this, mcontext, imageDatalist);
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
