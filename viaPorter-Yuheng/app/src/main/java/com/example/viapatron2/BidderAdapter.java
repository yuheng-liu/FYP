package com.example.viapatron2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.viapatron2.app.managers.DataManager;
import com.example.viapatron2.core.models.UserTripRequestSession;

import java.util.ArrayList;
import java.util.List;

public class BidderAdapter extends RecyclerView.Adapter<BidderAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView bidderName;
        public TextView bidderIndex;

        ViewHolder(View view) {
            super(view);
        }
    }

    // Private properties
    private List<BidderInfo> mBidderDataset;
//    private CallbackListener<BidRequest> mOnPositiveButtonClicked;
//    private CallbackListener<BidRequest> mOnNegativeButtonClicked;
    private DataManager mDataManager;
    private UserTripRequestSession userTripRequestSession;
    private BidderInfo bidderInfo;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BidderAdapter(DataManager mDataManager) {
        mBidderDataset = new ArrayList<>();
        this.mDataManager = mDataManager;
    }


    @NonNull
    @Override
    public BidderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View itemView = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bidder_item, parent, false);

        // set the view's size, margins, padding and layout parameters...
        ViewHolder vh = new ViewHolder(itemView);

        vh.bidderIndex = itemView.findViewById(R.id.bidding_index);
        vh.bidderName = itemView.findViewById(R.id.bidding_porter_name);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bidderIndex.setText(String.valueOf(position + 1));
        holder.bidderName.setText(bidderInfo.getBidderName());
    }

    @Override
    public int getItemCount() {
        return mBidderDataset.size();
    }
}
