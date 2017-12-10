package kr.co.wooltari.pet.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
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

    public PetDetailState(Activity activity, HealthStateDummy.StateDummy stateInfo, ICallbackPetState callback){
        this.activity = activity;

        initView();
        if(stateInfo!=null) setValue(stateInfo);

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

    public void setChart(List<HealthStateDummy.petWeight> dataList, double goalWeight){
        int start=0;
        if(dataList.size()>5) start = dataList.size()-5;
        List<Entry> entryList = new ArrayList<>();
        List<Entry> goals = new ArrayList<>();
        for(int i=start ; i<dataList.size() ; i++){
            entryList.add(new Entry(Float.parseFloat(dataList.get(i).inputDate.replace("-","")), (float)dataList.get(i).petWeight));
            goals.add(new Entry(Float.parseFloat(dataList.get(i).inputDate.replace("-","")), (float)goalWeight));
        }

        LineDataSet weightDataSet = new LineDataSet(entryList, "Weight");
        weightDataSet.setColor(Color.BLUE);
        LineDataSet goalDataSet = new LineDataSet(goals, "Goal");
        goalDataSet.setColor(Color.RED);

        LineData lineData = new LineData(weightDataSet, goalDataSet);
        chartPDSState.setData(lineData);
        chartPDSState.invalidate();
    }

    public interface ICallbackPetState{
        void goPetState();
    }
}
