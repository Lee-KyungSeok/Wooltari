package kr.co.wooltari.domain.user;

import com.google.gson.annotations.Expose;

/**
 * Created by Kyung on 2017-12-18.
 */

public class UserPatchData {
    @Expose
    private String nickname;
    @Expose
    private String password1;
    @Expose
    private String password2;
//    @Expose
//    private File image;

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void setPassword1(String password1){
        this.password1 = password1;
    }
    public void setPassword2(String password2){
        this.password2 = password2;
    }

//    public void setImage(File image){
//        this.image = image;
//    }
}
