package com.example.myentertainment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// booking picker class, lists the bookings under Booking.bookings arraylist
// when user selects the booking it will send information to BookingViewer
public class BookingPicker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_picker);

        this.setTitle("Booking Menu");

        ListView listFacilities = (ListView) findViewById(R.id.bookingList);

        ArrayAdapter<Booking> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                Booking.bookings);

        listFacilities.setAdapter(listAdapter);
        listFacilities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(BookingPicker.this, BookingViewer.class);

                intent.putExtra(BookingViewer.EXTRA_BOOKINGID, (int)id);
                startActivity(intent);
            }
        });
    }
}