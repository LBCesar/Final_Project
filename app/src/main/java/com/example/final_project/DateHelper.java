package com.example.final_project;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
    Tester class
    A class to slightly manage the dates better.
 */
public class DateHelper {

    public String date() {
        java.util.Date dNow = new java.util.Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMMM dd, yyyy");
//        String output = outputFormat.format(inputFormat.parse(String.valueOf(dNow)));

        //System.out.println("Current Date: "+ft.format(dNow));
        return ft.format(dNow);
    }
    public String date2() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMMM dd, yyyy");
//        String output = outputFormat.format(inputFormat.parse(String.valueOf(dNow)));

        //System.out.println("Current Date: "+ft.format(dNow));
        return ft.format(dNow);
    }
}
