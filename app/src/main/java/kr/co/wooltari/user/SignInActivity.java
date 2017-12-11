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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import kr.co.wooltari.domain.user.UserInfo;
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

    private Gson gson;
    private String URL="http://wooltari-test-server-dev.ap-northeast-2.elasticbeanstalk.com:80/";
    public static int COMPLETE_SIGNUP=8;
    public static int COMPLETE_MISSING_PASSWORD=9;
    
    private static String TAG="SignInActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        init();
    }
    private void init(){
        id_editText=findViewById(R.id.signin_id_edittext);
        password_editText=findViewById(R.id.signin_password_edittext);
    }

    public void onClick_Login(View view){
        String id=id_editText.getText().toString();
        String password=password_editText.getText().toString();



        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ISendUserLoginInfo retrofitService = retrofit.create(ISendUserLoginInfo.class);

                RequestUserLoginInfo requestUserLoginInfo=new RequestUserLoginInfo();
                requestUserLoginInfo.setId(id);
                requestUserLoginInfo.setPassword(password);

                Call<UserInfo> call = retrofitService.Post(requestUserLoginInfo);
                call.enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        Log.e(TAG+"1",call.toString());
                        Log.e(TAG+"2",response.toString());
                        if(response.code()==200) {
                            UserInfo userInfo = response.body();
                            Log.e(TAG, userInfo.toString());

                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
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
                .setNegativeButton("No", null)
                .show();
    }
}

interface ISendUserLoginInfo {
    @Headers("Content-Type: application/json")
    @POST("/auth/login/")
    public Call<UserInfo> Post(@Body RequestUserLoginInfo requestUserLoginInfo);
}

class RequestUserLoginInfo{
    private String id;
    private String password;

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
}