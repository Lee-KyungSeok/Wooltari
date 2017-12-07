package kr.co.wooltari.domain;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Kyung on 2017-12-07.
 */

public class MedicalInfoDummy {

    public static List<MedicalDummy> data;

    static {
        data = new ArrayList<>();
        for(int i=0; i<8; i++){
            data.add(new MedicalDummy(i));
        }
    }

    public static class MedicalDummy {
        public int pPK;
        public List<petMediInfo> petMediInfoList = new ArrayList<>();

        public MedicalDummy(int pPK){
            this.pPK = pPK;
            for(int i=0 ; i<pPK*2 ; i++){
                String imageUrl = null;
                String medicalDate;
                String description;
                String comment;

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(i!=0) {
                    calendar.add(Calendar.DATE, pPK*i-1);
                    medicalDate = sdf.format(calendar.getTime());
                } else {
                    calendar.add(Calendar.DATE, -20);
                    medicalDate = sdf.format(calendar.getTime());
                }

                description = "ajnfnjandskjkvkahfenrjnjkvnv115648as";
                comment = i+" akdjfkjjandfjbhasfdsdfjk156156164616516848948465165168484651bsdjnfief";

                if(i==pPK*2-1) {
                    imageUrl = "https://avatars0.githubusercontent.com/u/2" + pPK + "?v=4";
                    description = "Neutralization ~~";
                    comment = "Success!! Good!!\n My Pet is very Cute~~ \n i want to take walk with my pet!!";
                }
                petMediInfoList.add(new petMediInfo(pPK, imageUrl, medicalDate, description, comment));
            }
        }
    }

    public static class petMediInfo{
        public int medicalPk;
        public String imageUrl;
        public String medicalDate;
        public String description;
        public String comment;

        public petMediInfo(int pPk, String imageUrl, String medicalDate, String description, String comment){
            medicalPk = 10+pPk;
            this.imageUrl = imageUrl;
            this.medicalDate = medicalDate;
            this.description =description;
            this.comment = comment;
        }
    }
}
