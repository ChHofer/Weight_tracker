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
import java.util.Date;
import java.util.List;

import fang.weighttracker.model.Weight;
import fang.weighttracker.model.WeightLab;

public class Chart extends AppCompatActivity {
    private WeightLab weightLab = WeightLab.get(getApplication());
    private List<Weight> weights = weightLab.getWeights();

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

        List<Float> weights_value = new ArrayList<>() ;

        ArrayList<ILineDataSet> dataSets = null;

        for(Weight weight:weights){
            weights_value.add(Float.parseFloat(weight.getWeight()));
        }

        ArrayList<Entry> valueSet1 = new ArrayList<>();

        for(int i=weights_value.size()-1; i>=0; i--){
            Entry v1e1 = new Entry(weights_value.get(i), i); // Add weight value into chart
            valueSet1.add(v1e1);
        }


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
