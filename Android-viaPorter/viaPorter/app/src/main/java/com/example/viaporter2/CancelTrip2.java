package com.example.viaporter2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CancelTrip2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_trip2);
    }

    public void goToPorterMainScreenFromCancelTrip2ScreenViaConfirmButton(View view){
        Intent intent = new Intent(this, PorterMain.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToTripConfirmed2ScreenFromCancelTrip2ScreenViaCancelButton(View view){
        Intent intent = new Intent(this, TripConfirmed2.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
