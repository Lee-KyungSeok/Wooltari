package kr.co.wooltari.pet;

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

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.custom.PetNavigationView;
import kr.co.wooltari.domain.HealthStateDummy;
import kr.co.wooltari.medicalcare.healthState.PetStateActivity;
import kr.co.wooltari.util.ToolbarUtil;


public class PetDetailActivity extends AppCompatActivity {

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);
        initView();

        HealthStateDummy.stateData.get(0);
    }

    private void initView(){
        // 네비게이션 바 및 툴바 세팅
        drawer =findViewById(R.id.drawerPetLayout);
        PetNavigationView navigation = new PetNavigationView(this,drawer, findViewById(R.id.navPetView));
        Toolbar toolbar = findViewById(R.id.petDetailToolbar);
        ToolbarUtil.setCommonToolbar(this, toolbar, "Pet0");
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

    //====================================임시 버튼
    public void goProfile(View v){
        startActivity(new Intent(this,PetProfileActivity.class));
    }
    public void goDefault(View v){
        Intent intent = new Intent(this, PetProfileActivity.class);
        intent.putExtra(Const.PET_ID,2);
        startActivity(intent);
    }
    public void goState(View v){
        Intent intent = new Intent(this, PetStateActivity.class);
        intent.putExtra(Const.PET_ID,6);
        startActivity(intent);
    }
}
