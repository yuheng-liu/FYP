package com.example.viaporter.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viaporter.R;

import java.util.ArrayList;
import java.util.List;

public class BroadcastAdapter extends RecyclerView.Adapter<BroadcastAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView patronName;
        public TextView patronIndex;

        ViewHolder(View view) {
            super(view);
        }
    }

    private List<String> mBroadcastDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public BroadcastAdapter(String tempString) {
        if (mBroadcastDataset == null) {
            mBroadcastDataset = new ArrayList<String>();
        }
        mBroadcastDataset.add(tempString);
    }

    @NonNull
    @Override
    public BroadcastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create a new view
        View itemView = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.broadcast_request_item, parent, false);

        // set the view's size, margins, padding and layout parameters...
        ViewHolder vh = new ViewHolder(itemView);

        vh.patronName = itemView.findViewById(R.id.broadcast_patron_name_item);
        vh.patronIndex = itemView.findViewById(R.id.broadcast_request_index);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.patronIndex.setText(String.valueOf(position + 1));
        holder.patronName.setText(mBroadcastDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return mBroadcastDataset.size();
    }

//    public void addToDataset(String tempString) {
//        mBroadcastDataset.add(tempString);
//    }
}
