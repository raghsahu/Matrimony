package ics.hindu.matrimony.adapter;

/**
 * Created by varun on 29/08/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ics.hindu.matrimony.models.SubscriptionHistoryDto;
import ics.hindu.matrimony.R;
import ics.hindu.matrimony.utils.ProjectUtils;
import ics.hindu.matrimony.view.CustomTextView;
import ics.hindu.matrimony.view.CustomTextViewBold;

import java.util.ArrayList;
import java.util.Locale;


public class SubscriptionHisAdapter extends RecyclerView.Adapter<SubscriptionHisAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<SubscriptionHistoryDto> objects = null;
    private ArrayList<SubscriptionHistoryDto> subscriptionHistoryList;


    public SubscriptionHisAdapter(Context mContext, ArrayList<SubscriptionHistoryDto> objects) {
        this.mContext = mContext;
        this.objects = objects;
        this.subscriptionHistoryList = new ArrayList<SubscriptionHistoryDto>();
        this.subscriptionHistoryList.addAll(objects);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_subscription_his, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tvTxId.setText(objects.get(position).getTxn_id());
        holder.tvPrice.setText(objects.get(position).getCurrency_type() + " " + objects.get(position).getPrice());
        holder.tvSubType.setText(objects.get(position).getSubscription_name());

        holder.tvStartDate.setText(ProjectUtils.convertTimestampDateToTime(ProjectUtils.correctTimestamp(Long.parseLong(objects.get(position).getSubscription_start_date())))+" "+mContext.getResources().getString(R.string.to)+" "+ ProjectUtils.convertTimestampDateToTime(ProjectUtils.correctTimestamp(Long.parseLong(objects.get(position).getSubscription_end_date()))));


    }

    @Override
    public int getItemCount() {

        return objects.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        objects.clear();
        if (charText.length() == 0) {
            objects.addAll(subscriptionHistoryList);
        } else {
            for (SubscriptionHistoryDto productDTO : subscriptionHistoryList) {
                if (productDTO.getTxn_id().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    objects.add(productDTO);
                }
            }
        }
        notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CustomTextView tvSubType, tvStartDate;
        public CustomTextViewBold tvTxId, tvPrice;

        public MyViewHolder(View view) {
            super(view);
            tvTxId = view.findViewById(R.id.tvTxId);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvSubType = view.findViewById(R.id.tvSubType);
            tvStartDate = view.findViewById(R.id.tvStartDate);


        }
    }


}