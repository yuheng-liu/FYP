package com.example.viaporter.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.viaporter.CallbackListener;
import com.example.viaporter.MainActivity;
import com.example.viaporter.R;
import com.example.viaporter.managers.FirebaseAdaptersManager;
import com.example.viaporter.models.PatronTripRequest;
import com.example.viaporter.models.TripState;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class TripRequestFragment extends Fragment {
    private static final String TAG = "viaPatron.TRFragment";

    private MainActivity mActivity;
    private NavController navController;
    private RecyclerView mBroadcastView;
    private RecyclerView mCurrentBidView;
    private FirebaseRecyclerAdapter<PatronTripRequest, FirebaseAdaptersManager.BroadcastRequestHolder> mBroadcastAdapter;
    private FirebaseRecyclerAdapter<PatronTripRequest, FirebaseAdaptersManager.CurrentBidHolder> mCurrentBidAdapter;

    CallbackListener<PatronTripRequest> broadcastRequestPositiveCallback;
    CallbackListener<PatronTripRequest> broadcastRequestNegativeCallback;
    CallbackListener<PatronTripRequest> currentBidPositiveCallback;
    CallbackListener<PatronTripRequest> currentBidNegativeCallback;

    public TripRequestFragment() {} // Empty constructor

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        return inflater.inflate(R.layout.trip_request_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = (MainActivity) requireActivity();

        setupButtonListeners();
        setUpViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkTripState();
    }

    private void setUpViews() {
        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);

        mBroadcastView = mActivity.findViewById(R.id.broadcast_request_table);
        mBroadcastView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBroadcastAdapter = mActivity.getFirebaseAdaptersManager().getBroadcastRequestAdapter(
                mBroadcastView,
                broadcastRequestPositiveCallback,
                broadcastRequestNegativeCallback);
        mBroadcastView.setAdapter(mBroadcastAdapter);

        mCurrentBidView = mActivity.findViewById(R.id.current_bids_table);
        mCurrentBidView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCurrentBidAdapter = mActivity.getFirebaseAdaptersManager().getCurrentBidAdapter(
                mCurrentBidView,
                currentBidPositiveCallback,
                currentBidNegativeCallback);
        mCurrentBidView.setAdapter(mCurrentBidAdapter);
    }

    private void setupButtonListeners() {
        broadcastRequestPositiveCallback = new CallbackListener<PatronTripRequest>() {
            @Override
            public void accept(PatronTripRequest data) {
                mActivity.getDataManager().setSelectedBidRequest(data);
                mActivity.getFirebaseDatabaseManager().setListeners(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            TripState curState = dataSnapshot.getValue(TripState.class);
                            switch (curState){
                                case NOT_STARTED:
                                    break;
                                case PATRON_STARTED:
                                    navigateToTripConfirmed();
                                    break;
                                case CANCELLED:
                                    break;
                                case ENDED:
                                    break;
                                default :
                                    Log.d(TAG,"State change value not in switch statement");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                }, "TripRequestPatronTripStatus");
                mActivity.getDialogManager().showTripRequestBiddingDialog();
            }
        };

        broadcastRequestNegativeCallback = new CallbackListener<PatronTripRequest>() {
            @Override
            public void accept(PatronTripRequest data) {
                mActivity.getDataManager().setSelectedBidRequest(data);
                // for testing
                mActivity.getDialogManager().showTripRequestBidSuccessDialog();
            }
        };

        currentBidPositiveCallback = new CallbackListener<PatronTripRequest>() {
            @Override
            public void accept(PatronTripRequest data) {
                mActivity.getDataManager().setSelectedBidRequest(data);
                mActivity.getDialogManager().showTripRequestBiddingDialog();
            }
        };

        currentBidNegativeCallback = new CallbackListener<PatronTripRequest>() {
            @Override
            public void accept(PatronTripRequest data) {
                mActivity.getDataManager().setSelectedBidRequest(data);
                mActivity.getDialogManager().showTripRequestCancelBidDialog();
            }
        };
    }

    private void navigateToTripConfirmed() {
        mActivity.getDataManager().setTripState(TripState.PATRON_STARTED);
        mActivity.getFirebaseDatabaseManager().setTripStatus(TripState.PORTER_STARTED);
        mActivity.getFirebaseDatabaseManager().cancelMyCurrentBid();
        navController.navigate(R.id.navigation_trip_confirmed);
    }

    private void checkTripState() {
        switch (mActivity.getDataManager().getTripState()){
            case CANCELLED:
                Toast.makeText(mActivity, "We're sorry, your Patron has cancelled the trip. Please try booking again.", Toast.LENGTH_LONG).show();
                mActivity.getDataManager().setTripState(TripState.NOT_STARTED);
                mActivity.getFirebaseDatabaseManager().setTripStatus(TripState.NOT_STARTED);
                break;
            case ENDED:
                mActivity.getDialogManager().showTripRequestReviewPageDialog();
                mActivity.getDataManager().setTripState(TripState.NOT_STARTED);
                mActivity.getFirebaseDatabaseManager().setTripStatus(TripState.NOT_STARTED);
                break;
            case PORTER_CANCELLED:
                Toast.makeText(mActivity, "You have cancelled your trip.", Toast.LENGTH_LONG).show();
                mActivity.getDataManager().setTripState(TripState.NOT_STARTED);
                mActivity.getFirebaseDatabaseManager().setTripStatus(TripState.NOT_STARTED);
                break;
            default:
                Log.d(TAG, "Trip state not addressed, in default case");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        mBroadcastAdapter.startListening();
        mCurrentBidAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBroadcastAdapter.stopListening();
        mCurrentBidAdapter.stopListening();
    }
}
