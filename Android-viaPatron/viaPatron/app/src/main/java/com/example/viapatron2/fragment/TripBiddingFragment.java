package com.example.viapatron2.fragment;

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
import androidx.navigation.Navigation;
import com.example.viapatron2.BidderAdapter;
import com.example.viapatron2.R;
import com.example.viapatron2.activity.MainActivity;
import com.github.nkzawa.socketio.client.Socket;

import java.util.Locale;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class TripBiddingFragment extends Fragment {

    private static final String TAG = "viaPatron.BidFragment";

    private NavController navController;
    private BidderAdapter mBidderAdapter;
    private MainActivity mActivity;

    // Views
    private Button cancelBidButton;
    private TextView timeLeftTv;
    private AlertDialog warningDialog;

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
        createAdapter();
    }

    private void setViews() {

        Log.d(TAG, "setViews");

        timeLeftTv = mActivity.findViewById(R.id.trip_request_bidding_time_left);
        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);
        cancelBidButton = mActivity.findViewById(R.id.trip_request_bidding_cancel_button);

        if (cancelBidButton != null) {
            cancelBidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    warningDialog.show();
                }
            });
        }


        // Create alert dialog for when user clicks cancel button
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.trip_bidding_cancel_msg)
                .setTitle(R.string.trip_bidding_cancel_title)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button

                        Log.d(TAG, "User clicked OK");
                        Socket socket = mActivity.getmSocketManager().getSocket();
                        socket.emit("disconnect", "viaPatron");

//                        mActivity.getmSocketManager().closeConnection();
//                        socket.on("userdisconnect", new Emitter.Listener() {
//                            @Override
//                            public void call(final Object... args) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        String data = (String) args[0];
//
//                                        Toast.makeText(mActivity, data, Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }
//                        });

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

    private void createAdapter() {

        Log.d(TAG, "createAdapter");

        RecyclerView mBidderView = getActivity().findViewById(R.id.trip_request_bidding_table);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mBidderView.setLayoutManager(linearLayoutManager);
        mBidderAdapter = new BidderAdapter(mActivity.getService().getDataManager());
        mBidderView.setAdapter(mBidderAdapter);
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
