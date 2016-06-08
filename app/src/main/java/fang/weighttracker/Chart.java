package fang.weighttracker;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Chart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        LineChart chart = (LineChart) findViewById(R.id.chart_image);

        LineData data = new LineData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("Weight Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();

    }
    private ArrayList<ILineDataSet> getDataSet() {
        ArrayList<ILineDataSet> dataSets = null;

        ArrayList<Entry> valueSet1 = new ArrayList<>();

        Entry v1e1 = new Entry(190.000f, 0); // Jan
        valueSet1.add(v1e1);
        Entry v1e2 = new Entry(180.000f, 1); // Feb
        valueSet1.add(v1e2);
        Entry v1e3 = new Entry(200.000f, 2); // Mar
        valueSet1.add(v1e3);
        Entry v1e4 = new Entry(170.000f, 3); // Apr
        valueSet1.add(v1e4);
        Entry v1e5 = new Entry(190.000f, 4); // May
        valueSet1.add(v1e5);
        Entry v1e6 = new Entry(180.000f, 5); // Jun
        valueSet1.add(v1e6);


        LineDataSet LineDataSet = new LineDataSet(valueSet1, "My Weights");
        LineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(LineDataSet);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("SUN");
        xAxis.add("MON");
        xAxis.add("TUE");
        xAxis.add("WED");
        xAxis.add("THU");
        xAxis.add("FRI");
        xAxis.add("SAT");
        return xAxis;
    }

}
