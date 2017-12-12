package kr.co.wooltari.domain.pet;

import com.google.gson.annotations.Expose;

/**
 * Created by Kyung on 2017-12-09.
 */

public class Age {
    @Expose
    private String conversed_age;
    @Expose
    private String pet_age;

    public String getConversed_age() {
        return conversed_age;
    }

    public void setConversed_age(String conversed_age) {
        this.conversed_age = conversed_age;
    }

    public String getPet_age() {
        return pet_age;
    }

    public void setPet_age(String pet_age) {
        this.pet_age = pet_age;
    }

    @Override
    public String toString() {
        return "ClassPojo [conversed_age = " + conversed_age + ", pet_age = " + pet_age + "]";
    }
}

