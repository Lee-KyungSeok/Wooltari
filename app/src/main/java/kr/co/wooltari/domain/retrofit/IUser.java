package kr.co.wooltari.domain.retrofit;

import kr.co.wooltari.domain.user.PasswordForgot;
import kr.co.wooltari.domain.user.User;
import kr.co.wooltari.domain.user.UserInfo;
import kr.co.wooltari.domain.user.UserPatchData;
import kr.co.wooltari.domain.user.UserSigninData;
import kr.co.wooltari.domain.user.UserSignupData;
import kr.co.wooltari.domain.user.UserSignupFacebook;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Kyung on 2017-12-18.
 */

public interface IUser {

    @POST("auth/signup/")
    Call<UserInfo> signup(
            @Body UserSignupData signupData
    );

    @POST("auth/login/")
    Call<UserInfo> signin(
            @Body UserSigninData signinData
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
            @Path("user_pk") int userPK
    );

    @PATCH("profile/{user_pk}/")
    Call<User> updateUser(
            @Path("user_pk") int userPK,
            @Body UserPatchData patchData
    );

    @DELETE("profile/{user_pk}/")
    Call deleteUser(
            @Path("user_pk") int userPK
    );
}
