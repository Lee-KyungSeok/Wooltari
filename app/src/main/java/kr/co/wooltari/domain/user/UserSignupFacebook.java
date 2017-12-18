package kr.co.wooltari.domain.user;

import com.google.gson.annotations.Expose;

/**
 * Created by Kyung on 2017-12-18.
 */

public class UserSignupFacebook {
    @Expose
    private String facebook_user_id;
    @Expose
    private String access_token;
    @Expose
    private String device_token;

    public String getFacebook_user_id() {
        return facebook_user_id;
    }

    public void setFacebook_user_id(String facebook_user_id) {
        this.facebook_user_id = facebook_user_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    @Override
    public String toString() {
        return "ClassPojo [facebook_user_id = " + facebook_user_id + ", access_token = " + access_token + "]";
    }

}
