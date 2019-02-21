package com.example.viapatron2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.viapatron2.app.managers.DataManager;
import com.example.viapatron2.core.models.PorterBidRequest;
import com.example.viapatron2.core.models.UserTripRequestSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BidderAdapter extends RecyclerView.Adapter<BidderAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView bidderName;
        public TextView bidAmount;
        View mPositiveButton, mNegativeButton;

        ViewHolder(View view) {
            super(view);
        }
    }

    // Private properties
    private List<PorterBidRequest> mBidderDataset;
    private CallbackListener<PorterBidRequest> mOnPositiveButtonClicked;
    private CallbackListener<PorterBidRequest> mOnNegativeButtonClicked;
    private DataManager mDataManager;
    private UserTripRequestSession userTripRequestSession;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BidderAdapter(DataManager mDataManager) {
        mBidderDataset = new ArrayList<>();
        this.mDataManager = mDataManager;
    }

    // Positive and negative button handler
    public void setOnPositiveButtonClicked(CallbackListener<PorterBidRequest> onPositiveButtonClicked) {
        mOnPositiveButtonClicked = onPositiveButtonClicked;
    }

    public void setOnNegativeButtonClicked(CallbackListener<PorterBidRequest> onNegativeButtonClicked) {
        mOnNegativeButtonClicked = onNegativeButtonClicked;
    }

    public void addToDataSet(PorterBidRequest bidRequest) {
        mBidderDataset.add(bidRequest);
        notifyDataSetChanged();
    }

    public void resetDataSetWith(Collection<PorterBidRequest> tripRequests) {
        mBidderDataset.clear();
        mBidderDataset.addAll(tripRequests);
    }

    @NonNull
    @Override
    public BidderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View itemView = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bidder_item, parent, false);

        // set the view's size, margins, padding and layout parameters...
        ViewHolder vh = new ViewHolder(itemView);

        vh.bidderName = itemView.findViewById(R.id.bidding_porter_name);
        vh.bidAmount = itemView.findViewById(R.id.bid_amount);
        vh.mPositiveButton = itemView.findViewById(R.id.positiveButton);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PorterBidRequest itemData = mBidderDataset.get(position);

        holder.bidderName.setText(itemData.getPorterName());
        holder.bidAmount.setText(String.valueOf(itemData.getBidAmount()));

        holder.mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnPositiveButtonClicked == null) {
                    return;
                }
                mOnPositiveButtonClicked.accept(itemData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBidderDataset.size();
    }
}
