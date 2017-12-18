package kr.co.wooltari.domain.user;

import com.google.gson.annotations.Expose;

/**
 * Created by Kyung on 2017-12-18.
 */

public class UserError {
    @Expose
    private String[] password1;
    @Expose
    private String[] password2;
    @Expose
    private String[] nickname;
    @Expose
    private String[] email;
    @Expose
    private String[] non_field_errors;
    @Expose
    private String detail;

    public String[] getPassword1() {
        return password1;
    }

    public void setPassword1(String[] password1) {
        this.password1 = password1;
    }

    public String[] getPassword2() {
        return password2;
    }

    public void setPassword2(String[] password2) {
        this.password2 = password2;
    }

    public String[] getNickname() {
        return nickname;
    }

    public void setNickname(String[] nickname) {
        this.nickname = nickname;
    }

    public String[] getEmail() {
        return email;
    }

    public void setEmail(String[] email) {
        this.email = email;
    }

    public String[] getNon_field_errors() {
        return non_field_errors;
    }

    public void setNon_field_errors(String[] non_field_errors) {
        this.non_field_errors = non_field_errors;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "ClassPojo [password1 = " + password1 + ", password2 = " + password2 + ", nickname = " + nickname + ", email = " + email + ", non_field_errors = " + non_field_errors + "]";
    }
}
