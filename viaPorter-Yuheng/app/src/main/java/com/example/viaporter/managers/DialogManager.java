package com.example.viaporter.managers;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.viaporter.MainActivity;
import com.example.viaporter.R;
import com.example.viaporter.models.TripState;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

public class DialogManager {
    private static final String TAG = "DialogManager";

    private MainActivity mActivity;
    private NavController navController;

    private AlertDialog tripConfirmedStopTrip;
    private AlertDialog tripConfirmedCancelTrip;
    private AlertDialog tripRequestReviewPage;
    private AlertDialog tripRequestBidSuccess;
    private AlertDialog tripRequestBidding;
    private AlertDialog tripRequestCancelBid;

    /*                                      *
     * Use of Bill Pugh Singleton structure *
     *                                      */
    // Private constructor //
    private DialogManager() { }
    // Static inner class are not loaded until they are referenced
    private static class dialogManagerholder {
        private static DialogManager manager = new DialogManager();
    }
    // Global excess point
    public static DialogManager getSharedInstance() {
        return dialogManagerholder.manager;
    }
    /* ************************************ */

    public void setMainActivity(MainActivity mainActivity) {
        mActivity = mainActivity;
        navController = Navigation.findNavController(mActivity, R.id.my_nav_host_fragment);
        createAlertDialogs();
    }

    public void showTripConfirmedStopTripDialog() { tripConfirmedStopTrip.show(); }
    public void showTripConfirmedCancelTripDialog() { tripConfirmedCancelTrip.show(); }
    public void showTripRequestBidSuccessDialog() { tripRequestBidSuccess.show(); }
    public void showTripRequestReviewPageDialog() { tripRequestReviewPage.show(); }
    public void showTripRequestBiddingDialog() { tripRequestBidding.show(); }
    public void showTripRequestCancelBidDialog() { tripRequestCancelBid.show(); }

    private void createAlertDialogs() {
        /*
         * For TripConfirmedFragment
         */
        // dialog when stop trip button is pressed
        tripConfirmedStopTrip = new AlertDialog.Builder(mActivity)
                .setTitle(R.string.trip_confirmed_button_stop_title)
                .setMessage(R.string.trip_confirmed_button_stop_msg)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        navToHomeAndShowReview();
                        mActivity.getDataManager().setTripState(TripState.ENDED);
                        mActivity.getFirebaseDatabaseManager().setTripStatus(TripState.ENDED);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .create();

        // dialog when cancel trip button is pressed
        tripConfirmedCancelTrip = new AlertDialog.Builder(mActivity)
                .setTitle(R.string.trip_confirmed_button_cancel_trip)
                .setMessage(R.string.trip_confirmed_button_cancel_msg)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mActivity.getFirebaseDatabaseManager().setTripStatus(TripState.PORTER_CANCELLED);
                        mActivity.getDataManager().setTripState(TripState.PORTER_CANCELLED);
                        navController.popBackStack(R.id.navigation_jobs, false);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        // for testing
                        showTripConfirmedStopTripDialog();
                    }
                })
                .create();

        /*
         * For TripRequestFragment
         */
        // dialog for review page
        AlertDialog.Builder reviewPageBuilder = new AlertDialog.Builder(mActivity);
        final View reviewView = mActivity.getLayoutInflater().inflate(R.layout.review_custom_popup, null);
        reviewPageBuilder.setView(reviewView);
        Button reviewCancelButton = reviewView.findViewById(R.id.trip_ended_review_button);
        reviewCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripRequestReviewPage.cancel();
            }
        });
        tripRequestReviewPage = reviewPageBuilder.create();
        if (tripRequestReviewPage.getWindow() != null) {
            tripRequestReviewPage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        // dialog for bidding page
        AlertDialog.Builder biddingDialogBuilder = new AlertDialog.Builder(mActivity);
        final View biddingView = mActivity.getLayoutInflater().inflate(R.layout.place_bid_custom_popup, null);
        biddingDialogBuilder.setView(biddingView);
        final EditText bidAmountField = biddingView.findViewById(R.id.bid_amount_field);
        Button bidButton = biddingView.findViewById(R.id.bid_button);
        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Default bid amount
                Double currentBidAmount = 2.0;

                if (bidAmountField.getText().toString().length() > 0) {
                    currentBidAmount = Double.valueOf(bidAmountField.getText().toString());
                }
                mActivity.getFirebaseDatabaseManager().addToMyCurrentBids(currentBidAmount);
                mActivity.getFirebaseDatabaseManager().startPatronTripRequestStatusListener();
                tripRequestBidding.cancel();
            }
        });
        Button cancelButton = biddingView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripRequestBidding.cancel();
            }
        });
        tripRequestBidding = biddingDialogBuilder.create();

        // dialog for cancelling current bid
        tripRequestCancelBid = new AlertDialog.Builder(mActivity)
                .setTitle("Cancel Bid")
                .setMessage("Are you sure you want to withdraw your bid?")
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mActivity.getFirebaseDatabaseManager().cancelMyCurrentBid();
                        tripRequestCancelBid.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tripRequestCancelBid.cancel();
                    }
                })
                .create();

        // For testing only
        tripRequestBidSuccess = new AlertDialog.Builder(mActivity)
                .setTitle("Bid Success")
                .setMessage("The bid is successful")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        mActivity.getDataManager().setTripState(TripState.PATRON_STARTED);
                        navController.navigate(R.id.navigation_trip_confirmed);
                    }
                })
                .create();
    }

    private void navToHomeAndShowReview() {
        if (mActivity.getDataManager().getTripState() == TripState.IN_PROGRESS) {
            mActivity.getDataManager().setTripState(TripState.PATRON_STOPPED);
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.navigation_jobs, false)
                    .build();
            navController.navigate(R.id.navigation_jobs, null, navOptions);
        }
    }
}
