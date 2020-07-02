package com.example.final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.MarkerType;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/*  Report 2 - Bar Chart
    An activity that display a report with Bar graph. The graph contains total expense the user
    makes each day. Expense ($) per Day (date, YYYY-MM-DD format). In order to show such, a
    Bar chart from Anychart is implemented.

    GitHub: Anychart - https://github.com/AnyChart/AnyChart-Android
 */
public class DashboardActivity1 extends Activity {

    // Initialize all required variables
    private DBHelper mydb;
    int ourID = 0;

    ArrayList<String> alldates;
    ArrayList<Integer> myExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard1);

        // slides to DBMainActivity: https://github.com/r0adkll/Slidr
        Slidr.attach(this);

        // get the date of purchase and expense as an intent, this is obtain
        // from the database
        Intent intent = getIntent();
        ourID = intent.getIntExtra("ourID",0);
        alldates = intent.getStringArrayListExtra("ad");
        myExp = intent.getIntegerArrayListExtra("me");

        // Using Anychart do display in a Bar Graph
        AnyChartView anyChartView = findViewById(R.id.any_chart_view1);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar1));

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();

        // Two Arraylists, one contains dates (each day) & expense($)
        for (int i = 0; i < alldates.size(); i++) {
            if(alldates.get(i)!=null) {
                data.add(new ValueDataEntry(alldates.get(i), myExp.get(i)));
            }
        }

        // this is a dummy data
        data.add(new ValueDataEntry("2020-02-29", 999));
        data.add(new ValueDataEntry("2020-03-22", 654));

        Column column;
        column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("${%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.background("#1B1B1B");
        cartesian.textMarker("white");

        cartesian.xMinorGrid(true);
        cartesian.yMinorGrid(true);

        cartesian.title("Total Spending per Day");

        cartesian.yScale().minimum(0d);
        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Date (YYYY-MM-DD)");
        cartesian.yAxis(0).title("Expense (per day))");

        anyChartView.setChart(cartesian);

    }

    private static class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}
