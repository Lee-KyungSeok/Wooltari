package kr.co.wooltari.domain.pet;

import com.google.gson.annotations.Expose;

/**
 * Created by Kyung on 2017-12-12.
 */

public class PetOne {
    @Expose
    private Pet pet;
    @Expose
    private Owner owner;

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "ClassPojo [pet = " + pet + ", owner = " + owner + "]";
    }
}
