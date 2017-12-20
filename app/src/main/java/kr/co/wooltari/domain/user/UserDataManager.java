package kr.co.wooltari.domain.user;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import kr.co.wooltari.R;
import kr.co.wooltari.domain.RetrofitManager;
import kr.co.wooltari.domain.retrofit.IUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kyung on 2017-12-18.
 */

public class UserDataManager {
    public static void signup(Activity activity, UserSignupData signupData, CallbackSignup callback){
        IUser service = RetrofitManager.create(IUser.class, true, false);
        Call<UserInfo> remote = service.signup(signupData);
        remote.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Log.e("isSuccessful","====="+response.isSuccessful());
                if (response.code()==201){
                    Log.e("sign data","===="+response.body().getUser().toString());
                } else {
                    switch (response.code()){
                        case 400:
                            UserError errorString;
                            break;
                        case 413:
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

    public interface CallbackSignup{
        void getUserInfo(UserInfo userInfo);
    }
}
