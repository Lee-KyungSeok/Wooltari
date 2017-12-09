package kr.co.wooltari.pet.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.custom.PetNavigationView;
import kr.co.wooltari.domain.HealthStateDummy;
import kr.co.wooltari.domain.MedicalInfoDummy;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.util.LoadUtil;
import kr.co.wooltari.util.ToolbarUtil;


public class PetDetailActivity extends AppCompatActivity {
    // 툴바 세팅
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private CollapsingToolbarLayout petDetailTitle;
    // 각각의 item을 관리하는 class 세팅
    PetDetailProfile petDetailProfile;
    PetDetailState petDetailState;
    PetDetailSchedule petDetailSchedule;
    PetDetailVaccination petDetailVaccination;
    PetDetailMedical petDetailMedical;

    private PetDummy.Dummy petInfo;
    private HealthStateDummy.StateDummy stateInfo;
    private MedicalInfoDummy.MedicalDummy medicalInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        getData();
        initToolbar();
        init();
    }

    private void getData(){
        int pFK = getIntent().getIntExtra(Const.PET_ID,-1);
        petInfo = PetDummy.data.get(pFK);
        stateInfo = HealthStateDummy.stateData.get(pFK);
        medicalInfo = MedicalInfoDummy.data.get(pFK);
    }

    public void changeView(int pFK){
        petInfo = PetDummy.data.get(pFK);
        stateInfo = HealthStateDummy.stateData.get(pFK);
        medicalInfo = MedicalInfoDummy.data.get(pFK);

        petDetailTitle.setTitle(petInfo.name);
        getSupportActionBar().setTitle(petInfo.name);
        petDetailTitle.setContentScrimColor(LoadUtil.loadColor(this,petInfo.body_color));
        petDetailProfile.setValue(petInfo);
        petDetailState.setValue(stateInfo);
        if(medicalInfo.petMediInfoList.size()>0) petDetailMedical.setValue(medicalInfo.petMediInfoList.get(medicalInfo.petMediInfoList.size()-1));
        else petDetailMedical.setValue(null);
    }

    private void initToolbar(){
        // 네비게이션 바 및 툴바 세팅
        drawer =findViewById(R.id.drawerPetLayout);
        new PetNavigationView(this,drawer, findViewById(R.id.navPetView)).setPetSpinnerLocation(petInfo.name);
        petDetailTitle = findViewById(R.id.petDetailTitle);
        toolbar = findViewById(R.id.petDetailToolbar);
        ToolbarUtil.setCommonToolbar(this, toolbar, petInfo.name);
        // 네비게이션 토글 연결
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        petDetailTitle.setContentScrimColor(LoadUtil.loadColor(this,petInfo.body_color));
    }

    private void init(){
        petDetailProfile = new PetDetailProfile(this, petInfo);
        petDetailState = new PetDetailState(this, stateInfo);
        petDetailSchedule = new PetDetailSchedule(this);
        petDetailVaccination = new PetDetailVaccination(this);
        if(medicalInfo.petMediInfoList.size()>0)
            petDetailMedical = new PetDetailMedical(this, medicalInfo.petMediInfoList.get(medicalInfo.petMediInfoList.size()-1), petInfo.pk);
        else  petDetailMedical = new PetDetailMedical(this,null,petInfo.pk);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Const.PET_PROFILE:
                if(resultCode == RESULT_OK) {
                    getData();
                    petDetailProfile.setValue(petInfo);
                }
                break;

        }
    }
}
