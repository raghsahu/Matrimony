package com.inspire.rkspmatrimony.adapter;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.inspire.rkspmatrimony.AppIntro;
import com.inspire.rkspmatrimony.Models.LanguageDTO;
import com.inspire.rkspmatrimony.Models.LoginDTO;
import com.inspire.rkspmatrimony.R;
import com.inspire.rkspmatrimony.activity.LanguageSelection;
import com.inspire.rkspmatrimony.activity.dashboard.Dashboard;
import com.inspire.rkspmatrimony.interfaces.Consts;
import com.inspire.rkspmatrimony.network.NetworkManager;
import com.inspire.rkspmatrimony.sharedprefrence.SharedPrefrence;
import com.inspire.rkspmatrimony.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by amit on 7/9/17.
 */

public class AdapterLanguage extends RecyclerView.Adapter<AdapterLanguage.LanguageHolder> {
    private ArrayList<LanguageDTO> datas = new ArrayList<>();
    private LanguageSelection mContext;
    private SharedPrefrence prefrence;
    private String half, second_half;
    LoginDTO loginDTO;

    public AdapterLanguage(ArrayList<LanguageDTO> datas, LanguageSelection mContext) {
        this.datas = datas;
        this.mContext = mContext;
        prefrence = SharedPrefrence.getInstance(mContext);


    }

    @Override
    public LanguageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_language_holder, parent, false);
        LanguageHolder categoryHolder = new LanguageHolder(view);

        return categoryHolder;
    }

    @Override
    public void onBindViewHolder(LanguageHolder holder, final int position) {

        second_half = datas.get(position).getLanguage_name().substring(datas.get(position).getLanguage_name().length() / 2);
        half = datas.get(position).getLanguage_name().substring(0, datas.get(position).getLanguage_name().length() / 2);
        String next = "<font color='#02688C'>" + half + "</font>";
        String last = "<font color='#02688C'>" + second_half + "</font>";
        String complete_word = next + last;
        holder.tvLanguage.setText(Html.fromHtml(complete_word));
        final String language = datas.get(position).getLanguage_name();


        holder.lllanguagelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (NetworkManager.isConnectToInternet(mContext)) {
                    if (prefrence.getBooleanValue(Consts.IS_REGISTERED)) {
                        mContext.startActivity(new Intent(mContext, Dashboard.class));
                        mContext.overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                    } else {
                        mContext.startActivity(new Intent(mContext, AppIntro.class));
                        mContext.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                    mContext.finish();


                } else {
                    ProjectUtils.InternetAlertDialog( mContext,  mContext.getResources().getString(R.string.error_connecting), mContext.getResources().getString(R.string.internet_concation));

                }

                if (language.equals("English")) {
                    language("en");
                    prefrence.setValue(Consts.SELECTED_LANGUAGE, "en");

                } else if (language.equals("हिन्दी")) {
                    language("hi");
                    prefrence.setValue(Consts.SELECTED_LANGUAGE, "hi");

                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class LanguageHolder extends RecyclerView.ViewHolder {
        public LinearLayout lllanguagelayout;
        public TextView tvLanguage;

        public LanguageHolder(View itemView) {
            super(itemView);
            tvLanguage = (TextView) itemView.findViewById(R.id.tvLanguage);
            lllanguagelayout = (LinearLayout) itemView.findViewById(R.id.lllanguagelayout);
        }
    }

    public void language(String language) {
        String languageToLoad = language; // your language

        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        prefrence.setBooleanValue(Consts.LANGUAGE_SELECTION, true);
        mContext.getResources().updateConfiguration(config,
                mContext.getResources().getDisplayMetrics());

    }



}
