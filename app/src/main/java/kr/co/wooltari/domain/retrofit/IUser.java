package kr.co.wooltari.domain.retrofit;

import kr.co.wooltari.domain.user.PasswordForgot;
import kr.co.wooltari.domain.user.User;
import kr.co.wooltari.domain.user.UserInfo;
import kr.co.wooltari.domain.user.UserSigninData;
import kr.co.wooltari.domain.user.UserSignupFacebook;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Kyung on 2017-12-18.
 */

public interface IUser {

    @POST("auth/signup/")
    Call<UserInfo> signup(
            @Part("email") RequestBody email,
            @Part("nickname") RequestBody nickname,
            @Part("password1") RequestBody password1,
            @Part("password2") RequestBody password2,
            @Part MultipartBody.Part profile
    );

    @POST("auth/signup/")
    Call<UserInfo> signup(
            @Part("email") RequestBody email,
            @Part("nickname") RequestBody nickname,
            @Part("password1") RequestBody password1,
            @Part("password2") RequestBody password2
    );

    @POST("auth/login/")
    Call<UserInfo> signin(
            @Body User userData
    );

    @POST("auth/facebook-login/")
    Call<UserInfo> signinFacebook(
            @Body UserSignupFacebook signupFacebookData
    );

    @POST("auth/logout/")
    Call<UserInfo> signout();

    @POST("auth/reset-password/")
    Call<PasswordForgot> getNewPassword(
            @Body PasswordForgot passwordForgotData
    );

    @GET("profile/{user_pk}/")
    Call<User> getUser(
            @Path("user_pk") long userPK
    );

    @PATCH("profile/{user_pk}/")
    Call<User> updateUser(
            @Path("user_pk") long userPK,
            @Part("nickname") RequestBody nickname,
            @Part("password1") RequestBody password1,
            @Part("password2") RequestBody password2,
            @Part MultipartBody.Part profile
    );

    @PATCH("profile/{user_pk}/")
    Call<User> updateUser(
            @Path("user_pk") long userPK,
            @Part("nickname") RequestBody nickname,
            @Part("password1") RequestBody password1,
            @Part("password2") RequestBody password2
    );

    @DELETE("profile/{user_pk}/")
    Call<User> deleteUser(
            @Path("user_pk") long userPK
    );
}
