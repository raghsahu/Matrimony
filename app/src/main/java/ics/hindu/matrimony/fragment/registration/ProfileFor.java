package ics.hindu.matrimony.fragment.registration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ics.hindu.matrimony.models.ProfileForDTO;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.adapter.AdapterProfileFor;
import ics.hindu.matrimony.fragment.Divider_Profile_for;

import java.util.ArrayList;

public class ProfileFor extends Fragment implements AdapterProfileFor.UpdateDataClickListener {
    private View view;
    private RecyclerView rvProfileFor;
    private ArrayList<ProfileForDTO> profileforList;
    private GridLayoutManager gridLayoutManager;
    private AdapterProfileFor adapterProfileFor;
    public String profile = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_for, container, false);
        setUIAction(view);
        return view;
    }

    public void setUIAction(View view) {
        rvProfileFor = view.findViewById(R.id.rvProfileFor);
        profileforList= new ArrayList<>();

        profileforList.add(new ProfileForDTO("1",getResources().getString(R.string.self)));
        profileforList.add(new ProfileForDTO("2",getResources().getString(R.string.reletive)));
        profileforList.add(new ProfileForDTO("3",getResources().getString(R.string.son)));
        profileforList.add(new ProfileForDTO("4",getResources().getString(R.string.daughter)));
        profileforList.add(new ProfileForDTO("5",getResources().getString(R.string.brother)));
        profileforList.add(new ProfileForDTO("6",getResources().getString(R.string.sister)));



       // gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);

        gridLayoutManager = new GridLayoutManager(getContext().getApplicationContext(), 2, 1, false);
        rvProfileFor.addItemDecoration(new Divider_Profile_for(getContext(), 1));
        rvProfileFor.addItemDecoration(new Divider_Profile_for(getContext(), 0));
        rvProfileFor.setLayoutManager(gridLayoutManager);
        rvProfileFor.setItemAnimator(new DefaultItemAnimator());

        adapterProfileFor = new AdapterProfileFor(profileforList, this);
        rvProfileFor.setAdapter(adapterProfileFor);
        adapterProfileFor.setOnItemClickListener(this);
    }



    @Override
    public void onItemClick(int position) {
        adapterProfileFor.selected(position);
        profile = profileforList.get(position).getType();

    }

}
