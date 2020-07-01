package com.example.final_project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private DBHelper mydb ;

    ArrayList<Integer> price;
//    ArrayList<String> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // all this is default stuff upon creating tab activity
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        ViewPager viewPager = findViewById(R.id.view_pager);
//        viewPager.setAdapter(sectionsPagerAdapter);

//        TabLayout tabs = findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);


        Intent intent2 = getIntent();
        int ourID = getIntent().getIntExtra("ourID",0);
        int itemID = getIntent().getIntExtra("itemid",0);

        Cursor rs = mydb.getDataExpensesForItem(ourID, itemID);
//        Cursor sr = mydb.getDataItem(ourID,itemID);

        if(rs.getCount()>0) {
            rs.moveToFirst();
            while (!rs.isAfterLast()) {

                int exp = rs.getInt(rs.getColumnIndex(DBHelper.EXPENSES_COLUMN_PRICE));
                price.add(exp);
                rs.moveToNext();
            }
        }


        AnyChartView anyChartView = findViewById(R.id.piechart);
        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();

        data.add(new ValueDataEntry("okaljsad", 0001));
        pie.data(data);

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("My yearly expense")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
        anyChartView.setChart(pie);


    }


}