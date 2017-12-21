package kr.co.wooltari.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.domain.user.User;
import kr.co.wooltari.domain.user.UserDataManager;
import kr.co.wooltari.domain.user.UserSignupFacebook;
import kr.co.wooltari.main.MainActivity;
import kr.co.wooltari.user.SignInActivity;
import kr.co.wooltari.util.PreferenceUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (PreferenceUtil.getBoolean(getApplicationContext(),Const.USER_AUTO_SIGNIN)){
            User user = new User();
            user.setEmail(PreferenceUtil.getString(getApplicationContext(),Const.USER_EMAIL));
            user.setPassword(PreferenceUtil.getString(getApplicationContext(),Const.USER_PASSWORD));
            user.setDevice_token(FirebaseInstanceId.getInstance().getToken());
            UserDataManager.signin(this, user, true, new UserDataManager.CallbackSignin() {
                @Override
                public void success() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void fail() {
                    PreferenceUtil.setValue(getApplicationContext(),Const.USER_AUTO_SIGNIN,false);
                    PreferenceUtil.setValue(getApplicationContext(),Const.USER_EMAIL,"");
                    PreferenceUtil.setValue(getApplicationContext(),Const.USER_PASSWORD,"");
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                    finish();
                }
            });
        } else if (AccessToken.getCurrentAccessToken() != null){
//            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
            UserSignupFacebook userSignupFacebookData = new UserSignupFacebook();
            userSignupFacebookData.setFacebook_user_id(AccessToken.getCurrentAccessToken().getUserId());
            userSignupFacebookData.setAccess_token(AccessToken.getCurrentAccessToken().getToken());
            userSignupFacebookData.setDevice_token(FirebaseInstanceId.getInstance().getToken());
            UserDataManager.signinFacebook(this, userSignupFacebookData, new UserDataManager.CallbackSignin() {
                @Override
                public void success() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void fail() {
                    PreferenceUtil.setValue(getApplicationContext(),Const.USER_FACEBOOK_AUTO_SIGNIN,false);
                    LoginManager.getInstance().logOut();
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                    finish();
                }
            });
        } else {
            startActivity(new Intent(SplashActivity.this, SignInActivity.class));
            finish();
        }
    }
}
