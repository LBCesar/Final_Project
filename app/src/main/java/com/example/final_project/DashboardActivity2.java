package com.example.final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.charts.Radar;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// report graph 3
public class DashboardActivity2 extends Activity {

    DBHelper mydb;
    int ourID=0;
    ArrayList<Integer> myExp;
    ArrayList<Integer> mySave;


    ArrayList<String> alldates=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);

        Intent intent = getIntent();
        ourID=intent.getIntExtra("ourID",0);
        alldates=intent.getStringArrayListExtra("ad");
        myExp=intent.getIntegerArrayListExtra("me");
        mySave=intent.getIntegerArrayListExtra("ms");

        if(alldates.get(0)!=null) {
            Toast.makeText(getApplicationContext(), "TEST:" + alldates.get(0),
                    Toast.LENGTH_SHORT).show();
        }

        AnyChartView anyChartView = findViewById(R.id.any_chart_view2);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar1));

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO yorke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Daily Savings");

        cartesian.yAxis(0).title("Number of Bottles Buy");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        for (int i = 0; i < alldates.size(); i++) {
            if(alldates.get(i)!=null) {
                seriesData.add(new DashboardActivity2.CustomDataEntry(alldates.get(i), mySave.get(i), 2.3, 2.8));
                seriesData.add(new DashboardActivity2.CustomDataEntry("2020-07-02", 1200, 2.3, 2.8));

            }
        }
//        seriesData.add(new CustomDataEntry("1986", 3.6, 2.3, 2.8));
//        seriesData.add(new CustomDataEntry("1987", 7.1, 4.0, 4.1));
//        seriesData.add(new CustomDataEntry("1988", 8.5, 6.2, 5.1));
//        seriesData.add(new CustomDataEntry("1989", 9.2, 11.8, 6.5));
//        seriesData.add(new CustomDataEntry("1990", 10.1, 13.0, 12.5));
//        seriesData.add(new CustomDataEntry("1991", 11.6, 13.9, 18.0));
//        seriesData.add(new CustomDataEntry("1992", 16.4, 18.0, 21.0));
//        seriesData.add(new CustomDataEntry("1993", 18.0, 23.3, 20.3));
//        seriesData.add(new CustomDataEntry("1994", 13.2, 24.7, 19.2));

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        com.anychart.core.cartesian.series.Line series1 = cartesian.line(series1Mapping);
        series1.name("Brandy");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Whiskey");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

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




























//
//        Intent intent = getIntent();
//        ourID=intent.getIntExtra("ourID",0);
//        alldates=intent.getStringArrayListExtra("ad");
//        if(alldates.get(0)!=null) {
//            Toast.makeText(getApplicationContext(), "TEST:" + alldates.get(0),
//                    Toast.LENGTH_SHORT).show();
//        }
////       // ArrayList<String> a=mydb.getAllItemsName(ourID);
////        //ArrayList<String> b=mydb.getAllExpenses(ourID);
////
////                alldates=mydb.getAllDates(ourID);
////
////        java.util.Set<String> set2 = new HashSet<>(alldates);
////        alldates.clear();
////        alldates.addAll(set2);
////        Toast.makeText(getApplicationContext(), "======" ,
////                Toast.LENGTH_SHORT).show();
////        if(alldates.size()>0) {
////            Toast.makeText(getApplicationContext(), "======" + alldates.get(0),
////                    Toast.LENGTH_SHORT).show();
////        }
//
//
//
//        AnyChartView anyChartView = findViewById(R.id.any_chart_view2);
////        anyChartView.setProgressBar(findViewById(R.id.progress_bar2));
//
//        Radar radar = AnyChart.radar();
//        radar.title("Vertical bar graph");
//
//        radar.yScale().minimum(0d);
//        radar.yScale().minimumGap(0d);
//        radar.yScale().ticks().interval(50d);
//
//        radar.xAxis().labels().padding(5d, 5d, 5d, 5d);
//
//        radar.legend()
//                .align(Align.CENTER)
//                .enabled(true);
//
//        List<DataEntry> data = new ArrayList<>();
//        data.add(new CustomDataEntry("Strength", 136, 199, 43));
//        data.add(new CustomDataEntry("Agility", 79, 125, 56));
//        data.add(new CustomDataEntry("Stamina", 149, 173, 101));
//        data.add(new CustomDataEntry("Intellect", 135, 33, 202));
//        data.add(new CustomDataEntry("Spirit", 158, 64, 196));
//
//        Set set = Set.instantiate();
//        set.data(data);
//        Mapping shamanData = set.mapAs("{ x: 'x', value: 'value' }");
//        Mapping warriorData = set.mapAs("{ x: 'x', value: 'value2' }");
//        Mapping priestData = set.mapAs("{ x: 'x', value: 'value3' }");
//
//        Line shamanLine = radar.line(shamanData);
//        shamanLine.name("Shaman");
//        shamanLine.markers()
//                .enabled(true)
//                .type(MarkerType.CIRCLE)
//                .size(3d);
//
//        Line warriorLine = radar.line(warriorData);
//        warriorLine.name("Warrior");
//        warriorLine.markers()
//                .enabled(true)
//                .type(MarkerType.CIRCLE)
//                .size(3d);
//
//        radar.tooltip().format("Value: {%Value}");
//        anyChartView.setChart(radar);
//    }
//
//
//    private class CustomDataEntry extends ValueDataEntry {
//        public CustomDataEntry(String x, Number value, Number value2, Number value3) {
//            super(x, value);
//            setValue("value2", value2);
//            setValue("value3", value3);
//        }
//    }
//
//
//}
