package kr.co.wooltari.domain.user;

import com.google.gson.annotations.Expose;

/**
 * Created by Kyung on 2017-12-18.
 */

public class UserSigninData {
    @Expose
    private String email;
    @Expose
    private String password;
    @Expose
    private String device_token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    @Override
    public String toString() {
        return "ClassPojo [email = " + email + ", password = " + password + ", device_token = " + device_token + "]";
    }
}
