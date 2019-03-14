package com.example.viaporter.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.example.viaporter.CallbackListener;
import com.example.viaporter.MainActivity;
import com.example.viaporter.adapters.BroadcastAdapter;
import com.example.viaporter.adapters.CurrentBidAdapter;
import com.example.viaporter.R;
import com.example.viaporter.models.PatronTripRequest;
import com.example.viaporter.models.PatronTripSuccess;
import com.example.viaporter.models.PorterBidRequest;
import com.example.viaporter.models.PorterTripAccept;
import com.example.viaporter.models.TripStatus;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import io.reactivex.functions.Consumer;

public class TripRequestFragment extends Fragment {
    private static final String TAG = "viaPatron.TRFragment";

    private MainActivity mActivity;
    private NavController navController;
    private RecyclerView mBroadcastView;
    private RecyclerView mCurrentBidView;
    private BroadcastAdapter mBroadcastAdapter;
    private CurrentBidAdapter mCurrentBidAdapter;
    private AlertDialog bidSuccessDialog;
    private Gson gson;
    private PatronTripRequest selectedBidRequest;
    private AlertDialog reviewDialog;

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
        gson = new Gson();
        setUpViews();
        setupSocket();
        setupSocketListeners();
        setupButtonListeners();
    }

    private void setUpViews() {
        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);

        mBroadcastView = mActivity.findViewById(R.id.broadcast_request_table);
        mBroadcastView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBroadcastAdapter = new BroadcastAdapter();
        mBroadcastView.setAdapter(mBroadcastAdapter);

        mCurrentBidView = mActivity.findViewById(R.id.current_bids_table);
        mCurrentBidView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCurrentBidAdapter = new CurrentBidAdapter();
        mCurrentBidView.setAdapter(mCurrentBidAdapter);

        // Create alert dialog for when a bid is successful
        AlertDialog.Builder bidSuccessBuilder = new AlertDialog.Builder(mActivity);
        bidSuccessBuilder.setMessage("The bid is successful")
                .setTitle("Bid Success")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        try {
                            PorterTripAccept newTripAccept = new PorterTripAccept("Yuheng", mActivity.dataManager.getCurrentLocation(),"4.5");
                            JSONObject data = new JSONObject(gson.toJson(newTripAccept));
                            mActivity.socketManager.emitJSON("accept_trip", data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        navigateToTripConfirmed();
                    }
                });
        bidSuccessDialog = bidSuccessBuilder.create();

        // Create dialog for review page
        AlertDialog.Builder reviewPageBuilder = new AlertDialog.Builder(mActivity);
        final View reviewView = getLayoutInflater().inflate(R.layout.review_custom_popup, null);
        reviewPageBuilder.setView(reviewView);
        Button reviewCancelButton = reviewView.findViewById(R.id.trip_ended_review_button);
        reviewCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewDialog.cancel();
            }
        });
        reviewDialog = reviewPageBuilder.create();
        if (reviewDialog.getWindow() != null) {
            reviewDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        if (mActivity.dataManager.getTripStatus() == TripStatus.STOPPED){
            reviewDialog.show();
            mActivity.dataManager.setTripStatus(TripStatus.IDLE);
        }
    }

    private void setupSocket() {
        mActivity.socketManager.connectSocket();
        mActivity.socketManager.emitString("join", "viaPorter");
    }

    private void setupSocketListeners() {
        mActivity.addDisposable(mActivity.socketManager.addOnPatronTripRequest(new Consumer<PatronTripRequest>() {
            @Override
            public void accept(final PatronTripRequest tripRequest) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBroadcastAdapter.addToDataSet(tripRequest);
                    }
                });
            }
        }));

        mActivity.addDisposable(mActivity.socketManager.addOnPatronTripSuccess(new Consumer<PatronTripSuccess>() {
            @Override
            public void accept(PatronTripSuccess patronTripSuccess) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        bidSuccessDialog.show();
                        try {
                            PorterTripAccept newTripAccept = new PorterTripAccept("Yuheng", mActivity.dataManager.getCurrentLocation(),"4.5");
                            JSONObject data = new JSONObject(gson.toJson(newTripAccept));
                            mActivity.socketManager.emitJSON("accept_trip", data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        navigateToTripConfirmed();
                    }
                });
            }
        }));
    }

    private void setupButtonListeners() {
        mBroadcastAdapter.setOnPositiveButtonClicked(new CallbackListener<PatronTripRequest>() {
            @Override
            public void accept(PatronTripRequest data) {
                selectedBidRequest = data;

                new AlertDialog.Builder(mActivity)
                        .setTitle("Make Default Offer")
                        .setMessage("Would you like to make a default offer of $2?")
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    PorterBidRequest newBidRequest = new PorterBidRequest("Yuheng", 10.0);
                                    JSONObject data = new JSONObject(gson.toJson(newBidRequest));
                                    mActivity.socketManager.emitJSON("bid_request", data);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mBroadcastAdapter.removeDataSet(selectedBidRequest);
                                mCurrentBidAdapter.addToDataSet(selectedBidRequest);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .create()
                        .show();
            }
        });

        mBroadcastAdapter.setOnNegativeButtonClicked(new CallbackListener<PatronTripRequest>() {
            @Override
            public void accept(PatronTripRequest data) {
                selectedBidRequest = data;
                bidSuccessDialog.show();
            }
        });

        mCurrentBidAdapter.setOnPositiveButtonClicked(new CallbackListener<PatronTripRequest>() {
            @Override
            public void accept(PatronTripRequest data) {
                selectedBidRequest = data;
            }
        });

        mCurrentBidAdapter.setOnNegativeButtonClicked(new CallbackListener<PatronTripRequest>() {
            @Override
            public void accept(PatronTripRequest data) {
                selectedBidRequest = data;

                new AlertDialog.Builder(mActivity)
                        .setTitle("Cancel Bid")
                        .setMessage("Are you sure you want to withdraw your bid?")
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mCurrentBidAdapter.removeDataSet(selectedBidRequest);
                                mBroadcastAdapter.addToDataSet(selectedBidRequest);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    private void navigateToTripConfirmed() {
//        NavOptions navOptions = new NavOptions.bidSuccessBuilder()
//                .setPopUpTo(R.id.navigation_jobs, true)
//                .build();
//        navController.navigate(R.id.navigation_trip_confirmed, null, navOptions);
        mActivity.dataManager.setTripStatus(TripStatus.IN_PROGRESS);
        navController.navigate(R.id.navigation_trip_confirmed);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mActivity.socketManager.disconnectSocket();
    }
}
