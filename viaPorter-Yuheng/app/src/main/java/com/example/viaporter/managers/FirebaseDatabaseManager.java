package com.example.viaporter.managers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.viaporter.MainActivity;
import com.example.viaporter.models.PatronTripRequest;
import com.example.viaporter.models.PorterBidRequest;
import com.example.viaporter.models.PorterUserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDatabaseManager {
    private static final String TAG = "FirebaseDatabaseManager";

    private MainActivity mActivity;

    public String myUID;
    public DatabaseReference mDatabase;
    public DatabaseReference porterDatabase;
    public DatabaseReference patronDatabase;
    public DatabaseReference chatDatabase;
    public DatabaseReference broadcastTripsDatabase;
    public DatabaseReference currentBidsDatabase;
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
        myUID = FirebaseAuth.getInstance().getUid();
        setupDatabase();
    }

    private void setupDatabase() {
        broadcastTripsDatabase = mDatabase.child("Broadcast Trip Requests");
        chatDatabase = mDatabase.child("Chats");
        patronDatabase = mDatabase.child("Patrons");
        porterDatabase = mDatabase.child("Porters");
        currentBidsDatabase = porterDatabase.child(myUID).child("currentBids");

        // adding porter user to database
        porterDatabase.child(FirebaseAuth.getInstance().getUid())
                .setValue(new PorterUserDetails(FirebaseAuth.getInstance().getUid(), "Yuheng", 4.5));
    }

    void addToMyCurrentBids(Double bidAmount) {
        final PatronTripRequest newBid = mActivity.dataManager.getSelectedBidRequest();

        currentBidsDatabase
                .child(newBid.getPatronUid())
                .setValue(newBid);

        DatabaseReference patronBidRequestsList = patronDatabase
                .child(newBid.getPatronUid())
                .child("porterBidRequest")
                .child(myUID);

        patronBidRequestsList.setValue(new PorterBidRequest(myUID, "Yuheng", bidAmount));
        patronBidRequestsList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "value event onDataChanged");
                Log.d(TAG, String.valueOf(dataSnapshot.exists()));
                if (!dataSnapshot.exists()) {
                    currentBidsDatabase
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

    void cancelMyCurrentBid() {
        PatronTripRequest curBid = mActivity.dataManager.getSelectedBidRequest();

        currentBidsDatabase
                .child(curBid.getPatronUid())
                .removeValue();

        patronDatabase
                .child(curBid.getPatronUid())
                .child("porterBidRequest")
                .child(myUID)
                .removeValue();
    }
}
