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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;
import kr.co.wooltari.R;
import kr.co.wooltari.constant.Const;
import kr.co.wooltari.custom.PetNavigationView;
import kr.co.wooltari.domain.HealthStateDummy;
import kr.co.wooltari.domain.MedicalInfoDummy;
import kr.co.wooltari.domain.PetDummy;
import kr.co.wooltari.domain.pet.Age;
import kr.co.wooltari.domain.pet.Pet;
import kr.co.wooltari.domain.pet.PetDataManager;
import kr.co.wooltari.medicalcare.healthState.PetStateActivity;
import kr.co.wooltari.medicalcare.medicalinfo.PetMedicalInfoActivity;
import kr.co.wooltari.pet.PetProfileActivity;
import kr.co.wooltari.util.LoadUtil;
import kr.co.wooltari.util.ToolbarUtil;


public class PetDetailActivity extends AppCompatActivity implements PetNavigationView.ISetSpinner {
    // 툴바 세팅
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private CollapsingToolbarLayout petDetailTitle;
    private PetNavigationView navigationView;
    // 각각의 item을 관리하는 class 세팅
    PetDetailProfile petDetailProfile;
    PetDetailState petDetailState;
    PetDetailSchedule petDetailSchedule;
    PetDetailVaccination petDetailVaccination;
    PetDetailMedical petDetailMedical;

    private Pet petInfo;
    private HealthStateDummy.StateDummy stateInfo;
    private MedicalInfoDummy.MedicalDummy medicalInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        init();
        getData();
    }

    private void init(){
        petDetailProfile = new PetDetailProfile(this, () -> goActivity(PetProfileActivity.class, Const.PET_PROFILE));
        petDetailState = new PetDetailState(this, () -> goActivity(PetStateActivity.class,Const.PET_STATE));

        petDetailSchedule = new PetDetailSchedule(this);
        petDetailVaccination = new PetDetailVaccination(this);

        petDetailMedical = new PetDetailMedical(this ,() -> goActivity(PetMedicalInfoActivity.class, Const.PET_MEDICAL));

        ImageView imagePetDetailStage = findViewById(R.id.imagePetDetailStage);
        LoadUtil.blurImageLoad(this,LoadUtil.getResourceImageUri(R.drawable.detail_background_,this),imagePetDetailStage);
    }

    private void getData(){
        int petPK = getIntent().getIntExtra(Const.PET_ID,-1);
        if(petPK<=8) {
            petInfo = PetDummy.data.get(petPK);
            initToolbar();
            petDetailProfile.setValue(petInfo);
            petDetailProfile.setAge(null);
        } else {
            PetDataManager.getPet(this, petPK, new PetDataManager.CallbackGetPet() {
                @Override
                public void getPetData(Pet petData) {
                    if(petData==null) {
                        Toast.makeText(PetDetailActivity.this, getResources().getString(R.string.pet_null), Toast.LENGTH_SHORT).show();
                    } else {
                        petInfo = petData;
                        initToolbar();
                        petDetailProfile.setValue(petInfo);
                    }
                }
            });
            PetDataManager.getPetAge(this, petPK, new PetDataManager.CallbackGetPetAge() {
                @Override
                public void getPetAge(Age petAge) {
                    if(petAge==null){
                        Toast.makeText(PetDetailActivity.this, getResources().getString(R.string.pet_age_null), Toast.LENGTH_SHORT).show();
                    } else {
                        Age age = petAge;
                        petDetailProfile.setAge(age);
                    }
                }
            });

            tempLoad(petPK);
        }
    }

    // 임시로 로드
    private void tempLoad(int pk){
        // 서버 연동시 수정 필요
        for(HealthStateDummy.StateDummy data : HealthStateDummy.stateData){
            if(data.petPk == pk) {
                stateInfo = data;
                break;
            }
        }
        for(MedicalInfoDummy.MedicalDummy data : MedicalInfoDummy.data){
            if(data.pPK == pk){
                medicalInfo = data;
                break;
            }
        }

        if(stateInfo!=null) petDetailState.setValue(stateInfo);

        if(medicalInfo.petMediInfoList.size()>0) petDetailMedical.setValue(medicalInfo.petMediInfoList.get(medicalInfo.petMediInfoList.size()-1));
        else petDetailMedical.setValue(null);
    }

    /**
     * 네비게이션에서 다른 펫 선택시 바꾸는 메소드
     * @param petChangePK
     */
    public void changeView(int petChangePK){

        if(petChangePK<=8) {
            petInfo = PetDummy.data.get(petChangePK);
            petDetailTitle.setTitle(petInfo.getName());
            getSupportActionBar().setTitle(petInfo.getName());
            petDetailTitle.setContentScrimColor(LoadUtil.loadColor(this,petInfo.getBody_color()));
            petDetailTitle.setStatusBarScrimColor(LoadUtil.loadColor(this,petInfo.getBody_color()));
            petDetailProfile.setValue(petInfo);
            petDetailProfile.setAge(null);

        } else {
            PetDataManager.getPet(this, petChangePK, new PetDataManager.CallbackGetPet() {
                @Override
                public void getPetData(Pet petData) {
                    if(petData==null) {
                        Toast.makeText(PetDetailActivity.this, getResources().getString(R.string.pet_null), Toast.LENGTH_SHORT).show();
                    } else {
                        petInfo = petData;
                        petDetailTitle.setTitle(petInfo.getName());
                        getSupportActionBar().setTitle(petInfo.getName());
                        petDetailTitle.setContentScrimColor(LoadUtil.loadColor(PetDetailActivity.this,petInfo.getBody_color()));
                        petDetailTitle.setStatusBarScrimColor(LoadUtil.loadColor(PetDetailActivity.this,petInfo.getBody_color()));
                        petDetailProfile.setValue(petInfo);

                    }
                }
            });

            PetDataManager.getPetAge(this, petChangePK, new PetDataManager.CallbackGetPetAge() {
                @Override
                public void getPetAge(Age petAge) {
                    if(petAge==null){
                        Toast.makeText(PetDetailActivity.this, getResources().getString(R.string.pet_age_null), Toast.LENGTH_SHORT).show();
                    } else {
                        Age age = petAge;
                        petDetailProfile.setAge(age);
                    }
                }
            });
        }

        tempLoad(petChangePK);
        petDetailState.setValue(stateInfo);
        if(medicalInfo.petMediInfoList.size()>0) petDetailMedical.setValue(medicalInfo.petMediInfoList.get(medicalInfo.petMediInfoList.size()-1));
        else petDetailMedical.setValue(null);
    }

    private void initToolbar(){
        // 네비게이션 바 및 툴바 세팅
        drawer =findViewById(R.id.drawerPetLayout);
        navigationView = new PetNavigationView(this,drawer, findViewById(R.id.navPetView));
        petDetailTitle = findViewById(R.id.petDetailTitle);
        toolbar = findViewById(R.id.petDetailToolbar);
        toolbar.setContentInsetStartWithNavigation(
                getResources().getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_with_nav)
                        + getResources().getDimensionPixelSize(R.dimen.pet_detail_toolbar_profile_small_width)
        );
        ToolbarUtil.setCommonToolbar(this, toolbar, petInfo.getName());
        // 네비게이션 토글 연결
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        petDetailTitle.setContentScrimColor(LoadUtil.loadColor(this,petInfo.getBody_color()));
        petDetailTitle.setStatusBarScrimColor(LoadUtil.loadColor(this,petInfo.getBody_color()));
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
                    if(data.getIntExtra(Const.PET_INFO,-1) == Const.PET_PROFILE_DELETE){
                        finish();
                    } else {
                        getData();
                        petDetailProfile.setValue(petInfo);
                    }
                }
                break;

        }
    }

    /**
     * Activity로 이동하는 메소드
     * @param cls
     * @param requestCode
     */
    private void goActivity(Class<?> cls, int requestCode){
        Intent intent= new Intent(this, cls);
        intent.putExtra(Const.PET_ID, petInfo.getPk());
        intent.putExtra(Const.PET_NAME,petInfo.getName());
        intent.putExtra(Const.PET_COLOR,petInfo.getBody_color());
        intent.putExtra(Const.PET_PROFILE_URL,petInfo.getProfileUrl());
        intent.putExtra(Const.PET_ACTIVE,petInfo.getIs_active());
        startActivityForResult(intent,requestCode);
    }

    @Override
    public void setSpinner() {
        navigationView.setPetSpinnerLocation(petInfo.getName());
    }
}
