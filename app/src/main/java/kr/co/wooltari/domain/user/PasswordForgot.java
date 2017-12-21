package kr.co.wooltari.domain.user;

import com.google.gson.annotations.Expose;

/**
 * Created by Kyung on 2017-12-18.
 */

public class PasswordForgot {
    @Expose(serialize = true, deserialize = false)
    private String email;
    @Expose(serialize = false, deserialize = true)
    private String message;
    @Expose(serialize = false, deserialize = true)
    private String to_email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo_email() {
        return to_email;
    }

    public void setTo_email(String to_email) {
        this.to_email = to_email;
    }

    @Override
    public String toString() {
        return "ClassPojo [email = " + email + ", message = " + message + ", to_email = " + to_email + "]";
    }
}
