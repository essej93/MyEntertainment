package com.example.myentertainment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void newBooking(View view){
        Intent intent = new Intent(MainActivity.this, FacilityPicker.class);
        startActivity(intent);
    }

    public void viewBookings(View view){

        Intent intent = new Intent(MainActivity.this, BookingPicker.class);
        startActivity(intent);
    }
}