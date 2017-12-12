package kr.co.wooltari.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Arrays;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.custom.CameraGalleryPopup;
import kr.co.wooltari.domain.user.UserInfo;

import kr.co.wooltari.util.VerificationUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class SignUpActivity extends AppCompatActivity {
    private String TAG="SignUpActivity";
    // 위젯들
//    private ImageView UserProfileImageview;

    private EditText _id_editText;
    private Button _id_button;
    private TextView _id_errormassge_editText;

    private EditText nickname_editText;
    private Button nickname_button;
    private TextView nickname_errormassge_editText;

    private EditText password1_editText;
    private TextView password1_errormassage_editText;

    private EditText password2_editText;
    private TextView password2_errormassage_editText;

    private Button cancel_button;
    private Button join_button;

    // 카메라&갤러리 팝업, isImage는 프로필이 지정되어 있는지 체크
    CameraGalleryPopup cameraGalleryPopup = null;
    boolean isImage = false;

    private String URL="http://wooltari-test-server-dev.ap-northeast-2.elasticbeanstalk.com:80/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        initListener();
    }

    private void init(){
//        profile 필드
//        UserProfileImageview=findViewById(R.id.user_profile_imageview);

//        id 필드
        _id_editText=findViewById(R.id.id_editText);
        _id_button=findViewById(R.id.id_button);
        _id_errormassge_editText=findViewById(R.id.id_textView_errorMassage);

//        nickname 필드
        nickname_editText=findViewById(R.id.nickname_editText);
        nickname_button=findViewById(R.id.nickname_button);
        nickname_errormassge_editText=findViewById(R.id.nickname_textView_errormassage);

//        password1 필드
        password1_editText=findViewById(R.id.password1_editText);
        password1_errormassage_editText=findViewById(R.id.password1_textView_errormassage);

//        password2 필드
        password2_editText=findViewById(R.id.password2_editText);
        password2_errormassage_editText=findViewById(R.id.password2_textView_errormassage);

//            취소, 가입버튼
        cancel_button=findViewById(R.id.cancel_button);
        join_button=findViewById(R.id.ok_button);

        // 카마라 팝업 생성
//        cameraGalleryPopup = new CameraGalleryPopup(this, CameraGalleryPopup.PopupType.USER_PROFILE, basicProfileUri -> {
//            LoadUtil.circleImageLoad(this,basicProfileUri,UserProfileImageview);
//            isImage = false;
//        });

    }
//    public void onClick_set_image(View view){
//        cameraGalleryPopup.show();
//        cameraGalleryPopup.setBtnList(isImage);
//    }

    // 퍼미션 체크
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(Const.PERMISSION_REQ_CAMERA==requestCode || Const.PERMISSION_REQ_GALLERY == requestCode)
            cameraGalleryPopup.checkPermResult(requestCode,grantResults);
        else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // 이미지를 얻어온 결과를 다시 자리에 세팅
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Uri imageU ri = cameraGalleryPopup.getImage(requestCode,resultCode,data);
//        if(imageUri!=null) {
//            LoadUtil.circleImageLoad(this, imageUri, UserProfileImageview);
//            isImage = true;
//        }
//    }

    private void initListener(){

//          버튼 리스너 달기
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 뒤로 간다
                finish();
            }
        });


        _id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setErrorMessageblank();
                BeforeRequestVaildationOnlyEmailCheck();

                if(isNickNameFieldEnable()==false){
                    setNickNameFieldEnable(true);
                }
            }
        });

        nickname_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setErrorMessageblank();

                BeforeRequestVaildationOnlyNicknameCheck();

                if(isPasswordFieldEnable()==false){
                    setPasswordFieldEnalbe(true);
                }
            }
        });

        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setErrorMessageblank();

                if(VaildationCheck()==false){
                    return;
                }
                BeforeRequestVaildationCheck();
            }
        });

    }
    private void setNickNameFieldEnable(boolean setNickNameFieldEnable){
        nickname_editText.setEnabled(setNickNameFieldEnable);
        nickname_button.setEnabled(setNickNameFieldEnable);
    }
    private boolean isNickNameFieldEnable(){
        return nickname_editText.isEnabled()&& nickname_button.isEnabled();
    }
    private void setPasswordFieldEnalbe(boolean setPasswordFieldEnable){
        password1_editText.setEnabled(setPasswordFieldEnable);
        password2_editText.setEnabled(setPasswordFieldEnable);
    }
    private boolean isPasswordFieldEnable(){
        return password1_editText.isEnabled() && password2_editText.isEnabled();
    }

    private void BeforeRequestVaildationOnlyNicknameCheck(){
        String nickname=nickname_editText.getText().toString();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ISendUserInfo retrofitService = retrofit.create(ISendUserInfo.class);

                RequestUserInfo requestUserInfo=new RequestUserInfo("", nickname, "", "");

                Call<UserInfo> call = retrofitService.Post(requestUserInfo);
                call.enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        Log.e(TAG+"1",call.toString());
                        Log.e(TAG+"2",response.toString());

                        if(response.code()==400){
                            String errorMessage="";
                            Gson gson = new Gson();

                            try {
                                errorMessage=response.errorBody().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.e(TAG+"3",errorMessage);
                            ResponseErrorUserInfo error = gson.fromJson(errorMessage, ResponseErrorUserInfo.class);
//                                      닉네임 검사
                            if(error.getNickname()==null){
                                nickname_errormassge_editText.setTextColor(Color.GREEN);
                                nickname_errormassge_editText.setText("사용가능한 닉네임 입니다");

                            }else{
                                nickname_errormassge_editText.setTextColor(Color.RED);
                                if(error.getNickname()[0].contains("This field may not be blank.")){
                                    nickname_errormassge_editText.setText("공백입니다");
                                }else if(error.getNickname()[0].contains("User with this nickname already exists.")){
                                    nickname_errormassge_editText.setText("이미 존재하는 닉네임 입니다.");
                                }else{}
                            }
                        }else if(response.code()==201){

                        }
                        else{
                            try {
                                Log.e(TAG, "알수 없는 에러 발생 "+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        Log.e(TAG, "error "+call.toString());
                        finish();
                    }
                });

            }
        });
    }
    private void BeforeRequestVaildationOnlyEmailCheck(){
        String id=_id_editText.getText().toString();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ISendUserInfo retrofitService = retrofit.create(ISendUserInfo.class);

                RequestUserInfo requestUserInfo=new RequestUserInfo(id, "", "", "");

                Call<UserInfo> call = retrofitService.Post(requestUserInfo);
                call.enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        Log.e(TAG+"1",call.toString());
                        Log.e(TAG+"2",response.toString());

                        //실페
                        if(response.code()==400){
                            String errorMessage="";
                            Gson gson = new Gson();

                            try {
                                errorMessage=response.errorBody().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.e(TAG+"3",errorMessage);
                            ResponseErrorUserInfo error = gson.fromJson(errorMessage, ResponseErrorUserInfo.class);
//                                      이메일 검사
                            if(error.getEmail()==null){
                                _id_errormassge_editText.setTextColor(Color.GREEN);
                                _id_errormassge_editText.setText("가입가능한 이메일 입니다.");
                            }else{
                                _id_errormassge_editText.setTextColor(Color.RED);
                                if(error.getEmail()[0].contains( "This field may not be blank.")){
                                    _id_errormassge_editText.setText("공백입니다");
                                }else if(error.getEmail()[0].contains("Enter a valid email address.")){
                                    _id_errormassge_editText.setText("이메일 형식이 아닙니다.");
                                }else if(error.getEmail()[0].contains("This field must be unique.")){
                                    _id_errormassge_editText.setText("이미 가입된 이메일 입니다");
                                }else{}
                            }
                        }else if(response.code()==201){

                        }
                        else{
                            try {
                                Log.e(TAG, "알수 없는 에러 발생 "+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        Log.e(TAG, "error "+call.toString());
                    }
                });

            }
        });
    }
    private void BeforeRequestVaildationCheck() {
        String id=_id_editText.getText().toString();
        String nickname=nickname_editText.getText().toString();
        String password1=password1_editText.getText().toString();
        String password2=password2_editText.getText().toString();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ISendUserInfo retrofitService = retrofit.create(ISendUserInfo.class);

                RequestUserInfo requestUserInfo=new RequestUserInfo(id, nickname, password1, password2);

                Call<UserInfo> call = retrofitService.Post(requestUserInfo);
                call.enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        Log.e(TAG+"1",call.toString());
                        Log.e(TAG+"2",response.toString());

                        // 성공시
                        if(response.code()==201) {
                            UserInfo userInfo = response.body();
                            Log.e(TAG, userInfo.toString());
                            Intent intent = getIntent();
                            intent.putExtra("email", userInfo.getUser().getEmail());
                            intent.putExtra("nickname", userInfo.getUser().getNickname());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        //실페
                        else if(response.code()==400){
                            String errorMessage="";
                            Gson gson = new Gson();

                            try {
                                errorMessage=response.errorBody().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Log.e(TAG+"3",errorMessage);
                            ResponseErrorUserInfo error = gson.fromJson(errorMessage, ResponseErrorUserInfo.class);
//                                      이메일 검사
                            if(error.getEmail()==null){
                                _id_errormassge_editText.setTextColor(Color.GREEN);
                                _id_errormassge_editText.setText("가입가능한 이메일 입니다.");
                            }else{
                                _id_errormassge_editText.setTextColor(Color.RED);
                                if(error.getEmail()[0].contains( "This field may not be blank.")){
                                    _id_errormassge_editText.setText("공백입니다");
                                }else if(error.getEmail()[0].contains("Enter a valid email address.")){
                                    _id_errormassge_editText.setText("이메일 형식이 아닙니다.");
                                }else if(error.getEmail()[0].contains("This field must be unique.")){
                                    _id_errormassge_editText.setText("이미 가입된 이메일 입니다");
                                }else{}
                            }

//                                      닉네임 검사
                            if(error.getNickname()==null){
                                nickname_errormassge_editText.setTextColor(Color.GREEN);
                                nickname_errormassge_editText.setText("사용가능한 닉네임 입니다");

                            }else{
                                nickname_errormassge_editText.setTextColor(Color.RED);
                                if(error.getNickname()[0].contains("This field may not be blank.")){
                                    nickname_errormassge_editText.setText("공백입니다");
                                }else if(error.getNickname()[0].contains("User with this nickname already exists.")){
                                    nickname_errormassge_editText.setText("이미 존재하는 닉네임 입니다.");
                                }else{}
                            }

//                                      패스워드 검사 1
                            if(error.getPassword1()==null){
                                password1_errormassage_editText.setTextColor(Color.GREEN);
                                password1_errormassage_editText.setText("사용가능한 패스워드 입니다");
                            }else{
                                password1_errormassage_editText.setTextColor(Color.RED);
                                if(error.getPassword1()[0].contains("This field may not be blank.")){
                                    password1_errormassage_editText.setText("공백입니다");
                                }else{}
                            }

                            // password 검사 부분이 서버에서 받아온 값으로 처리하지 못함 그래서 로컬 값으로 처리함.
                            if(password1.equals(password2)){
                                password2_errormassage_editText.setTextColor(Color.GREEN);
                                password2_errormassage_editText.setText("패스워드가 일치 합니다");
                            }else{
                                password2_errormassage_editText.setTextColor(Color.RED);
                                password2_errormassage_editText.setText("패스워드가 일치 하지 않습니다");
                            }
                            if(password1.equals(" ")||password1.equals("")){
                                password1_errormassage_editText.setTextColor(Color.RED);
                                password1_errormassage_editText.setText("패스워드가 공백입니다");
                            }else if(password2.equals(" ")||password2.equals("")){
                                password2_errormassage_editText.setTextColor(Color.RED);
                                password2_errormassage_editText.setText("패스워드가 공백입니다");
                            }

                            //TODO:서버 Api 로 구현할때 패스워드에 버그가 있어서 주석 처리함.
//                                      패스워드 검사 2
//                                        if(error.getPassword2()==null){
//                                            password2_errormassage_editText.setTextColor(Color.GREEN);
//                                            password2_errormassage_editText.setText("패스워드가 일치 합니다");
//                                        }else{
//                                            password2_editText.setTextColor(Color.RED);
//
//                                            if(error.getPassword2()[0].contains("This field may not be blank.")){
//                                                password2_errormassage_editText.setText("패스워드가 공백입니다");
//                                            }else{}
//                                        }
//
//                                        if(error.getNon_field_errors()==null){
//                                            password2_errormassage_editText.setTextColor(Color.GREEN);
//                                            password2_errormassage_editText.setText("패스워드가 일치 합니다");
//                                        }else{
//                                            password2_errormassage_editText.setTextColor(Color.RED);
//
//                                            if(error.getNon_field_errors()[0].contains("Passwords do not match")){
//                                                password2_errormassage_editText.setText("패스워드가 일치 하지 않습니다");
//                                            }else {}
//                                        }

                        }else{
                            try {
                                Log.e(TAG, "알수 없는 에러 발생 "+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        Log.e(TAG, "error "+call.toString());
                        finish();
                    }
                });

            }
        });
    }

    private boolean VaildationCheck() {
        String id=_id_editText.getText().toString();
        String nickname=nickname_editText.getText().toString();
        String password1=password1_editText.getText().toString();
        String password2=password2_editText.getText().toString();
        return VerificationUtil.isValidEmail(id) &&
                VerificationUtil.isValidNickName(nickname) &&
                VerificationUtil.isValidPassword(password1) &&
                VerificationUtil.isValidPassword(password2);
    }

    private void setErrorMessageblank(){
        _id_errormassge_editText.setText(" ");
        nickname_errormassge_editText.setText(" ");
        password1_errormassage_editText.setText(" ");
        password2_errormassage_editText.setText(" ");
    }
}



interface ISendUserInfo {

    @Headers("Content-Type: application/json")
    @POST("/auth/signup/")
    public Call<UserInfo> Post(@Body RequestUserInfo requestUserInfo);
}

class RequestUserInfo{
    private String email;

    private String nickname;

    private String password1;

    private String password2;

    public RequestUserInfo(String email, String nickname, String password1, String password2) {
        this.email = email;
        this.nickname = nickname;
        this.password1 = password1;
        this.password2 = password2;
    }

    public void setEmail(String email){ this.email=email;}

    public void setNickname(String nickname){ this.nickname=nickname;}

    public void setPassword1(String password1){this.password1=password1;}

    public void setPassword2(String password2){this.password2=password2;}

    public String getEmail(){ return this.email;}

    public String getNickname(){ return this.nickname;}

    public String getPassword1(){return this.password1;}

    public String getPassword2(){return this.password2;}

    @Override
    public String toString() {
        return "RequestUserInfo{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password1='" + password1 + '\'' +
                ", password2='" + password2 + '\'' +
                '}';
    }
}

class ResponseErrorUserInfo
{
    private String[] password1;

    private String[] password2;

    private String[] nickname;

    private String[] email;

    public String[] getNon_field_errors() {
        return non_field_errors;
    }

    public void setNon_field_errors(String[] non_field_errors) {
        this.non_field_errors = non_field_errors;
    }

    private String[] non_field_errors;

    public String[] getPassword1 ()
    {
        return password1;
    }

    public void setPassword1 (String[] password1)
    {
        this.password1 = password1;
    }

    public String[] getPassword2 ()
    {
        return password2;
    }

    public void setPassword2 (String[] password2)
    {
        this.password2 = password2;
    }

    public String[] getNickname ()
    {
        return nickname;
    }

    public void setNickname (String[] nickname)
    {
        this.nickname = nickname;
    }

    public String[] getEmail ()
    {
        return email;
    }

    public void setEmail (String[] email)
    {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ResponseErrorUserInfo{" +
                "password1=" + Arrays.toString(password1) +
                ", password2=" + Arrays.toString(password2) +
                ", nickname=" + Arrays.toString(nickname) +
                ", email=" + Arrays.toString(email) +
                ", non_field_errors=" + Arrays.toString(non_field_errors) +
                '}';
    }
}




