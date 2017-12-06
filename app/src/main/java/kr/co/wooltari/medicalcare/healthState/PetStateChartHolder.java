package kr.co.wooltari.medicalcare.healthState;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

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

    public PetStateChartHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    private void initView(View itemView){
        chartPetState = itemView.findViewById(R.id.chartPetState);
    }

    public void setChart(List<HealthStateDummy.petWeight> dataList, double goalWeight){
        List<Entry> entryList = new ArrayList<>();
        List<Entry> goals = new ArrayList<>();
        for(HealthStateDummy.petWeight data : dataList){
            Log.e("date","====================="+data.inputDate);
            Log.e("weight","====================="+data.petWeight);
            entryList.add(new Entry(Float.parseFloat(data.inputDate.replace("-","")), (float)data.petWeight));
            goals.add(new Entry(Float.parseFloat(data.inputDate.replace("-","")), (float)goalWeight));
        }

        LineDataSet weightDataSet = new LineDataSet(entryList, "Weight");
        weightDataSet.setColor(Color.BLUE);
        LineDataSet goalDataSet = new LineDataSet(goals, "Goal");
        goalDataSet.setColor(Color.RED);

        LineData lineData = new LineData(weightDataSet, goalDataSet);
        chartPetState.setData(lineData);
        chartPetState.invalidate();
    }
}
