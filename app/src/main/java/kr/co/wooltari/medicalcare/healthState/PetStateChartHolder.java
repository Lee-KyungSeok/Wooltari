package kr.co.wooltari.medicalcare.healthState;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.domain.HealthStateDummy;

/**
 * Created by Kyung on 2017-12-04.
 */

public class PetStateChartHolder extends RecyclerView.ViewHolder {

    private LineChart chartPetState;
    private TextView textStateLastDate;

    public PetStateChartHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    private void initView(View itemView){
        chartPetState = itemView.findViewById(R.id.chartPetState);
        textStateLastDate = itemView.findViewById(R.id.textStateLastDate);
    }

    public void setChart(List<HealthStateDummy.petWeight> dataList, double goalWeight){
        int start=0;
        if(dataList.size()>20) start = dataList.size()-20;

        List<Entry> entryList = new ArrayList<>();
        List<Entry> goals = new ArrayList<>();
        for(int i=start ; i<dataList.size() ; i++){
            entryList.add(new Entry(i, (float)dataList.get(i).petWeight));
            goals.add(new Entry(i, (float)goalWeight));
        }

        LineDataSet weightDataSet = new LineDataSet(entryList, "Weight");
        weightDataSet.setColor(Color.BLUE);
        LineDataSet goalDataSet = new LineDataSet(goals, "Goal");
        goalDataSet.setColor(Color.RED);
        goalDataSet.setValueTextSize(0);
        goalDataSet.setDrawCircles(false);

        XAxis xAxis = chartPetState.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter((value, axis) -> {
            String x = dataList.get((int)value).inputDate;
            return x;
        });
        Description description = new Description();
        description.setText("");
        chartPetState.setDescription(description);

        LineData lineData = new LineData(weightDataSet, goalDataSet);
        chartPetState.setData(lineData);
        chartPetState.invalidate();
    }

    public void setTextStateLastDate(String date){
        textStateLastDate.setText(date);
    }
}
