package com.example.viaporter2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

//public static final String EXTRA_MESSAGE = "com.example.viaporter2.MESSAGE";

public class PorterMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porter_main);
    }

    public void goToMyProfileScreenFromPorterMainScreenViaProfileButton(View view) {
        Intent intent = new Intent(this, MyProfile.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void goToStationsScreenFromPorterMainScreenViaSearchButton(View view) {
        Intent intent = new Intent(this, Stations.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /** Called when the user taps the Send button */
    //public void sendMessage(View view) {
      //  Intent intent = new Intent(this, Stations.class);
        //EditText editText = (EditText) findViewById(R.id.where_to);
       // String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
       // startActivity(intent);
   // }
}



