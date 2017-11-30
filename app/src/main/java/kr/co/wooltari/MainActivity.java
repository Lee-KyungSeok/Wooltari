package kr.co.wooltari;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import kr.co.wooltari.custom.PetNavigationView;
import kr.co.wooltari.pet.PetDetailActivity;
import kr.co.wooltari.util.ToolbarUtil;

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
