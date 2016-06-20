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
        chart.setDescription("Weight Chart for recent 7 records");
        chart.animateXY(500, 500);
        chart.invalidate();


    }
    private ArrayList<ILineDataSet> getDataSet() {

        List<Float> weights_value = new ArrayList<>() ;

        ArrayList<ILineDataSet> dataSets = null;

        for(Weight weight:weights){
            weights_value.add(Float.parseFloat(weight.getWeight()));
        }

        if(weights_value.size() > 7){
            int m = weights_value.size() - 7;
            for(int n=(weights_value.size()-1),j=0; j<m; n--,j++) {
                weights_value.remove(n);
            }
        }

        ArrayList<Entry> valueSet1 = new ArrayList<>();

        for(int i=(weights_value.size()-1),j=0; i>=Math.max(0, weights_value.size()-7); i--,j++){
            Entry v1e1 = new Entry(weights_value.get(i),j); // Add weight value into chart
            valueSet1.add(v1e1);
        }
/**
        if(weights.size() < 7){
            for(int i=0, j=0; i<=7; i++,j++){
                Entry v1e1 = new Entry(weights_value.get(i),j); // Add weight value into chart
                valueSet1.add(v1e1);
            }
        }else{

            for(int i=weights_value.size()-1, j=0; i>=weights_value.size()-7; i--,j++) {

                Entry v1e1 = new Entry(weights_value.get(i), j); // Add weight value into chart
                valueSet1.add(v1e1);
            }
        }
*/

        LineDataSet LineDataSet = new LineDataSet(valueSet1, "My Weights");
        LineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(LineDataSet);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();


        xAxis.add("1");
        xAxis.add("2");
        xAxis.add("3");
        xAxis.add("4");
        xAxis.add("5");
        xAxis.add("6");
        xAxis.add("7");
        return xAxis;
    }

}
