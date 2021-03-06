package kr.co.wooltari.user;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Observable;
import jp.wasabeef.glide.transformations.BlurTransformation;
import kr.co.wooltari.R;
import kr.co.wooltari.domain.user.User;
import kr.co.wooltari.domain.user.UserDataManager;
import kr.co.wooltari.domain.user.UserSignupFacebook;
import kr.co.wooltari.main.MainActivity;
import kr.co.wooltari.util.LoadUtil;
import kr.co.wooltari.util.VerificationUtil;

public class SignInActivity extends AppCompatActivity {

    private ImageView imageSigninBackground;
    private LoginButton btnFacebook;
    private EditText editSigninEmail;
    private EditText editSigninPassword;
    private Button btnSignin;
    private TextView textViewForgot;
    private Button btnSignup;
    private Toast backToast;
    private FrameLayout signinProgressStage;

    private long backPressedTime = 0;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initView();
        init();

    }

    private void initView() {
        imageSigninBackground = findViewById(R.id.imageSigninBackground);
        btnFacebook = findViewById(R.id.btnFacebook);
        editSigninEmail = findViewById(R.id.editSigninEmail);
        editSigninPassword = findViewById(R.id.editSigninPassword);
        btnSignin = findViewById(R.id.btnSignin);
        textViewForgot = findViewById(R.id.textViewForgot);
        btnSignup = findViewById(R.id.btnSignup);
        signinProgressStage = findViewById(R.id.signinProgressStage);
        signinProgressStage.setVisibility(View.GONE);
    }

    private void init() {
        setBackground();
        setListener();
        setEditTextChecking();
        setFaceBookLogin();
    }

    private void setBackground() {
        Glide.with(this)
                .load(LoadUtil.getResourceImageUri(R.drawable.startback, this))
                .thumbnail(0.1f)
                .into(imageSigninBackground);
    }

    private void setListener() {
        btnSignin.setOnClickListener(v -> {
            signinProgressStage.setVisibility(View.VISIBLE);
            User user = new User();
            user.setEmail(editSigninEmail.getText().toString());
            user.setPassword(editSigninPassword.getText().toString());
            user.setDevice_token(getDeviceToken());
            UserDataManager.signin(this, user, false, new UserDataManager.CallbackSignin() {
                @Override
                public void success() {
                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void fail() {
                    signinProgressStage.setVisibility(View.GONE);
                }
            });
        });

        btnSignup.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });

        textViewForgot.setOnClickListener(v -> {
            startActivity(new Intent(this, MissingPasswordActivity.class));
        });
    }

    private void setEditTextChecking() {
        Observable<TextViewTextChangeEvent> enaukEmitter = RxTextView.textChangeEvents(editSigninEmail);
        Observable<TextViewTextChangeEvent> passwordEmitter = RxTextView.textChangeEvents(editSigninPassword);
        Observable.combineLatest(
                enaukEmitter, passwordEmitter,
                (e, p) -> VerificationUtil.isValidEmail(e.text().toString()) && p.text().length() > 0
        ).subscribe(
                flag -> {
                    btnSignin.setEnabled(flag);
                    if(flag) btnSignin.setBackgroundColor(LoadUtil.loadColor(this,"wooltari"));
                    else btnSignin.setBackgroundColor(LoadUtil.loadColor(this,"translucent"));
                }
        );
    }

    private void setFaceBookLogin(){
        callbackManager = CallbackManager.Factory.create();
        btnFacebook.setReadPermissions("email");
        btnFacebook.setOnClickListener(v -> signinProgressStage.setVisibility(View.VISIBLE));
        btnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                UserSignupFacebook signupFacebookData = new UserSignupFacebook();
                signupFacebookData.setFacebook_user_id(loginResult.getAccessToken().getUserId());
                signupFacebookData.setAccess_token(loginResult.getAccessToken().getToken());
                signupFacebookData.setDevice_token(getDeviceToken());
                UserDataManager.signinFacebook(SignInActivity.this, signupFacebookData, new UserDataManager.CallbackSignin() {
                    @Override
                    public void success() {
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void fail() {
                        LoginManager.getInstance().logOut();
                        signinProgressStage.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onCancel() {
                Log.e("cancel","=======================");
                signinProgressStage.setVisibility(View.GONE);
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("error","======================="+error.toString());
                signinProgressStage.setVisibility(View.GONE);
            }
        });
    }

    private String getDeviceToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }

    private void showGuide() {
        backToast = Toast.makeText(this, getResources().getString(R.string.user_sign_in_back_pressed),Toast.LENGTH_SHORT);
        backToast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 페이스북 로그인 결과 전송
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        } else if (System.currentTimeMillis() <= backPressedTime + 2000) {
            finish();
            backToast.cancel();
        }
    }
}

