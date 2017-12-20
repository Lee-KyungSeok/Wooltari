package kr.co.wooltari.domain.user;

import com.google.gson.annotations.Expose;

/**
 * User 클래스
 *  - 1. 서버 요청에 대한 response로 받는 객체 (deserialize=true 인 것)
 *  - 2. signin에서 body로 보낼 객체 (serialize=true 인 것)
 *  - 3. signup 및 update 에서 사용할 필드를 저장하고 있는 클래스
 */
public class User {
    @Expose(serialize = false, deserialize = true)
    private String date_joined;
    @Expose(serialize = false, deserialize = true)
    private boolean is_active;
    @Expose(serialize = false, deserialize = true)
    private String nickname;
    @Expose(serialize = false, deserialize = true)
    private String user_type;
    @Expose(serialize = false, deserialize = true)
    private long pk;
    @Expose(serialize = false, deserialize = true)
    private String image;

    @Expose(serialize = true, deserialize = true)
    private String email;
    @Expose(serialize = true, deserialize = false)
    private String password;
    @Expose(serialize = true, deserialize = false)
    private String device_token;

    @Expose(serialize = false, deserialize = false)
    private String password1;
    @Expose(serialize = false, deserialize = false)
    private String password2;

    public String getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
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

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public void setPassword1(String password1){
        this.password1 = password1;
    }

    public String getPassword1(){
        return password1;
    }

    public void setPassword2(String password2){
        this.password2 = password2;
    }

    public String getPassword2(){
        return password2;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    @Override
    public String toString() {
        return "ClassPojo [date_joined = " + date_joined + ", is_active = " + is_active + ", nickname = " + nickname + ", email = " + email + ", user_type = " + user_type + ", pk = " + pk + "]";
    }
}
