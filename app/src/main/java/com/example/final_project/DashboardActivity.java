/*
    Cesar Gonzalez
    Shoraj Manandhar
    App: eTRACK
    Final Project: Expense Tracker
    2nd July, 2020
 */

package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import com.example.final_project.ui.main.SectionsPagerAdapter;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.List;

/*  Report 1 - Pie Chart
    An activity that display a report with pie chart. The chart shows daily expense
    based on each item category (such as Grocery, Book, Online...). In order to do such,
    Anychart is implemented.

    GitHub: Anychart - https://github.com/AnyChart/AnyChart-Android
 */
public class DashboardActivity extends AppCompatActivity {

    // Initialize all required variables
    DBHelper mydb;
    ArrayList<String> alldates;
    ArrayList<Integer> myExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // slides to DBMainActivity: https://github.com/r0adkll/Slidr
        Slidr.attach(this);

        int ourID;
        mydb = new DBHelper(this);
        Intent intent = getIntent();

         // get the item names and expense as an intent, this is obtain
         // from the database
        ourID = intent.getIntExtra("ourID",0);
        alldates = intent.getStringArrayListExtra("ai");
        myExp = intent.getIntegerArrayListExtra("tsfi");

//        ArrayList<String> a = mydb.getAllItemsName(ourID);
//        ArrayList<String> b = mydb.getAllExpenses(ourID);

        // Using Anychart do display in a pie graph
        AnyChartView anyChartView = findViewById(R.id.piechart);
        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();

        // using a for-loop to put the appropriate values in the arraylist
        for(int i=0; i<alldates.size(); i++){
            data.add(new ValueDataEntry(alldates.get(i), myExp.get(i)));
        }

        pie.data(data);
        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Daily Expense Based On Category")
                .padding(5d, 0d, 5d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        pie.animation(true);
        pie.fill("aquastyle");
        pie.background("black");
        pie.outline(true);
        anyChartView.setChart(pie);

    }

}


