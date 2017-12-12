package kr.co.wooltari.domain.pet;

import com.google.gson.annotations.Expose;

/**
 * Created by Kyung on 2017-12-08.
 */

public class Owner {
    @Expose
    private String pk;
    @Expose
    private String email;
    @Expose
    private String nickname;
    @Expose
    private String user_type;
    @Expose
    private String is_active;
    @Expose
    private String date_joined;

    public String getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @Override
    public String toString() {
        return "ClassPojo [date_joined = " + date_joined + ", is_active = " + is_active + ", nickname = " + nickname + ", email = " + email + ", user_type = " + user_type + ", pk = " + pk + "]";
    }
}
