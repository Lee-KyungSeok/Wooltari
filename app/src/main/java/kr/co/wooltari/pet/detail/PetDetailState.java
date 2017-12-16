package kr.co.wooltari.pet.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.domain.HealthStateDummy;
import kr.co.wooltari.medicalcare.healthState.PetStateActivity;

/**
 * Created by Kyung on 2017-12-07.
 */

public class PetDetailState {

    private TextView PDSMore;
    private TextView textInputPDSWeight;
    private TextView textInputPDSHeight;
    private TextView textInputPDSNeckSize;
    private TextView textInputPDSChestSize;
    private LineChart chartPDSState;

    Activity activity;
    HealthStateDummy.StateDummy stateInfo;

    public PetDetailState(Activity activity, ICallbackPetState callback){
        this.activity = activity;

        initView();
//        if(stateInfo!=null) setValue(stateInfo);

        PDSMore.setOnClickListener(v -> callback.goPetState());
    }

    private void initView(){
        PDSMore = activity.findViewById(R.id.PDSMore);
        textInputPDSWeight = activity.findViewById(R.id.textInputPDSWeight);
        textInputPDSHeight = activity.findViewById(R.id.textInputPDSHeight);
        textInputPDSNeckSize = activity.findViewById(R.id.textInputPDSNeckSize);
        textInputPDSChestSize = activity.findViewById(R.id.textInputPDSChestSize);
        chartPDSState = activity.findViewById(R.id.chartPDSState);
    }

    public void setValue(HealthStateDummy.StateDummy stateInfo){
        this.stateInfo = stateInfo;
        textInputPDSWeight.setText(stateInfo.petNowWeight+" kg");
        textInputPDSHeight.setText(stateInfo.petHeight+" cm");
        textInputPDSNeckSize.setText(stateInfo.petNeckSize+" cm");
        textInputPDSChestSize.setText(stateInfo.petChestSize+" cm");
        setChart(stateInfo.petWeightList,stateInfo.petTargetWeight);
    }

    private void setChart(List<HealthStateDummy.petWeight> dataList, double goalWeight){
        int start=0;
        if(dataList.size()>10) start = dataList.size()-10;
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

        XAxis xAxis = chartPDSState.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter((value, axis) -> {
            String x = dataList.get((int)value).inputDate;
            return x;
        });

        Description description = new Description();
        description.setText("");
        chartPDSState.setDescription(description);

        LineData lineData = new LineData(weightDataSet, goalDataSet);
        chartPDSState.setData(lineData);
        chartPDSState.invalidate();
    }

    public interface ICallbackPetState{
        void goPetState();
    }
}
