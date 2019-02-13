package com.example.viaporter.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viaporter.R;
import com.example.viaporter.managers.DataManager;
import com.example.viaporter.models.PatronTripRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BroadcastAdapter extends RecyclerView.Adapter<BroadcastAdapter.ViewHolder> {
    private List<PatronTripRequest> mBroadcastDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView stationName;
        public TextView startLocation;
        public TextView endLocation;
        public TextView numLuggage;

        ViewHolder(View view) {
            super(view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BroadcastAdapter() {
        mBroadcastDataSet = DataManager.getSharedInstance().getPatronTripRequestList();
    }

    public void addToDataSet(PatronTripRequest tripRequest) {
        mBroadcastDataSet.add(tripRequest);
        notifyDataSetChanged();
    }

    public void resetDataSetWith(Collection<PatronTripRequest> tripRequests) {
        mBroadcastDataSet.clear();
        mBroadcastDataSet.addAll(tripRequests);
    }

    @NonNull
    @Override
    public BroadcastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.broadcast_request_item, parent, false);

        // set the view's size, margins, padding and layout parameters...
        ViewHolder vh = new ViewHolder(itemView);

        vh.stationName = itemView.findViewById(R.id.broadcast_station_name);
        vh.startLocation = itemView.findViewById(R.id.broadcast_start_location);
        vh.endLocation = itemView.findViewById(R.id.broadcast_end_location);
        vh.numLuggage = itemView.findViewById(R.id.broadcast_number_of_luggage);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PatronTripRequest itemData = mBroadcastDataSet.get(position);

        holder.stationName.setText(itemData.getTrainStationName());
        holder.startLocation.setText(itemData.getTrainStationName());
        holder.endLocation.setText(itemData.getTripEndLocation());
        holder.numLuggage.setText(String.valueOf(itemData.getNumberOfLuggage()));
    }

    @Override
    public int getItemCount() {
        return mBroadcastDataSet.size();
    }
}
