package com.example.viaporter.managers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.viaporter.MainActivity;
import com.example.viaporter.models.ChatMessage;
import com.example.viaporter.models.LocationUpdate;
import com.example.viaporter.models.PatronTripRequest;
import com.example.viaporter.models.PorterBidRequest;
import com.example.viaporter.models.PorterUserDetails;
import com.example.viaporter.models.TripState;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDatabaseManager {
    private static final String TAG = "FirebaseDatabaseManager";

    private MainActivity mActivity;

    public String myUid;
    public DatabaseReference mDatabase;
    public DatabaseReference porterDatabase;
    public DatabaseReference patronDatabase;
    public DatabaseReference chatDatabase;
    public DatabaseReference broadcastTripsDatabase;

    private ValueEventListener patronTripRequestStatusListener;
    private ValueEventListener patronTripConfirmedStatusListener;
    /*                                      *
     * Use of Bill Pugh Singleton structure *
     *                                      */
    // Private constructor //
    private FirebaseDatabaseManager() {
    }
    // Static inner class are not loaded until they are referenced
    private static class FirebaseDatabaseManagerholder {
        private static FirebaseDatabaseManager manager = new FirebaseDatabaseManager();
    }
    // Global excess point
    public static FirebaseDatabaseManager getSharedInstance() {
        return FirebaseDatabaseManagerholder.manager;
    }
    /* ************************************ */

    public void setMainActivity(MainActivity mainActivity) {
        mActivity = mainActivity;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        myUid = FirebaseAuth.getInstance().getUid();
        setupDatabase();
    }

    private void setupDatabase() {
        broadcastTripsDatabase = mDatabase.child("Broadcast Trip Requests");
        chatDatabase = mDatabase.child("Chats");
        patronDatabase = mDatabase.child("Patrons");
        porterDatabase = mDatabase.child("Porters");

        // adding porter user to database
        porterDatabase.child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists() && FirebaseAuth.getInstance().getCurrentUser() != null){
                    porterDatabase.child(myUid)
                            .setValue(new PorterUserDetails(myUid, "Yuheng", 4.5, mActivity.getDataManager().getTripState()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        broadcastTripsDatabase.child("test")
//                .setValue(new PatronTripRequest("date", "wYlcyX4OJrWGvoMLCcjhDZzeqIv1", "start", "end", 5, 10, 10.0));
    }

    public void sendChatMessage(String msg) {
        chatDatabase
                .push()
                .setValue(new ChatMessage(msg,
                        FirebaseAuth.getInstance()
                                .getCurrentUser()
                                .getDisplayName())
                );
    }

    public void addToMyCurrentBids(Double bidAmount) {
        final PatronTripRequest newBid = mActivity.getDataManager().getSelectedBidRequest();
        newBid.setBidAmount(bidAmount);

        porterDatabase
                .child(myUid)
                .child("currentBids")
                .child(newBid.getPatronUid())
                .setValue(newBid);

        DatabaseReference patronBidRequestsList = patronDatabase
                .child(newBid.getPatronUid())
                .child("porterBidRequest")
                .child(myUid);

        patronBidRequestsList.setValue(new PorterBidRequest(myUid, "Yuheng", bidAmount));
        patronBidRequestsList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "value event onDataChanged");
                Log.d(TAG, String.valueOf(dataSnapshot.exists()));
                if (!dataSnapshot.exists()) {
                    porterDatabase
                            .child(myUid)
                            .child("currentBids")
                            .child(newBid.getPatronUid())
                            .removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "error on listening for single value event");
            }
        });
    }

    public void cancelMyCurrentBid() {
        PatronTripRequest curBid = mActivity.getDataManager().getSelectedBidRequest();

        porterDatabase
                .child(myUid)
                .child("currentBids")
                .child(curBid.getPatronUid())
                .removeValue();

        patronDatabase
                .child(curBid.getPatronUid())
                .child("porterBidRequest")
                .child(myUid)
                .removeValue();
    }

    public void updateMyCurrentLocation(Double latitude, Double longitude) {
        porterDatabase
                .child(myUid)
                .child("currentLocation")
                .setValue(new LocationUpdate(latitude, longitude));
    }

    public void setTripStatus(TripState status) {
        porterDatabase
                .child(myUid)
                .child("tripState")
                .setValue(status);
    }

    public void setListeners(ValueEventListener listener, String type){
        switch (type){
            case "TripRequestPatronTripStatus":
                patronTripRequestStatusListener = listener;
                break;
            case "TripConfirmedPatronTripStatus":
                patronTripConfirmedStatusListener = listener;
                break;
            default:
                Log.d(TAG,"No listener matched");
        }
    }

    public void startPatronTripRequestStatusListener(){
        patronDatabase
                .child(mActivity.getDataManager().getSelectedBidRequest().getPatronUid())
                .child("tripState")
                .addValueEventListener(patronTripRequestStatusListener);
    }

    public void startPatronTripConfirmedStatusListener() {
        patronDatabase
                .child(mActivity.getDataManager().getSelectedBidRequest().getPatronUid())
                .child("tripState")
                .addValueEventListener(patronTripConfirmedStatusListener);
    }
}
