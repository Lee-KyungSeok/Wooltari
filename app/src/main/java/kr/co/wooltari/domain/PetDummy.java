package kr.co.wooltari.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Kyung on 2017-11-28.
 */

public class PetDummy {
    public static List<Dummy> data;

    static {
        Random random = new Random();
        data = new ArrayList<>();
        for(int i=0; i<8; i++){
            data.add(new Dummy(i,"MyPet " + i, i,random.nextInt()+""));
        }
    }

    public static class Dummy {
        public int pPK;
        public String sex;
        public String neuter;
        public String pName;
        public String pProfile;
        public String petNumber;
        public boolean state;
        public String color;

        public Dummy(int pPK, String pName, int num, String petNumber){
            this.pPK = pPK;
            this.pName = pName;
            this.pProfile = "https://avatars0.githubusercontent.com/u/" + num + "?v=4";
            this.petNumber = petNumber;
            if(pPK%2==0){
                this.sex = "M";
                this.neuter = "N";
                this.state = true;
            } else {
                this.sex = "F";
                this.neuter = "Y";
                this.state = false;
            }
            switch (pPK){
                case 0: this.color = "colorBurgundy"; break;
                case 1: this.color = "colorPink"; break;
                case 2: this.color = "colorBeige"; break;
                case 3: this.color = "colorDarkBlue"; break;
                case 4: this.color = "colorOrangeMuffler"; break;
                case 5: this.color = "colorDarkGreen"; break;
                case 6: this.color = "colorGoldGreen"; break;
                case 7: this.color = "colorBlueOfSea"; break;
            }
        }
    }
}
