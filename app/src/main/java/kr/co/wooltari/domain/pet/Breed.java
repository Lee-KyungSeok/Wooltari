package kr.co.wooltari.domain.pet;

import com.google.gson.annotations.Expose;

/**
 * Created by Kyung on 2017-12-12.
 */

public class Breed {
    @Expose(serialize = false, deserialize = true)
    private String breeds_name;

    @Expose(serialize = true, deserialize = false)
    private String species;

    public String getBreeds_name() {
        return breeds_name;
    }

    public void setBreeds_name(String breeds_name) {
        this.breeds_name = breeds_name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    @Override
    public String toString() {
        return "ClassPojo [breeds_name = " + breeds_name + ", species = " + species+"]";
    }
}
