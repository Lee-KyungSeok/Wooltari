package kr.co.wooltari.domain.user;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import kr.co.wooltari.R;
import kr.co.wooltari.application.WooltariApp;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.domain.retrofit.RetrofitManager;
import kr.co.wooltari.domain.retrofit.IUser;
import kr.co.wooltari.main.MainActivity;
import kr.co.wooltari.user.SignInActivity;
import kr.co.wooltari.util.PreferenceUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kyung on 2017-12-18.
 */

public class UserDataManager {

    private static final String EMAIL_INVALID = "Enter a valid email address.";
    private static final String EMAIL_UNIQUE = "This field must be unique.";
    private static final String NICKNAME_UNIQUE = "User with this nickname already exists.";

    /**
     * User 로그인 메소드
     * @param activity 사용된 엑티비티
     * @param user 로그인할 객체
     * @param profileImage 프로필 이미지 (없으면 null)
     */
    public static void signup(Activity activity, User user, File profileImage){
        IUser service = RetrofitManager.create(IUser.class, true, false);
        Call<UserInfo> remote = null;// service.signup(signupData);

        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), user.getEmail());
        RequestBody nickname = RequestBody.create(MediaType.parse("text/plain"), user.getNickname());
        RequestBody password1 = RequestBody.create(MediaType.parse("text/plain"), user.getPassword1());
        RequestBody password2 = RequestBody.create(MediaType.parse("text/plain"), user.getPassword2());

        if(profileImage==null){
            remote = service.signup(email, nickname, password1, password2);
        } else {
            RequestBody imageFile = RequestBody.create(MediaType.parse("image/*"), profileImage);
            MultipartBody.Part image = MultipartBody.Part.createFormData("image", profileImage.getName(), imageFile);
            remote = service.signup(email, nickname, password1, password2, image);
        }
        remote.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.code()==201){
                    Log.e("sign data","===="+response.body().getUser().toString());
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    activity.finish();
                } else {
                    switch (response.code()){
                        case 400:
                            try {
                                String errorString = response.errorBody().string();
                                Gson gson = new Gson();
                                UserError error = gson.fromJson(errorString, UserError.class);
                                if(error.getEmail()!=null){
                                    switch (error.getEmail()[0]) {
                                        case EMAIL_UNIQUE:
                                            Toast.makeText(activity, activity.getResources().getString(R.string.user_email_unique), Toast.LENGTH_SHORT).show();
                                            break;
                                        case EMAIL_INVALID:
                                            Toast.makeText(activity, activity.getResources().getString(R.string.user_email_valid), Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            Toast.makeText(activity, error.getEmail()[0], Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                } else if (error.getNickname()!=null){
                                    switch (error.getNickname()[0]) {
                                        case NICKNAME_UNIQUE:
                                            Toast.makeText(activity, activity.getResources().getString(R.string.user_nickname_unique), Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            Toast.makeText(activity, error.getNickname()[0], Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                } else if (error.getPassword1()!=null){
                                    Toast.makeText(activity, error.getPassword1()[0], Toast.LENGTH_SHORT).show();
                                } else if (error.getPassword2()!=null){
                                    Toast.makeText(activity, error.getPassword2()[0], Toast.LENGTH_SHORT).show();
                                } else if (error.getNon_field_errors()!=null){
                                    Toast.makeText(activity, error.getNon_field_errors()[0], Toast.LENGTH_SHORT).show();
                                } else if (error.getDetail()!=null) {
                                    Toast.makeText(activity, error.getDetail(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("error",response.errorBody().toString());
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 413:
                            Toast.makeText(activity, activity.getResources().getString(R.string.image_size_error), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Log.e("error",response.errorBody().toString());
                            Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e("signup Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 이메일을 통한 로그인
     */
    public static void signin(Activity activity, User user, boolean isAutoSignin , CallbackSignin callback){
        IUser service = RetrofitManager.create(IUser.class,true,false);
        Call<UserInfo> remote = service.signin(user);
        remote.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if(response.code()==200){
                    UserInfo userInfo = response.body();
                    WooltariApp.userToken = userInfo.getToken();
                    WooltariApp.userPK = userInfo.getUser().getPk();
                    WooltariApp.userEmail = userInfo.getUser().getEmail();
                    WooltariApp.userName = userInfo.getUser().getNickname();
                    WooltariApp.userImage = userInfo.getUser().getImage();
                    PreferenceUtil.setValue(activity, Const.USER_EMAIL, user.getEmail());
                    PreferenceUtil.setValue(activity, Const.USER_PASSWORD, user.getPassword());
                    PreferenceUtil.setValue(activity, Const.USER_AUTO_SIGNIN, true);
                    callback.success();
                } else {
                    if(isAutoSignin){
                        Toast.makeText(activity,activity.getResources().getString(R.string.user_sign_again),Toast.LENGTH_SHORT).show();
                        callback.fail();
                    } else {
                        UserError error;
                        try {
                            String errorString = response.errorBody().string();
                            Gson gson = new Gson();
                            error = gson.fromJson(errorString, UserError.class);
                            switch (response.code()) {
                                case 401:
                                    if (error.getMessage() != null) {
                                        Toast.makeText(activity, activity.getResources().getString(R.string.user_signin_fail), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                default:
                                    Log.e("error", response.errorBody().toString());
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                if(isAutoSignin) {
                    Log.e("signin Failure", t.getMessage());
                    Toast.makeText(activity, activity.getResources().getString(R.string.user_sign_again), Toast.LENGTH_SHORT).show();
                    callback.fail();
                } else {
                    Log.e("signin Failure", t.getMessage());
                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 페이스북을 통한 로그인
     */
    public static void signinFacebook(Activity activity){
    }

    /**
     * 로그 아웃 실행
     */
    public static void signout(Activity activity){
        IUser service = RetrofitManager.create(IUser.class,false,true);
        Call<UserInfo> remote = service.signout();
        remote.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if(response.code()==200){
                    PreferenceUtil.setValue(activity, Const.USER_AUTO_SIGNIN, false);
                    PreferenceUtil.setValue(activity, Const.USER_EMAIL, "");
                    PreferenceUtil.setValue(activity, Const.USER_PASSWORD, "");
                    activity.startActivity(new Intent(activity, SignInActivity.class));
                    activity.finish();
                } else {
                    UserError error;
                    try {
                        String errorString = response.errorBody().string();
                        Gson gson = new Gson();
                        error = gson.fromJson(errorString,UserError.class);
                        switch (response.code()){
                            case 400:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, activity.getResources().getString(R.string.user_signout_fail), Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("error",response.errorBody().toString());
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            default:
                                Log.e("error",response.errorBody().toString());
                                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.e("signout Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 비밀번호 찾기 메소드
     */
    public static void getNewPassword(Activity activity, String email){
        PasswordForgot passwordForgot = new PasswordForgot();
        passwordForgot.setEmail(email);
        IUser service = RetrofitManager.create(IUser.class, true, false);
        Call<PasswordForgot> remote = service.getNewPassword(passwordForgot);
        remote.enqueue(new Callback<PasswordForgot>() {
            @Override
            public void onResponse(Call<PasswordForgot> call, Response<PasswordForgot> response) {
                if(response.code() == 200){
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    activity.finish();
                } else {
                    UserError error;
                    try {
                        String errorString = response.errorBody().string();
                        Gson gson = new Gson();
                        error = gson.fromJson(errorString,UserError.class);
                        switch (response.code()){
                            case 400:
                                if(error.getEmail()!=null){
                                    switch (error.getEmail()[0]){
                                        case EMAIL_INVALID:
                                            Toast.makeText(activity, activity.getResources().getString(R.string.user_email_valid), Toast.LENGTH_SHORT).show();
                                            break;
                                        default:
                                            Toast.makeText(activity, error.getEmail()[0], Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                } else if (error.getNon_field_errors()!=null){
                                    Toast.makeText(activity,activity.getResources().getString(R.string.user_forgot_password_email_incorrect), Toast.LENGTH_SHORT).show();
                                }else {
                                    Log.e("error",response.errorBody().toString());
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            default:
                                Log.e("error",response.errorBody().toString());
                                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PasswordForgot> call, Throwable t) {
                Log.e("getNewPassword Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 유저정보 조회 메소드
     */
    public static void getUser(Activity activity, CallbackGetUser callback){
        IUser service = RetrofitManager.create(IUser.class, true, true);
        Call<User> remote = service.getUser(WooltariApp.userPK);
        remote.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code() == 200){
                    callback.getUser(response.body());
                } else {
                    UserError error;
                    try {
                        String errorString = response.errorBody().string();
                        Gson gson = new Gson();
                        error = gson.fromJson(errorString,UserError.class);
                        switch (response.code()){
                            case 400:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, activity.getResources().getString(R.string.user_null), Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("error",response.errorBody().toString());
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            default:
                                Log.e("error",response.errorBody().toString());
                                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("getUser Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 유저정보 업데이트
     */
    public static void updateUser(Activity activity, User user, File profileImage, CallbackGetUser callback){
        IUser service = RetrofitManager.create(IUser.class, false, true);
        Call<User> remote = null;
        RequestBody nickname = RequestBody.create(MediaType.parse("text/plain"), user.getNickname());
        RequestBody password1 = RequestBody.create(MediaType.parse("text/plain"), user.getPassword1());
        RequestBody password2 = RequestBody.create(MediaType.parse("text/plain"), user.getPassword2());
        if(profileImage == null){
            remote = service.updateUser(WooltariApp.userPK, nickname, password1, password2);
        } else {
            RequestBody imageFile = RequestBody.create(MediaType.parse("image/*"), profileImage);
            MultipartBody.Part image = MultipartBody.Part.createFormData("image", profileImage.getName(), imageFile);
            remote = service.updateUser(WooltariApp.userPK, nickname, password1, password2,image);
        }
        remote.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.code()==200){
                    callback.getUser(response.body());
                } else if (response.code()==413){
                    Toast.makeText(activity, activity.getResources().getString(R.string.image_size_error), Toast.LENGTH_SHORT).show();
                } else {
                    UserError error;
                    try {
                        String errorString = response.errorBody().string();
                        Gson gson = new Gson();
                        error = gson.fromJson(errorString,UserError.class);
                        switch (response.code()){
                            case 400:
                                if(error.getNon_field_errors()!=null){
                                    Toast.makeText(activity, error.getNon_field_errors()[0], Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("error",response.errorBody().toString());
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 401:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, activity.getResources().getString(R.string.invalid_token), Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("error",response.errorBody().toString());
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 404:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, activity.getResources().getString(R.string.user_null), Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("error",response.errorBody().toString());
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            default:
                                Log.e("error",response.errorBody().toString());
                                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("updateUser Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 유저정보 삭제
     */
    public static void deleteUser(Activity activity, CallbackDelete callback){
        IUser service = RetrofitManager.create(IUser.class,true,true);
        Call<User> remote = service.deleteUser(WooltariApp.userPK);
        remote.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code()==204) {
                    Toast.makeText(activity, activity.getResources().getString(R.string.user_delete_success), Toast.LENGTH_SHORT).show();
                    callback.finish();
                } else {
                    UserError error;
                    try {
                        String errorString = response.errorBody().string();
                        Gson gson = new Gson();
                        error = gson.fromJson(errorString,UserError.class);
                        switch (response.code()){
                            case 401:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, activity.getResources().getString(R.string.invalid_token), Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("error",response.errorBody().toString());
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 403:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, activity.getResources().getString(R.string.invalid_token), Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("error",response.errorBody().toString());
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 404:
                                if(error.getDetail()!=null){
                                    Toast.makeText(activity, activity.getResources().getString(R.string.user_null), Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("error",response.errorBody().toString());
                                    Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                }
                                break;
                            default:
                                Log.e("error",response.errorBody().toString());
                                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("deleteUser Failure",t.getMessage());
                Toast.makeText(activity, activity.getResources().getString(R.string.unknown_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface CallbackSignin{
        void success();
        void fail();
    }

    public interface CallbackGetUser{
        void getUser(User user);
    }

    public interface CallbackDelete{
        void finish();
    }
}
