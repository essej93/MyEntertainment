package com.example.myentertainment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

// Facility picker class used to list the facilities in the Facility.facilities array
// User will pick the facility which will determine the createBookingMenu layout and validation
public class FacilityPicker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_picker);

        this.setTitle("Select Facility");

        ListView listFacilities = (ListView) findViewById(R.id.bookingList);

        ArrayAdapter<Facility> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                Facility.facilities);

        listFacilities.setAdapter(listAdapter);
        listFacilities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(FacilityPicker.this, createBookingMenu.class);

                intent.putExtra(createBookingMenu.EXTRA_FACILITYID, (int)id);
                startActivity(intent);
            }
        });
    }
}