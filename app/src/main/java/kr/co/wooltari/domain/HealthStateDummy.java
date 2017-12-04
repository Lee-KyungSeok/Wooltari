package kr.co.wooltari.domain;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by Kyung on 2017-12-04.
 */

public class HealthStateDummy {
    public static List<StateDummy> stateData;

    static {
        stateData = new ArrayList<>();
        for(int i=0 ; i<8 ; i++){
            stateData.add(new StateDummy(i));
        }
    }

    public static class StateDummy{
        public int pPk;
        public List<petWeight> petWeightList = new ArrayList<>();
        public double petTargetWeight;
        public double petHeight;
        public double petNeckSize;
        public double petChestSize;

        public StateDummy(int pPk){
            this.pPk = pPk;
            petTargetWeight = (pPk+1) * 1.5;
            petHeight = (pPk+1) * 1.5;
            petNeckSize = (pPk+1) * 1.1;
            petChestSize = (pPk+1) * 1.1;

            Random random = new Random();
            int max = (pPk+2) * 3;
            for(int i=1 ; i<=max ; i++){
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE,pPk*i);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String weightDateTime = sdf.format(calendar.getTime());
                double weightResult = Math.floor(100*((double)(pPk+1) + random.nextDouble()*((double)(pPk+1)) - (((double)pPk+1)*random.nextDouble())*0.3))/100;
                Log.e("weight check!!",pPk+" : weightDateTime = "+weightDateTime + " / weightResult = "+weightResult);
                petWeightList.add(new petWeight(weightDateTime,weightResult));
            }
            Log.e("size","====================="+petWeightList.size());
        }
    }

    public static class petWeight{
        String inputDate;
        double petWeight;

        public petWeight(String inputDate, double petWeight){
            this.inputDate = inputDate;
            this.petWeight = petWeight;
        }
    }
}
