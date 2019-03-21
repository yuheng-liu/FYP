package com.example.viaporter.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viaporter.CallbackListener;
import com.example.viaporter.R;
import com.example.viaporter.managers.DataManager;
import com.example.viaporter.models.PatronTripRequest;

import java.util.Collection;
import java.util.List;

import ru.rambler.libs.swipe_layout.SwipeLayout;

import static com.example.viaporter.constants.AppConstants.LUGGAGE;
import static com.example.viaporter.constants.AppConstants.WEIGHT;

public class CurrentBidAdapter extends RecyclerView.Adapter<CurrentBidAdapter.ViewHolder> {
    private List<PatronTripRequest> mCurrentBidDataSet;
    private CallbackListener<PatronTripRequest> mOnPositiveButtonClicked;
    private CallbackListener<PatronTripRequest> mOnNegativeButtonClicked;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView stationNameLeft;
        public TextView stationNameRight;
        public TextView startLocation;
        public TextView endLocation;
        public TextView numLuggage;
        public TextView totalLuggageWeight;
        public View mPositiveButton;
        public View mNegativeButton;
        public SwipeLayout swipeLayout;

        ViewHolder(View view) {
            super(view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CurrentBidAdapter() {
        mCurrentBidDataSet = DataManager.getSharedInstance().getCurrentBidList();
    }

    public void addToDataSet(PatronTripRequest tripRequest) {
        mCurrentBidDataSet.add(tripRequest);
        notifyDataSetChanged();
    }

    public void resetDataSetWith(Collection<PatronTripRequest> tripRequests) {
        mCurrentBidDataSet.clear();
        mCurrentBidDataSet.addAll(tripRequests);
        notifyDataSetChanged();
    }

    public void removeDataSet(PatronTripRequest tripRequest) {
        mCurrentBidDataSet.remove(tripRequest);
        notifyDataSetChanged();
    }

    // Positive and negative button handler
    public void setOnPositiveButtonClicked(CallbackListener<PatronTripRequest> onPositiveButtonClicked) {
        mOnPositiveButtonClicked = onPositiveButtonClicked;
    }

    public void setOnNegativeButtonClicked(CallbackListener<PatronTripRequest> onNegativeButtonClicked) {
        mOnNegativeButtonClicked = onNegativeButtonClicked;
    }

    @NonNull
    @Override
    public CurrentBidAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.current_bid_item, parent, false);

        // set the view's size, margins, padding and layout parameters...
        ViewHolder viewHolder = new ViewHolder(itemView);

        viewHolder.stationNameLeft = itemView.findViewById(R.id.current_bid_station_name_left);
        viewHolder.stationNameRight = itemView.findViewById(R.id.current_bid_station_name_right);
        viewHolder.startLocation = itemView.findViewById(R.id.current_bid_start_location);
        viewHolder.endLocation = itemView.findViewById(R.id.current_bid_end_location);
        viewHolder.numLuggage = itemView.findViewById(R.id.current_bid_number_of_luggage);
        viewHolder.totalLuggageWeight = itemView.findViewById(R.id.current_bid_total_luggage_weight);
        viewHolder.mPositiveButton = itemView.findViewById(R.id.positiveButton);
        viewHolder.mNegativeButton = itemView.findViewById(R.id.negativeButton);
        viewHolder.swipeLayout = itemView.findViewById(R.id.swipe_layout);

        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final PatronTripRequest itemData = mCurrentBidDataSet.get(position);

        holder.stationNameLeft.setText(itemData.getTrainStationName());
        holder.stationNameRight.setText(itemData.getTrainStationName());
        holder.startLocation.setText(itemData.getTripStartLocation());
        holder.endLocation.setText(itemData.getTripEndLocation());
        holder.numLuggage.setText(LUGGAGE + " : " + String.valueOf(itemData.getNumberOfLuggage()));
        holder.totalLuggageWeight.setText(WEIGHT + " : " + String.valueOf(itemData.getTotalLuggageWeight()) + "kg");

        holder.mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnPositiveButtonClicked == null) {
                    return;
                }
                mOnPositiveButtonClicked.accept(itemData);
                holder.swipeLayout.animateReset();
            }
        });

        holder.mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnNegativeButtonClicked == null) {
                    return;
                }
                mOnNegativeButtonClicked.accept(itemData);
                holder.swipeLayout.animateReset();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCurrentBidDataSet.size();
    }
}
