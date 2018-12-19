package com.example.viapatron2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TripRequestBidding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_request_bidding);
    }

    public void goToPorterMainScreenFromTripRequestBiddingScreenViaCancelRequestButton(View view){
        Intent intent = new Intent(this, PorterMain.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToTripRequestBidding2ScreenFromTripRequestBiddingScreenViaWhiteSpaceButton(View view){
        Intent intent = new Intent(this, TripRequestBidding2.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


}
