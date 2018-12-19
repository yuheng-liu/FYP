package com.example.viapatron2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TripConfirmed2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_confirmed2);
    }

    public void goToTripInProgressScreenFromTripConfirmed2ScreenViaPressAgainToConfirmButton(View view){
        Intent intent = new Intent(this, TripInProgress.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToCancelTrip2ScreenFromTripConfirmed2ScreenViaCancelButton(View view){
        Intent intent = new Intent(this, CancelTrip2.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
