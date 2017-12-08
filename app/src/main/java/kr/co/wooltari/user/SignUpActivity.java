package kr.co.wooltari.user;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.custom.CameraGalleryPopup;
import kr.co.wooltari.domain.user.UserInfo;
import kr.co.wooltari.util.LoadUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class SignUpActivity extends AppCompatActivity {
    private String TAG="SignUpActivity";
    // 위젯들
    private ImageView UserProfileImageview;

    private EditText _id_editText;
    private Button _id_button;
    private TextView _id_errormassge_editText;

    private EditText nickname_editText;
    private Button nickname_button;
    private TextView nickname_errormassge_editText;

    private EditText password1_editText;
    private TextView password1_errormassage;

    private EditText password2_editText;
    private TextView password2_errormassage;

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
        // TODO 지금은 url 이지만 나중에는 기본 이미지따다가 그걸로 바꿀것
        UserProfileImageview=findViewById(R.id.user_profile_imageview);
//        LoadUtil.circleImageLoad(this, PetDummy.data.get(1).pProfile, UserProfileImageview);

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
        password1_errormassage=findViewById(R.id.password1_textView_errormassage);

//        password2 필드
        password2_editText=findViewById(R.id.password2_editText);
        password2_errormassage=findViewById(R.id.password2_textView_errormassage);

//            취소, 가입버튼
        cancel_button=findViewById(R.id.cancel_button);
        join_button=findViewById(R.id.ok_button);

        // 카마라 팝업 생성
        cameraGalleryPopup = new CameraGalleryPopup(this, CameraGalleryPopup.PopupType.USER_PROFILE, basicProfileUri -> {
            LoadUtil.circleImageLoad(this,basicProfileUri,UserProfileImageview);
            isImage = false;
        });

    }
    public void onClick_set_image(View view){
        cameraGalleryPopup.show();
        cameraGalleryPopup.setBtnList(isImage);
    }

    // 퍼미션 체크
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(Const.PERMISSION_REQ_CAMERA==requestCode || Const.PERMISSION_REQ_GALLERY == requestCode)
            cameraGalleryPopup.checkPermResult(requestCode,grantResults);
        else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // 이미지를 얻어온 결과를 다시 자리에 세팅
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri imageUri = cameraGalleryPopup.getImage(requestCode,resultCode,data);
        if(imageUri!=null) {
            LoadUtil.circleImageLoad(this, imageUri, UserProfileImageview);
            isImage = true;
        }
    }

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
//                String id=_id_editText.getText().toString();
//                AsyncTask.execute(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Retrofit retrofit = new Retrofit.Builder()
//                                .baseUrl(URL)
//                                .addConverterFactory(GsonConverterFactory.create())
//                                .build();
//
//                        ISendUserInfo retrofitService = retrofit.create(ISendUserInfo.class);
//                        Call<UserInfo> call = retrofitService.IDPost(id);
//                        call.enqueue(new Callback<UserInfo>() {
//                            @Override
//                            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
//                                Log.e(TAG+"1",call.toString());
//                                Log.e(TAG+"2",response.toString());
//                            }
//
//                            @Override
//                            public void onFailure(Call<UserInfo> call, Throwable t) {
//                                Log.e(TAG, "error "+call.toString());
//                            }
//                        });
//
//                    }
//                });
            }
        });

        nickname_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname=nickname_editText.getText().toString();
            }
        });

        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                        RequestUserInfo requestUserInfo=new RequestUserInfo();

                        requestUserInfo.setEmail(id);
                        requestUserInfo.setNickname(nickname);
                        requestUserInfo.setPassword1(password1);
                        requestUserInfo.setPassword2(password2);

                        Call<UserInfo> call = retrofitService.Post(requestUserInfo);
                        call.enqueue(new Callback<UserInfo>() {
                            @Override
                            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                                Log.e(TAG+"1",call.toString());
                                Log.e(TAG+"2",response.toString());
                                UserInfo userInfo = response.body();
                                Log.e(TAG,userInfo.toString());
                                Intent intent=getIntent();
                                intent.putExtra("email", userInfo.getUser().getEmail());
                                intent.putExtra("nickname", userInfo.getUser().getNickname());
                                setResult(RESULT_OK, intent);
                                finish();
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
        });

    }

}

interface ISendUserInfo {

    @Headers("Content-Type: application/json")
    @POST("/auth/signup/")
    public Call<UserInfo> Post(@Body RequestUserInfo requestUserInfo);

//    @Headers("Content-Type: application/json")
//    @POST("/auth/signup")
//    @FormUrlEncoded
//    public Call<UserInfo> IDPost(@Field("id")String _id);
}

class NotVaildEmail
{
    private String[] email;

    public String[] getEmail ()
    {
        return email;
    }

    public void setEmail (String[] email)
    {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [email = "+email+"]";
    }
}

class RequestUserInfo{
    private String email;
    private String nickname;
    private String password1;
    private String password2;
    public void setEmail(String email){ this.email=email;}
    public void setNickname(String nickname){ this.nickname=nickname;}
    public void setPassword1(String password1){this.password1=password1;}
    public void setPassword2(String password2){this.password2=password2;}
    public String getEmail(){ return this.email;}
    public String getNickname(){ return this.nickname;}
    public String getPassword1(){return this.password1;}
    public String getPassword2(){return this.password2;}

}




