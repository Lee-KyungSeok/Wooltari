package kr.co.wooltari;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kr.co.wooltari.pet.PetDetailActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //=============================Pet 화면으로 넘기는 임시 버튼===============================
    public void goPet(View v){
        startActivity(new Intent(this, PetDetailActivity.class));
    }
}
