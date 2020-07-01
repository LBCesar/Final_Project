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
import com.anychart.charts.Pie;
import com.anychart.charts.Radar;
import com.anychart.core.radar.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.MarkerType;

import java.util.ArrayList;
import java.util.List;

// report graph 3
public class DashboardActivity2 extends Activity {

    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);


        Intent intent7 = getIntent();


        AnyChartView anyChartView = findViewById(R.id.any_chart_view2);
//        anyChartView.setProgressBar(findViewById(R.id.progress_bar2));

        Radar radar = AnyChart.radar();
        radar.title("Vertical bar graph");

        radar.yScale().minimum(0d);
        radar.yScale().minimumGap(0d);
        radar.yScale().ticks().interval(50d);

        radar.xAxis().labels().padding(5d, 5d, 5d, 5d);

        radar.legend()
                .align(Align.CENTER)
                .enabled(true);

        List<DataEntry> data = new ArrayList<>();
        data.add(new CustomDataEntry("Strength", 136, 199, 43));
        data.add(new CustomDataEntry("Agility", 79, 125, 56));
        data.add(new CustomDataEntry("Stamina", 149, 173, 101));
        data.add(new CustomDataEntry("Intellect", 135, 33, 202));
        data.add(new CustomDataEntry("Spirit", 158, 64, 196));

        Set set = Set.instantiate();
        set.data(data);
        Mapping shamanData = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping warriorData = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping priestData = set.mapAs("{ x: 'x', value: 'value3' }");

        Line shamanLine = radar.line(shamanData);
        shamanLine.name("Shaman");
        shamanLine.markers()
                .enabled(true)
                .type(MarkerType.CIRCLE)
                .size(3d);

        Line warriorLine = radar.line(warriorData);
        warriorLine.name("Warrior");
        warriorLine.markers()
                .enabled(true)
                .type(MarkerType.CIRCLE)
                .size(3d);

        radar.tooltip().format("Value: {%Value}");
        anyChartView.setChart(radar);
    }


    private class CustomDataEntry extends ValueDataEntry {
        public CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }
    }


}
