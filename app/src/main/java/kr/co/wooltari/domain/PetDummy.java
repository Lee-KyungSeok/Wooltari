package kr.co.wooltari.domain;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import kr.co.wooltari.domain.pet.Pet;
import kr.co.wooltari.domain.pet.PetDataManager;

/**
 * Created by Kyung on 2017-11-28.
 */

public class PetDummy {
    public static List<Pet> data;

    static {
        Random random = new Random();
        data = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            data.add(createDummy(i, "MyPet " + i, i, random.nextInt() + ""));
        }
//        PetDataManager.getPet(12);
    }

    public static Pet createDummy(int pk, String name, int num, String identified_number) {
        String species;
        String breeds;
        String gender;
        boolean is_neutering;
        boolean is_active;
        String body_color = null;
        String birth_date;

        if (pk % 2 == 0) {
            gender = "male";
            is_neutering = false;
            is_active = true;
            species = "dog";
            if (pk % 4 == 0) breeds = "Golden Retriever";
            else breeds = "Shih Tzu";
        } else {
            gender = "female";
            is_neutering = true;
            is_active = false;
            species = "cat";
            breeds = "Korean Short Hair";
        }

        switch (pk) {
            case 0: body_color = "colorBurgundy"; break;
            case 1: body_color = "colorPink"; break;
            case 2: body_color = "colorBeige"; break;
            case 3: body_color = "colorDarkBlue"; break;
            case 4:body_color = "colorOrangeMuffler";break;
            case 5:body_color = "colorDarkGreen"; break;
            case 6: body_color = "colorGoldGreen"; break;
            case 7: body_color = "colorBlueOfSea"; break;
        }

        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DATE, (-1) * random.nextInt(2500) - 300);
        birth_date = sdf.format(calendar.getTime());

        Pet pet = new Pet();
        pet.setPk(pk);
        pet.setName(name);
        pet.setProfileUrl("https://avatars0.githubusercontent.com/u/" + num + "?v=4");
        pet.setIdentified_number(identified_number);
        pet.setGender(gender);
        pet.setIs_neutering(is_neutering);
        pet.setIs_active(is_active);
        pet.setSpecies(species);
        pet.setBreeds(breeds);
        pet.setBody_color(body_color);
        pet.setBirth_date(birth_date);

        return pet;
    }
}
