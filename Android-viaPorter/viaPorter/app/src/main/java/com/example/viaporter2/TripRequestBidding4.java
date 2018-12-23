package com.example.viaporter2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TripRequestBidding4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_request_bidding4);
    }

    public void goToTripRequestBidding5ScreenFromTripRequestsBidding4ScreenViaBidSuccessSpaceButton(View view){
        Intent intent = new Intent(this, TripRequestBidding5.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToTripRequestBidding6ScreenFromTripRequestsBidding4ScreenViaBidSuccessSpaceButton(View view){
        Intent intent = new Intent(this, TripRequestBidding6.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToTripRequestBidding7ScreenFromTripRequestsBidding4ScreenViaBidSuccessSpaceButton(View view){
        Intent intent = new Intent(this, TripRequestBidding7.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
