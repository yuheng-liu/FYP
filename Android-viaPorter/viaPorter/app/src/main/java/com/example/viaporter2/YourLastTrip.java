package com.example.viaporter2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class YourLastTrip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_last_trip);
    }

    public void goToPorterMainScreenFromYourLastTripScreenViaRateYourRideStarsButton1(View view){
        Intent intent = new Intent(this, PorterMain.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToPorterMainScreenFromYourLastTripScreenViaRateYourRideStarsButton2(View view){
        Intent intent = new Intent(this, PorterMain.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToPorterMainScreenFromYourLastTripScreenViaRateYourRideStarsButton3(View view){
        Intent intent = new Intent(this, PorterMain.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToPorterMainScreenFromYourLastTripScreenViaRateYourRideStarsButton4(View view){
        Intent intent = new Intent(this, PorterMain.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToPorterMainScreenFromYourLastTripScreenViaRateYourRideStarsButton5(View view){
        Intent intent = new Intent(this, PorterMain.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
