package kr.co.wooltari.domain.pet;

import com.google.gson.annotations.Expose;

/**
 * Created by Kyung on 2017-12-13.
 */

public class ActivePet {
    @Expose(serialize = true, deserialize = false)
    private String is_active;

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

}
