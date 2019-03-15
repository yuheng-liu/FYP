package com.example.viaporter.managers;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.example.viaporter.MainActivity;
import com.example.viaporter.R;
import com.example.viaporter.models.PorterTripAccept;
import com.example.viaporter.models.TripStatus;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

public class DialogManager {
    private static final String TAG = "DialogManager";

    private MainActivity mActivity;
    private NavController navController;
    private Gson gson;

    private AlertDialog tripConfirmedStopTrip;
    private AlertDialog tripConfirmedCancelTrip;
    private AlertDialog tripRequestBidSuccess;
    private AlertDialog tripRequestReviewPage;

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
        gson = new Gson();
    }

    public void showTripConfirmedStopTrip() { tripConfirmedStopTrip.show(); }
    public void showTripConfirmedCancelTrip() { tripConfirmedCancelTrip.show(); }
    public void showTripRequestBidSuccess() { tripRequestBidSuccess.show(); }
    public void showTripRequestReviewPage() { tripRequestReviewPage.show(); }

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
                        mActivity.dataManager.setTripStatus(TripStatus.CANCELLED);
                        navController.popBackStack(R.id.navigation_jobs, false);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        // for testing
                        showTripConfirmedStopTrip();
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

        // For testing only
        tripRequestBidSuccess = new AlertDialog.Builder(mActivity)
                .setTitle("Bid Success")
                .setMessage("The bid is successful")
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
                        mActivity.dataManager.setTripStatus(TripStatus.IN_PROGRESS);
                        navController.navigate(R.id.navigation_trip_confirmed);
                    }
                })
                .create();
    }

    private void navToHomeAndShowReview() {
        if (mActivity.dataManager.getTripStatus() == TripStatus.IN_PROGRESS) {
            mActivity.dataManager.setTripStatus(TripStatus.STOPPED);
            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.navigation_jobs, false)
                    .build();
            navController.navigate(R.id.navigation_jobs, null, navOptions);
        }
    }
}
