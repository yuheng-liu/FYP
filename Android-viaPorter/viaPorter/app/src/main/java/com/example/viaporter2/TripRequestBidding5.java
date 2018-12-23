package com.example.viaporter2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TripRequestBidding5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_request_bidding5);
    }

    public void goToTripRequestsBidding7ScreenFromTripRequestsBidding5ScreenViaSpaceButton(View view){
        Intent intent = new Intent(this, TripRequestBidding7.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToTripRequestsBidding8ScreenFromTripRequestsBidding5ScreenViaEditBidButton(View view){
        Intent intent = new Intent(this, TripRequestBidding8.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
