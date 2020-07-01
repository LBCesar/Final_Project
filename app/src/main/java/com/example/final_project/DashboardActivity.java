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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.final_project.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        int ourID;
        // all this is default stuff upon creating tab activity
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        ViewPager viewPager = findViewById(R.id.piechart);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);
        mydb = new DBHelper(this);
 //       Cursor rs = mydb.getDataExpensesForItem(ourID, itemID);
//        Cursor sr = mydb.getDataItem(ourID,itemID);

        Intent intent = getIntent();
        ourID=intent.getIntExtra("ourID",0);
        Toast.makeText(getApplicationContext(), "Nothing"+ ourID,
                Toast.LENGTH_SHORT).show();
        ArrayList<String> a=mydb.getAllItemsName(ourID);
        ArrayList<String> b=mydb.getAllExpenses(ourID);

        if(a.size()>0) {
            Toast.makeText(getApplicationContext(), "======" + a.get(0),
                    Toast.LENGTH_SHORT).show();
            if(b.size()>0) {
                Toast.makeText(getApplicationContext(), "=======" + b.get(0),
                        Toast.LENGTH_SHORT).show();
            }
        }


/*
        if(rs.getCount()>0) {
            rs.moveToFirst();
            while (!rs.isAfterLast()) {

                int exp = rs.getInt(rs.getColumnIndex(DBHelper.EXPENSES_COLUMN_PRICE));
                price.add(exp);
                rs.moveToNext();
            }
        }
*/
//
//        AnyChartView anyChartView = findViewById(R.id.piechart);
//        Pie pie = AnyChart.pie();
//
//        List<DataEntry> data = new ArrayList<>();
//
//        data.add(new ValueDataEntry("okaljsad", 0001));
//        pie.data(data);
//
//        pie.labels().position("outside");
//
//        pie.legend().title().enabled(true);
//        pie.legend().title()
//                .text("My yearly expense")
//                .padding(0d, 0d, 10d, 0d);
//
//        pie.legend()
//                .position("center-bottom")
//                .itemsLayout(LegendLayout.HORIZONTAL)
//                .align(Align.CENTER);
//        anyChartView.setChart(pie);


    }


}


