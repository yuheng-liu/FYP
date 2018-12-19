package com.example.viaporter2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TripConfirmed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_confirmed);
    }

    public void goToTripConfirmed2ScreenFromTripConfirmedScreenViaStartTripButton(View view){
        Intent intent = new Intent(this, TripConfirmed2.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void goToCancelTripScreenFromStationsScreenViaCancelButton(View view){
        Intent intent = new Intent(this, CancelTrip.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
