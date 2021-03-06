package com.example.viapatron2.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import com.example.viapatron2.BidderAdapter;
import com.example.viapatron2.CallbackListener;
import com.example.viapatron2.R;
import com.example.viapatron2.activity.MainActivity;
import com.example.viapatron2.core.models.*;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import io.reactivex.functions.Consumer;

import java.util.Locale;

public class TripBiddingFragment extends Fragment {

    private static final String TAG = "viaPatron.BidFragment";

    private NavController navController;
    private BidderAdapter mBidderAdapter;
    private CallbackListener<PorterBidRequest> mOnPositiveButtonClicked;
    private FirebaseRecyclerAdapter<PorterBidRequest, PorterBidsViewHolder> mBidderViewAdapter;
    private MainActivity mActivity;
    private MyViewModel model;

    // Views
    private Button cancelBidButton;
    private TextView timeLeftTv;
    private TextView toTitle;
    private TextView fromTitle;
    private TextView luggageTitle;
    private TextView luggageWeightTitle;
    private AlertDialog warningDialog;

    // model
    String modelToLocation;
    String modelFromLocation;
    int modelLuggageNo;
    int modelLuggageWeight;

    private UserTripRequestSession userTripRequestSession;

    // items
    private CountDownTimer countDownTimer;
    private boolean isCountingDown = false;


    public TripBiddingFragment() {
        // Empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        return inflater.inflate(R.layout.trip_bidding_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = (MainActivity) requireActivity();

        setViews();
        setUpViewModel();
//        createAdapter();
//        setupSocketListeners();

        mOnPositiveButtonClicked = new CallbackListener<PorterBidRequest>() {

            @Override
            public void accept(PorterBidRequest data) {

                String alertMsg = getString(R.string.trip_bidding_confirm_msg) + " $" + data.getBidAmount() + "?";

                new AlertDialog.Builder(mActivity)
                        .setTitle(R.string.trip_bidding_confirm_title)
                        .setMessage(alertMsg)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

//                                mActivity.getmSocketManager().acceptBidder(setTrip());

                                if (countDownTimer != null) {
                                    countDownTimer.cancel();
                                }

                                NavOptions navOptions = new NavOptions.Builder()
                                        .setPopUpTo(R.id.navigation_trip, true)
                                        .build();
                                navController.navigate(R.id.navigation_trip_confirmed, null, navOptions);

                                // Update trip status to manage navigation
                                mActivity.getmDataManager().updateTripStatus(TripStatus.PATRON_STARTED);

                                // Update selected porter
                                mActivity.getmDataManager().setAcceptedBidRequest(data);

                                // Update triggering of start trip for porter
                                if (FirebaseAuth.getInstance().getUid() != null) {
                                    mActivity.getmDatabase()
                                            .child("Patrons")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .child("tripState")
                                            .setValue(TripStatus.PATRON_STARTED);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                return;
                            }
                        })
                        .create()
                        .show();

            }
        };

        createFirebaseAdapter();
    }

    private void setViews() {

        Log.d(TAG, "setViews");

        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);
        timeLeftTv = mActivity.findViewById(R.id.trip_request_bidding_time_left);
        cancelBidButton = mActivity.findViewById(R.id.trip_request_bidding_cancel_button);
        toTitle = mActivity.findViewById(R.id.tv_trip_bidding_to);
        fromTitle = mActivity.findViewById(R.id.tv_trip_bidding_from);
        luggageTitle = mActivity.findViewById(R.id.tv_trip_bidding_luggage);
        luggageWeightTitle = mActivity.findViewById(R.id.tv_trip_bidding_luggage_weight);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.d(TAG, "onDestinationChanged " + destination.getLabel());
            }
        });

        if (cancelBidButton != null) {
            cancelBidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    warningDialog.show();
                }
            });
        }

        // Create alert dialog for when user clicks cancel button
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage(R.string.trip_bidding_cancel_msg)
                .setTitle(R.string.trip_bidding_cancel_title)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            if (FirebaseAuth.getInstance().getUid() != null) {
                                mActivity.getmDatabase().child("Broadcast Trip Requests").child(FirebaseAuth.getInstance().getUid()).removeValue();
                                mActivity.getmDatabase().child("Patrons").child(FirebaseAuth.getInstance().getUid()).child("porterBidRequest").removeValue();
                            }
                            navController.navigateUp();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                        Log.d(TAG, "User clicked cancel");
                    }
                });

        warningDialog = builder.create();
    }

    private void setUpViewModel() {

        Log.d(TAG, "setUpViewModel");

        // Re-created activities receive the same MyViewModel instance created by the first activity.
        model = ViewModelProviders.of(mActivity).get(MyViewModel.class);
        userTripRequestSession = model.getUserTripRequestSession();

        modelToLocation = userTripRequestSession.getTripEndLocation();
        modelFromLocation = userTripRequestSession.getTripStartLocation();
        modelLuggageNo = userTripRequestSession.getNumberOfLuggage();
        modelLuggageWeight = userTripRequestSession.getTotalLuggageWeight();

        model.getRequestSession().observe(this, users -> {

            // update UI

            if (toTitle != null) {
                toTitle.setText(modelToLocation);
            }

            if (fromTitle != null) {
                fromTitle.setText(modelFromLocation);
            }

            if (luggageTitle != null) {
                String luggageNum = String.valueOf(modelLuggageNo) + " " + getResources().getString(R.string.trip_request_confirm_luggage_text);
                luggageTitle.setText(luggageNum);
            }

            if (luggageWeightTitle != null) {
                String luggageWeightText = String.valueOf(modelLuggageWeight) + " " + getResources().getString(R.string.trip_request_confirm_luggage_KG_text);
                luggageWeightTitle.setText(String.valueOf(luggageWeightText));
            }
        });
    }


    private void createFirebaseAdapter() {

        Log.d(TAG, "createFirebaseAdapter");
        RecyclerView mBidderRecyclerView = mActivity.findViewById(R.id.trip_request_bidding_table);
        mBidderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBidderRecyclerView.setHasFixedSize(true);

        DatabaseReference curDatabase = mActivity.getmDatabase()
                .child("Patrons")
                .child(FirebaseAuth.getInstance().getUid())
                .child("porterBidRequest");

        curDatabase.keepSynced(true);

        Query query = curDatabase.limitToLast(50);

        FirebaseRecyclerOptions<PorterBidRequest> options =
                new FirebaseRecyclerOptions.Builder<PorterBidRequest>()
                        .setQuery(query, PorterBidRequest.class)
                        .build();

        mBidderViewAdapter = new FirebaseRecyclerAdapter<PorterBidRequest, PorterBidsViewHolder>(options) {
            @NonNull
            @Override
            public PorterBidsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                Log.d(TAG, "onCreateViewHolder");

                // create a new view
                View itemView = (View) LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.bidder_item, viewGroup, false);

                // set the view's size, margins, padding and layout parameters...
                PorterBidsViewHolder vh = new PorterBidsViewHolder(itemView);

                vh.bidderIndex = itemView.findViewById(R.id.bidding_porter_index);
                vh.bidderName = itemView.findViewById(R.id.bidding_porter_name);
                vh.bidAmount = itemView.findViewById(R.id.bid_amount);
                vh.mPositiveButton = itemView.findViewById(R.id.positiveButton);

                return vh;
            }

            @Override
            protected void onBindViewHolder(@NonNull PorterBidsViewHolder holder, int position, @NonNull PorterBidRequest model) {
                Log.d(TAG, "onBindViewHolder");

                try {
                    int indexPos = position + 1;
                    String bidAmountString = "$" + String.valueOf(model.getBidAmount());

                    holder.bidderIndex.setText(String.valueOf(indexPos));
                    holder.bidderName.setText(model.getPorterName());
                    holder.bidAmount.setText(bidAmountString);
                    holder.mPositiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mOnPositiveButtonClicked == null) { return; }
                            mOnPositiveButtonClicked.accept(model);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        mBidderRecyclerView.setAdapter(mBidderViewAdapter);
    }


    public static class PorterBidsViewHolder extends RecyclerView.ViewHolder{

        public TextView bidderIndex;
        public TextView bidderName;
        public TextView bidAmount;
        View mPositiveButton, mNegativeButton;

        public PorterBidsViewHolder(View itemView){
            super(itemView);
        }
    }



//    private void createAdapter() {
//
//        Log.d(TAG, "createAdapter");
//
//        RecyclerView mBidderView = mActivity.findViewById(R.id.trip_request_bidding_table);
//        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        mBidderView.setLayoutManager(linearLayoutManager);
//        mBidderAdapter = new BidderAdapter(mActivity.getService().getDataManager());
//
//        // TODO: remove below part when not testing
//        // START OF TESTING SEGMENT
//        PorterBidRequest testPorterBidRequest = new PorterBidRequest();
//        testPorterBidRequest.setPorterName("Max");
//        testPorterBidRequest.setBidAmount(2.2);
//        mBidderAdapter.addToDataSet(testPorterBidRequest);
//        // END OF TESTING SEGMENT
//
//
//        mBidderAdapter.setOnPositiveButtonClicked(new CallbackListener<PorterBidRequest>() {
//            @Override
//            public void accept(PorterBidRequest data) {
//
//                String alertMsg = getString(R.string.trip_bidding_confirm_msg) + " $" + data.getBidAmount() + "?";
//
//                new AlertDialog.Builder(mActivity)
//                        .setTitle(R.string.trip_bidding_confirm_title)
//                        .setMessage(alertMsg)
//                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                // Update trip status to manage navigation
//                                mActivity.getmDataManager().updateTripStatus(TripStatus.PATRON_STARTED);
//
//                                // todo: send socket msg on acceptance to viaPorter
//                                mActivity.getmSocketManager().acceptBidder(setTrip());
//
//                                if (countDownTimer != null) {
//                                    countDownTimer.cancel();
//                                }
//                                NavOptions navOptions = new NavOptions.Builder()
//                                        .setPopUpTo(R.id.navigation_trip, false)
//                                        .build();
//                                navController.navigate(R.id.navigation_trip_confirmed, null, navOptions);
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // do nothing
//                                return;
//                            }
//                        })
//                        .create()
//                        .show();
//            }
//        });
//        mBidderView.setAdapter(mBidderAdapter);
//    }

    private void setupSocketListeners() {
        mActivity.addDisposable(mActivity.getmSocketManager().addOnPorterBidRequest(new Consumer<PorterBidRequest>() {
            @Override
            public void accept(final PorterBidRequest bidRequest) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBidderAdapter.addToDataSet(bidRequest);
                    }
                });
            }
        }));
    }

    private Trip setTrip() {

        Trip tripSession = new Trip();
        tripSession.setPatronLocation(mActivity.getmDataManager().getCurrentLocation());

        Log.d(TAG, "patronLocation = " + mActivity.getmDataManager().getCurrentLocation());

        tripSession.setPickupLocation(modelFromLocation);
        tripSession.setDropoffLocation(modelToLocation);
        return tripSession;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void startCountdown() {

        Log.d(TAG, "startCountdown");

        countDownTimer = new CountDownTimer(180000, 1000) {
            @Override
            public void onTick(final long millisUntilFinished) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long seconds = millisUntilFinished / 1000;
                        long minutes = seconds / 60;
                        seconds = seconds % 60;
                        timeLeftTv.setText(String.format(Locale.US, "%d : %02d", minutes, seconds));
                    }
                });
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish");

                if (FirebaseAuth.getInstance().getUid() != null) {
                    mActivity.getmDatabase().child("Broadcast Trip Requests").child(FirebaseAuth.getInstance().getUid()).removeValue();
                    mActivity.getmDatabase().child("Patrons").child(FirebaseAuth.getInstance().getUid()).child("porterBidRequest").removeValue();
                    navController.navigateUp();
                }
            }
        };

        countDownTimer.start();
        isCountingDown = true;
    }



    @Override
    public void onStart() {
        super.onStart();

        if (mBidderViewAdapter != null) {
            mBidderViewAdapter.startListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume " + isCountingDown);

        if (!isCountingDown) {
            startCountdown();
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
        if (mBidderViewAdapter != null) {
            mBidderViewAdapter.stopListening();
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        if (countDownTimer != null) {
            countDownTimer.cancel();

            if (FirebaseAuth.getInstance().getUid() != null) {
                mActivity.getmDatabase().child("Broadcast Trip Requests").child(FirebaseAuth.getInstance().getUid()).removeValue();
                mActivity.getmDatabase().child("Patrons").child(FirebaseAuth.getInstance().getUid()).child("porterBidRequest").removeValue();
            }
        }
        super.onDestroy();
    }
}
