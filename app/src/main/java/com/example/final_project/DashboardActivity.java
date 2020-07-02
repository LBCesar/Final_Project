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

// report graph 1
public class DashboardActivity extends AppCompatActivity {
    DBHelper mydb;
    ArrayList<String> alldates;
    ArrayList<Integer> myExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        int ourID;

        mydb = new DBHelper(this);
        Intent intent = getIntent();

        ourID = intent.getIntExtra("ourID",0);
        alldates = intent.getStringArrayListExtra("ai");
        myExp = intent.getIntegerArrayListExtra("tsfi");

        Toast.makeText(getApplicationContext(), "Nothing"+ ourID,
                Toast.LENGTH_SHORT).show();

        ArrayList<String> a=mydb.getAllItemsName(ourID);
        ArrayList<String> b=mydb.getAllExpenses(ourID);

//        if(a.size()>0) {
//            Toast.makeText(getApplicationContext(), "======" + a.get(0),
//                    Toast.LENGTH_SHORT).show();
//            if(b.size()>0) {
//                Toast.makeText(getApplicationContext(), "=======" + b.get(0),
//                        Toast.LENGTH_SHORT).show();
//            }
//        }

        AnyChartView anyChartView = findViewById(R.id.piechart);
        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
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


