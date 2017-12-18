package kr.co.wooltari.domain.user;

import com.google.gson.annotations.Expose;

public class UserInfo {
    @Expose
    private String token;
    @Expose
    private User user;
    @Expose
    private String message;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ClassPojo [token = " + token + ", user = " + user + ", message = " + message + "]";
    }
}
