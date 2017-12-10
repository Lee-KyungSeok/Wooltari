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

    public static void createStateDummy(int petPK){
        StateDummy stateDummy = new StateDummy(petPK);
        stateData.add(stateDummy);
    }

    public static class StateDummy{
        public int petPk;
        public List<petWeight> petWeightList = new ArrayList<>();
        public double petNowWeight;
        public double petTargetWeight;
        public double petHeight;
        public double petNeckSize;
        public double petChestSize;

        public StateDummy(int petPk){
            this.petPk = petPk;
            petTargetWeight = Math.floor((petPk +1) * 150)/100;
            petHeight = Math.floor((petPk +1) * 150)/100;
            petNeckSize = Math.floor((petPk +1) * 110)/100;
            petChestSize = Math.floor((petPk +1) * 110)/100;

            Random random = new Random();
            int max = (petPk +2) * 3;
            for(int i=1 ; i<=max ; i++){
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, petPk *i);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String weightDateTime = sdf.format(calendar.getTime());

                double weightResult = Math.floor(100*((double)(petPk +1) + random.nextDouble()*((double)(petPk +1)) - (((double) petPk +1)*random.nextDouble())*0.3))/100;
//                Log.e("weight check!!",petPk+" : weightDateTime = "+weightDateTime + " / weightResult = "+weightResult);

                petWeightList.add(new petWeight(weightDateTime,weightResult));
                if(i==max) petNowWeight = weightResult;
            }
//            Log.e("size","====================="+petWeightList.size());
        }
    }

    public static class petWeight{
        public String inputDate;
        public double petWeight;

        public petWeight(String inputDate, double petWeight){
            this.inputDate = inputDate;
            this.petWeight = petWeight;
        }
    }
}
