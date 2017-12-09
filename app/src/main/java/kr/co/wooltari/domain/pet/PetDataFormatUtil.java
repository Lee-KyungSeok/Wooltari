package kr.co.wooltari.domain.pet;

import android.content.Context;

import kr.co.wooltari.R;

/**
 * Created by Kyung on 2017-12-08.
 */

public class PetDataFormatUtil {

    public static String SpeciesNameById(Context context, int speciesId){
//        String SpeciesArray[] = context.getResources().getStringArray(R.array.pet_species_item);
        switch (speciesId){
            case 1: return "Dog";
            case 2: return "Cat";
        }
        return null;
    }

    public static int SpeciesIdByName(Context context, String speciesName) {
        switch (speciesName){
            case "Dog": return 1;
            case "Cat": return 2;
        }
        return 0;
    }

    public static String BreedsNameById(Context context, int breedsId){
//        String SpeciesArray[] = context.getResources().getStringArray(R.array.pet_species_item);
        switch (breedsId){
            case 1: return "Shih Tzu";
            case 2: return "Golden Retriever";
            case 3: return "Korean Shorthair";
            case 4: return "Persian";
        }
        return null;
    }

    public static int BreedsIdByName(Context context, String breedsName) {
        switch (breedsName){
            case "Shih Tzu": return 1;
            case "Golden Retriever": return 2;
            case "Korean Shorthair": return 3;
            case "Persian": return 4;
        }
        return 0;
    }
}
