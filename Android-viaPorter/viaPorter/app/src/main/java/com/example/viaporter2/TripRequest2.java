package com.example.viaporter2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TripRequest2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_request2);
    }

    public void goToTripRequestsAuctionScreenFromTripRequests2ScreenViaLeftAuctionButton(View view){
        Intent intent = new Intent(this, TripRequestBidding.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void goToTripRequestsRejectScreenFromTripRequests2ScreenViaRightRejectButton(View view){
        Intent intent = new Intent(this, TripRequestBidding2.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
