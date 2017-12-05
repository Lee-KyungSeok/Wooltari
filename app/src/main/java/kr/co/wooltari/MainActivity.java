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
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        // 네비게이션 바 및 툴바 세팅
        drawer =findViewById(R.id.drawerMainLayout);
        new PetNavigationView(this,drawer, findViewById(R.id.navMainView));
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        ToolbarUtil.setCommonToolbar(this, toolbar, "");
        // 네비게이션 토글 연결
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu_etc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return ToolbarUtil.setMenuItemSelectedAction(this, item) || super.onOptionsItemSelected(item);
    }

    //=============================Pet 화면으로 넘기는 임시 버튼===============================
    public void goPet(View v){
        startActivity(new Intent(this, PetDetailActivity.class));
    }
}
