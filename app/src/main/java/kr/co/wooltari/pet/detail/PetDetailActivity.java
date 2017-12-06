package kr.co.wooltari.pet.detail;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.custom.PetNavigationView;
import kr.co.wooltari.domain.HealthStateDummy;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.util.ToolbarUtil;


public class PetDetailActivity extends AppCompatActivity {
    // 툴바 세팅
    DrawerLayout drawer;
    Toolbar toolbar;
    CollapsingToolbarLayout petDetailTitle;
    // 각각의 item을 관리하는 class 세팅
    PetDetailProfile petDetailProfile;
    PetDetailState petDetailState;
    PetDetailSchedule petDetailSchedule;
    PetDetailVaccination petDetailVaccination;
    PetDetailMedical petDetailMedical;

    private PetDummy.Dummy petInfo;
    private HealthStateDummy.StateDummy stateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        int pFK = getIntent().getIntExtra(Const.PET_ID,-1);
        petInfo = PetDummy.data.get(pFK);
        stateInfo = HealthStateDummy.stateData.get(pFK);

        initToolbar();
    }

    public void changeView(int pFK){
        petInfo = PetDummy.data.get(pFK);
        stateInfo = HealthStateDummy.stateData.get(pFK);
        petDetailTitle.setTitle(petInfo.pName);
        getSupportActionBar().setTitle(petInfo.pName);
    }

    private void initToolbar(){
        // 네비게이션 바 및 툴바 세팅
        drawer =findViewById(R.id.drawerPetLayout);
        new PetNavigationView(this,drawer, findViewById(R.id.navPetView)).setPetSpinnerLocation(petInfo.pName);
        petDetailTitle = findViewById(R.id.petDetailTitle);
        toolbar = findViewById(R.id.petDetailToolbar);
        ToolbarUtil.setCommonToolbar(this, toolbar, petInfo.pName);
        // 네비게이션 토글 연결
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void init(){
        petDetailProfile = new PetDetailProfile(this);
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
}
