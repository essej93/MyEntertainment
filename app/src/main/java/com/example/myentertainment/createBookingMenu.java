package com.example.myentertainment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class createBookingMenu extends AppCompatActivity {

    public static final String EXTRA_FACILITYID = " ";
    Button timePickerButton;
    int hour, minute, day;
    double start, finish, total;
    int sessionTime = 0;
    TextView finishTime, totalView, discountView;
    Facility currentFacility;
    int dayPos, partLanePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_booking_menu);

        // the following code gets the current facility
        int fID = (Integer) getIntent().getExtras().get(EXTRA_FACILITYID);
        currentFacility = Facility.facilities[fID];

        // sets the activity title to the current Facility
        this.setTitle("Schedule Booking");

        timePickerButton = findViewById(R.id.sessionStartTimeSelector);

        finishTime = findViewById(R.id.finishTimeText);

        updateCurrentDayTime();




        TextView activityTextView = (TextView) findViewById(R.id.activityName);
        activityTextView.setText(currentFacility.getName());

        ImageView img = (ImageView) findViewById(R.id.bookingImg);

        img.setImageResource(currentFacility.getImgID());
        //img.setContentDescription((currentFacility.getName()));

        // sets textView to "Lanes" if it's bowling
        if(currentFacility.getFacilityID() == 0){
            TextView pTextView = findViewById(R.id.pTextView);
            pTextView.setText("Lanes:");
        }

        // gets spinner information and sets the spinner listeners
        Spinner sessionSpinner = (Spinner) findViewById(R.id.sessionTimeSpinner);
        Spinner daySpinner = (Spinner) findViewById(R.id.daySpinner);
        Spinner partSpinner = (Spinner) findViewById(R.id.participantSpinner);
        sessionSpinner.setOnItemSelectedListener(spinnerListener);
        daySpinner.setOnItemSelectedListener(spinnerListener);
        partSpinner.setOnItemSelectedListener(spinnerListener);



    }

    // adapter item listener for the time, day and participant spinners
    // values are updated when new items are selected
    private final AdapterView.OnItemSelectedListener spinnerListener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                    switch(adapterView.getId()){
                        case(R.id.sessionTimeSpinner):
                            sessionTime = position + 1;
                            break;
                        case(R.id.daySpinner):
                            dayPos = position;

                            break;
                        case(R.id.participantSpinner):
                            partLanePos = position + 1;
                            break;
                    }
                    updateTotalInfo();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            };

    /*                    */

    // method used to set the default time/day based on the current time/day of the user
    private void updateCurrentDayTime() {

        Calendar currentDayTime = Calendar.getInstance();

        hour = currentDayTime.get(Calendar.HOUR_OF_DAY);
        minute = currentDayTime.get(Calendar.MINUTE);
        day = currentDayTime.get(Calendar.DAY_OF_WEEK);

        // the following if statements round the selected time to the nearest 30 mins
        if(minute <= 30){
            minute = 30;
        }
        else if (minute <= 59 && hour < 23){
            minute = 0;
            hour += 1;
        }
        else if (minute <= 59 && hour == 23 && day != 6){
            minute = 0;
            hour = 0;
            day += 1;
        }
        else if (minute <= 59 && hour == 23){
            minute = 0;
            hour = 0;
            day = 0;
        }

        updateStartTime();
        updateDay(day);



    }

    // method used to set the time that the user selects with the time picker
    public void updateStartTime(){
        start = hour;
        if(minute != 0) start += .5;
        timePickerButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
    }

    // method used to set the current day
    public void updateDay(int d){
        Spinner spinner = findViewById(R.id.daySpinner);

        spinner.setSelection(d);
    }

    // method used to show the time picker and allows user to select a time
    public void timePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int timerHour, int timerMinute) {
                hour = timerHour;
                minute = timerMinute;

                if (timerMinute >= 30 && timerMinute <=59) minute = 30;
                else if (timerMinute > 0) minute = 0;

                updateStartTime();

                if(sessionTime != 0){
                    updateFinishTime(sessionTime);
                }
                updateTotalInfo();
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    // method used to update finish time based on the session time selected.
    public void updateFinishTime(int sTime){
        finish = hour+sTime;
        if(minute != 0) finish += 0.5;
        finishTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour+sTime, minute));
    }

    // method used to update the total cost information as well as discount
    public void updateTotalInfo(){
        double discount = 0.0;

        updateFinishTime(sessionTime);
        updateStartTime();

        total = currentFacility.calculateCost(partLanePos, dayPos, start, finish);
        if(total >= 100){
            discount = total*0.1;
            total -= discount;
        }

        totalView = findViewById(R.id.totalTextView);
        discountView = findViewById(R.id.discountTextView);

        totalView.setText(String.format("$%.2f", total));
        discountView.setText(String.format("$%.2f", discount));


    }


    // method used to validate user selections based on facility
    public boolean validateSelection(){
        boolean valid = true;

        // checks number of participants for karaoke
        if (currentFacility.getFacilityID() == 3){
            if(partLanePos > 5){
                Toast.makeText(getApplicationContext(), "Too many participants. " +
                        "There can only be a max of 5 for karaoke", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        // checks to ensure magic show session time is a 2 hour block and on the correct start time
        else if (currentFacility.getFacilityID() == 4){
            if((sessionTime % 2) != 0){
                Toast.makeText(getApplicationContext(), "Magic show sessions are 2 hours each " +
                        "Please pick a 2 hour session or multiple 2 hour sessions", Toast.LENGTH_LONG).show();
                return false;
            }
            // if it's on a valid start time, it does nothing
            else if((start == currentFacility.sessionTimes[day][0]) || (start == currentFacility.sessionTimes[day][0] + 2) ||
                    (start == currentFacility.sessionTimes[day][0] + 4)){

            }
            // if invalid start time, it will show invalid message
            else{
                Toast.makeText(getApplicationContext(), "Invalid session start time" +
                        " Please pick a session that starts at " +
                        String.format("%.0f:00, %.0f:00 or %.0f:00", currentFacility.sessionTimes[day][0], currentFacility.sessionTimes[day][0] + 2, currentFacility.sessionTimes[day][0] + 4), Toast.LENGTH_LONG).show();

                return false;
            }

        }

        // following if statements determine whether a valid time as been selected
        if(start < currentFacility.sessionTimes[day][0] || start > currentFacility.sessionTimes[day][1]){
            Toast.makeText(getApplicationContext(), "Invalid start time." +
                    " Please pick a new start time between " +
                    String.format("%.0f:00 and %.0f:00", currentFacility.sessionTimes[day][0], currentFacility.sessionTimes[day][1]), Toast.LENGTH_LONG).show();
            return false;
        }
        else if (finish < currentFacility.sessionTimes[day][0] || finish > currentFacility.sessionTimes[day][1]){
            Toast.makeText(getApplicationContext(), "Invalid finish time." +
                    " Please pick a new session time between " +
                    String.format("%.0f:00 and %.0f:00", currentFacility.sessionTimes[day][0], currentFacility.sessionTimes[day][1]), Toast.LENGTH_LONG).show();
            return false;
        }


        return valid;
    }

    // submit booking method when user selects the submit booking button
    // it validates the users options and if it's valid it will submit the booking
    public void submitBooking(View v){

        if(validateSelection()){
            updateFinishTime(sessionTime);
            Booking.addBooking(new Booking(currentFacility.getFacilityID(), start, finish, total));
            Intent intent = new Intent(createBookingMenu.this, MainActivity.class);
            Toast.makeText(getApplicationContext(), "Booking submitted successfully", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
    }



}