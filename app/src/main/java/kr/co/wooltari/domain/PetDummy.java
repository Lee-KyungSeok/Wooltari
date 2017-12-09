package kr.co.wooltari.domain;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import kr.co.wooltari.domain.pet.IPet;
import kr.co.wooltari.domain.pet.Pet;
import kr.co.wooltari.domain.pet.PetError;
import kr.co.wooltari.domain.pet.PetLoader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        PetLoader.getPet(12);
    }

    public static class Dummy {
        //temp
        public String pProfile;
        public int species;
        public int breeds;
        public int pk;
        public String gender;
        public boolean is_neutering;
        public String name;
        public String identified_number;
        public boolean is_active;
        public String body_color;
        private String birth_date;

        public Dummy(int pk, String name, int num, String identified_number){
            this.pk = pk;
            this.name = name;
            this.pProfile = "https://avatars0.githubusercontent.com/u/" + num + "?v=4";
            this.identified_number = identified_number;
            if(pk %2==0){
                this.gender = "male";
                this.is_neutering = false;
                this.is_active = true;
                this.species = 1;
                if(pk%4==0) this.breeds = 2;
                else  this.breeds = 1;
            } else {
                this.gender = "female";
                this.is_neutering = true;
                this.is_active = false;
                this.species = 2;
                this.breeds = 4;
            }
            switch (pk){
                case 0: this.body_color = "colorBurgundy"; break;
                case 1: this.body_color = "colorPink"; break;
                case 2: this.body_color = "colorBeige"; break;
                case 3: this.body_color = "colorDarkBlue"; break;
                case 4: this.body_color = "colorOrangeMuffler"; break;
                case 5: this.body_color = "colorDarkGreen"; break;
                case 6: this.body_color = "colorGoldGreen"; break;
                case 7: this.body_color = "colorBlueOfSea"; break;
            }

            Random random = new Random();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            calendar.add(Calendar.DATE, (-1)*random.nextInt(2500)-300);
            birth_date = sdf.format(calendar.getTime());
            Log.e("date","=================================="+birth_date);
        }
    }
}
