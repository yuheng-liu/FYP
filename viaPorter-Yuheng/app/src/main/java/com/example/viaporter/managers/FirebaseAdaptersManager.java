package com.example.viaporter.managers;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viaporter.CallbackListener;
import com.example.viaporter.MainActivity;
import com.example.viaporter.R;
import com.example.viaporter.models.ChatMessage;
import com.example.viaporter.models.PatronTripRequest;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import ru.rambler.libs.swipe_layout.SwipeLayout;

public class FirebaseAdaptersManager {
    private static final String TAG = "FirebaseAdaptersManager";

    private MainActivity mActivity;

    private FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder> mChatBoxAdapter;
    private FirebaseRecyclerAdapter<PatronTripRequest, BroadcastRequestHolder> mBroadcastRequestAdapter;
    private FirebaseRecyclerAdapter<PatronTripRequest, CurrentBidHolder> mCurrentBidAdapter;

    /*                                      *
     * Use of Bill Pugh Singleton structure *
     *                                      */
    // Private constructor //
    private FirebaseAdaptersManager() {
    }
    // Static inner class are not loaded until they are referenced
    private static class FirebaseAdaptersManagerholder {
        private static FirebaseAdaptersManager manager = new FirebaseAdaptersManager();
    }
    // Global excess point
    public static FirebaseAdaptersManager getSharedInstance() {
        return FirebaseAdaptersManagerholder.manager;
    }
    /* ************************************ */

    public void setMainActivity(MainActivity mainActivity) {
        mActivity = mainActivity;
    }

    // START OF ADAPTER SETUP FOR CHAT BOX
    public static class ChatViewHolder extends RecyclerView.ViewHolder{
        ChatViewHolder(View itemView){ super(itemView); }

        TextView nameField;
        TextView messageField;
    }

    public FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder> getChatBoxAdapter(final RecyclerView mChatBoxView) {
        final int TYPE_MY_MSG = 1;
        final int TYPE_THEIR_MSG = 2;

        DatabaseReference curDatabase = mActivity.getFirebaseDatabaseManager().chatDatabase;
        curDatabase.keepSynced(true);

        Query query = curDatabase.limitToLast(50);

        final FirebaseRecyclerOptions<ChatMessage> options =
                new FirebaseRecyclerOptions.Builder<ChatMessage>()
                        .setQuery(query, ChatMessage.class)
                        .build();

        mChatBoxAdapter = new FirebaseRecyclerAdapter<ChatMessage, ChatViewHolder>(options) {
            @NonNull
            @Override
            public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                int layout = viewType == TYPE_MY_MSG ? R.layout.my_chat_bubble : R.layout.their_chat_bubble;

                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
                ChatViewHolder viewHolder = new ChatViewHolder(itemView);

                viewHolder.nameField = itemView.findViewById(R.id.name);
                viewHolder.messageField = itemView.findViewById(R.id.message_body);

                return viewHolder;
            }

            @Override
            protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull ChatMessage model) {
                holder.nameField.setText(model.getMessageUser());
                holder.messageField.setText(model.getMessageText());
            }

            @Override
            public int getItemViewType(int position) {
                return getItem(position).getMessageUser().equals("Yuheng") ? TYPE_MY_MSG : TYPE_THEIR_MSG;
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();

                mChatBoxView.scrollToPosition(mChatBoxAdapter.getItemCount() - 1);
            }
        };
        return mChatBoxAdapter;
    }
    // END OF ADAPTER SETUP FOR CHAT BOX

    // START OF ADAPTER SETUP FOR BROADCAST REQUEST
    public static class BroadcastRequestHolder extends RecyclerView.ViewHolder {
        BroadcastRequestHolder(View itemView) { super(itemView); }

        TextView startLocation;
        TextView endLocation;
        TextView luggageInfo;
        View mPositiveButton;
        View mNegativeButton;
        SwipeLayout swipeLayout;
    }

    public FirebaseRecyclerAdapter<PatronTripRequest, BroadcastRequestHolder> getBroadcastRequestAdapter(
            final RecyclerView mBroadcastView,
            final CallbackListener<PatronTripRequest> onPositiveButtonClicked,
            final CallbackListener<PatronTripRequest> onNegativeButtonClicked){

        DatabaseReference curDatabase = mActivity.getFirebaseDatabaseManager().broadcastTripsDatabase;
        curDatabase.keepSynced(true);

        Query query = curDatabase.limitToLast(50);

        final FirebaseRecyclerOptions<PatronTripRequest> options =
                new FirebaseRecyclerOptions.Builder<PatronTripRequest>()
                        .setQuery(query, PatronTripRequest.class)
                        .build();

        mBroadcastRequestAdapter = new FirebaseRecyclerAdapter<PatronTripRequest, BroadcastRequestHolder>(options) {
            @NonNull
            @Override
            public BroadcastRequestHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.broadcast_request_item, viewGroup, false);
                BroadcastRequestHolder viewHolder = new BroadcastRequestHolder(itemView);

                viewHolder.startLocation = itemView.findViewById(R.id.broadcast_start_location);
                viewHolder.endLocation = itemView.findViewById(R.id.broadcast_end_location);
                viewHolder.luggageInfo = itemView.findViewById(R.id.broadcast_luggage_info);
                viewHolder.mPositiveButton = itemView.findViewById(R.id.positiveButton);
                viewHolder.mNegativeButton = itemView.findViewById(R.id.negativeButton);
                viewHolder.swipeLayout = itemView.findViewById(R.id.swipe_layout);

                return viewHolder;
            }

            @Override
            protected void onBindViewHolder(@NonNull final BroadcastRequestHolder holder, int position, @NonNull final PatronTripRequest model) {
                holder.startLocation.setText(model.getTripStartLocation());
                holder.endLocation.setText(model.getTripEndLocation());
                holder.luggageInfo.setText(model.getNumberOfLuggage() + " (" + model.getTotalLuggageWeight() + "KG)");

                holder.mPositiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onPositiveButtonClicked.accept(model);
                        holder.swipeLayout.animateReset();
                    }
                });

                holder.mNegativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onNegativeButtonClicked.accept(model);
                        holder.swipeLayout.animateReset();
                    }
                });
                // for disabling right swipe at times
//                holder.swipeLayout.setRightSwipeEnabled(false);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();

                mBroadcastView.scrollToPosition(mBroadcastRequestAdapter.getItemCount() - 1);
            }
        };
        return mBroadcastRequestAdapter;
    }
    // END OF ADAPTER SETUP FOR BROADCAST REQUEST

    // START OF ADAPTER SETUP FOR CURRENT BID
    public static class CurrentBidHolder extends RecyclerView.ViewHolder {
        CurrentBidHolder(View itemView) { super(itemView); }

        TextView startLocation;
        TextView endLocation;
        TextView luggageInfo;
        TextView bidAmount;
        View mPositiveButton;
        View mNegativeButton;
        SwipeLayout swipeLayout;
    }

    public FirebaseRecyclerAdapter<PatronTripRequest, CurrentBidHolder> getCurrentBidAdapter(
            final RecyclerView mCurrentBidView,
            final CallbackListener<PatronTripRequest> onPositiveButtonClicked,
            final CallbackListener<PatronTripRequest> onNegativeButtonClicked){

        DatabaseReference curDatabase = mActivity.getFirebaseDatabaseManager()
                .porterDatabase
                .child(mActivity.getFirebaseDatabaseManager().myUid)
                .child("currentBids");
        curDatabase.keepSynced(true);

        Query query = curDatabase.limitToLast(50);

        final FirebaseRecyclerOptions<PatronTripRequest> options =
                new FirebaseRecyclerOptions.Builder<PatronTripRequest>()
                        .setQuery(query, PatronTripRequest.class)
                        .build();

        mCurrentBidAdapter = new FirebaseRecyclerAdapter<PatronTripRequest, CurrentBidHolder>(options) {
            @NonNull
            @Override
            public CurrentBidHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.current_bid_item, viewGroup, false);
                CurrentBidHolder viewHolder = new CurrentBidHolder(itemView);

                viewHolder.startLocation = itemView.findViewById(R.id.current_bids_start_location);
                viewHolder.endLocation = itemView.findViewById(R.id.current_bids_end_location);
                viewHolder.luggageInfo = itemView.findViewById(R.id.current_bids_luggage_info);
                viewHolder.bidAmount = itemView.findViewById(R.id.current_bids_bid_amount);
                viewHolder.mPositiveButton = itemView.findViewById(R.id.positiveButton);
                viewHolder.mNegativeButton = itemView.findViewById(R.id.negativeButton);
                viewHolder.swipeLayout = itemView.findViewById(R.id.swipe_layout);

                return viewHolder;
            }

            @Override
            protected void onBindViewHolder(@NonNull final CurrentBidHolder holder, int position, @NonNull final PatronTripRequest model) {
                holder.startLocation.setText(model.getTripStartLocation());
                holder.endLocation.setText(model.getTripEndLocation());
                holder.luggageInfo.setText(model.getNumberOfLuggage() + " (" + model.getTotalLuggageWeight() + "KG)");
                holder.bidAmount.setText("$" + model.getBidAmount().toString());

                holder.mPositiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onPositiveButtonClicked.accept(model);
                        holder.swipeLayout.animateReset();
                    }
                });

                holder.mNegativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onNegativeButtonClicked.accept(model);
                        holder.swipeLayout.animateReset();
                    }
                });
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();

                mCurrentBidView.scrollToPosition(mCurrentBidAdapter.getItemCount() - 1);
            }
        };
        return mCurrentBidAdapter;
    }
    // END OF ADAPTER SETUP FOR CURRENT BID
}
