package com.example.viaporter2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Stations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations);
    }

    public void goToPorterMainScreenFromStationsScreenViaCrossBackButton(View view){
        Intent intent = new Intent(this, PorterMain.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToTripRequestScreenFromStationsScreenViaMumbaiCentralButton(View view){
        Intent intent = new Intent(this, TripRequest.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    //Get the Intent that started this activity and extract the string
    //Intent intent = getIntent();
    //String message = intent.getStringExtra(PorterMain.EXTRA_MESSAGE);

    // Capture the layout's TextView and set the string as its text
    // TextView textView = findViewById(R.id.mum);
    // textView.setText(message);
}
