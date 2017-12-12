package kr.co.wooltari.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.google.firebase.iid.FirebaseInstanceId;

=======
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.Date;

import kr.co.wooltari.domain.user.UserInfo;
>>>>>>> a01418a2c5d117940843895e5cdf9cf70f23af32
import kr.co.wooltari.main.MainActivity;
import kr.co.wooltari.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class SignInActivity extends AppCompatActivity {
    private EditText id_editText;
    private EditText password_editText;
    private TextView errormessage_textview;

    private Gson gson;
    private String URL="http://wooltari-test-server-dev.ap-northeast-2.elasticbeanstalk.com:80/";

    public static int COMPLETE_SIGNUP=8;
    public static int COMPLETE_MISSING_PASSWORD=9;
    
    private static String TAG="SignInActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
<<<<<<< HEAD

        Log.e("refreshedToken", "Refreshed token: " + FirebaseInstanceId.getInstance().getToken());

=======
        init();
    }
    private void init(){
        id_editText=findViewById(R.id.signin_id_edittext);
        password_editText=findViewById(R.id.signin_password_edittext);
        errormessage_textview=findViewById(R.id.signinactivity_errormessage_textview);
>>>>>>> a01418a2c5d117940843895e5cdf9cf70f23af32
    }

    public void onClick_Login(View view){
        errormessage_textview.setText(" ");
        String id=id_editText.getText().toString();
        String password=password_editText.getText().toString();

        // TODO : 임시로 액티비티 연결함
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);

        // TODO: 로그인 과정 다시 할 것
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(URL)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                ISendUserLoginInfo retrofitService = retrofit.create(ISendUserLoginInfo.class);
//
//                RequestUserLoginInfo requestUserLoginInfo=new RequestUserLoginInfo(id, password);
//
//                Call<ResponseSigninInfo> call = retrofitService.Post(requestUserLoginInfo);
//                call.enqueue(new Callback<ResponseSigninInfo>() {
//                    @Override
//                    public void onResponse(Call<ResponseSigninInfo> call, Response<ResponseSigninInfo> response) {
//                        Log.e(TAG+"1",call.toString());
//                        Log.e(TAG+"2",response.toString());
//                        String responseMessage="";
//                        if(response.code()==200) {
//                            ResponseSigninInfo userInfo = response.body();
//                            Log.e(TAG, userInfo.toString());
//
//                            if(userInfo.getUser().getIs_active()){
//                                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//                                startActivity(intent);
//                            }else{
//                                errormessage_textview.setText("Email is NotActive");
//                            }
//
//                        }else if(response.code()==401){
//                            try {
//                                responseMessage=response.errorBody().string();
//                                Log.e(TAG, responseMessage);
//                                Gson gson = new Gson();
//                                ResponseUserLoginInfo error = gson.fromJson(responseMessage, ResponseUserLoginInfo.class);
//
//                                if(responseMessage.contains("message")){
//                                    errormessage_textview.setText(error.getMessage());
//                                }else{
//                                    errormessage_textview.setText(error.getDetail());
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseSigninInfo> call, Throwable t) {
//                        Log.e(TAG, "error "+call.toString());
//                        finish();
//                    }
//                });
//
//            }
//        });
    }

    public void onClick_Signup(View view){
        Intent intent=new Intent(this,SignUpActivity.class);
        startActivityForResult(intent, COMPLETE_SIGNUP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String joinMassage = null;
        if(resultCode==RESULT_OK) {
            if (requestCode == COMPLETE_SIGNUP) {
                String email = data.getStringExtra("email");
                String nickname = data.getStringExtra("nickname");

                joinMassage = nickname + "님 환영합니다.\n" + email + "로 가셔서 가입메일을 확인해주세요!!";
                
            }else if(requestCode==COMPLETE_MISSING_PASSWORD){
                String email=data.getStringExtra("email");
                joinMassage=email+"로 가셔서 임시 패스워드를 확인해주세요!";
            }

            Toast.makeText(this, joinMassage, Toast.LENGTH_LONG).show();
        }
    }


    public void onClick_MiggingPassword(View view){ 
        Intent intent=new Intent(this,MissingPasswordActivity.class);
        startActivityForResult(intent, COMPLETE_MISSING_PASSWORD);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Pet")
                .setMessage("Are you sure you want to close this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        // 액티비티 종료
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}

interface ISendUserLoginInfo {
    @Headers("Content-Type: application/json")
    @POST("/auth/login/")
    public Call<ResponseSigninInfo> Post(@Body RequestUserLoginInfo requestUserLoginInfo);
}

class ResponseUserLoginInfo{
    private String message;
    private String detail;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

class RequestUserLoginInfo{
    private String id;
    private String password;

    public RequestUserLoginInfo(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RequestUserLoginInfo{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

class ResponseSigninInfo
{
    private String token;

    private User user;

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ResponseSigninInfo{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}

class User
{
    private int pk;
    private String user_type;
    private String email;
    private String nickname;
    private Boolean is_active;
    private String date_joined;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public String getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }

    @Override
    public String toString() {
        return "User{" +
                "pk=" + pk +
                ", user_type='" + user_type + '\'' +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", is_active=" + is_active +
                ", date_joined=" + date_joined +
                '}';
    }
}

