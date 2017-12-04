package kr.co.wooltari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import kr.co.wooltari.pet.PetProfileActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onCLick_Login(View view){
        startActivity(new Intent(this,MainActivity.class));
    }

    public void onCLick_Signup(View view){
        startActivity(new Intent(this,SignUpActivity.class));
    }
}
