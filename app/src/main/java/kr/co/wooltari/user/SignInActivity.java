package kr.co.wooltari.user;

import android.content.DialogInterface;
import android.content.Intent;
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

import kr.co.wooltari.main.MainActivity;
import kr.co.wooltari.R;

public class SignInActivity extends AppCompatActivity {
    private EditText id_editText;
    private EditText password_editText;

    private Gson gson;
    private String url="http://wooltari-test-server-dev.ap-northeast-2.elasticbeanstalk.com/auth/signup/";
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

        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("email",id_editText.getText().toString());
        jsonObject.addProperty("password", id_editText.getText().toString());
        String json = gson.toJson(jsonObject);








        //       startActivity(new Intent(this,MainActivity.class));
    }

    public void onClick_Signup(View view){
        startActivity(new Intent(this,SignUpActivity.class));
    }

    public void onClick_MiggingPassword(View view){ startActivity(new Intent(this,MissingPasswordActivity.class));}
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

//
//public class UserInfo
//{
//    private String password1;
//
//    private String password2;
//
//    private String nickname;
//
//    private String email;
//
//    public String getPassword1 ()
//    {
//        return password1;
//    }
//
//    public void setPassword1 (String password1)
//    {
//        this.password1 = password1;
//    }
//
//    public String getPassword2 ()
//    {
//        return password2;
//    }
//
//    public void setPassword2 (String password2)
//    {
//        this.password2 = password2;
//    }
//
//    public String getNickname ()
//    {
//        return nickname;
//    }
//
//    public void setNickname (String nickname)
//    {
//        this.nickname = nickname;
//    }
//
//    public String getEmail ()
//    {
//        return email;
//    }
//
//    public void setEmail (String email)
//    {
//        this.email = email;
//    }
//
//    @Override
//    public String toString()
//    {
//        return "ClassPojo [password1 = "+password1+", password2 = "+password2+", nickname = "+nickname+", email = "+email+"]";
//    }
//}
