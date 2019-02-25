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
import androidx.navigation.Navigation;
import com.example.viapatron2.BidderAdapter;
import com.example.viapatron2.CallbackListener;
import com.example.viapatron2.R;
import com.example.viapatron2.activity.MainActivity;
import com.example.viapatron2.core.models.MyViewModel;
import com.example.viapatron2.core.models.PorterBidRequest;
import com.example.viapatron2.core.models.Trip;
import com.example.viapatron2.core.models.UserTripRequestSession;
import com.github.nkzawa.socketio.client.Socket;
import io.reactivex.functions.Consumer;

import java.util.Locale;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class TripBiddingFragment extends Fragment {

    private static final String TAG = "viaPatron.BidFragment";

    private NavController navController;
    private BidderAdapter mBidderAdapter;
    private MainActivity mActivity;
    private MyViewModel model;

    // Views
    private Button cancelBidButton;
    private TextView timeLeftTv;
    private TextView smallStationTitleLeft;
    private TextView smallStationTitleRight;
    private TextView toTitle;
    private TextView fromTitle;
    private TextView luggageTitle;
    private TextView luggageWeightTitle;
    private TextView priceTitle;
    private AlertDialog warningDialog;

    // model
    String modelStationName;
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
        createAdapter();
        setupSocketListeners();
    }

    private void setViews() {

        Log.d(TAG, "setViews");

        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);
        timeLeftTv = mActivity.findViewById(R.id.trip_request_bidding_time_left);
        cancelBidButton = mActivity.findViewById(R.id.trip_request_bidding_cancel_button);
        smallStationTitleLeft = mActivity.findViewById(R.id.tv_trip_bidding_station_left_side);
        smallStationTitleRight = mActivity.findViewById(R.id.tv_trip_bidding_station_right_side);
        toTitle = mActivity.findViewById(R.id.tv_trip_bidding_to);
        fromTitle = mActivity.findViewById(R.id.tv_trip_bidding_from);
        luggageTitle = mActivity.findViewById(R.id.tv_trip_bidding_luggage);
        luggageWeightTitle = mActivity.findViewById(R.id.tv_trip_bidding_luggage_weight);
        priceTitle = mActivity.findViewById(R.id.trip_bidding_price);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                Log.d(TAG, "onDestinationChanged " + destination.getLabel() + destination.getId());
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
                        // User clicked OK button

                        Socket socket = mActivity.getmSocketManager().getSocket();
                        socket.emit("disconnect", "viaPatron");
                        navController.navigateUp();
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

        modelStationName = userTripRequestSession.getStation();
        modelToLocation = userTripRequestSession.getToLocation();
        modelFromLocation = userTripRequestSession.getFromLocation();
        modelLuggageNo = userTripRequestSession.getNoOfLuggage();
        modelLuggageWeight = userTripRequestSession.getTotalLuggageWeight();

        model.getRequestSession().observe(this, users -> {

            // update UI
            if (smallStationTitleLeft != null) {
                smallStationTitleLeft.setText(modelStationName);
            }

            if (smallStationTitleRight != null) {
                smallStationTitleRight.setText(modelStationName);
            }

            if (toTitle != null) {
                toTitle.setText(modelToLocation);
            }

            if (fromTitle != null) {
                fromTitle.setText(modelFromLocation);
            }

            if (luggageTitle != null) {
                String luggageNum = String.valueOf(modelLuggageNo) + " " + R.string.trip_request_confirm_luggage_KG_text;
                luggageTitle.setText(luggageNum);
            }

            if (luggageWeightTitle != null) {
                String luggageWeightText = String.valueOf(modelLuggageWeight) + " " + R.string.trip_request_confirm_luggage_text;
                luggageWeightTitle.setText(String.valueOf(luggageWeightText));
            }
        });
    }

    private void createAdapter() {

        Log.d(TAG, "createAdapter");

        RecyclerView mBidderView = mActivity.findViewById(R.id.trip_request_bidding_table);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mBidderView.setLayoutManager(linearLayoutManager);
        mBidderAdapter = new BidderAdapter(mActivity.getService().getDataManager());

        mBidderAdapter.setOnPositiveButtonClicked(new CallbackListener<PorterBidRequest>() {
            @Override
            public void accept(PorterBidRequest data) {

                String alertMsg = getString(R.string.trip_bidding_confirm_msg) + " " + data.getBidAmount() + "?";

                new AlertDialog.Builder(mActivity)
                        .setTitle(R.string.trip_bidding_confirm_title)
                        .setMessage(alertMsg)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // todo: send socket msg on acceptance to viaPorter
                                mActivity.getmSocketManager().acceptBidder(setTrip());
                                navController.navigate(R.id.navigation_trip_confirmed);
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
        });
        mBidderView.setAdapter(mBidderAdapter);
    }

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
                runOnUiThread(new Runnable() {
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
                // todo: upon timer end, end bidding appropriately
                //getSocketManager().stopBidding(currentRideRequest.getRideRequestId());
                Socket socket = mActivity.getmSocketManager().getSocket();
                socket.emit("disconnect", "viaPatron");
                navController.navigateUp();
            }
        };

        countDownTimer.start();
        isCountingDown = true;
    }

    @Override
    public void onStart() {
        super.onStart();
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
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
