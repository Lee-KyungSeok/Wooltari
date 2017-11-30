package kr.co.wooltari.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyung on 2017-11-28.
 */

public class PetDummy {
    public static List<Dummy> data;

    static {
        data = new ArrayList<>();
        for(int i=0; i<8; i++){
            data.add(new Dummy(i,"MyPet " + i, i));
        }
    }

    public static class Dummy {
        public int pPK;
        public String pName;
        public String pProfile;

        public Dummy(int pPK, String pName, int num){
            this.pPK = pPK;
            this.pName = pName;
            this.pProfile = "https://avatars0.githubusercontent.com/u/" + num + "?v=4";
        }
    }
}
