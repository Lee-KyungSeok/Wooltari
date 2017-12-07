package kr.co.wooltari.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kr.co.wooltari.R;

public class UserDetailActivity extends AppCompatActivity{
    Button ModifyUserInfoButton;
    TextView EmailTextView;
    EditText NickNameEdittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        init();
    }
    private void init(){
        ModifyUserInfoButton= findViewById(R.id.userdetail_modify_UserInfo_button);
        EmailTextView=findViewById(R.id.userdetail_email_textview);
        NickNameEdittext =findViewById(R.id.userdetail_nickname_edittext);
    }

    public void modifyUserInfo(View view) {
        boolean flag=NickNameEdittext.isEnabled();
        if(flag){
            NickNameEdittext.setEnabled(false);
        }else{
            NickNameEdittext.setEnabled(true);
        }
    }
}
