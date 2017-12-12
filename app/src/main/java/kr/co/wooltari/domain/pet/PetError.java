package kr.co.wooltari.domain.pet;

import com.google.gson.annotations.Expose;

/**
 * Created by Kyung on 2017-12-09.
 */

public class PetError {
    @Expose(serialize = false, deserialize = true)
    private String detail;
    @Expose(serialize = false, deserialize = true)
    private String[] body_color;
    @Expose(serialize = false, deserialize = true)
    private String[] species;
    @Expose(serialize = false, deserialize = true)
    private String[] birth_date;
    @Expose(serialize = false, deserialize = true)
    private String[] name;
    @Expose(serialize = false, deserialize = true)
    private String[] gender;
    @Expose(serialize = false, deserialize = true)
    private String[] breeds;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String[] getBody_color() {
        return body_color;
    }

    public void setBody_color(String[] body_color) {
        this.body_color = body_color;
    }

    public String[] getSpecies() {
        return species;
    }

    public void setSpecies(String[] species) {
        this.species = species;
    }

    public String[] getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String[] birth_date) {
        this.birth_date = birth_date;
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String[] getGender() {
        return gender;
    }

    public void setGender(String[] gender) {
        this.gender = gender;
    }

    public String[] getBreeds() {
        return breeds;
    }

    public void setBreeds(String[] breeds) {
        this.breeds = breeds;
    }


    @Override
    public String toString() {
        return "ClassPojo [detail = " + detail + "]";
    }

    /*
    @Override
    public String toString() {
        return "ClassPojo [body_color = " + body_color + ", species = " + species + ", birth_date = " + birth_date + ", name = " + name + ", gender = " + gender + ", breeds = " + breeds + "]";
    }
    */
}
