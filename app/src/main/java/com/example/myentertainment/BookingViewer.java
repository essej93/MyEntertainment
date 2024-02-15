package com.example.myentertainment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

// Booking viewer class is used to view the selected booking information
public class BookingViewer extends AppCompatActivity {
    public static final String EXTRA_BOOKINGID = " ";
    Booking currentBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_viewer);

        int bID = (Integer) getIntent().getExtras().get(EXTRA_BOOKINGID);
        currentBooking = Booking.bookings.get(bID);


        // text views which will show the booking information
        TextView bookingNumberTextView = findViewById(R.id.bookingNumTextView);
        TextView facilityTextView = findViewById(R.id.facilityNameTextView);
        TextView startTextView = findViewById(R.id.bookingStartTextView);
        TextView finishTextView = findViewById(R.id.bookingFinishTextView);
        TextView costTextView = findViewById(R.id.bookingCostTextView);

        bookingNumberTextView.setText("#"+ currentBooking.getBookingID());
        facilityTextView.setText(currentBooking.getFacilityName());
        costTextView.setText(String.format("$%.2f", currentBooking.getBookingCost()));


        if((currentBooking.getStartTime() % 1) == 0.5){
            startTextView.setText(String.format("%.0f:30", currentBooking.getStartTime()));
        }
        else{
            startTextView.setText(String.format("%.0f:00", currentBooking.getStartTime()));
        }

        if((currentBooking.getFinishTime() % 1) == 0.5){
            finishTextView.setText(String.format("%.0f:30", currentBooking.getFinishTime()));
        }
        else{
            finishTextView.setText(String.format("%.0f:00", currentBooking.getFinishTime()));
        }




    }
}
