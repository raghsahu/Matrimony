package ics.hindu.matrimony.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import ics.hindu.matrimony.other.AppIntro;
import ics.hindu.matrimony.models.LanguageDTO;
import ics.hindu.matrimony.models.LoginDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.activity.LanguageSelection;
import ics.hindu.matrimony.activity.dashboard.Dashboard;
import ics.hindu.matrimony.interfaces.Consts;
import ics.hindu.matrimony.network.NetworkManager;
import ics.hindu.matrimony.sharedprefrence.SharedPrefrence;
import ics.hindu.matrimony.utils.ProjectUtils;

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
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    public AdapterLanguage(ArrayList<LanguageDTO> datas, LanguageSelection mContext) {
        this.datas = datas;
        this.mContext = mContext;
        prefrence = SharedPrefrence.getInstance(mContext);
        sharedpreferences = mContext.getSharedPreferences(Consts.LANGUAGE_PREF, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
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
                    ProjectUtils.InternetAlertDialog(mContext, mContext.getResources().getString(R.string.error_connecting), mContext.getResources().getString(R.string.internet_concation));

                }


                if (language.equals("English")) {
                    language("en");
                    editor.putString(Consts.SELECTED_LANGUAGE, "en");

                } else if (language.equals("हिन्दी")) {
                    language("hi");
                    editor.putString(Consts.SELECTED_LANGUAGE, "hi");
                }
                editor.commit();

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
        editor.putBoolean(Consts.LANGUAGE_SELECTION, true);
        mContext.getResources().updateConfiguration(config,
                mContext.getResources().getDisplayMetrics());

    }


}
