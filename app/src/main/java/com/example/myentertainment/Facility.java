package com.example.myentertainment;

public class Facility {
    // variables to hold information for each facility
    // hourlyMultiplier calculates the cost based on whether it is charged
    // per hour, per 2 hours or every 30 mins.
    // i.e 1 hour will have a multiplier of 1, 30 mins will have a multiplier of 2
    // and 2 hours will have a multiplier of 0.5
    String name;
    int facilityID, imgID;
    double hourlyMultiplier;
    int[] costsList;
    double[][] sessionTimes;

    Facility(int facilityID, String name, double multiplier, int imgID, int[] costs, double[][] session){
        this.facilityID = facilityID;
        this.name = name;
        hourlyMultiplier = multiplier;
        this.imgID = imgID;

        costsList = costs;
        sessionTimes = session;
    }

    public String getName(){return this.name;}

    public double getHourlyMultiplier() {return hourlyMultiplier;}

    public int getFacilityID() {return facilityID;}

    public int getImgID(){return imgID;}

    public int[] getCostsList(){return costsList;}

    public double[][] getSessionTimes(){return sessionTimes;}

    public String toString(){return this.name;}


    // arrays which hold information on each facility/activity session times and costs.
    static final int[] bowlingCosts = {15, 10, 20, 15};
    static final double[][] bowlingTime = {{6.00, 22.00},{6.00, 22.00},{6.00, 22.00},{6.00, 22.00},
            {6.00, 22.00},{6.00, 22.00},{6.00, 22.00}};
    static final int[] cafeCosts = {12, 10, 15, 10};
    static final double[][] cafeTimes = {{6.00, 22.00},{6.00, 22.00},{6.00, 22.00},{6.00, 22.00},
            {6.00, 22.00},{6.00, 22.00},{6.00, 22.00}};
    static final int[] laserCosts = {10, 8, 12, 10};
    static final double[][] laserTimes = {{6.00, 22.00},{6.00, 22.00},{6.00, 22.00},{6.00, 22.00},
            {6.00, 22.00},{6.00, 22.00},{6.00, 22.00}};
    static final int[] karaokeCosts = {30, 25, 35, 30};
    static final double[][] karaokeTimes = {{6.00, 22.00},{6.00, 22.00},{6.00, 22.00},{6.00, 22.00},
            {6.00, 22.00},{6.00, 22.00},{6.00, 22.00}};
    static final int[] magicShowCosts = {50, 0, 50, 0};
    static final double[][] magicShowTimes = {{12.00, 22.00},{12.00, 22.00},{16.00, 22.00},{16.00, 22.00},
            {16.00, 22.00},{16.00, 22.00},{16.00, 22.00}};
    static final int[] rockClimbingCosts = {15, 10, 20, 15};
    static final double[][] rockClimbingTimes = {{6.00, 22.00},{6.00, 22.00},{6.00, 22.00},{6.00, 22.00},
            {6.00, 22.00},{6.00, 22.00},{6.00, 22.00}};
    static final int[] kidPlaylandCosts = {15, 10, 20, 15};
    static final double[][] kidPlaylandTimes = {{6.00, 22.00},{6.00, 22.00},{6.00, 22.00},{6.00, 22.00},
            {6.00, 22.00},{6.00, 22.00},{6.00, 22.00}};


    public static final Facility[] facilities = {
            new Facility(0, "Bowling", 1, R.drawable.bowling, bowlingCosts, bowlingTime ),
            new Facility(1, "Internet & Gaming Cafe", 1, R.drawable.internetcafe, cafeCosts, cafeTimes),
            new Facility(2, "Laser Arena", 2, R.drawable.lasertag, laserCosts, laserTimes),
            new Facility(3, "Karaoke", 1, R.drawable.karaoke, karaokeCosts, karaokeTimes),
            new Facility(4, "Magic Show", 0.5, R.drawable.magic, magicShowCosts, magicShowTimes),
            new Facility(5, "Rock Climbing", 1, R.drawable.rockclimbing, rockClimbingCosts, rockClimbingTimes),
            new Facility(6, "Kid Playland", 1, R.drawable.kidplayland, kidPlaylandCosts, kidPlaylandTimes)
    };

    public double calculateCost(int participants, int day, double start, double finish){
        double total;
        double peak = 0;
        double offPeak = 0;


        // if statement first calculates peak/offpeak hours then calculates the cost for weekdays
        if (day > 1){

            // if statements determines if time range is in peak/offpeak or both
            if((start >= 6 && start <= 16) && (finish <= 22 && finish > 16)){
                offPeak = 16 - start;
                peak = finish - 16;
            }
            else if(start >= 6 && finish <=16){
                offPeak = finish - start;
            }
            else if(start >= 16 && finish > 16){
                peak = finish - start;
            }

            if(offPeak >= 0){
                offPeak = (costsList[1] * hourlyMultiplier) * offPeak * participants;
            }
            else {
                offPeak = 0;
            }
            if(peak >= 0){
                peak = (costsList[0] * hourlyMultiplier) * peak * participants;
            }
            else{
                peak = 0;
            }
        }

        // else if statement calculates peak/offpeak hours then calculates cost for weekends
        else {

            // / if statements determines if time range is in peak/offpeak or both
            if((start >= 6 && start <= 12) && (finish <= 22 && finish > 12)){
                offPeak = 12 - start;
                peak = finish - 12;
            }
            else if(start >= 6 && finish <=12){
                offPeak = finish - start;
            }
            else if(start >= 12 && finish <= 22){
                peak = finish - start;
            }

            // following if statements is to reduce any error for miscalculations
            if(offPeak >= 0){
                offPeak = (costsList[3] * hourlyMultiplier) * offPeak * participants;
            }
            else {
                offPeak = 0;
            }
            if(peak >= 0){
                peak = (costsList[2] * hourlyMultiplier) * peak * participants;
            }
            else{
                peak = 0;
            }

        }

        // adds peak/offpeak costs to total
        total = peak+offPeak;


        return total;
    }



}
